package com.tf.base.common.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tf.base.common.service.BaseService;
import com.tf.permission.client.constants.PermissionConstants;
import com.tf.permission.client.entity.User;

public class ControllerInterceptor extends HandlerInterceptorAdapter {

	private static Logger log = LoggerFactory.getLogger(ControllerInterceptor.class);
	
	@Autowired
	private BaseService baseService;

	/**
     * handler执行前
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws HttpSessionRequiredException,IOException {

        // SESSION为空时，抛出SESSION异常
        if (request.getSession() == null) {

            throw new HttpSessionRequiredException("noSession");
        }
        
        User user = (User) request.getSession().getAttribute(PermissionConstants.CURRENT_USER);
        
        String name = null;
        
        if (user != null) {
        	name = user.getUsername();
        }
        
        baseService.setBaseInfo("", "", user);

        if (log.isInfoEnabled()) {

            StringBuffer sb = new StringBuffer();
            sb.append("***");
            sb.append(" Controller preHandle");
            sb.append(" url:");
            sb.append(request.getRequestURI());
            sb.append(" user:");
            sb.append(" ***");

            log.info(sb.toString());
        }

        return true;
    }
    
    /**
     * handler执行后
     */
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView){

        if (log.isInfoEnabled()) {

            StringBuffer sb = new StringBuffer();
            sb.append("***");
            sb.append(" Controller postHandle");
            sb.append(" url:");
            sb.append(modelAndView == null ? null : modelAndView.getViewName());
            sb.append(" ***");

            log.info(sb.toString());
        }
    }
}
