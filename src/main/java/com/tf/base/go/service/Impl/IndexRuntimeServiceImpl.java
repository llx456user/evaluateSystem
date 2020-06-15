package com.tf.base.go.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.tf.base.common.domain.IndexInfoProcess;
import com.tf.base.common.domain.IndexModel;
import com.tf.base.common.domain.IndexNodeProcess;
import com.tf.base.common.persistence.IndexInfoProcessMapper;
import com.tf.base.common.persistence.IndexModelMapper;
import com.tf.base.common.persistence.IndexNodeProcessMapper;
import com.tf.base.common.service.BaseService;
import com.tf.base.go.define.IndexDefinitions;
import com.tf.base.go.engine.IndexEngine;
import com.tf.base.go.service.IndexRuntimeService;
import com.tf.base.go.service.IndexTask;
import com.tf.base.go.service.IndexTaskResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service("indexRuntimeService")
public class IndexRuntimeServiceImpl implements IndexRuntimeService {
    @Autowired
    IndexInfoProcessMapper indexInfoProcessMapper;
    @Autowired
    IndexModelMapper indexModelMapper;
    @Autowired
    IndexNodeProcessMapper indexNodeProcessMapper;
    @Autowired
    private BaseService baseService;


    @Override
    public IndexInfoProcess startIndexByProject(Integer assessId, Integer indexId, JSONObject param, IndexEngine indexEngine,IndexDefinitions indexDefinitions) {
        IndexInfoProcess indexInfoProcess = new IndexInfoProcess();
        List<IndexNodeProcess> nodeProList ;
        Example example = new Example(IndexInfoProcess.class);
        example.createCriteria().andEqualTo("indexId", indexId).andEqualTo("processType",1).andEqualTo("businessId",assessId);
        List<IndexInfoProcess> indexInfoList = indexInfoProcessMapper.selectByExample(example);
        if(indexInfoList.size()>0){
            indexInfoProcess=indexInfoList.get(0);
            Example indexNodeExample = new Example(IndexNodeProcess.class);
            indexNodeExample.createCriteria().andEqualTo("indexProcessId", indexInfoList.get(0).getId()).andEqualTo("isdelete",0);
            nodeProList = indexNodeProcessMapper.selectByExample(indexNodeExample);
        }else {
            Integer userid = Integer.parseInt(baseService.getUserId());
            Date now = new Date();
            indexInfoProcess.setCreateTime(now);
            indexInfoProcess.setCreateUid(userid);
            indexInfoProcess.setUpdateTime(now);
            indexInfoProcess.setUpdateUid(userid);
            indexInfoProcess.setIndexStatus(0);//表示未开始
            indexInfoProcess.setProcessType(1);//表示执行类型
            indexInfoProcess.setBusinessId(assessId);
            indexInfoProcess.setIndexId(indexId);
            if (param != null) {
                indexInfoProcess.setIndexProcessParam(param.toJSONString());
            }
            indexInfoProcess.setIsdelete(0);
            // 设置参数
            indexInfoProcessMapper.insertSelective(indexInfoProcess);
            indexInfoProcess = indexInfoProcess;
            IndexModel indexModelParam = new IndexModel();
            indexModelParam.setIndexId(indexId);
            List<IndexModel> indexmodelList = indexModelMapper.select(indexModelParam);
            nodeProList = new ArrayList<>();
            setIndexNodeProcess(nodeProList, userid, now, indexInfoProcess, indexmodelList);
            if (nodeProList.size() > 0) {
                indexNodeProcessMapper.insertList(nodeProList);
            }
        }
        IndexTaskResult result = indexEngine.getIndexTaskService().batchExecute(indexEngine, param,buildIndexTaskList(nodeProList,indexEngine,indexDefinitions),indexDefinitions);
        if(result.getResultCode()==IndexTaskResult.INDEX_ERROR){
            indexInfoProcess.setIndexStatus(-1);

        }else if(result.getResultCode()==IndexTaskResult.INDEX_SUCCESS){
            indexInfoProcess.setIndexStatus(2);
        }

        indexInfoProcess.setIndexProcessData(result.getMsg());

        return indexInfoProcess;
    }

    private void setIndexNodeProcess(List<IndexNodeProcess> nodeProList, Integer userid, Date now, IndexInfoProcess indexInfoProcess, List<IndexModel> indexmodelList) {
        for (IndexModel model : indexmodelList) {
            IndexNodeProcess indexNodeProcess = new IndexNodeProcess();
            indexNodeProcess.setCreateTime(now);
            indexNodeProcess.setCreateUid(userid);
            indexNodeProcess.setUpdateTime(now);
            indexNodeProcess.setUpdateUid(userid);
            indexNodeProcess.setNodeStatus(0);
            indexNodeProcess.setIsdelete(0);
            indexNodeProcess.setIndexProcessId(indexInfoProcess.getId());
            indexNodeProcess.setNodeKey(model.getNodeKey());
            nodeProList.add(indexNodeProcess);
        }
    }

    @Override
    public IndexInfoProcess getIndexInfoProcess(Integer indexInfoId) {
        return this.indexInfoProcessMapper.selectByPrimaryKey(indexInfoId);
    }


