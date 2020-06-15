package com.tf.base.common.persistence;


import com.tf.base.common.domain.WordCategory;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface WordCategoryMapper extends Mapper<WordCategory>, MySqlMapper<WordCategory> {
    void deleteById(Integer categoryId);
}