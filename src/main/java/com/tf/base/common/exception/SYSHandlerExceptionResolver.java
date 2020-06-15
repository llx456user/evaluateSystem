package com.tf.base.common.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class SYSHandlerExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		
		response.setCharacterEncoding("UTF-8");
		
		try {
			if (ex instanceof HttpSessionRequiredException) {
				response.sendError(401);
			}else  {
				response.sendError(500);
			}
			
	    } catch (IOException e) {  
	          
	    }  
		
		return null;
	}

}
