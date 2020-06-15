package com.tf.base.project.domain;


import net.sf.json.JSONObject;

import java.util.List;

public class ProjectBatchAssessTemplateParam {

	private List<List<JSONObject>> assessParamResultLists;

	private List<String> assessNameResultLists;


	public List<List<JSONObject>> getAssessParamResultLists() {
		return assessParamResultLists;
	}

	public void setAssessParamResultLists(List<List<JSONObject>> assessParamResultLists) {
		this.assessParamResultLists = assessParamResultLists;
	}

	public List<String> getAssessNameResultLists() {
		return assessNameResultLists;
	}

	public void setAssessNameResultLists(List<String> assessNameResultLists) {
		this.assessNameResultLists = assessNameResultLists;
	}
}