    @Override
    public IndexInfoProcess startIndexFromData(Integer indexDefineId, JSONObject param,IndexEngine indexEngine,IndexDefinitions indexDefinitions) {
        Integer userid = Integer.parseInt(baseService.getUserId());
        Date now = new Date();
        IndexInfoProcess indexInfoProcess = new IndexInfoProcess();
        indexInfoProcess.setCreateTime(now);
        indexInfoProcess.setCreateUid(userid);
        indexInfoProcess.setUpdateTime(now);
        indexInfoProcess.setUpdateUid(userid);
        indexInfoProcess.setIndexStatus(0);//表示未开始
        indexInfoProcess.setProcessType(1);
        if (param != null) {
            indexInfoProcess.setIndexProcessParam(param.toJSONString());
        }

        indexInfoProcess.setIsdelete(0);
//      设置参数    indexInfoProcess.set
        indexInfoProcessMapper.insertSelective(indexInfoProcess);
        IndexModel indexModelParam = new IndexModel();
        indexModelParam.setIndexId(indexDefineId);
        List<IndexModel> indexmodelList = indexModelMapper.select(indexModelParam);
        List<IndexNodeProcess> nodeProcessesList = new ArrayList<>();
        setIndexNodeProcess(nodeProcessesList, userid, now, indexInfoProcess, indexmodelList);
        if (nodeProcessesList.size() > 0) {
            indexNodeProcessMapper.insertList(nodeProcessesList);
        }
        buildIndexTaskList(nodeProcessesList,indexEngine,indexDefinitions);
        return indexInfoProcess;
    }

    @Override
    public IndexInfoProcess startIndexWithHistory(IndexInfoProcess indexInfoProcess) {
        return null;
    }

    @Override
    public IndexInfoProcess startIndexDebug(Integer nodeKey, Integer indexDefineId,IndexEngine indexEngine,IndexDefinitions indexDefinitions) {
        Example example = new Example(IndexInfoProcess.class);
        example.createCriteria().andEqualTo("indexId", indexDefineId).andEqualTo("processType",0);
        List<IndexInfoProcess> indexInfoList = indexInfoProcessMapper.selectByExample(example);
        if(indexInfoList!=null&&indexInfoList.size()>0){//如果进行过调试，则需要清除数据，重新处理
            Example indexNodeExample = new Example(IndexNodeProcess.class);
            indexNodeExample.createCriteria().andEqualTo("indexProcessId", indexInfoList.get(0).getId()).andEqualTo("isdelete", 0);
            indexNodeProcessMapper.deleteByExample(indexNodeExample);
            indexInfoProcessMapper.deleteByPrimaryKey(indexInfoList.get(0).getId());
        }

        Integer userid = Integer.parseInt(baseService.getUserId());
        Date now = new Date();
        IndexInfoProcess indexInfoProcess = new IndexInfoProcess();
        indexInfoProcess.setCreateTime(now);
        indexInfoProcess.setCreateUid(userid);
        indexInfoProcess.setUpdateTime(now);
        indexInfoProcess.setUpdateUid(userid);
        indexInfoProcess.setIndexId(indexDefineId);
        indexInfoProcess.setIndexStatus(0);//表示未开始
        indexInfoProcess.setProcessType(0);
        indexInfoProcess.setIsdelete(0);
        indexInfoProcessMapper.insertSelective(indexInfoProcess);
        IndexModel indexModelParam = new IndexModel();
        indexModelParam.setIndexId(indexDefineId);
        List<IndexModel> indexmodelList = indexModelMapper.select(indexModelParam);
        List<IndexNodeProcess> nodeProcessesList = new ArrayList<>();
        setIndexNodeProcess(nodeProcessesList, userid, now, indexInfoProcess, indexmodelList);
        if (nodeProcessesList.size() > 0) {
            indexNodeProcessMapper.insertList(nodeProcessesList);
        }
        //初始化数据节点和常量节点
        IndexTaskResult result = indexEngine.getIndexTaskService().execute(indexEngine,nodeKey ,buildIndexTaskList(nodeProcessesList,indexEngine,indexDefinitions));
        if(result.getResultCode()==IndexTaskResult.INDEX_ERROR){//初始化数据失败
            indexInfoProcess.setIndexStatus(-1);
            indexInfoProcess.setIndexProcessData(result.getMsg());
            indexInfoProcessMapper.updateByPrimaryKeySelective(indexInfoProcess);
        }else if(result.getResultCode()==IndexTaskResult.INDEX_SUCCESS){
            indexInfoProcess.setIndexStatus(2);
        }
        return indexInfoProcess;
    }


//    @Override
//    public IndexTaskResult singleStepExecution(Integer nodeKey,IndexEngine indexEngine) {
//        IndexTaskResult result=indexEngine.getIndexTaskService().execute(nodeKey);
//        return result;
//    }



    /**
     * 构建节点任务
     */
    private Map<Integer,IndexTask>  buildIndexTaskList(List<IndexNodeProcess> indexNodeProcessesList,IndexEngine indexEngine,IndexDefinitions indexDefinitions) {
//        indexEngine.getIndexTaskService().clear();
        Map<Integer,IndexTask> keyTaskMap= new HashMap<Integer,IndexTask>();//所有的节点任务
        for (IndexNodeProcess indexNodeProcess : indexNodeProcessesList) {
            IndexTask indexTask = new IndexTaskImpl(indexEngine, indexNodeProcess,indexDefinitions);
//            indexEngine.getIndexTaskService().addIndexTask(indexTask);
            keyTaskMap.put(indexTask.getIndexNodeProcess().getNodeKey(),indexTask);
        }

        for(IndexTask task :keyTaskMap.values()){
            task.setKeyTaskMap(keyTaskMap);//每个任务设置下上下文
        }
        return keyTaskMap ;
    }
}
