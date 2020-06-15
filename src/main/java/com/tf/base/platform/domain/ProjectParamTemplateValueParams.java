package com.tf.base.platform.domain;

import com.tf.base.common.domain.Pager;

public class ProjectParamTemplateValueParams extends Pager {

	private String dataName;
	private String projectId;
	private Integer dataTemplateNumber;
	private Integer groupDataTemplateNumber;


	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Integer getDataTemplateNumber() {
		return dataTemplateNumber;
	}

	public void setDataTemplateNumber(Integer dataTemplateNumber) {
		this.dataTemplateNumber = dataTemplateNumber;
	}

	public Integer getGroupDataTemplateNumber() {
		return groupDataTemplateNumber;
	}

	public void setGroupDataTemplateNumber(Integer groupDataTemplateNumber) {
		this.groupDataTemplateNumber = groupDataTemplateNumber;
	}
}
