package com.tf.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

public class TestFile {

    public static void main(String[] args) {
//        String test1="v1.023432";
//        test1=test1.replaceAll("\\.","");
//        System.out.println(test1+"ceshi");

        File file = new File("D:\\1223_1.js");
        try {
            writeJsFile("12",file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 写入js，覆盖原内容
     * @param content
     * @param fileName
     * @return
     * @throws Exception
     */
    public static boolean writeJsFile(String content, File fileName)throws Exception{
        RandomAccessFile mm=null;
        boolean flag=false;
        FileOutputStream fileOutputStream=null;
        try {
            if(!fileName.exists()){//如果文件不存在，先创建一个
                fileName.createNewFile();
            }
            fileOutputStream = new FileOutputStream(fileName);
            fileOutputStream.write(content.getBytes("utf-8"));
            fileOutputStream.close();
            flag=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
