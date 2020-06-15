package com.tf.base.datasource.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tf.base.common.domain.DatasourceDb;
import com.tf.base.common.domain.DatasourceFileTitle;
import com.tf.base.common.domain.DatasourceSql;
import com.tf.base.common.persistence.DatasourceDbMapper;
import com.tf.base.common.persistence.DatasourceFileTitleMapper;
import com.tf.base.common.persistence.DatasourceSqlMapper;
import com.tf.base.common.service.BaseService;
import com.tf.base.common.utils.DateUtil;
import com.tf.base.common.utils.StringUtil;
import com.tf.base.datasource.domain.DatasourceSqlParams;
import com.tf.base.util.ParamSqlImpl;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class DatasourceSqlService {

	@Autowired
	private BaseService baseService;
	@Autowired
	private DatasourceSqlMapper datasourceSqlMapper;
	@Autowired
	private DatasourceFileTitleMapper datasourceFileTitleMapper;
	@Autowired
	private DatasourceDbMapper datasourceDbMapper;

	public List<DatasourceSql> queryListBySourceID(int sourceid) {
		Example example = new Example(DatasourceSql.class);
		example.createCriteria().andEqualTo("datasourceId", sourceid);
		return datasourceSqlMapper.selectByExample(example);
	}

	public DatasourceSql queryObjectByKey(int id) {
		return datasourceSqlMapper.selectByPrimaryKey(id);
	}


	public int queryCount(DatasourceSqlParams params) {

		return datasourceSqlMapper.queryCount(params);
	}

	public List<DatasourceSql> queryList(DatasourceSqlParams params, int start) {

		List<DatasourceSql> list = datasourceSqlMapper.queryList(params, start);
		if (list != null) {
			for (DatasourceSql r : list) {
				r.setUpdateTimeStr(DateUtil.TimeToString(r.getUpdateTime()));
			}
		}
		return list;
	}

	public int saveSql(String datasourceId, String sqlName, String sqlStr) {

		Integer userid = Integer.parseInt(baseService.getUserId());
		Date now = new Date();
		DatasourceSql bean = new DatasourceSql();
		bean.setDatasourceId(Integer.parseInt(datasourceId));
		bean.setSqlName(sqlName);
		bean.setSqlStr(sqlStr);

		bean.setCreateTime(now);
		bean.setCreateUid(userid);
		bean.setIsdelete(0);
		bean.setUpdateTime(now);
		bean.setUpdateUid(userid);

		// 调试
		DatasourceDb datasourceDb = datasourceDbMapper.selectByPrimaryKey(datasourceId);
		ParamSqlImpl paramSql = new ParamSqlImpl(datasourceDb, bean);
		String[] cNames = paramSql.getColumnsName();
		paramSql.closeConnection();
		if (cNames == null || cNames.length == 0) {
			return 400;
		} else {
			int i = datasourceSqlMapper.insertSelective(bean);
			if (i == 1) {
				// 保存成功后 插入到 datasource_file_title 表中
				int num = 0;
				for (String cName : cNames) {
					DatasourceFileTitle dft = new DatasourceFileTitle();
					dft.setType(2);
					dft.setFileId(bean.getId());
					dft.setColumnName(cName);
					dft.setColumnNum(num++);
					dft.setIsdelete(0);
					dft.setCreateTime(now);
					dft.setCreateUid(userid);
					dft.setUpdateTime(now);
					dft.setUpdateUid(userid);
					datasourceFileTitleMapper.insertSelective(dft);
				}
			}
			return i;
		}
	}

	public int updateSql(String id, String datasourceId, String sqlName, String sqlStr) {
		Integer userid = Integer.parseInt(baseService.getUserId());
		Date now = new Date();
		String oldSqlName = "";
		DatasourceSql bean = datasourceSqlMapper.selectByPrimaryKey(id);
		oldSqlName = bean.getSqlName();
		bean.setDatasourceId(Integer.parseInt(datasourceId));
		bean.setSqlName(sqlName);
		bean.setSqlStr(sqlStr);

		bean.setUpdateTime(now);
		bean.setUpdateUid(userid);
		// 调试
		DatasourceDb datasourceDb = datasourceDbMapper.selectByPrimaryKey(datasourceId);
		ParamSqlImpl paramSql = new ParamSqlImpl(datasourceDb, bean);
		String[] cNames = paramSql.getColumnsName();
		paramSql.closeConnection();
		if (cNames == null || cNames.length == 0) {
			return 400;
		} else {
			int i = datasourceSqlMapper.updateByPrimaryKeySelective(bean);
			if (i == 1) {
				// 保存成功后 更新 datasource_file_title 表中
				//1、先删除老的
				Example example = new Example(DatasourceFileTitle.class);
				example.createCriteria().andEqualTo("type", 2).andEqualTo("fileId", bean.getId());
				datasourceFileTitleMapper.deleteByExample(example);
				//2、插入新的
				int num=0;
				for(String cName:cNames){
					DatasourceFileTitle dft = new DatasourceFileTitle();
					dft.setType(2);
					dft.setFileId(bean.getId());
					dft.setColumnName(cName);
					dft.setColumnNum(num++);
					dft.setIsdelete(0);
					dft.setCreateTime(now);
					dft.setCreateUid(userid);
					dft.setUpdateTime(now);
					dft.setUpdateUid(userid);
					datasourceFileTitleMapper.insertSelective(dft);
				}
			}
			return i;
		}
		
		
	}

	public int deleteSql(String id) {
		Integer userid = Integer.parseInt(baseService.getUserId());
		Date now = new Date();
		DatasourceSql bean = datasourceSqlMapper.selectByPrimaryKey(id);
		bean.setIsdelete(1);
		bean.setUpdateTime(now);
		bean.setUpdateUid(userid);
		int i = datasourceSqlMapper.updateByPrimaryKeySelective(bean);

		// 保存成功后 插入到 datasource_file_title 表中
		if (i == 1) {
			Example example = new Example(DatasourceFileTitle.class);
			example.createCriteria().andEqualTo("fileId", bean.getId());
			List<DatasourceFileTitle> dfts = datasourceFileTitleMapper.selectByExample(example);
			if (dfts != null && dfts.size() > 0) {
				for (DatasourceFileTitle dft : dfts) {
					dft.setIsdelete(1);
					datasourceFileTitleMapper.updateByPrimaryKey(dft);
				}
			}
		}
		return i;
	}

	/**
	 * 验证名sqlName是否重复使用
	 * 
	 * @param id
	 * @param sqlName
	 * @return
	 */
	public boolean checkName(String id, String sqlName) {

		Example example = new Example(DatasourceSql.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("sqlName", sqlName).andEqualTo("isdelete", 0);
		if (!StringUtil.isEmpty(id)) {
			criteria.andNotEqualTo("id", id);
		}

		List<DatasourceSql> list = datasourceSqlMapper.selectByExample(example);
		if (list == null || list.size() <= 0) {
			return true;
		}
		return false;
	}
}
