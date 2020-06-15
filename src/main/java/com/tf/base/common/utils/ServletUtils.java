/**
 * Http与Servlet工具类
 */
package com.tf.base.common.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

public class ServletUtils {
	/**
	 * 用于servlet接收数据
	 * @param request
	 * @return
	 */
	public static String receiveData(HttpServletRequest request){
		String json = null;
		try{
			BufferedReader br = new BufferedReader(new 
					InputStreamReader((ServletInputStream)request.getInputStream(),"UTF-8"));
	        String line = null;
	 
	        StringBuffer sb = new StringBuffer();
	        while((line = br.readLine())!=null){
	         sb.append(line);
	        }
	        json = sb.toString();
	        sb = null;
	        line = null;
	        br.close();
	        br = null;
		}catch(Exception e){
			e.printStackTrace();
		}
		return json;
	}
}
