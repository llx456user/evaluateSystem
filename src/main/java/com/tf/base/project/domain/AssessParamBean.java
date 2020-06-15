package com.tf.base.project.domain;

import java.util.List;


public class AssessParamBean {

	private String indexName;
	private List<String> parentNameList;//父节点名称
	private Integer parentId;//父节点id
	private String rootName;// 根节点名称
	
	private List<AssessParamExt> fParamList;
	
	private List<AssessParamExt> cParamList;

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public List<AssessParamExt> getfParamList() {
		return fParamList;
	}

	public void setfParamList(List<AssessParamExt> fParamList) {
		this.fParamList = fParamList;
	}

	public List<AssessParamExt> getcParamList() {
		return cParamList;
	}

	public void setcParamList(List<AssessParamExt> cParamList) {
		this.cParamList = cParamList;
	}


	public List<String> getParentNameList() {
		return parentNameList;
	}

	public void setParentNameList(List<String> parentNameList) {
		this.parentNameList = parentNameList;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getRootName() {
		return rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}
}
