package com.tf.base.project.ahp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MatixcJsonBean {
    private float[][] matixc_float = null;//浮点型矩阵
    public static void main(String[] args) {
        String jsonstr = "{\"xArray\":[{\"42\":\"1\",\"43\":\"2\",\"44\":\"2\"},{\"42\":\"0.5\",\"43\":\"1\",\"44\":\"2\"},{\"42\":\"0.5\",\"43\":\"0.5\",\"44\":\"1\"}],\"yArray\":[{\"42\":\"1\",\"43\":\"0.5\",\"44\":\"0.5\"},{\"42\":\"2\",\"43\":\"1\",\"44\":\"0.5\"},{\"42\":\"2\",\"43\":\"2\",\"44\":\"1\"}]}";
        JSONObject json = JSONObject.fromObject(jsonstr);
        JSONArray xArray = json.getJSONArray("xArray");
        MatixcJsonBean matixcJsonBean = new MatixcJsonBean(xArray);
        matixcJsonBean.paintMatrix();
//        if (json.size() > 0) {
//            for (int i = 0; i < xArray.size(); i++) {
//                // 遍历 jsonarray 数组，把每一个对象转成 json 对象
//                JSONObject job = xArray.getJSONObject(i);
//              // 得到 每个对象中的属性值
//                Iterator iterator = job.keys();
//                while(iterator.hasNext()){
//                   String  key = (String) iterator.next();
//                   String  value = job.getString(key);
//                    System.out.println(key+"="+value);
//                }
//            }
//        }
    }

    public  MatixcJsonBean(JSONArray xArray){
        buildMatixcFloat(xArray);
    }

    private void buildMatixcFloat(JSONArray xArray) {
        if (xArray.size() > 0) {
            matixc_float= new float[xArray.size()][xArray.size()];
            for (int i = 0; i < xArray.size(); i++) {
                // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                JSONObject jsonObject = xArray.getJSONObject(i);
                // 得到 每个对象中的属性值
                Iterator iterator = jsonObject.keys();
                int colum =0 ;
                while (iterator.hasNext()) {
                    if(colum>=i) {
                        String key = (String) iterator.next();
                        String value = jsonObject.getString(key);
//                    System.out.println(key + "=" + value);
                        matixc_float[i][colum] = getFloatValue(value);
                    }else {
                        matixc_float[i][colum]=1/matixc_float[colum][i];
                        iterator.next();
                    }
                    colum++;
                }
            }
        }
    }

    private  Float getFloatValue(String value){
        if(value.contains("/")){
            String[] dArray = value.split("/");
           return  Float.valueOf(dArray[0].trim())/Float.valueOf(dArray[1].trim()) ;
        }else if(value.contains("\\")){
            String[] dArray = value.split("\\\\");
            return  Float.valueOf(dArray[0].trim())/Float.valueOf(dArray[1].trim()) ;
        }
        return  Float.valueOf(value) ;
    }


    /**
     * 获取数组
     * @return
     */
    public float[][] getMatixcFloat() {
        return matixc_float;
    }

    public void paintMatrix() {//打印出刚输入的矩阵
        for (int i = 0; i < matixc_float.length; i++) {
            for (int j = 0; j < matixc_float.length; j++) {
                if (j == matixc_float.length-1) {
                    System.out.print(matixc_float[i][j]);
                    System.out.println("");
                } else {
                    System.out.print(matixc_float[i][j] + " ");
                }
            }
        }
    }
}
