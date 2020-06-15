package com.tf.base.platform.domain;

import com.tf.base.common.domain.Pager;

public class TempletInfoParams extends Pager {

	private String templetName;
	private String templetContent;
	private String categoryId;
	private String id;
	
	
	public String getTempletName() {
		return templetName;
	}
	public void setTempletName(String templetName) {
		this.templetName = templetName;
	}
	public String getTempletContent() {
		return templetContent;
	}
	public void setTempletContent(String templetContent) {
		this.templetContent = templetContent;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	
}
