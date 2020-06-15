package com.tf.base.platform.controller;

import com.tf.base.common.domain.*;
import com.tf.base.common.persistence.TempletInfoMapper;
import com.tf.base.common.service.BaseService;
import com.tf.base.platform.domain.TempletInfoParams;
import com.tf.base.platform.service.IndexService;
import com.tf.base.platform.service.ModelInfoService;
import com.tf.base.platform.service.TempletService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/platform/")
public class TempletController {

    @Autowired
    private BaseService baseService;
    @Autowired
    private ModelInfoService modelInfoService;

    @Autowired
    private IndexService indexService;
    @Autowired
    private TempletService templetService;
    @Autowired
    private TempletInfoMapper templetInfoMapper;
    

    @RequestMapping(value = "templetList", method = RequestMethod.GET)
    public String indexList(Model model) {
        return "platform/templetList";
    }
    

    @ResponseBody
    @RequestMapping(value = "templetList", method = RequestMethod.POST)
    public Map<String, Object> indexList(TempletInfoParams params) {
        Map<String, Object> result = new HashMap<String, Object>();
        int total = templetService.queryCount(params);
        result.put("total", total);
        if (total == 0) {
            result.put("rows", new ArrayList<TempletInfo>());
            return result;
        } else {
            int start = (params.getPage() - 1) * params.getRows();
            List<TempletInfo> rows = templetService.queryList(params, start);
            result.put("rows", rows);
            return result;
        }
    }

    @ResponseBody
    @RequestMapping(value = "templetCategoryList", method = RequestMethod.POST)
    public List<TempletCategory> categoryList() {
        return templetService.categoryList();
    }

    /**
     * 分类
     * @param category
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "templetCategoryCreate", method = RequestMethod.POST)
    public String categoryCreate(TempletCategory category) {
        category.setIsdelete(0);
        category.setCreateTime(new Date());
        category.setCreateUid(Integer.parseInt(baseService.getUserId()));
        templetService.categoryCreate(category);
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "templetCategoryUpdate", method = RequestMethod.POST)
    public String categoryUpdate(TempletCategory category) {
        category.setUpdateTime(new Date());
        category.setUpdateUid(Integer.parseInt(baseService.getUserId()));
        templetService.categoryUpdate(category);
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "templetCategoryRemove", method = RequestMethod.POST)
    public String categoryRemove(Integer categoryId) {
    	templetService.categoryRemove(categoryId);
        return "success";
    }
    
    
    
    @RequestMapping(value = "templetAdd", method = RequestMethod.GET)
    public String indexAdd(Model model, String categoryId) {
        model.addAttribute("categoryId", categoryId);
       //模型
        List<ModelCategory> mcList = modelInfoService.findModel();
        model.addAttribute("mcList", mcList);
        
        return "platform/templetAdd";
    }
    
    @RequestMapping(value = "templetEdit", method = RequestMethod.GET)
    public String templetEdit(Model model, String id) {
    	model.addAttribute("id", id);
    	//模型
    	List<ModelCategory> mcList = modelInfoService.findModel();
    	model.addAttribute("mcList", mcList);
    	TempletInfo info  = templetInfoMapper.selectByPrimaryKey(id);
    	model.addAttribute("info", info);
    	model.addAttribute("categoryId", info.getTempletCategoryid());
    	//TODO
    	
    	return "platform/templetAdd";
    }
    
    @RequestMapping(value = "templetView", method = RequestMethod.GET)
    public String templetView(Model model, String id) {
    	model.addAttribute("id", id);
    	//模型
    	List<ModelCategory> mcList = modelInfoService.findModel();
    	model.addAttribute("mcList", mcList);
    	TempletInfo info  = templetInfoMapper.selectByPrimaryKey(id);
    	model.addAttribute("info", info);
    	//TODO
    	
    	return "platform/templetView";
    }

    //模板保存
    @RequestMapping(value = "templetSave", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> templetSave(TempletInfoParams params) {

    	Map<String, Object> result = new HashMap<String, Object>();
       TempletInfo templetInfo = new TempletInfo();

       templetInfo.setTempletName(params.getTempletName());
       templetInfo.setTempletCategoryid(Integer.parseInt(params.getCategoryId()));
       
       Date now =new Date();
       if(params.getId() != null && !params.getId().equals("")){
    	   templetInfo.setId(Integer.parseInt(params.getId()));
           templetInfo.setTempletContent(params.getTempletContent());
           templetInfo.setUpdateTime(now);
           templetInfo.setUpdateUid(Integer.parseInt(baseService.getUserId()));
           templetService.templetUpdate(templetInfo);
           result.put("status", 1);
           result.put("msg", "修改成功");
           return result;
       }
       int count = templetInfoMapper.selectCount(templetInfo);
        if(count > 0){
            result.put("status", 0);
            result.put("msg", "模板名称已经存在");
            return result;
        }
        templetInfo.setTempletContent(params.getTempletContent());
        templetInfo.setCreateTime(now);
        templetInfo.setCreateUid(Integer.parseInt(baseService.getUserId()));
        templetInfo.setUpdateTime(now);
        templetInfo.setUpdateUid(Integer.parseInt(baseService.getUserId()));
        templetInfo.setIsdelete(0);
        
        int i = templetService.templetSave(templetInfo);
        result.put("status", 1);
        result.put("msg", "保存成功");
        return result;
    }
    
    @RequestMapping(value = "templetDel", method = RequestMethod.POST)
    @ResponseBody
    public Map templetDel(String id) {
    	Map<String, Object> result = new HashMap<String, Object>();
    	TempletInfo t = new TempletInfo();
    	t.setId(Integer.parseInt(id));
    	t.setIsdelete(1);
    	int i = templetInfoMapper.updateByPrimaryKeySelective(t);
    	if(i > 0){
    		result.put("status", 1);
            result.put("msg", "删除成功");
    	}else{
    		result.put("status", 0);
            result.put("msg", "删除失败");
    	}
    	
    	return result;
    }
    @RequestMapping(value = "templetAllListByCateId", method = RequestMethod.POST)
    @ResponseBody
    public List<TempletInfo> templetAllListByCateId(String categoryId) {
    	Map<String, Object> result = new HashMap<String, Object>();
    	TempletInfo t = new TempletInfo();
    	t.setIsdelete(0);
    	t.setTempletCategoryid(Integer.parseInt(categoryId));
    	List<TempletInfo> templetInfos = templetInfoMapper.select(t);
    	
    	return templetInfos;
    }


}
