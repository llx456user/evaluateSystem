package com.tf.base.common.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "struct_parmeter")
public class StructParmeter {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 结构体id
     */
    @Column(name = "struct_id")
    private Integer structId;

    /**
     * 参数名称
     */
    @Column(name = "parmeter_name")
    private String parmeterName;

    /**
     * 参数单位
     */
    @Column(name = "parmeter_unit")
    private String parmeterUnit;

    /**
     * 参数含义
     */
    @Column(name = "parmeter_unit_ex")
    private String parmeterUnitEx;

    /**
     * 参数类型
     */
    @Column(name = "parmeter_type")
    private String parmeterType;

    /**
     * 是否数组0：否，1：是，默认为0.
     */
    @Column(name = "is_array")
    private Boolean isArray;

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
     * 获取结构体id
     *
     * @return struct_id - 结构体id
     */
    public Integer getStructId() {
        return structId;
    }

    /**
     * 设置结构体id
     *
     * @param structId 结构体id
     */
    public void setStructId(Integer structId) {
        this.structId = structId;
    }

    /**
     * 获取参数名称
     *
     * @return parmeter_name - 参数名称
     */
    public String getParmeterName() {
        return parmeterName;
    }

    /**
     * 设置参数名称
     *
     * @param parmeterName 参数名称
     */
    public void setParmeterName(String parmeterName) {
        this.parmeterName = parmeterName;
    }

    /**
     * 获取参数单位
     *
     * @return parmeter_unit - 参数单位
     */
    public String getParmeterUnit() {
        return parmeterUnit;
    }

    /**
     * 设置参数单位
     *
     * @param parmeterUnit 参数单位
     */
    public void setParmeterUnit(String parmeterUnit) {
        this.parmeterUnit = parmeterUnit;
    }

    /**
     * 获取参数含义
     *
     * @return parmeter_unit_ex - 参数含义
     */
    public String getParmeterUnitEx() {
        return parmeterUnitEx;
    }

    /**
     * 设置参数含义
     *
     * @param parmeterUnitEx 参数含义
     */
    public void setParmeterUnitEx(String parmeterUnitEx) {
        this.parmeterUnitEx = parmeterUnitEx;
    }

    /**
     * 获取参数类型
     *
     * @return parmeter_type - 参数类型
     */
    public String getParmeterType() {
        return parmeterType;
    }

    /**
     * 设置参数类型
     *
     * @param parmeterType 参数类型
     */
    public void setParmeterType(String parmeterType) {
        this.parmeterType = parmeterType;
    }

    /**
     * 获取是否数组0：否，1：是，默认为0.
     *
     * @return is_array - 是否数组0：否，1：是，默认为0.
     */
    public Boolean getIsArray() {
        return isArray;
    }

    /**
     * 设置是否数组0：否，1：是，默认为0.
     *
     * @param isArray 是否数组0：否，1：是，默认为0.
     */
    public void setIsArray(Boolean isArray) {
        this.isArray = isArray;
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
        sb.append(", structId=").append(structId);
        sb.append(", parmeterName=").append(parmeterName);
        sb.append(", parmeterUnit=").append(parmeterUnit);
        sb.append(", parmeterUnitEx=").append(parmeterUnitEx);
        sb.append(", parmeterType=").append(parmeterType);
        sb.append(", isArray=").append(isArray);
        sb.append(", createUid=").append(createUid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUid=").append(updateUid);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isdelete=").append(isdelete);
        sb.append("]");
        return sb.toString();
    }
}