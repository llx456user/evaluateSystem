package com.tf.base.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tf.base.common.domain.DatasourceFile;
import com.tf.base.datasource.domain.FileParams;
import com.tf.base.datasource.domain.FileQueryResult;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface DatasourceFileMapper extends MySqlMapper<DatasourceFile>, Mapper<DatasourceFile> {

	int queryCount(@Param("params")FileParams params);

	List<FileQueryResult> queryList(@Param("params")FileParams params, @Param("start")int start);

	List<DatasourceFile> queryFileList(Integer cid);
}