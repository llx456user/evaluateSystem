package com.tf.base.platform.service;

import com.tf.base.common.domain.ModelParmeter;
import com.tf.base.common.domain.StructParmeter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanquan on 2018/5/4.
 */
public class ModelParmeterTool {
    private  List<ModelParmeter> parmeterList ;
    private  List<ModelParmeter> inputParmeter ;
    private  List<ModelParmeter> outputParmeter ;
    private  List<ModelParmeter> struct ;
    private Map<String,List<ModelParmeter>> structParmeterMap ;
    private  Map<String, List<StructParmeter>> sMap ;
    public ModelParmeterTool(List<ModelParmeter> parmeterList,Map<String, List<StructParmeter>> sMap){
        this.parmeterList=parmeterList;
        this.sMap=sMap;
        build();
    }

    /**
     * 构造
     */
    private  void build(){
        inputParmeter = new ArrayList<>();
        outputParmeter = new ArrayList<>();
        struct = new ArrayList<>();
        structParmeterMap = new HashMap<>();
        for(ModelParmeter modelParmeter: parmeterList){
            if(modelParmeter.getInoutType()==0){//输入参数
                setParam(modelParmeter, inputParmeter);
            }else if(modelParmeter.getInoutType()==1){
                setParam(modelParmeter, outputParmeter);
            }

//            else if(modelParmeter.getInoutType()==2){
//                if("struct".equals(modelParmeter.getParmeterType())){
//                    struct.add(modelParmeter);
//
//                }else {
//                    if(structParmeterMap.containsKey(modelParmeter.getParentId())){
//                        List<ModelParmeter> list = structParmeterMap.get(modelParmeter.getParentId());
//                        list.add(modelParmeter);
//                        structParmeterMap.put(modelParmeter.getParentId(), list) ;
//                    }else {
//                        List<ModelParmeter> list = new ArrayList<>();
//                        list.add(modelParmeter);
//                        structParmeterMap.put(modelParmeter.getParentId(), list) ;
//                    }
//                }
//            }
        }
    }

    private void setParam(ModelParmeter modelParmeter, List<ModelParmeter> outputParmeter) {
        if (isStruct(modelParmeter.getParmeterType())) {
            struct.add(modelParmeter);
            List<StructParmeter> sList = sMap.get(modelParmeter.getParmeterType());
            List<ModelParmeter> mList = new ArrayList<>();
            for (StructParmeter structParmeter : sList) {
                mList.add(getModelParam(modelParmeter, structParmeter));
            }
            structParmeterMap.put(modelParmeter.getParmeterType(), mList) ;
            outputParmeter.add(modelParmeter);
        } else {
            outputParmeter.add(modelParmeter);
        }
    }

    private ModelParmeter  getModelParam(ModelParmeter modelParmeter, StructParmeter structParmeter) {
        ModelParmeter modelParmeter1 = new ModelParmeter();
        modelParmeter1.setInoutType(modelParmeter.getInoutType());
        modelParmeter1.setParmeterType(structParmeter.getParmeterType());
        modelParmeter1.setParmeterName(structParmeter.getParmeterName());
        modelParmeter1.setParmeterUnit(structParmeter.getParmeterUnit());
        modelParmeter1.setParmeterUnitEx(structParmeter.getParmeterUnitEx());
        modelParmeter1.setModelId(modelParmeter.getModelId());
        modelParmeter1.setIsArray(structParmeter.getParmeterType().contains("*"));
        return  modelParmeter1 ;
    }

    /**
     * 获取输入参数
     * @return
     */
    public List<ModelParmeter> getInputParmeter(){
        return  this.inputParmeter ;
    }

    /**
     * 获取输出参数
     * @return
     */
    public  List<ModelParmeter> getOutParmeter(){
        return  this.outputParmeter ;
    }

    /**
     * 获取结构体
     * @return
     */
    public  List<ModelParmeter> getStruct(){
        return  this.struct ;
    }

    /**
     * 获取结构体参数
     * @return
     */
    public List<ModelParmeter> getStructParmeter(ModelParmeter modelParmeter ){
        return  structParmeterMap.get(modelParmeter.getParmeterType()) ;
    }

    public static  boolean   isStruct(String paramType){
            if(paramType.startsWith("int")){
                return  false ;
            }else  if(paramType.startsWith("long")){
                return false ;
            }else  if(paramType.startsWith("float")){
                return false;
            }else  if(paramType.startsWith("double")){
                return false ;
            }else  if(paramType.startsWith("string")){
                return  false ;
            }
            return   true ;
        }
}
