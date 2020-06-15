package com.tf.base.common.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ReadEasyExcelUtil {

	public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
	public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
	public static final String NOT_EXCEL_FILE = " : Not the Excel file!";

	/**
     * get postfix of the path
     * @param path
     * @return
     */
    public static String getPostfix(String path) {
        if (path == null || "".equals(path.trim())) {
            return "";
        }
        if (path.contains(".")) {
            return path.substring(path.lastIndexOf(".") + 1, path.length());
        }
        return "";
    }

	/**
     * read the Excel file
     * @param path the path of the Excel file
     * @return
     * @throws IOException
     */
    public static List<String> readExcel(String path) throws IOException {
        if (path == null || "".equals(path)) {
            return null;
        } else {
            String postfix = getPostfix(path);
            if (!"".equals(postfix)) {
                if (OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
                    return readXls(path);
                } else if (OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
                    return readXlsx(path);
                }
            } else {
                System.out.println(path + NOT_EXCEL_FILE);
            }
        }
        return null;
    }

    /**
     * Read the Excel 2010
     * @param path the path of the excel file
     * @return
     * @throws IOException
     */
    private static List<String> readXlsx(String path) throws IOException {
        System.out.println("Processing..." + path);
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        List<String> list = new ArrayList<String>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                	StringBuffer sb = new StringBuffer();
                	for (int cellNum = 0; cellNum < xssfRow.getLastCellNum(); cellNum++) {
                		XSSFCell cell = xssfRow.getCell(cellNum);
//                		System.out.println(rowNum+"---"+cellNum);
                		sb.append(getValue(cell));
                		if(cellNum < xssfRow.getLastCellNum()){
                			sb.append(",");
                		}
                	}

                    list.add(sb.toString());
                }
            }
        }
        return list;
    }

    /**
     * Read the Excel 2003-2007
     * @param path the path of the Excel
     * @return
     * @throws IOException
     */
    private static List<String> readXls(String path) throws IOException {
        System.out.println("Processing..." + path);
        InputStream is = new FileInputStream(path);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        List<String> list = new ArrayList<>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                	StringBuffer sb = new StringBuffer();
                	for (int cellNum = 0; cellNum < hssfRow.getLastCellNum(); cellNum++) {
                		HSSFCell cell = hssfRow.getCell(cellNum);
                		//System.out.println(getValue(cell));
                		sb.append(getValue(cell));
                		if(cellNum < hssfRow.getLastCellNum()){
                			sb.append(",");
                		}
                	}

                    list.add(sb.toString());
                }
            }
        }
        return list;
    }

    @SuppressWarnings("static-access")
    private static String getValue(XSSFCell xssfRow) {
    	if(xssfRow == null){
    		return "";
    	}
        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfRow.getBooleanCellValue());
        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
            return String.valueOf(xssfRow.getNumericCellValue());
        } else {
            return String.valueOf(xssfRow.getStringCellValue());
        }
    }

    @SuppressWarnings("static-access")
    private static String getValue(HSSFCell hssfCell) {
    	if(hssfCell == null){
    		return "";
    	}
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }
}
