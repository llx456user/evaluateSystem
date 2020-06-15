package com.tf.base.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;

/**
 * 字符串工具类.
 * 
 */
public class StringUtil {
	
	

    /**
     * 判断是否是String型变量
     * @param bean
     * @return
     */
    public static boolean isStr(Object bean) {
		return bean.getClass().getName().equals("java.lang.String");
	}
	
	
	/**
	 * 判断是否是数字 整数或小数
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str){
		
		if(isEmpty(str)){
			return false ;
		}
		String pattern = "[0-9]+(.[0-9]+)?";  
        //对()的用法总结：将()中的表达式作为一个整体进行处理，必须满足他的整体结构才可以。  
        //(.[0-9]+)? ：表示()中的整体出现一次或一次也不出现  
        Pattern p = Pattern.compile(pattern);  
        Matcher m = p.matcher(str);  
        boolean b = m.matches(); 
        return  b ;
	}

	/**
	 * 空字符串判断.
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.trim().length() == 0);
	}
	
	/**
	 * 空字符串判断.
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static boolean objIsEmpty(Object str) {
		
		return (str == null || str.toString().trim().length() == 0);
	}

	/**
	 * 根据正则取得第一个匹配项的第一个分组内容.
	 * 
	 * @param content
	 *            内容
	 * @param regetx
	 *            正则表达式，带一个分组
	 * @return
	 */
	public static String regexGetFirstData(String content, String regetx) {
		Matcher m = Pattern.compile(regetx, Pattern.DOTALL).matcher(content);
		if (m.find()) {
			return m.group(1);
		}
		return "";
	}

	/**
	 * 根据正则取得所有匹配项的第一个分组内容.
	 * 
	 * @param content
	 *            内容
	 * @param regetx
	 *            正则表达式，带一个分组
	 * @return
	 */
	public static List<String> regexGetAllData(String content, String regetx) {
		List<String> resultList = new ArrayList<String>();
		Matcher m = Pattern.compile(regetx, Pattern.DOTALL).matcher(content);
		while (m.find()) {
			resultList.add(m.group(1));
		}
		return resultList;
	}
	/**
	 * 判断比对值是否在数组中
	 * @param strv
	 * @param value
	 * @return
	 */
	public static boolean isExistsValue(String[]  strv ,String value){
		
		if(strv== null ||  StringUtil.isEmpty(value)){
			return false ;
		}
		for(String str : strv ){
			if(str.equals(value)){
				return true;
			}
		}
		return false;
	}
	
	
	public static String strFilterNull(String str){
		return str==null?"":str;
	}
	
	/**
	 * 将字符串为空补充指定数量空格
	 * @param str
	 * @param len 空格数量
	 * @return
	 */
	public static String strToFillSpace(String str, int len){
		
		String space = "";
		if(StringUtil.isEmpty(str)){
			
			for(int i=0;i<len;i++){
				space = space + " ";
			}
			return space;
		}
		return strFilterNull(str) ;
		
	}
	/**
	 * 对list数组拼接成字符串
	 * @param list
	 * @param symbols  连接符合  如 , ; 
	 * @return
	 */
	public static String listToStr(List<String> list, String symbols){
		
		if(list == null || list.isEmpty()){
			return "" ;
		}
		int index = 0 ;
		StringBuffer  sbf = new StringBuffer();
		for(String str : list){
			if(index == 0)
			  sbf.append(str);
			else
			  sbf.append(symbols).append(str);
			index ++ ;
		}
		return sbf.toString();
	}
	
	public static List<String> stringToList(String str) {
		
		List<String> list = new ArrayList<String>();
		if (!StringUtils.isEmpty(str)) {
			String[] strings = str.split(",");
			for (String strr : strings) {
				list.add(strr);
			}
		}
		return list;
	}
	
	
	/**
	 * 判断字符串是否为json
	 * @param str
	 * @return
	 */
	public static boolean isJson(String str){
		try {
			JSONArray.fromObject(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
