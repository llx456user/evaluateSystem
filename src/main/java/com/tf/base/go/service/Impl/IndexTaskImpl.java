package com.tf.base.go.service.Impl;

import com.tf.base.common.domain.IndexNodeProcess;

import com.tf.base.go.define.IndexDefinitions;
import com.tf.base.go.define.IndexNode;
import com.tf.base.go.engine.IndexEngine;
import com.tf.base.go.service.IndexTask;
import com.tf.base.go.service.IndexTaskResult;
import com.tf.base.go.util.InparamterUtil;
import com.tf.base.jni.JnaResult;
import com.tf.base.jni.JnaUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class IndexTaskImpl implements IndexTask {
    IndexEngine indexEngine;
    IndexNodeProcess indexNodeProcess;
    IndexDefinitions indexDefinitions;
    private Map<Integer,IndexTask> keyTaskMap  ;
    @Override
    public Map<Integer, IndexTask> getKeyTaskMap() {
        return keyTaskMap;
    }
    @Override
    public void setKeyTaskMap(Map<Integer, IndexTask> keyTaskMap) {
        this.keyTaskMap = keyTaskMap;
    }

    public IndexTaskImpl(IndexEngine indexEngine, IndexNodeProcess indexNodeProcess,IndexDefinitions indexDefinitions) {
        this.indexEngine = indexEngine;
        this.indexNodeProcess = indexNodeProcess;
        this.indexDefinitions = indexDefinitions;
    }

//    @Override
//    public List<IndexTask> getNextIndexTask() {
//        List<IndexTask> list = new ArrayList<>();
//        Collection<IndexNode> nodes = getNode().getNextNodes();
//        for (IndexNode node:nodes) {
//            IndexTask indexTask = keyTaskMap.get(node.getKey());
//            IndexNodeProcess indexNodeProcess = getIndexEngine().getIndexTaskService().getIndexNodeProcess(indexTask.getIndexNodeProcess().getId());
//            indexTask.getIndexNodeProcess().setNodeOutputParam(indexNodeProcess.getNodeOutputParam());
//            indexTask.getIndexNodeProcess().setNodeStatus(indexNodeProcess.getNodeStatus());
//            list.add(indexTask);
//        }
//        return list ;
//    }

    @Override
    public List<IndexTask> getPreIndexTask() {
        List<IndexTask> list = new ArrayList<>();
        Collection<IndexNode> nodes = getNode().getPreNodes();
        for (IndexNode node:nodes) {
            IndexTask indexTask = keyTaskMap.get(node.getKey());
            IndexNodeProcess indexNodeProcess = getIndexEngine().getIndexTaskService().getIndexNodeProcess(indexTask.getIndexNodeProcess().getId());
            indexTask.getIndexNodeProcess().setNodeOutputParam(indexNodeProcess.getNodeOutputParam());
            indexTask.getIndexNodeProcess().setNodeStatus(indexNodeProcess.getNodeStatus());
            list.add(indexTask);
        }
        return list ;
    }

    @Override
    public IndexEngine getIndexEngine() {
        return this.indexEngine;
    }

    @Override
    public IndexNodeProcess getIndexNodeProcess() {
        return this.indexNodeProcess;
    }

    @Override
    public IndexTaskResult execute(String param,String dllPath) {
        IndexTaskResult indexTaskResult = new IndexTaskResult();
        try {
            JnaResult result = JnaUtil.callDllFun(dllPath, param);
            indexTaskResult.setResultCode(result.getResultcode());
            indexTaskResult.setMsg(result.getMsg());
        }catch (Exception e){
            indexTaskResult.setResultCode(IndexTaskResult.INDEX_ERROR);
            indexTaskResult.setMsg("加载dll失败");
        }
        return indexTaskResult;
    }


    @Override
    public IndexNode getNode() {
        return indexDefinitions.getNodeByKey(this.getIndexNodeProcess().getNodeKey());
    }
}
