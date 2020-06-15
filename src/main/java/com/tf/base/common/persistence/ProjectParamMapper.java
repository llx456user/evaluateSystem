package com.tf.base.common.persistence;

import com.tf.base.common.domain.ProjectParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import javax.persistence.TableGenerator;
import java.util.List;

public interface ProjectParamMapper extends Mapper<ProjectParam>, MySqlMapper<ProjectParam> {
    /**
     * 查询数据模板分组数
     */
    int count(@Param("projectId") int projectId);

    /**
     * 查询数据模板分组数据
     */
    List<ProjectParam> list(@Param("projectId") int projectId);

    /**
     * 查询数据模板分组去去重数据
     */
    List<ProjectParam> listGroupTemplate(@Param("projectId") int projectId);

    /**
     * 查询模板套数
     *
     * @param projectId
     * @return
     */
    int queryMaxSuitCount(@Param("projectId") int projectId);

}