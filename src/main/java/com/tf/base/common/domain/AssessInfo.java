package com.tf.base.common.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.*;

@Table(name = "assess_info")
public class AssessInfo {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /**
     * 项目ID
     */
    @Column(name = "project_id")
    private Integer projectId;
    
    /**
     * 指标ID
     */
    @Column(name = "index_id")
    private Integer indexId;
    /**
     * 节点ID
     */
    @Column(name = "node_id")
    private Integer nodeId;

    /**
     * 评估名称
     */
    @Column(name = "assess_name")
    private String assessName;

    /**
     * 评估备注
     */
    @Column(name = "assess_content")
    private String assessContent;
    
    /**
     * 评估版本
     */
    @Column(name = "assess_version")
    private String assessVersion;
    
    /**
     * 评估参数
     */
    @Column(name = "assess_param")
    private String assessParam;
    
    /**
     * 评估结果
     */
    @Column(name = "assess_result")
    private String assessResult;
    
    /**
     * 评估状态[0:未评估；1：评估中，2：评估成功，3：评估失败]
     */
    @Column(name = "assess_status")
    private Integer assessStatus;
    
    @Transient
    private String assessStatusStr;
    
    /**
     * 创建人ID
     */
    @Column(name = "create_uid")
    private Integer createUid;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 更新人ID
     */
    @Column(name = "update_uid")
    private Integer updateUid;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private String updateTime;
    
    @Transient
    private String updateTimeStr;

    /**
     * 是否删除【0:否1：是】
     */
    private Integer isdelete;

    

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取评估名称
     *
     * @return assess_name - 评估名称
     */
    public String getAssessName() {
        return assessName;
    }

    /**
     * 设置评估名称
     *
     * @param assessName 评估名称
     */
    public void setAssessName(String assessName) {
        this.assessName = assessName;
    }

    /**
     * 获取评估版本
     *
     * @return assess_version - 评估版本
     */
    public String getAssessVersion() {
        return assessVersion;
    }

    /**
     * 设置评估版本
     *
     * @param assessVersion 评估版本
     */
    public void setAssessVersion(String assessVersion) {
        this.assessVersion = assessVersion;
    }

    /**
     * 获取指标ID
     *
     * @return index_id - 指标ID
     */
    public Integer getIndexId() {
        return indexId;
    }

    /**
     * 设置指标ID
     *
     * @param indexId 指标ID
     */
    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }


    /**
     * 获取创建人ID
     *
     * @return create_uid - 创建人ID
     */
    public Integer getCreateUid() {
        return createUid;
    }

    /**
     * 设置创建人ID
     *
     * @param createUid 创建人ID
     */
    public void setCreateUid(Integer createUid) {
        this.createUid = createUid;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            this.createTime = simpleDateFormat.format(createTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取更新人ID
     *
     * @return update_uid - 更新人ID
     */
    public Integer getUpdateUid() {
        return updateUid;
    }

    /**
     * 设置更新人ID
     *
     * @param updateUid 更新人ID
     */
    public void setUpdateUid(Integer updateUid) {
        this.updateUid = updateUid;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.updateTime =simpleDateFormat.format(updateTime);
    }

    /**
     * 获取是否删除【0:否1：是】
     *
     * @return isdelete - 是否删除【0:否1：是】
     */
    public Integer getIsdelete() {
        return isdelete;
    }

    /**
     * 设置是否删除【0:否1：是】
     *
     * @param isdelete 是否删除【0:否1：是】
     */
    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    /**
     * 获取评估备注
     *
     * @return assess_content - 评估备注
     */
    public String getAssessContent() {
        return assessContent;
    }

    /**
     * 设置评估备注
     *
     * @param assessContent 评估备注
     */
    public void setAssessContent(String assessContent) {
        this.assessContent = assessContent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", assessName=").append(assessName);
        sb.append(", assessVersion=").append(assessVersion);
        sb.append(", indexId=").append(indexId);
        sb.append(", createUid=").append(createUid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUid=").append(updateUid);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", assessContent=").append(assessContent);
        sb.append("]");
        return sb.toString();
    }

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getAssessParam() {
		return assessParam;
	}

	public void setAssessParam(String assessParam) {
		this.assessParam = assessParam;
	}

	public String getAssessResult() {
		return assessResult;
	}

	public void setAssessResult(String assessResult) {
		this.assessResult = assessResult;
	}

	public Integer getAssessStatus() {
		return assessStatus;
	}

	public void setAssessStatus(Integer assessStatus) {
		this.assessStatus = assessStatus;
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public String getAssessStatusStr() {
		return assessStatusStr;
	}

	public void setAssessStatusStr(String assessStatusStr) {
		this.assessStatusStr = assessStatusStr;
	}
}