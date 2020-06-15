package com.tf.base.platform.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tf.base.common.domain.DataFile;
import com.tf.base.common.domain.DatasourceFile;
import com.tf.base.common.domain.ModelCategory;
import com.tf.base.common.domain.ModelInfo;
import com.tf.base.common.domain.ModelParmeter;
import com.tf.base.common.domain.StructDefine;
import com.tf.base.common.persistence.ModelCategoryMapper;
import com.tf.base.common.persistence.ModelInfoMapper;
import com.tf.base.common.persistence.ModelParmeterMapper;
import com.tf.base.common.persistence.StructDefineMapper;
import com.tf.base.common.service.CommonService;
import com.tf.base.common.utils.JSONUtil;
import com.tf.base.common.utils.StringUtil;
import com.tf.base.platform.domain.ModelInfoParams;
import com.tf.base.platform.domain.ModelInfoQueryResult;
import com.tf.base.platform.service.ModelInfoService;

import net.sf.json.JSONObject;

@Controller
public class ModelController {
	
	@Autowired
	private ModelCategoryMapper modelCategoryMapper;
	@Autowired
	private ModelInfoMapper modelInfoMapper;
	@Autowired
	private ModelInfoService modelInfoService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private ModelParmeterMapper modelParmeterMapper;
	@Autowired
	private StructDefineMapper structDefineMapper;

	@RequestMapping(value = "/platform/modelList", method = RequestMethod.GET)
	public String queryInit(Model model) {
		
		//模型类型列表
		List<ModelCategory> mcList=modelCategoryMapper.selectWsc();
				
		model.addAttribute("mcList", mcList);
		
		return "platform/modelList";
	}
	@RequestMapping(value = "/platform/modelList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modelInfoQuery(ModelInfoParams params) {
		
		Map<String, Object> result = new HashMap<String, Object>();

		int start = (params.getPage() - 1) * params.getRows();
		List<ModelInfoQueryResult> rows = new ArrayList<ModelInfoQueryResult>();
		
		int total = modelInfoService.queryCount(params);
		
		if (total == 0) {
			result.put("rows", rows);
			result.put("total", total);
			return result;
		}
		rows = modelInfoService.queryList(params,start);
		
		result.put("total", total);
		result.put("rows", rows);
		return result;
	}
	
	@RequestMapping(value = "/platform/saveModelCategory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveCategory(String categoryId,String categoryName) {
		int i =0;
		if(StringUtil.isEmpty(categoryId)){
			i=modelInfoService.saveCategory(categoryName);
		}else{
			i=modelInfoService.updateCategory(categoryId, categoryName);
		}
		 
		return i > 0 ? commonService.returnMsg(1, "保存成功") : commonService.returnMsg(0, "保存失败");
	}
	
	
	@RequestMapping(value = "/platform/deleteModelCategory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteModelCategory(String categoryId) {
		
		int i = modelInfoService.deleteCategory(categoryId);
		return i > 0 ? commonService.returnMsg(1, "删除成功") : commonService.returnMsg(0, "删除失败");
	}
	
	@RequestMapping(value="/platform/toAddModelInfo")
	public String toAdd(Model model,String categoryId) {
		ModelInfo bean=new ModelInfo();
		bean.setModelCategoryid(Integer.parseInt(categoryId));
		model.addAttribute("bean", bean);
		return "platform/addModelInfo";
	}

	@RequestMapping(value="/platform/toEditModelInfo")
	public String toEdit(Model model, Integer modelInfoId) {
		ModelInfo modelInfo = modelInfoMapper.selectByPrimaryKey(modelInfoId);
		
		Map<String,List<ModelParmeter>> parMap=modelInfoService.getModelParmeter(modelInfoId+"");
		List<ModelParmeter> inparList=parMap.get("inParList");
		List<ModelParmeter> outparList=parMap.get("outParList");
//		List<ModelParmeter> structList=parMap.get("structList");
		List<StructDefine> structList = structDefineMapper.queryListAll();
		
		model.addAttribute("modelInfo", modelInfo);
		model.addAttribute("inparList", inparList);
		model.addAttribute("outparList", outparList);
		model.addAttribute("structList", structList);
		model.addAttribute("bean", modelInfo);
		return "platform/addModelInfo";
	}
	
	@RequestMapping(value = "/platform/saveModelInfo")
	@ResponseBody
	public Map<String, Object> saveModelInfo( String reqJson) {
		JSONObject json=JSONUtil.toJSONObject(reqJson);
		int i =modelInfoService.saveModelInfo(json);
		if(i==400){
			return commonService.returnMsg(0, "模型名称不能重复");
		}else if(i==401){
			return commonService.returnMsg(0, "同一模型内参数名称不能重复");
		}else if(i==402){
            return commonService.returnMsg(0, "模型描述不能为空");
        }else if(i==403){
			return commonService.returnMsg(0, "模型参数类型不能为空");
		}
		
		return i > 0 ? commonService.returnMsg(1, "保存成功") : commonService.returnMsg(0, "保存失败");
	} 
	
