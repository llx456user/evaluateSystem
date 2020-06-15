package com.tf.base.go.engine;


import com.alibaba.fastjson.JSONObject;
import com.tf.base.common.domain.IndexInfoProcess;
import com.tf.base.go.service.IndexRepositoryService;
import com.tf.base.go.service.IndexRuntimeService;
import com.tf.base.go.service.IndexTaskResult;
import com.tf.base.go.service.IndexTaskService;

public interface IndexEngine {

    /**
     * 获取指标资源服务
     * @return
     */
    IndexRepositoryService getIndexRepositoryService();

    /**
     * 获取指标运行时服务
     * @return
     */
    IndexRuntimeService getIndexRuntimeService();

    /**
     * 获取指标任务服务
     * @return
     */
    IndexTaskService getIndexTaskService();



    /**
     * 单步执行，调试时候用
     * @param nodeKey
     * @return
     */
    IndexTaskResult singleStepExecution(Integer nodeKey,Integer indexDefineId,String evalPath);



    IndexInfoProcess startIndexByProject(Integer assessId, Integer indexId, JSONObject param,String evalPath);

}
