package com.tf.base.project.domain;

import com.tf.base.common.domain.AssessParam;
import com.tf.base.common.domain.ProjectParamTemplate;

import java.util.List;

public class ProjectParamTemplateBean {

	private String indexName;
	
	private List<ProjectParamTemplate> fParamList;
	
	private List<ProjectParamTemplate> cParamList;

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public List<ProjectParamTemplate> getfParamList() {
		return fParamList;
	}

	public void setfParamList(List<ProjectParamTemplate> fParamList) {
		this.fParamList = fParamList;
	}

	public List<ProjectParamTemplate> getcParamList() {
		return cParamList;
	}

	public void setcParamList(List<ProjectParamTemplate> cParamList) {
		this.cParamList = cParamList;
	}
	
	
}
