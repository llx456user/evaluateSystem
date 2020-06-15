package com.tf.base.datasource.domain;

import com.tf.base.common.domain.Pager;

public class FileParams extends Pager {

	private String categoryid;
	private String field;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}
	
}
