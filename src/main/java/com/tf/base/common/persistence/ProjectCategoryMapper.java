package com.tf.base.common.persistence;

import java.util.List;

import com.tf.base.common.domain.ProjectCategory;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface ProjectCategoryMapper extends MySqlMapper<ProjectCategory>, Mapper<ProjectCategory> {
	public List<ProjectCategory> selectWsc();
}