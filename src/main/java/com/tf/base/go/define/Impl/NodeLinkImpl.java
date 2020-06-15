package com.tf.base.go.define.Impl;


import com.tf.base.go.define.IndexNode;
import com.tf.base.go.define.NodeLink;

/**
 * Created by HP on 2018/4/12.
 */
public class NodeLinkImpl implements NodeLink {
    private String modelPortType;
    private boolean  isArray ;
    private  String text ;
    private String fromPort;
    private String toPort;
    private IndexNode fromNode;
    private IndexNode toNode;
    private boolean isX;
    private boolean isY;
    private  boolean isL;
    @Override
    public IndexNode getFromNode() {
        return this.fromNode;
    }

    @Override
    public String getFromPort() {
        return this.fromPort;
    }

    @Override
    public IndexNode getToNode() {
        return this.toNode;
    }

    @Override
    public String getToPort() {
        return this.toPort;
    }
    @Override
    public String getModelPortType(){
        return  this.modelPortType ;
    }



    public boolean isArray() {
        return isArray;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public boolean isX() {
        return isX;
    }

    @Override
    public boolean isY(){
        return isY;
    }

    public  void setY(boolean isY){
        this.isY=isY;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setArray(boolean isArray) {
        this.isArray = isArray;
    }
    public  void setX(boolean isX){
        this.isX=isX;
    }

    @Override
    public boolean isL() {
        return isL;
    }

    public void setL(boolean l) {
        isL = l;
    }

    public void setModelPortType(String modelPortType) {
        this.modelPortType = modelPortType;
    }

    public void setFromPort(String fromPort) {
        this.fromPort = fromPort;
    }

    public void setToPort(String toPort) {
        this.toPort = toPort;
    }

    public void setFromNode(IndexNode fromNode) {
        this.fromNode = fromNode;
    }

    public void setToNode(IndexNode toNode) {
        this.toNode = toNode;
    }
}
