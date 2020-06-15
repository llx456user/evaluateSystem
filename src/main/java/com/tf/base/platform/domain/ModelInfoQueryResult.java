package com.tf.base.platform.domain;

import com.tf.base.common.domain.ModelInfo;

public class ModelInfoQueryResult extends ModelInfo{
	
	private String updateTimeStr;
	
	private String modelStatusStr;

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public String getModelStatusStr() {
		return modelStatusStr;
	}

	public void setModelStatusStr(String modelStatusStr) {
		this.modelStatusStr = modelStatusStr;
	}
}
