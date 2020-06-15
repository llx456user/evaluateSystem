package com.tf.base.project.domain;

import java.util.List;

import com.tf.base.common.domain.IndexModel;
import com.tf.base.common.domain.ProjectIndex;

import com.tf.base.common.domain.ProjectIndexAssess;
import net.sf.json.JSONObject;

public class AssessProjectIndex extends ProjectIndexAssess{

	private List<IndexModelBean> picTypes;

	private List<JSONObject> tables;

    /**
     * 评估编号
     */
	private  String no ;

	public List<IndexModelBean> getPicTypes() {
		return picTypes;
	}

	public void setPicTypes(List<IndexModelBean> picTypes) {
		this.picTypes = picTypes;
	}

	public List<JSONObject> getTables() {
		return tables;
	}

	public void setTables(List<JSONObject> tables) {
		this.tables = tables;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
}
