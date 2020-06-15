package com.tf.base.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tf.base.common.domain.DatasourceSql;
import com.tf.base.datasource.domain.DatasourceSqlParams;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface DatasourceSqlMapper extends MySqlMapper<DatasourceSql>, Mapper<DatasourceSql> {
	
	int queryCount(@Param("params") DatasourceSqlParams params);

	List<DatasourceSql> queryList(@Param("params") DatasourceSqlParams params, @Param("start") int start);
	
}