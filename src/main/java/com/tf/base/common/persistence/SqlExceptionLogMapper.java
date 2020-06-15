package com.tf.base.common.persistence;

import java.util.List;

import com.tf.base.common.domain.SqlExceptionLog;
import com.tf.base.common.domain.SqlExceptionLogExample;


public interface SqlExceptionLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SqlExceptionLog record);

    int insertSelective(SqlExceptionLog record);

    List<SqlExceptionLog> selectByExampleWithBLOBs(SqlExceptionLogExample example);

    List<SqlExceptionLog> selectByExample(SqlExceptionLogExample example);

    SqlExceptionLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SqlExceptionLog record);

    int updateByPrimaryKeyWithBLOBs(SqlExceptionLog record);

    int updateByPrimaryKey(SqlExceptionLog record);
}