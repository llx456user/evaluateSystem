package com.tf.base.common.persistence;

import com.tf.base.common.domain.ModelInfo;
import com.tf.base.platform.domain.ModelInfoParams;
import com.tf.base.platform.domain.ModelInfoQueryResult;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface ModelInfoMapper extends MySqlMapper<ModelInfo>, Mapper<ModelInfo> {
	int queryCount(@Param("params") ModelInfoParams params);

	List<ModelInfoQueryResult> queryList(@Param("params") ModelInfoParams params, @Param("start") int start);
	
	/**
	 * 更新模型删除状态
	 * @param modelCategoryid
	 * @return
	 */
	int updateIsdelete(@Param("modelCategoryid") Integer modelCategoryid);

	public List<ModelInfo> selectModelByCatId(String catId);

	public List<ModelInfo> findModel();

}