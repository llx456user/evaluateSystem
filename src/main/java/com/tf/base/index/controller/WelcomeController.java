package com.tf.base.index.controller;

import java.util.List;

import com.tf.base.index.domain.RecentProjectInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tf.base.common.service.BaseService;
import com.tf.base.index.domain.CountInfo;
import com.tf.base.index.domain.RecentAssessInfo;
import com.tf.base.index.service.WelcomeService;
import com.tf.permission.client.entity.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class WelcomeController {
	
	@Autowired
	private WelcomeService welcomeService;
	
	@Autowired
	private BaseService baseService;

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcom() {
		
		return "index/welcome";
	}
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello(Model model) {
		
		String userid = baseService.isAdmin()? "" :baseService.getUserId();
		
		//统计数
		
		List<String> counts = welcomeService.categoryCount(userid);
		//近期评估情况
		
		List<RecentProjectInfo> recentAssessInfos = welcomeService.recentAssessCount(userid);
		
		model.addAttribute("ProjectCounts", counts.get(0));
		model.addAttribute("ModelCounts", counts.get(1));
		model.addAttribute("IndexCounts", counts.get(2));
		model.addAttribute("recentAssessInfos", recentAssessInfos);
		return "index/hello";
	}
	
	@RequestMapping(value = "/count/project")
    @ResponseBody
    public JSONObject countProject(){
		String userid = baseService.isAdmin()? "" :baseService.getUserId();
        JSONObject jo=new JSONObject();

        List<String> yearsList =welcomeService.recentYearList(12);
        
        String[] array = new String[yearsList.size()];
        String[] invNames =yearsList.toArray(array);
        Integer[] loanCounts = new Integer[invNames.length];
        
        List<CountInfo> counts =welcomeService.countProject(userid,yearsList.get(0),yearsList.get(yearsList.size() - 1));
		for (int i = 0; i < invNames.length; i++) {
        	boolean hasCount = false;
        	for (CountInfo countInfo : counts) {
    			if(invNames[i].equals(countInfo.getDate())){
    				loanCounts[i] = Integer.parseInt(countInfo.getC());
    				hasCount = true;
    			}
    		}
        	
        	if(!hasCount)
        		loanCounts[i] = 0;
		}
        

        jo.put("invNames",invNames);
        jo.put("loanCounts",loanCounts);
        return jo;
    }
    @RequestMapping(value = "/count/model")
    @ResponseBody
    public JSONObject countModel(){
    	String userid = baseService.isAdmin()? "" :baseService.getUserId();
    	
        JSONObject jo=new JSONObject();

        List<String> yearsList =welcomeService.recentYearList(12);
        
        String[] array = new String[yearsList.size()];
        String[] invNames =yearsList.toArray(array);
        Integer[] loanCounts = new Integer[invNames.length];

        List<CountInfo> counts =welcomeService.countModel(userid,yearsList.get(0),yearsList.get(yearsList.size() - 1));
        for (int i = 0; i < invNames.length; i++) {
        	boolean hasCount = false;
        	for (CountInfo countInfo : counts) {
    			if(invNames[i].equals(countInfo.getDate())){
    				loanCounts[i] = Integer.parseInt(countInfo.getC());
    				hasCount = true;
    			}
    		}
        	if(!hasCount)
        		loanCounts[i] = 0;
		}
        
        
        jo.put("invNames",invNames);
        jo.put("loanCounts",loanCounts);
        return jo;
    }
    
    @RequestMapping(value = "/count/index")
    @ResponseBody
    public JSONObject countIndex(){
    	String userid = baseService.isAdmin()? "" :baseService.getUserId();
        JSONObject jo=new JSONObject();

        List<String> yearsList =welcomeService.recentYearList(12);
       
        String[] array = new String[yearsList.size()];
        String[] invNames =yearsList.toArray(array);
        Integer[] loanCounts = new Integer[invNames.length];
        
        List<CountInfo> counts =welcomeService.countIndex(userid,yearsList.get(0),yearsList.get(yearsList.size() - 1));
        
        for (int i = 0; i < invNames.length; i++) {
        	boolean hasCount = false;
        	for (CountInfo countInfo : counts) {
    			if(invNames[i].equals(countInfo.getDate())){
    				loanCounts[i] = Integer.parseInt(countInfo.getC());
    				hasCount = true;
    			}
    		}
        	if(!hasCount)
        		loanCounts[i] = 0;
		}
        

        jo.put("invNames",invNames);
        jo.put("loanCounts",loanCounts);
        return jo;
    }
}
