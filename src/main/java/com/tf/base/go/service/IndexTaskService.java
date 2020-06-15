package com.tf.base.go.service;

import com.alibaba.fastjson.JSONObject;
import com.tf.base.common.domain.IndexNodeProcess;
import com.tf.base.go.define.IndexDefinitions;
import com.tf.base.go.engine.IndexEngine;

import java.util.Collection;
import java.util.Map;

/**
 * 在指标定义中的每一个执行节点被称为一个Task，
 * 对业务的数据存取，状态变更等操作均需要在Task中完成。
 * 它提供了运行时任务查询、领取、完成、删除以及变量设置等功能。
 */
public interface IndexTaskService {

//    /**
//     * 获取所有任务节点
//     *
//     * @return
//     */
//    Collection<IndexTask> getIndexTasks();

    IndexNodeProcess getIndexNodeProcess( Integer id) ;

    /**
     * 获取当前任务节点
     *
     * @return
     */
    IndexTask getCurrentTask();



    /**
     * 执行任务节点
     *
     * @param taskKey
     * @return
     */
    IndexTaskResult execute(IndexEngine indexEngine,Integer taskKey,Map<Integer,IndexTask> keyTaskMap);


    /**
     * 批量执行任务
     * @return
     */
    IndexTaskResult  batchExecute(IndexEngine indexEngine,JSONObject param,Map<Integer,IndexTask> indexTaskMap,IndexDefinitions indexDefinitions);

//    /**
//     * 增加任务节点
//     * @param task
//     */
//     void addIndexTask(IndexTask task);

//    /**
//     * 清空缓存
//     */
//    void  clear();
//    /**
//     * 获取指标信息任务
//     * @return
//     */
//    Map<Integer,IndexTask> getIndexTaskMap();

    /**
     * 初始化数据节点
     * @return
     */
    IndexTaskResult doExecuteInitData(IndexEngine indexEngine,Map<Integer,IndexTask> keyTaskMap);


    /**
     * 初始化数据节点，包括常量
     * @return
     */
    IndexTaskResult doExecuteInitDataAndConstant(IndexEngine indexEngine, JSONObject dataObject, Map<Integer,IndexTask> keyTaskMap, IndexDefinitions indexDefinitions);



}
