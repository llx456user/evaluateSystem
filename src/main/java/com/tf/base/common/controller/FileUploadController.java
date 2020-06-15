package com.tf.base.common.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.tf.base.common.constants.CommonConstants;
import com.tf.base.common.constants.CommonConstants.LOG_OPERATION_TYPE;
import com.tf.base.common.domain.DataFile;
import com.tf.base.common.persistence.DataFileMapper;
import com.tf.base.common.service.BaseService;
import com.tf.base.common.utils.ApplicationProperties;

import net.sf.json.JSONObject;

@Controller
public class FileUploadController {

	
	@Autowired
	private BaseService baseService;
	@Autowired
	private DataFileMapper dataFileMapper;
	
	@RequestMapping(value = {"*/fileUpload","*/*/fileUpload"})
    public void onSubmit(String modelTbId, String model, MultipartHttpServletRequest multipartRequest, HttpServletResponse response) {
		
		// 取得文件列表
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        
        Date now = new Date();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        
        // 设置文件路径
       // String ctxPath = "/data/order/change/pic/" + dateFormat.format(now) + "/";
        ApplicationProperties app = new ApplicationProperties();
        String filePath = app.getValueByKey("file.upload.path") + dateFormat.format(now) + File.separator;

        File folder = new File(filePath);

		if (!folder.exists()) {
			folder.mkdirs();
		}
        List<String> urls = new ArrayList<String>();
        
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
        	
             // 上传文件名
             MultipartFile mf = entity.getValue();
             String prefix = mf.getOriginalFilename().substring(mf.getOriginalFilename().lastIndexOf(".")+1);
             String fileName = modelTbId + "_" + now.getTime() + "." + prefix;
             File uploadFile = new File(filePath + fileName);
             try {
				FileCopyUtils.copy(mf.getBytes(), uploadFile);
				DataFile f = new DataFile();
				f.setFilename(fileName);
				f.setUploadfilename(mf.getOriginalFilename());
				f.setFilesize(String.valueOf(mf.getSize()));
				f.setModel(model);
				f.setModelTbId(modelTbId);
				f.setPathname(filePath);
				f.setStatus(CommonConstants.STATUS_FLAG_VALID);
				f.setType("1");
				f.setUploader(baseService.getShowName());
				f.setUploadTime(new Date());
				dataFileMapper.insertSelective(f);
				
				urls.add(filePath + fileName);
			 } catch (IOException e) {
				
				e.printStackTrace();
			 }
        }
        
        Map<String, Object> resMap = new HashMap<String, Object>();

        resMap.put("status", "1");
        resMap.put("url" , urls);

        outputJSONResult(resMap, response);
	}
	
	@RequestMapping(value = {"*/upload","*/*/upload"})
	@ResponseBody
	public String fileUpload(@RequestParam("file") CommonsMultipartFile  file,HttpServletRequest request){
		String url = "";
		//if(!file.isEmpty()) {
	        Date now = new Date();
	        
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	        
	        // 设置文件路径
	       // String ctxPath = "/data/order/change/pic/" + dateFormat.format(now) + "/";
	        ApplicationProperties app = new ApplicationProperties();
	        String filePath = app.getValueByKey("file.upload.path") + dateFormat.format(now) + File.separator;
	
	        File folder = new File(filePath);
	
			if (!folder.exists()) {
				folder.mkdirs();
			}
             // 上传文件名
             String prefix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
             String fileName = now.getTime() + "." + prefix;
             File uploadFile = new File(filePath + fileName);
             try {
            	 file.transferTo(uploadFile);
				url = (filePath + fileName);
			 } catch (IOException e) {
				
				e.printStackTrace();
			 }
      //  }
        
        return url;
	}


	//评估数据zui上传
	@RequestMapping(value = {"*/zuiupload","*/*/zuiupload"})
	@ResponseBody
	public String zuifileUpload(@RequestParam("file") CommonsMultipartFile  file,HttpServletRequest request){
		String url = "";
		//if(!file.isEmpty()) {
		Date now = new Date();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		// 设置文件路径
		// String ctxPath = "/data/order/change/pic/" + dateFormat.format(now) + "/";
		ApplicationProperties app = new ApplicationProperties();
		String filePath = app.getValueByKey("file.upload.path") + dateFormat.format(now) + File.separator;

		File folder = new File(filePath);

		if (!folder.exists()) {
			folder.mkdirs();
		}
		// 上传文件名
		String prefix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
		String fileName = now.getTime() + "." + prefix;
		File uploadFile = new File(filePath + fileName);
		try {
			file.transferTo(uploadFile);
			url = (filePath + fileName);
		} catch (IOException e) {

			e.printStackTrace();
		}
		//  }

		return url;
	}
	
	@RequestMapping(value = {"*/downFile","*/*/downFile"})
	public void onDown(String id, HttpServletResponse response) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		if(StringUtils.isEmpty(id)){
			resMap.put("status", "0");
	        resMap.put("msg" , "附件ID为NULL！");

		}else{
			
			DataFile f = dataFileMapper.selectByPrimaryKey(id);
		
			File file = new File(f.getPathname(), f.getFilename());
            if (file.exists()) {
	                response.setContentType("application/force-download");// 设置强制下载不打开
	                response.addHeader("Content-Disposition",
	                        "attachment;fileName=" + f.getUploadfilename());// 设置文件名
	                byte[] buffer = new byte[1024];
	                FileInputStream fis = null;
	                BufferedInputStream bis = null;
	                try {
	                    fis = new FileInputStream(file);
	                    bis = new BufferedInputStream(fis);
	                    OutputStream os = response.getOutputStream();
	                    int i = bis.read(buffer);
	                    while (i != -1) {
	                        os.write(buffer, 0, i);
	                        i = bis.read(buffer);
	                    }
	                } catch (Exception e) {
	                    // TODO: handle exception
	                    e.printStackTrace();
	                } finally {
	                    if (bis != null) {
	                        try {
	                            bis.close();
	                        } catch (IOException e) {
	                            // TODO Auto-generated catch block
	                            e.printStackTrace();
	                        }
	                    }
	                    if (fis != null) {
	                        try {
	                            fis.close();
	                        } catch (IOException e) {
	                            // TODO Auto-generated catch block
	                            e.printStackTrace();
	                        }
	                    }
	                }
	                
				resMap.put("status", "1");
		        resMap.put("msg" , "下载附件成功！");
		        
		        
			}
		}
		
		 outputJSONResult(resMap, response);
	}
	
	@RequestMapping(value = {"*/deleteFile","*/*/deleteFile"})
	public void onDelete(String id, HttpServletResponse response) {
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		if(StringUtils.isEmpty(id)){
			resMap.put("status", "0");
	        resMap.put("msg" , "附件ID为NULL！");

		}else{
			
			DataFile oldF = dataFileMapper.selectByPrimaryKey(id);
			DataFile f = new DataFile();
			f.setId(Integer.parseInt(id));
			f.setStatus(CommonConstants.STATUS_FLAG_INVALID);
			dataFileMapper.updateByPrimaryKeySelective(f);
			
			resMap.put("status", "1");
	        resMap.put("msg" , "删除附件成功！");
	        
		}
		

        outputJSONResult(resMap, response);
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
    
    
	
}
