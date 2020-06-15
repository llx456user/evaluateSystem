package com.tf.base.project.domain;

import com.tf.base.common.domain.Pager;

public class ProjectInfoParams extends Pager {

	private String projectName;
	private String projectContent;
	private String categoryId;
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectContent() {
		return projectContent;
	}
	public void setProjectContent(String projectContent) {
		this.projectContent = projectContent;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	
	

	
}
