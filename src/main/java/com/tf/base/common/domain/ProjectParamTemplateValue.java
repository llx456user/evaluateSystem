package com.tf.base.common.domain;

import javax.persistence.*;

@Table(name = "project_param_template_value")
public class ProjectParamTemplateValue {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 项目ID
     */
    @Column(name = "project_id")
    private Integer projectId;

    /**
     * 数据名称
     */
    @Column(name = "data_name")
    private String dataName;

    /**
     * 数据备注
     */
    @Column(name = "data_comment")
    private String dataComment;

    /**
     * 模板id
     */
    @Column(name = "project_param_id")
    private Integer projectParamId;

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
     * 数据模板套数
     */
    @Column(name = "param_suit_number")
    private Integer paramSuitNumber;

    /**
     * 数据模板分组数
     */
    @Column(name = "data_template_number")
    private Integer dataTemplateNumber;


    /**
     * 分组数据名称
     */
    @Column(name = "group_data_name")
    private String groupDataName;

    /**
     * 分组数据备注
     */
    @Column(name = "group_data_comment")
    private String groupDataComment;

    /**
     * 分组数据模板套数
     */
    @Column(name = "group_param_suit_number")
    private Integer groupParamSuitNumber;

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
     * 获取项目ID
     *
     * @return project_id - 项目ID
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * 设置项目ID
     *
     * @param projectId 项目ID
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * 获取数据名称
     *
     * @return data_name - 数据名称
     */
    public String getDataName() {
        return dataName;
    }

    /**
     * 设置数据名称
     *
     * @param dataName 数据名称
     */
    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    /**
     * 获取数据备注
     *
     * @return data_comment - 数据备注
     */
    public String getDataComment() {
        return dataComment;
    }

    /**
     * 设置数据备注
     *
     * @param dataComment 数据备注
     */
    public void setDataComment(String dataComment) {
        this.dataComment = dataComment;
    }

    /**
     * 获取模板id
     *
     * @return project_param_id - 模板id
     */
    public Integer getProjectParamId() {
        return projectParamId;
    }

    /**
     * 设置模板id
     *
     * @param projectParamId 模板id
     */
    public void setProjectParamId(Integer projectParamId) {
        this.projectParamId = projectParamId;
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

    /**
     * 获取数据模板套数
     *
     * @return param_suit_number - 数据模板套数
     */
    public Integer getParamSuitNumber() {
        return paramSuitNumber;
    }

    /**
     * 设置数据模板套数
     *
     * @param paramSuitNumber 数据模板套数
     */
    public void setParamSuitNumber(Integer paramSuitNumber) {
        this.paramSuitNumber = paramSuitNumber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", dataName=").append(dataName);
        sb.append(", dataComment=").append(dataComment);
        sb.append(", projectParamId=").append(projectParamId);
        sb.append(", paramValue=").append(paramValue);
        sb.append(", paramType=").append(paramType);
        sb.append(", paramSuitNumber=").append(paramSuitNumber);
        sb.append(", dataTemplateNumber=").append(dataTemplateNumber);
        sb.append("]");
        return sb.toString();
    }

    public Integer getDataTemplateNumber() {
        return dataTemplateNumber;
    }

    public void setDataTemplateNumber(Integer dataTemplateNumber) {
        this.dataTemplateNumber = dataTemplateNumber;
    }

    public String getGroupDataName() {
        return groupDataName;
    }

    public void setGroupDataName(String groupDataName) {
        this.groupDataName = groupDataName;
    }

    public String getGroupDataComment() {
        return groupDataComment;
    }

    public void setGroupDataComment(String groupDataComment) {
        this.groupDataComment = groupDataComment;
    }

    public Integer getGroupParamSuitNumber() {
        return groupParamSuitNumber;
    }

    public void setGroupParamSuitNumber(Integer groupParamSuitNumber) {
        this.groupParamSuitNumber = groupParamSuitNumber;
    }
}