package com.tf.base.project.controller;


import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javafx.scene.control.Cell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyle;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDecimalNumber;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyle;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STStyleType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

import com.tf.base.project.domain.AssessTable;

import sun.misc.BASE64Decoder;
public class WordBean {

    private static final String String = null;
    //word文件 
    CustomXWPFDocument document= new CustomXWPFDocument(); 
    //题号
    int t = 1 ;
    
    public WordBean(CustomXWPFDocument document){
    	this.document = document;
    }
    
    public XWPFParagraph createParagraph(){
    	XWPFParagraph p = document.createParagraph(); //每一个表格内容设置为一个段落，便于设置样式，字体
        //原本是在方法内写，但每个段落好像自动会有
        //一个换行，导致有多少表格 下边就有多少空行             
    	return p;
    }
    
    public void close() throws IOException, XmlException{
    	CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();  
		XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr); 
		System.out.println("create_table document written success.");  
    }
    public void addTitle(String title){
    	 //添加标题  
        XWPFParagraph titleParagraph = document.createParagraph();
        addCustomHeadingStyle(document, "标题 1", 1);
        titleParagraph.setStyle("标题 1");
        //设置段落居中  
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);  
        XWPFRun titleParagraphRun = titleParagraph.createRun();  
        titleParagraphRun.setText(title);  
        titleParagraphRun.setColor("000000");  
        titleParagraphRun.setFontSize(14);
        titleParagraphRun.setFontFamily("黑体");//字体
    }
    
    
    //添加图片    //base64Info为前台传过来的图片信息
    public void addPicture(String base64Info, String imgWidth, String imgHeight) throws IOException, InvalidFormatException {
            base64Info = base64Info.replaceAll(" ", "+"); 
            String pictureInfo = (String)base64Info.split("base64,")[1]; 

            BASE64Decoder decoder = new BASE64Decoder();  
            byte[] a = decoder.decodeBuffer(pictureInfo);// Base64解码  
	        for (int i = 0; i < a.length; ++i) {  
	            if (a[i] < 0) {// 调整异常数据  
	               a[i] += 256;  
	            }  
	        }
	        String picId1 = document.addPictureData(a, XWPFDocument.PICTURE_TYPE_JPEG);
	        document.createPicture(picId1, document.getNextPicNameNumber(XWPFDocument.PICTURE_TYPE_PNG), Integer.parseInt(imgWidth), Integer.parseInt(imgHeight));
    }
    //添加图片    //base64Info为前台传过来的图片信息
    public void addPicture(String base64Info) throws IOException, InvalidFormatException {
    	base64Info = base64Info.replaceAll(" ", "+"); 
    	String pictureInfo = (String)base64Info.split("base64,")[1]; 
    	
    	BASE64Decoder decoder = new BASE64Decoder();  
    	byte[] a = decoder.decodeBuffer(pictureInfo);// Base64解码  
    	for (int i = 0; i < a.length; ++i) {  
    		if (a[i] < 0) {// 调整异常数据  
    			a[i] += 256;  
    		}  
    	}
    	String picId1 = document.addPictureData(a, XWPFDocument.PICTURE_TYPE_JPEG);
    	document.createPicture(picId1, document.getNextPicNameNumber(XWPFDocument.PICTURE_TYPE_PNG), 660,180);
    }

    /**
     * 评估结果
     * @param assessTable
     */
    public void addAssessResultTable(AssessTable assessTable){
        ExcelMergeUtil util = new ExcelMergeUtil();
        XWPFTable table1 = document.createTable(assessTable.getRows(), assessTable.getColumns());
        util.megerTableCell(table1,assessTable);
    }

    /**
     * 模板评估结果
     * @param assessTable
     */
    public void templateAddAssessResultTable(AssessTable assessTable, XWPFParagraph paragraph){
        ExcelMergeUtil util = new ExcelMergeUtil();
        // 指定位置插入表格
        XmlCursor cursor = paragraph.getCTP().newCursor();
        XWPFTable table = document.insertNewTbl(cursor);
        XWPFTableRow row = table.getRow(0);
        for (int j = 1; j < assessTable.getColumns(); j++) {
            row.createCell();
        }
        if (assessTable.getRows() > 0) {
            for (int i = 1; i < assessTable.getRows(); i++){
                XWPFTableRow row1 = table.createRow();
            }
        }
        util.megerTableCell(table,assessTable);
    }


    //添加带图片的表格
    public void addPic(Map beanMap ,XWPFParagraph p) throws Exception{
        addPicQueNam(beanMap);
        //创建表格
        XWPFTable ComTable1 = document.createTable();  
        //外边框样式 
        setBorders(ComTable1);
        // 设置上下左右四个方向的距离
        ComTable1.setCellMargins(20, 20, 20, 20);

        //列宽自动分割  
        CTTblWidth comTableWidth = ComTable1.getCTTbl().addNewTblPr().addNewTblW();  
        comTableWidth.setType(STTblWidth.DXA);  
        comTableWidth.setW(BigInteger.valueOf(9072));  
        //去除单元格间的竖线
         ComTable1.setInsideVBorder(XWPFBorderType.NONE, 0, 0, "");
         //横线颜色
         ComTable1.setInsideHBorder(XWPFBorderType.SINGLE , 0, 0, "F4F4F4");
         //表格第一行  
         XWPFTableRow comTableRowOne = ComTable1.getRow(0); 
         setCell(comTableRowOne.getCell(0),"选项",p);
         comTableRowOne.getCell(0).setColor("F4F4F4");  //设置表格颜色
         setCell( comTableRowOne.addNewTableCell(),"小计",p);
         comTableRowOne.getCell(1).setColor("F4F4F4");
         setCell(comTableRowOne.addNewTableCell(),"百分比",p);
         comTableRowOne.getCell(2).setColor("F4F4F4"); 

         List<Map<Object,Object>> childrenList = (List<Map<Object, Object>>) beanMap.get("children");
         for(Map<Object,Object> childMap : childrenList ){
            XWPFTableRow comTableRow = ComTable1.createRow();  
           setPicCell( comTableRow.getCell(0),(String)childMap.get("aswrNm"),(String)childMap.get("picUrlAddr"),p);
           setCell(comTableRow.getCell(1),(String)childMap.get("count").toString(),p);
           setCell(comTableRow.getCell(2),(String)childMap.get("percent"),p);
         }
         //存放图片
         String base64Info = (String)beanMap.get("base64Info");
         if(base64Info != null){
              addPicture(base64Info);
         }
    }
    
    public void addMatrixTable(Map<String, Object> beanMap,XWPFParagraph p){
    	//创建表格
        XWPFTable ComTable5 = document.createTable();  
        //外边框样式 
        setBorders(ComTable5);
        // 设置上下左右四个方向的距离
        ComTable5.setCellMargins(20, 20, 20, 20);
        //列宽自动分割  
        CTTblWidth comTableWidth11 = ComTable5.getCTTbl().addNewTblPr().addNewTblW();  
        comTableWidth11.setType(STTblWidth.DXA);  
        comTableWidth11.setW(BigInteger.valueOf(9072));  
        //去除单元格间的竖线
         ComTable5.setInsideVBorder(XWPFBorderType.NONE, 0, 0, "");
         //横线颜色
         ComTable5.setInsideHBorder(XWPFBorderType.SINGLE , 0, 0, "F4F4F4");
        //表格第一行  
         XWPFTableRow comTableRowOne11 = ComTable5.getRow(0); 
         comTableRowOne11.getCell(0).setText(" "); 
         comTableRowOne11.getCell(0).setColor("F4F4F4");  //设置表格颜色
         List<Map<Object,Object>> childrenList11 = (List<Map<Object, Object>>) beanMap.get("children");
         List<Map<Object,Object>> childrenList12 =(List<Map<Object, Object>>) childrenList11.get(0).get("children");
          int b = 1;
         for(Map<Object,Object> map : childrenList12){
             setCell(comTableRowOne11.addNewTableCell(),(String)map.get("aswrNm"),p);
           //comTableRowOne11.addNewTableCell().setText((String)map.get("aswrNm")); 
           comTableRowOne11.getCell(b).setColor("F4F4F4");
           b++;
         }//标题行结束

          for(Map<Object,Object> map : childrenList11){
              XWPFTableRow comTableRow = ComTable5.createRow();  
              setCell(comTableRow.getCell(0),(String)map.get("quNm"),p);
             // comTableRow.getCell(0).setText((String)map.get("quNm"));//每行第一个为矩阵题目 
               List<Map<Object,Object>> map11 =(List<Map<Object, Object>>) map.get("children");
                      int c = 1;
                  for(Map<Object,Object> map12 : map11){
                      setCell(comTableRow.getCell(c),map12.get("count").toString()+"("+(String)map12.get("percent")+")",p);
                 // comTableRow.getCell(c).setText((String)map12.get("count")+"("+(String)map12.get("percent")+")");
                       c++;
                 }  
          }
    }
    
    int tmpChildrenLen = 0;
    public int getChildrenNum(String id,Map<String, Object> map){

    		if(map.containsKey("children") && map.get("children")!=null ){
    			if(map.get("id").toString().equals(id)){
    				//console.log(d.children.length);
    				tmpChildrenLen += ((ArrayList<Map<java.lang.String, Object>>)map.get("children")).size();
    			}
    			ArrayList<Map<java.lang.String, Object>> tmp = (ArrayList<Map<java.lang.String, Object>>) map.get("children");
    			for (Map<String, Object> tmpMap : tmp) {
					getChildrenNum(tmpMap.get("id").toString(),tmpMap);
				}
    						
    		}
    		return tmpChildrenLen;

    }
    
    private void addTreeTableNoHeader_Tr(Map<String, Object> map,XWPFTable comTable1, XWPFParagraph p){
    	
    	tmpChildrenLen = 0;
    	 XWPFTableRow comTableRow = comTable1.createRow();  
    	 
    	 String id = map.get("id").toString();
    	 setCell( comTableRow.getCell(0),(String)map.get("indexName").toString(),p);
    	 
    	 ArrayList<Map<String, Object>> list = (ArrayList<Map<java.lang.String, Object>>) map.get("children"); 
    	 if( list!=null && list.size() > 0){
    		 for (Map<String, Object> map2 : list) {
    			 addTreeTableNoHeader_Tr(map2, comTable1, p);
			}
    	 }
    }
  //添加shuxing表格 无表头
    public void addTreeTableNoHeader(Map<String, Object> map,XWPFParagraph p){
    	//创建表格
        XWPFTable ComTable1 = document.createTable();  
        //外边框样式 
        setBorders(ComTable1);
        // 设置上下左右四个方向的距离
        ComTable1.setCellMargins(20, 20, 20, 20);

        //列宽自动分割  
        CTTblWidth comTableWidth = ComTable1.getCTTbl().addNewTblPr().addNewTblW();  
        comTableWidth.setType(STTblWidth.DXA);  
        comTableWidth.setW(BigInteger.valueOf(9072));  
        //去除单元格间的竖线
         //ComTable1.setInsideVBorder(XWPFBorderType.NONE, 0, 0, "");
        ComTable1.setInsideVBorder(XWPFBorderType.SINGLE, 0, 0, "C3599D");
         //横线颜色
         ComTable1.setInsideHBorder(XWPFBorderType.SINGLE , 0, 0, "C3599D");
        setBorders(ComTable1);
        addTreeTableNoHeader_Tr(map,ComTable1,p);
        
        
        mergeCellsVertically(ComTable1, 0, 0, 4);
        mergeCellsVertically(ComTable1, 0, 0, 0);
        mergeCellsVertically(ComTable1, 0, 0, 0);
        mergeCellsVertically(ComTable1, 0, 0, 0);
        
    }
    //添加普通表格 无表头
    public void addTableNoHeader(String[] headers,List<Map<String,Object>> dataset,XWPFParagraph p){
    	//创建表格
        XWPFTable ComTable1 = document.createTable(dataset.size()-1,headers.length);  
        //外边框样式 
        setBorders(ComTable1);
        // 设置上下左右四个方向的距离
        ComTable1.setCellMargins(20, 20, 20, 20);

        //列宽自动分割  
        CTTblWidth comTableWidth = ComTable1.getCTTbl().addNewTblPr().addNewTblW();  
        comTableWidth.setType(STTblWidth.DXA);  
        comTableWidth.setW(BigInteger.valueOf(9072));  
        //去除单元格间的竖线
         //ComTable1.setInsideVBorder(XWPFBorderType.NONE, 0, 0, "");
        ComTable1.setInsideVBorder(XWPFBorderType.SINGLE, 0, 0, "C3599D");
         //横线颜色
         ComTable1.setInsideHBorder(XWPFBorderType.SINGLE , 0, 0, "C3599D");
        setBorders(ComTable1);
         for(Map<String,Object> childMap : dataset ){
             XWPFTableRow comTableRow = ComTable1.createRow();  
             for (int i = 0; i < headers.length ; i++) {
            	 setCell( comTableRow.getCell(i),(String)childMap.get(headers[i]).toString(),p);
			 }
         }
    }
    //添加普通表格带主表头
    public void addTableHasMainTitle(String title,String[] headers,List<Map<String,Object>> dataset,XWPFParagraph p, String exportWay){

        XWPFTable ComTable1 = null;
        if ("export".equals(exportWay)) {
            //创建表格
            ComTable1 = document.createTable(2,headers.length);
        } else {
            // 指定位置插入表格
            XmlCursor cursor = p.getCTP().newCursor();
            ComTable1 = document.insertNewTbl(cursor);
            XWPFTableRow row = ComTable1.getRow(0);
            XWPFTableRow row2 = ComTable1.createRow();
            for (int i = 1; i < headers.length; i++) {
                row.createCell();
                row2.createCell();
            }
        }
    	//外边框样式 
    	setBorders(ComTable1);
    	// 设置上下左右四个方向的距离
    	ComTable1.setCellMargins(20, 20, 20, 20);
    	
    	//列宽自动分割  
    	CTTblWidth comTableWidth = ComTable1.getCTTbl().addNewTblPr().addNewTblW();  
    	comTableWidth.setType(STTblWidth.DXA);  
    	comTableWidth.setW(BigInteger.valueOf(9072));  
    	//去除单元格间的竖线
    	//ComTable1.setInsideVBorder(XWPFBorderType.NONE, 0, 0, "");
    	ComTable1.setInsideVBorder(XWPFBorderType.SINGLE, 0, 0, "C3599D");
    	//横线颜色
    	ComTable1.setInsideHBorder(XWPFBorderType.SINGLE , 0, 0, "C3599D");
    	setBorders(ComTable1);

    	 CTTblBorders borders=ComTable1.getCTTbl().getTblPr().addNewTblBorders();  
         CTBorder hBorder=borders.addNewInsideH();  
         hBorder.setVal(STBorder.Enum.forString("thick"));  
         hBorder.setSz(new BigInteger("1"));  
         hBorder.setColor("BF6BCC");  
           
         CTBorder vBorder=borders.addNewInsideV();  
         vBorder.setVal(STBorder.Enum.forString("thick"));  
         vBorder.setSz(new BigInteger("1"));  
         vBorder.setColor("BF6BCC");  
           
         CTBorder lBorder=borders.addNewLeft();  
         lBorder.setVal(STBorder.Enum.forString("thick"));  
         lBorder.setSz(new BigInteger("1"));  
         lBorder.setColor("BF6BCC");  
           
         CTBorder rBorder=borders.addNewRight();  
         rBorder.setVal(STBorder.Enum.forString("thick"));  
         rBorder.setSz(new BigInteger("1"));  
         rBorder.setColor("BF6BCC");  
           
         CTBorder tBorder=borders.addNewTop();  
         tBorder.setVal(STBorder.Enum.forString("thick"));  
         tBorder.setSz(new BigInteger("1"));  
         tBorder.setColor("BF6BCC");  
           
         CTBorder bBorder=borders.addNewBottom();  
         bBorder.setVal(STBorder.Enum.forString("thick"));  
         bBorder.setSz(new BigInteger("1"));  
         bBorder.setColor("BF6BCC");
    	//表格第一行  表头
    	XWPFTableRow comTableRowOne = ComTable1.getRow(0); 
    	XWPFTableCell titleCell = comTableRowOne.getCell(0);
    	//titleCell.setColor("F4F4F4");
    	//titleCell.setText(title);
    	if(titleCell.getParagraphs().size()>0){
    		XWPFParagraph  p1=titleCell.getParagraphs().get(0);
    		XWPFRun pRun=p1.createRun();
    		pRun.setText(title);
    		 pRun.setFontSize(14);
    		pRun.setFontFamily("宋体");
    		pRun.setBold(true);
    		//垂直居中
    		titleCell.setVerticalAlignment(XWPFVertAlign.CENTER);
    	    //水平居中
    	    p1.setAlignment(ParagraphAlignment.CENTER);
	   }else{
		   XWPFParagraph   p1=titleCell.addParagraph();
		   XWPFRun pRun=p1.createRun();
		   pRun.setText(title);
		    pRun.setFontSize(14);
		   pRun.setFontFamily("宋体");
		   pRun.setBold(true);
		   
		 //垂直居中
		   titleCell.setVerticalAlignment(XWPFVertAlign.CENTER);
		    //水平居中
		    p1.setAlignment(ParagraphAlignment.CENTER);
	   }
    	    
    	mergeCellsHorizontal(ComTable1, 0, 0, headers.length-1);
    	XWPFTableRow comTableRowTwo = ComTable1.getRow(1);

    	List<XWPFTableCell> list = comTableRowTwo.getTableCells();

    	for (int i = 0;i<headers.length ; i++) {
    		
    		setCell(comTableRowTwo.getCell(i),headers[i],p);
    		comTableRowTwo.getCell(i).setColor("F4F4F4");  //设置表格颜色
    	}
    	for(Map<String,Object> childMap : dataset ){
    		XWPFTableRow comTableRow = ComTable1.createRow();  
    		for (int i = 0; i < headers.length ; i++) {
    			setCell( comTableRow.getCell(i),(String)childMap.get(headers[i]).toString(),p);
    		}
    	}
    }
    //添加普通表格
    public void addTable(String[] headers,List<Map<String,Object>> dataset,XWPFParagraph p){
    	//创建表格
    	XWPFTable ComTable1 = document.createTable(2,headers.length);
    	//外边框样式 
    	setBorders(ComTable1);
    	// 设置上下左右四个方向的距离
    	ComTable1.setCellMargins(20, 20, 20, 20);
    	
    	//列宽自动分割  
    	CTTblWidth comTableWidth = ComTable1.getCTTbl().addNewTblPr().addNewTblW();  
    	comTableWidth.setType(STTblWidth.DXA);  
    	comTableWidth.setW(BigInteger.valueOf(9072));  
    	//去除单元格间的竖线
    	//ComTable1.setInsideVBorder(XWPFBorderType.NONE, 0, 0, "");
    	ComTable1.setInsideVBorder(XWPFBorderType.SINGLE, 0, 0, "C3599D");
    	//横线颜色
    	ComTable1.setInsideHBorder(XWPFBorderType.SINGLE , 0, 0, "C3599D");
    	setBorders(ComTable1);
    	//表格第一行  表头
    	XWPFTableRow comTableRowOne = ComTable1.getRow(0); 
    	/*CTTblBorders borders=ComTable1.getCTTbl().getTblPr().addNewTblBorders();  
         CTBorder tBorder=borders.addNewTop(); 
         CTBorder lBorder=borders.addNewLeft(); 
         CTBorder rBorder=borders.addNewRight(); 
         CTBorder bBorder=borders.addNewBottom(); 
         tBorder.setVal(STBorder.Enum.forString("thick"));  
         tBorder.setSz(new BigInteger("1"));  
         tBorder.setColor("C3599D");  
         lBorder.setVal(STBorder.Enum.forString("thick"));  
         lBorder.setSz(new BigInteger("1"));  
         lBorder.setColor("C3599D");  
         rBorder.setVal(STBorder.Enum.forString("thick"));  
         rBorder.setSz(new BigInteger("1"));  
         rBorder.setColor("C3599D");  
         bBorder.setVal(STBorder.Enum.forString("thick"));  
         bBorder.setSz(new BigInteger("1"));  
         bBorder.setColor("C3599D");  */
    	for (int i = 0;i<headers.length ; i++) {
    		
    		setCell(comTableRowOne.getCell(i),headers[i],p);
    		comTableRowOne.getCell(i).setColor("F4F4F4");  //设置表格颜色
    	}
    	for(Map<String,Object> childMap : dataset ){
    		XWPFTableRow comTableRow = ComTable1.createRow();  
    		for (int i = 0; i < headers.length ; i++) {
    			setCell( comTableRow.getCell(i),(String)childMap.get(headers[i]).toString(),p);
    		}
    	}
    }

  //换行
   public void br(){
       XWPFParagraph paragraph = document.createParagraph(); 
       XWPFRun paragraphRun = paragraph.createRun(); 
       paragraphRun.setText("");
       XWPFParagraph paragraph1 = document.createParagraph(); 
       XWPFRun paragraphRun1 = paragraph1.createRun(); 
       paragraphRun.setText(""); 


   }

   //添加图片题带题目
   public void addPicQueNam(Map map) throws InvalidFormatException, IOException{

       XWPFParagraph paragraph = document.createParagraph();  
       XWPFRun queNum = paragraph.createRun(); 
       queNum.setText(String.valueOf(t)); 
       queNum.setColor("87CEFF");
       queNum.setFontSize(12);//每一个creatRun是一个样式
       XWPFRun paragraphRun = paragraph.createRun(); 
       String quNm = (String)map.get("quNm");
       paragraphRun.setText((String)map.get("quNm"));
       String quPicUrlAddr = (String)map.get("quPicUrlAddr");
       paragraphRun.setFontSize(10);

       if(quPicUrlAddr != null){

                 URL picUrl = new URL(quPicUrlAddr); 
            //   DataInputStream pictureData = new DataInputStream(picUrl.openStream()); 
                 InputStream pictureData = picUrl.openStream();
                 XWPFRun quPic =paragraph.createRun();
                 quPic.addPicture(pictureData, XWPFDocument.PICTURE_TYPE_PNG, "", Units.toEMU(40), Units.toEMU(20));

      }

       t++;
   }
 //添加小标题
   public void addTitle_2(String content){

       XWPFParagraph paragraph = document.createParagraph();  
       addCustomHeadingStyle(document, "标题 2", 2);
       paragraph.setStyle("标题 2");
       XWPFRun paragraphRun = paragraph.createRun(); 
       paragraphRun.setText(content);  
//       paragraphRun.setFontSize(10);
       paragraphRun.setBold(true);
       paragraphRun.setFontFamily("黑体");
   }
   
  //添加普通内容
   public void addContent(String content){

       XWPFParagraph paragraph = document.createParagraph();
       addCustomHeadingStyle(document, "标题 3", 3);
       paragraph.setStyle("标题 3");
       XWPFRun queNum = paragraph.createRun(); 
       queNum.setColor("000000");
       // queNum.setFontSize(11);
       queNum.setFontFamily("宋体");
       XWPFRun paragraphRun = paragraph.createRun(); 
       paragraphRun.setText(content);  
       // paragraphRun.setFontSize(11);
       paragraphRun.setFontFamily("宋体");
       paragraphRun.setBold(true);
   }
   //添加题目
   public void addQueNam(String content){
	   
	   XWPFParagraph paragraph = document.createParagraph();  
	   XWPFRun queNum = paragraph.createRun(); 
	   queNum.setText(String.valueOf(t)); 
	   queNum.setColor("87CEFF");
	   // queNum.setFontSize(12);
	   XWPFRun paragraphRun = paragraph.createRun(); 
	   paragraphRun.setText(content);  
	   paragraphRun.setFontSize(10);
	   t++;
   }

   //设置外边框样式
   private void setBorders( XWPFTable ComTable){
         CTTblBorders borders=ComTable.getCTTbl().getTblPr().addNewTblBorders();  
            CTBorder lBorder=borders.addNewLeft();  
            lBorder.setVal(STBorder.Enum.forString("single"));  
            lBorder.setSz(new BigInteger("10"));  
            lBorder.setColor("F4F4F4");  

            CTBorder rBorder=borders.addNewRight();  
           rBorder.setVal(STBorder.Enum.forString("single"));  
            rBorder.setSz(new BigInteger("10"));  
            rBorder.setColor("F4F4F4");  

            CTBorder tBorder=borders.addNewTop();  
           tBorder.setVal(STBorder.Enum.forString("single"));  
            tBorder.setSz(new BigInteger("10"));  
            tBorder.setColor("F4F4F4");  

            CTBorder bBorder=borders.addNewBottom();  
            bBorder.setVal(STBorder.Enum.forString("single"));  
            bBorder.setSz(new BigInteger("10"));  
            bBorder.setColor("F4F4F4");  


     }
   
   /** 
    *  
    * @param xDocument 
    * @param cell 
    * @param text 
    * @param bgcolor 
    * @param width 
    */  
   private void setCellText(XWPFDocument xDocument, XWPFTableCell cell,  
           String text, String bgcolor, int width) {  
       CTTc cttc = cell.getCTTc();  
       CTTcPr cellPr = cttc.addNewTcPr();  
       cellPr.addNewTcW().setW(BigInteger.valueOf(width));  
       XWPFParagraph pIO =cell.addParagraph();  
       cell.removeParagraph(0);  
       XWPFRun rIO = pIO.createRun();  
       rIO.setFontFamily("微软雅黑");  
       rIO.setColor("000000");  
       rIO.setFontSize(12);  
       rIO.setText(text);  
   }
   
