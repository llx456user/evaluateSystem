package com.tf.base.jni;

import com.tf.base.common.utils.ApplicationProperties;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description
 * @Author 圈哥
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/3/7
 */
public class JnaFileUtil {

    public static String[] getDllInputFilaPath(){
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        ApplicationProperties app = new ApplicationProperties();
        String filePath = app.getValueByKey("file.upload.path") +"dllfile"+File.separator+ dateFormat.format(now) + File.separator;
        File folder = new File(filePath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
       return new  String [] {filePath +now.getTime() + ".txt",filePath +now.getTime() + "_out.txt"};
    }



    public static boolean writeDllInputParamter(String inputParamter,String inputFilePath){
//        String fileName=getFileName();
//        System.out.println(fileName);
        File file = new File(inputFilePath);
        boolean flag = false;
        FileOutputStream fileOutputStream = null;
        try {
            if (!file.exists()) {//如果文件不存在，先创建一个
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(inputFilePath);
            fileOutputStream.write(inputParamter.getBytes("utf-8"));
            fileOutputStream.close();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }




}
