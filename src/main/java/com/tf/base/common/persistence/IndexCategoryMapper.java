package com.tf.base.common.persistence;

import com.tf.base.common.domain.IndexCategory;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface IndexCategoryMapper extends MySqlMapper<IndexCategory>, Mapper<IndexCategory> {
    void deleteById(Integer categoryId);

}