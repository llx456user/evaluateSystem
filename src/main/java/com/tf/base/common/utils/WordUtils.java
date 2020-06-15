package com.tf.base.common.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class WordUtils {
	
	public static final String version_2003 = "2003";
	public static final String version_2007 = "2007";

	/**
	 * 
	 * @param destFile
	 * @param fileCon
	 */
	@Deprecated
	public static void exportDoc(String destFile, String fileContent) {
		try {
			// doc content
			ByteArrayInputStream bais = new ByteArrayInputStream(fileContent.getBytes());
			POIFSFileSystem fs = new POIFSFileSystem();
			DirectoryEntry directory = fs.getRoot();
			directory.createDocument("WordDocument", bais);
			FileOutputStream ostream = new FileOutputStream(destFile);
			fs.writeFilesystem(ostream);
			bais.close();
			ostream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出Word文档
	 * @param destFile  目标文件
	 * @param fileContent  Word内容
	 */
	public static void exportDocx(String destFile,String fileContent){
		 
		exportDocx(destFile, fileContent, 14);
	}
	
	/**
	 * 导出Word文档
	 * @param destFile  目标文件
	 * @param fileContent  Word内容
	 * @param FontSize  Word内容字体大小
	 */
	public static void exportDocx(String destFile,String fileContent,int FontSize){
		XWPFDocument docx = new XWPFDocument();
        XWPFParagraph par = docx.createParagraph();
        XWPFRun run = par.createRun();
        run.setText(fileContent);
        run.setFontSize(FontSize);
//        InputStream pic = new FileInputStream("C:\\Users\\amitabh\\Pictures\\pics\\pool.jpg");
//        byte [] picbytes = IOUtils.toByteArray(pic);
//        docx.addPicture(picbytes, Document.PICTURE_TYPE_JPEG);

        FileOutputStream out;
		try {
			out = new FileOutputStream(destFile);
			docx.write(out); 
	        out.close(); 
//	        pic.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	/**
	 * 导出Word文档
	 * @param destFile  目标文件
	 * @param fileContent  Word内容
	 * @param FontSize  Word内容字体大小
	 */
	public static void exportDocx(String destFile,String fileContent,int FontSize,String picPath){
		XWPFDocument docx = new XWPFDocument();
		XWPFParagraph par = docx.createParagraph();
		XWPFRun run = par.createRun();
		run.setText(fileContent);
		run.setFontSize(FontSize);
//        InputStream pic = new FileInputStream("C:\\Users\\amitabh\\Pictures\\pics\\pool.jpg");
//        byte [] picbytes = IOUtils.toByteArray(pic);
		
		FileOutputStream out;
		try {
			InputStream pic = new FileInputStream(picPath);
			 byte [] picbytes = IOUtils.toByteArray(pic);
			docx.addPictureData(picbytes, Document.PICTURE_TYPE_PNG);
			out = new FileOutputStream(destFile);
			docx.write(out); 
			out.close(); 
	        pic.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * 读取 Word内容 2003 2007
	 * @param destFile
	 * @return
	 */
	public static String readWord(String destFile) {
         try {
        	 
        	 String version  = "";
        	 if(destFile.endsWith(".doc")){
        		 version = version_2003;
        	 }else if(destFile.endsWith(".docx")){
        		 version = version_2007;
        	 }
        	 
        	 if(version.equals(version_2003)){
        		 InputStream is = new FileInputStream(new File(destFile));
                 WordExtractor ex = new WordExtractor(is);
                 String text2003 = ex.getText();
                 return text2003;
        	 }else
        	 if(version.equals(version_2007)){
 
	             OPCPackage opcPackage = POIXMLDocument.openPackage(destFile);
	             POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
	             String text2007 = extractor.getText();
	             return text2007;
        	 }else{
        		 return "版本检测出现问题.";
        	 }
        	 
         } catch (Exception e) {
             e.printStackTrace();
             return "读取word出现异常.";
         }
         
     }

	/**
	 * 读取word模板并替换变量
	 * 
	 * @param srcPath
	 * @param map
	 * @return
	 */
	public static HWPFDocument replaceDoc(String srcPath, Map<String, String> map) {
		try {
			// 读取word模板
			FileInputStream fis = new FileInputStream(new File(srcPath));
			HWPFDocument doc = new HWPFDocument(fis);
			// 读取word文本内容
			Range bodyRange = doc.getRange();
			// 替换文本内容
			for (Map.Entry<String, String> entry : map.entrySet()) {
				bodyRange.replaceText("${" + entry.getKey() + "}", entry.getValue());
			}
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static void main(String[] args) {
		File f = new File("d:/t.docx");
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		exportDocx("d:/t.docx", "123456");
		System.out.println(readWord("c:/work/test/t.docx"));;
		
	}
}
