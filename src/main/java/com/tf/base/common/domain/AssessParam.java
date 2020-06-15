package com.tf.base.common.domain;

import javax.persistence.*;

@Table(name = "assess_param")
public class AssessParam {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 评估ID
     */
    @Column(name = "assess_id")
    private Integer assessId;

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
     * @return assess_id - 评估ID
     */
    public Integer getAssessId() {
        return assessId;
    }

    /**
     * 设置评估ID
     *
     * @param assessId 评估ID
     */
    public void setAssessId(Integer assessId) {
        this.assessId = assessId;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", assessId=").append(assessId);
        sb.append(", indexId=").append(indexId);
        sb.append(", paramName=").append(paramName);
        sb.append(", paramValue=").append(paramValue);
        sb.append(", paramType=").append(paramType);
        sb.append(", nodeId=").append(nodeId);
        sb.append(", nodeKey=").append(nodeKey);
        sb.append("]");
        return sb.toString();
    }
}