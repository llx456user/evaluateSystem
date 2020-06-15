package com.tf.base.go.service.Impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tf.base.common.domain.IndexInfoProcess;
import com.tf.base.common.domain.ModelInfo;
import com.tf.base.common.persistence.ModelInfoMapper;
import com.tf.base.common.utils.StringUtil;
import com.tf.base.go.define.IndexDefinitions;
import com.tf.base.go.define.IndexNode;
import com.tf.base.go.define.NodeLink;
import com.tf.base.go.process.StarterProcessorService;
import com.tf.base.go.service.IndexTask;
import com.tf.base.go.service.PictureUtil;
import com.tf.base.go.type.Category;
import com.tf.base.go.type.NodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by wanquan on 2018/5/7.
 */
@Service
public class ModelInputService {

    private Logger logger = Logger.getLogger(ModelInputService.class.getName());

    public static final String success = "success";
    public static final String error = "error";
    public static final String msg = "msg";
    public static final String code = "code";
    DecimalFormat decimalFormat = new DecimalFormat("0.00000");
    @Autowired
    private ModelInfoMapper modelInfoMapper;

    /**
     * 获取输入参数
     *
     * @param indexTask
     * @return
     */
    public Map<String, String> getModelInputParamter(IndexTask indexTask) {
        Map<String, String> inputMap = new LinkedHashMap<>();
        JSONObject inputJsonObject = new JSONObject(new LinkedHashMap());
        Map<Integer, IndexTask> nodeMap = new HashMap<>();
        ModelInfo modelInfo = modelInfoMapper.selectByPrimaryKey(indexTask.getNode().getModelId());
        //1、获取前置任务数据
        List<IndexTask> preIndexTaskList = indexTask.getPreIndexTask();
        for (IndexTask task : preIndexTaskList) {
            nodeMap.put(task.getNode().getKey(), task);
        }
        //2、根据线获取数据对应关系
        List<NodeLink> nodeLinks = indexTask.getNode().getInNodeLinks();
        for (NodeLink nLink : nodeLinks) {
            String outputParamString = nodeMap.get(nLink.getFromNode().getKey()).getIndexNodeProcess().getNodeOutputParamExp();

            if (modelInputIsArray(modelInfo, nLink)) {//输出是数组
                JSONObject outJsonObject = JSONObject.parseObject(outputParamString);
                //如果前置节点是数据源
                setArrayInput(inputJsonObject, nLink, outputParamString, outJsonObject);
            } else {
                //如果前置节点是数据源
                setNotArrayInput(inputJsonObject, nLink, outputParamString);
            }
        }
        inputMap.put(code, success);
        inputMap.put(msg, inputJsonObject.toJSONString());
        return inputMap;
    }


    /**
     * 获取子节点的输入及输出
     *
     * @param indexTask
     * @return
     */
    public String[] getChildInputParamter(IndexTask indexTask) {
        JSONObject inputJsonObject = new JSONObject(new LinkedHashMap());
        JSONObject outJsonObject = new JSONObject(new LinkedHashMap());
        Map<Integer, IndexTask> nodeMap = indexTask.getKeyTaskMap();//节点和key的对应关系
        //1、根据线获取数据对应关系
        List<NodeLink> nodeLinks = indexTask.getNode().getInNodeLinks();
        // 2、获取输出线
        List<NodeLink> outLinkList = indexTask.getNode().getOutNodeLink();
        for (int i = 0; i < nodeLinks.size(); i++) {
            NodeLink nLink = nodeLinks.get(i);

            String outputParamString = nodeMap.get(nLink.getFromNode().getKey()).getIndexNodeProcess().getNodeOutputParamExp();
            JSONObject jsonObject = JSONObject.parseObject(outputParamString);
            setArrayInput(inputJsonObject, nLink, outputParamString, jsonObject);
        }
        HashMap<String, String> inOutMapping = new HashMap<>();
        if (outLinkList != null && outLinkList.size() > 0) {
            IndexNode node = outLinkList.get(0).getFromNode();
            JSONObject inService = node.getInServices();
            JSONObject outService = node.getOutServices();
            String[] inArray = new String[inService.size()];
            inService.keySet().toArray(inArray);
            String[] outArray = new String[outService.size()];
            outService.keySet().toArray(outArray);
            for (int i = 0; i < outArray.length; i++) {
                if(i<inArray.length){
                    inOutMapping.put(outArray[i],inArray[i]);
                }
            }
        }

        for (int i = 0; i < outLinkList.size(); i++) {
            NodeLink oLink = outLinkList.get(i);
            String outPort = oLink.getFromPort();
            if(inOutMapping.containsKey(outPort)){
                Object object = inputJsonObject.get(inOutMapping.get(outPort));
                outJsonObject.put(oLink.getFromPort(), object);//输入直接转化为输出
            }
//            if (i < nodeLinks.size()) {//说明输入是存在的
//                Object object = inputJsonObject.get(nodeLinks.get(i).getToPort());
//                outJsonObject.put(oLink.getFromPort(), object);//输入直接转化为输出
//            }
        }
        return new String[]{inputJsonObject.toJSONString(), outJsonObject.toJSONString()};
    }


