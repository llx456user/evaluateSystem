package com.tf.base.common.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "project_index_assess")
public class ProjectIndexAssess {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 项目指标ID
     */
    @Column(name = "project_index_id")
    private Integer projectIndexId;



    /**
     * 评估结果id
     */
    @Column(name = "assess_id")
    private Integer assessId;
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
     * 节点名称
     */
    @Column(name = "index_name")
    private String indexName;

    /**
     * 节点状态【0未执行，1:执行成功 2：执行失败】
     */
    @Column(name = "index_status")
    private Integer indexStatus;

    /**
     * 父节点ID
     */
    private Integer parentid;

    /**
     * 节点层级
     */
    @Column(name = "index_level")
    private Integer indexLevel;
    
    /**
     * 计算结果
     */
    @Column(name = "index_value")
    private String indexValue;

    /**
     * 指标key
     */
    @Column(name = "index_key")
    private Integer indexKey;

    /**
     * 指标模板ID
     */
    @Column(name = "index_template_id")
    private Integer indexTemplateId;

    /**
     * 创建人ID
     */
    @Column(name = "create_uid")
    private Integer createUid;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新人ID
     */
    @Column(name = "update_uid")
    private Integer updateUid;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否删除【0:否1：是】
     */
    private Integer isdelete;

    /**
     * 备注
     */
    private String remark;

    /**
     * 权重
     */
    private String weight;
    
    /**
     * 当前节点权重
     */
    @Column(name = "weight_current")
    private String weightCurrent;

    /**
     * ahp配置
     */
    private String ahp;
    
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
     * 获取项目ID
     *
     * @return project_id - 项目ID
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * 设置项目ID
     *
     * @param projectId 项目ID
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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
     * 获取节点名称
     *
     * @return index_name - 节点名称
     */
    public String getIndexName() {
        return indexName;
    }

    /**
     * 设置节点名称
     *
     * @param indexName 节点名称
     */
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    /**
     * 获取节点状态【0未执行，1:执行成功 2：执行失败】
     *
     * @return index_status - 节点状态【0未执行，1:执行成功 2：执行失败】
     */
    public Integer getIndexStatus() {
        return indexStatus;
    }

    /**
     * 设置节点状态【0未执行，1:执行成功 2：执行失败】
     *
     * @param indexStatus 节点状态【0未执行，1:执行成功 2：执行失败】
     */
    public void setIndexStatus(Integer indexStatus) {
        this.indexStatus = indexStatus;
    }

    /**
     * 获取父节点ID
     *
     * @return parentid - 父节点ID
     */
    public Integer getParentid() {
        return parentid;
    }

    /**
     * 设置父节点ID
     *
     * @param parentid 父节点ID
     */
    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    /**
     * 获取节点层级
     *
     * @return index_level - 节点层级
     */
    public Integer getIndexLevel() {
        return indexLevel;
    }

    /**
     * 设置节点层级
     *
     * @param indexLevel 节点层级
     */
    public void setIndexLevel(Integer indexLevel) {
        this.indexLevel = indexLevel;
    }

    /**
     * 获取计算结果
     *
     * @return index_value - 计算结果
     */
    public String getIndexValue() {
        return indexValue;
    }

    /**
     * 设置计算结果
     *
     * @param indexValue 计算结果
     */
    public void setIndexValue(String indexValue) {
        this.indexValue = indexValue;
    }

    /**
     * 获取指标key
     *
     * @return index_key - 指标key
     */
    public Integer getIndexKey() {
        return indexKey;
    }

    /**
     * 设置指标key
     *
     * @param indexKey 指标key
     */
    public void setIndexKey(Integer indexKey) {
        this.indexKey = indexKey;
    }

    /**
     * 获取指标模板ID
     *
     * @return index_template_id - 指标模板ID
     */
    public Integer getIndexTemplateId() {
        return indexTemplateId;
    }

    /**
     * 设置指标模板ID
     *
     * @param indexTemplateId 指标模板ID
     */
    public void setIndexTemplateId(Integer indexTemplateId) {
        this.indexTemplateId = indexTemplateId;
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
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取权重
     *
     * @return weight - 权重
     */
    public String getWeight() {
        return weight;
    }

    /**
     * 设置权重
     *
     * @param weight 权重
     */
    public void setWeight(String weight) {
        this.weight = weight;
    }

    /**
     * 获取ahp配置
     *
     * @return ahp - ahp配置
     */
    public String getAhp() {
        return ahp;
    }

    /**
     * 设置ahp配置
     *
     * @param ahp ahp配置
     */
    public void setAhp(String ahp) {
        this.ahp = ahp;
    }
    public Integer getProjectIndexId() {
        return projectIndexId;
    }

    public void setProjectIndexId(Integer projectIndexId) {
        this.projectIndexId = projectIndexId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", indexId=").append(indexId);
        sb.append(", indexName=").append(indexName);
        sb.append(", indexStatus=").append(indexStatus);
        sb.append(", parentid=").append(parentid);
        sb.append(", indexLevel=").append(indexLevel);
        sb.append(", indexValue=").append(indexValue);
        sb.append(", indexKey=").append(indexKey);
        sb.append(", indexTemplateId=").append(indexTemplateId);
        sb.append(", createUid=").append(createUid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUid=").append(updateUid);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", remark=").append(remark);
        sb.append(", weight=").append(weight);
        sb.append(", ahp=").append(ahp);
        sb.append("]");
        return sb.toString();
    }

	public String getWeightCurrent() {
		return weightCurrent;
	}

	public void setWeightCurrent(String weightCurrent) {
		this.weightCurrent = weightCurrent;
	}

	public Integer getAssessId() {
		return assessId;
	}

	public void setAssessId(Integer assessId) {
		this.assessId = assessId;
	}
}