	/**
	 * 生成模型框架，并下载
	 * @param id
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value = "/platform/makeModelKj")
	@ResponseBody
	public void makeModelKj(String id, HttpServletRequest request ,HttpServletResponse response) throws IOException{
		String templatePath = request.getSession().getServletContext().getRealPath("\\TemplateProject\\");
		String [] ret=modelInfoService.makeModelKj(id,templatePath);
	
		Map<String, Object> resMap = new HashMap<String, Object>();
		if(ret==null||ret.length<2){
		}else{
			String fileName=ret[0];
			String filePath=ret[1];
			response.setContentType("application/force-download");
			response.setHeader("Location",fileName);
			//filename应该是编码后的(utf-8)
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName); 
			OutputStream outputStream = response.getOutputStream();
			InputStream inputStream = new FileInputStream(filePath);
			byte[] buffer = new byte[1024];
			int i = -1;
			while ((i = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, i);
			}
			outputStream.flush();
			outputStream.close();
			inputStream.close();
			outputStream = null;

		}
	
	}
	
	@RequestMapping(value = "/platform/deleteModelInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteModelInfo(String modelInfoId) {
		
		int i = modelInfoService.deleteModelInfo(modelInfoId);
		return i > 0 ? commonService.returnMsg(1, "删除成功") : commonService.returnMsg(0, "删除失败");
	}
	
	@RequestMapping(value="/platform/vewModeIfo")
	public String vewModeIfo(Model model,String modelInfoId) {
		ModelInfo modelInfo=modelInfoMapper.selectByPrimaryKey(modelInfoId);
		Map<String,List<ModelParmeter>> parMap=modelInfoService.getModelParmeter(modelInfoId);
		List<ModelParmeter> inparList=parMap.get("inParList");
		List<ModelParmeter> outparList=parMap.get("outParList");
		
		model.addAttribute("modelInfo", modelInfo);
		model.addAttribute("inparList", inparList);
		model.addAttribute("outparList", outparList);
		
		return "platform/viewModelInfo";
	}
	/**
	 * 上传dll后更新dll信息到模型表中
	 * @param modelInfoId
	 * @param dllPath
	 * @return
	 */
	@RequestMapping(value = "/platform/updateDll", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateDll(String modelInfoId,String dllPath) {
		
		int i = modelInfoService.updateDll(modelInfoId, dllPath);
		return i > 0 ? commonService.returnMsg(1, "上传成功") : commonService.returnMsg(0, "上传失败");
	}
	
	 private static void outputJSONResult(Object result, HttpServletResponse response) {

	        JSONObject jsonObject = JSONObject.fromObject(result);
	        try {
	            response.setHeader("ContentType", "text/json");
	            response.setCharacterEncoding("utf-8");
	            PrintWriter pw = response.getWriter();
	            pw.write(jsonObject.toString());
	            pw.flush();
	            pw.close();

	        } catch (IOException e) {
	        	
	        	e.printStackTrace();
	        }
	    }
	@RequestMapping(value = "/platform/autoSaveModelInfo")
	@ResponseBody
	public Map<String, Object> autoSaveModelInfo( String reqJson) {
		JSONObject json=JSONUtil.toJSONObject(reqJson);
		int i =modelInfoService.saveModelInfo(json);
		if(i==400){
			return commonService.returnMsg(0, "模型名称不能重复");
		}else if(i==401){
			return commonService.returnMsg(0, "同一模型内参数名称不能重复");
		}else if(i==402){
			return commonService.returnMsg(0, "模型描述不能为空");
		}else if(i==403){
			return commonService.returnMsg(0, "模型参数类型不能为空");
		}

		return i > 0 ? commonService.returnMsg(1, "保存成功"+i) : commonService.returnMsg(0, "保存失败");
	}
	@RequestMapping("/platform/setCopyModel")
	public String setCopyModel(Model model,String categoryId,String modelframeid){
		model.addAttribute("modelframeid", modelframeid);
		model.addAttribute("categoryId", categoryId);
		return "platform/copyModel";
	}
	@ResponseBody
	@RequestMapping("/platform/modelcategoryList")
	public Object categoryList() {
		return modelInfoService.categoryList();
	}


	@RequestMapping("/platform/setmodelValue")
	public Object setmodelValue(Model model,String modelInfoId,String categoryId) {
		ModelInfo bean=new ModelInfo();
		bean.setModelCategoryid(Integer.parseInt(categoryId));
		model.addAttribute("bean", bean);


//		ModelInfo modelInfo = modelInfoMapper.selectByPrimaryKey(modelInfoId);

		Map<String,List<ModelParmeter>> parMap=modelInfoService.getModelParmeter(modelInfoId+"");
		List<ModelParmeter> inparList=parMap.get("inParList");
		List<ModelParmeter> outparList=parMap.get("outParList");

		List<StructDefine> structList = structDefineMapper.queryListAll();

//		model.addAttribute("modelInfo", modelInfo);
		model.addAttribute("inparList", inparList);
		model.addAttribute("outparList", outparList);
		model.addAttribute("structList", structList);
//		model.addAttribute("bean", modelInfo);
		return "platform/copyModelInfo";





	}
}
