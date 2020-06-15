package com.tf.base.go.engine.Impl;

import com.alibaba.fastjson.JSONObject;
import com.tf.base.common.domain.IndexInfoProcess;
import com.tf.base.common.persistence.IndexInfoMapper;
import com.tf.base.go.define.IndexDefinitions;
import com.tf.base.go.engine.IndexEngine;
import com.tf.base.go.service.IndexRepositoryService;
import com.tf.base.go.service.IndexRuntimeService;
import com.tf.base.go.service.IndexTaskResult;
import com.tf.base.go.service.IndexTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("indexEngine")
public class IndexEngineImpl implements IndexEngine {
    @Autowired
    @Qualifier("indexRepositoryService")
    IndexRepositoryService indexRepositoryService;
    @Autowired
    @Qualifier("indexRuntimeService")
    IndexRuntimeService indexRuntimeService;
    @Autowired
    @Qualifier("indexTaskService")
    IndexTaskService indexTaskService;
    @Autowired
    IndexInfoMapper indexInfoMapper ;

//   indexTaskService public IndexEngineImpl(IndexInfo indexInfo) {
//        indexRepositoryService = IndexRepositoryServiceImpl.build(this, indexInfo);
//        indexRuntimeService = IndexRuntimeServiceImpl.build(this);
//        indexTaskService = IndexTaskServiceImpl.build(this);
//    }

    @Override
    public IndexRepositoryService getIndexRepositoryService() {
        return this.indexRepositoryService;
    }

    @Override
    public IndexRuntimeService getIndexRuntimeService() {
        return this.indexRuntimeService;
    }

    @Override
    public IndexTaskService getIndexTaskService() {
        return this.indexTaskService;
    }


    @Override
    public IndexTaskResult singleStepExecution(Integer nodeKey, Integer indexDefineId,String evalPath) {
        IndexTaskResult result = new IndexTaskResult();
        //构建初始化环境
        IndexDefinitions indexDefinitions = getIndexRepositoryService().getIndexDefinitions(indexInfoMapper.selectByPrimaryKey(indexDefineId), evalPath);
        IndexInfoProcess indexInfoProcess = getIndexRuntimeService().startIndexDebug(nodeKey,indexDefineId,this,indexDefinitions);
        if(indexInfoProcess.getIndexStatus()==-1){
            result.setResultCode(IndexTaskResult.INDEX_ERROR);
            result.setMsg(indexInfoProcess.getIndexProcessData());
            return  result;
        }
        result.setResultCode(IndexTaskResult.INDEX_SUCCESS);
        result.setMsg("调试成功");
        return result;
    }



    @Override
    public IndexInfoProcess startIndexByProject(Integer assessId, Integer indexId, JSONObject param,String evalPath) {
        //构建初始化环境
        IndexDefinitions indexDefinitions = getIndexRepositoryService().getIndexDefinitions(indexInfoMapper.selectByPrimaryKey(indexId),evalPath);//构建初始化环境
        return  getIndexRuntimeService().startIndexByProject( assessId,  indexId,  param,this,indexDefinitions);
    }
}
