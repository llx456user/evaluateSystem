package com.tf.base.project.domain;

import com.tf.base.platform.domain.IndexModelExt;

import java.util.List;

public class ProjectParamBean {

	private String indexName;
	private Integer indexId;
	private Integer isFinishFlg;// 0-未完成，1-已完成
	private Integer randomNumber; //0-100随机数
    private List<String> parentNameList;//父节点名称
	private Integer parentId;//父节点id
    private String rootName;// 根节点名称

	private List<IndexModelExt> fParamList;

	private List<IndexModelExt> cParamList;

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public List<IndexModelExt> getfParamList() {
		return fParamList;
	}

	public void setfParamList(List<IndexModelExt> fParamList) {
		this.fParamList = fParamList;
	}

	public List<IndexModelExt> getcParamList() {
		return cParamList;
	}

	public void setcParamList(List<IndexModelExt> cParamList) {
		this.cParamList = cParamList;
	}


	public Integer getIndexId() {
		return indexId;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public Integer getIsFinishFlg() {
		return isFinishFlg;
	}

	public void setIsFinishFlg(Integer isFinishFlg) {
		this.isFinishFlg = isFinishFlg;
	}

	public Integer getRandomNumber() {
		return randomNumber;
	}

	public void setRandomNumber(Integer randomNumber) {
		this.randomNumber = randomNumber;
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
