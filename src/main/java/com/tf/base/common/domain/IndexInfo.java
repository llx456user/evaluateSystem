package com.tf.base.common.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "index_info")
public class IndexInfo {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 指标分类名称
     */
    @Column(name = "index_name")
    private String indexName;

    /**
     * 指标分类id
     */
    @Column(name = "index_categoryid")
    private Integer indexCategoryid;

    /**
     * 指标是否发布【0：未发布;1：已发布】
     */
    @Column(name = "index_complete")
    private Byte indexComplete;


    @Column(name = "index_content")
    private String indexContent;

    @Column(name = "index_data")
    private String indexData;

    @Column(name = "index_formwork_data")
    private String indexFormworkData;

    /**
     * 指标版本
     */
    @Column(name = "index_version")
    private String indexVersion;

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
     * 子指标是否显示【0：不显示;1：显示】
     */
    @Column(name = "visible_child")
    private  Byte visibleChild;

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
     * @return index_name - 指标分类名称
     */
    public String getIndexName() {
        return indexName;
    }

    /**
     * 设置指标分类名称
     *
     * @param indexName 指标分类名称
     */
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    /**
     * 获取指标分类id
     *
     * @return index_categoryid - 指标分类id
     */
    public Integer getIndexCategoryid() {
        return indexCategoryid;
    }

    /**
     * 设置指标分类id
     *
     * @param indexCategoryid 指标分类id
     */
    public void setIndexCategoryid(Integer indexCategoryid) {
        this.indexCategoryid = indexCategoryid;
    }

    /**
     * 获取指标是否发布【0：未发布;1：已发布】
     *
     * @return index_complete - 指标是否发布【0：未发布;1：已发布】
     */
    public Byte getIndexComplete() {
        return indexComplete;
    }

    /**
     * 设置指标是否发布【0：未发布;1：已发布】
     *
     * @param indexComplete 指标是否发布【0：未发布;1：已发布】
     */
    public void setIndexComplete(Byte indexComplete) {
        this.indexComplete = indexComplete;
    }

    /**
     * 获取指标版本
     *
     * @return index_version - 指标版本
     */
    public String getIndexVersion() {
        return indexVersion;
    }

    /**
     * 设置指标版本
     *
     * @param indexVersion 指标版本
     */
    public void setIndexVersion(String indexVersion) {
        this.indexVersion = indexVersion;
    }

    public String getIndexContent() {
        return indexContent;
    }

    public void setIndexContent(String indexContent) {
        this.indexContent = indexContent;
    }

    public String getIndexData() {
        return indexData;
    }

    public void setIndexData(String indexData) {
        this.indexData = indexData;
    }

    public String getIndexFormworkData() {
        return indexFormworkData;
    }

    public void setIndexFormworkData(String indexFormworkData) {
        this.indexFormworkData = indexFormworkData;
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

    public Byte getVisibleChild() {
        return visibleChild;
    }

    public void setVisibleChild(Byte visibleChild) {
        this.visibleChild = visibleChild;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", indexName=").append(indexName);
        sb.append(", indexCategoryid=").append(indexCategoryid);
        sb.append(", indexComplete=").append(indexComplete);
        sb.append(", indexVersion=").append(indexVersion);
        sb.append(", createUid=").append(createUid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUid=").append(updateUid);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isdelete=").append(isdelete);
        sb.append("]");
        return sb.toString();
    }
}
