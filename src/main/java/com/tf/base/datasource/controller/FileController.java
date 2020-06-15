package com.tf.base.datasource.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tf.base.common.domain.DatasourceCategory;
import com.tf.base.common.domain.DatasourceFile;
import com.tf.base.common.service.CommonService;
import com.tf.base.datasource.domain.FileParams;
import com.tf.base.datasource.domain.FileQueryResult;
import com.tf.base.datasource.service.FileService;

@Controller
public class FileController {
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private FileService fileService;

	@RequestMapping(value = "/datasource/fileList", method = RequestMethod.GET)
	public String queryInit(Model model) {
		List<DatasourceCategory> categories = fileService.queryCategoryList();
		model.addAttribute("categories", categories);
		return "datasource/fileList";
	}
	
	@RequestMapping(value = "/datasource/fileList2", method = RequestMethod.GET)
	public String queryInit2(Model model,String pId,String frameid) {
		List<DatasourceCategory> categories = fileService.queryCategoryList();
		model.addAttribute("categories", categories);
		model.addAttribute("pId", pId);
		model.addAttribute("frameid", frameid);
		return "datasource/fileList2";
	}
	
	@RequestMapping(value = "/datasource/fileList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doQuery(FileParams params) {
		
		Map<String, Object> result = new HashMap<String, Object>();

		int start = (params.getPage() - 1) * params.getRows();
		List<FileQueryResult> rows = new ArrayList<FileQueryResult>();
		
		int total = fileService.queryCount(params);
		if (total == 0) {
			result.put("rows", rows);
			result.put("total", total);
			return result;
		}
		rows = fileService.queryList(params,start);
		
		
		result.put("total", total);
		result.put("rows", rows);
		return result;
	}
	
	
	@RequestMapping(value = "/datasource/saveCategory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveCategory(String categoryName,String categoryId) {
		
		int i = fileService.saveCategory(categoryName,categoryId);
		return i > 0 ? commonService.returnMsg(1, "保存成功") : commonService.returnMsg(0, "保存失败");
	}
	
	@RequestMapping(value = "/datasource/delCategory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delCategory(String categoryId) {
		
		int i = fileService.delCategory(categoryId);
		return i > 0 ? commonService.returnMsg(1, "删除成功") : commonService.returnMsg(0, "删除失败");
	}
	
	@RequestMapping(value = "/datasource/fileAdd", method = RequestMethod.GET)
	public String fileAdd(Model model,String categoryId) {
		model.addAttribute("categoryId", categoryId);
		return "datasource/fileAdd";
	}
	@RequestMapping(value = "/datasource/fileEdit", method = RequestMethod.GET)
	public String fileEdit(Model model,String id) {
		DatasourceFile df = fileService.findById(id);
		model.addAttribute("categoryId", df.getCategoryId());
		model.addAttribute("df", df);
		return "datasource/fileAdd";
	}
	
	@RequestMapping(value = "/datasource/fileSave")
	@ResponseBody
	public Map<String, Object> fileSave(DatasourceFile df,String cover) {
		
		int i = fileService.fileSave(df,cover);
		if(i == -1){
			return commonService.returnMsg(-1, "文件名已存在");
		}
		return i > 0 ? commonService.returnMsg(1, "保存成功") : commonService.returnMsg(0, "保存失败");
	}
	
	@RequestMapping(value = "/datasource/fileLook", method = RequestMethod.GET)
	public String fileLook(Model model,String id) {
		DatasourceFile df = fileService.findById(id);
		model.addAttribute("df", df);
		return "datasource/fileLook";
	}
	
	@RequestMapping(value = "/datasource/fileDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> fileDel(String id) {
		int i = 0;
		try {
			
			i = fileService.fileDel(id);
			if(i > 0 ){
				//delete file
				DatasourceFile df = fileService.findById(id);
				File uploadFile = new File(df.getFilePath());
				uploadFile.delete();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return i > 0 ? commonService.returnMsg(1, "删除成功") : commonService.returnMsg(0, "删除失败");
	}
}
