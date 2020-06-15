package com.tf.base.project.domain;

import com.tf.base.common.domain.ProjectParam;
import com.tf.base.platform.domain.IndexModelExt;

import java.util.List;

public class ProjectGroupParamBean {

	private List<ProjectParam> fParamList;

	private List<ProjectParam> cParamList;

	private Integer dataTemplateNumber;

	private String dataTemplateName;

	public List<ProjectParam> getfParamList() {
		return fParamList;
	}

	public void setfParamList(List<ProjectParam> fParamList) {
		this.fParamList = fParamList;
	}

	public List<ProjectParam> getcParamList() {
		return cParamList;
	}

	public void setcParamList(List<ProjectParam> cParamList) {
		this.cParamList = cParamList;
	}

	public Integer getDataTemplateNumber() {
		return dataTemplateNumber;
	}

	public void setDataTemplateNumber(Integer dataTemplateNumber) {
		this.dataTemplateNumber = dataTemplateNumber;
	}

	public String getDataTemplateName() {
		return dataTemplateName;
	}

	public void setDataTemplateName(String dataTemplateName) {
		this.dataTemplateName = dataTemplateName;
	}
}
