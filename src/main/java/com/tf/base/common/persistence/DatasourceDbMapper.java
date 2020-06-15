package com.tf.base.common.persistence;

import com.tf.base.common.domain.DatasourceDb;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface DatasourceDbMapper extends MySqlMapper<DatasourceDb>, Mapper<DatasourceDb> {
    public List<DatasourceDb> selectDbType();
}