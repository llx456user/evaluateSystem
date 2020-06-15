package com.tf.base.common.persistence;

import com.tf.base.common.domain.StructDefine;
import com.tf.base.platform.domain.ModelInfoParams;
import com.tf.base.platform.domain.ModelInfoQueryResult;
import com.tf.base.platform.domain.StructInfoParams;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface StructDefineMapper extends Mapper<StructDefine>, MySqlMapper<StructDefine> {
//    int queryCount(@Param("params") StructDefine params);
    List<StructDefine> queryList(@Param("params") StructInfoParams params, @Param("start") int start);
    List<StructDefine> queryListAll();
    int queryCount(@Param("params") StructInfoParams params);

    int updateIsdelete(@Param("structCategoryid") Integer structCategoryid);
}