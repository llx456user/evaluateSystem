package com.tf.base.common.domain;

import javax.persistence.*;

@Table(name = "project_param_template")
public class ProjectParamTemplate {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 评估ID
     */
    @Column(name = "project_id")
    private Integer projectId;

    /**
     * 指标ID
     */
    @Column(name = "index_id")
    private Integer indexId;

    /**
     * 参数名称
     */
    @Column(name = "param_name")
    private String paramName;

    /**
     * 参数id
     */
    @Column(name = "project_param_id")
    private Integer projectParamId;

    /**
     * 参数名称
     */
    @Column(name = "project_param_name")
    private String projectParamName;

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
     * 节点ID
     */
    @Column(name = "node_id")
    private Integer nodeId;

    /**
     * 节点key
     */
    @Column(name = "node_key")
    private Integer nodeKey;

    /**
     * 是否自定义（0-否，1-是）
     */
    @Column(name = "is_self_defined")
    private Integer isSelfDefined;

    /**
     * 数据关联选择的数据模板分组信息 例如1_1_520
     */
    @Column(name = "group_template_data")
    private String groupTemplateData;

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
     * 获取评估ID
     *
     * @return project_id - 评估ID
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * 设置评估ID
     *
     * @param projectId 评估ID
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * 获取指标ID
     *
     * @return index_id - 指标ID
     */
    public Integer getIndexId() {
        return indexId;
    }

    /**
     * 设置指标ID
     *
     * @param indexId 指标ID
     */
    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
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
     * 获取参数id
     *
     * @return project_param_id - 参数id
     */
    public Integer getProjectParamId() {
        return projectParamId;
    }

    /**
     * 设置参数id
     *
     * @param projectParamId 参数id
     */
    public void setProjectParamId(Integer projectParamId) {
        this.projectParamId = projectParamId;
    }

    /**
     * 获取参数名称
     *
     * @return project_param_name - 参数名称
     */
    public String getProjectParamName() {
        return projectParamName;
    }

    /**
     * 设置参数名称
     *
     * @param projectParamName 参数名称
     */
    public void setProjectParamName(String projectParamName) {
        this.projectParamName = projectParamName;
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
     * 获取节点ID
     *
     * @return node_id - 节点ID
     */
    public Integer getNodeId() {
        return nodeId;
    }

    /**
     * 设置节点ID
     *
     * @param nodeId 节点ID
     */
    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    /**
     * 获取节点key
     *
     * @return node_key - 节点key
     */
    public Integer getNodeKey() {
        return nodeKey;
    }

    /**
     * 设置节点key
     *
     * @param nodeKey 节点key
     */
    public void setNodeKey(Integer nodeKey) {
        this.nodeKey = nodeKey;
    }

    /**
     * 获取是否自定义（0-否，1-是）
     *
     * @return is_self_defined - 是否自定义（0-否，1-是）
     */
    public Integer getIsSelfDefined() {
        return isSelfDefined;
    }

    /**
     * 设置是否自定义（0-否，1-是）
     *
     * @param isSelfDefined 是否自定义（0-否，1-是）
     */
    public void setIsSelfDefined(Integer isSelfDefined) {
        this.isSelfDefined = isSelfDefined;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", indexId=").append(indexId);
        sb.append(", paramName=").append(paramName);
        sb.append(", projectParamId=").append(projectParamId);
        sb.append(", projectParamName=").append(projectParamName);
        sb.append(", paramValue=").append(paramValue);
        sb.append(", paramType=").append(paramType);
        sb.append(", nodeId=").append(nodeId);
        sb.append(", nodeKey=").append(nodeKey);
        sb.append(", isSelfDefined=").append(isSelfDefined);
        sb.append(", groupTemplateData=").append(groupTemplateData);
        sb.append("]");
        return sb.toString();
    }

    public String getGroupTemplateData() {
        return groupTemplateData;
    }

    public void setGroupTemplateData(String groupTemplateData) {
        this.groupTemplateData = groupTemplateData;
    }
}