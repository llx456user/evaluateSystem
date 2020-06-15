package com.tf.base.go.define;


/**
 * Created by HP on 2018/4/12.
 */
public interface NodeLink {

    /**
     * 获取from节点
     * @return
     */
    IndexNode getFromNode();

    /**
     * 获取from port
     * @return
     */
    String getFromPort();

    /**
     * 获取到达节点
     * @return
     */
    IndexNode getToNode();

    /**
     * 获取到达 port
     * @return
     */
    String getToPort();

    /**
     * 获取模型到达节点类型
     * @return
     */
    String getModelPortType();

    /**
     * 是否数组
     * @return
     */
    boolean isArray();

    /**
     * if条件判断
     * @return
     */
    String getText();

    boolean isX();

    boolean isY();

    boolean isL();


}
