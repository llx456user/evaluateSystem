/**
 * 
 */
package com.tf.base.common.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author spring
 * 
 */
public class JSONUtil {

	private static ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * 多传JSON字段过来不要报错，不设置会报错的。
	 */
	static {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * 将对象转换为JSON字符串
	 * 
	 * @param object
	 *            对象
	 */
	public static String toJson(Object object) {
		Assert.notNull(object);
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将对象转换为JSON流
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param contentType
	 *            contentType
	 * @param object
	 *            对象
	 */
	public static void toJson(HttpServletResponse response, String contentType,
			Object value) {
		Assert.notNull(response);
		Assert.notNull(contentType);
		Assert.notNull(value);
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType(contentType);
			mapper.writeValue(response.getWriter(), value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将对象转换为JSON流
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param object
	 *            对象
	 */
	public static void toJson(HttpServletResponse response, Object value) {
		Assert.notNull(response);
		Assert.notNull(value);
		try {
			mapper.writeValue(response.getWriter(), value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将JSON字符串转换为对象
	 * 
	 * @param json
	 *            JSON字符串
	 * @param valueType
	 *            对象类型
	 */
	public static <T> T toObject(String json, Class<T> valueType) {
		if(StringUtil.isEmpty(json)){
			return null;
		}
		Assert.notNull(json);
		Assert.notNull(valueType);
		try {
			return mapper.readValue(json, valueType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将JSON字符串转换为对象
	 * 
	 * @param json
	 *            JSON字符串
	 * @param typeReference
	 *            对象类型
	 */
	public static <T> T toObject(String json, TypeReference<?> typeReference) {
		Assert.notNull(json);
		Assert.notNull(typeReference);
		try {
			return mapper.readValue(json, typeReference);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将JSON字符串转换为对象
	 * 
	 * @param json
	 *            JSON字符串
	 * @param javaType
	 *            对象类型
	 */
	public static <T> T toObject(String json, JavaType javaType) {
		Assert.notNull(json);
		Assert.notNull(javaType);
		try {
			return mapper.readValue(json, javaType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将json格式的字符串解析成Map对象 <li>
	 * json格式：{"user":"admin","pwd":"pwd","fromCity" :"BJS","toCity":"LAX"}
	 */
	public static HashMap<String, String> toHashMap(Object object) {
		HashMap<String, String> data = new HashMap<String, String>();
		// 将json字符串转换成jsonObject
		JSONObject jsonObject = JSONObject.fromObject(object);
		Iterator it = jsonObject.keys();
		// 遍历jsonObject数据，添加到Map对象
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			String value = String.valueOf(jsonObject.get(key));
			data.put(key.toLowerCase(), value);
		}
		return data;
	}

	public static  List<?> toArrayList(String json, Class<?> clazz) {
		JSONArray jsonArray = JSONArray.fromObject(json);
		return (List<?>) JSONArray.toCollection(jsonArray, clazz);
		
	}
	public static JSONObject toJSONObject(Object object){
		return JSONObject.fromObject(object);
	}
}
