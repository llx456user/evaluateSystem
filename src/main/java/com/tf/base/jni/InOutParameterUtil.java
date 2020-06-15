package com.tf.base.jni;


import com.tf.base.common.domain.ModelInfo;
import com.tf.base.common.domain.ModelParmeter;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InOutParameterUtil {

    public static void main(String[] args) {

        ModelInfo modelInfo = new ModelInfo();
        modelInfo.setId(3018);
        modelInfo.setModelName("wangmw_test1");
        modelInfo.setModelVersion("1.0");
        modelInfo.setModelContent("1");
        modelInfo.setModelCategoryid(109);

        List<ModelParmeter> list = new ArrayList<>();
        ModelParmeter modelParmeter1= new ModelParmeter();
        modelParmeter1.setId(3034);
        modelParmeter1.setModelId(3018);
        modelParmeter1.setParmeterName("p1");
        modelParmeter1.setParmeterUnit("not_array");
        modelParmeter1.setParmeterType("int");
        modelParmeter1.setInoutType(0);
        modelParmeter1.setParentId(0);
        list.add(modelParmeter1);

        ModelParmeter modelParmeter2= new ModelParmeter();
        modelParmeter2.setId(3035);
        modelParmeter2.setModelId(3018);
        modelParmeter2.setParmeterName("p2");
        modelParmeter2.setParmeterUnit("not_array");
        modelParmeter2.setParmeterType("long");
        modelParmeter2.setInoutType(0);
        modelParmeter2.setParentId(0);
        list.add(modelParmeter2);

        ModelParmeter modelParmeter3= new ModelParmeter();
        modelParmeter3.setId(3036);
        modelParmeter3.setModelId(3018);
        modelParmeter3.setParmeterName("o1");
        modelParmeter3.setParmeterUnit("not_array");
        modelParmeter3.setParmeterType("long");
        modelParmeter3.setInoutType(1);
        modelParmeter3.setParentId(0);
        list.add(modelParmeter3);
        InOutParameterUtil util  = new InOutParameterUtil();
        JSONObject object = util.getModelInput(modelInfo, list);
        System.out.printf(object.toString());
//        ModelParmeter modelParmeter3= new ModelParmeter();
//        modelParmeter3.setId(3036);
//        modelParmeter3.setModelId(3018);
//        modelParmeter3.setParmeterName("cc1");
//        modelParmeter3.setParmeterUnit("not_array");
//        modelParmeter3.setParmeterType("struct_int");
//        modelParmeter3.setInoutType(0);
//        modelParmeter3.setParentId(0);
//        list.add(modelParmeter3);
//        ModelParmeter modelParmeter4= new ModelParmeter();
//        modelParmeter4.setId(3037);
//        modelParmeter4.setModelId(3018);
//        modelParmeter4.setParmeterName("struct_int");
//        modelParmeter4.setParmeterUnit("not_array");
//        modelParmeter4.setParmeterType("struct");
//        modelParmeter4.setInoutType(2);
//        modelParmeter4.setParentId(0);
//        list.add(modelParmeter4);
//        ModelParmeter modelParmeter5= new ModelParmeter();
//        modelParmeter5.setId(3038);
//        modelParmeter5.setModelId(3018);
//        modelParmeter5.setParmeterName("cc2");
//        modelParmeter5.setParmeterUnit("not_array");
//        modelParmeter5.setParmeterType("struct_int");
//        modelParmeter5.setInoutType(0);
//        modelParmeter5.setParentId(0);
//        list.add(modelParmeter5);
//        ModelParmeter modelParmeter6= new ModelParmeter();
//        modelParmeter6.setId(3039);
//        modelParmeter6.setModelId(3018);
//        modelParmeter6.setParmeterName("c1");
//        modelParmeter6.setParmeterUnit("not_array");
//        modelParmeter6.setParmeterType("int");
//        modelParmeter6.setParentId(3037);
//        modelParmeter6.setInoutType(2);
//        list.add(modelParmeter6);
//        ModelParmeter modelParmeter7= new ModelParmeter();
//        modelParmeter7.setId(3040);
//        modelParmeter7.setModelId(3018);
//        modelParmeter7.setParmeterName("c2");
//        modelParmeter7.setParmeterUnit("not_array");
//        modelParmeter7.setParmeterType("int");
//        modelParmeter7.setParentId(3037);
//        modelParmeter7.setInoutType(2);
//        list.add(modelParmeter7);

    }


    /**
     * 获取模型输入参数
     * @return
     */
    public JSONObject getModelInput(ModelInfo modelInfo,List<ModelParmeter> params){
        InParamterBuilder inParamterBuilder = new InParamterBuilder();
        JSONObject result = new JSONObject();
//        //获取模型参数
//        List<ModelParmeter>  params = null ;
        //包含数据
        if(isContainArray(params)){

        }else {//不包含数组
//            JSONArray jsonInData = new JSONArray();
            //取得模型的入参信息
            for(ModelParmeter parmeter:params){
                if(parmeter.getInoutType()==0){//表示入参类型
                    result=  inParamterBuilder.getParmeterData(result,parmeter);
//                    jsonInData.add(object);
                }
            }
//            //基本类型输入参数
//            if(jsonInData.size()>0){
//                result.put("normal", jsonInData);
//            }
        }
        return  result ;
    }







    /**
     * 判断是否包含数组
     * @param parmeters
     * @return
     */
    public boolean isContainArray(List<ModelParmeter> parmeters){
        //判断是否包括数组参数
        for (int i = 0; i < parmeters.size(); i++) {
            ModelParmeter param = parmeters.get(i);
            if (0 == param.getInoutType() && !param.getParmeterUnit().equals("not_array")) {
                return  true;
            }
        }
        return  false ;
    }



    /**
     * 插入参数
     * @param object
     * @param key
     * @param type
     * @param data
     */
    public void putData(JSONObject object, String key, String type, String data) {
        if (type.equalsIgnoreCase("int")) {
            object.put(key, Integer.valueOf(data));
        }
        else if (type.equalsIgnoreCase("float")) {
            object.put(key, Float.valueOf(data));
        }
        else if (type.equalsIgnoreCase("long")) {
            object.put(key, Long.valueOf(data));
        }
        else if (type.equalsIgnoreCase("double")){
            object.put(key, Double.valueOf(data));
        }
        else {
            object.put(key, data);
        }
    }

}
