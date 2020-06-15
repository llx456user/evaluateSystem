/**
 * 
 */
package com.tf.base.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import net.sf.json.JSONObject;

public class ParseUtil {

	public static String readTxtFile(String filePath) {
		StringBuffer str = new StringBuffer();
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					str.append(lineTxt);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return str.toString();

	}

	/**
	 * 简单对编码转换 - 编码
	 * 
	 * @param data
	 * @return
	 */
	public static String encodeData(String data) {
		String strRtn = "";
		if (data == null)
			return strRtn;
		try {
			strRtn = Eryptogram.encryptData2(data);// URLEncoder.encode(data,
													// Constant.ENC_UTF8);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strRtn;
	}

	/**
	 * 简单对编码转换－解编码
	 * 
	 * @param data
	 * @return
	 */
	public static String decodeData(String data) {
		String strRtn = "";
		if (data == null)
			return strRtn;
		try {
			strRtn = Eryptogram.decryptData2(data);// URLDecoder.decode(data,
													// Constant.ENC_UTF8);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strRtn;
	}

	/**
	 * 将json格式的字符串解析成Map对象 <li>
	 * json格式：{"user":"admin","pwd":"pwd","fromCity" :"BJS","toCity":"LAX"}
	 */
	public static HashMap<String, String> toHashMap(Object object) {
		HashMap<String, String> data = new HashMap<String, String>();
		// 将json字符串转换成jsonObject
		JSONObject jsonObject = JSONObject.fromObject(object);
		Iterator<?> it = jsonObject.keys();
		// 遍历jsonObject数据，添加到Map对象
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			String value = String.valueOf(jsonObject.get(key));
			data.put(key.toLowerCase(), value);
		}
		return data;
	}

	/*
	 * 取得hash值
	 */
	public static int getHash(String str) {
		return str.hashCode() & 0x7FFFFFFF;
	}

	/**
	 * 返回本地生成的PNR
	 * 
	 * @param length
	 * @return
	 */
	public static String getLocalPnr(int length) { // length表示生成字符串的长度
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // 生成字符串从此序列中取
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // 生成字符串从此序列中取
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		int number = random.nextInt(base.length());
		sb.append(base.charAt(number));
		for (int i = 0; i < length - 1; i++) {
			int num = random.nextInt(str.length());
			sb.append(str.charAt(num));
		}
		return sb.toString();
	}

	/**
	 * 保留两位小数
	 */
	public static double reserve2Bit(double val) {
		DecimalFormat df = new DecimalFormat("#.00");
		return Double.valueOf(df.format(val));
	}

	public static String getRandomNum(int num) {
		String[] digits = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
		Random rnum = new Random(new Date().getTime());

		for (int i = 0; i < digits.length; i++) {
			int index = Math.abs(rnum.nextInt()) % 10;
			String tmpDigit = digits[index];
			digits[index] = digits[i];
			digits[i] = tmpDigit;
		}

		String returnStr = digits[0];
		for (int i = 1; i < num; i++) {
			returnStr = digits[i] + returnStr;
		}
		return returnStr;
	}

	// lihaichao
	public static boolean isNotNull(Object obj) {
		if (obj != null && !"".equals(obj.toString().trim())) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void main(String[] args) {
		String s = "6605B932A4325F1BE4EF376AB93AD273962019F752B6E1FA188E4F20E804B7DDA0205D3CFBF178AF188E4F20E804B7DDFECFC73A63E5FBA119BEDD769DEFE8992D03EE870D3BC1D4BFDF79B5E276E0069D6BC4F3E1B5E4E46B30F509D587E579BAB5489DA2AAEC6D79B16D4A2EA7064F66D0EA5E8D897614E5DBE324EA321C034E1398A9338EDF884EF80F65A314DE5545CCCF88BC6CED1166D0EA5E8D89761447C19B0AC05D82E94E1398A9338EDF881683F96143AF2DEAEA36766424D9E61E104BB32AEA8BF49E43E84FC73696E60C4E1398A9338EDF88A5478A9F0D82844945CCCF88BC6CED11104BB32AEA8BF49E8975192C92685F094971666FCE2B10D50DD9E5F2CF0D3054DF8D87892853A5E4F1A32AFED2789149918BC663E31FC7D0127C89DC4672F54C2A96AE511F32BE47834534B86E5CDFA4";
		System.out.println(decodeData(s));
	}

}
