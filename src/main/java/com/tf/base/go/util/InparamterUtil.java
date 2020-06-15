package com.tf.base.go.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tf.base.common.utils.ApplicationProperties;
import com.tf.base.common.utils.StringUtil;
import com.tf.base.go.define.NodeLink;
import com.tf.base.go.service.IndexTask;
import com.tf.base.go.type.Category;
import net.sf.json.util.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InparamterUtil {
    public static final String success = "success";
    public static final String error = "error";
    public static final String msg = "msg";
    public static final String code = "code";

    public static void main(String[] args) {
//        String path = "D:\\upload\\txt\\20200108\\1578464292919.txt";
////        String path = "D:\\upload\\1578464292919.txt";
//        writerString(path,"你好");
      String out =   readString("D:\\upload\\txt\\20200108\\1578464292919.txt");
      System.out.println(out);
    }

    /**
     * 获取输入参数
     *
     * @param indexTask
     * @return
     */
    public static Map<String, String> getInputParamter(IndexTask indexTask) {
        Map<String, String> inputMap = new HashMap<>();
        JSONObject inputJsonObject = new JSONObject();
        Map<Integer, IndexTask> nodeMap = new HashMap<>();
        //1、获取前置任务数据
        List<IndexTask> preIndexTaskList = indexTask.getPreIndexTask();
        for (IndexTask task : preIndexTaskList) {
            nodeMap.put(task.getNode().getKey(), task);
        }
        //2、根据线获取数据对应关系
        List<NodeLink> nodeLinks = indexTask.getNode().getInNodeLinks();
        for (NodeLink nLink : nodeLinks) {
            String outputParamString = nodeMap.get(nLink.getFromNode().getKey()).getIndexNodeProcess().getNodeOutputParamExp();
            JSONObject outJsonObject = JSONObject.parseObject(outputParamString);
            //如果前置节点是数据源
            if (nLink.getFromNode().getNodeCategory() == Category.Datasource) {
                JSONArray out = outJsonObject.getJSONArray(nLink.getFromPort());
                if (!setInputValueByDatasource(inputJsonObject, nLink, out)) {
                    inputMap.put(code, error);
                    inputMap.put(msg, nLink.getFromNode().getNodeText() + "的" + nLink.getFromPort() + "端口无数据或数据格式不匹配");
                    return inputMap;
                }
            } else if (nLink.getFromNode().getNodeCategory() == Category.Constant) {//如果前置节点是常量
//               String out = nodeMap.get(nLink.getFromNode().getKey()).getIndexNodeProcess().getNodeOutputParam();
                setBasicTypeSingle(inputJsonObject, nLink, outputParamString);
            } else if (nLink.getFromNode().getNodeCategory() == Category.If) {//如果前置节点是IF
                if(outJsonObject.getString("if").equals(nLink.getText())){

                }
            } else if (nLink.getFromNode().getNodeCategory() == Category.Model) {//如果前置节点是model
                setInputValue(inputJsonObject, nLink, outJsonObject);
            }

        }
        inputMap.put(code, success);
        inputMap.put(msg, inputJsonObject.toJSONString());
        return inputMap;
    }

    /**
     * 获取IF的条件参数
     * @param indexTask
     * @return
     */
    public static  double  getIfInputParam(IndexTask indexTask){
        List<NodeLink> inLinks = indexTask.getNode().getInNodeLinks();
        Map<Integer, IndexTask> nodeMap = new HashMap<>();
        //1、获取前置任务数据
        List<IndexTask> preIndexTaskList = indexTask.getPreIndexTask();
        for (IndexTask task : preIndexTaskList) {
            nodeMap.put(task.getNode().getKey(), task);
        }
        for (NodeLink nLink : inLinks) {
            if("input".equals(nLink.getText())) {//如果是输入线，则为判断线
                String outputParamString = nodeMap.get(nLink.getFromNode().getKey()).getIndexNodeProcess().getNodeOutputParamExp();
                JSONObject outJsonObject = JSONObject.parseObject(outputParamString);
                //如果前置节点是数据源
                if (nLink.getFromNode().getNodeCategory() == Category.Datasource) {
                    JSONArray out = outJsonObject.getJSONArray(nLink.getFromPort());
                    if(out.size()>0){
                        return  Double.valueOf(out.getString(0));
                    }
                } else if (nLink.getFromNode().getNodeCategory() == Category.Constant) {//如果前置节点是常量
                    return Double.valueOf(outputParamString);
                } else if (nLink.getFromNode().getNodeCategory() == Category.If) {//如果前置节点是IF
                    //todo  IF 嵌套IF暂且不处理

                } else if (nLink.getFromNode().getNodeCategory() == Category.Model) {//如果前置节点是model
                    Object listArray = new JSONTokener(outJsonObject.getString(nLink.getFromPort()));
                    if (listArray instanceof JSONArray) {//如果是数组
                        JSONArray jsonArray = (JSONArray)listArray;
                        if(jsonArray.size()>0){
                            return  Double.valueOf(jsonArray.getString(0));
                        }
                    } else if (listArray instanceof JSONObject) {//如果是简单对象
                       return Double.valueOf(outJsonObject.getString(nLink.getFromPort())) ;
                    }
                }
            }

        }
            return  0.0;
        }

    /**
     * 获取IF的条件参数 次函数需要更新
     * @param indexTask
     * @return
     */
    public static  JSONObject  getIfOutParam(IndexTask indexTask){
        JSONObject outJsonObject = new JSONObject() ;
        List<NodeLink> inLinks = indexTask.getNode().getInNodeLinks();
        Map<Integer, IndexTask> nodeMap = new HashMap<>();
        //1、获取前置任务数据
        List<IndexTask> preIndexTaskList = indexTask.getPreIndexTask();
        for (IndexTask task : preIndexTaskList) {
            nodeMap.put(task.getNode().getKey(), task);
        }
        for (NodeLink nLink : inLinks) {
            if("out".equals(nLink.getText())) {//如果是输入线，则为判断线
                String outputParamString = nodeMap.get(nLink.getFromNode().getKey()).getIndexNodeProcess().getNodeOutputParamExp();
                 outJsonObject = JSONObject.parseObject(outputParamString);
                //如果前置节点是数据源
                if (nLink.getFromNode().getNodeCategory() == Category.Datasource) {
                    JSONArray out = outJsonObject.getJSONArray(nLink.getFromPort());
                    outJsonObject.put("out",out);
                } else if (nLink.getFromNode().getNodeCategory() == Category.Constant) {//如果前置节点是常量
                    outJsonObject.put("out",outputParamString);
                } else if (nLink.getFromNode().getNodeCategory() == Category.If) {//如果前置节点是IF
                    //todo  IF 嵌套IF暂且不处理
                } else if (nLink.getFromNode().getNodeCategory() == Category.Model) {//如果前置节点是model
                    Object listArray = new JSONTokener(outJsonObject.getString(nLink.getFromPort()));
                    outJsonObject.put("out",listArray);
                }
            }
        }
        return outJsonObject;
    }





    private static boolean setInputValueByDatasource(JSONObject inputJsonObject, NodeLink nLink, JSONArray out) {
        if (out.size() == 0) {// 给出合理的错误提示
            return false;
        }
        try {
            if (!nLink.isArray()) {
                if (out.size() == 1) { //如果节点的尺寸为1说明参数正确
                    //1、获取单位，如果
                    setBasicType(inputJsonObject, nLink, out);
                } else if (out.size() > 1) {// 数组形式传递，待讨论
                    //1、获取单位，如果
                    if ("int".equals(nLink.getModelPortType())) {
                        JSONArray array = new JSONArray();
                        for (int i = 0; i < out.size(); i++) {
                            array.add((int)Double.parseDouble(ifEmpty(out.getString(i))));
                        }
                        inputJsonObject.put(nLink.getToPort(), array);
                    } else if ("float".equals(nLink.getModelPortType())) {
                        JSONArray array = new JSONArray();
                        for (int i = 0; i < out.size(); i++) {
                            array.add(Float.parseFloat(ifEmpty(out.getString(i))));
                        }
                        inputJsonObject.put(nLink.getToPort(), array);
                    } else if ("double".equals(nLink.getModelPortType())) {
                        JSONArray array = new JSONArray();
                        for (int i = 0; i < out.size(); i++) {
                            array.add(Double.parseDouble(ifEmpty(out.getString(i))));
                        }
                        inputJsonObject.put(nLink.getToPort(), array);
                    } else if ("long".equals(nLink.getModelPortType())) {
                        JSONArray array = new JSONArray();
                        for (int i = 0; i < out.size(); i++) {
                            array.add((long)Double.parseDouble(ifEmpty(out.getString(i))));
                        }
                        inputJsonObject.put(nLink.getToPort(), array);
                    } else if ("string".equals(nLink.getModelPortType())) {
                        inputJsonObject.put(nLink.getToPort(), out.getString(0));
                    }
                }
            } else {//输出的接口是数组
                if ("int".equals(nLink.getModelPortType())||"int*".equals(nLink.getModelPortType())) {
                    JSONArray array = new JSONArray();
                    for (int i = 0; i < out.size(); i++) {
                        array.add((int)Double.parseDouble(ifEmpty(out.getString(i))));
                    }
                    inputJsonObject.put(nLink.getToPort(), array);
                } else if ("float".equals(nLink.getModelPortType())||"float*".equals(nLink.getModelPortType())) {
                    JSONArray array = new JSONArray();
                    for (int i = 0; i < out.size(); i++) {
                        array.add(Float.parseFloat(ifEmpty(out.getString(i))));
                    }
                    inputJsonObject.put(nLink.getToPort(), array);
                } else if ("double".equals(nLink.getModelPortType())||"double*".equals(nLink.getModelPortType())) {
                    JSONArray array = new JSONArray();
                    for (int i = 0; i < out.size(); i++) {
                        array.add(Double.parseDouble(ifEmpty(out.getString(i))));
                    }
                    inputJsonObject.put(nLink.getToPort(), array);
                } else if ("long".equals(nLink.getModelPortType())||"long*".equals(nLink.getModelPortType())) {
                    JSONArray array = new JSONArray();
                    for (int i = 0; i < out.size(); i++) {
                        array.add((long)Double.parseDouble(ifEmpty(out.getString(i))));
                    }
                    inputJsonObject.put(nLink.getToPort(), array);
                } else if ("string".equals(nLink.getModelPortType())) {
                    inputJsonObject.put(nLink.getToPort(), out);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static void setBasicType(JSONObject inputJsonObject, NodeLink nLink, JSONArray out) {
        if ("int".equals(nLink.getModelPortType())) {
            inputJsonObject.put(nLink.getToPort(), (int)Double.parseDouble(ifEmpty(out.getString(0))));
        } else if ("float".equals(nLink.getModelPortType())) {
            inputJsonObject.put(nLink.getToPort(), Float.parseFloat(ifEmpty(out.getString(0))));
        } else if ("double".equals(nLink.getModelPortType())) {
            inputJsonObject.put(nLink.getToPort(),Double.parseDouble(ifEmpty(out.getString(0))));
        } else if ("long".equals(nLink.getModelPortType())) {
            inputJsonObject.put(nLink.getToPort(), (long)Double.parseDouble(ifEmpty(out.getString(0))));
        } else if ("string".equals(nLink.getModelPortType())) {
            inputJsonObject.put(nLink.getToPort(), out.getString(0));
        }
    }

    /**
     * 获取基本类型
     * @param inputJsonObject
     * @param nLink
     * @param out
     */
    private static void setBasicTypeSingle(JSONObject inputJsonObject, NodeLink nLink, String  out) {
        if ("int".equals(nLink.getModelPortType())) {
            inputJsonObject.put(nLink.getToPort(), (int)Double.parseDouble(ifEmpty(out)));
        } else if ("float".equals(nLink.getModelPortType())) {
            inputJsonObject.put(nLink.getToPort(), Float.parseFloat(ifEmpty(out)));
        } else if ("double".equals(nLink.getModelPortType())) {
            inputJsonObject.put(nLink.getToPort(),Double.parseDouble(ifEmpty(out)));
        } else if ("long".equals(nLink.getModelPortType())) {
            inputJsonObject.put(nLink.getToPort(), (long)Double.parseDouble(ifEmpty(out)));
        } else if ("string".equals(nLink.getModelPortType())) {
            inputJsonObject.put(nLink.getToPort(), out);
        }
    }

    private static void setInputValue(JSONObject inputJsonObject, NodeLink nLink, JSONObject outJsonObject) {
        if (!nLink.isArray()) {
            //1、获取单位，如果
            if ("int".equals(nLink.getModelPortType())) {
                inputJsonObject.put(nLink.getToPort(), outJsonObject.getIntValue(nLink.getFromPort()));
            } else if ("float".equals(nLink.getModelPortType())) {
                inputJsonObject.put(nLink.getToPort(), outJsonObject.getFloatValue(nLink.getFromPort()));
            } else if ("double".equals(nLink.getModelPortType())) {
                inputJsonObject.put(nLink.getToPort(), outJsonObject.getDoubleValue(nLink.getFromPort()));
            } else if ("long".equals(nLink.getModelPortType())) {
                inputJsonObject.put(nLink.getToPort(), outJsonObject.getLongValue(nLink.getFromPort()));
            } else if ("string".equals(nLink.getModelPortType())) {
                inputJsonObject.put(nLink.getToPort(), outJsonObject.getString(nLink.getFromPort()));
            }
        }
    }

    /**
     * 前置节点是model信息
     * @param inputJsonObject
     * @param nLink
     * @param outJsonObject
     */
    private  static  void setInputValueModel(JSONObject inputJsonObject, NodeLink nLink, JSONObject outJsonObject){
//        if(modelInfo.getIsSingleFun()){//但函数
//
//        }

    }


    private static  String ifEmpty(Object obj){
        if(obj == null || obj.toString().trim().equals("")){
            return "0";
        }
        return obj.toString();
    }

    public static void writerString(String filePath,String content) {
        FileWriter fwriter = null;
        try {

            File file=new File(filePath);
            if( !file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
            fwriter = new FileWriter(new File(filePath));
            fwriter.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    /**
     * 读取文件为字符串
     *
     * @return
     */
    public static String readString(String filePath) {
        if(StringUtil.isEmpty(filePath)){
            return  "";
        }
        String str = "";
        File file = new File(filePath);
        try {
            FileInputStream in = new FileInputStream(file);
            // size 为字串的长度 ，这里一次性读完
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            str = new String(buffer);
//            str = new String(buffer, "GB2312");
        } catch (IOException e) {
            return null;
        }
        return str;
    }


    public  static  String createFileName(){
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        // 设置文件路径
        // String ctxPath = "/data/order/change/pic/" + dateFormat.format(now) + "/";
        ApplicationProperties app = new ApplicationProperties();
        String fileName = app.getValueByKey("file.upload.txt.path") + dateFormat.format(now) + File.separator+System.currentTimeMillis()+".txt";
        return  fileName ;
    }


}
