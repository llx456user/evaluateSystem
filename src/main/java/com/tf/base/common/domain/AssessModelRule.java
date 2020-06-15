package com.tf.base.common.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "assess_model_rule")
public class AssessModelRule {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 评估ID
     */
    @Column(name = "assess_id")
    private Integer assessId;

    /**
     * 模型参数ID
     */
    @Column(name = "mod_para_id")
    private Integer modParaId;

    /**
     * 模型字段ID
     */
    @Column(name = "mod_field_id")
    private Integer modFieldId;

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
     * 获取评估ID
     *
     * @return assess_id - 评估ID
     */
    public Integer getAssessId() {
        return assessId;
    }

    /**
     * 设置评估ID
     *
     * @param assessId 评估ID
     */
    public void setAssessId(Integer assessId) {
        this.assessId = assessId;
    }

    /**
     * 获取模型参数ID
     *
     * @return mod_para_id - 模型参数ID
     */
    public Integer getModParaId() {
        return modParaId;
    }

    /**
     * 设置模型参数ID
     *
     * @param modParaId 模型参数ID
     */
    public void setModParaId(Integer modParaId) {
        this.modParaId = modParaId;
    }

    /**
     * 获取模型字段ID
     *
     * @return mod_field_id - 模型字段ID
     */
    public Integer getModFieldId() {
        return modFieldId;
    }

    /**
     * 设置模型字段ID
     *
     * @param modFieldId 模型字段ID
     */
    public void setModFieldId(Integer modFieldId) {
        this.modFieldId = modFieldId;
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
        sb.append(", modParaId=").append(modParaId);
        sb.append(", modFieldId=").append(modFieldId);
        sb.append(", createUid=").append(createUid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUid=").append(updateUid);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isdelete=").append(isdelete);
        sb.append("]");
        return sb.toString();
    }
}