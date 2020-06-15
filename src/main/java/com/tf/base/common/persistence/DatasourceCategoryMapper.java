package com.tf.base.common.persistence;

import com.tf.base.common.domain.DatasourceCategory;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface DatasourceCategoryMapper extends MySqlMapper<DatasourceCategory>, Mapper<DatasourceCategory> {
}