    /**
     * 获取输出参数
     *
     * @param indexTask
     */
    public JSONObject getOutNodeInputParamter(IndexTask indexTask) {
        //定义json的输出格式。pictureType，input-x,input-y[]
        JSONObject inputJsonObject = new JSONObject();
        //2、根据线获取数据对应关系
        List<NodeLink> nodeLinks = indexTask.getNode().getInNodeLinks();
        Map<Integer, IndexTask> nodeMap = new HashMap<>();
        //1、获取前置任务数据
        List<IndexTask> preIndexTaskList = indexTask.getPreIndexTask();
        for (IndexTask task : preIndexTaskList) {
            nodeMap.put(task.getNode().getKey(), task);
        }
        if (indexTask.getNode().getNodeCategory() == Category.Grid) {//表格处理
            Map<String, List<String>> pMap = new LinkedHashMap<>();
            int rowLenth = 0;
            for (NodeLink nLink : nodeLinks) {
                String outputParamString = nodeMap.get(nLink.getFromNode().getKey()).getIndexNodeProcess().getNodeOutputParamExp();
                JSONObject outJsonObject = JSONObject.parseObject(outputParamString);
                pMap.put(nLink.getToPort(), getPictureValue(outJsonObject, nLink));
                if (rowLenth == 0) {
                    rowLenth = getPictureValue(outJsonObject, nLink).size();
                }
            }
            List<String[]> data = new ArrayList<>();
            int headerLenth = 0;
            for (int i = 0; i < rowLenth; i++) {

                if (i == 0) {
                    List<String> hList = new ArrayList<>();
                    String[] hstrings = new String[pMap.keySet().size()];
                    for (String key : pMap.keySet()) {
                        hList.add(key);
                    }
                    hList.toArray(hstrings);
                    data.add(hstrings);
                }
                List<String> rList = new ArrayList<>();
                for (String key : pMap.keySet()) {
                    rList.add(pMap.get(key).get(i));
                }
                if (headerLenth == 0) {
                    headerLenth = pMap.keySet().size();
                }
                String[] strings = new String[pMap.keySet().size()];
                rList.toArray(strings);
                data.add(strings);
            }
            inputJsonObject.put("title", indexTask.getNode().getPictureTitle());
            inputJsonObject.put("data", data);
            inputJsonObject.put("colspan", headerLenth);
            return inputJsonObject;
        }
        inputJsonObject.put("pictureType", indexTask.getNode().getNodeType().getValue());//设置图形的类型
        boolean isOutArray = pictureInputIsArray(indexTask.getNode().getNodeType());
//        String fileName = indexTask.getIndexEngine().getIndexRepositoryService().getIndexDefinitions().getEvalPath()+File.separator+getFileName(indexTask);
        LinkLineBean linkLineBean = new LinkLineBean(nodeLinks, nodeMap);
        if (indexTask.getNode().getNodeType() == NodeType.Pie) {//饼状图
            List<String> xArrays = new ArrayList<>();
            List<String> yArrays = new ArrayList<>();
            NodeLink link = getStructLinks(nodeLinks);
            if (link != null) { //增加了特殊的处理，处理结构体数据
                inputJsonObject = getPieJSONObject(nodeMap.get(link.getFromNode().getKey()).getIndexNodeProcess().getNodeOutputParamExp(), indexTask.getNode().getPictureTitle(), link.getFromPort());
//                writeJsFile(inputJsonObject,fileName);
                return inputJsonObject;
            }
            for (NodeLink nLink : nodeLinks) {
                String outputParamString = nodeMap.get(nLink.getFromNode().getKey()).getIndexNodeProcess().getNodeOutputParamExp();
                JSONObject outJsonObject = JSONObject.parseObject(outputParamString);
                if (nLink.isX()) {
                    xArrays = getPictureValue(outJsonObject, nLink);
                } else {
                    yArrays = getPictureValue(outJsonObject, nLink);
                }
            }
            if (xArrays.size() == 0) {
                for (int i = 1; i <= yArrays.size(); i++) {
                    xArrays.add(String.valueOf(i));
                }
            }
            List<JSONObject> datas = new ArrayList<>();
            for (int i = 0; i < xArrays.size(); i++) {
                JSONObject object = new JSONObject();
                object.put("name", xArrays.get(i));
                object.put("value", yArrays.get(i));
                datas.add(object);
            }
            inputJsonObject.put("title", indexTask.getNode().getPictureTitle());
            inputJsonObject.put("invNames", xArrays);
            inputJsonObject.put("loanCounts", datas);
        } else if (indexTask.getNode().getNodeType() == NodeType.Bar) {//柱状图
            NodeLink link = linkLineBean.getStructLinks();
            if (link != null) { //增加了特殊的处理，处理结构体数据
                inputJsonObject = getBarJSONObject(nodeMap.get(link.getFromNode().getKey()).getIndexNodeProcess().getNodeOutputParam(), indexTask.getNode().getPictureTitle(), indexTask.getNode().getxTitle(), indexTask.getNode().getyTitle(), link.getFromPort(), indexTask.getNode().getNodeType().getValue());
//                writeJsFile(inputJsonObject,fileName);
                return inputJsonObject;
            }
            //判断x轴
            NodeLink xLink = null;
            NodeLink lLink = getLabelLinks(nodeLinks);
            List<String> labelList = new ArrayList<>();
            int lCount = 0;
            if (lLink != null) {
                labelList = getPictureValue(JSONObject.parseObject(nodeMap.get(lLink.getFromNode().getKey()).getIndexNodeProcess().getNodeOutputParamExp()), lLink);
            }
            int outSize = 0;
            List<JSONObject> datas = new ArrayList<>();
            for (NodeLink nLink : nodeLinks) {
                String outputParamString = nodeMap.get(nLink.getFromNode().getKey()).getIndexNodeProcess().getNodeOutputParamExp();
                JSONObject outJsonObject = JSONObject.parseObject(outputParamString);
                if (nLink.isX()) {
                    xLink = nLink;
                } else if (!nLink.isL()) {
                    List paramValueList = getPictureValue(outJsonObject, nLink);
                    outSize = paramValueList.size();
                    JSONObject object = new JSONObject();
                    if (labelList.size() > lCount) {
                        object.put("name", labelList.get(lCount));
                    } else {
                        object.put("name", nLink.getToPort());
                    }
                    object.put("data", paramValueList);
                    datas.add(object);
                    lCount++;
                }
            }
            List<String> xArrays = new ArrayList<>();
            if (xLink != null) {//说明X已经赋值
                String outputParamString = nodeMap.get(xLink.getFromNode().getKey()).getIndexNodeProcess().getNodeOutputParamExp();
                JSONObject outJsonObject = JSONObject.parseObject(outputParamString);
                xArrays = getPictureValue(outJsonObject, xLink);
            } else {
                for (int i = 1; i <= outSize; i++) {
                    xArrays.add(String.valueOf(i));
                }
            }
            inputJsonObject.put("title", indexTask.getNode().getPictureTitle());
            inputJsonObject.put("xtitle", indexTask.getNode().getxTitle());
            inputJsonObject.put("ytitle", indexTask.getNode().getyTitle());
            inputJsonObject.put("invNames", xArrays);
            inputJsonObject.put("loanCounts", datas);
        } else if (indexTask.getNode().getNodeType() == NodeType.Hline || indexTask.getNode().getNodeType() == NodeType.Line) {
            NodeLink link = linkLineBean.getStructLinks();
            if (link != null) { //增加了特殊的处理，处理结构体数据
                inputJsonObject = getLineJSONObject(nodeMap.get(link.getFromNode().getKey()).getIndexNodeProcess().getNodeOutputParamExp(), indexTask.getNode().getPictureTitle(), indexTask.getNode().getxTitle(), indexTask.getNode().getyTitle(), link.getFromPort(), indexTask.getNode().getNodeType().getValue());
//                writeJsFile(inputJsonObject,fileName);
                return inputJsonObject;
            }
            inputJsonObject.put("title", indexTask.getNode().getPictureTitle());
            inputJsonObject.put("xtitle", indexTask.getNode().getxTitle());
            inputJsonObject.put("ytitle", indexTask.getNode().getyTitle());
            JSONObject lineObject = linkLineBean.getLineDatas(indexTask.getNode().getNodeType().getValue());
            inputJsonObject.put("datas", lineObject.getJSONArray("datas"));
            inputJsonObject.put("legendData", lineObject.getJSONArray("legendData"));
        } else if (indexTask.getNode().getNodeType() == NodeType.Scatter) {//散点图
            NodeLink link = getStructLinks(nodeLinks);
            if (link != null) { //增加了特殊的处理，处理结构体数据
                inputJsonObject = getScatterJSONObject(nodeMap.get(link.getFromNode().getKey()).getIndexNodeProcess().getNodeOutputParamExp(), indexTask.getNode().getPictureTitle(), indexTask.getNode().getxTitle(), indexTask.getNode().getyTitle(), link.getFromPort(), indexTask.getNode().getNodeType().getValue());
                return inputJsonObject;
            }
            //判断x轴
            List<List<String>> xArrays = new ArrayList<>();
            List<List<String>> yArrays = new ArrayList<>();
            List<String> yNames = new ArrayList<>();
            for (NodeLink nLink : nodeLinks) {
                String outputParamString = nodeMap.get(nLink.getFromNode().getKey()).getIndexNodeProcess().getNodeOutputParamExp();
                JSONObject outJsonObject = JSONObject.parseObject(outputParamString);
                if (nLink.isX()) {
                    List<String> paramValueList = getPictureValue(outJsonObject, nLink);
                    xArrays.add(paramValueList);
                } else {
                    List<String> paramValueList = getPictureValue(outJsonObject, nLink);
                    yNames.add(nLink.getToPort());
                    yArrays.add(paramValueList);
                }
            }
            Map<String, JSONArray> map = new LinkedHashMap<>();

            if (xArrays.size() > 0) {
                for (int i = 0; i < xArrays.size(); i++) {
                    for (int j = 0; j < xArrays.get(i).size(); j++) {
                        if (map.containsKey(yNames.get(i))) {
                            JSONArray array = map.get(yNames.get(i));
                            array.add(new String[]{xArrays.get(i).get(j), yArrays.get(i).get(j)});
                            map.put(yNames.get(i), array);
                        } else {
                            JSONArray array = new JSONArray();
                            array.add(new String[]{xArrays.get(i).get(j), yArrays.get(i).get(j)});
                            map.put(yNames.get(i), array);
                        }
                    }
                }
            } else {
                for (int i = 0; i < yArrays.size(); i++) {
                    for (int j = 0; j < yArrays.get(i).size(); j++) {
                        if (map.containsKey(yNames.get(i))) {
                            JSONArray array = map.get(yNames.get(i));
                            array.add(new String[]{xArrays.get(i).get(j), yArrays.get(i).get(j)});
                            map.put(yNames.get(i), array);
                        } else {
                            JSONArray array = new JSONArray();
                            array.add(new String[]{j + "", yArrays.get(i).get(j)});
                            map.put(yNames.get(i), array);
                        }
                    }
                }
            }

            JSONArray datas = new JSONArray();
            JSONArray legendData = new JSONArray();

            int i = 0;
            for (String dataKey : map.keySet()) {//进行组装数据
                datas.add(PictureUtil.getScatterJson(map.get(dataKey), i, dataKey, new JSONObject()));
                legendData.add(dataKey);
                i++;
            }
            inputJsonObject.put("title", indexTask.getNode().getPictureTitle());
            inputJsonObject.put("xtitle", indexTask.getNode().getxTitle());
            inputJsonObject.put("ytitle", indexTask.getNode().getyTitle());
            inputJsonObject.put("datas", datas);
            inputJsonObject.put("legendData", legendData);
        } else if (indexTask.getNode().getNodeType() == NodeType.Hscatter) {//三维图
            NodeLink link = getStructLinks(nodeLinks);
            if (link != null) { //增加了特殊的处理，处理结构体数据
                inputJsonObject = getHscatterJSONObject(nodeMap.get(link.getFromNode().getKey()).getIndexNodeProcess().getNodeOutputParam(), indexTask.getNode().getPictureTitle(), indexTask.getNode().getxTitle(), indexTask.getNode().getyTitle(), indexTask.getNode().getzTitle(), link.getFromPort(), indexTask.getNode().getNodeType().getValue());
                return inputJsonObject;
            }
            //判断x轴
            List<List<String>> xArrays = new ArrayList<>();
            List<List<String>> yArrays = new ArrayList<>();
            List<List<String>> zArrays = new ArrayList<>();
            JSONArray legendData = new JSONArray();
            double min_x = 0;
            double min_y = 0;
            double min_z = 0;
            List<String> zNames = new ArrayList<>();
            for (NodeLink nLink : nodeLinks) {
                String outputParamString = nodeMap.get(nLink.getFromNode().getKey()).getIndexNodeProcess().getNodeOutputParamExp();
                JSONObject outJsonObject = JSONObject.parseObject(outputParamString);
                if (nLink.isX()) {
                    List<String> paramValueList = getPictureValue(outJsonObject, nLink);
                    xArrays.add(paramValueList);
                } else if (nLink.isY()) {
                    List<String> paramValueList = getPictureValue(outJsonObject, nLink);
                    yArrays.add(paramValueList);
                } else {
                    List<String> paramValueList = getPictureValue(outJsonObject, nLink);
                    zNames.add(nLink.getToPort());
                    zArrays.add(paramValueList);
                }
            }
            JSONArray datas = new JSONArray();
            if (xArrays.size() > 0) {
                for (int i = 0; i < xArrays.size(); i++) {
                    for (int j = 0; j < xArrays.get(i).size(); j++) {
                        JSONArray vJsonArray = new JSONArray();
                        if (i == 0 && j == 0) {
                            min_x = Double.valueOf(xArrays.get(i).get(j));
                            min_y = Double.valueOf(yArrays.get(i).get(j));
                            min_z = Double.valueOf(zArrays.get(i).get(j));
                        } else {
                            if (Double.valueOf(xArrays.get(i).get(j)) < min_x) {
                                min_x = Double.valueOf(xArrays.get(i).get(j));
                            }
                            if (Double.valueOf(yArrays.get(i).get(j)) < min_y) {
                                min_y = Double.valueOf(yArrays.get(i).get(j));
                            }
                            if (Double.valueOf(zArrays.get(i).get(j)) < min_z) {
                                min_z = Double.valueOf(zArrays.get(i).get(j));
                            }
                        }
                        vJsonArray.add(getDouble2String(xArrays.get(i).get(j)));
                        vJsonArray.add(getDouble2String(yArrays.get(i).get(j)));
                        vJsonArray.add(getDouble2String(zArrays.get(i).get(j)));
                        vJsonArray.add(zNames.get(i));
                        datas.add(vJsonArray);
                    }
                    legendData.add(zNames.get(i));
                }
            } else {
                for (int i = 0; i < yArrays.size(); i++) {
                    for (int j = 0; j < yArrays.get(i).size(); j++) {
                        if (i == 0 && j == 0) {
                            min_y = Double.valueOf(yArrays.get(i).get(j));
                            min_z = Double.valueOf(zArrays.get(i).get(j));
                        } else {
                            if (Double.valueOf(yArrays.get(i).get(j)) < min_y) {
                                min_y = Double.valueOf(yArrays.get(i).get(j));
                            }
                            if (Double.valueOf(zArrays.get(i).get(j)) < min_z) {
                                min_z = Double.valueOf(zArrays.get(i).get(j));
                            }
                        }
                        JSONArray vJsonArray = new JSONArray();
                        vJsonArray.add(String.valueOf(j));
                        vJsonArray.add(getDouble2String(yArrays.get(i).get(j)));
                        vJsonArray.add(getDouble2String(zArrays.get(i).get(j)));
                        vJsonArray.add(zNames.get(i));
                        datas.add(vJsonArray);
                    }
                    legendData.add(zNames.get(i));
                }
            }
            inputJsonObject.put("title", indexTask.getNode().getPictureTitle());
            inputJsonObject.put("xtitle", indexTask.getNode().getxTitle());
            inputJsonObject.put("ytitle", indexTask.getNode().getyTitle());
            inputJsonObject.put("ztitle", indexTask.getNode().getzTitle());
            inputJsonObject.put("min_x", getDouble2String(String.valueOf(min_x)));
            inputJsonObject.put("min_y", getDouble2String(String.valueOf(min_y)));
            inputJsonObject.put("min_z", getDouble2String(String.valueOf(min_z)));
            inputJsonObject.put("loanCounts", datas);
        }
//        writeJsFile(inputJsonObject,fileName);
        return inputJsonObject;
    }

