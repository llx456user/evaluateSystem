package com.tf.base.jni;


import com.tf.base.common.domain.ModelParmeter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InParamterBuilder {

    private JSONObject dataJson = null;

    //保存内容为结构体类型变量名称和结构体成员列表
    Map<String, List<ModelParmeter>> structInfo = null;

    /**
     * JSON格式作为输入
     */
    public InParamterBuilder() {
        //测试使用
        this.dataJson = initBasicJson();
    }

    /**
     *
     */
    public void init(JSONObject dataJson) {
        this.dataJson = dataJson;
    }

    /**
     * 初始化结构体
     * @param modelParmeterList
     */
    public void initStruct(List<ModelParmeter> modelParmeterList) {
        structInfo = new HashedMap();
        for (int i = 0; i < modelParmeterList.size(); i++) {
            ModelParmeter param = modelParmeterList.get(i);
            //判断类型是否为结构体
            if (0 == param.getInoutType() && !ParameterTypeUtil.isBasicType(param.getParmeterType())) {
                structInfo.put(param.getParmeterName(), new ArrayList());
                for (int j = 0; j < modelParmeterList.size(); j++) {
                    ModelParmeter struct = modelParmeterList.get(j);
                    //判断结构体的成员变量
                    if (struct.getParmeterType().equals("struct") && param.getParmeterType().equals(struct.getParmeterName())) {
                        for (int k = 0; k < modelParmeterList.size(); k++) {
                            ModelParmeter member = modelParmeterList.get(k);
                            if (member.getParentId() == struct.getId()) {
                                structInfo.get(param.getParmeterName()).add(member);
                            }
                        }
                        break;
                    }
                }
            }
        }
    }




    public JSONObject getParmeterData(JSONObject object,ModelParmeter parmeter) {
//        JSONObject object = new JSONObject();
        if (parmeter.getParmeterUnit().equals("not_array")) {//1、非数组
            //1.1如果是基本类型,则直接获取
            if (ParameterTypeUtil.isBasicType(parmeter.getParmeterType())) {
                return putBasicTypeParmeterData(object,parmeter);
            } else {//1.2结构体

            }
        } else {//2、数据
            //2.1基本类型数组
            if (ParameterTypeUtil.isBasicType(parmeter.getParmeterType())) {
                object.put(parmeter.getParmeterName(), getBasicTypeParmeterArrayData(parmeter));
                return object;
            } else {//2.2 结构体数组

            }
        }
        return null;
    }


    /**
     * 根据参数名获取参数数据
     *
     * @param parmeter
     * @return
     */
    private JSONObject putBasicTypeParmeterData(JSONObject object,ModelParmeter parmeter) {
//        JSONObject object = new JSONObject();
        if (parmeter.getParmeterType().equalsIgnoreCase("int")) {
            object.put(parmeter.getParmeterName(), this.dataJson.getInt(parmeter.getParmeterName()));
        } else if (parmeter.getParmeterType().equalsIgnoreCase("float")) {
            object.put(parmeter.getParmeterName(), this.dataJson.getDouble(parmeter.getParmeterName()));
        } else if (parmeter.getParmeterType().equalsIgnoreCase("long")) {
            object.put(parmeter.getParmeterName(), this.dataJson.getLong(parmeter.getParmeterName()));
        } else if (parmeter.getParmeterType().equalsIgnoreCase("double")) {
            object.put(parmeter.getParmeterName(), this.dataJson.getDouble(parmeter.getParmeterName()));
        } else {
            object.put(parmeter.getParmeterName(), this.dataJson.getString(parmeter.getParmeterName()));
        }
        return object;
    }

    /**
     * 获取基本类型数组
     *
     * @param parmeter
     * @return
     */
    private JSONArray getBasicTypeParmeterArrayData(ModelParmeter parmeter) {
        //注意，同一个输入，变量的命名不同,因为是数组，必须以*号开头
        JSONArray jsonArray = dataJson.getJSONArray(parmeter.getParmeterName().substring(1));
        return jsonArray;
    }


    /**
     * 获取结构体数据
     *
     * @param parmeter
     * @return
     */
    public JSONObject getStructParmeterData(ModelParmeter parmeter) {
        JSONObject result = new JSONObject();
        JSONObject structJson = new JSONObject();
        //获取结构体的数据
        JSONObject structData = null ;
        List<ModelParmeter> memList = structInfo.get(parmeter.getParmeterName());
        for(ModelParmeter memP : memList){
            if (parmeter.getParmeterUnit().equals("not_array")) {//1、非数组
                if(ParameterTypeUtil.isBasicType(memP.getParmeterType())){
                    putStructBasicData(structJson,structData,memP.getParmeterType(),memP.getParmeterName());
                }else{//结构体

                }
            }else {//数组
                if(ParameterTypeUtil.isBasicType(memP.getParmeterType())){//基本类型数组
                    structJson.put(memP.getParmeterName().substring(1),structData.getJSONArray(memP.getParmeterName().substring(1)));
                }else{//结构体数组

                }
            }
        }
        result.put(parmeter.getParmeterName(),structJson);
        return result;
    }


    /**
     * 设置基本类型
     * @param object 源json
     * @param objectData 数据json
     * @param type  类型
     * @param key  主键
     */
    private  void  putStructBasicData(JSONObject object ,JSONObject objectData,String type,String key){
        if (type.equalsIgnoreCase("int")) {
            object.put(key, objectData.getInt(key));
        }
        else if (type.equalsIgnoreCase("float")) {
            object.put(key, objectData.getDouble(key));
        }
        else if (type.equalsIgnoreCase("long")) {
            object.put(key, objectData.getLong(key));
        }
        else if (type.equalsIgnoreCase("double")){
            object.put(key, objectData.getDouble(key));
        }
        else {
            object.put(key, objectData.getString(key));
        }
    }



    /**
     * @param parmeterName
     * @return
     */
    public String getStructArrayParmeterData(String parmeterName) {
        return null;

    }


    //测试使用
    private JSONObject initBasicJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("p1", 10);
        jsonObject.put("p2", 11);
        return jsonObject;
    }
}
