package com.tf.base.common.persistence;

import com.tf.base.common.domain.IndexModel;
import com.tf.base.platform.domain.ModelInfoParams;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface IndexModelMapper extends Mapper<IndexModel>, MySqlMapper<IndexModel> {

	List<IndexModel> selectNodeTypeByIndexId(int indexId);


	List<IndexModel> selectLabelByProjectId(int projectId);

	int getIndexChildCount(@Param("params") ModelInfoParams params);

	List<IndexModel> selectIndexChildList(@Param("params")ModelInfoParams params, @Param("start")int start);
}
