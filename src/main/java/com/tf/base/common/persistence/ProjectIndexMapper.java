package com.tf.base.common.persistence;

import com.tf.base.common.domain.ProjectIndex;

import com.tf.base.project.domain.ProjectIndexParams;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface ProjectIndexMapper extends Mapper<ProjectIndex>, MySqlMapper<ProjectIndex> {
    void updateName(ProjectIndexParams params);
    List<ProjectIndex> selectNodetree(String params);
}