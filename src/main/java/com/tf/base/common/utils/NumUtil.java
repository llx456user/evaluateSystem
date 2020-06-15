package com.tf.base.common.utils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 数字处理类
 * @author yangjunwei
 *
 */
public class NumUtil {
	
	
	public static BigDecimal nullToZero(BigDecimal value){
		
		if(value == null){
			 return BigDecimal.ZERO ;
		}
		return value ;
	}
	
    public static BigDecimal nullToZero(Integer value){
		
		if(value == null){
			 return BigDecimal.ZERO ;
		}
		return new BigDecimal(value) ;
	}

	
    public static BigDecimal toBigDecimal(String str) {
		if(StringUtil.isEmpty(str)){
			return BigDecimal.ZERO;
		}
		if(NumUtil.isNumeric(str)){
			return new BigDecimal(str);
		}
		return BigDecimal.ZERO;
	}
    
    public static boolean isNumeric(String str){ 
	   Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]*"); 
	   Matcher isNum = pattern.matcher(str);
	   if( !isNum.matches() ){
	       return false; 
	   } 
	   return true; 
	}
}
