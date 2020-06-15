package com.tf.base.go.process;

import com.tf.base.go.model.IndexNodeLink;

/**
 * Created by HP on 2018/4/12.
 */
public class NodeStarterLink {

    private Integer fromKey;
    private Integer toKey;
    private String fromPort;
    private String toPort;

    private String modelPortType;

    private NodeStarter fromStarter;
    private NodeStarter toStarter;

    public boolean isFloatPortType() {
        return "float".equals(modelPortType);
    }

    public boolean isDoublePortType() {
        return "double".equals(modelPortType);
    }

    public boolean isLongPortType() {
        return "long".equals(modelPortType);
    }

    public boolean isIntPortType() {
        return "int".equals(modelPortType);
    }

    public NodeStarterLink(IndexNodeLink nodeLink) {
        this.setFromKey(nodeLink.getFromKey());
        this.setToKey(nodeLink.getToKey());
        this.setFromPort(nodeLink.getFromPort());
        this.setToPort(nodeLink.getToPort());
        this.setModelPortType(nodeLink.getModelPortType());
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

    public NodeStarter getFromStarter() {
        return fromStarter;
    }

    public void setFromStarter(NodeStarter fromStarter) {
        this.fromStarter = fromStarter;
    }

    public NodeStarter getToStarter() {
        return toStarter;
    }

    public void setToStarter(NodeStarter toStarter) {
        this.toStarter = toStarter;
    }
}
