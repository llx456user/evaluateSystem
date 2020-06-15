package com.tf.base.common.persistence;

import java.util.List;

import com.tf.base.index.domain.RecentProjectInfo;
import org.apache.ibatis.annotations.Param;

import com.tf.base.common.domain.AssessInfo;
import com.tf.base.index.domain.CountInfo;
import com.tf.base.index.domain.RecentAssessInfo;
import com.tf.base.project.domain.AssessInfoParams;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface AssessInfoMapper extends MySqlMapper<AssessInfo>, Mapper<AssessInfo> {

	List<RecentProjectInfo> recentAssessCount(@Param("userid")String userid);

	List<String> categoryCount(@Param("userid")String userid);

	List<CountInfo> countProject(@Param("userid")String userid,@Param("startMonth")String startMonth, @Param("endMonth")String endMonth);

	List<CountInfo> countModel(@Param("userid")String userid,@Param("startMonth")String startMonth, @Param("endMonth")String endMonth);

	List<CountInfo> countIndex(@Param("userid")String userid,@Param("startMonth")String startMonth, @Param("endMonth")String endMonth);
	
	
	int queryCount(@Param("params") AssessInfoParams params);

	List<AssessInfo> queryList(@Param("params") AssessInfoParams params, @Param("start") int start);
	
	String getShowNameByUid(int userId);
	
}