package com.tf.base.common.persistence;

import com.tf.base.common.domain.ProjectParamTemplateValue;
import com.tf.base.platform.domain.ProjectParamTemplateValueParams;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface ProjectParamTemplateValueMapper extends Mapper<ProjectParamTemplateValue>, MySqlMapper<ProjectParamTemplateValue> {

    int queryCount(@Param("params") ProjectParamTemplateValueParams params);

    int queryMaxSuitCount(@Param("projectId") int projectId);

    int queryMaxDataTemplateSuitCount(@Param("projectId") int projectId, @Param("dataTemplateNumber") int dataTemplateNumber, @Param("groupParamSuitNumber") int groupParamSuitNumber);

    int queryGroupMaxDataTemplateSuitCount(@Param("projectId") int projectId);

    List<ProjectParamTemplateValue> queryList(@Param("params") ProjectParamTemplateValueParams params, @Param("start") int start);

    List<ProjectParamTemplateValue> queryGroupDataTemplateList(@Param("params") ProjectParamTemplateValueParams params);

    List<ProjectParamTemplateValue> listGroupDataTemplate(@Param("params") ProjectParamTemplateValueParams params);

    List<ProjectParamTemplateValue> listGroupTemplateInfo(@Param("projectId") int projectId, @Param("dataTemplateNumber") int dataTemplateNumber);

    /**
     * 获取分组名称
     *
     * @param projectId
     * @param groupParamSuitNumber
     * @return
     */
    String getGroupDataName(@Param("projectId") int projectId, @Param("groupParamSuitNumber") int groupParamSuitNumber);

    /**
     * 获取分组下的每套数据的数据名称
     *
     * @param projectId
     * @param dataTemplateNumber
     * @param groupParamSuitNumber
     * @return
     */
    List<ProjectParamTemplateValue> listDataName(@Param("projectId") int projectId, @Param("dataTemplateNumber") int dataTemplateNumber, @Param("groupParamSuitNumber") int groupParamSuitNumber);
}