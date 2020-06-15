package com.tf.base.datasource.domain;

import com.tf.base.common.domain.DatasourceDb;

public class DbQueryResult extends DatasourceDb{

	private String updateTimeStr;
	private String url;

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
