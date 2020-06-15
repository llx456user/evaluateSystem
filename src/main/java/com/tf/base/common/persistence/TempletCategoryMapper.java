package com.tf.base.common.persistence;

import com.tf.base.common.domain.TempletCategory;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface TempletCategoryMapper extends MySqlMapper<TempletCategory>, Mapper<TempletCategory> {

	int deleteById(Integer categoryId);
}