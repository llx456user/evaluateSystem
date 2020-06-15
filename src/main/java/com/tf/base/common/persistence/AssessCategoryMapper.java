package com.tf.base.common.persistence;

import com.tf.base.common.domain.AssessCategory;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface AssessCategoryMapper extends MySqlMapper<AssessCategory>, Mapper<AssessCategory> {
}