package com.tf.base.common.utils;

import java.util.HashMap;
import java.util.Map;

public class SmsTest {

	public static void main(String[] args) {
		
		/*dx.sms.sendurl=http://118.145.18.124:8806/sms.aspx
		dx.sms.queryurl=http://118.145.18.124:8806/statusApi.aspx
		dx.sms.userid=657
		dx.sms.account=sjjj
		dx.sms.password=sjjj321*/
		
		String sendurl = "http://118.145.18.124:8806/sms.aspx";
		Map<String, String> map = new HashMap<String, String>();
		map.put("action", "send");
		map.put("userid", "657");
		map.put("account", "sjjj");
		map.put("password", "sjjj321");
		map.put("mobile", "18522765175");
		map.put("content", "你好 测试数据23");
		System.out.println("参数==>" + sendurl + ", " + map.toString());
		// 发送指令
		String xml = HttpUtil.doPost(sendurl, map, "UTF-8");
		
		System.out.println("xml =" + xml);

	}

}
