package com.tf.base.datasource.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.quartz.impl.jdbcjobstore.DBSemaphore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tf.base.common.domain.DatasourceDb;
import com.tf.base.common.domain.DatasourceFile;
import com.tf.base.common.service.CommonService;
import com.tf.base.common.utils.JdbcUtils;
import com.tf.base.datasource.domain.DbQueryResult;
import com.tf.base.datasource.service.DbService;
import com.tf.base.demo.domain.DemoParams;
import com.tf.base.demo.domain.QueryResult;

@Controller
public class DbController {
	
	@Autowired
	private DbService dbService;
	@Autowired
	private CommonService commonService;

	@RequestMapping(value = "/datasource/dbList", method = RequestMethod.GET)
	public String queryInit(Model model) {
		
		List<DbQueryResult> rows = dbService.queryList();
		
		model.addAttribute("total", rows.size());
		model.addAttribute("rows", rows);
		return "datasource/dbList";
	}
	
	@RequestMapping(value = "/datasource/dbSave")
	@ResponseBody
	public Map<String, Object> dbSave(DatasourceDb db) {
		
		int i = dbService.save(db);
		return i > 0 ? commonService.returnMsg(1, "保存成功") : commonService.returnMsg(0, "保存失败");
	}
	
	@RequestMapping(value = "/datasource/dbDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> dbDel(String id) {
		int i = 0;
		try {
			
			i = dbService.dbDel(id);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return i > 0 ? commonService.returnMsg(1, "删除成功") : commonService.returnMsg(0, "删除失败");
	}
	@RequestMapping(value = "/datasource/dbTest", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> dbTest(String sourceName,String sourceType,String server,String url,String username,String password) {
		int i = 0;
		try {
			
			i = JdbcUtils.getConn(server, url, username, password) != null ? 1 : 0;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return i > 0 ? commonService.returnMsg(1, "数据库连接成功") : commonService.returnMsg(0, "数据库连接失败");
	}
}
