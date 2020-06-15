package com.tf.base.common.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "index_debug_info")
public class IndexDebugInfo {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 指标ID
     */
    @Column(name = "index_id")
    private Integer indexId;

    /**
     * 指标执行ID
     */
    @Column(name = "index_process_id")
    private Integer indexProcessId;

    /**
     * 是否完成【0：否;1：是】
     */
    @Column(name = "idebug_complete")
    private Integer idebugComplete;

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
     * 获取指标ID
     *
     * @return index_id - 指标ID
     */
    public Integer getIndexId() {
        return indexId;
    }

    /**
     * 设置指标ID
     *
     * @param indexId 指标ID
     */
    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    /**
     * 获取指标执行ID
     *
     * @return index_process_id - 指标执行ID
     */
    public Integer getIndexProcessId() {
        return indexProcessId;
    }

    /**
     * 设置指标执行ID
     *
     * @param indexProcessId 指标执行ID
     */
    public void setIndexProcessId(Integer indexProcessId) {
        this.indexProcessId = indexProcessId;
    }

    /**
     * 获取是否完成【0：否;1：是】
     *
     * @return idebug_complete - 是否完成【0：否;1：是】
     */
    public Integer getIdebugComplete() {
        return idebugComplete;
    }

    /**
     * 设置是否完成【0：否;1：是】
     *
     * @param idebugComplete 是否完成【0：否;1：是】
     */
    public void setIdebugComplete(Integer idebugComplete) {
        this.idebugComplete = idebugComplete;
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
        sb.append(", indexId=").append(indexId);
        sb.append(", indexProcessId=").append(indexProcessId);
        sb.append(", idebugComplete=").append(idebugComplete);
        sb.append(", createUid=").append(createUid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUid=").append(updateUid);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isdelete=").append(isdelete);
        sb.append("]");
        return sb.toString();
    }
}