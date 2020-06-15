/**
 * 
 */
package com.tf.base.common.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 文件操作工具类
 *
 */
public class FileUtil {

	/**
	 * 解析多个上传文件
	 * @param request
	 * @return
	 */
	public static List<MultipartFile> resolverMultipartFile(HttpServletRequest request){
		//创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
        	List<MultipartFile> fileList = new ArrayList<MultipartFile>();
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){  
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if(file != null){ 
                	fileList.add(file);
                }  
            }  
              
        }
        return null;
	}
	
	/**
	 * 解析单个上传文件
	 * @param request
	 * @return
	 */
	public static MultipartFile resolverSingleFile(HttpServletRequest request){
		//创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){  
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if(file != null){ 
                	return file;
                }  
            }  
              
        }
        return null;
	}
}
