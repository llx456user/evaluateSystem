package com.tf.base.platform.service;

import com.tf.base.common.domain.ModelInfo;
import com.tf.base.common.domain.ModelParmeter;
import com.tf.base.common.utils.ApplicationProperties;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by wanquan on 2018/3/29.
 * modelinfo工具类，主要要来生成model的参数
 */
public class ModelInfoUtil {

    public static void main(String[] args) {
        ModelInfoUtil modelInfoUtil= new ModelInfoUtil();
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
        modelParmeter1.setParmeterName("a");
        modelParmeter1.setParmeterUnit("not_array");
        modelParmeter1.setParmeterType("int");
        modelParmeter1.setInoutType(0);
        modelParmeter1.setParentId(0);
        list.add(modelParmeter1);
        ModelParmeter modelParmeter2= new ModelParmeter();
        modelParmeter2.setId(3035);
        modelParmeter2.setModelId(3018);
        modelParmeter2.setParmeterName("d");
        modelParmeter2.setParmeterUnit("not_array");
        modelParmeter2.setParmeterType("struct_int");
        modelParmeter2.setInoutType(1);
        modelParmeter2.setParentId(0);
        list.add(modelParmeter2);
        ModelParmeter modelParmeter3= new ModelParmeter();
        modelParmeter3.setId(3036);
        modelParmeter3.setModelId(3018);
        modelParmeter3.setParmeterName("cc1");
        modelParmeter3.setParmeterUnit("not_array");
        modelParmeter3.setParmeterType("struct_int");
        modelParmeter3.setInoutType(0);
        modelParmeter3.setParentId(0);
        list.add(modelParmeter3);
        ModelParmeter modelParmeter4= new ModelParmeter();
        modelParmeter4.setId(3037);
        modelParmeter4.setModelId(3018);
        modelParmeter4.setParmeterName("struct_int");
        modelParmeter4.setParmeterUnit("not_array");
        modelParmeter4.setParmeterType("struct");
        modelParmeter4.setInoutType(2);
        modelParmeter4.setParentId(0);
        list.add(modelParmeter4);
        ModelParmeter modelParmeter5= new ModelParmeter();
        modelParmeter5.setId(3038);
        modelParmeter5.setModelId(3018);
        modelParmeter5.setParmeterName("cc2");
        modelParmeter5.setParmeterUnit("not_array");
        modelParmeter5.setParmeterType("struct_int");
        modelParmeter5.setInoutType(0);
        modelParmeter5.setParentId(0);
        list.add(modelParmeter5);
        ModelParmeter modelParmeter6= new ModelParmeter();
        modelParmeter6.setId(3039);
        modelParmeter6.setModelId(3018);
        modelParmeter6.setParmeterName("c1");
        modelParmeter6.setParmeterUnit("not_array");
        modelParmeter6.setParmeterType("int");
        modelParmeter6.setParentId(3037);
        modelParmeter6.setInoutType(2);
        list.add(modelParmeter6);
        ModelParmeter modelParmeter7= new ModelParmeter();
        modelParmeter7.setId(3040);
        modelParmeter7.setModelId(3018);
        modelParmeter7.setParmeterName("c2");
        modelParmeter7.setParmeterUnit("not_array");
        modelParmeter7.setParmeterType("int");
        modelParmeter7.setParentId(3037);
        modelParmeter7.setInoutType(2);
        list.add(modelParmeter7);
        modelInfoUtil.createModelXML(modelInfo,list);
    }

    //文件存储路径
//    public static final String FILE_DIR = "D:/upload5/DllAndXml/";

    /**
     * 模型xml创建信息
     * @param modelInfo
     * @param list
     * @return [0] 文件名 [1] 模型xml名称
     */
    public String[] createModelXML(ModelInfo modelInfo, List<ModelParmeter> list) {
        String[] xml = new String[2];
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        /** 构建文件保存的目录* */
        /** 创建目录* */
        ApplicationProperties app = new ApplicationProperties();
        String  FILE_DIR =app.getValueByKey("file.upload.path")+"DllAndXml/";
        File saveFile = new File(FILE_DIR);
        if (!saveFile.exists()){
            saveFile.mkdirs();
        }
        /** 拼成完整的文件保存路径加文件* */
        String fileName = FILE_DIR + UUID.randomUUID().toString() + ".xml";
        makeXML(fileName, createDoc(modelInfo, list));
        xml[0] = fileName;
        xml[1] = modelInfo.getModelName() + ".xml";
        return xml;
    }

    /**
     * 更新xml信息
     * @param modelInfo
     * @param list
     * @return
     */
    public String[] updateXML(ModelInfo modelInfo, List<ModelParmeter> list) {
        String[] xml = new String[2];
        /** 创建目录* */
        ApplicationProperties app = new ApplicationProperties();
        String  FILE_DIR =app.getValueByKey("file.upload.path")+"DllAndXml/";
        File logoSaveFile = new File(FILE_DIR);
        if (!logoSaveFile.exists()){
            logoSaveFile.mkdirs();
        }
        /** 拼成完整的文件保存路径加文件* */
        String fileName = modelInfo.getXmlPath();

        makeXML(fileName, createDoc(modelInfo, list));
        xml[0] = fileName;
        xml[1] = modelInfo.getModelName() + ".xml";
        return xml;
    }

