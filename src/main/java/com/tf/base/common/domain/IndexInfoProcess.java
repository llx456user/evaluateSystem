package com.tf.base.common.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "index_info_process")
public class IndexInfoProcess {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 指标定义ID
     */
    @Column(name = "index_id")
    private Integer indexId;

    /**
     * 业务ID，关联的调试或者指标执行
     */
    @Column(name = "business_id")
    private Integer businessId;

    /**
     * 执行类型【0：调试;1:运行】
     */
    @Column(name = "process_type")
    private Integer processType;

    /**
     * 指标执行完成【0：未开始;1：执行中;执行完成】
     */
    @Column(name = "index_status")
    private Integer indexStatus;

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
    
    
    @Column(name = "index_process_param")
    private String indexProcessParam;
    
    
    @Column(name = "index_process_data")
    private String indexProcessData;

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
     * 获取指标定义ID
     *
     * @return index_id - 指标定义ID
     */
    public Integer getIndexId() {
        return indexId;
    }

    /**
     * 设置指标定义ID
     *
     * @param indexId 指标定义ID
     */
    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    /**
     * 获取业务ID，关联的调试或者指标执行
     *
     * @return business_id - 业务ID，关联的调试或者指标执行
     */
    public Integer getBusinessId() {
        return businessId;
    }

    /**
     * 设置业务ID，关联的调试或者指标执行
     *
     * @param businessId 业务ID，关联的调试或者指标执行
     */
    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    /**
     * 获取执行类型【0：调试;1:运行】
     *
     * @return process_type - 执行类型【0：调试;1:运行】
     */
    public Integer getProcessType() {
        return processType;
    }

    /**
     * 设置执行类型【0：调试;1:运行】
     *
     * @param processType 执行类型【0：调试;1:运行】
     */
    public void setProcessType(Integer processType) {
        this.processType = processType;
    }

    /**
     * 获取指标执行完成【0：未开始;1：执行中;2:执行完成;-1:启动错误】
     *
     * @return index_status - 指标执行完成【0：未开始;1：执行中;2:执行完成;-1:启动错误】
     */
    public Integer getIndexStatus() {
        return indexStatus;
    }

    /**
     * 设置指标执行完成【0：未开始;1：执行中;2执行完成】
     *
     * @param indexStatus 指标执行完成【0：未开始;1：执行中;2执行完成;-1:启动错误】
     */
    public void setIndexStatus(Integer indexStatus) {
        this.indexStatus = indexStatus;
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
        sb.append(", businessId=").append(businessId);
        sb.append(", processType=").append(processType);
        sb.append(", indexStatus=").append(indexStatus);
        sb.append(", createUid=").append(createUid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUid=").append(updateUid);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isdelete=").append(isdelete);
        sb.append("]");
        return sb.toString();
    }

	public String getIndexProcessParam() {
		return indexProcessParam;
	}

	public void setIndexProcessParam(String indexProcessParam) {
		this.indexProcessParam = indexProcessParam;
	}

	public String getIndexProcessData() {
		return indexProcessData;
	}

	public void setIndexProcessData(String indexProcessData) {
		this.indexProcessData = indexProcessData;
	}
}