package com.tf.base.common.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

import com.tf.base.common.constants.CommonConstants;

/**
 * 大数据导出到Excel工具类
 *
 */
public class BigExcelUtil {
	
	
	
	/**
	 * 將表頭和对应子段通过MAP传递过来
	 * 导出excel到HttpServletResponse中的通用方法
	 * @param fileName       文件名称
	 * @param header_Field   表头和对应字段map 格式  header_Field.put("渠道订单号","channelOrderNo");
	 * @param dataset
	 * @param response
	 * @param field_pattern  根据具体子段传递对应的格式  如field_pattern.put("createtime","yyyy-MM-dd hh24:mm:ss");
	 */
	public static <T> void exportExcel(String fileName, Map<String,String> header_Field, Collection<T> dataset,
			                          HttpServletResponse response, Map<String,String> field_pattern) {

		setResponseInfo(fileName, response);
		try {
			createBigExcel(header_Field, field_pattern, dataset,response.getOutputStream());
		} catch (Exception e) {
			System.out.println("导出Excel发生异常:"+ e.getMessage());
		}

	}
	
	/**
	 * 將表頭和对应子段通过数组分别传递过来
	 * 导出excel到HttpServletResponse中的通用方法
	 * @param fileName
	 * @param headers
	 * @param mappingFields
	 * @param dataset
	 * @param response
	 * @param field_pattern  根据具体子段传递对应的格式  如field_pattern.put("createtime","yyyy-MM-dd hh24:mm:ss");
	 * @param isWrap 
	 */
	public static <T> void exportExcel(String fileName, String[] headers,String[] mappingFields, Collection<T> dataset,
			                          HttpServletResponse response, Map<String,String> field_pattern, boolean isWrap) {

		
		try {
			setResponseInfo(fileName, response);
			createBigExcel(headers, mappingFields, field_pattern, dataset,response.getOutputStream(),isWrap);
		} catch (IOException e) {
			System.out.println("导出Excel发生异常:"+ e.getMessage());
		}
	}
	
	public static <T> void exportExcel(String fileName, String[] headers,String[] mappingFields, Collection<T> dataset,
            HttpServletResponse response, Map<String,String> field_pattern){
		exportExcel(fileName, headers, mappingFields, dataset, response, field_pattern, false);
	}

