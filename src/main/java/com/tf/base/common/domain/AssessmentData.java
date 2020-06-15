package com.tf.base.common.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "assessment_data")
public class AssessmentData {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 数据集ID
     */
    @Column(name = "project_id")
    private Integer projectId;

    /**
     * 模型ID
     */
    @Column(name = "model_id")
    private Integer modelId;

    /**
     * 指标ID
     */
    @Column(name = "assess_id")
    private Integer assessId;

    /**
     * 评估状态[0:评估中，1：评估成功，2：评估失败]
     */
    @Column(name = "assess_status")
    private Integer assessStatus;

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
     * 评估内容
     */
    @Column(name = "assess_content")
    private String assessContent;

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
     * 获取数据集ID
     *
     * @return project_id - 数据集ID
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * 设置数据集ID
     *
     * @param projectId 数据集ID
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * 获取模型ID
     *
     * @return model_id - 模型ID
     */
    public Integer getModelId() {
        return modelId;
    }

    /**
     * 设置模型ID
     *
     * @param modelId 模型ID
     */
    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    /**
     * 获取指标ID
     *
     * @return assess_id - 指标ID
     */
    public Integer getAssessId() {
        return assessId;
    }

    /**
     * 设置指标ID
     *
     * @param assessId 指标ID
     */
    public void setAssessId(Integer assessId) {
        this.assessId = assessId;
    }

    /**
     * 获取评估状态[0:评估中，1：评估成功，2：评估失败]
     *
     * @return assess_status - 评估状态[0:评估中，1：评估成功，2：评估失败]
     */
    public Integer getAssessStatus() {
        return assessStatus;
    }

    /**
     * 设置评估状态[0:评估中，1：评估成功，2：评估失败]
     *
     * @param assessStatus 评估状态[0:评估中，1：评估成功，2：评估失败]
     */
    public void setAssessStatus(Integer assessStatus) {
        this.assessStatus = assessStatus;
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
     * 获取评估内容
     *
     * @return assess_content - 评估内容
     */
    public String getAssessContent() {
        return assessContent;
    }

    /**
     * 设置评估内容
     *
     * @param assessContent 评估内容
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
        sb.append(", projectId=").append(projectId);
        sb.append(", modelId=").append(modelId);
        sb.append(", assessId=").append(assessId);
        sb.append(", assessStatus=").append(assessStatus);
        sb.append(", createUid=").append(createUid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUid=").append(updateUid);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", assessContent=").append(assessContent);
        sb.append("]");
        return sb.toString();
    }
}