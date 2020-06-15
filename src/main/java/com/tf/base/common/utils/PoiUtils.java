package com.tf.base.common.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiUtils {

	public static void main(String[] args) {

		
	}

	public static int getNumberOfSheets(Object workbook) {
		if (workbook != null) {
			if (workbook instanceof XSSFWorkbook) {
				return ((XSSFWorkbook) workbook).getNumberOfSheets();
			} else if (workbook instanceof HSSFWorkbook) {
				return ((HSSFWorkbook) workbook).getNumberOfSheets();
			}
		}
		return 0;
	}

	public static int getNumberOfRows(Object sheet) {
		if (sheet != null) {
			if (sheet instanceof HSSFSheet) {
				return ((HSSFSheet) sheet).getPhysicalNumberOfRows();
			} else if (sheet instanceof XSSFSheet) {
				return ((XSSFSheet) sheet).getPhysicalNumberOfRows();
			}
		}
		return 0;
	}

	public static int getNumberOfCells(Object row) {
		if (row != null) {
			if (row instanceof XSSFRow) {
				return ((XSSFRow) row).getPhysicalNumberOfCells();
			} else if (row instanceof HSSFRow) {
				return ((HSSFRow) row).getPhysicalNumberOfCells();
			}
		}
		return 0;
	}

	public static int getLastCellNumber(Object row) {
		if (row != null) {
			if (row instanceof XSSFRow) {
				return ((XSSFRow) row).getLastCellNum();
			} else if (row instanceof HSSFRow) {
				return ((HSSFRow) row).getLastCellNum();
			}
		}
		return 0;
	}

	// ------------------------------------------------------------------------
	public static InputStream getInputStream(String fileName) throws FileNotFoundException {

		if (fileName != null && !"".equals(fileName)) {
			return new FileInputStream(fileName);
		}
		return null;
	}

	public static Workbook getWorkbook(InputStream inputStream) throws IOException, InvalidFormatException {
		if (inputStream != null) {
			return WorkbookFactory.create(inputStream);
		}
		return null;
	}

	public static Sheet getSheet(Workbook workbook, int sheetCount) {
		if (workbook != null) {
			return workbook.getSheetAt(sheetCount);
		}
		return null;
	}

	public static Row getRow(Sheet sheet, int rowNumber) {
		if (sheet != null) {
			return sheet.getRow(rowNumber);
		}
		return null;
	}
	public static void shiftRows(Sheet sheet,int rowIndex) {
		if (sheet != null) {
			int lastRowNum=sheet.getLastRowNum();  
			if(rowIndex >= 0 && rowIndex < lastRowNum){
				sheet.shiftRows(rowIndex+1,lastRowNum,-1);//将行号为rowIndex+1一直到行号为lastRowNum的单元格全部上移一行，以便删除rowIndex行  				 
			}else if(rowIndex==lastRowNum){  
				Row removingRow=sheet.getRow(rowIndex);
				if(removingRow!=null){
					sheet.removeRow(removingRow);  					
				}
			}
		}
	}

	public static Cell getCell(Row row, int cellNumber) {
		if (row != null) {
			return row.getCell(cellNumber);
		}
		return null;
	}

	static BigDecimal multiplicand = new BigDecimal(100).setScale(0);

	public static Object getCellValue(Cell cell, boolean isPercent) {
		if (cell != null) {
			if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
				return cell.getBooleanCellValue();
			} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				if (DateUtil.isCellDateFormatted(cell)) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh24:mm:ss");
					return formatter.format(cell.getDateCellValue()).trim();
				} else {
					// 对数字进行格式化，使其不自动转化为科学计数法。
					Object obj = null;
					if (isPercent) {
						obj = new BigDecimal(cell.getNumericCellValue()).setScale(8, RoundingMode.HALF_UP)
								.multiply(multiplicand).setScale(8, RoundingMode.HALF_UP);
						obj = trim(obj.toString()) + "%";
					} else {
						obj = new BigDecimal(cell.getNumericCellValue()).setScale(6, RoundingMode.HALF_UP);
						obj = trim(obj.toString());
					}
					return String.valueOf(obj);
				}
			} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				return cell.getRichStringCellValue().getString().trim();
			} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				return cell.getCellFormula();
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				return "";
			}
		}
		return "";
	}
	public static Object getCellValue(Cell cell, boolean isPercent,String dateFormate) {
		if (cell != null) {
			if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
				return cell.getBooleanCellValue();
			} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				if (DateUtil.isCellDateFormatted(cell)) {
					SimpleDateFormat formatter = new SimpleDateFormat(dateFormate);
					
					return formatter.format(cell.getDateCellValue()).trim();
				} else {
					// 对数字进行格式化，使其不自动转化为科学计数法。
					Object obj = null;
					if (isPercent) {
						obj = new BigDecimal(cell.getNumericCellValue()).setScale(8, RoundingMode.HALF_UP)
								.multiply(multiplicand).setScale(8, RoundingMode.HALF_UP);
						obj = trim(obj.toString()) + "%";
					} else {
						obj = new BigDecimal(cell.getNumericCellValue()).setScale(6, RoundingMode.HALF_UP);
						obj = trim(obj.toString());
					}
					return String.valueOf(obj);
				}
			} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				return cell.getRichStringCellValue().getString().trim();
			} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				return cell.getCellFormula();
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				return "";
			}
		}
		return "";
	}

	/**
	  * 清零
	  * @param str 原始字符串
	  * @return
	  */
	public static String trim(String str) {
		if (str.indexOf(".") != -1 && str.charAt(str.length() - 1) == '0') {
			return trim(str.substring(0, str.length() - 1));
		} else {
			return str.charAt(str.length() - 1) == '.' ? str.substring(0, str.length() - 1) : str;
		}
	}

	public static void updateCell(Row row, int cellNumber, String cellVaule) {
		if (row != null && cellNumber >= 0) {
			Cell updateCell = row.getCell(cellNumber);
			if (updateCell == null) {
				updateCell = row.createCell(cellNumber);
			}
			if (updateCell != null) {
				updateCell.setCellValue(cellVaule);				
			}
		}
	}

	public static Object getCellValue(Row row, int cellNumber, boolean isPresent) {
		if (row != null) {
			Cell cell = PoiUtils.getCell(row, cellNumber);
			if (cell != null) {
				return PoiUtils.getCellValue(cell, isPresent);

			}
		}
		return null;
	}

	public static void setCellValue(Row row, int cellNumber, String value) {
		if (row != null) {
			Cell cell = PoiUtils.getCell(row, cellNumber);
			if (cell != null) {
				cell.setCellValue(value);

			}
		}

	}

	public static void setCellValue(Row row, int cellNumber, double value) {
		if (row != null) {
			Cell cell = PoiUtils.getCell(row, cellNumber);
			if (cell != null) {
				cell.setCellValue(value);

			}
		}
	}

	//	------------------------------------------create
	public static Workbook createWorkbook() {
		return new XSSFWorkbook();
	}

	public static Sheet createSheet(Workbook workbook, int index, String sheetName) {
		if (workbook != null) {
			Sheet sheet = workbook.createSheet();
			if (sheetName != null) {
				workbook.setSheetName(index, sheetName);
			}
			return sheet;
		}
		return null;
	}

	public static Row createRow(Sheet sheet, int rowLine) {
		if (sheet != null) {
			return sheet.createRow(rowLine);
		}
		return null;
	}

	public static void createCell(Row row, int cellIndex, String cellVaule) {
		if (row != null && cellIndex >= 0) {
			Cell createCell = row.createCell(cellIndex);
			if (createCell != null) {
				createCell.setCellValue(cellVaule);
			}
		}
	}

	/**
	 * 创建带样式的单元格
	 * @param row
	 * @param cellIndex
	 * @param cellValue
	 * @param style
	 */
	public static void createCell(Row row, int cellIndex, String cellValue, CellStyle style) {
		if (row != null && cellIndex >= 0) {
			Cell createCell = row.createCell(cellIndex);
			if (createCell != null) {
				createCell.setCellValue(cellValue);
				if (style != null) {
					createCell.setCellStyle(style);
				}
			}
		}
	}

	public static String createExcel(Workbook workbook, String fileName) {

		String returnStr = null;
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(fileName);
			workbook.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			returnStr = e.getMessage();
		} catch (IOException e) {
			returnStr = e.getMessage();
		} finally {
			try {
				if (fileOut != null) {
					fileOut.close();
					fileOut = null;
				}
			} catch (IOException e) {
				returnStr = e.getMessage();
			}
		}
		return returnStr;
	}

}
