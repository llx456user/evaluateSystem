package com.tf.base.common.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "index_model")
public class IndexModel {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 节点类型
     */
    @Column(name = "node_type")
    private String nodeType;

    /**
     * 指标id
     */
    @Column(name = "index_id")
    private Integer indexId;

    /**
     * 模型id
     */
    @Column(name = "model_id")
    private Integer modelId;

    /**
     * 节点key
     */
    @Column(name = "node_key")
    private Integer nodeKey;

    /**
     * 节点分类：0：数据源，1：模型：2：图;3:常数;4:IF判断;5:表格
     */
    @Column(name = "node_category")
    private Integer nodeCategory;

    /**
     * 启动节点：0:否，1:是
     */
    @Column(name = "start_node")
    private Integer startNode;

    /**
     * 结束节点：0:否，1:是
     */
    @Column(name = "end_node")
    private Integer endNode;

    /**
     * 符号类型：>,>=,==,<=,<
     */
    @Column(name = "sign_type")
    private String signType;

    /**
     * 设置数字
     */
    @Column(name = "setting_num")
    private String settingNum;

    /**
     * 节点展示内容
     */
    @Column(name = "node_text")
    private String nodeText;

    /**
     * 创建人ID
     */
    @Column(name = "create_uid")
    private Integer createUid;

    /**
     * 更新人ID
     */
    @Column(name = "update_uid")
    private Integer updateUid;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否删除【0:否1：是】
     */
    private Integer isdelete;

    /**
     * 参数名称：
     */
    @Column(name = "param_name")
    private String paramName;

    /**
     * 设置数字
     */
    @Column(name = "default_value")
    private String defaultValue;

    /**
     * 图形标题
     */
    @Column(name = "picture_title")
    private String pictureTitle;

    /**
     * x标题
     */
    @Column(name = "x_title")
    private String xTitle;

    /**
     * y标题
     */
    @Column(name = "y_title")
    private String yTitle;

    @Transient
    private  String projectNodeName ;//项目节点名称

    @Transient
    public String getProjectNodeName() {
        return projectNodeName;
    }

    @Transient
    public void setProjectNodeName(String projectNodeName) {
        this.projectNodeName = projectNodeName;
    }

    @Transient
    private  String indexName ;//指标名称

    @Transient
    public String getIndexName() {
        return indexName;
    }

    @Transient
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

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
     * 获取节点类型
     *
     * @return node_type - 节点类型
     */
    public String getNodeType() {
        return nodeType;
    }

    /**
     * 设置节点类型
     *
     * @param nodeType 节点类型
     */
    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    /**
     * 获取指标id
     *
     * @return index_id - 指标id
     */
    public Integer getIndexId() {
        return indexId;
    }

    /**
     * 设置指标id
     *
     * @param indexId 指标id
     */
    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    /**
     * 获取模型id
     *
     * @return model_id - 模型id
     */
    public Integer getModelId() {
        return modelId;
    }

