package com.tf.base.common.persistence;

import com.tf.base.common.domain.ProjectAssess;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface ProjectAssessMapper extends MySqlMapper<ProjectAssess>, Mapper<ProjectAssess> {
}