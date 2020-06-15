package com.tf.base.jni.model;

import com.google.common.collect.Lists;
import com.tf.base.common.domain.ModelParmeter;
import com.tf.base.platform.service.ModelParmeterTool;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MakeFileUtil {
    private ModelParmeterTool modelParmeterTool;
//    private  Map<String,JNIParameter[]> structMap ;//记录结构体定义
    private  List<JNIParameter> inList;//输入参数
    private  List<JNIParameter> outList;//输出参数
    private  List<JNIParameter> structInstanceList ;//结构体实例  此处应该有问题，因为一个结构体，可以有多个实例

    public MakeFileUtil(ModelParmeterTool modelParmeterTool) {
        this.modelParmeterTool = modelParmeterTool;
        this.inList = new ArrayList<>();
        this.outList = new ArrayList<>();
        this.structInstanceList= new ArrayList<>();
//        this.structMap=new HashMap<>();
        buildParam();
    }


    private  void buildParam(){
        //1、构造结构体
//        List<ModelParmeter> structList = modelParmeterTool.getStruct();
//        while (structList.size()!=structMap.keySet().size()){
//            for(ModelParmeter modelParmeter:structList){
//                if(!structMap.containsKey(modelParmeter.getParmeterName())){
//                    if(isReadStruct(modelParmeter)){
//                        structMap.put(modelParmeter.getParmeterName(),getStructParameter(modelParmeter));
//                    }
//                }
//            }
//        }

        //1、构造输入参数
        List<ModelParmeter> inputList = modelParmeterTool.getInputParmeter();
        for(ModelParmeter modelParmeter:inputList){
            JNIParameter inParmeter = getJniParameter(modelParmeter);
            this.inList.add(inParmeter);
//            if(ParameterType.isStruct(inParmeter.getType())){
//                this.structInstanceList.add(inParmeter);
//            }
        }
        //2、构造输出参数
        List<ModelParmeter> outList=modelParmeterTool.getOutParmeter();
        for(ModelParmeter modelParmeter:outList){
            JNIParameter outParameter = getJniParameter(modelParmeter);;
            this.outList.add(outParameter);
//            if(ParameterType.isStruct(outParameter.getType())){
//                this.structInstanceList.add(outParameter);
//            }
        }

    }


//    /**
//     * 判断结构体是否有依赖完成
//     * @param modelParmeter
//     * @return
//     */
//    private  boolean isReadStruct(ModelParmeter modelParmeter ){
//        List<ModelParmeter> list = modelParmeterTool.getStructParmeter(modelParmeter);
//        for (ModelParmeter p : list) {
//            if ("struct".equals(ParameterType.getParamType(p.getParmeterType()))) {
//                       if(!structMap.containsKey(p.getParmeterType())){
//                           return  false ;
//                       }
//            }
//        }
//        return  true ;
//    }

//    /**
//     * 获取结构体参数
//     * @param modelParmeter
//     * @return
//     */
//    private  JNIParameter[] getStructParameter(ModelParmeter modelParmeter ){
//        List<JNIParameter> jniParameterList = new ArrayList<>();
//        List<ModelParmeter> list = modelParmeterTool.getStructParmeter(modelParmeter);
//        for (ModelParmeter p : list) {
//            JNIParameter jniParameter = getJniParameter(p);
//            jniParameterList.add(jniParameter);
//        }
//        JNIParameter[] re = new JNIParameter[jniParameterList.size()];
//        jniParameterList.toArray(re);
//        return re;
//    }

    /**
     * 获取Jni参数
     * @param p
     * @return
     */
    private JNIParameter getJniParameter(ModelParmeter p) {
        JNIParameter jniParameter = new JNIParameter();
        if (ModelParmeterTool.isStruct(p.getParmeterType())) {
            //针对图谱输出，增加了结构体数组
            if(p.getParmeterType().toLowerCase().startsWith("picture")){
                jniParameter.setIsArray(true);
            }else {
                jniParameter.setIsArray(p.getIsArray());
            }
            jniParameter.setName(p.getParmeterName());
            jniParameter.setType(p.getParmeterType());
            jniParameter.setParameters(getStructParameter(p));
            if(p.getParmeterType().startsWith("picture")){
                if(!isContainPicture()){
                    this.structInstanceList.add(jniParameter);
                }
            }else {
                this.structInstanceList.add(jniParameter);
            }

        } else {
            jniParameter.setIsArray(p.getIsArray());
            jniParameter.setName(p.getParmeterName());
            jniParameter.setType(ParameterType.getParamType(p.getParmeterType()));
        }
        return jniParameter;
    }

    private   boolean isContainPicture(){
        for ( JNIParameter p:structInstanceList){
            if(p.getType().startsWith("picture")){
                return  true ;
            }
        }
        return  false ;
    }



    private JNIParameter[]  getStructParameter(ModelParmeter p){
            List<ModelParmeter> list =  modelParmeterTool.getStructParmeter(p);
            JNIParameter[] jniParametersList = new JNIParameter[list.size()];
            for(int i=0;i<list.size();i++){
                JNIParameter jniParameter = new JNIParameter();
                jniParameter.setIsArray(list.get(i).getIsArray());
                jniParameter.setName(list.get(i).getParmeterName());
                jniParameter.setType(ParameterType.getParamType(list.get(i).getParmeterType()));
                jniParametersList[i]=jniParameter;
            }
        return  jniParametersList ;
    }


    public void makeCppFile(String dll_name,String templatePath,String outPath,boolean isSingleFun){
        JNIParameter[] inParam=new JNIParameter[inList.size()] ;
        inList.toArray(inParam);
        JNIParameter[] outParam=new JNIParameter[outList.size()];
        outList.toArray(outParam);
        JNIParameter[] structArray=new JNIParameter[structInstanceList.size()];
        structInstanceList.toArray(structArray);
        JNIParameter inModel = new JNIParameter("in","InParameter",inParam);
        JNIParameter outModel =  new JNIParameter("out","OutParameter",outParam);
        List<JNIParameter> definitionList = Lists.asList(inModel,outModel, structArray);
        Map<String, Object> map = new HashMap<String, Object>();
        if(isSingleFun){
            map.put("arrayFunction", true);
            if(null != structArray){
                for(JNIParameter each : structArray){
                    each.setIsArray(true);
                }
            }
            if(null != inParam){
                for(JNIParameter each : inParam){
                    each.setIsArray(true);
                }
            }
            if(null != outParam){
                for(JNIParameter each : outParam){
                    each.setIsArray(true);
                }
            }
        }
        map.put("api_name", dll_name.toUpperCase()+"_API");
        map.put("header", dll_name);
        map.put("inModel", inModel);
        map.put("outModel", outModel);
        map.put("list", definitionList);
        makeFile(map, templatePath, "cpp_header.ftl", outPath + dll_name + ".h");
        makeFile(map, templatePath, "cpp_parse.ftl", outPath + "support.cpp");
        makeFile(map, templatePath, "cpp_main.ftl", outPath + dll_name+".cpp");
    }


    /**
     * 制作文件
     * @param value
     * @param templatePath
     * @param templeName
     * @param outFileName
     */
    private void makeFile(Object value, String templatePath, String templeName, String outFileName) {
        try {
            Configuration cfg = new Configuration();
            cfg.setTemplateLoader(new ClassTemplateLoader());
            cfg.setObjectWrapper(new DefaultObjectWrapper());
            cfg.setDefaultEncoding("UTF-8");
            cfg.setDirectoryForTemplateLoading(new File(templatePath));
            Writer out = new OutputStreamWriter(new FileOutputStream(outFileName), "UTF-8");
            Template temp = cfg.getTemplate(templeName);
            temp.setEncoding("UTF-8");
            temp.process(value, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
