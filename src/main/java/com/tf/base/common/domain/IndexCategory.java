package com.tf.base.common.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "index_category")
public class IndexCategory {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 指标分类名称
     */
    @Column(name = "category_name")
    private String categoryName;

    /**
     * 分类父节点id
     */
    @Column(name = "category_parentid")
    private Integer categoryParentid;

    /**
     * 节点分类（1级，2级）
     */
    @Column(name = "category_level")
    private Integer categoryLevel;

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
     * 获取指标分类名称
     *
     * @return category_name - 指标分类名称
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 设置指标分类名称
     *
     * @param categoryName 指标分类名称
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 获取分类父节点id
     *
     * @return category_parentid - 分类父节点id
     */
    public Integer getCategoryParentid() {
        return categoryParentid;
    }

    /**
     * 设置分类父节点id
     *
     * @param categoryParentid 分类父节点id
     */
    public void setCategoryParentid(Integer categoryParentid) {
        this.categoryParentid = categoryParentid;
    }

    /**
     * 获取节点分类（1级，2级）
     *
     * @return category_level - 节点分类（1级，2级）
     */
    public Integer getCategoryLevel() {
        return categoryLevel;
    }

    /**
     * 设置节点分类（1级，2级）
     *
     * @param categoryLevel 节点分类（1级，2级）
     */
    public void setCategoryLevel(Integer categoryLevel) {
        this.categoryLevel = categoryLevel;
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
        sb.append(", categoryName=").append(categoryName);
        sb.append(", categoryParentid=").append(categoryParentid);
        sb.append(", categoryLevel=").append(categoryLevel);
        sb.append(", createUid=").append(createUid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUid=").append(updateUid);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isdelete=").append(isdelete);
        sb.append("]");
        return sb.toString();
    }
}