    /**
     * 获取label
     *
     * @param nodeLinks
     * @return
     */
    private NodeLink getLabelLinks(List<NodeLink> nodeLinks) {
        for (NodeLink nodeLink : nodeLinks) {
            if (nodeLink.isL()) {
                return nodeLink;
            }
        }
        return null;
    }

    private NodeLink getStructLinks(List<NodeLink> nodeLinks) {
        for (NodeLink nodeLink : nodeLinks) {
            if ("struct".equals(nodeLink.getToPort())) {
                return nodeLink;
            }
        }
        return null;
    }

    /**
     * 获三维散点图的展示
     *
     * @return
     */
    public JSONObject getHscatterJSONObject(String jsonObject, String title, String xTitle, String yTitle, String zTitle, String fromport, String pictureType) {
        //定义json的输出格式。pictureType，input-x,input-y[]
        JSONObject inputJsonObject = new JSONObject();
        JSONObject outJsonObject = JSONObject.parseObject(jsonObject);
        JSONArray jsonArray = outJsonObject.getJSONArray(fromport);
        JSONArray legendData = new JSONArray();
        double min_x = 0;
        double min_y = 0;
        double min_z = 0;
        JSONArray datas = new JSONArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject tJson = jsonArray.getJSONObject(i);
            List<String> xArrays = getLabelValue(tJson, "x");//对x轴进行赋值
            List<String> yArrays = getLabelValue(tJson, "y");//对y轴进行赋值
            List<String> zArrays = getLabelValue(tJson, "z");//对y轴进行赋值
            legendData.add(tJson.getString("label"));//添加示例图
            for (int j = 0; j < yArrays.size(); j++) {
                JSONArray vJsonArray = new JSONArray();
                if (i == 0 && j == 0) {
                    min_x = Double.valueOf(xArrays.get(j));
                    min_y = Double.valueOf(yArrays.get(j));
                    min_z = Double.valueOf(zArrays.get(j));
                } else {
                    if (Double.valueOf(xArrays.get(j)) < min_x) {
                        min_x = Double.valueOf(xArrays.get(j));
                    }
                    if (Double.valueOf(yArrays.get(j)) < min_y) {
                        min_y = Double.valueOf(yArrays.get(j));
                    }
                    if (Double.valueOf(zArrays.get(j)) < min_z) {
                        min_z = Double.valueOf(zArrays.get(j));
                    }
                }

                vJsonArray.add(getDouble2String(xArrays.get(j)));
                vJsonArray.add(getDouble2String(yArrays.get(j)));
                vJsonArray.add(getDouble2String(zArrays.get(j)));
                vJsonArray.add(tJson.getString("label"));
                datas.add(vJsonArray);
            }
        }
        inputJsonObject.put("pictureType", pictureType);
        inputJsonObject.put("title", title);
        inputJsonObject.put("xtitle", xTitle);
        inputJsonObject.put("ytitle", yTitle);
        inputJsonObject.put("ztitle", zTitle);
        inputJsonObject.put("min_x", getDouble2String(String.valueOf(min_x)));
        inputJsonObject.put("min_y", getDouble2String(String.valueOf(min_y)));
        inputJsonObject.put("min_z", getDouble2String(String.valueOf(min_z)));
        inputJsonObject.put("legendData", legendData);
        inputJsonObject.put("loanCounts", datas);
        return inputJsonObject;
    }

    /**
     * 保留两位小数
     *
     * @param value
     * @return
     */
    private String getDouble2String(String value) {
        BigDecimal b = new BigDecimal(Double.valueOf(value).doubleValue());
        double Value = b.setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(Value);
    }


    /**
     * 获散点图的展示
     *
     * @return
     */
    public JSONObject getScatterJSONObject(String jsonObject, String title, String xTitle, String yTitle, String fromport, String pictureType) {
        //定义json的输出格式。pictureType，input-x,input-y[]
        logger.info("getScatterJSONObject问题数据：" + jsonObject);
        JSONObject inputJsonObject = new JSONObject();
        JSONObject outJsonObject = JSONObject.parseObject(jsonObject);
        JSONArray jsonArray = outJsonObject.getJSONArray(fromport);
        JSONArray datas = new JSONArray();
        JSONArray legendData = new JSONArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject tJson = jsonArray.getJSONObject(i);
            List<String> xArrays = getLabelValue(tJson, "x");//对x轴进行赋值
            List<String> yArrays = getLabelValue(tJson, "y");//对y轴进行赋值
            JSONArray datavalue = new JSONArray();
            //获取平均间距
            double minX = 0;
            double maxX = 0;
            double jWidth = 0;
            for (int j = 0; j < yArrays.size(); j++) {
                JSONArray value = new JSONArray();
                value.add(xArrays.get(j));
                value.add(yArrays.get(j));
                double xDouble = Double.valueOf(xArrays.get(j)).doubleValue();
                if (j == 0) {
                    minX = xDouble;
                    maxX = xDouble;
                } else {
                    if (xDouble < minX) {
                        minX = xDouble;
                    }
                    if (xDouble > maxX) {
                        maxX = xDouble;
                    }
                }
                datavalue.add(value);
            }
            jWidth = (maxX - minX) / 40;

            JSONObject jsonObject1 = new JSONObject();
            if (tJson.containsKey("mark")) {
                String mark = tJson.getString("mark");
                if (!StringUtil.isEmpty(mark)) {
                    JSONArray da = new JSONArray();
                    String[] mCharArray = mark.split("\\|");
                    for (String mChar : mCharArray) {
                        String[] mCh = mChar.split("_");
                        if (mCh.length >= 3) {
                            JSONObject jo = new JSONObject();
                            jo.put("xAxis", mCh[0]);
                            jo.put("yAxis", mCh[1]);
                            jo.put("value", mCh[2]);
                            da.add(jo);
                            if (mCh.length == 4) {
                                int sysbol = Integer.valueOf(mCh[3]);
                                switch (sysbol) {
                                    case 0:
                                        jsonObject1.put("symbol", "pin");
                                        break;
                                    case 1:
                                        jsonObject1.put("symbol", "circle");
                                        break;
                                    case 2:
                                        jsonObject1.put("symbol", "ract");
                                        break;
                                    case 3:
                                        jsonObject1.put("symbol", "roundRact");
                                        break;
                                    case 4:
                                        jsonObject1.put("symbol", "arrow");
                                        break;
                                }

                            }
                        }
                    }
                    if (da.size() > 0) {
                        da = sortJsonArray(da, jWidth);
                        jsonObject1.put("data", da);
                    }
                }
            }
            datas.add(PictureUtil.getScatterJson(datavalue, i, tJson.getString("label"), jsonObject1));
            legendData.add(tJson.getString("label"));
        }
        inputJsonObject.put("pictureType", pictureType);
        inputJsonObject.put("title", title);
        inputJsonObject.put("xtitle", xTitle);
        inputJsonObject.put("ytitle", yTitle);
        inputJsonObject.put("datas", datas);
        inputJsonObject.put("legendData", legendData);
        return inputJsonObject;
    }

    /**
     * 排序 json，并根据距离分配大小
     *
     * @param da
     * @param width 宽度
     * @return
     */
    private JSONArray sortJsonArray(JSONArray da, double width) {
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
            if (i > 0) {
                JSONObject preJsonObj = list.get(i - 1);
                double jWidth = jsonObj.getDoubleValue("xAxis") - preJsonObj.getDoubleValue("xAxis");
                if (jWidth < width) {
                    if (!preJsonObj.containsKey("symbolOffset")) {
                        JSONArray jArray = new JSONArray();
                        jArray.add(0);
                        jArray.add(-40);
                        jsonObj.put("symbolOffset", jArray);
                    }
                }
            }
            sort_JsonArray.add(jsonObj);
        }
        return sort_JsonArray;
    }

    //
