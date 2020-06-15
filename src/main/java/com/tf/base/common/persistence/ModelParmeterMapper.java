package com.tf.base.common.persistence;

import com.tf.base.common.domain.ModelParmeter;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface ModelParmeterMapper extends MySqlMapper<ModelParmeter>, Mapper<ModelParmeter> {
}