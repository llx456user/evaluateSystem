package com.tf.base.common.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;


@Service
public class CommonService {
	public static  final  int success=1;
	public static  final  int fail=0;
	/**
	 * 返回信息及标示
	 * @param status
	 * @param msg
	 * @return
	 */
	public Map<String,Object> returnMsg(int status,String msg){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("status", status);
		result.put("msg", msg);
		return result;
	}
	/**
	 *返回信息及标示+保留字段 
	 * @param status
	 * @param msg
	 * @param info
	 * @return
	 */
	public Map<String,Object> returnMsg(int status,String msg,String info){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("status", status);
		result.put("msg", msg);
		result.put("info", info);
		return result;
	}

	public Map<String,Object> returnSucessMsg(String msg){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("status", success);
		result.put("msg", msg);
		return result;
	}

	public Map<String,Object> returnFailMsg(String msg){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("status", fail);
		result.put("msg", msg);
		return result;
	}
}
