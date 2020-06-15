package com.tf.base.go.define;

import com.alibaba.fastjson.JSONObject;
import com.tf.base.go.type.Category;
import com.tf.base.go.type.NodeType;

import java.util.Collection;
import java.util.List;

public interface IndexNode extends IndexNodeModel, IndexNodeIf,IndexNodePictrue {
    /**
     * 是否连线
     *
     * @return
     */
    boolean hasLine();

    /**
     * 获取主键
     *
     * @return
     */
    Integer getKey();

    /**
     * 获取节点分类
     *
     * @return
     */
    Category getNodeCategory();

    /**
     * 获取节点类型，只有Category类型为Datasource和Picture的节点才有类型
     *
     * @return
     */
    NodeType getNodeType();

    /**
     * 获取节点的展示内容
     */
    String getNodeText();

    /**
     * 获取此节点的前置节点
     *
     * @return
     */
    Collection<IndexNode> getPreNodes();

    /**
     * 获取此节点的后置节点
     *
     * @return
     */
    Collection<IndexNode> getNextNodes();

    /**
     * 获取此节点的输入连线
     *
     * @return
     */
    List<NodeLink> getInNodeLinks();

    /**
     * 获取此节点的输出连线
     *
     * @return
     */
    List<NodeLink> getOutNodeLink();


    /**
     * 判断此节点是否为输出节点，输出节点的后续节点为图或者表
     *
     * @return
     */
    boolean isOutNode();

    /**
     * 判断是否是结束节点,此节点是项目的结束节点，一个项目结束节点只有一个
     *
     * @return
     */
    boolean isEndNode();




    String getParamName();



    String getDefaultValue();

    /**
     * 判断是否是启动节点
     *
     * @return
     */
    boolean isStartNode();

    void addInNodeLink(NodeLink link);

    void addOutNodeLink(NodeLink link);

    JSONObject getInServices();

    JSONObject getOutServices();

}
