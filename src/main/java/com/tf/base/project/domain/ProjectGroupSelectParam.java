package com.tf.base.project.domain;


import java.util.List;

public class ProjectGroupSelectParam {

	private List<ProjectGroupTemplateParam> fParamList;
	private List<ProjectGroupTemplateParam> cParamList;

	public List<ProjectGroupTemplateParam> getfParamList() {
		return fParamList;
	}

	public void setfParamList(List<ProjectGroupTemplateParam> fParamList) {
		this.fParamList = fParamList;
	}

	public List<ProjectGroupTemplateParam> getcParamList() {
		return cParamList;
	}

	public void setcParamList(List<ProjectGroupTemplateParam> cParamList) {
		this.cParamList = cParamList;
	}
}
