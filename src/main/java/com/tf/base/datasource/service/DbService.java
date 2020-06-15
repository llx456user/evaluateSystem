package com.tf.base.datasource.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.tf.base.common.domain.DatasourceDb;
import com.tf.base.common.persistence.DatasourceDbMapper;
import com.tf.base.common.service.BaseService;
import com.tf.base.common.utils.DateUtil;
import com.tf.base.datasource.domain.DbQueryResult;

import tk.mybatis.mapper.entity.Example;

@Service
public class DbService {

	@Autowired
	private BaseService baseService;
	@Autowired
	private DatasourceDbMapper datasourceDbMapper;
	
	

	public List<DbQueryResult> queryList() {
		
		List<DbQueryResult> dbQueryResults = new ArrayList<>();
		
		Example example = new Example(DatasourceDb.class);
		example.setOrderByClause(" id desc");
		example.createCriteria().andEqualTo("isdelete", 0);
		List<DatasourceDb> dbDatasourceDbs = datasourceDbMapper.selectByExample(example);
		for (DatasourceDb datasourceDb : dbDatasourceDbs) {
			DbQueryResult target = new DbQueryResult();
			BeanUtils.copyProperties(datasourceDb, target);
			String updateTimeStr = DateUtil.TimeToString(datasourceDb.getUpdateTime());
			target.setUpdateTimeStr(updateTimeStr);
			JSONObject json = (JSONObject) JSONObject.parse(datasourceDb.getConfig());
			String url = json.getString("url");
			target.setUrl(url);
			dbQueryResults.add(target);
		}
		return dbQueryResults;
	}



	public int save(DatasourceDb db) {
		Date now = new Date();
		Integer userid = Integer.parseInt(baseService.getUserId());
		
		if(db.getId() != null && !db.getId().toString().equals("")){
			db.setUpdateTime(now);
			db.setUpdateUid(userid);
			return datasourceDbMapper.updateByPrimaryKeySelective(db);
		}else{
			db.setCreateTime(now);
			db.setCreateUid(userid);
			db.setUpdateTime(now);
			db.setUpdateUid(userid);
			db.setIsdelete(0);
			return datasourceDbMapper.insertSelective(db);
		}
	}



	public int dbDel(String id) {
		DatasourceDb db = new DatasourceDb();
		db.setId(Integer.parseInt(id));
		db.setIsdelete(1);
		return datasourceDbMapper.updateByPrimaryKeySelective(db);
	}
}
