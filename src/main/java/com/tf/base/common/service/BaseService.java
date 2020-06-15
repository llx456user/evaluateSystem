package com.tf.base.common.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tf.base.common.utils.ApplicationProperties;
import com.tf.permission.client.constants.PermissionConstants;
import com.tf.permission.client.entity.DepartmentInfo;
import com.tf.permission.client.entity.User;
import com.tf.permission.client.service.PermissionClientService;

@Service
public class BaseService {
	
	private static ThreadLocal<Map<String, Object>> userBaseInfo = new InheritableThreadLocal<Map<String,Object>>();
	
	private static Map<String, Object> userDeptsInfo = new HashMap<String,Object>();
	
	
	@Autowired
	private PermissionClientService permissionClientService;
	
	
	
	
	public String getCurrentUserDeptId(){
		
		Subject subject = SecurityUtils.getSubject();
		
		Session session = subject.getSession();
		
		User u = (User) session.getAttribute(PermissionConstants.CURRENT_USER);
		
		return u.getDepartment();
	}
	
	public void setBaseInfo(String ip,String mac,User user) {
		
		userBaseInfo.remove();
		
		Map<String, Object> newBaseInfo = new HashMap<String, Object>();
		
		newBaseInfo.put("ip" , ip);
		newBaseInfo.put("mac" , mac);
		newBaseInfo.put("user" , user);
		
		userBaseInfo.set(newBaseInfo);
	}
	
	public Object getBaseInfo(String key) {
		
		return userBaseInfo.get().get(key);
	}
	
	public String getUserName() {

		if (this.getBaseInfo("user") != null) {
			return ((User) this.getBaseInfo("user")).getUsername();
		}
		return null;
	}
	
	public String getUserId() {

		if (this.getBaseInfo("user") != null) {
			return ((User) this.getBaseInfo("user")).getId()+"";
		}
		return null;
	}

	public String getShowName() {

		if (this.getBaseInfo("user") != null) {
			return ((User) this.getBaseInfo("user")).getShowname();
		}
		return null;

	}
	
	public String getDepartment(){
		
		return ((User)this.getBaseInfo("user")).getDepartment();
	}
	
	public String getDeptName(){
		
		return getDeptNameById(getDepartment());
	}
	
	public List<DepartmentInfo> getAllDepts(){
		
		List<DepartmentInfo> allDepartments = permissionClientService.findAllDepartments();
		
		return allDepartments;
	}
	
	public String getDeptNameById(String id){
		return (String) userDeptsInfo.get(id);
	}
	
	public synchronized void putAllDepts(){
		
		List<DepartmentInfo> allDepartments = permissionClientService.findAllDepartments();
		
		for (DepartmentInfo departmentInfo : allDepartments) {
			userDeptsInfo.put(departmentInfo.getId(), departmentInfo.getName());
			
		}
	}
	
	public String getTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		
		Date now = new Date(); 
		
		return df.format( now );
	}
	
	public String parseDateFormat(String inputFormat, String outputFormat, String dateStringToParse){
		
		if ("".equals(dateStringToParse) || dateStringToParse == null) {
			return "";
		}
		
		SimpleDateFormat bartDateFormat =  new SimpleDateFormat(inputFormat);  
		
		Date date = null;
		try {
			date = bartDateFormat.parse(dateStringToParse);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		SimpleDateFormat dateFormat =  new SimpleDateFormat(outputFormat);  
		
		return dateFormat.format(date);
	}
	
	public boolean isAdmin(){
		ApplicationProperties app = new ApplicationProperties();
        String adminRoleId = app.getValueByKey("role.admin.id");
        User user = (User) this.getBaseInfo("user");
    	List<Long> roleIds = user.getRoleIds();
    	
    	if(roleIds.contains(Long.parseLong(adminRoleId))){
    		return true;
    	}
    	return false;
	}
}
