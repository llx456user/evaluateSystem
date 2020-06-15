package com.tf.base.go.define.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tf.base.common.domain.*;
import com.tf.base.common.utils.StringUtil;
import com.tf.base.go.define.IndexDefinitions;
import com.tf.base.go.define.IndexNode;
import com.tf.base.go.define.NodeLink;
import com.tf.base.go.type.Category;
import org.apache.commons.lang.StringUtils;

import java.util.*;

import static com.tf.base.go.type.NodeType.File;

/**
 * 主要负责 点线的关系处理
 */
public class IndexDefinitionsImpl implements IndexDefinitions {
    //包含的指标信息
    private IndexInfo indexInfo;
    private Map<Integer, IndexNode> keyNodeMap = new HashMap<>();
    String  evalPath ;
    public IndexDefinitionsImpl(IndexInfo indexInfo,String evalPath) {
        this.indexInfo = indexInfo;
        this.evalPath = evalPath ;
        if (!StringUtil.isEmpty(indexInfo.getIndexData())) {
            this.build(JSONObject.parseObject(indexInfo.getIndexData()));
        }
    }

    /**
     * 获取指标信息
     *
     * @return
     */
    @Override
    public IndexInfo getIndexInfo() {
        return this.indexInfo;
    }

    @Override
    public List<IndexNode> getNodes() {
        List<IndexNode> nodes = new ArrayList<>();
        for (Integer key : keyNodeMap.keySet()) {
            nodes.add(keyNodeMap.get(key));
        }
        return nodes;
    }

//    /**
//     * 获取Model类型的节点
//     * @return
//     */
//    @Override
//    public List<IndexNode> getModelIndexNode(){
//        List<IndexNode> modelNodes = new ArrayList<>();
//        for(Integer key :keyNodeMap.keySet()){
//            if(keyNodeMap.get(key).getNodeCategory()== Category.Model){
//                modelNodes.add(keyNodeMap.get(key));
//            }
//        }
//        return  modelNodes ;
//    }

    @Override
    public List<IndexNode> getStartModelNode() {
        List<IndexNode> modelNodes = new ArrayList<>();
        for (Integer key : keyNodeMap.keySet()) {
            if (keyNodeMap.get(key).getNodeCategory() == Category.Model) {
                if (keyNodeMap.get(key).isStartNode()) {
                    modelNodes.add(keyNodeMap.get(key));
                }
            }
        }
        return modelNodes;
    }

    /**
     * 获取定义的数据输入节点
     *
     * @return
     */
    @Override
    public List<IndexNode> getDataNode() {
        List<IndexNode> dataNodes = new ArrayList<>();
        for (Integer key : keyNodeMap.keySet()) {
            if (keyNodeMap.get(key).getNodeCategory() == Category.Datasource) {
                dataNodes.add(keyNodeMap.get(key));
            }
        }
        return dataNodes;
    }

    /**
     * 判断流程的连线是否完整，有没有多余的没有连线的节点
     *
     * @return
     */
    @Override
    public IndexNode getNotLineNode() {
        IndexNode node = null;
        for (Integer key : keyNodeMap.keySet()) {
            if (!keyNodeMap.get(key).hasLine()) {
                node = keyNodeMap.get(key);
                break;
            }
        }
        return node;
    }

    /**
     * 限定节点模型只有一个结束节点
     *
     * @return
     */
    @Override
    public boolean isUniqueEndNode() {
        List<IndexNode> endNodes = new ArrayList<>();
        for (Integer key : keyNodeMap.keySet()) {
            if (keyNodeMap.get(key).getNodeCategory() == Category.End) {
                endNodes.add(keyNodeMap.get(key));
            }
        }
        if (endNodes.size() == 1) {
            return true;
        }
        return false;
    }

    @Override
    public IndexNode getNodeByKey(Integer key) {
        return keyNodeMap.get(key);
    }

    @Override
    public boolean checkIfNode() {
        for(IndexNode node :keyNodeMap.values()){
            if(node.getNodeCategory()==Category.If){
                if(node.getInNodeLinks().size()!=2){
                    return  false ;
                }
                if(node.getOutNodeLink().size()!=2){
                    return  false ;
                }
                if(!checkInNode(node.getInNodeLinks().get(0),node.getInNodeLinks().get(1))){
                    return  false ;
                }

                if(!checkOutNode(node.getOutNodeLink().get(0),node.getOutNodeLink().get(1))){
                    return  false ;
                }

                if(StringUtils.isEmpty(node.getSettingNum())){
                    return  false ;
                }
            }
        }
        return true;
    }