//    {
//        symbol:'circle',
//                name: "input-y",
//            smooth:true,
//            symbolSize: 3,
//            showSymbol: true,
//            hoverAnimation: false,
//            type:'scatter',
//            data:[["5","6"],["6","7"],["7","8"],["8","9"],["9","10"],["10","11"],["11","12"],["12","13"],
//                          ["13","14"],["14","15"],["15","16"],["16","17"],["17","18"],["18","19"]],
//        color : colors[0]
//
//    }


    /**
     * 获取线图的展示
     *
     * @return
     */
    public JSONObject getLineJSONObject(String jsonObject, String title, String xTitle, String yTitle, String fromport, String pictureType) {
        //定义json的输出格式。pictureType，input-x,input-y[]
        JSONObject inputJsonObject = new JSONObject();
        JSONObject outJsonObject = JSONObject.parseObject(jsonObject);
        JSONArray jsonArray = outJsonObject.getJSONArray(fromport);
        JSONArray datas = new JSONArray();
        JSONArray legendData = new JSONArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = new JSONObject();
            JSONObject tJson = jsonArray.getJSONObject(i);
            List<String> xArr = getLabelValue(tJson, "x");//对x轴进行赋值
            List<String> yArr = getLabelValue(tJson, "y");//对y轴进行赋值
            JSONArray datavalue = new JSONArray();
            for (int j = 0; j < xArr.size(); j++) {
                JSONArray value = new JSONArray();
                value.add(xArr.get(j));
                value.add(yArr.get(j));
                datavalue.add(value);
            }
            legendData.add(tJson.getString("label"));
//            object.put("name",tJson.getString("label"));//标题
//            object.put("data",datavalue);//值
            if (pictureType.equals(NodeType.Hline.getValue())) {
                datas.add(PictureUtil.getLineJson(datavalue, i, tJson.getString("label"), true));
            } else {
                datas.add(PictureUtil.getLineJson(datavalue, i, tJson.getString("label"), false));
            }
        }
        inputJsonObject.put("pictureType", pictureType);
        inputJsonObject.put("title", title);
        inputJsonObject.put("xtitle", xTitle);
        inputJsonObject.put("ytitle", yTitle);
