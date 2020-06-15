package com.tf.base.util;

import com.tf.base.project.controller.CustomXWPFDocument;
import com.tf.base.project.controller.WordBean;
import com.tf.base.project.domain.AssessTable;
import com.tf.base.project.domain.WordPicture;
import com.tf.base.project.domain.WordTable;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 根据数据库类型获取
 */
public class WordTemplateImpl {
    /**
     * 正则匹配字符串
     *
     * @param str
     * @return
     */
    private Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    /**
     * 文本替换
     *
     * @param param
     * @param object
     * @param label
     * @return
     */
    public Map<String, Object> textReplace(Map<String, Object> param, Object object, String label) {
        param.put(label, object.toString().replaceAll("\r|\n|\t", "").trim());
        return param;
    }


    /**
     * 树替换
     *
     * @param param
     * @param object
     * @param label
     * @return
     */
    public Map<String, Object> tree2Replace(Map<String, Object> param, Object object, String label) {
        Map<String, Object> treeTag = new HashMap<String, Object>();
        if (object instanceof WordPicture) {
            WordPicture wordPicture = (WordPicture) object;
            if (wordPicture != null && !"".equals(wordPicture)) {
                treeTag.put("width", String.valueOf(wordPicture.getWeight()));
                treeTag.put("height", String.valueOf(wordPicture.getHeight()));
                treeTag.put("content", String.valueOf(wordPicture.getBase64()));
                treeTag.put("labelType", 2);
                param.put(label, treeTag);
            } else {
                param.put(label, object.toString().trim());
            }
        } else {
            param.put(label, object.toString().trim());
        }

        return param;
    }

    /**
     * 树替换
     *
     * @param param
     * @param assessTable
     * @param label
     * @return
     */
    public Map<String, Object> tree3Replace(Map<String, Object> param, AssessTable assessTable, String label) {
        Map<String, Object> treeTag = new HashMap<String, Object>();
        treeTag.put("content", assessTable);
        treeTag.put("labelType", 3);
        param.put(label, treeTag);
        return param;
    }

    /**
     * 数值替换
     *
     * @param param
     * @param object
     * @param label
     * @return
     */
    public Map<String, Object> numberReplace(Map<String, Object> param, Object object, String label) {
        param.put(label, object.toString().replaceAll("\r|\n|\t", "").trim());
        return param;
    }

    /**
     * 图片替换
     *
     * @param param
     * @param object
     * @param label
     * @return
     */
    public Map<String, Object> pictureReplace(Map<String, Object> param, Object object, String label) {
        Map<String, Object> treeTag = new HashMap<String, Object>();
        if (object instanceof WordPicture) {
            WordPicture wordPicture = (WordPicture) object;
            if (wordPicture != null && !"".equals(wordPicture)) {
                treeTag.put("width", String.valueOf(wordPicture.getWeight()));
                treeTag.put("height", String.valueOf(wordPicture.getHeight()));
                treeTag.put("content", String.valueOf(wordPicture.getBase64()));
                treeTag.put("labelType", 5);
                param.put(label, treeTag);
            } else {
                param.put(label, "");
            }
        } else {
            param.put(label, object.toString().trim());
        }

        return param;
    }

    /**
     * 表格替换
     *
     * @param param
     * @param object
     * @param label
     * @return
     */
    public Map<String, Object> tableReplace(Map<String, Object> param, Object object, String label) {
        Map<String, Object> tableTag = new HashMap<String, Object>();
        if (object instanceof WordTable){
            WordTable wordTable = (WordTable) object;
            if (wordTable != null && !"".equals(wordTable)) {
                tableTag.put("title", String.valueOf(wordTable.getTitle()));
                tableTag.put("headers", wordTable.getHeaders());
                tableTag.put("dataset", wordTable.getDataset());
                tableTag.put("labelType", 6);
                param.put(label, tableTag);
            } else {
                param.put(label, "");
            }
        } else {
            param.put(label, object.toString().trim());
        }

        return param;
    }

    /**
     * 获取标签
     *
     * @param filePath
     * @return
     */
    public Map<String, String> getTagInfo(String filePath) {
        InputStream is = null;
        Map<String, String> map = new HashMap<>();
        try {
            is = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        XWPFDocument doc = null;
        try {
            doc = new XWPFDocument(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<XWPFParagraph> paragraphList = doc.getParagraphs();
        if (paragraphList != null && paragraphList.size() > 0) {
            for (XWPFParagraph paragraph : paragraphList) {
                List<XWPFRun> runs = paragraph.getRuns();
                if (matcher(paragraph.getText()).find()) {
                    String text = paragraph.getText();
                    Integer index = text.indexOf("$");
                    String tagText = text.substring(index + 1);
                    System.out.println("tagText -------" + tagText);// 文本标签
                    System.out.println("paragraphTag -------" + paragraph.getText());// 文本标签
                    map.put(tagText.replaceAll("}", "").replaceAll("\\{", "").replaceAll("\\$", "").trim(), paragraph.getText());
                }
            }

        }
        //处理表格
        Iterator<XWPFTable> it = doc.getTablesIterator();
        while (it.hasNext()) {
            XWPFTable table = it.next();
            List<XWPFTableRow> rows = table.getRows();
            for (XWPFTableRow row : rows) {
                List<XWPFTableCell> cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
//                    List<XWPFParagraph> paragraphListTable =  cell.getParagraphs();
                    if (matcher(cell.getText()).find()) {
                        String text = cell.getText();
                        Integer index = text.indexOf("$");
                        String tagText = text.substring(index + 1);
                        System.out.println("tableTag -------" + cell.getText());// 表格标签
                        map.put(tagText.replaceAll("}", "").replaceAll("\\{", "").replaceAll("\\$", "").trim(), cell.getText());
                    }
                }
            }
        }
        return map;
    }

}
