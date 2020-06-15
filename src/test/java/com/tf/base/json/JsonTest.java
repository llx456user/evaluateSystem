package com.tf.base.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tf.base.go.service.Impl.SortComparator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wanquan on 2018/5/7.
 */
public class JsonTest {
    @Test
    public void testJson(){
        JSONObject object = new JSONObject();
        object.put("p1", "11");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add("1");
        jsonArray.add("2");
        object.put("p2", jsonArray);
        System.out.printf(object.getString("p2"));
        if(object.getString("p2").contains("[")){
            System.out.printf("11");
        }
        if(object.get("p2")instanceof  JSONArray){
            System.out.printf("33");
        }
        if(object.get("p1")instanceof  JSONObject){
            System.out.printf("44");
        }
    }


    @Test
    public void testMarkJson(){
        //构造marker标签
        JSONArray da  = new JSONArray();
        JSONObject jo = new JSONObject();
        jo.put("xAxis",1);
        jo.put("yAxis",1);
        jo.put("value",1);
        da.add(jo);
        JSONObject jo2 = new JSONObject();
        jo2.put("xAxis",3);
        jo2.put("yAxis",3);
        jo2.put("value",1);
        da.add(jo2);
        JSONObject jo3 = new JSONObject();
        jo3.put("xAxis",15);
        jo3.put("yAxis",15);
        jo3.put("value",15);
        da.add(jo3);

        JSONObject jo4 = new JSONObject();
        jo4.put("xAxis",100);
        jo4.put("yAxis",100);
        jo4.put("value",100);
        da.add(jo4);

        JSONObject jo5 = new JSONObject();
        jo5.put("xAxis",50);
        jo5.put("yAxis",50);
        jo5.put("value",50);
        da.add(jo5);

        JSONObject jo6 = new JSONObject();
        jo6.put("xAxis",49);
        jo6.put("yAxis",49);
        jo6.put("value",49);
        da.add(jo6);

        JSONObject jo7 = new JSONObject();
        jo7.put("xAxis",30);
        jo7.put("yAxis",30);
        jo7.put("value",30);
        da.add(jo7);
        JSONArray array = sortJsonArray(da, 10);
        //打印输出
        for(int i=0;i<array.size();i++){
            JSONObject jsonObj = (JSONObject)array.get(i);
            System.out.println(jsonObj.get("xAxis"));
            System.out.println(jsonObj.get("yAxis"));
            if(jsonObj.containsKey("symbolOffset")){
                System.out.println(jsonObj.get("symbolOffset"));
            }
        }
    }

    /**
     * 排序 json，并根据距离分配大小
     * @param da
     * @param width 宽度
     * @return
     */
    private JSONArray sortJsonArray(JSONArray da,double width) {
        //排序
        JSONArray sort_JsonArray = new JSONArray();
        List<JSONObject> list = new ArrayList<JSONObject>();
        JSONObject jsonObj = null;
        for (int i = 0; i < da.size(); i++) {
            jsonObj = (JSONObject) da.get(i);
            list.add(jsonObj);
        }
        Collections.sort(list, new SortComparator());
        for (int i = 0; i < list.size(); i++) {
            jsonObj = list.get(i);
            if(i>0){
                JSONObject preJsonObj = list.get(i - 1);
                double jWidth = jsonObj.getDoubleValue("xAxis") - preJsonObj.getDoubleValue("xAxis");
                if(jWidth<width){
                    if(!preJsonObj.containsKey("symbolOffset")){
                        jsonObj.put("symbolOffset","[0,-40]");
                    }
                }
            }
            sort_JsonArray.add(jsonObj);
        }
        return sort_JsonArray;
    }

}
