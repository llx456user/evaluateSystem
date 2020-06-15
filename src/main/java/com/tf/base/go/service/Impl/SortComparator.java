package com.tf.base.go.service.Impl;
import com.alibaba.fastjson.JSONObject;

import java.util.Comparator;

/**
 * @Description
 * @Author 圈哥
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/11/4
 */
 public class SortComparator implements Comparator<JSONObject> {
    @Override
    public int compare(JSONObject o1, JSONObject o2) {
       double d1 =  o1.getDoubleValue("xAxis");
       double d2 = o2.getDoubleValue("xAxis");
       if(d1>d2){
           return  1;
       }else if(d1<d2){
           return  -1;
       }
        return 0;
    }
}
