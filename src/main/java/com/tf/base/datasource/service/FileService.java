package com.tf.base.datasource.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tf.base.common.domain.DatasourceCategory;
import com.tf.base.common.domain.DatasourceFile;
import com.tf.base.common.domain.DatasourceFileTitle;
import com.tf.base.common.persistence.DatasourceCategoryMapper;
import com.tf.base.common.persistence.DatasourceFileMapper;
import com.tf.base.common.persistence.DatasourceFileTitleMapper;
import com.tf.base.common.service.BaseService;
import com.tf.base.common.utils.ApplicationProperties;
import com.tf.base.common.utils.DateUtil;
import com.tf.base.common.utils.FileReadUtils;
import com.tf.base.common.utils.ReadExcelUtil;
import com.tf.base.datasource.domain.FileParams;
import com.tf.base.datasource.domain.FileQueryResult;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class FileService {

	//评估结果数据类型
	public static  int  FileCategoryType_Assess = -1;
	//数据赛选结果数据类型
	public static  int   FileCategoryType_Data = -2;

	
	private static List<String> sufs_excel = Arrays.asList(".xls",".xlsx");
	private static List<String> sufs_csv = Arrays.asList(".csv");
	private static List<String> sufs_txt = Arrays.asList(".txt");

	@Autowired
	private BaseService baseService;
	
	@Autowired
	private DatasourceCategoryMapper datasourceCategoryMapper;
	@Autowired
	private DatasourceFileMapper datasourceFileMapper;
	@Autowired
	private DatasourceFileTitleMapper datasourceFileTitleMapper;

	public int saveCategory(String categoryName, String categoryId){
		
		Integer userid = Integer.parseInt(baseService.getUserId());
		Date now = new Date();
		DatasourceCategory dc = new DatasourceCategory();
		if(null == categoryId || "".equals(categoryId)){
			
			dc.setCategoryName(categoryName);
			dc.setCreateTime(now);
			dc.setCreateUid(userid);
			dc.setIsdelete(0);
			dc.setUpdateTime(now);
			dc.setUpdateUid(userid);
			return datasourceCategoryMapper.insertSelective(dc);
		}else{
			dc.setCategoryName(categoryName);
			dc.setId(Integer.parseInt(categoryId));
			dc.setUpdateTime(now);
			dc.setUpdateUid(userid);
			return datasourceCategoryMapper.updateByPrimaryKeySelective(dc);
		}
	}

	public int queryCount(FileParams params) {
		
		return datasourceFileMapper.queryCount(params);
	}

	/**
	 *
	 * @param cid
	 * @return
	 */
	public List<DatasourceFile> queryFileListByCategoryID(int cid){
		return  datasourceFileMapper.queryFileList(cid);
	}

	public List<FileQueryResult> queryList(FileParams params,int start) {
		
		List<FileQueryResult> fileQueryResults = datasourceFileMapper.queryList(params,start);
		for (FileQueryResult fileQueryResult : fileQueryResults) {
			fileQueryResult.setUpdateTimeStr(DateUtil.TimeToString(fileQueryResult.getUpdateTime()));
		}
		return fileQueryResults;
	}

	/**
	 * 根据ID获取文件内容
	 * @return
	 */
	public List<DatasourceCategory> queryCategoryList() {
	
		Example example = new Example(DatasourceCategory.class);
		example.createCriteria().andEqualTo("isdelete");
		example.setOrderByClause(" id desc ");
		return datasourceCategoryMapper.selectByExample(example);
	}

	public DatasourceFile findById(String id) {
		DatasourceFile df = datasourceFileMapper.selectByPrimaryKey(id);
		return df;
	}

	public int fileSave(DatasourceFile df, String cover) {
		Integer userid = Integer.parseInt(baseService.getUserId());
		Date now = new Date();
		try {
			//新增
			if(df.getId() == null || "".equals(df.getId().toString())){
				DatasourceFile param = new DatasourceFile();
				param.setFileName(df.getFileName());
				param.setFileVersion(df.getFileVersion());
				param.setIsdelete(0);
				if(cover==null || !"1".equals(cover)){
					int i = datasourceFileMapper.selectCount(param);
					if(i > 0){
						return -1;
					}
				}else{
					DatasourceFile tmp = datasourceFileMapper.selectOne(param);
					datasourceFileMapper.delete(param);
					DatasourceFileTitle dft = new DatasourceFileTitle();
					dft.setFileId(tmp.getId());
					datasourceFileTitleMapper.delete(dft);
				}
				
				df.setId(null);
				df.setCreateTime(now);
				df.setUpdateTime(now);
				df.setUpdateUid(userid);
				df.setIsdelete(0);
				df.setFilePath(df.getFilePath());
				datasourceFileMapper.insertSelective(df);

				
				updateDataFileTitle(now,userid,df);
				

				return 1;
			}else{//修改
				df.setUpdateTime(now);
				df.setUpdateUid(userid);
				df.setFilePath(df.getFilePath());
				datasourceFileMapper.updateByPrimaryKeySelective(df);

				
				Example example = new Example(DatasourceFileTitle.class);
				Criteria criteria = example.createCriteria();
				criteria.andEqualTo("fileId", df.getId());
				DatasourceFileTitle dft = new DatasourceFileTitle();
				dft.setUpdateTime(now);
				dft.setUpdateUid(userid);
				dft.setIsdelete(1);
				datasourceFileTitleMapper.updateByExampleSelective(dft, example);
				
				updateDataFileTitle(now,userid,df);

				return 1;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return 0;
	}
	public int updateDataFileTitle(Date now, Integer userid, DatasourceFile df){
		String suf = df.getFilePath().substring(df.getFilePath().lastIndexOf("."));
		List<String> rows = null;
		boolean isTxt = false;
    	if(sufs_excel.contains(suf)){
    		try {
    			rows = ReadExcelUtil.readExcel(df.getFilePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}else if(sufs_csv.contains(suf)){
    		rows = FileReadUtils.readFileByLines(df.getFilePath());
    	}else{
    		isTxt = true;
    		rows = FileReadUtils.readFileByLines(df.getFilePath());
    	}
		if(rows == null || rows.size()<=0){
			return 0;
		}
		String row = rows.get(0);
		String[] cols = isTxt ? row.split("\t"):row.split(",");
		for (int i = 0; i < cols.length; i++) {
			
			DatasourceFileTitle dft = new DatasourceFileTitle();
			dft.setFileId(df.getId());
			dft.setCreateTime(now);
			dft.setCreateUid(userid);
			dft.setColumnName(isNumeric(cols[i])?(i+""):cols[i]);
			dft.setColumnNum(i);
			dft.setIsdelete(0);
			dft.setUpdateTime(now);
			dft.setUpdateUid(userid);
			dft.setType(1);
			datasourceFileTitleMapper.insertSelective(dft);
		}
		return 1;
	}
	
	public List<String> fileUpload(MultipartFile file){
		List<String> urls = new ArrayList<String>();
		if(!file.isEmpty()) {
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
				urls.add(filePath + fileName);
			 } catch (IOException e) {
				
				e.printStackTrace();
			 }
        }
        
        return urls;
	}

	public int fileDel(String id) {
		DatasourceFile df = new DatasourceFile();
		df.setId(Integer.parseInt(id));
		df.setIsdelete(1);
		return datasourceFileMapper.updateByPrimaryKeySelective(df);
	}

	public int findCount(DatasourceFile df) {
		return datasourceFileMapper.selectCount(df);
	}

	public int delCategory(String categoryId) {
		DatasourceFile df = new DatasourceFile();
		df.setCategoryId(Integer.parseInt(categoryId));
		int i = 0;
		try {
			
			datasourceFileMapper.delete(df);
			datasourceCategoryMapper.deleteByPrimaryKey(categoryId);
			
			List<DatasourceFile> dfs = datasourceFileMapper.select(df);
			for (DatasourceFile datasourceFile : dfs) {
				File uploadFile = new File(datasourceFile.getFilePath());
				uploadFile.delete();
			}
			i++;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return i;
	}
	public static boolean isNumeric(String str) {
        // 该正则表达式可以匹配所有的数字 包括负数
        Pattern pattern = Pattern.compile("-?[0-9]+(\\.[0-9]+)?");
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }

        Matcher isNum = pattern.matcher(bigStr); // matcher是全匹配
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
	
}
