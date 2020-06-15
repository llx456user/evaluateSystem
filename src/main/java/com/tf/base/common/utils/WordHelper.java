package com.tf.base.common.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.xml.transform.Templates;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.util.IOUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFComment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.contenttype.ContentType;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.WordprocessingML.AlternativeFormatInputPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.CTAltChunk;
import org.docx4j.wml.Document;
import org.springframework.stereotype.Service;

@Service
public class WordHelper {
	
	private final static String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
	
	public static final int EMU_PER_PIXEL = 9525;
	
	public boolean replaceTxt(String path, String destPath, Map<String, String> map) {
		try {
			FileInputStream fis = new FileInputStream(path);

			XWPFDocument document = new XWPFDocument(fis);

			// 替换段落中的指定文字
			Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
			while (itPara.hasNext()) {
				XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
				List<XWPFRun> runs = paragraph.getRuns();
				for (int i = 0; i < runs.size(); i++) {
					String oneparaString = runs.get(i).getText(runs.get(i).getTextPosition());
					for (Map.Entry<String, String> entry : map.entrySet()) {
						oneparaString = oneparaString.replace(entry.getKey(), entry.getValue());
					}
					runs.get(i).setText(oneparaString, 0);
				}
			}

			// 替换表格中的指定文字
			Iterator<XWPFTable> itTable = document.getTablesIterator();
			while (itTable.hasNext()) {
				XWPFTable table = (XWPFTable) itTable.next();
				int rcount = table.getNumberOfRows();
				for (int i = 0; i < rcount; i++) {
					XWPFTableRow row = table.getRow(i);
					List<XWPFTableCell> cells = row.getTableCells();
					for (XWPFTableCell cell : cells) {
						String cellTextString = cell.getText();
						for (Entry<String, String> e : map.entrySet()) {
							if (cellTextString.contains(e.getKey()))
								cellTextString = cellTextString.replace(e.getKey(), e.getValue());
						}
						cell.removeParagraph(0);
						cell.setText(cellTextString);
					}
				}
			}
			FileOutputStream outStream = null;
			outStream = new FileOutputStream(destPath);
			document.write(outStream);
			outStream.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}
	
	
	public InputStream replaceTxt(String path, Map<String, String> map) {
		
		InputStream target = null;
		
		try {
			FileInputStream fis = new FileInputStream(path);

			XWPFDocument document = new XWPFDocument(fis);

			// 替换段落中的指定文字
			Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
			while (itPara.hasNext()) {
				XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
				List<XWPFRun> runs = paragraph.getRuns();
				for (int i = 0; i < runs.size(); i++) {
					String oneparaString = runs.get(i).getText(runs.get(i).getTextPosition());
					for (Map.Entry<String, String> entry : map.entrySet()) {
						oneparaString = oneparaString.replace(entry.getKey(), entry.getValue());
					}
					runs.get(i).setText(oneparaString, 0);
				}
			}

			// 替换表格中的指定文字
			Iterator<XWPFTable> itTable = document.getTablesIterator();
			while (itTable.hasNext()) {
				XWPFTable table = (XWPFTable) itTable.next();
				int rcount = table.getNumberOfRows();
				for (int i = 0; i < rcount; i++) {
					XWPFTableRow row = table.getRow(i);
					List<XWPFTableCell> cells = row.getTableCells();
					for (XWPFTableCell cell : cells) {
						String cellTextString = cell.getText();
						for (Entry<String, String> e : map.entrySet()) {
							if (cellTextString.contains(e.getKey()))
								cellTextString = cellTextString.replace(e.getKey(), e.getValue());
						}
						cell.removeParagraph(0);
						cell.setText(cellTextString);
					}
				}
			}
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			document.write(outStream);
			
			target = new ByteArrayInputStream(outStream.toByteArray());
			
			outStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return target;

	}
	
	public void replaceTxtWithDocx4j(String path,Map<String, String> map,String outputPath){
		try {
			WordprocessingMLPackage mlPackage = WordprocessingMLPackage.load(new File(path));  
			MainDocumentPart documentPart = mlPackage.getMainDocumentPart();
			org.docx4j.wml.Document wmlDocumentEl = (org.docx4j.wml.Document)documentPart.getJaxbElement();
			String xmlContent = org.docx4j.XmlUtils.marshaltoString(wmlDocumentEl, true);
			for (Entry<String, String> e : map.entrySet()) {
				if (xmlContent.contains(e.getKey()))
					xmlContent = xmlContent.replace(e.getKey(), e.getValue());
			}
			Object obj = XmlUtils.unmarshalString(xmlContent);
			documentPart.setJaxbElement((Document) obj);
			mlPackage.addTargetPart(documentPart);
			mlPackage.save(new java.io.File(outputPath));
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	public InputStream replaceTxtWithDocx4j(String path,Map<String, String> map){
		try {
			WordprocessingMLPackage mlPackage = WordprocessingMLPackage.load(new File(path));  
			MainDocumentPart documentPart = mlPackage.getMainDocumentPart();
			org.docx4j.wml.Document wmlDocumentEl = (org.docx4j.wml.Document)documentPart.getJaxbElement();
			String xmlContent = org.docx4j.XmlUtils.marshaltoString(wmlDocumentEl, true);
			for (Entry<String, String> e : map.entrySet()) {
				if (xmlContent.contains(e.getKey()))
					xmlContent = xmlContent.replace(e.getKey(), e.getValue());
			}
			Object obj = XmlUtils.unmarshalString(xmlContent);
			documentPart.setJaxbElement((Document) obj);
			mlPackage.addTargetPart(documentPart);
			
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			mlPackage.save(outStream);
			
			return new ByteArrayInputStream(outStream.toByteArray());
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}


	public boolean insertImg(String srcPath, String destPath, String imgPath) {

		try {
			FileInputStream fis = new FileInputStream(srcPath);

			XWPFDocument document = new XWPFDocument(fis);

			XWPFParagraph paragraph = document.createParagraph();
			XWPFRun run = paragraph.createRun();

			String imgName = this.getImgName(imgPath);

			int format = this.getFormatBySuffix(imgPath);

			int[] imgWHs = getImgWidthAndHeight(imgPath);
			run.setText(imgName);
			run.addBreak();
			// run.addPicture(new FileInputStream(picPath), format, imgName,
			// Units.toEMU(200), Units.toEMU(200)); // 200x200 pixels
			run.addPicture(new FileInputStream(imgPath), format, imgName, Units.toEMU(imgWHs[0]),
					Units.toEMU(imgWHs[1])); // 200x200 pixels
			run.addBreak(BreakType.PAGE);

			FileOutputStream out = new FileOutputStream(destPath);
			document.write(out);
			out.close();

			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}
	
	public InputStream insertImg(InputStream docxInputStream, String imgPath) {

		InputStream target = null;
		
		try {
			
			XWPFDocument document = new XWPFDocument(docxInputStream);
			
			XWPFParagraph paragraph = document.createParagraph();
			XWPFRun run = paragraph.createRun();

			String imgName = this.getImgName(imgPath);

			int format = this.getFormatBySuffix(imgPath);

			int[] imgWHs = getImgWidthAndHeight(imgPath);
//			run.setText(imgName);
			run.addBreak();
			run.addPicture(new FileInputStream(imgPath), format, imgName, toPIXEL(imgWHs[0]),
					toPIXEL(imgWHs[1])); // 200x200 pixels
			run.addBreak(BreakType.PAGE);

			ByteArrayOutputStream ostream = new ByteArrayOutputStream();
			document.write(ostream);
			
			target = new ByteArrayInputStream(ostream.toByteArray());
			
			ostream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return target;

	}

	

	public void mergeDocx(String src1Path, String src2Path, String destPath) {

		try {

			FileInputStream fis1 = new FileInputStream(src1Path);
			XWPFDocument document = new XWPFDocument(fis1);

			FileInputStream fis2 = new FileInputStream(src2Path);
			XWPFDocument document2 = new XWPFDocument(fis2);
			List<XWPFParagraph> paragraph2s = document2.getParagraphs();

			XWPFWordExtractor docx = new XWPFWordExtractor(POIXMLDocument.openPackage(src2Path));
			// 提取.docx正文文本
			String text = docx.getText();
			System.out.println(text);
			// 提取.docx批注
			XWPFComment[] comments = document2.getComments();
			for (XWPFComment comment : comments) {
				comment.getId();// 提取批注Id
				comment.getAuthor();// 提取批注修改人
				comment.getText();// 提取批注内容
				System.out.println(comment.getId());
				System.out.println(comment.getAuthor());
				System.out.println(comment.getText());
			}

			FileOutputStream out = new FileOutputStream(destPath);
			document.write(out);
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public InputStream mergeDocx(final List<InputStream> streams) throws Docx4JException,IOException {

		WordprocessingMLPackage target = null;
		final File generated = File.createTempFile("generated", ".docx");

		int chunkId = 0;
		Iterator<InputStream> it = streams.iterator();
		while (it.hasNext()) {
			InputStream is = it.next();
			if (is != null) {
				if (target == null) {
					// Copy first (master) document
					OutputStream os = new FileOutputStream(generated);
					os.write(IOUtils.toByteArray(is));
					os.close();

					target = WordprocessingMLPackage.load(generated);
				} else {
					// Attach the others (Alternative input parts)
					insertDocx(target.getMainDocumentPart(), IOUtils.toByteArray(is), chunkId++);
				}
			}
		}

		if (target != null) {
			target.save(generated);
			return new FileInputStream(generated);
		} else {
			return null;
		}
	}
	
	// 插入文档   
	private void insertDocx(MainDocumentPart main, byte[] bytes, int chunkId) {  
	    try {  
	        AlternativeFormatInputPart afiPart = new AlternativeFormatInputPart(  
	                new PartName("/part" + chunkId + ".docx"));  
	        afiPart.setContentType(new ContentType(CONTENT_TYPE));  
	        afiPart.setBinaryData(bytes);  
	        Relationship altChunkRel = main.addTargetPart(afiPart);  
	  
	        CTAltChunk chunk = Context.getWmlObjectFactory().createCTAltChunk();  
	        chunk.setId(altChunkRel.getId());  
	  
	        main.addObject(chunk);  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	}  
	
	
	private int[] getImgWidthAndHeight(String imagePath) {

		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		return new int[] { width, height };
	}

	private String getImgName(String picPath) {

		return picPath.substring(picPath.lastIndexOf("/") + 1);
	}

	private int getFormatBySuffix(String imgFile) {
		int format = -99;
		if (imgFile.endsWith(".emf"))
			format = XWPFDocument.PICTURE_TYPE_EMF;
		else if (imgFile.endsWith(".wmf"))
			format = XWPFDocument.PICTURE_TYPE_WMF;
		else if (imgFile.endsWith(".pict"))
			format = XWPFDocument.PICTURE_TYPE_PICT;
		else if (imgFile.endsWith(".jpeg") || imgFile.endsWith(".jpg"))
			format = XWPFDocument.PICTURE_TYPE_JPEG;
		else if (imgFile.endsWith(".png"))
			format = XWPFDocument.PICTURE_TYPE_PNG;
		else if (imgFile.endsWith(".dib"))
			format = XWPFDocument.PICTURE_TYPE_DIB;
		else if (imgFile.endsWith(".gif"))
			format = XWPFDocument.PICTURE_TYPE_GIF;
		else if (imgFile.endsWith(".tiff"))
			format = XWPFDocument.PICTURE_TYPE_TIFF;
		else if (imgFile.endsWith(".eps"))
			format = XWPFDocument.PICTURE_TYPE_EPS;
		else if (imgFile.endsWith(".bmp"))
			format = XWPFDocument.PICTURE_TYPE_BMP;
		else if (imgFile.endsWith(".wpg"))
			format = XWPFDocument.PICTURE_TYPE_WPG;
		else {
			System.err.println(
					"Unsupported picture: " + imgFile + ". Expected emf|wmf|pict|jpeg|png|dib|gif|tiff|eps|bmp|wpg");
		}

		return format;
	}
	
	public boolean isImgBySuffix(String imgFile){
		
		boolean is = false;
		if (imgFile.endsWith(".jpeg") || imgFile.endsWith(".jpg"))
			is = true;
		else if (imgFile.endsWith(".png"))
			is = true;
		else if (imgFile.endsWith(".gif"))
			is = true;
		else if (imgFile.endsWith(".bmp"))
			is = true;
		
		return is;
	}
	
	public static int toPIXEL(double points){
        return (int)Math.round(EMU_PER_PIXEL*points);
    }

	public static void main(String[] args) {

		
		test3();
	}
	
	
	public static void test1(){
		
		WordHelper helper = new WordHelper();
		Map<String, String> map = new HashMap<>();
		map.put("${orderDate}", "2016-11-24");
		map.put("${channel}", "淘宝");
		
		helper.replaceTxt("d:/渠道赔付单-模板.docx", "d:/aaa.docx", map);
		helper.insertImg("d:/aaa.docx", "d:/aaa.docx", "d:/1.png");
		List<InputStream> streams = new ArrayList<>();
		try {
			FileInputStream fis = new FileInputStream("d:/aaa.docx");
			FileInputStream fis2 = new FileInputStream("d:/渠道赔付单-模板.docx");
			streams.add(fis);
			streams.add(fis2);
			InputStream mergeDocx = helper.mergeDocx(streams);
			FileOutputStream fos = new FileOutputStream("d:/aaa.docx");
			XWPFDocument document = new XWPFDocument(mergeDocx);
			document.write(fos);
		} catch (Docx4JException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	private static void test2(){
		WordHelper helper = new WordHelper();
		Map<String, String> map = new HashMap<>();
		map.put("${orderDate}", "2016-11-24");
		map.put("${channel}", "淘宝");
		
		InputStream inputStream = helper.replaceTxt("d:/渠道赔付单-模板.docx", map);
		InputStream fis = helper.insertImg(inputStream, "d:/1.png");
		List<InputStream> streams = new ArrayList<>();
		try {
			
			FileInputStream fis2 = new FileInputStream("d:/渠道赔付单-模板.docx");
			streams.add(fis);
			streams.add(fis2);
			InputStream mergeDocx = helper.mergeDocx(streams);
			FileOutputStream fos = new FileOutputStream("d:/aaa.docx");
			XWPFDocument document = new XWPFDocument(mergeDocx);
			document.write(fos);
		} catch (Docx4JException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void test3(){
		WordHelper helper = new WordHelper();
		Map<String, String> map = new HashMap<>();
		map.put("orderDate", "2016-11-24");
		map.put("channel", "淘宝");
		
		helper.replaceTxtWithDocx4j("d:/渠道赔付单-模板.docx", map, "d:/ccc.docx");
	}
}
