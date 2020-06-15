package com.tf.base.datasource.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tf.base.common.domain.DatasourceDb;
import com.tf.base.common.domain.DatasourceFileTitle;
import com.tf.base.common.domain.DatasourceSql;
import com.tf.base.common.persistence.DatasourceDbMapper;
import com.tf.base.common.persistence.DatasourceSqlMapper;
import com.tf.base.common.service.CommonService;
import com.tf.base.common.utils.JSONUtil;
import com.tf.base.common.utils.JdbcUtils;
import com.tf.base.common.utils.StringUtil;
import com.tf.base.datasource.domain.DatasourceSqlParams;
import com.tf.base.datasource.service.DatasourceSqlService;

import net.sf.json.JSONObject;
import tk.mybatis.mapper.entity.Example;

@Controller
public class DatasourceSqlController {

	@Autowired
	private DatasourceSqlService datasourceSqlService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private DatasourceSqlMapper datasourceSqlMapper;
	@Autowired
	private DatasourceDbMapper datasourceDbMapper;

	@RequestMapping(value = "/sql/sqlList", method = RequestMethod.GET)
	public String queryInit(Model model) {

		// 模型类型列表
		Example example =new Example(DatasourceDb.class);
		example.createCriteria().andEqualTo("isdelete", 0);
		List<DatasourceDb> dbList = datasourceDbMapper.selectByExample(example);

		model.addAttribute("dbList", dbList);

		return "datasource/sqlList";
	}

	@RequestMapping(value = "/sql/sqlList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sqlQuery(DatasourceSqlParams params) {

		Map<String, Object> result = new HashMap<String, Object>();

		int start = (params.getPage() - 1) * params.getRows();
		List<DatasourceSql> rows = new ArrayList<DatasourceSql>();

		int total = datasourceSqlService.queryCount(params);

		if (total == 0) {
			result.put("rows", rows);
			result.put("total", total);
			return result;
		}
		rows = datasourceSqlService.queryList(params, start);

		result.put("total", total);
		result.put("rows", rows);
		return result;
	}

	@RequestMapping(value = "/sql/saveSql", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveSql(String id, String datasourceId, String sqlName, String sqlStr) {
		int i = 0;
		if(!datasourceSqlService.checkName(id, sqlName)){
			return commonService.returnMsg(0, "sql名称重复");
		}
		if (StringUtil.isEmpty(id)) {
			i = datasourceSqlService.saveSql(datasourceId, sqlName, sqlStr);
		} else {
			i = datasourceSqlService.updateSql(id, datasourceId, sqlName, sqlStr);
		}
		if(i==400){
			return commonService.returnMsg(0, "sql测试失败");
		}
		return i > 0 ? commonService.returnMsg(1, "保存成功") : commonService.returnMsg(0, "保存失败");
	}

	@RequestMapping(value = "/sql/deleteSql", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSql(String id) {
		int i = datasourceSqlService.deleteSql(id);
		return i > 0 ? commonService.returnMsg(1, "删除成功") : commonService.returnMsg(0, "删除失败");
	}

	@RequestMapping(value = "/sql/toAddSql")
	public String toAdd(Model model, String datasourceId) {
		DatasourceSql bean = new DatasourceSql();
		bean.setDatasourceId(Integer.parseInt(datasourceId));
		DatasourceDb datasourceDb=datasourceDbMapper.selectByPrimaryKey(datasourceId);
		bean.setSourceName(datasourceDb.getSourceName());
		model.addAttribute("bean", bean);
		return "datasource/addSql";
	}

	@RequestMapping(value = "/sql/toEditSql")
	public String toEdit(Model model, Integer id) {
		DatasourceSql bean = datasourceSqlMapper.selectByPrimaryKey(id);
		DatasourceDb datasourceDb=datasourceDbMapper.selectByPrimaryKey(bean.getDatasourceId());
		bean.setSourceName(datasourceDb.getSourceName());
		model.addAttribute("bean", bean);
		return "datasource/addSql";
	}

	@RequestMapping(value = "/sql/vewSql")
	public String vewSql(Model model, String id) {
		DatasourceSql bean = datasourceSqlMapper.selectByPrimaryKey(id);
		DatasourceDb datasourceDb=datasourceDbMapper.selectByPrimaryKey(bean.getDatasourceId());
		bean.setSourceName(datasourceDb.getSourceName());
		model.addAttribute("bean", bean);

		return "datasource/viewSql";
	}
	
	@RequestMapping(value = "/sql/test", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> test(Integer id) {
		Map<String, Object> retMap=commonService.returnMsg(0, "sql测试失败");
		DatasourceSql bean = datasourceSqlMapper.selectByPrimaryKey(id);
		DatasourceDb datasourceDb=datasourceDbMapper.selectByPrimaryKey(bean.getDatasourceId());
		
		//{"server":"com.mysql.jdbc.Driver","url":"jdbc:mysql://47.104.1.212:3306/evaluation_integration","username":"evl_inte","password":"evl_inte0321"}
		JSONObject dbConfigJson=JSONUtil.toJSONObject(datasourceDb.getConfig());
		
		try{
			String jdbcName=dbConfigJson.getString("server");
			String url=dbConfigJson.getString("url");
			String username=dbConfigJson.getString("username");
			String password=dbConfigJson.getString("password");
			Connection conn=JdbcUtils.getConn(jdbcName, url, username, password);
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(bean.getSqlStr());
			if(rs!=null){
				retMap=commonService.returnMsg(1, "sql测试成功");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  retMap;
	}
}
