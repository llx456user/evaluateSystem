package com.tf.base.datasource.domain;

import com.tf.base.common.domain.Pager;

public class DatasourceSqlParams extends Pager {

	private Integer datasourceId;
	private String sqlName;
	private String dbName;

	public Integer getDatasourceId() {
		return datasourceId;
	}

	public void setDatasourceId(Integer datasourceId) {
		this.datasourceId = datasourceId;
	}

	public String getSqlName() {
		return sqlName;
	}

	public void setSqlName(String sqlName) {
		this.sqlName = sqlName;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

}
