package com.tf.base.project.domain;

import com.tf.base.common.domain.Pager;

public class AssessInfoParams extends Pager {

	private String nodeId;//nodeID
	private String projectId;//项目ID
	private String indexId; //指标ID
	private String assessName; //评估名称
	private String assessVersion; //评估版本
	private String assessContent; //评估备注
	private String assessParam; //评估参数
	private String filePath; //评估文件路径
	
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getIndexId() {
		return indexId;
	}
	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}
	public String getAssessName() {
		return assessName;
	}
	public void setAssessName(String assessName) {
		this.assessName = assessName;
	}
	public String getAssessVersion() {
		return assessVersion;
	}
	public void setAssessVersion(String assessVersion) {
		this.assessVersion = assessVersion;
	}
	public String getAssessContent() {
		return assessContent;
	}
	public void setAssessContent(String assessContent) {
		this.assessContent = assessContent;
	}
	public String getAssessParam() {
		return assessParam;
	}
	public void setAssessParam(String assessParam) {
		this.assessParam = assessParam;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
	
	
	

	
}
