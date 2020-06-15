package com.tf.base.common.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "datasource_file_title")
public class DatasourceFileTitle {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 文件名
     */
    @Column(name = "column_name")
    private String columnName;

    /**
     * 文件ID
     */
    @Column(name = "file_id")
    private Integer fileId;

    /**
     * 列序号
     */
    @Column(name = "column_num")
    private Integer columnNum;

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
     * 类型：1-文件，2-sql
     */
    @Column(name = "type")
    private Integer type;

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
     * 获取文件名
     *
     * @return column_name - 文件名
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * 设置文件名
     *
     * @param columnName 文件名
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * 获取文件ID
     *
     * @return file_id - 文件ID
     */
    public Integer getFileId() {
        return fileId;
    }

    /**
     * 设置文件ID
     *
     * @param fileId 文件ID
     */
    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    /**
     * 获取列序号
     *
     * @return column_num - 列序号
     */
    public Integer getColumnNum() {
        return columnNum;
    }

    /**
     * 设置列序号
     *
     * @param columnNum 列序号
     */
    public void setColumnNum(Integer columnNum) {
        this.columnNum = columnNum;
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
        sb.append(", columnName=").append(columnName);
        sb.append(", fileId=").append(fileId);
        sb.append(", columnNum=").append(columnNum);
        sb.append(", createUid=").append(createUid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUid=").append(updateUid);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isdelete=").append(isdelete);
        sb.append("]");
        return sb.toString();
    }

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}