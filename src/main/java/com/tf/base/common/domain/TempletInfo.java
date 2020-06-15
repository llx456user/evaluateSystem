package com.tf.base.common.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "templet_info")
public class TempletInfo {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
     * 指标id
     * */

    @Column(name = "index_id")
    private Integer indexid;

    /**
     * 模板名称
     */
    @Column(name = "templet_name")
    private String templetName;

    @Column(name = "templet_content")
    private String templetContent;

    @Column(name = "templet_data")
    private String templetData;

    @Column(name = "templet_formwork_data")
    private String templetFormworkData;

    /**
     * 模板分类id
     */
    @Column(name = "templet_categoryid")
    private Integer templetCategoryid;

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

    /*
     * 获取指标id*
     */
    public Integer getIndexid() {
        return indexid;
    }

    /* 设置指标id*/

    public void setIndexid(Integer indexid) {
        this.indexid = indexid;
    }

    /**
     * 获取模板名称
     *
     * @return templet_name - 模板名称
     */
    public String getTempletName() {
        return templetName;
    }

    /**
     * 设置模板名称
     *
     * @param templetName 模板名称
     */
    public void setTempletName(String templetName) {
        this.templetName = templetName;
    }

    /**
     * 获取模板分类id
     *
     * @return templet_categoryid - 模板分类id
     */
    public Integer getTempletCategoryid() {
        return templetCategoryid;
    }

    /**
     * 设置模板分类id
     *
     * @param templetCategoryid 模板分类id
     */
    public void setTempletCategoryid(Integer templetCategoryid) {
        this.templetCategoryid = templetCategoryid;
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



    public String getTempletContent() {
        return templetContent;
    }

    public void setTempletContent(String templetContent) {
        this.templetContent = templetContent;
    }

    public String getTempletData() {
        return templetData;
    }

    public void setTempletData(String templetData) {
        this.templetData = templetData;
    }

    public String getTempletFormworkData() {
        return templetFormworkData;
    }

    public void setTempletFormworkData(String templetFormworkData) {
        this.templetFormworkData = templetFormworkData;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", indexid=").append(indexid);
        sb.append(", templetName=").append(templetName);
        sb.append(", templetCategoryid=").append(templetCategoryid);
        sb.append(", createUid=").append(createUid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUid=").append(updateUid);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isdelete=").append(isdelete);
        sb.append("]");
        return sb.toString();
    }
}