    @Override
    public boolean checkName() {
        for(IndexNode node :keyNodeMap.values()){
            if(node.getNodeCategory()==Category.Constant){
                if(StringUtil.isEmpty(node.getDefaultValue())){
                    return  false ;
                }
            }else  if(node.getNodeCategory()==Category.Datasource){
                if(node.getNodeType()==File){
                    if(StringUtil.isEmpty(node.getParamName())){
                        return  false ;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean checkTitle() {
        for(IndexNode node :keyNodeMap.values()){
            if(node.getNodeCategory() == Category.Picture||node.getNodeCategory() == Category.Grid){
                if(StringUtil.isEmpty(node.getPictureTitle())){
                    return  false ;
                }
            }
        }
        return true;
    }

    @Override
    public String getEvalPath() {
        return this.evalPath;
    }

    /**
     * 核定输入线
     * @param nodeLink1
     * @param nodeLink2
     * @return
     */
    private  boolean checkInNode(NodeLink nodeLink1,NodeLink nodeLink2){
        if("input".equals(nodeLink1.getText())&&"out".equals(nodeLink2.getText())){
            return  true ;
        }
        if("out".equals(nodeLink1.getText())&&"input".equals(nodeLink2.getText())){
            return  true ;
        }
        return  false  ;
    }


    /**
     * 核定输出线
     * @param nodeLink1
     * @param nodeLink2
     * @return
     */
    private  boolean checkOutNode(NodeLink nodeLink1,NodeLink nodeLink2){
        if("yes".equals(nodeLink1.getText())&&"no".equals(nodeLink2.getText())){
            return  true ;
        }
        if("no".equals(nodeLink1.getText())&&"yes".equals(nodeLink2.getText())){
            return  true ;
        }

        return  false ;
    }

    private void buildNodeArray(JSONArray nodeDataArray) {
        if (null != nodeDataArray && nodeDataArray.size() > 0) {
            for (int i = 0; i < nodeDataArray.size(); i++) {
                JSONObject json = nodeDataArray.getJSONObject(i);
                IndexNode node = IndexNodeImpl.build(json);
                if (null != node) {
                    keyNodeMap.put(node.getKey(), node);
                }
            }
        }
    }

    private void buildNodeLinkArray(JSONArray linkDataArray) {
        if (null != linkDataArray && linkDataArray.size() > 0) {
            for (int i = 0; i < linkDataArray.size(); i++) {
                JSONObject json = linkDataArray.getJSONObject(i);
                Integer fromKey = json.getInteger("from");
                Integer toKey = json.getInteger("to");
                String fromPort = json.getString("fromPort");
                String toPort = json.getString("toPort");
                IndexNode fromNode = keyNodeMap.get(fromKey);
                IndexNode toNode = keyNodeMap.get(toKey);
                NodeLinkImpl link = new NodeLinkImpl();
                link.setFromPort(fromPort);
                link.setToPort(toPort);
                link.setToNode(toNode);
                link.setFromNode(fromNode);
                if (json.containsKey("text")) {
                    link.setText(json.getString("text"));
                }
                if (null != toNode) {
                    if (null != toNode.getInServices()) {
                        if (toNode.getInServices().getJSONObject(toPort).containsKey("modelPortType")) {
                            link.setModelPortType(toNode.getInServices().getJSONObject(toPort).getString("modelPortType"));
                        }
                        if (toNode.getInServices().getJSONObject(toPort).containsKey("isArray")) {
                            link.setArray(toNode.getInServices().getJSONObject(toPort).getBooleanValue("isArray"));
                        }
                        if (toNode.getInServices().getJSONObject(toPort).containsKey("isX")) {
                            link.setX(toNode.getInServices().getJSONObject(toPort).getBooleanValue("isX"));
                        }
                        if (toNode.getInServices().getJSONObject(toPort).containsKey("isY")) {
                            link.setY(toNode.getInServices().getJSONObject(toPort).getBooleanValue("isY"));
                        }
                        if(toNode.getInServices().getJSONObject(toPort).containsKey("isL")){
                            link.setL(toNode.getInServices().getJSONObject(toPort).getBooleanValue("isL"));
                        }
                    }
                    toNode.addInNodeLink(link);
                }
                if (null != fromNode) {
                    fromNode.addOutNodeLink(link);
                }
            }
        }
    }


    /**
     * @param jsonObject
     */
    public void build(JSONObject jsonObject) {
        JSONArray nodeDataArray = jsonObject.getJSONArray("nodeDataArray");
        if (null != nodeDataArray && nodeDataArray.size() > 0) {
            buildNodeArray(nodeDataArray);
        }
        JSONArray linkDataArray = jsonObject.getJSONArray("linkDataArray");
        if (null != linkDataArray && linkDataArray.size() > 0) {
            buildNodeLinkArray(linkDataArray);
        }
    }


}