//设置图片题表格内容及对齐方式
   private void setPicCell(XWPFTableCell cell,String text,String picUrlAddr, XWPFParagraph p) throws InvalidFormatException, IOException{

       if(cell.getParagraphs().size()>0){
             p=cell.getParagraphs().get(0);
    }else{
            p=cell.addParagraph();
    }
    XWPFRun pRun=p.createRun();
    pRun.setText(text);
    if(picUrlAddr != null){   //url为图片地址
    URL picUrl = new URL(picUrlAddr); 
    InputStream pictureData = picUrl.openStream();
    pRun.addPicture(pictureData, XWPFDocument.PICTURE_TYPE_PNG, "", Units.toEMU(40), Units.toEMU(20));

    }

    //垂直居中
    cell.setVerticalAlignment(XWPFVertAlign.CENTER);
    //水平居中
    p.setAlignment(ParagraphAlignment.CENTER);


 }

//设置表格内容及对齐方式
   private void setCell(XWPFTableCell cell,String text,XWPFParagraph p){

	  /* CTTc cttc = cell.getCTTc();  
	    CTTcPr cellPr = cttc.addNewTcPr();  
	    cellPr.addNewTcW().setW(BigInteger.valueOf(1600));  
	    //cell.setColor(bgcolor);  
	    CTTcPr ctPr = cttc.addNewTcPr();  
	    CTShd ctshd = ctPr.addNewShd();  
	    ctshd.setFill("CCCCCC");  
	    ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);  */
	    
       if(cell.getParagraphs().size()>0){
             p=cell.getParagraphs().get(0);
    }else{
            p=cell.addParagraph();
    }
    XWPFRun pRun=p.createRun();
    pRun.setText(text);
    // pRun.setFontSize(16);
    pRun.setFontFamily("宋体");
    //垂直居中
    cell.setVerticalAlignment(XWPFVertAlign.CENTER);
    //水平居中
    p.setAlignment(ParagraphAlignment.CENTER);
    

 }


   private  ByteArrayInputStream getCompressed( InputStream is ,String fileName)throws IOException{
            byte data[] = new byte[2048];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream( bos );
            BufferedInputStream entryStream = new BufferedInputStream( is, 2048);
            ZipEntry entry = new ZipEntry( fileName +".docx");
            zos.putNextEntry( entry );
            int count;
            while ( ( count = entryStream.read( data, 0, 2048) ) != -1 )
            {
                zos.write( data, 0, count );
            }
            entryStream.close();
            zos.closeEntry();
            zos.close();

            return new ByteArrayInputStream( bos.toByteArray() );
   }
   
   /*** 
   *  跨行合并   
   * @param table 
   * @param col  合并列 
   * @param fromRow 起始行 
   * @param toRow   终止行 
   */  
   private void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {    
          for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {    
              XWPFTableCell cell = table.getRow(rowIndex).getCell(col);    
              if ( rowIndex == fromRow ) {    
                  // The first merged cell is set with RESTART merge value    
                  cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);    
              } else {    
                  // Cells which join (merge) the first one, are set with CONTINUE    
                  cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);    
              }    
          }    
      }  
     
   /*** 
   * 跨列合并  
   * @param table 
   * @param row 所合并的行 
   * @param fromCell  起始列 
   * @param toCell   终止列 
   */  
   private  void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {    
          for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {    
              XWPFTableCell cell = table.getRow(row).getCell(cellIndex);    
              if ( cellIndex == fromCell ) {    
                  // The first merged cell is set with RESTART merge value    
                  cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);    
              } else {    
                  // Cells which join (merge) the first one, are set with CONTINUE    
                  cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);    
              }    
          }    
      }    
        
     
   /*** 
   * 导出word 设置行宽 
   * @param table 
   * @param width 
   */  
   private  void setTableWidth(XWPFTable table,String width){    
           CTTbl ttbl = table.getCTTbl();    
           CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();    
           CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();    
           CTJc cTJc=tblPr.addNewJc();    
           cTJc.setVal(STJc.Enum.forString("center"));    
           tblWidth.setW(new BigInteger(width));    
           tblWidth.setType(STTblWidth.DXA);    
   }

	public void addBody(String content,boolean isCenter) {
		XWPFParagraph paragraph = document.createParagraph();  
		if(isCenter){
			paragraph.setAlignment(ParagraphAlignment.CENTER);  
		}
	    XWPFRun paragraphRun = paragraph.createRun(); 
	    paragraphRun.setText(content);  
	    // paragraphRun.setFontSize(11);
	    paragraphRun.setFontFamily("宋体");
	}  
	
	/**
	 * 增加自定义标题样式。这里用的是stackoverflow的源码
	 * 
	 * @param docxDocument 目标文档
	 * @param strStyleId 样式名称
	 * @param headingLevel 样式级别
	 */
	private static void addCustomHeadingStyle(XWPFDocument docxDocument, String strStyleId, int headingLevel) {

	    CTStyle ctStyle = CTStyle.Factory.newInstance();
	    ctStyle.setStyleId(strStyleId);

	    CTString styleName = CTString.Factory.newInstance();
	    styleName.setVal(strStyleId);
	    ctStyle.setName(styleName);

	    CTDecimalNumber indentNumber = CTDecimalNumber.Factory.newInstance();
	    indentNumber.setVal(BigInteger.valueOf(headingLevel));

	    // lower number > style is more prominent in the formats bar
	    ctStyle.setUiPriority(indentNumber);

	    CTOnOff onoffnull = CTOnOff.Factory.newInstance();
	    ctStyle.setUnhideWhenUsed(onoffnull);

	    // style shows up in the formats bar
	    ctStyle.setQFormat(onoffnull);

	    // style defines a heading of the given level
	    CTPPr ppr = CTPPr.Factory.newInstance();
	    ppr.setOutlineLvl(indentNumber);
	    ctStyle.setPPr(ppr);

	    XWPFStyle style = new XWPFStyle(ctStyle);

	    // is a null op if already defined
	    XWPFStyles styles = docxDocument.createStyles();

	    style.setType(STStyleType.PARAGRAPH);
	    styles.addStyle(style);

	}

    /**
     * 根据指定的参数值、模板，生成 word 文档
     * @param param
     * @throws IOException
     * @throws InvalidFormatException
     */
    public CustomXWPFDocument generateWord(Map<String, Object> param, String exportWay)throws IOException, InvalidFormatException {
        if (param != null && param.size() > 0) {

            //处理段落
            List<XWPFParagraph> paragraphList = document.getParagraphs();
            processParagraphs(paragraphList, param, exportWay);

            //处理表格
            Iterator<XWPFTable> it = document.getTablesIterator();
            while (it.hasNext()) {
                XWPFTable table = it.next();
                List<XWPFTableRow> rows = table.getRows();
                for (XWPFTableRow row : rows) {
                    List<XWPFTableCell> cells = row.getTableCells();
//                    processTables(cells, param);
                    for (XWPFTableCell cell : cells) {
                        List<XWPFParagraph> paragraphListTable =  cell.getParagraphs();
                        processParagraphs(paragraphListTable, param, exportWay);
                    }
                }
            }
        }
        return document;
    }


    /**
     * 处理表格
     *
     * @param cells
     * @param param
     * @throws IOException
     * @throws InvalidFormatException
     */
    public void processTables(List<XWPFTableCell> cells, Map<String, Object> param) throws
            IOException, InvalidFormatException {
        for (int i = 0; i < cells.size(); i++) {
            String text = cells.get(i).getText();
            for (Map.Entry<String, Object> entry : param.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (text.indexOf(key) != -1) {
                    cells.get(i).removeParagraph(0);
                    XWPFParagraph pargraph = cells.get(i).addParagraph();
                    Map pic = (Map) value;
                    String width = String.valueOf(pic.get("width"));
                    String height = String.valueOf(pic.get("height"));
                    String base64 = String.valueOf(pic.get("content"));
                    if (width.equals("null") && height.equals("null")) {
                        AssessTable assessTable = (AssessTable) pic.get("content");
                        addAssessResultTable(assessTable);
                    } else {
                        addPicture(base64, width, height, pargraph);
                    }
                    List<XWPFParagraph> pars = cells.get(i).getParagraphs();
                    for (XWPFParagraph par : pars) {
                        List<XWPFRun> runs = par.getRuns();
                        for (XWPFRun run : runs) {
                            run.removeBreak();
                        }
                    }
                    break;
                }
            }
        }
    }


    /**
     * 处理段落
     *
     * @param paragraphList
     * @param param
     * @throws IOException
     * @throws InvalidFormatException
     */
    public void processParagraphs
    (List<XWPFParagraph> paragraphList, Map<String, Object> param, String exportWay) throws
            IOException, InvalidFormatException {
        if (paragraphList != null && paragraphList.size() > 0) {
            for (int i = 0; i < paragraphList.size(); i++) {
                List<XWPFRun> runs = paragraphList.get(i).getRuns();
                for (int j = 0; j < runs.size(); j++) {
                    String text = paragraphList.get(i).getText();
                    if (text != null) {
                        boolean isSetText = false;
                        for (Map.Entry<String, Object> entry : param.entrySet()) {
                            String key = entry.getKey();
                            if (text.indexOf(key) != -1) {
                                isSetText = true;
                                Object value = entry.getValue();
                                if (value instanceof String) {
                                    // 文本替换
                                    text = text.replace("${" + key + "}", value.toString());
                                    break;
                                } else if (value instanceof Map) {
                                    // 树/图片/表格替换
                                    text = text.replace("${" + key + "}", "");
                                    Map pic = (Map) value;
                                    String width = String.valueOf(pic.get("width"));
                                    String height = String.valueOf(pic.get("height"));
                                    String base64 = String.valueOf(pic.get("content"));
                                    String labelType = String.valueOf(pic.get("labelType"));
                                    // 表格
                                    if (!labelType.equals("null") && "6".equals(labelType)) {
                                        String title = String.valueOf(pic.get("title"));
                                        String[] headers = (java.lang.String[]) pic.get("headers");
                                        List<Map<String, Object>> dataset = (List<Map<java.lang.String, Object>>) pic.get("dataset");
                                        addTableHasMainTitle(title, headers, dataset, paragraphList.get(i), exportWay);
                                    } else {
                                        // 评估结果
                                        if (width.equals("null") && height.equals("null")) {
                                            AssessTable assessTable = (AssessTable) pic.get("content");
                                            // 生成评估结果
                                            templateAddAssessResultTable(assessTable, paragraphList.get(i));
                                            break;
                                        } else {
                                            // 插入图片
                                            addPicture(base64, width, height,paragraphList.get(i));
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        if (isSetText) {
                            runs.get(j).setText(text, 0);
                            if (j+1 < runs.size()) {
                                for (int k = j+1; k<runs.size(); k++) {
                                    runs.get(k).setText("",0);
                                }
                            }
                            break;
                        }

                    }
                }
            }
        }
    }

    /**
     * @param base64Info
     * @param imgWidth
     * @param imgHeight
     * @param paragraph
     * @throws IOException
     * @throws InvalidFormatException
     */
    public void addPicture(String base64Info, String imgWidth, String imgHeight, XWPFParagraph paragraph) throws IOException, InvalidFormatException {
        base64Info = base64Info.replaceAll(" ", "+");
        String pictureInfo = (String) base64Info.split("base64,")[1];

        BASE64Decoder decoder = new BASE64Decoder();
        byte[] a = decoder.decodeBuffer(pictureInfo);// Base64解码
        for (int i = 0; i < a.length; ++i) {
            if (a[i] < 0) {// 调整异常数据
                a[i] += 256;
            }
        }
        String picId1 = document.addPictureData(a, XWPFDocument.PICTURE_TYPE_JPEG);
        document.createPicture(picId1, document.getNextPicNameNumber(XWPFDocument.PICTURE_TYPE_PNG), Integer.parseInt(imgWidth), Integer.parseInt(imgHeight), paragraph);
    }
}