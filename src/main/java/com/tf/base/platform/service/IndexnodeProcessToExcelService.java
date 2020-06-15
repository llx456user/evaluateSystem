package com.tf.base.platform.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.converter.ExcelToFoUtils;
import org.springframework.stereotype.Service;

import com.tf.base.common.utils.ExcelUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 指标节点执行 导出Excel服务类
 * @author yangkun
 *
 */
@Service
public class IndexnodeProcessToExcelService {

	/**
	 * {"p1":[2,3,3,5,3],"p2":[4,4,4,8,4]}
	 * @param nodeInputParamJson
	 */
	public void toExcel(JSONObject nodeInputParamJson,String fileName,HttpServletResponse response){
		
		Set<String> keys=nodeInputParamJson.keySet();
		/**要导出的列表，按照一定的规则解析成以下list格式*/
		List<Map<String, Object>> dataList=new ArrayList<>();
		/**excel 列表头*/
		String[] headers=new String[keys.size()];
		/**字段对应的key，dataList中的元素Map的key相对应*/
		String[] fieldNames=new String[keys.size()];
		
		int i=0;
		for(String key:keys){
			headers[i]=key;
			fieldNames[i]=key;
			Object object=nodeInputParamJson.get(key);
			if(object instanceof JSONArray){
				JSONArray ja=(JSONArray) object;
				for(int x=0;x<ja.size();x++){
					Map<String,Object> map=(Map<String, Object>) (dataList.get(x)==null?new HashMap<>():dataList.get(x));
					map.put(key, ja.get(x));
					dataList.add(map);
				}
			}else{
				Map<String,Object> map=(Map<String, Object>) (dataList.get(0)==null?new HashMap<>():dataList.get(0));
				map.put(key, object);
				dataList.add(map);
			}
			
			i++;
		}
		
		//开始导出Excel
		ExcelUtils.exportExcel(fileName, headers, fieldNames, dataList, response, null, null);
	}
	
}
