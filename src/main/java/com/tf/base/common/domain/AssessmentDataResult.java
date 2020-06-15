package com.tf.base.common.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "assessment_data_result")
public class AssessmentDataResult {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 评估结果ID
     */
    @Column(name = "assessment_id")
    private Integer assessmentId;

    /**
     * KEYID
     */
    @Column(name = "key_id")
    private Integer keyId;

    /**
     * 模型ID
     */
    @Column(name = "model_id")
    private Integer modelId;

    /**
     * 是否最后[0:否，1：是]
     */
    @Column(name = "isFinal")
    private Integer isfinal;

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
     * 评估结果
     */
    @Column(name = "assess_result")
    private String assessResult;

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
     * 获取评估结果ID
     *
     * @return assessment_id - 评估结果ID
     */
    public Integer getAssessmentId() {
        return assessmentId;
    }

    /**
     * 设置评估结果ID
     *
     * @param assessmentId 评估结果ID
     */
    public void setAssessmentId(Integer assessmentId) {
        this.assessmentId = assessmentId;
    }

    /**
     * 获取KEYID
     *
     * @return key_id - KEYID
     */
    public Integer getKeyId() {
        return keyId;
    }

    /**
     * 设置KEYID
     *
     * @param keyId KEYID
     */
    public void setKeyId(Integer keyId) {
        this.keyId = keyId;
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
     * 获取是否最后[0:否，1：是]
     *
     * @return isFinal - 是否最后[0:否，1：是]
     */
    public Integer getIsfinal() {
        return isfinal;
    }

    /**
     * 设置是否最后[0:否，1：是]
     *
     * @param isfinal 是否最后[0:否，1：是]
     */
    public void setIsfinal(Integer isfinal) {
        this.isfinal = isfinal;
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
     * 获取评估结果
     *
     * @return assess_result - 评估结果
     */
    public String getAssessResult() {
        return assessResult;
    }

    /**
     * 设置评估结果
     *
     * @param assessResult 评估结果
     */
    public void setAssessResult(String assessResult) {
        this.assessResult = assessResult;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", assessmentId=").append(assessmentId);
        sb.append(", keyId=").append(keyId);
        sb.append(", modelId=").append(modelId);
        sb.append(", isfinal=").append(isfinal);
        sb.append(", createUid=").append(createUid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUid=").append(updateUid);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", assessResult=").append(assessResult);
        sb.append("]");
        return sb.toString();
    }
}