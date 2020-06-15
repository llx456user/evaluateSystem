package com.tf.base.common.persistence;

import com.tf.base.common.domain.ModelCategory;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface ModelCategoryMapper extends MySqlMapper<ModelCategory>, Mapper<ModelCategory> {
	public List<ModelCategory> selectWsc();
//	public List<ModelCategory> selectModel();
}