    private static final String[] ELEMENT_IN_TAG = {"model_parameterInput", "parameter", "parameter_name", "parameter_type", "parameter_unit", "is_array"};
    private static final String[] ELEMENT_IN_TAG_INNER = {"innerparameters", "innerparameter", "parameter_name", "parameter_type", "parameter_unit", "is_array"};
    private static final String[] ELEMENT_OUT_TAG = {"model_return", "return", "return_name", "return_type", "return_unit", "return_source"};
    private static final String[] ELEMENT_OUT_TAG_INNER = {"innerparameters", "innerparameter", "return_name", "return_type", "return_unit", "return_source"};

    public Document createDoc(ModelInfo model, List<ModelParmeter> params) {
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("model");
        Element modelName = root.addElement("model_name");
        modelName.setText(model.getModelName().toString());
        Element modelVer = root.addElement("model_version");
        if (null != model.getModelVersion()) {
            modelVer.setText(model.getModelVersion());
        }
        Element modelDes = root.addElement("model_descript");
        if (null != model.getModelContent()) {
            modelDes.setText(model.getModelContent());
        }
        Element modelSingleFun = root.addElement("model_singlefun");
        if(null != model.getIsSingleFun()){//是否为单一函数
            modelSingleFun.setText(String.valueOf(model.getIsSingleFun()));
        }

        createElement(root, 0, "", ELEMENT_IN_TAG, params);
        createElement(root, 1, "", ELEMENT_OUT_TAG, params);

        return doc;
    }

    /**
     * @param root
     * @param typeFlag   参数类型：0是入参。1是出参
     * @param parentType 结构体参数类型
     * @param tag
     * @param params
     */
    public void createElement(Element root, int typeFlag, String parentType, String[] tag, List<ModelParmeter> params) {
        int count = 0;
        int parentId = 0;
        if (null != parentType && !parentType.isEmpty()) {
            parentId = getParentId(params, parentType);
        }
        Element element = root.addElement(tag[0]);
        for (int i = 0; i < params.size(); i++) {
            ModelParmeter param = params.get(i);
            int paramParentId=param.getParentId()==null?0:param.getParentId();
            if (typeFlag == param.getInoutType() && !param.getParmeterName().endsWith("Len")
                    && parentId == paramParentId) {
                Element parameter = element.addElement(tag[1]);
                parameter.addAttribute("id", String.valueOf(count));
                Element retName = parameter.addElement(tag[2]);
                Element retType = parameter.addElement(tag[3]);
                Element retUnit = parameter.addElement(tag[4]);
                retUnit.setText(param.getParmeterUnit()==null?"":param.getParmeterUnit());//单位
                Element retArray = parameter.addElement(tag[5]);
                retArray.setText(String.valueOf(param.getIsArray()));
                if (param.getParmeterName().startsWith("*")) {
                    retName.setText(param.getParmeterName().substring(1));
                    if (confirmType(INT_TYPE_SET, param.getParmeterType()) || confirmType(FLOAT_TYPE_SET, param.getParmeterType())) {
                        retType.setText(param.getParmeterType());
//                        retUnit.setText("normal_array");
                    } else {
                        retType.setText("array");
//                        retUnit.setText("Struct_array");
                        String[] subTag;
                        if (tag[3].startsWith("parameter")) {
                            subTag = ELEMENT_IN_TAG_INNER;
                        } else {
                            subTag = ELEMENT_OUT_TAG_INNER;
                        }
                        createElement(parameter, 2, param.getParmeterType(), subTag, params);
                    }
                } else {
                    retName.setText(param.getParmeterName());
                    retType.setText(param.getParmeterType());
//                    retUnit.setText("not_array");
                }
                count++;
            }
        }
    }


    /**
     * 获取结构体的ID
     *
     * @param params
     * @param parentType
     * @return
     */
    public static int getParentId(List<ModelParmeter> params, String parentType) {
        int parentId = 0;

        for (int i = 0; i < params.size(); i++) {
            ModelParmeter param = params.get(i);
            if (2 == param.getInoutType() && param.getParmeterName().equals(parentType) && param.getParmeterType().equals("struct")) {
                parentId = param.getId();
                break;
            }
        }
        return parentId;
    }


    public static final String[] INT_TYPE_SET = {"int", "long"};
    public static final String[] FLOAT_TYPE_SET = {"float", "double"};

    /**
     * 确认参数类型
     *
     * @param typeSet
     * @param type
     * @return
     */
    public static  boolean confirmType(String[] typeSet, String type) {
        boolean bool = false;
        for (String baseType : typeSet) {
            if (baseType.equals(type)) {
                bool = true;
                break;
            }
        }
        return bool;
    }


    /**
     * 创建XML
     * @param xmlName
     * @param doc
     * @return
     */
    public boolean makeXML(String xmlName, Document doc) {
        boolean bool = false;
        FileWriter out = null;
        try {
            out = new FileWriter(xmlName);
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(doc);
            bool = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return bool;
    }
}
