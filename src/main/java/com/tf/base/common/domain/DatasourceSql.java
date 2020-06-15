package com.tf.base.common.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "datasource_sql")
public class DatasourceSql {
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 数据源ID
	 */
	@Column(name = "datasource_id")
	private Integer datasourceId;

	/**
	 * sql名字
	 */
	@Column(name = "sql_name")
	private String sqlName;

	@Column(name = "sql_str")
	private String sqlStr;

	@Column(name = "config")
	private String config;

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

	@Transient
	private String updateTimeStr;

	/**
	 * 是否删除【0:否1：是】
	 */
	private Integer isdelete;
	
	@Transient
	private String sourceName;

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
	 * @param id
	 *            主键
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取数据源ID
	 *
	 * @return datasource_id - 数据源ID
	 */
	public Integer getDatasourceId() {
		return datasourceId;
	}

	/**
	 * 设置数据源ID
	 *
	 * @param datasourceId
	 *            数据源ID
	 */
	public void setDatasourceId(Integer datasourceId) {
		this.datasourceId = datasourceId;
	}

	/**
	 * 获取sql名字
	 *
	 * @return sql_name - sql名字
	 */
	public String getSqlName() {
		return sqlName;
	}

	/**
	 * 设置sql名字
	 *
	 * @param sqlName
	 *            sql名字
	 */
	public void setSqlName(String sqlName) {
		this.sqlName = sqlName;
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
	 * @param createUid
	 *            创建人ID
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
	 * @param createTime
	 *            创建时间
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
	 * @param updateUid
	 *            更新人ID
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
	 * @param updateTime
	 *            更新时间
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
	 * @param isdelete
	 *            是否删除【0:否1：是】
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
		sb.append(", datasourceId=").append(datasourceId);
		sb.append(", sqlName=").append(sqlName);
		sb.append(", createUid=").append(createUid);
		sb.append(", createTime=").append(createTime);
		sb.append(", updateUid=").append(updateUid);
		sb.append(", updateTime=").append(updateTime);
		sb.append(", isdelete=").append(isdelete);
		sb.append("]");
		return sb.toString();
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public String getSqlStr() {
		return sqlStr;
	}

	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
}