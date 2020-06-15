package com.tf.base.go.define;

import com.alibaba.fastjson.JSONObject;
import com.tf.base.common.domain.ModelInfo;

public interface IndexNodeModel {
    /**
     * 判断此节点是否为输出节点，输出节点的后续节点为图或者表
     * @return
     */
    boolean isOutNode();
    /**
     * 判断是否是结束节点,此节点是项目的结束节点，一个项目结束节点只有一个
     * @return
     */
    boolean isEndNode();

    /**
     * 判断是否是启动节点
     * @return
     */
    boolean  isStartNode();
    /**
     * 获取模型信息
     * @return
     */
     Integer  getModelId();

}
