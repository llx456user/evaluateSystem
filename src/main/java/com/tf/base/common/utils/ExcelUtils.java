/**
 *
 */
package com.tf.base.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.tf.base.common.exception.ExcelOptExcepiton;
import com.tf.base.common.constants.CommonConstants;

/**
 * Excel操作的工具类
 *
 */
public class ExcelUtils {

	/**
	 * 读取excel文件
	 *
	 * @param titleArr
	 * @param is
	 * @throws ExcelOptExcepiton
	 *             读取文件失败或者其它校验失败都会抛出此异常
	 * @return
	 */
	public static List<Map<String, String>> readExcel(String[] titleArr,InputStream is)throws Exception {

		Workbook workbook = null;

		try {

			List<String> normalTitleList = Arrays.asList(titleArr);
			workbook = PoiUtils.getWorkbook(is);
			// 取第一个sheet
			Sheet sheet = PoiUtils.getSheet(workbook, 0);
			if (sheet == null) {
				throw new ExcelOptExcepiton("导入文件内容为空");
			}
			// 取标题行
			Map<Integer, String> titleMap = new HashMap<Integer, String>();
			Row firstRow = PoiUtils.getRow(sheet, 0);
			if (firstRow == null) {
				throw new ExcelOptExcepiton("导入文件内容为空");
			}
			for (int cellNum = 0; cellNum < PoiUtils.getNumberOfCells(firstRow); cellNum++) {
				Cell cell = firstRow.getCell(cellNum);
				titleMap.put(cellNum, getValue(cell));
			}
			// 校验标题是否正确
			for (String title : normalTitleList) {
				if (!titleMap.values().contains(title)) {
					throw new ExcelOptExcepiton("导入文件缺少【" + title + "】标题");
				}
			}
			List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
			// 循环行Row
			for (int rowNum = 1; rowNum <= PoiUtils.getNumberOfRows(sheet); rowNum++) {
				Row hssfRow = PoiUtils.getRow(sheet, rowNum);
				if (hssfRow == null) {
					continue;
				}
				Map<String, String> rowData = new HashMap<String, String>();
				// 循环列Cell
				int allCellNum = PoiUtils.getLastCellNumber(hssfRow); //PoiUtils.getNumberOfCells(hssfRow)
				for (int cellNum = 0; cellNum < allCellNum ; cellNum++) {
					Cell cell = PoiUtils.getCell(hssfRow, cellNum);
					if(cell ==null){
						rowData.put(titleMap.get(cellNum), "");
					}else{
						rowData.put(titleMap.get(cellNum), getValue(cell));
					}

				}
				dataList.add(rowData);
			}
			return dataList;
		} catch (Exception e) {
			throw new ExcelOptExcepiton(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 导出excel到HttpServletResponse中的通用方法
	 *
	 * @param fileName
	 * @param headers
	 * @param mappingFields
	 * @param dataset
	 * @param response
	 * @param pattern
	 */
	public static <T> void exportExcel(String fileName, String[] headers,String[] mappingFields, Collection<T> dataset,
			                           HttpServletResponse response, Map<String,String> field_pattern,String version) {

		try{
			fileName =  URLEncoder.encode(fileName, "utf-8");
		}catch(Exception e){
		}
		fileName = fileName + "_"+ DateFormatUtils.format(new Date(), "yyyyMMddhh24mmss");
		// 设置response头信息
		response.reset();
		response.setCharacterEncoding(CommonConstants.CHARACTER_ENCODE);
		response.setContentType(CommonConstants.CONTENT_TYPE);
		response.setContentType("application/vnd.ms-excel"); // 改成输出excel文件
		try {
			OutputStream out = response.getOutputStream() ;
			if("2003".equals(version)){
				response.setHeader("Content-disposition", "attachment; filename="+ fileName + ".xls");
				exportExcel(fileName, headers, mappingFields, dataset,out, field_pattern);
			}else{
				response.setHeader("Content-disposition", "attachment; filename="+ fileName + ".xlsx");
				BigExcelUtil.createBigExcel(headers, mappingFields, field_pattern, dataset, out);
			}
		} catch (IOException e) {

		}

	}
	/**
	 * 导出的私有公共方法
	 *
	 * @param title
	 * @param headers
	 * @param mappingFields
	 * @param dataset
	 * @param out
	 * @param pattern
	 */
	@SuppressWarnings("unchecked")
	private static <T> void exportExcel(String title, String[] headers,String[] mappingFields, Collection<T> dataset,
			                            OutputStream out,Map<String,String> field_pattern) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		try {
			// 生成一个表格
			HSSFSheet sheet = workbook.createSheet("sheet1");
			// 设置表格默认列宽度为15个字节
			sheet.setDefaultColumnWidth(15);
			// 生成一个样式
			HSSFCellStyle style = createCenterCellStyle(workbook);
			// 生成并设置另一个样式
			HSSFCellStyle style2 = createAllCenterCellStyle(workbook);
			Map<Integer, String> headerMap = new HashMap<Integer, String>();
			// 产生表格标题行
			HSSFRow row = sheet.createRow(0);
			for (int i = 0; i < headers.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style);
				HSSFRichTextString text = new HSSFRichTextString(headers[i]);
				cell.setCellValue(text);
				headerMap.put(i, mappingFields[i]);
			}

			// 遍历集合数据，产生数据行
			Iterator<T> it = dataset.iterator();
			int index = 0;
			while (it.hasNext()) {
				index++;
				row = sheet.createRow(index);
				T t = (T) it.next();
				if (t instanceof Map) {
					Map<String, Object> data = (Map<String, Object>) t;
					for (int i = 0; i < headers.length; i++) {
						String key = headerMap.get(i);
						HSSFCell cell = row.createCell(i);
						cell.setCellStyle(style2);
						String value = data.get(key) == null ? "" : data.get(
								key).toString();
						cell.setCellValue(value);
					}
				} else {
					// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
					for (int i = 0; i < headers.length; i++) {
						HSSFCell cell = row.createCell(i);
						cell.setCellStyle(style2);
						String fieldName = headerMap.get(i);
						String getMethodName = "get"
								+ fieldName.substring(0, 1).toUpperCase()
								+ fieldName.substring(1);

						Class<T> tCls = (Class<T>) t.getClass();
						Method getMethod = tCls.getMethod(getMethodName,
								new Class[] {});
						Object value = getMethod.invoke(t, new Object[] {});
						// 判断值的类型后进行强制类型转换
						String textValue = null;
						if (value instanceof Date) {
							Date date = (Date) value;
							String pattern    =  field_pattern.get(fieldName);
							String dateformat = StringUtils.isBlank(pattern) ? "yyyy-MM-dd": pattern ;
							SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
							textValue = sdf.format(date);
						}
						else {
							// 其它数据类型都当作字符串简单处理
							textValue = value == null ? "" : value.toString();
						}
						// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
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
				}

			}
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 清理资源
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 创建居中单元格样式
	 *
	 * @param workbook
	 * @return
	 */
	private static HSSFCellStyle createCenterCellStyle(HSSFWorkbook workbook) {
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		//style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成字体样式
		HSSFFont font = workbook.createFont();
		//font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 11);
		font.setFontName("宋体");
		//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		return style;
	}

	/**
	 * 创建横向和纵向都居中的单元格样式
	 *
	 * @param workbook
	 * @return
	 */
	private static HSSFCellStyle createAllCenterCellStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style2 = workbook.createCellStyle();
		//style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		//style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style2.setWrapText(true); //自动换行
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		//font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);font.setFontHeightInPoints((short) 12);
		font2.setFontName("宋体");
		font2.setFontHeightInPoints((short) 11);
		 //把字体应用到当前的样式
		style2.setFont(font2);
		return style2;
	}

	/**
	 * 得到Excel表中的值
	 *
	 * @param cell
	 *            Excel中的每一个格子
	 * @return Excel中每一个格子中的值
	 */
	private static String getValue(Cell cell) {
		String value = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC: // 数字

			if(HSSFDateUtil.isCellDateFormatted(cell)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				value =  sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
			}else{
				double cellValue = cell.getNumericCellValue();
				value = new DecimalFormat("0.####").format(cellValue);
			}
			break;
		case HSSFCell.CELL_TYPE_STRING: // 字符串
			value = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
			value = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_FORMULA: // 公式
			value = cell.getCellFormula();
			break;
		case HSSFCell.CELL_TYPE_BLANK: // 空值
		case HSSFCell.CELL_TYPE_ERROR: // 故障
		default:
			break;
		}
		return value;
	}

	/**
	 * 删除前一天的文件
	 *
	 * @param storePath
	 */
	private static void deleteOldFile(String storePath) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = sdf.parse(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
		} catch (ParseException e) {
			date = new Date();
		}
		long now = date.getTime();
		try {
			File storeFile = new File(storePath);
			File[] files = storeFile.listFiles();
			if (files != null && files.length > 0) {
				for (File file : files) {
					long lastMod = file.lastModified();
					if (lastMod < now) {
						try {
							file.deleteOnExit();
						} catch (Exception e) {
						}

					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws FileNotFoundException {

	}
}
