package com.tf.base.platform.domain;

import com.tf.base.common.domain.Pager;

public class ModelInfoParams extends Pager {

	private String modelName;
	private String modelContent;
	private String categoryId;
	
	
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getModelContent() {
		return modelContent;
	}
	public void setModelContent(String modelContent) {
		this.modelContent = modelContent;
	}

	
}
