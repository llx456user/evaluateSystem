package com.tf.base.platform.controller;

import com.tf.base.common.domain.*;

import com.tf.base.common.persistence.StructCategoryMapper;

import com.tf.base.common.persistence.StructDefineMapper;
import com.tf.base.common.service.CommonService;
import com.tf.base.common.utils.JSONUtil;
import com.tf.base.common.utils.StringUtil;
import com.tf.base.platform.service.StructDefineService;
import com.tf.base.platform.domain.StructInfoParams;
import com.tf.base.platform.service.StructInfoService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StructController {
    @Autowired
    private StructCategoryMapper structCategoryMapper;
    @Autowired
    private StructInfoService structInfoService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private StructDefineService structDefineService;
    @Autowired
    private StructDefineMapper structDefineMapper;
    @RequestMapping(value = "/platform/structList", method = RequestMethod.GET)
    public String queryInit(Model model) {

        List<StructCategory> stList=structCategoryMapper.selectWsc();

        model.addAttribute("stList", stList);

        return "platform/structList";
    }
    
    @RequestMapping(value = "/platform/structList2", method = RequestMethod.GET)
    public String queryInit2(Model model,String frameid) {

        List<StructCategory> stList=structCategoryMapper.selectWsc();

        model.addAttribute("stList", stList);
        model.addAttribute("frameid", frameid);
        return "platform/structList2";
    }

    @RequestMapping(value = "/platform/structList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> structDefineQuery(StructInfoParams params) {

        Map<String, Object> result = new HashMap<String, Object>();

        int start = (params.getPage() - 1) * params.getRows();
        List<StructDefine> rows = new ArrayList<StructDefine>();

        int total = structDefineService.queryCount(params);

        if (total == 0) {
            result.put("rows", rows);
            result.put("total", total);
            return result;
        }
        rows = structDefineService.queryList(params,start);

        result.put("total", total);
        result.put("rows", rows);
        return result;
    }

    //新建结构体分类
    @RequestMapping(value = "/platform/saveStructCategory", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveCategory(String categoryId,String categoryName) {
        int i =0;
        if(StringUtil.isEmpty(categoryId)){
            i=structInfoService.saveCategory(categoryName);
        }else{
            i=structInfoService.updateCategory(categoryId, categoryName);
        }

        return i > 0 ? commonService.returnMsg(1, "保存成功") : commonService.returnMsg(0, "保存失败");
    }

     //删除
     @RequestMapping(value = "/platform/deleteStructCategory", method = RequestMethod.POST)
     @ResponseBody
     public Map<String, Object> deleteStructCategory(String categoryId) {

         int i = structInfoService.deleteCategory(categoryId);
         return i > 0 ? commonService.returnMsg(1, "删除成功") : commonService.returnMsg(0, "删除失败");
     }
     //查看
    @RequestMapping(value="/platform/vewStructInfo")
    public String vewModeIfo(Model model,String structInfoId) {
        StructDefine structDefine=structDefineMapper.selectByPrimaryKey(structInfoId);
        Map<String,List<StructParmeter>> parMap=structInfoService.getStructParmeter(structInfoId);
        List<StructParmeter> structList=parMap.get("structList");

        model.addAttribute("structDefine", structDefine);
        model.addAttribute("structList", structList);


        return "platform/viewStructInfo";
    }
    //编辑
    @RequestMapping(value="/platform/toEditStructInfo")
    public String toEdit(Model model, Integer structInfoId) {
        StructDefine structDefine = structDefineMapper.selectByPrimaryKey(structInfoId);

        Map<String,List<StructParmeter>> parMap=structInfoService.getStructParmeter(structInfoId+"");

        List<StructParmeter> structList=parMap.get("structList");
        model.addAttribute("structDefine", structDefine);
        model.addAttribute("structList", structList);
        model.addAttribute("bean", structDefine);
        return "platform/addStructInfo";
    }
//    编辑保存
    @RequestMapping(value="/platform/saveStructInfo")
    @ResponseBody
    public Map<String, Object> saveStructInfo( String reqJson) {
        JSONObject json=JSONUtil.toJSONObject(reqJson);
        int i =structInfoService.saveStructInfo(json);
        if(i==400){
            return commonService.returnMsg(0, "结构体名称不能重复");
        }else if(i==401){
            return commonService.returnMsg(0, "同一结构体参数名称不能重复");
        }else if(i==402){
            return commonService.returnMsg(0, "结构体描述不能为空");
        }

        return i > 0 ? commonService.returnMsg(1, "保存成功") : commonService.returnMsg(0, "保存失败");
    }

    @RequestMapping(value = "/platform/deleteStructInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteStructInfo(String structInfoId) {

        int i = structInfoService.deleteStructInfo(structInfoId);
        return i > 0 ? commonService.returnMsg(1, "删除成功") : commonService.returnMsg(0, "删除失败");
    }


    @RequestMapping(value="/platform/toAddStructInfo")
    public String toAdd(Model model,String categoryId) {
        StructDefine bean=new StructDefine();
        bean.setCategoryId(Integer.parseInt(categoryId));
        model.addAttribute("bean", bean);
        return "platform/addStructInfo";
    }
    @RequestMapping(value="/platform/setStructId")
    @ResponseBody
    public Map setStructId(Model model,String structId) {
        StructDefine structDefine= structDefineMapper.selectByPrimaryKey(structId);
        String structname=structDefine.getStructName();


        Map<String,String> result = new HashMap<String,String>();
        result.put("structname", structname);
        return result;

    }
}
