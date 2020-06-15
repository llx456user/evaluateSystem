package com.tf.base.common.domain;

import javax.persistence.*;

@Table(name = "project_param")
public class ProjectParam {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 节点ID
     */
    @Column(name = "project_id")
    private Integer projectId;

    /**
     * 参数名称
     */
    @Column(name = "param_name")
    private String paramName;

    /**
     * 参数值
     */
    @Column(name = "param_value")
    private String paramValue;

    /**
     * 参数类型【file,constrant】
     */
    @Column(name = "param_type")
    private String paramType;

    /**
     * 数据模板分组数
     */
    @Column(name = "data_template_number")
    private Integer dataTemplateNumber;

    /**
     * 分组名称
     */
    @Column(name = "data_template_Name")
    private String dataTemplateName;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取节点ID
     *
     * @return project_id - 节点ID
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * 设置节点ID
     *
     * @param projectId 节点ID
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * 获取参数名称
     *
     * @return param_name - 参数名称
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * 设置参数名称
     *
     * @param paramName 参数名称
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * 获取参数值
     *
     * @return param_value - 参数值
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * 设置参数值
     *
     * @param paramValue 参数值
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    /**
     * 获取参数类型【file,constrant】
     *
     * @return param_type - 参数类型【file,constrant】
     */
    public String getParamType() {
        return paramType;
    }

    /**
     * 设置参数类型【file,constrant】
     *
     * @param paramType 参数类型【file,constrant】
     */
    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", paramName=").append(paramName);
        sb.append(", paramValue=").append(paramValue);
        sb.append(", paramType=").append(paramType);
        sb.append(", dataTemplateNumber=").append(dataTemplateNumber);
        sb.append(", dataTemplateName=").append(dataTemplateName);
        sb.append("]");
        return sb.toString();
    }

    public Integer getDataTemplateNumber() {
        return dataTemplateNumber;
    }

    public void setDataTemplateNumber(Integer dataTemplateNumber) {
        this.dataTemplateNumber = dataTemplateNumber;
    }

    public String getDataTemplateName() {
        return dataTemplateName;
    }

    public void setDataTemplateName(String dataTemplateName) {
        this.dataTemplateName = dataTemplateName;
    }
}