package com.tf.base.project.controller;

import java.util.*;

import com.tf.base.common.domain.IndexNodeProcess;
import com.tf.base.common.persistence.IndexNodeProcessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import net.sf.json.JSONObject;


@Controller
public class GeneratePicController {
	@Autowired
private IndexNodeProcessMapper indexNodeProcessMapper ;
	@RequestMapping(value = "/generate/pic")
    @ResponseBody
    public JSONObject generate(String type,String indexId,String nodeKey,String assessId){
		Map pMap = new HashMap();
		pMap.put("indexId",indexId);
		pMap.put("businessId",assessId);
		pMap.put("nodeKey",nodeKey);
		List<IndexNodeProcess> pList = indexNodeProcessMapper.getNodeProcessByBusinessId(pMap);
		if(pList!=null&&pList.size()>0){
			IndexNodeProcess indexNodeProcess = pList.get(0);
			String jsonString = indexNodeProcess.getNodeInputParamExp();
			return  JSONObject.fromObject(jsonString);
		}
//		if("bar".equals(type)){
//			List<IndexNodeProcess> pList = indexNodeProcessMapper.getNodeProcessByBusinessId(pMap);
//			if(pList!=null&&pList.size()>0){
//				IndexNodeProcess indexNodeProcess = pList.get(0);
//				String jsonString = indexNodeProcess.getNodeInputParam();
//				return  JSONObject.fromObject(jsonString);
//			}
////        	return generateBar();
//        }else if("pie".equals(type)){
//        	return generatePie();
//        }else if("line".equals(type)){
//
//        	return generateLine();
//        }else if("Hline".equals(type)){
//
//        	return generateHLine();
//        }else if("scatter".equals(type)){
//
//        	return generateScatter();
//        }
        return null;
    }

	private JSONObject generateBar() {
		JSONObject jo=new JSONObject();
		List<String> xArrays = Arrays.asList("a1","a2","a3","a4");

        List<JSONObject> datas=  new ArrayList<>();
        JSONObject jsonObj = new JSONObject();
        jsonObj.element("name", "b1").element("data", Arrays.asList("1","3","5","7"));
        datas.add(jsonObj);
        JSONObject jsonObj2 = new JSONObject();
        jsonObj2.element("name", "b2").element("data", Arrays.asList("2","4","6","8"));
        datas.add(jsonObj2);

        jo.put("invNames",xArrays);
        jo.put("loanCounts",datas);
        return jo;
	}
	private JSONObject generateLine() {
		JSONObject jo=new JSONObject();
		List<String> xArrays = Arrays.asList("a1","a2","a3","a4");

		List<JSONObject> datas=  new ArrayList<>();
		JSONObject jsonObj = new JSONObject();
		jsonObj.element("name", "b1").element("data", Arrays.asList("1","3","5","2"));
		datas.add(jsonObj);
		JSONObject jsonObj2 = new JSONObject();
		jsonObj2.element("name", "b2").element("data", Arrays.asList("2","4","6","5"));
		datas.add(jsonObj2);

		jo.put("invNames",xArrays);
		jo.put("loanCounts",datas);
		return jo;
	}

	private JSONObject generateHLine() {
		JSONObject jo=new JSONObject();
		List<String> xArrays = Arrays.asList("a1","a2","a3","a4");

		List<JSONObject> datas=  new ArrayList<>();
		JSONObject jsonObj = new JSONObject();
		jsonObj.element("name", "b1").element("data", Arrays.asList("1","3","5","3"));
		datas.add(jsonObj);
		JSONObject jsonObj2 = new JSONObject();
		jsonObj2.element("name", "b2").element("data", Arrays.asList("2","4","6","4"));
		datas.add(jsonObj2);

		jo.put("invNames",xArrays);
		jo.put("loanCounts",datas);
		return jo;
	}
	private JSONObject generatePie() {
		JSONObject jo=new JSONObject();
		List<String> xArrays = Arrays.asList("a1","a2","a3","a4");

		List<JSONObject> datas=  new ArrayList<>();
        JSONObject jsonObj = new JSONObject();
        jsonObj.element("name", "a1").element("value", "1");
        datas.add(jsonObj);
        JSONObject jsonObj2 = new JSONObject();
        jsonObj2.element("name", "a2").element("value", "2");
        datas.add(jsonObj2);
        JSONObject jsonObj3 = new JSONObject();
        jsonObj3.element("name", "a3").element("value", "3");
        datas.add(jsonObj3);
        JSONObject jsonObj4 = new JSONObject();
        jsonObj4.element("name", "a4").element("value", "4");
        datas.add(jsonObj4);

        jo.put("invNames",xArrays);
        jo.put("loanCounts",datas);
		return jo;
	}
	private JSONObject generateScatter() {
		JSONObject jo=new JSONObject();
		List<JSONObject> datas=  new ArrayList<>();
		JSONObject jsonObj = new JSONObject();
		jsonObj.element("name", "a1").element("value",new String[]{"8","20"});
		datas.add(jsonObj);
		JSONObject jsonObj2 = new JSONObject();
		jsonObj2.element("name", "a2").element("value",new String[]{"2","13"});
		datas.add(jsonObj2);
		JSONObject jsonObj3 = new JSONObject();
		jsonObj3.element("name", "a3").element("value",new String[]{"10","6"});
		datas.add(jsonObj3);
		JSONObject jsonObj4 = new JSONObject();
		jsonObj4.element("name", "a4").element("value",new String[]{"21","3"});
		datas.add(jsonObj4);
		jo.put("loanCounts",datas);
		return jo;
	}
}
