package com.tf.base.go.service;

import com.tf.base.common.domain.IndexNodeProcess;
import com.tf.base.go.define.IndexNode;
import com.tf.base.go.engine.IndexEngine;

import java.util.List;
import java.util.Map;

/**
 * 所有的执行节点的动作都是任务
 */
public interface IndexTask {

//    List<IndexTask> getNextIndexTask();
    /**
     * 前置任务
     * @return
     */
     List<IndexTask> getPreIndexTask();
    /**
     * 获取指标引擎
     * @return
     */
    IndexEngine getIndexEngine();
    /**
     * 获取任务实例节点
     * @return
     */
    IndexNodeProcess getIndexNodeProcess();
    /**
     * 执行任务
     * @param param
     * @param dllPath
     * @return
     */
    IndexTaskResult execute(String param,String dllPath);

    /**
     * 任务对应的节点定义信息
     * @return
     */
     IndexNode getNode();


     Map<Integer, IndexTask> getKeyTaskMap() ;


     void setKeyTaskMap(Map<Integer, IndexTask> keyTaskMap) ;
}
