package com.tf.base.common.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dataset_field")
public class DatasetField {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 字段ID
     */
    @Column(name = "field_id")
    private Integer fieldId;

    /**
     * 字段尺寸
     */
    @Column(name = "field_size")
    private Integer fieldSize;

    /**
     * 规则ID
     */
    @Column(name = "rule_id")
    private Integer ruleId;

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
     * 获取字段ID
     *
     * @return field_id - 字段ID
     */
    public Integer getFieldId() {
        return fieldId;
    }

    /**
     * 设置字段ID
     *
     * @param fieldId 字段ID
     */
    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * 获取字段尺寸
     *
     * @return field_size - 字段尺寸
     */
    public Integer getFieldSize() {
        return fieldSize;
    }

    /**
     * 设置字段尺寸
     *
     * @param fieldSize 字段尺寸
     */
    public void setFieldSize(Integer fieldSize) {
        this.fieldSize = fieldSize;
    }

    /**
     * 获取规则ID
     *
     * @return rule_id - 规则ID
     */
    public Integer getRuleId() {
        return ruleId;
    }

    /**
     * 设置规则ID
     *
     * @param ruleId 规则ID
     */
    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
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
        sb.append(", fieldId=").append(fieldId);
        sb.append(", fieldSize=").append(fieldSize);
        sb.append(", ruleId=").append(ruleId);
        sb.append(", createUid=").append(createUid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUid=").append(updateUid);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isdelete=").append(isdelete);
        sb.append("]");
        return sb.toString();
    }
}