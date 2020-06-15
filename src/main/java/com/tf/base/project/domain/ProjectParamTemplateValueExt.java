package com.tf.base.project.domain;

import com.tf.base.common.domain.ProjectParamTemplateValue;

public class ProjectParamTemplateValueExt extends ProjectParamTemplateValue {

	private String paramName;

	private String fileValueAndName;

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getFileValueAndName() {
		return fileValueAndName;
	}

	public void setFileValueAndName(String fileValueAndName) {
		this.fileValueAndName = fileValueAndName;
	}
}
