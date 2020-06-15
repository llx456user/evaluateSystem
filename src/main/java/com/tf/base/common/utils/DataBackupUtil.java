package com.tf.base.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tf.base.common.constants.CommonConstants;

public class DataBackupUtil {
	

    public static boolean exportDatabaseTool(String hostIP, String userName, String password, String savePath, String fileName, String databaseName,String dervierPath) throws InterruptedException { 
	          
    	  File saveFile = new File(savePath);  
          if (!saveFile.exists()) {// 如果目录不存在  
              saveFile.mkdirs();// 创建文件夹  
          }  
          if(!savePath.endsWith(File.separator)){  
              savePath = savePath + File.separator;  
          }  
            
          PrintWriter printWriter = null;  
          BufferedReader bufferedReader = null;  
          try {  
              printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(savePath + fileName), "utf8"));
              Process process = Runtime.getRuntime().exec(dervierPath + "mysqldump -h" + hostIP + " -u" + userName + " -p" + password + " --set-charset=UTF8 " + databaseName);  
              InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), "utf8");  
              bufferedReader = new BufferedReader(inputStreamReader);  
              String line;  
              while((line = bufferedReader.readLine())!= null){  
                  printWriter.println(line);  
              }  
              printWriter.flush();  
              if(process.waitFor() == 0){//0 表示线程正常终止。  
                  return true;  
              }  
          }catch (IOException e) {  
              e.printStackTrace();  
          } finally {  
              try {  
                  if (bufferedReader != null) {  
                      bufferedReader.close();  
                  }  
                  if (printWriter != null) {  
                      printWriter.close();  
                  }  
              } catch (IOException e) {  
                  e.printStackTrace();  
              }  
          }  
          return false;
     }
}
