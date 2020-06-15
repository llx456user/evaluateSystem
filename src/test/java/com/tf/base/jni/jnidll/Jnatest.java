package com.tf.base.jni.jnidll;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tf.base.jni.JnaResult;
import com.tf.base.jni.JnaUtil;
import org.junit.Test;


public class Jnatest {
    @Test
    public void testJNA() {
        JSONObject object = new JSONObject();
        JSONArray jsonArray1 = new JSONArray();
        JSONArray jsonArray2 = new JSONArray();
        JSONArray jsonArray3 = new JSONArray();
        JSONArray jsonArray4 = new JSONArray();
        JSONArray jsonArray5 = new JSONArray();
        JSONArray jsonArray6 = new JSONArray();
        JSONArray jsonArray7 = new JSONArray();
        JSONArray jsonArray8 = new JSONArray();
        JSONArray jsonArray9 = new JSONArray();
        JSONArray jsonArray10 = new JSONArray();
        for (int i = 1; i < 100000; i++) {
            jsonArray1.add(i);
            jsonArray2.add(i);
            jsonArray3.add(i);
            jsonArray4.add(i);
            jsonArray5.add(i);
            jsonArray6.add(i);
            jsonArray7.add(i);
            jsonArray8.add(i);
            jsonArray9.add(i);
            jsonArray10.add(i);
        }


        object.put("inParam1", jsonArray1);
        object.put("inParam2", jsonArray2);
        object.put("inParam3", jsonArray3);
        object.put("inParam4", jsonArray4);
        object.put("inParam5", jsonArray5);
        object.put("inParam6", jsonArray6);
        object.put("inParam7", jsonArray7);
        object.put("inParam8", jsonArray8);
        object.put("inParam9", jsonArray9);
        object.put("inParam10", jsonArray10);
        String input = object.toJSONString();
        System.out.println(input);
        String dllPath = "E:\\dllworkspace\\bigdata10\\x64\\Debug\\bigdata10.dll";
        long l = System.currentTimeMillis();
        System.out.printf(""+System.currentTimeMillis());
        JnaResult result = JnaUtil.callDllFun(dllPath, input);
        System.out.printf(result.getMsg());
        System.out.printf("输出结果码为：" + result.getResultcode());
        System.out.printf("时间间隔"+(System.currentTimeMillis()-l));
    }

}
