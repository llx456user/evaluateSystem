package com.tf.base.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tf.base.common.domain.ProjectInfo;
import com.tf.base.project.domain.ProjectInfoParams;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface ProjectInfoMapper extends MySqlMapper<ProjectInfo>, Mapper<ProjectInfo> {
	
	int queryCount(@Param("params") ProjectInfoParams params);

	List<ProjectInfo> queryList(@Param("params") ProjectInfoParams params, @Param("start") int start);
	
	/**
	 * 更新模型删除状态
	 * @param modelCategoryid
	 * @return
	 */
	int updateIsdelete(@Param("categoryId") Integer categoryId);
}