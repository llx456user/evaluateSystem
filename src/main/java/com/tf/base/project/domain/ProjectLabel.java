package com.tf.base.project.domain;

import java.util.List;

//项目便签
public class ProjectLabel implements  LabelType{
    private String  description; // 标签描述
    private String  label ;// 标签
    private Integer projectId ;//项目ID
    private Integer nodeId;//节点ID
    private Integer indexId;//指标ID
    private Integer nodeKey ;//指标节点
    private int  type ;//标签类型
    private String typeName;//标签名称
    private String rootName;//根节点名称

    private List<String> parentNameList;// 父节点名称
    private Integer parentLevelTwoId;//第二级父节点id
    private String nodeName;//节点名称

    private List<ProjectLabel> nodeIndexList;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getIndexId() {
        return indexId;
    }

    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    public Integer getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(Integer nodeKey) {
        this.nodeKey = nodeKey;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getParentNameList() {
        return parentNameList;
    }

    public void setParentNameList(List<String> parentNameList) {
        this.parentNameList = parentNameList;
    }

    public Integer getParentLevelTwoId() {
        return parentLevelTwoId;
    }

    public void setParentLevelTwoId(Integer parentLevelTwoId) {
        this.parentLevelTwoId = parentLevelTwoId;
    }

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public List<ProjectLabel> getNodeIndexList() {
        return nodeIndexList;
    }

    public void setNodeIndexList(List<ProjectLabel> nodeIndexList) {
        this.nodeIndexList = nodeIndexList;
    }
}
