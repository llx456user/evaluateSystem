package com.tf.base.common.utils;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class BeanUtils {

	/** 
     * @Description <p>获取到对象中属性为null的属性名  </P> 
     * @param source 要拷贝的对象 
     * @return 
     */  
    public static String[] getNullPropertyNames(Object source) {  
        final BeanWrapper src = new BeanWrapperImpl(source);  
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();  
   
        Set<String> emptyNames = new HashSet<String>();  
        for (java.beans.PropertyDescriptor pd : pds) {  
            Object srcValue = src.getPropertyValue(pd.getName());  
            if (srcValue == null)  
                emptyNames.add(pd.getName());
            if(srcValue instanceof String){
            	if(srcValue.toString().equals("null") || srcValue.toString().equals(""))
            		emptyNames.add(pd.getName());
            		
            }
        }  
        String[] result = new String[emptyNames.size()];  
        return emptyNames.toArray(result);  
    }  
   
    /** 
     * @Description <p> 拷贝非空对象属性值 </P> 
     * @param source 源对象 
     * @param target 目标对象 
     */  
    public static void copyPropertiesIgnoreNull(Object source, Object target) {  
    	org.springframework.beans.BeanUtils.copyProperties(source, target, getNullPropertyNames(source));  
    } 
}
