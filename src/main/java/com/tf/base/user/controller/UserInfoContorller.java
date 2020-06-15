package com.tf.base.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tf.base.common.service.BaseService;
import com.tf.permission.client.constants.PermissionConstants;
import com.tf.permission.client.entity.ResourceInfo;
import com.tf.permission.client.entity.User;
import com.tf.permission.client.service.PermissionClientService;

import net.sf.json.JSONArray;





@Controller
public class UserInfoContorller {
	@Autowired
	private BaseService baseService;
	@Autowired
	private PermissionClientService permissionClientService;
	
    // 登录
	@RequestMapping(value="/userInfoContorller/login",method=RequestMethod.POST)
	public String login(HttpServletRequest request, 
			HttpServletResponse response ,ModelMap model) throws Exception{
		
		  String exceptionClassName = (String)request.getAttribute("shiroLoginFailure");
		  String error = null;
		  
		  if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
	            error = "用户名/密码错误";
	        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
	            error = "用户名/密码错误";
	        } else if(exceptionClassName != null) {
	            error = "其他错误：" + exceptionClassName;
	        }
		 
		  model.addAttribute("error", error);
		  
		  if (!StringUtils.isEmpty(error)) {
			  return "login/login";
		  }

		  return "redirect:/index";
	        
	}
	
	@RequestMapping(value="/ajaxLogin",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> ajaxLogin(String username,String password,HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		
		Map<String,Object> ret = new HashMap<String,Object>();
		
		/*Subject currentUser = SecurityUtils.getSubject();  
		UsernamePasswordToken token = null ;
		if (!currentUser.isAuthenticated()) {
			
			token = new UsernamePasswordToken(username,password);
			try {
				currentUser.login(token);
				request.getSession().setAttribute(
						PermissionConstants.CURRENT_USER,
						permissionClientService.findUserByUsername(username));
				this.cacheUserGroupInfo((User)request.getSession().getAttribute(PermissionConstants.CURRENT_USER), request.getSession());
				return this.setSuccessStatus(ret, request);
			} catch (Exception ex) {
				ret.put("status", -1);
				ex.printStackTrace();
			}
		}else{
			//表示已有权限
            return this.setSuccessStatus(ret, request);
		}*/
		return ret;
	}
	
	
	public Map<String,Object>  setSuccessStatus(Map<String,Object> ret ,HttpServletRequest request){
		
		SavedRequest req = WebUtils.getAndClearSavedRequest(request);
		ret.put("status", 0);
		ret.put("SavedRequest", req);
		return ret ;
	}
	
	
	@RequestMapping(value="/userInfoContorller/update_password",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> update_password(String oldpassword,String newpassword) throws Exception{
	
		Map<String, String> map = new HashMap<String, String>();
		try {
			
			/*User u = (User) baseService.getBaseInfo("user");
			
			if (!oldpassword.equals(u.getPassword())) {
				
				map.put("mes", "原密码不正确。");
				map.put("status", "-1");
				
				return map; 
			}
			
			int res = permissionClientService.modifyUserPassword(u.getUsername(), oldpassword, newpassword);
			
			if (res == 0) {
				
				map.put("mes", "更新成功。");
				map.put("status", "0");
			} else {
				
				map.put("mes", "更新失败。");
				map.put("status", "-1");
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("mes", "更新失败");
			map.put("status", "-1");
//			throw e;
		}
		return map; 
	}
	


	@RequestMapping(value="/login",method = RequestMethod.GET)
	public String login() throws Exception{
		return "login/login";
	}
	
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) {
		
		User u = (User) request.getAttribute(PermissionConstants.CURRENT_USER);
		request.getSession().setAttribute(PermissionConstants.CURRENT_USER, u);
		model.addAttribute("showname", u.getShowname());
		List<ResourceInfo> meuns  =  permissionClientService.getMenusByUserName(u.getUsername());
//		model.addAttribute("menus", JSON.toJSONString(meuns));
		model.addAttribute("menus", meuns);
		model.addAttribute("showname", u.getShowname());
		
//		model.addAttribute("menus", JSONArray.fromObject("[{\"resourcename\":\"MenuTest主菜单\",\"subResources\":[{\"resourcename\":\"Menu1\",\"resourceurl\":\"demo/query\"}]}]"));
//		return "index/index";
		return "index/zui";
	}
	
	
	
	
}