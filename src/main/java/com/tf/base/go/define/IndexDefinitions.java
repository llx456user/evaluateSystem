package com.tf.base.go.define;

import com.tf.base.common.domain.IndexInfo;
import com.tf.base.common.domain.ModelInfo;

import java.util.List;

/**
 * 主要负责 点线的关系处理
 */
public interface IndexDefinitions {
    /**
     * 获取指标信息
     * @return
     */
      IndexInfo getIndexInfo();

    /**
     * 获取Model类型的节点
     * @return
     */
      List<IndexNode> getNodes();

    /**
     * 获得启动模型节点
     * @return
     */
      List<IndexNode> getStartModelNode() ;
    /**
     * 获取定义的数据输入节点
     * @return
     */
     List<IndexNode> getDataNode();
    /**
     * 判断流程的连线是否完整，有没有多余的没有连线的节点
     * @return
     */
      IndexNode  getNotLineNode();
    /**
     * 限定节点模型只有一个结束节点
     * @return
     */
      boolean isUniqueEndNode();

    /**
     *
     */
    IndexNode   getNodeByKey(Integer key);

    /**
     * 检验IF节点连线，并核定是否设置了值。
     * @return
     */
    boolean      checkIfNode() ;

    /**
     * 核定常量和数据源的名字
     * @return
     */
    boolean checkName();

    /**
     * 核定输出图标的标题
     * @return
     */
    boolean checkTitle();

    /**
     * 获取eval保存路径
     * @return
     */
    String getEvalPath();

}
