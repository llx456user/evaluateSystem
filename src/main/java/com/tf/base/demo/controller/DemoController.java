package com.tf.base.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tf.base.demo.domain.DemoParams;
import com.tf.base.demo.domain.QueryResult;

@Controller
public class DemoController {

	@RequestMapping(value = "/demo/query", method = RequestMethod.GET)
	public String demoQueryInit() {
		
		return "demo/demo_query";
	}
	
	@RequestMapping(value = "/demo/query", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> demoQuery(DemoParams params) {
		
		Map<String, Object> result = new HashMap<String, Object>();

		int start = (params.getPage() - 1) * params.getRows();
		int total = 10;
		List<QueryResult> rows = new ArrayList<QueryResult>();
		
		for (int i = 0; i < 10; i++) {
			QueryResult e = new QueryResult();
			e.setField1("1" + i);
			e.setField2("2" + i);	
			e.setField3("3" + i);
			if(StringUtils.isNotEmpty(params.getField1()) && !params.getField1().equals(e.getField2())){
				continue;
			}
			rows.add(e);
		}
		if (total == 0) {
			result.put("rows", rows);
			result.put("total", total);
			return result;
		}
		
		result.put("total", total);
		result.put("rows", rows);
		return result;
	}
}
