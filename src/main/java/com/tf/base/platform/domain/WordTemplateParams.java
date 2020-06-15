package com.tf.base.platform.domain;

import com.tf.base.common.domain.Pager;

public class WordTemplateParams extends Pager {

	private String modelName;
	private String categoryId;

	public String getCategoryId() {
		return categoryId;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getModelName() {
		return modelName;
	}
}
