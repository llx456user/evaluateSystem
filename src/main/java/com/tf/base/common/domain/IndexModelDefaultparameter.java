package com.tf.base.common.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "index_model_defaultparameter")
public class IndexModelDefaultparameter {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 评价id
     */
    @Column(name = "assess_id")
    private Integer assessId;

    /**
     * 模型id
     */
    @Column(name = "model_id")
    private Integer modelId;

    /**
     * 参数id
     */
    @Column(name = "parameter_id")
    private Integer parameterId;

    /**
     * 默认值
     */
    @Column(name = "default_value")
    private Float defaultValue;

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
     * 获取评价id
     *
     * @return assess_id - 评价id
     */
    public Integer getAssessId() {
        return assessId;
    }

    /**
     * 设置评价id
     *
     * @param assessId 评价id
     */
    public void setAssessId(Integer assessId) {
        this.assessId = assessId;
    }

    /**
     * 获取模型id
     *
     * @return model_id - 模型id
     */
    public Integer getModelId() {
        return modelId;
    }

    /**
     * 设置模型id
     *
     * @param modelId 模型id
     */
    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    /**
     * 获取参数id
     *
     * @return parameter_id - 参数id
     */
    public Integer getParameterId() {
        return parameterId;
    }

    /**
     * 设置参数id
     *
     * @param parameterId 参数id
     */
    public void setParameterId(Integer parameterId) {
        this.parameterId = parameterId;
    }

    /**
     * 获取默认值
     *
     * @return default_value - 默认值
     */
    public Float getDefaultValue() {
        return defaultValue;
    }

    /**
     * 设置默认值
     *
     * @param defaultValue 默认值
     */
    public void setDefaultValue(Float defaultValue) {
        this.defaultValue = defaultValue;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", assessId=").append(assessId);
        sb.append(", modelId=").append(modelId);
        sb.append(", parameterId=").append(parameterId);
        sb.append(", defaultValue=").append(defaultValue);
        sb.append(", createUid=").append(createUid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUid=").append(updateUid);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isdelete=").append(isdelete);
        sb.append("]");
        return sb.toString();
    }
}