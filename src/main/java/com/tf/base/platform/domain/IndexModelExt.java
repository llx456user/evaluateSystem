package com.tf.base.platform.domain;

import com.tf.base.common.domain.IndexModel;

import java.security.PrivateKey;

public class IndexModelExt extends IndexModel {
    private Integer projectParamId;

    private Integer isSelfDefined;

    private String constValueOrFileName;

    private String filePath;

    private Integer nodeId;

    private String groupTemplateData;


    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getProjectParamId() {
        return projectParamId;
    }

    public void setProjectParamId(Integer projectParamId) {
        this.projectParamId = projectParamId;
    }

    public Integer getIsSelfDefined() {
        return isSelfDefined;
    }

    public void setIsSelfDefined(Integer isSelfDefined) {
        this.isSelfDefined = isSelfDefined;
    }

    public String getConstValueOrFileName() {
        return constValueOrFileName;
    }

    public void setConstValueOrFileName(String constValueOrFileName) {
        this.constValueOrFileName = constValueOrFileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getGroupTemplateData() {
        return groupTemplateData;
    }

    public void setGroupTemplateData(String groupTemplateData) {
        this.groupTemplateData = groupTemplateData;
    }
}
