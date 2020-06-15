package com.tf.base.go.model;

import com.alibaba.fastjson.JSONObject;
import com.tf.base.go.model.IndexNodeDefinition;

/**
 * Created by HP on 2018/4/12.
 */
public class IndexNodeLink {

    private Integer fromKey;
    private Integer toKey;
    private String fromPort;
    private String toPort;

    private String modelPortType;

    private IndexNodeDefinition fromNode;
    private IndexNodeDefinition toNode;

    public static IndexNodeLink build(JSONObject json) {
        if(null != json){
            IndexNodeLink link = new IndexNodeLink();
            Integer fromKey = json.getInteger("from");
            Integer toKey = json.getInteger("to");
            String fromPort = json.getString("fromPort");
            String toPort = json.getString("toPort");
            link.setFromKey(fromKey);
            link.setToKey(toKey);
            link.setFromPort(fromPort);
            link.setToPort(toPort);
            return link;
        }
        return null;
    }

    public Integer getFromKey() {
        return fromKey;
    }

    public void setFromKey(Integer fromKey) {
        this.fromKey = fromKey;
    }

    public Integer getToKey() {
        return toKey;
    }

    public void setToKey(Integer toKey) {
        this.toKey = toKey;
    }

    public String getFromPort() {
        return fromPort;
    }

    public void setFromPort(String fromPort) {
        this.fromPort = fromPort;
    }

    public String getToPort() {
        return toPort;
    }

    public void setToPort(String toPort) {
        this.toPort = toPort;
    }

    public String getModelPortType() {
        return modelPortType;
    }

    public void setModelPortType(String modelPortType) {
        this.modelPortType = modelPortType;
    }

    public IndexNodeDefinition getFromNode() {
        return fromNode;
    }

    public void setFromNode(IndexNodeDefinition fromNode) {
        this.fromNode = fromNode;
    }

    public IndexNodeDefinition getToNode() {
        return toNode;
    }

    public void setToNode(IndexNodeDefinition toNode) {
        this.toNode = toNode;
    }
}