//        inputJsonObject.put("invNames",xArrays);
        inputJsonObject.put("datas", datas);
        inputJsonObject.put("legendData", legendData);
        return inputJsonObject;
    }


//    {
//        symbol: 'circle',
//                symbolSize: 3,
//            name:"name",
//            type:'line',
//            data:[["10","11"],["20","25"],["30","35"]],
//        showSymbol: true,
//                hoverAnimation: false,
//            itemStyle : {
//        normal : {
//            color:colors[0],
//                    lineStyle:{
//                color:colors[0]
//            }
//        }
//    }
//    }


    /**
     * 获取pie图的展示
     *
     * @return
     */
    public JSONObject getBarJSONObject(String jsonObject, String title, String xTitle, String yTitle, String fromport, String pictureType) {
        //定义json的输出格式。pictureType，input-x,input-y[]
        JSONObject inputJsonObject = new JSONObject();
        JSONObject outJsonObject = JSONObject.parseObject(jsonObject);
        JSONArray jsonArray = outJsonObject.getJSONArray(fromport);
        List<String> xArrays = new ArrayList<>(); //标题
        List<JSONObject> datas = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = new JSONObject();
            JSONObject tJson = jsonArray.getJSONObject(i);
            object.put("name", tJson.getString("label"));//标题
            xArrays = getLabelValue(tJson, "x");//对x轴进行赋值
            object.put("data", getLabelValue(tJson, "y"));//值
            datas.add(object);
        }
        inputJsonObject.put("pictureType", pictureType);
        inputJsonObject.put("title", title);
        inputJsonObject.put("xtitle", xTitle);
        inputJsonObject.put("ytitle", yTitle);
        inputJsonObject.put("invNames", xArrays);
        inputJsonObject.put("loanCounts", datas);
        return inputJsonObject;
    }

    /**
     * 获取pie图的展示
     *
     * @return
     */
    public JSONObject getPieJSONObject(String jsonObject, String title, String fromport) {
        //定义json的输出格式。pictureType，input-x,input-y[]
        JSONObject inputJsonObject = new JSONObject();
        JSONObject outJsonObject = JSONObject.parseObject(jsonObject);
        JSONArray jsonArray = outJsonObject.getJSONArray(fromport);
        List<String> xArrays = new ArrayList<>(); //标题
        List<JSONObject> datas = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = new JSONObject();
            JSONObject tJson = jsonArray.getJSONObject(i);
            object.put("name", tJson.getString("label"));//标题
            xArrays.add(tJson.getString("label"));
            object.put("value", tJson.getJSONArray("y").getDoubleValue(0));//值
            datas.add(object);
        }
        inputJsonObject.put("title", title);
        inputJsonObject.put("invNames", xArrays);
        inputJsonObject.put("loanCounts", datas);
        return inputJsonObject;
    }

    public String getEndNodeValue(IndexTask indexTask) {
        //1、获取前置任务数据
        Map<Integer, IndexTask> nodeMap = new HashMap<>();
        List<IndexTask> preIndexTaskList = indexTask.getPreIndexTask();
        for (IndexTask task : preIndexTaskList) {
            nodeMap.put(task.getNode().getKey(), task);
        }
        //2、根据线获取数据对应关系
        List<NodeLink> nodeLinks = indexTask.getNode().getInNodeLinks();
        for (NodeLink nLink : nodeLinks) {
            String outputParamString = nodeMap.get(nLink.getFromNode().getKey()).getIndexNodeProcess().getNodeOutputParamExp();
            JSONObject outJsonObject = JSONObject.parseObject(outputParamString);
            if (nLink.getFromNode().getNodeCategory() == Category.Datasource) {
                JSONArray out = outJsonObject.getJSONArray(nLink.getFromPort());
                if (out.size() > 0) {
                    return out.getString(0);
                }
            } else if (nLink.getFromNode().getNodeCategory() == Category.Constant) {//如果前置节点是常量
                return outputParamString;
            } else if (nLink.getFromNode().getNodeCategory() == Category.If) {//如果前置节点是IF
//                if(outJsonObject.getString("if").equals(nLink.getText())){
//                    setModelValue(inputJsonObject, outJsonObject, nLink,true);
//                }
            } else if (nLink.getFromNode().getNodeCategory() == Category.Model) {//如果前置节点是model
                Object object = outJsonObject.get(nLink.getFromPort());
                if (object instanceof JSONArray) {//输出是数组
                    JSONArray out = (JSONArray) object;
                    if (out.size() > 0) {
                        return out.getString(0);
                    }
                } else {
                    return outJsonObject.getString(nLink.getFromPort());
                }
            }
            return "0";
        }


        return null;
    }


    private void setNotArrayInput(JSONObject inputJsonObject, NodeLink nLink, String outputParamString) {
        if (nLink.getFromNode().getNodeCategory() == Category.Datasource) {
            JSONObject outJsonObject = JSONObject.parseObject(outputParamString);
            JSONArray out = outJsonObject.getJSONArray(nLink.getFromPort());
            setInputNotArrayByString(inputJsonObject, out.getString(0), nLink);
        } else if (nLink.getFromNode().getNodeCategory() == Category.Constant) {//如果前置节点是常量
            setInputNotArrayByString(inputJsonObject, outputParamString, nLink);
        } else if (nLink.getFromNode().getNodeCategory() == Category.If) {//如果前置节点是IF
            JSONObject outJsonObject = JSONObject.parseObject(outputParamString);
            if (outJsonObject.getString("if").equals(nLink.getText())) {
                setModelValue(inputJsonObject, outJsonObject, nLink, false);
            }
        } else if (nLink.getFromNode().getNodeCategory() == Category.Model) {//如果前置节点是model
            JSONObject outJsonObject = JSONObject.parseObject(outputParamString);
            setModelValue(inputJsonObject, outJsonObject, nLink, false);
        }
    }

    private void setArrayInput(JSONObject inputJsonObject, NodeLink nLink, String outputParamString, JSONObject outJsonObject) {
        if (nLink.getFromNode().getNodeCategory() == Category.Datasource) {
            JSONArray out = outJsonObject.getJSONArray(nLink.getFromPort());
            setInputArray(out, nLink, inputJsonObject);
        } else if (nLink.getFromNode().getNodeCategory() == Category.Constant) {//如果前置节点是常量
            setInputArrayByString(inputJsonObject, outputParamString, nLink);
        } else if (nLink.getFromNode().getNodeCategory() == Category.If) {//如果前置节点是IF
            if (outJsonObject.getString("if").equals(nLink.getText())) {
                setModelValue(inputJsonObject, outJsonObject, nLink, true);
            }
        } else if (nLink.getFromNode().getNodeCategory() == Category.Model || nLink.getFromNode().getNodeCategory() == Category.Child) {//如果前置节点是model或者是子节点
            setModelValue(inputJsonObject, outJsonObject, nLink, true);
        }
    }

    /**
     * 判断输出是否数组
     *
     * @param nodeType
     * @return
     */
    private boolean pictureInputIsArray(NodeType nodeType) {
        if (nodeType == NodeType.Line) {//折线图
            return true;
        } else if (nodeType == NodeType.Pie) {//饼图
            return false;
        } else if (nodeType == NodeType.Bar) {//柱状图
            return true;
        } else if (nodeType == NodeType.Hline) {//平滑折现图
            return true;
        } else if (nodeType == NodeType.Scatter) {//散点图
            return true;
        } else if (nodeType == NodeType.Hscatter) {//三维图
            return true;
        }
        return true;
    }


    /**
     * 判断参数是否为数组
     *
     * @param info
     * @param link
     * @return
     */
    private boolean modelInputIsArray(ModelInfo info, NodeLink link) {
        if (info.getIsSingleFun()) {//如果是单函数，默认都是数组
            return true;
        }
        if (link.isArray()) {
            return true;
        }
        return false;
    }


    /**
     * 根据值，设置数据
     *
     * @param valueObject
     * @param value
     * @param link
     */
    private void setInputNotArrayByString(JSONObject valueObject, String value, NodeLink link) {
        //1、获取单位，如果
        if ("int".equals(link.getModelPortType())) {
            valueObject.put(link.getToPort(), Integer.parseInt(ifEmpty(value)));
        } else if ("float".equals(link.getModelPortType())) {
            valueObject.put(link.getToPort(), Float.parseFloat(ifEmpty(value)));
        } else if ("double".equals(link.getModelPortType())) {
            valueObject.put(link.getToPort(), Double.parseDouble(ifEmpty(value)));
        } else if ("long".equals(link.getModelPortType())) {
            valueObject.put(link.getToPort(), Long.parseLong(ifEmpty(value)));
        } else if ("string".equals(link.getModelPortType())) {
            valueObject.put(link.getToPort(), value);
        }
    }

    /**
     * 根据值，设置数据
     *
     * @param valueObject
     * @param value
     * @param link
     */
    private void setInputArrayByString(JSONObject valueObject, String value, NodeLink link) {
        JSONArray array = new JSONArray();
        //1、获取单位，如果
        if ("int".equals(link.getModelPortType())) {
            array.add(Integer.parseInt(ifEmpty(value)));
            valueObject.put(link.getToPort(), array);
        } else if ("float".equals(link.getModelPortType())) {
            array.add(Float.parseFloat(ifEmpty(value)));
            valueObject.put(link.getToPort(), array);
        } else if ("double".equals(link.getModelPortType())) {
            array.add(Double.parseDouble(ifEmpty(value)));
            valueObject.put(link.getToPort(), array);
        } else if ("long".equals(link.getModelPortType())) {
            array.add(Long.parseLong(ifEmpty(value)));
            valueObject.put(link.getToPort(), array);
        } else if ("string".equals(link.getModelPortType())) {
            valueObject.put(link.getToPort(), value);
        }
    }

    private List<String> getPictureValue(JSONObject outJsonObject, NodeLink link) {
        List<String> outList = new ArrayList<>();
        Object object = outJsonObject.get(link.getFromPort());
        if (object instanceof JSONArray) {//输出是数组
            JSONArray out = (JSONArray) object;
            for (int i = 0; i < out.size(); i++) {
                outList.add(out.getString(i));
            }
        } else {
            outList.add(outJsonObject.getString(link.getFromPort()));
        }
        return outList;
    }

    /***
     * 获取label的值
     * @param outJsonObject
     * @param name
     * @return
     */
    private List<String> getLabelValue(JSONObject outJsonObject, String name) {
        List<String> outList = new ArrayList<>();
        JSONArray array = outJsonObject.getJSONArray(name);
        for (int i = 0; i < array.size(); i++) {
            outList.add(array.getString(i));
        }
        return outList;
    }


    private void setModelValue(JSONObject valueObject, JSONObject outJsonObject, NodeLink link, boolean isArray) {
        Object object = outJsonObject.get(link.getFromPort());
        if (object instanceof JSONArray) {//输出是数组
            JSONArray out = (JSONArray) object;
            if (isArray) {//输入是数组
                setInputArray(out, link, valueObject);
            } else {
                setInputNotArrayByString(valueObject, out.getString(0), link);
            }
        } else {// 输出是单变量
            if (isArray) {//输入是数组
                JSONArray out = new JSONArray();
                setInputArray(out, link, valueObject);
                setInputArrayByString(valueObject, outJsonObject.getString(link.getFromPort()), link);
            } else {
                setInputNotArrayByString(valueObject, outJsonObject.getString(link.getFromPort()), link);
            }
        }
    }


    /**
     * 获取数据数据
     *
     * @param out
     * @param nLink
     * @return
     */
    private void setInputArray(JSONArray out, NodeLink nLink, JSONObject valueObject) {
        JSONArray array = new JSONArray();
        if ("int".equals(nLink.getModelPortType()) || "int*".equals(nLink.getModelPortType())) {
            for (int i = 0; i < out.size(); i++) {
                array.add((int) Double.parseDouble(ifEmpty(out.getString(i))));
            }
        } else if ("float".equals(nLink.getModelPortType()) || "float*".equals(nLink.getModelPortType())) {
            for (int i = 0; i < out.size(); i++) {
                array.add(Float.parseFloat(ifEmpty(out.getString(i))));
            }
        } else if ("double".equals(nLink.getModelPortType()) || "double*".equals(nLink.getModelPortType())) {
            for (int i = 0; i < out.size(); i++) {
                array.add(Double.parseDouble(ifEmpty(out.getString(i))));
            }
        } else if ("long".equals(nLink.getModelPortType()) || "long*".equals(nLink.getModelPortType())) {
            for (int i = 0; i < out.size(); i++) {
                array.add((long) Double.parseDouble(ifEmpty(out.getString(i))));
            }
        } else if ("string".equals(nLink.getModelPortType()) || "string*".equals(nLink.getModelPortType())) {
            for (int i = 0; i < out.size(); i++) {
                array.add(out.getString(i) == null ? "" : out.getString(i));
            }
        } else {//增加对默认情况的处理，由于增加了子节点影响的
            array = out;
        }
        valueObject.put(nLink.getToPort(), array);

    }

    private String ifEmpty(Object obj) {
        if (obj == null || obj.toString().trim().equals("")) {
            return "0";
        }
        return obj.toString();
    }


    /**
     * 获取文件类型
     *
     * @param indexTask
     * @return
     */
    public static String getFileName(IndexTask indexTask, IndexInfoProcess indexInfoProcess) {
        String indexId = indexInfoProcess.getIndexId() + "";
        String businessId = indexInfoProcess.getBusinessId() + "";
        String nodeKey = indexTask.getIndexNodeProcess().getNodeKey() + "";
        return businessId + "_" + indexId + "_" + nodeKey + "_" + indexTask.getNode().getNodeType().getValue() + ".js";
    }

    /**
     * 写入js，覆盖原内容
     *
     * @param jsonStr
     * @param task
     * @param indexInfoProcess
     * @return
     * @throws Exception
     */
    public boolean writeJsFile(String jsonStr, IndexTask task, IndexInfoProcess indexInfoProcess, IndexDefinitions indexDefinitions) {

        String fileName = indexDefinitions.getEvalPath() + File.separator + getFileName(task, indexInfoProcess);
        File file = new File(fileName);
        String content = "var datas = " + jsonStr;
        RandomAccessFile mm = null;
        boolean flag = false;
        FileOutputStream fileOutputStream = null;
        try {
            if (!file.exists()) {//如果文件不存在，先创建一个
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(fileName);
            fileOutputStream.write(content.getBytes("utf-8"));
            fileOutputStream.close();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static void main(String[] args) {

//        Double.parseDouble("61979878900");
//        DecimalFormat f=new DecimalFormat();
//        double dou = Double.valueOf("");
        DecimalFormat decimalFormat = new DecimalFormat("0.00000");
        BigDecimal cc = BigDecimal.valueOf(Double.parseDouble("619876839900"));

        String bb = decimalFormat.format(Double.parseDouble("619876839900"));

//        System.out.print(f.format(Double.parseDouble("61988399")));
        System.out.print(cc);
        System.out.print(bb);

    }


}
