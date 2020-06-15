package com.tf.base.project.domain;

public class ProjectIndexParams {

	private String projectId;
	private String indexName;
	private String remarks;
	private Integer level;
	private Integer parentId;
	private String id;
	private String weightCurrent;
	private  String indexId ;

	public String getIndexId() {
		return indexId;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWeightCurrent() {
		return weightCurrent;
	}
	public void setWeightCurrent(String weightCurrent) {
		this.weightCurrent = weightCurrent;
	}
	
	
}
