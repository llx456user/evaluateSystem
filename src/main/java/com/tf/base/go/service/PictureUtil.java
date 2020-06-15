package com.tf.base.go.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class PictureUtil {


    // 自定义颜色40组
   private static String[] colors = new String[] {"#FF0000","#660033","#663366","#FF33FF","#330066","#3300CC","#FF9999","#FFCCFF","#6666CC","#663333",
            "#FFFF33","#999900","#99CC33","#336600","#33FF33","#99CC99","#00FFFF","#009999","#003366","#000000",
            "#FF5555","#335511","#331133","#FF11FF","#115533","#1155CC","#FF4444","#CCFFFF","#3333CC","#331111",
            "#FFFF11","#555544","#33CC11","#113355","#11FF11","#44CC44","#55FFFF","#554444","#551133","#555555"};

    /**
     * 获取曲线图的样式
     * @param value
     * @param cIndex
     * @param name
     * @return
     */
    public static  JSONObject getLineJson (JSONArray value, int cIndex, String name, boolean smooth){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("symbol","circle");
        jsonObject.put("symbolSize",3);
        jsonObject.put("type","line");
        if(smooth){
            jsonObject.put("smooth",smooth);
        }
        jsonObject.put("sampling","average");//
        jsonObject.put("name",name);
        jsonObject.put("data",value);
        jsonObject.put("showSymbol",true);
        jsonObject.put("hoverAnimation",false);
        JSONObject  itemStyle =  new JSONObject();
        JSONObject  normal =  new JSONObject();
        JSONObject  lineStyle =  new JSONObject();
        int index;
        if (cIndex > 39){
            index = cIndex % 40;
        }else {
            index= cIndex;
        }
        lineStyle.put("color",colors[index]);
        normal.put("color",colors[index]);
        normal.put("lineStyle",lineStyle);
        itemStyle.put("normal",normal);
        jsonObject.put("itemStyle",itemStyle);
        return  jsonObject ;
    }

    public static JSONObject getScatterJson (JSONArray value,int cIndex,String name,JSONObject mark){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("symbol","circle");
        jsonObject.put("name",name);
        jsonObject.put("smooth",true);
        jsonObject.put("symbolSize",3);
        jsonObject.put("showSymbol",true);
        jsonObject.put("hoverAnimation",false);
        jsonObject.put("large",true);
        jsonObject.put("largeThreshold",10000);
        jsonObject.put("type","scatter");
        jsonObject.put("data",value);
        if(mark.containsKey("data")){
            jsonObject.put("markPoint",mark);
        }
        int index;
        if (cIndex > 39){
            index = cIndex % 40;
        }else {
            index= cIndex;
        }
        jsonObject.put("color",colors[index]);
        return  jsonObject ;
    }

}
