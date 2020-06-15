package com.tf.base.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class FileReadUtils {

    private static   Logger log = LoggerFactory.getLogger(FileReadUtils.class);

	public static List<String> readFileByLines(String fileName) {
		List<String> results = new ArrayList<>();
         File file = new File(fileName);
         BufferedReader reader = null;
         try {
//             System.out.println("以行为单位读取文件内容，一次读一整行：");
             reader = new BufferedReader(new FileReader(file));
             String tempString = null;
             int line = 1;
             // 一次读入一行，直到读入null为文件结束
             while ((tempString = reader.readLine()) != null) {
                 // 显示行号
//                 System.out.println("line " + line + ": " + tempString);
                 results.add(tempString);
                 line++;
             }
             reader.close();

          } catch (IOException e) {
              e.printStackTrace();
          } finally {
              if (reader != null) {
                 try {
                      reader.close();
                  } catch (IOException e1) {
                  }
              }
          }
         return results;
     }


	public static Map<String,List<String>> getColumnsList(List<String> readFileByLines, String mark_split){
	    Map<String,String> nameMap = new HashMap<>();
		Map<String,List<String>> results = new HashMap<>();
		int count =0;
		for (String line : readFileByLines) {
			String[] lineStrs = line.split(mark_split);
			for (int i = 0; i < lineStrs.length; i++) {
			    if(count==0){//处理表头数据
                    List<String> objs = new ArrayList<String>();
                    results.put(lineStrs[i], objs);
                    nameMap.put(String.valueOf(i),lineStrs[i]);
                }else{
			        if(results==null||nameMap==null){
                        log.info("FileReadUtils-->getColumnsList : results 或者 nameMap 为空");
                    }
                    List<String> tmp = results.get(nameMap.get(String.valueOf(i)));
                    if(lineStrs==null||lineStrs[i]==null||tmp==null){
                        log.info("FileReadUtils-->getColumnsList : lineStrs 或者 lineStrs 为空,第" + i +"行");
                    }else {
                        tmp.add(lineStrs[i]);
                        results.put(nameMap.get(String.valueOf(i)), tmp);
                    }
                }
			}
            count++;
		}
		return results;
	}
}
