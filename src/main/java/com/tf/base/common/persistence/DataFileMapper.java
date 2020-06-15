package com.tf.base.common.persistence;

import com.tf.base.common.domain.DataFile;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface DataFileMapper extends MySqlMapper<DataFile>, Mapper<DataFile> {
}