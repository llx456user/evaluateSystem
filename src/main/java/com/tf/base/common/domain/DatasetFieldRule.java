package com.tf.base.common.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "dataset_field_rule")
public class DatasetFieldRule {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 最小值
     */
    @Column(name = "min_number")
    private Float minNumber;

    /**
     * 最大值
     */
    @Column(name = "max_number")
    private Float maxNumber;

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
     * 规则名
     */
    @Column(name = "rule_name")
    private byte[] ruleName;

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
     * 获取最小值
     *
     * @return min_number - 最小值
     */
    public Float getMinNumber() {
        return minNumber;
    }

    /**
     * 设置最小值
     *
     * @param minNumber 最小值
     */
    public void setMinNumber(Float minNumber) {
        this.minNumber = minNumber;
    }

    /**
     * 获取最大值
     *
     * @return max_number - 最大值
     */
    public Float getMaxNumber() {
        return maxNumber;
    }

    /**
     * 设置最大值
     *
     * @param maxNumber 最大值
     */
    public void setMaxNumber(Float maxNumber) {
        this.maxNumber = maxNumber;
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
     * 获取规则名
     *
     * @return rule_name - 规则名
     */
    public byte[] getRuleName() {
        return ruleName;
    }

    /**
     * 设置规则名
     *
     * @param ruleName 规则名
     */
    public void setRuleName(byte[] ruleName) {
        this.ruleName = ruleName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", minNumber=").append(minNumber);
        sb.append(", maxNumber=").append(maxNumber);
        sb.append(", createUid=").append(createUid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUid=").append(updateUid);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", ruleName=").append(ruleName);
        sb.append("]");
        return sb.toString();
    }
}