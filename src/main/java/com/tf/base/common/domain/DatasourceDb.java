package com.tf.base.common.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "datasource_db")
public class DatasourceDb {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 数据源名称
     */
    @Column(name = "source_name")
    private String sourceName;

    /**
     * 数据源类型[默认为jdbc]
     */
    @Column(name = "source_type")
    private String sourceType;

    /**
     * 数据源分类【0：mysql;1:sqlserver;2:oracle】
     */
    @Column(name = "db_type")
    private Byte dbType;

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
     * 数据源配置信息
     */
    private String config;

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
     * 获取数据源名称
     *
     * @return source_name - 数据源名称
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * 设置数据源名称
     *
     * @param sourceName 数据源名称
     */
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    /**
     * 获取数据源类型[默认为jdbc]
     *
     * @return source_type - 数据源类型[默认为jdbc]
     */
    public String getSourceType() {
        return sourceType;
    }

    /**
     * 设置数据源类型[默认为jdbc]
     *
     * @param sourceType 数据源类型[默认为jdbc]
     */
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * 获取数据源分类【0：mysql;1:sqlserver;2:oracle】
     *
     * @return db_type - 数据源分类【0：mysql;1:sqlserver;2:oracle】
     */
    public Byte getDbType() {
        return dbType;
    }

    /**
     * 设置数据源分类【0：mysql;1:sqlserver;2:oracle】
     *
     * @param dbType 数据源分类【0：mysql;1:sqlserver;2:oracle】
     */
    public void setDbType(Byte dbType) {
        this.dbType = dbType;
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
     * 获取数据源配置信息
     *
     * @return config - 数据源配置信息
     */
    public String getConfig() {
        return config;
    }

    /**
     * 设置数据源配置信息
     *
     * @param config 数据源配置信息
     */
    public void setConfig(String config) {
        this.config = config;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", sourceName=").append(sourceName);
        sb.append(", sourceType=").append(sourceType);
        sb.append(", dbType=").append(dbType);
        sb.append(", createUid=").append(createUid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUid=").append(updateUid);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", config=").append(config);
        sb.append("]");
        return sb.toString();
    }
}