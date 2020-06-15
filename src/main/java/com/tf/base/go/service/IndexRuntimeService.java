package com.tf.base.go.service;

import com.alibaba.fastjson.JSONObject;
import com.tf.base.common.domain.IndexInfoProcess;
import com.tf.base.go.define.IndexDefinitions;
import com.tf.base.go.engine.IndexEngine;

/**
 * 每当一个Index定义被启动一次之后，都会生成一个相应的对象实例。
 * IndexRuntimeService提供了启动、查询实例、设置获取实例变量等功能。
 * 此外它还提供了对部署，定义和实例的存取服务
 */
public interface IndexRuntimeService {


    /**
     * 启动流程，按照评估进行
     * @param assessId
     * @param indexId
     * @param param
     * @param indexEngine
     * @return
     */
    IndexInfoProcess startIndexByProject(Integer assessId, Integer indexId, JSONObject param, IndexEngine indexEngine, IndexDefinitions indexDefinitions);

    /**
     * 获取当前流程实例
     *
     * @return
     */
    IndexInfoProcess getIndexInfoProcess(Integer indexInfoId);

    /***
     * 启动新的指标实例，并返回指标实例对象，创建流程实例，并初始化所有节点实例。
     * @param indexDefineId
     * @param param
     * @return
     */
    IndexInfoProcess startIndexFromData(Integer indexDefineId, JSONObject param,IndexEngine indexEngine,IndexDefinitions indexDefinitions);

    /**
     * 启动历史指标实例，业务场景，就是继续上次实例
     *
     * @param indexInfoProcess
     * @return
     */
    IndexInfoProcess startIndexWithHistory(IndexInfoProcess indexInfoProcess);

    /**
     * 启动调试指标实例,创建流程实例，并初始化所有节点实例
     *
     * @param indexDefineId
     * @return
     */
    IndexInfoProcess startIndexDebug(Integer nodeKey, Integer indexDefineId,IndexEngine indexEngine,IndexDefinitions indexDefinitions);




//    /**
//     * 单步执行，调试时候用
//     * @param nodeKey
//     * @return
//     */
//    IndexTaskResult singleStepExecution(Integer nodeKey,IndexEngine indexEngine);



}
