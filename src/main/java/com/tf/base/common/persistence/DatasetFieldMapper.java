package com.tf.base.common.persistence;

import com.tf.base.common.domain.DatasetField;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface DatasetFieldMapper extends MySqlMapper<DatasetField>, Mapper<DatasetField> {
}