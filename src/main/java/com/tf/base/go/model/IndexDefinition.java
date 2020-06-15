package com.tf.base.go.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tf.base.go.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexDefinition {

    private Map<Integer, IndexNodeDefinition> keyNodeMap;

    private List<IndexNodeLink> linkList;

    private IndexDefinition(JSONObject indexJson) {
        buildIndex(indexJson);
    }

    private void buildIndex(JSONObject indexJson) {
        if (null != indexJson) {
            JSONArray nodeDataArray = indexJson.getJSONArray("nodeDataArray");
            if (null != nodeDataArray && nodeDataArray.size() > 0) {
                buildNodeArray(nodeDataArray);
            }
            JSONArray linkDataArray = indexJson.getJSONArray("linkDataArray");
            if (null != linkDataArray && linkDataArray.size() > 0) {
                buildNodeLinkArray(linkDataArray);
            }
        }
    }

    private void buildNodeArray(JSONArray nodeDataArray) {
        if (null != nodeDataArray && nodeDataArray.size() > 0) {
            keyNodeMap = new HashMap<Integer, IndexNodeDefinition>();
            for (int i = 0; i < nodeDataArray.size(); i++) {
                JSONObject json = nodeDataArray.getJSONObject(i);
                IndexNodeDefinition node = IndexNodeDefinition.build(json);
                if (null != node) {
                    keyNodeMap.put(node.getKey(), node);
                }
            }
        }
    }

    private void buildNodeLinkArray(JSONArray linkDataArray) {
        if (null != linkDataArray && linkDataArray.size() > 0) {
            linkList = new ArrayList<IndexNodeLink>();
            for (int i = 0; i < linkDataArray.size(); i++) {
                JSONObject json = linkDataArray.getJSONObject(i);
                IndexNodeLink link = IndexNodeLink.build(json);
                if(null != link) {
                    IndexNodeDefinition fromNode = keyNodeMap.get(link.getFromKey());
                    IndexNodeDefinition toNode = keyNodeMap.get(link.getToKey());
                    link.setFromNode(fromNode);
                    link.setToNode(toNode);
                    if (null != toNode) {
                        if (null != toNode.getInServices()) {
                            JSONObject portJson = toNode.getInServices().getJSONObject(link.getToPort());
                            if (null != portJson && portJson.containsKey("modelPortType")) {
                                link.setModelPortType(portJson.getString("modelPortType"));
                            }
                        }
                        toNode.addInNodeLink(link);
                    }
                    if (null != fromNode) {
                        fromNode.addOutNodeLink(link);
                    }
                    linkList.add(link);
                }
            }
        }
    }

    public static IndexDefinition build(JSONObject indexJson) {
        return new IndexDefinition(indexJson);
    }

    public List<IndexNodeLink> getLinkList() {
        return linkList;
    }

    public List<IndexNodeDefinition> getNodes() {
        return null == keyNodeMap ? null : new ArrayList<IndexNodeDefinition>(keyNodeMap.values());
    }


    private IndexNodeDefinition getNodeByKey(int key) {
        return null == keyNodeMap ? null : keyNodeMap.get(key);
    }


    private List<IndexNodeDefinition> getStartInputNodes() {
        if (null != keyNodeMap && keyNodeMap.size() > 0) {
            List<IndexNodeDefinition> list = new ArrayList<IndexNodeDefinition>();
            for (IndexNodeDefinition node : keyNodeMap.values()) {
                if (null == node.getPreNodes()) {//todo
                    list.add(node);
                }
            }
            return list;
        }
        return null;
    }


    private List<IndexNodeDefinition> getOutNodes() {
        if (null != keyNodeMap && keyNodeMap.size() > 0) {
            List<IndexNodeDefinition> list = new ArrayList<IndexNodeDefinition>();
            for (IndexNodeDefinition node : keyNodeMap.values()) {
                if (null == node.getNextNodes()) {
                    list.add(node);
                }
            }
            return list;
        }
        return null;
    }


    public String toString() {
        List<IndexNodeDefinition> startNodes = getStartInputNodes();
        if (null != startNodes && startNodes.size() > 0) {
            StringBuffer sb = new StringBuffer("[");
            for (int i = 0; i < startNodes.size(); i++) {
                IndexNodeDefinition node = startNodes.get(i);
                sb.append(node.toString());
                if (i < startNodes.size() - 1) {
                    sb.append(",");
                }
            }
            sb.append("]");
            return sb.toString();
        }
        return super.toString();
    }
}
