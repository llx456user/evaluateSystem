package com.tf.base.common.persistence;


import com.tf.base.common.domain.WordTemplate;
import com.tf.base.platform.domain.WordTemplateParams;
import com.tf.base.platform.domain.WordTemplateQueryResult;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface WordTemplateMapper extends Mapper<WordTemplate>, MySqlMapper<WordTemplate> {

    int getCount(@Param("params") WordTemplateParams params);

    List<WordTemplateQueryResult> selectList(@Param("params") WordTemplateParams params, @Param("start")int start);

    void deleteByCategoryId(Integer categoryId);

    WordTemplate getProjectBindInfo(@Param("projectId") Integer projectId);

}