    /**
     * 设置模型id
     *
     * @param modelId 模型id
     */
    public void setModelId(Integer modelId) {
        this.modelId = modelId;
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
     * 获取节点分类：0：数据源，1：模型：2：图;3:常数;4:IF判断;5:表格
     *
     * @return node_category - 节点分类：0：数据源，1：模型：2：图;3:常数;4:IF判断;5:表格
     */
    public Integer getNodeCategory() {
        return nodeCategory;
    }

    /**
     * 设置节点分类：0：数据源，1：模型：2：图;3:常数;4:IF判断;5:表格
     *
     * @param nodeCategory 节点分类：0：数据源，1：模型：2：图;3:常数;4:IF判断;5:表格
     */
    public void setNodeCategory(Integer nodeCategory) {
        this.nodeCategory = nodeCategory;
    }

    /**
     * 获取启动节点：0:否，1:是
     *
     * @return start_node - 启动节点：0:否，1:是
     */
    public Integer getStartNode() {
        return startNode;
    }

    /**
     * 设置启动节点：0:否，1:是
     *
     * @param startNode 启动节点：0:否，1:是
     */
    public void setStartNode(Integer startNode) {
        this.startNode = startNode;
    }

    /**
     * 获取结束节点：0:否，1:是
     *
     * @return end_node - 结束节点：0:否，1:是
     */
    public Integer getEndNode() {
        return endNode;
    }

    /**
     * 设置结束节点：0:否，1:是
     *
     * @param endNode 结束节点：0:否，1:是
     */
    public void setEndNode(Integer endNode) {
        this.endNode = endNode;
    }

    /**
     * 获取符号类型：>,>=,==,<=,<
     *
     * @return sign_type - 符号类型：>,>=,==,<=,<
     */
    public String getSignType() {
        return signType;
    }

    /**
     * 设置符号类型：>,>=,==,<=,<
     *
     * @param signType 符号类型：>,>=,==,<=,<
     */
    public void setSignType(String signType) {
        this.signType = signType;
    }

    /**
     * 获取设置数字
     *
     * @return setting_num - 设置数字
     */
    public String getSettingNum() {
        return settingNum;
    }

    /**
     * 设置设置数字
     *
     * @param settingNum 设置数字
     */
    public void setSettingNum(String settingNum) {
        this.settingNum = settingNum;
    }

    /**
     * 获取节点展示内容
     *
     * @return node_text - 节点展示内容
     */
    public String getNodeText() {
        return nodeText;
    }

    /**
     * 设置节点展示内容
     *
     * @param nodeText 节点展示内容
     */
    public void setNodeText(String nodeText) {
        this.nodeText = nodeText;
    }

    /**
     * 获取创建人ID
     *
     * @return create_uid - 创建人ID
     */
    public Integer getCreateUid() {
        return createUid;
    }

    /**
     * 设置创建人ID
     *
     * @param createUid 创建人ID
     */
    public void setCreateUid(Integer createUid) {
        this.createUid = createUid;
    }

    /**
     * 获取更新人ID
     *
     * @return update_uid - 更新人ID
     */
    public Integer getUpdateUid() {
        return updateUid;
    }

    /**
     * 设置更新人ID
     *
     * @param updateUid 更新人ID
     */
    public void setUpdateUid(Integer updateUid) {
        this.updateUid = updateUid;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取是否删除【0:否1：是】
     *
     * @return isdelete - 是否删除【0:否1：是】
     */
    public Integer getIsdelete() {
        return isdelete;
    }

    /**
     * 设置是否删除【0:否1：是】
     *
     * @param isdelete 是否删除【0:否1：是】
     */
    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    /**
     * 获取参数名称：
     *
     * @return param_name - 参数名称：
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * 设置参数名称：
     *
     * @param paramName 参数名称：
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * 获取设置数字
     *
     * @return default_value - 设置数字
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * 设置设置数字
     *
     * @param defaultValue 设置数字
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * 获取图形标题
     *
     * @return picture_title - 图形标题
     */
    public String getPictureTitle() {
        return pictureTitle;
    }

    /**
     * 设置图形标题
     *
     * @param pictureTitle 图形标题
     */
    public void setPictureTitle(String pictureTitle) {
        this.pictureTitle = pictureTitle;
    }

    /**
     * 获取x标题
     *
     * @return x_title - x标题
     */
    public String getxTitle() {
        return xTitle;
    }

    /**
     * 设置x标题
     *
     * @param xTitle x标题
     */
    public void setxTitle(String xTitle) {
        this.xTitle = xTitle;
    }

    /**
     * 获取y标题
     *
     * @return y_title - y标题
     */
    public String getyTitle() {
        return yTitle;
    }

    /**
     * 设置y标题
     *
     * @param yTitle y标题
     */
    public void setyTitle(String yTitle) {
        this.yTitle = yTitle;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", nodeType=").append(nodeType);
        sb.append(", indexId=").append(indexId);
        sb.append(", modelId=").append(modelId);
        sb.append(", nodeKey=").append(nodeKey);
        sb.append(", nodeCategory=").append(nodeCategory);
        sb.append(", startNode=").append(startNode);
        sb.append(", endNode=").append(endNode);
        sb.append(", signType=").append(signType);
        sb.append(", settingNum=").append(settingNum);
        sb.append(", nodeText=").append(nodeText);
        sb.append(", createUid=").append(createUid);
        sb.append(", updateUid=").append(updateUid);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", paramName=").append(paramName);
        sb.append(", defaultValue=").append(defaultValue);
        sb.append(", pictureTitle=").append(pictureTitle);
        sb.append(", xTitle=").append(xTitle);
        sb.append(", yTitle=").append(yTitle);
        sb.append("]");
        return sb.toString();
    }
}
