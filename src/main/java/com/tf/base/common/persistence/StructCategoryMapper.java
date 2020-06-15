package com.tf.base.common.persistence;

import com.tf.base.common.domain.ModelCategory;
import com.tf.base.common.domain.StructCategory;
import com.tf.base.common.domain.StructDefine;
import com.tf.base.platform.domain.StructInfoParams;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface StructCategoryMapper extends Mapper<StructCategory>, MySqlMapper<StructCategory> {
    public List<StructCategory> selectWsc();

}