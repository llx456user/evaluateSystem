package com.tf.base.util;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.ConvertUtils;
import org.docx4j.model.datastorage.XPathEnhancerParser.main_return;

import com.tf.base.common.utils.FileReadUtils;
import com.tf.base.common.utils.ReadExcelUtil;

/**
 * 目前支持xls,.xlsx,csv,txt等其他文件格式，其余的后缀都默认为文本文件格式读取。
 */
public class ParamFileImpl  implements  ParamProcess{
	static Pattern pattern  = Pattern.compile("(-?\\d+\\.?\\d*)[Ee]{1}[\\+-]?[0-9]*");

	private static List<String> sufs_excel = Arrays.asList(".xls",".xlsx");
	private static List<String> sufs_csv = Arrays.asList(".csv");
	private static List<String> sufs_txt = Arrays.asList(".txt");
    private  JSONObject dataJsonObject = new JSONObject();
	static DecimalFormat ds = new DecimalFormat("0");


	private Map<String,List<String>> results = null;

	private static String mark_split = ",";
	private static String mark_tab = "\t";
    /**
     *
     * @param filePath 文件路径
     */
    public ParamFileImpl(String filePath){
    	String suf = filePath.substring(filePath.lastIndexOf("."));

    	if(sufs_excel.contains(suf)){
    		try {
    			List<String> readFileByLines = ReadExcelUtil.readExcel(filePath);

    			for(int i=0;i<readFileByLines.size();i++){
    				String data=readFileByLines.get(i);
    				String s[]=data.split(",");
    				String sd[] =new String[s.length];
					StringBuffer stringBuffer =new StringBuffer();
					String sPhone;
    				for(int j=0;j<s.length;j++) {
						if (isENum(s[j])) {
							sPhone = ds.format(Double.parseDouble(s[j])).trim();
//							String d = s[j] + "," + sPhone

							stringBuffer.append(sPhone+",");

						}else {
							stringBuffer.append(s[j]+",");
						}
						readFileByLines.set(i, stringBuffer.toString());
					}

				}
				results = FileReadUtils.getColumnsList(readFileByLines,mark_split);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}else if(sufs_csv.contains(suf)){
    		List<String> readFileByLines = FileReadUtils.readFileByLines(filePath);
    		results = FileReadUtils.getColumnsList(readFileByLines,mark_split);
    	}else{
    		List<String> readFileByLines = FileReadUtils.readFileByLines(filePath);
    		results = FileReadUtils.getColumnsList(readFileByLines,mark_tab);
    	}
    }

	@Override
	public String[] getColumnsName() {
		return  results.keySet().toArray(new String[results.keySet().size()]);
	}

	@Override
	public int getColumnsCount() {
		return results.keySet().size();
	}

	@Override
	public int[] getColumnsIntValue(String columnName) {
    	List<String> list = results.get(columnName);
    	if(list == null){
    		return new int[0];
    	}
    	int [] arr = new int[list.size()];
    	for (int i = 0; i < list.size(); i++) {
			arr[i] = (int)(Double.parseDouble(ifEmpty(list.get(i))));
		}
    	return  arr;
	}

	@Override
	public long[] getColumnsLongValues(String columnName) {
    	List<String> list = results.get(columnName);
    	if(list == null){
    		return new long[0];
    	}
    	long [] arr = new long[list.size()];
    	for (int i = 0; i < list.size(); i++) {
			arr[i] = (long)Double.parseDouble(ifEmpty(list.get(i)));
		}
    	return  arr;
	}

	@Override
	public double[] getColumnsDoubleValues(String columnName) {
    	List<String> list = results.get(columnName);
    	if(list == null){
    		return new double[0];
    	}
    	double [] arr = new double[list.size()];
    	for (int i = 0; i < list.size(); i++) {
			arr[i] = Double.parseDouble(ifEmpty(list.get(i)));
		}
    	return  arr;
	}

	@Override
	public float[] getColumnsFloatValues(String columnName) {
    	List<String> list = results.get(columnName);
    	if(list == null){
    		return new float[0];
    	}
    	float [] arr = new float[list.size()];
    	for (int i = 0; i < list.size(); i++) {
			arr[i] = Float.parseFloat(ifEmpty(list.get(i)));
		}
    	return  arr;
	}

	@Override
	public JSONObject getDataJSONObject() {
    	//将文件结果转化为json格式输出
		for(String key:results.keySet()){
			dataJsonObject.put(key,JSONArray.parse(JSON.toJSONString(results.get(key))));
		}
		return dataJsonObject;
	}

	private String ifEmpty(Object obj){
    	if(obj == null || obj.toString().trim().equals("")){
    		return "0";
    	}
    	return obj.toString();
    }

    public static void main(String[] args) {
		boolean param = ParamFileImpl.pattern.matcher("6.1988399E7").matches();

//		Map<Integer,List<Object>> results = getColumnsList("d:/11.txt");
//		System.out.println(results.keySet().size());
//

		System.out.println("解析txt前内存:"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/(1024)+"KB");
		ParamFileImpl object = new ParamFileImpl("E:\\性能测试.txt");
		JSONObject json = object.getDataJSONObject();
		System.out.println("解析txt后内存:"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/(1024)+"KB");

//
//		JSONObject object = p.getDataJSONObject();
//		System.out.println(object.toJSONString());
//		int[] intt = p.getColumnsIntValue("p1");
////		for (int i : intt) {
////			System.out.println(i);
////		}
////		double[] doublet = p.getColumnsDoubleValues("p2");
////		for (double d : doublet) {
////			System.out.println(d);
////		}
////		float[] floett = p.getColumnsFloatValues("p3");
////		for (float f : floett) {
////			System.out.println(f);
////		}
	}
	static boolean isENum(String input) {//判断输入字符串是否为科学计数法
    	return pattern.matcher(input).matches();
    }
}