	/**
	 * 
	 * @param <T>
	 * @param field_pattern 相应字段对应的样式  主要应用于日前格式
	 * @param header_Field  存入的数据为数值  注：map必须为有序map 如：LinkedHashMap
	 */
	public static <T> void createBigExcel(Map<String,String> header_Field,Map<String,String> field_pattern,
			                              Collection<T> dataset, OutputStream out){
		
		String[] headers    = (String[])header_Field.keySet().toArray(new String[0]);
		String[] fieldNames = (String[])header_Field.values().toArray(new String[0]);
		createBigExcel(headers,fieldNames,field_pattern,dataset,out,false);
	}
	/**
	 * 
	 * @param <T>
	 * @param headers
	 * @param fieldNames
	 * @param field_pattern
	 * @param dataset
	 * @param out
	 * @param isWrap 
	 */
    public static <T> void createBigExcel(String[] headers,String[] fieldNames,Map<String,String> field_pattern,
    		                          Collection<T> dataset, OutputStream out, boolean isWrap){
    	 // keep 100 rows in memory, exceeding rows will be flushed to disk
		SXSSFWorkbook workbook = new SXSSFWorkbook(100);
		SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet("sheet1");
		try {
			createExcelHeader(headers, sheet, workbook);
			createExcelRow(dataset, fieldNames, sheet, workbook, field_pattern,isWrap);
			workbook.write(out);
		} catch (IOException e) {

		} finally {
			try {
				out.close();
			} catch (IOException e) {
			}
			// dispose of temporary files backing this workbook on disk
			workbook.dispose();
		}
	}
    /**
     * 创建Excel导出表头
     * 可设置表头宽度  表头格式为 表头:20 默认宽度为12
     * @param headers
     * @param sheet
     */
    private static void createExcelHeader(String[] headers,SXSSFSheet sheet,SXSSFWorkbook workbook){
    	 
    	 XSSFCellStyle head_Style = initHeadStyle(workbook);
    	 SXSSFRow row = (SXSSFRow) sheet.createRow(0);
 		 for (int i = 0; i < headers.length; i++) {
 			String[] headwidth = headers[i].split(":");
 			SXSSFCell cell = (SXSSFCell) row.createCell(i);
 			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
 			cell.setCellValue(headwidth[0]);
 			cell.setCellStyle(head_Style);
            int width = 12  ; //默认宽度
            //通过表头值获取手工设置的宽度  如表头:30
 			if(headwidth.length==2 && !StringUtil.isEmpty(headwidth[1])){
 				width = Integer.valueOf(headwidth[1]);
 			}
 			sheet.setColumnWidth(i, width * 256);
 		 }
    }
    /**
     * 创建行数据
     * @param dataset
     * @param fieldNames
     * @param sheet
     * @param workbook
     * @param field_pattern
     * @param isWrap 
     */
    public static <T> void createExcelRow(Collection<T> dataset,String[] fieldNames,SXSSFSheet sheet,
    		                              SXSSFWorkbook workbook,Map<String,String> field_pattern, boolean isWrap){
    	
    	// 遍历集合数据，产生数据行
    	Iterator<T> it = dataset.iterator();
    	int index = 0;
    	int sheetIndex=2;
    	SXSSFRow row = null ;
    	XSSFCellStyle style = initRowStyle(workbook, isWrap);
    	field_pattern = (field_pattern !=null ? field_pattern : new HashMap<String,String>());
    	while (it.hasNext()) {
    			index++;
    			if(index%50000==0){
    				sheet = (SXSSFSheet) workbook.createSheet("sheet" + sheetIndex);
    			}
    			row = (SXSSFRow)sheet.createRow(index);
    			T t = (T) it.next();
    			if (t instanceof Map) {
    				Map<String, Object> data = (Map<String, Object>) t;
    				for (int i = 0; i < fieldNames.length; i++) {
    					String key = fieldNames[i];
    					SXSSFCell cell = (SXSSFCell) row.createCell(i);
    					cell.setCellStyle(style);
    					String value = data.get(key) == null ? "" : data.get(key).toString();
    					cell.setCellValue(value);
    					//sheet.autoSizeColumn(i); 
    				}
    			} else {
    				// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
    				
    				try{
	    				for (int i = 0; i < fieldNames.length; i++) {
	    					 SXSSFCell cell = (SXSSFCell) row.createCell(i);
	    					 cell.setCellStyle(style);
	    					 String fieldName = fieldNames[i];
	    					 String getMethodName = "get"+ fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);
	    					 Class<T> tCls = (Class<T>) t.getClass();
	    					 Method getMethod = tCls.getMethod(getMethodName,new Class[] {});
	    					 Object value = getMethod.invoke(t, new Object[] {});
	    					 // 判断值的类型后进行强制类型转换
	    					 String textValue = null;
	    					 if (value instanceof Date) {
	    							Date date = (Date) value;
	    							String pattern    =  field_pattern.get(fieldName);
	    							String dateformat = StringUtils.isBlank(pattern) ? "yyyy-MM-dd": pattern ;
	    							SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
	    							textValue = sdf.format(date);
	    					 }else {
	    							// 其它数据类型都当作字符串简单处理
	    							textValue = value == null ? "" : value.toString();
	    					 }
	    					 if (textValue != null) {
	    							Pattern p = Pattern.compile("^//d+(//.//d+)?$");
	    							Matcher matcher = p.matcher(textValue);
	    							if (matcher.matches()) {
	    								// 是数字当作double处理
	    								cell.setCellValue(Double.parseDouble(textValue));
	    							} else {
	    								HSSFRichTextString richString = new HSSFRichTextString(textValue);
	    								cell.setCellValue(richString);
	    							}
	    					 }
	    				}
    				}catch(Exception e){
    					
    				}
    		}

    	}
    }
    
    /**
	 * 设置 response 信息
	 * @param fileName
	 * @param response
	 */
	public static void setResponseInfo(String fileName,HttpServletResponse response){
		
		try {
			fileName = URLEncoder.encode(fileName, "utf-8");
			fileName = fileName + "_"+ DateFormatUtils.format(new Date(), "yyyyMMddhh24mmss");
		} catch (Exception e) {
		}
		// 设置response头信息
		response.reset();
		response.setCharacterEncoding(CommonConstants.CHARACTER_ENCODE);
		response.setContentType(CommonConstants.CONTENT_TYPE);
		response.setContentType("application/vnd.ms-excel"); // 改成输出excel文件
		response.setHeader("Content-disposition", "attachment; filename="+ fileName + ".xlsx");
	}
    
    /**
     * 初始化表头样式
     */
    private static XSSFCellStyle initHeadStyle(SXSSFWorkbook workbook){
    	
    	XSSFCellStyle head_Style=(XSSFCellStyle) workbook.createCellStyle();
		head_Style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		head_Style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		head_Style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		head_Style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		//head_Style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		//head_Style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		head_Style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		XSSFFont head_font = (XSSFFont) workbook.createFont();
		head_font.setFontName("宋体");// 设置头部字体为宋体
		head_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
		head_font.setFontHeightInPoints((short) 12);
		head_Style.setFont(head_font);// 单元格样式使用字体
		return head_Style ;

    }
    /**
     * 初始化行数据表格样式
     * @param workbook
     * @param isWrap 
     * @return
     */
    private static XSSFCellStyle initRowStyle(SXSSFWorkbook workbook, boolean isWrap){
    	
    	XSSFCellStyle string_style = (XSSFCellStyle)workbook.createCellStyle();
		string_style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		string_style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		string_style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		string_style.setBorderTop(XSSFCellStyle.BORDER_THIN);
		string_style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		string_style.setWrapText(isWrap);
		if(isWrap){
			string_style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		}
		XSSFFont data_font = (XSSFFont) workbook.createFont();
		data_font.setFontName("宋体");// 设置头部字体为宋体
		data_font.setFontHeightInPoints((short) 11);
		string_style.setFont(data_font);// 单元格样式使用字体
		return string_style;
    }

	public static <T> void createBigExcel(String[] headers, String[] mappingFields, Map<String, String> field_pattern,
			Collection<T> dataset, OutputStream out) {
		// TODO Auto-generated method stub
		createBigExcel(headers, mappingFields, field_pattern, dataset, out, false);
		
	}
    
    

}
