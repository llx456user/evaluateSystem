package com.tf.base.datasource.domain;

import com.tf.base.common.domain.DatasourceFile;

public class FileQueryResult extends DatasourceFile{

	private String showname;
	private String updateTimeStr;

	public String getShowname() {
		return showname;
	}

	public void setShowname(String showname) {
		this.showname = showname;
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}
	
	
}
