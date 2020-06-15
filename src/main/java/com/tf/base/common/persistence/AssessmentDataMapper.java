package com.tf.base.common.persistence;

import com.tf.base.common.domain.AssessmentData;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface AssessmentDataMapper extends MySqlMapper<AssessmentData>, Mapper<AssessmentData> {
}