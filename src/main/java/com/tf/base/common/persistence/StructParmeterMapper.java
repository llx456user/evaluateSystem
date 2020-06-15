package com.tf.base.common.persistence;

import com.tf.base.common.domain.StructParmeter;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

public interface StructParmeterMapper extends Mapper<StructParmeter>, MySqlMapper<StructParmeter> {

    List<StructParmeter> queryStructParmeterByStructid(@Param("structName") String structName) ;
}