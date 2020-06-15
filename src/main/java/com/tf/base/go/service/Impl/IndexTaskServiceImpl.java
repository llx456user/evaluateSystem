package com.tf.base.go.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.tf.base.common.domain.*;
import com.tf.base.common.persistence.DatasourceDbMapper;
import com.tf.base.common.persistence.IndexNodeProcessMapper;
import com.tf.base.common.persistence.ModelInfoMapper;
import com.tf.base.datasource.service.DatasourceSqlService;
import com.tf.base.datasource.service.FileService;
import com.tf.base.go.define.IndexDefinitions;
import com.tf.base.go.define.IndexNode;
import com.tf.base.go.define.NodeLink;
import com.tf.base.go.engine.IndexEngine;
import com.tf.base.go.service.IndexTask;
import com.tf.base.go.service.IndexTaskResult;
import com.tf.base.go.service.IndexTaskService;
import com.tf.base.go.type.Category;
import com.tf.base.go.type.NodeStatus;
import com.tf.base.go.type.NodeType;
import com.tf.base.go.type.SignType;
import com.tf.base.go.util.InparamterUtil;
import com.tf.base.util.ParamFileImpl;
import com.tf.base.util.ParamProcess;
import com.tf.base.util.ParamSqlImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service("indexTaskService")
public class IndexTaskServiceImpl implements IndexTaskService {
    @Autowired
    FileService fileService;
    @Autowired
    DatasourceSqlService datasourceSqlService;
    @Autowired
    private DatasourceDbMapper datasourceDbMapper;
    @Autowired
    private IndexNodeProcessMapper indexNodeProcessMapper;
    @Autowired
    private ModelInfoMapper modelInfoMapper;
    @Autowired
    private ModelInputService modelInputService;

    private IndexTask currentIndexTask = null;
//    private  Map<Integer,IndexTask> keyTaskMap= new ConcurrentHashMap<Integer,IndexTask>();//所有的节点任务
//    private  Map<Integer,IndexTask> modelTaskMap = new ConcurrentHashMap<>();//模型任务
//    private  Map<Integer,IndexTask> outTaskMap = new ConcurrentHashMap<>();//输出任务

//    @Override
//    public Collection<IndexTask> getIndexTasks() {
//        return  this.keyTaskMap.values();
//    }

    private static Logger log = LoggerFactory.getLogger(IndexTaskServiceImpl.class);

    @Override
    public IndexNodeProcess getIndexNodeProcess(Integer id) {
        return indexNodeProcessMapper.selectByPrimaryKey(id);
    }

    @Override
    public IndexTask getCurrentTask() {
        return this.currentIndexTask;
    }

    @Override
    public IndexTaskResult execute(IndexEngine indexEngine, Integer taskKey, Map<Integer, IndexTask> keyTaskMap) {
        //初始化数据
        IndexTaskResult indexResult = doExecuteInitData(indexEngine, keyTaskMap);
        //1、执行初始化数据
        if (indexResult.getResultCode() == IndexTaskResult.INDEX_ERROR) {
            return indexResult;
        }
        IndexTask indexTask = keyTaskMap.get(taskKey);
        IndexTaskResult result = doExecuteModelTask(indexTask, keyTaskMap);
        return result;
    }


    @Override
    public IndexTaskResult batchExecute(IndexEngine indexEngine, JSONObject param, Map<Integer, IndexTask> keyTaskMap,IndexDefinitions indexDefinitions) {
        /**
         * 初始化数据
         */
        IndexTaskResult indexResult = doExecuteInitDataAndConstant(indexEngine, param, keyTaskMap,indexDefinitions);
        //1、执行初始化数据
        if (indexResult.getResultCode() == IndexTaskResult.INDEX_ERROR) {
            return indexResult;
        }
        indexResult = doExecuteModelTask(null, keyTaskMap);//执行model数据节点
        if (indexResult.getResultCode() == IndexTaskResult.INDEX_ERROR) {
            return indexResult;
        }

        doExecuteChildTask(indexEngine,keyTaskMap,indexDefinitions);

        indexResult = doExecuteOutTask(indexEngine,keyTaskMap,indexDefinitions);
        if (indexResult.getResultCode() == IndexTaskResult.INDEX_ERROR) {
            return indexResult;
        }
        return indexResult;
    }



    @Override
    public IndexTaskResult doExecuteInitData(IndexEngine indexEngine, Map<Integer, IndexTask> keyTaskMap) {
        IndexTaskResult result = new IndexTaskResult();
        Collection<IndexTask> indexTasks = new ArrayList<>();
        Map<Integer, IndexTask> modelTaskMap = new HashMap<>();//模型任务
        Map<Integer, IndexTask> outTaskMap = new HashMap<>();//输出任务

        try {
            for (IndexTask task : keyTaskMap.values()) {
                String outString = "";
                if (task.getNode().getNodeCategory() == Category.Datasource) {
                    //首先判断数据节点是否已经有数据输入
                    String paramByKey = getParamByKey(indexEngine, String.valueOf(task.getIndexNodeProcess().getNodeKey()),task.getIndexNodeProcess().getIndexProcessId());
                    if (paramByKey != null) {
                        outString = getDataByDataSouce(Integer.valueOf(paramByKey), task.getNode().getNodeType());
                    } else {//获取模型里面节点的数据,
                        outString = getDataByDataSouce(task.getNode().getModelId(), task.getNode().getNodeType());
                    }
                    task.getIndexNodeProcess().setNodeOutputParamExp(outString);
                    task.getIndexNodeProcess().setNodeStatus(1);
                    indexTasks.add(task);
                } else if (task.getNode().getNodeCategory() == Category.Constant) {//
                    outString = task.getNode().getDefaultValue();//获取默认值
                    task.getIndexNodeProcess().setNodeOutputParamExp(outString);
                    task.getIndexNodeProcess().setNodeStatus(1);
                    indexTasks.add(task);
                } else if (task.getNode().getNodeCategory() == Category.Model || task.getNode().getNodeCategory() == Category.If) { //执行任务
                    modelTaskMap.put(task.getNode().getKey(), task);
                } else if (task.getNode().getNodeCategory() == Category.Grid || task.getNode().getNodeCategory() == Category.Picture||task.getNode().getNodeCategory() == Category.Child) {//输出任务
                    outTaskMap.put(task.getNode().getKey(), task);
                }
            }
        } catch (Exception e) {
            result.setResultCode(IndexTaskResult.INDEX_ERROR);
            result.setMsg("获取数据错误");
            return result;
        }
        if (indexTasks.size() > 0) {
            for (IndexTask indexTask : indexTasks) {
                indexNodeProcessMapper.updateByPrimaryKeySelective(indexTask.getIndexNodeProcess());
//                updateCachTask(indexTask, indexTask.getNode().getKey());//刷新缓存
            }
        }

        result.setModelTaskMap(modelTaskMap);
        result.setOutTaskMap(outTaskMap);
        result.setResultCode(IndexTaskResult.INDEX_SUCCESS);
        result.setMsg("数据初始化成功");
        return result;
    }


    @Override
    public IndexTaskResult doExecuteInitDataAndConstant(IndexEngine indexEngine, JSONObject dataObject, Map<Integer, IndexTask> keyTaskMap, IndexDefinitions indexDefinitions) {
        IndexTaskResult result = new IndexTaskResult();
        Collection<IndexTask> indexTasks = new ArrayList<>();
//        Map<Integer,IndexTask> modelTaskMap = new HashMap<>();//模型任务
//        Map<Integer,IndexTask> outTaskMap = new HashMap<>();//输出任务
        try {
            for (IndexTask task : keyTaskMap.values()) {
                String outString = "";
                IndexNode indexNode =  task.getNode();
                if (task.getNode().getNodeCategory() == Category.Datasource) {
                    ParamProcess paramProcess = null;
                    if (task.getNode().getNodeType() == NodeType.File) {//
                        String paramByKey = String.valueOf(task.getIndexNodeProcess().getNodeKey());
                        String filePath = dataObject.getString(paramByKey);
                        if (filePath != null) {
                            paramProcess = new ParamFileImpl(filePath);
                        }

                    } else if (task.getNode().getNodeType() == NodeType.Sql) {
//                        String paramByKey = getParamByKey(indexEngine,String.valueOf(task.getIndexNodeProcess().getNodeKey()));
                        int sqlid = indexDefinitions.getNodeByKey(task.getIndexNodeProcess().getNodeKey()).getModelId();
                        DatasourceSql sql = datasourceSqlService.queryObjectByKey(sqlid);
                        DatasourceDb db = datasourceDbMapper.selectByPrimaryKey(sql.getDatasourceId());
                        paramProcess = new ParamSqlImpl(db, sql);
                    }
                    outString = paramProcess.getDataJSONObject().toJSONString();
                    task.getIndexNodeProcess().setNodeOutputParamExp(outString);
                    task.getIndexNodeProcess().setNodeStatus(1);
                    indexTasks.add(task);
                } else if (task.getNode().getNodeCategory() == Category.Constant) {//
                    String paramByKey = String.valueOf(task.getIndexNodeProcess().getNodeKey());
                    outString = dataObject.getString(paramByKey);
                    task.getIndexNodeProcess().setNodeOutputParamExp(outString);
                    task.getIndexNodeProcess().setNodeStatus(1);
                    indexTasks.add(task);
                }
//                else if(task.getNode().getNodeCategory()==Category.Model||task.getNode().getNodeCategory()==Category.If){ //执行任务
//                    modelTaskMap.put(task.getNode().getKey(),task);
//                }else if(task.getNode().getNodeCategory()==Category.Grid||task.getNode().getNodeCategory()==Category.Picture||task.getNode().getNodeCategory()==Category.End||task.getNode().getNodeCategory()==Category.Out){//输出任务
//                    outTaskMap.put(task.getNode().getKey(),task);
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setResultCode(IndexTaskResult.INDEX_ERROR);
            result.setMsg("获取数据错误");
            return result;
        }
        if (indexTasks.size() > 0) {
            for (IndexTask indexTask : indexTasks) {
                indexNodeProcessMapper.updateByPrimaryKeySelective(indexTask.getIndexNodeProcess());
//                updateCachTask(indexTask, indexTask.getNode().getKey());//刷新缓存
            }
        }

//        result.setModelTaskMap(modelTaskMap);
//        result.setOutTaskMap(outTaskMap);
        result.setResultCode(IndexTaskResult.INDEX_SUCCESS);
        result.setMsg("数据初始化成功");
        return result;
    }

//    private void updateCachTask(IndexTask indexTask, Integer key) {
//        keyTaskMap.put(key, indexTask);//更新缓存
//    }

    /**
     * 批量执行信息
     *
     * @return
     */
    private IndexTaskResult doExecuteModelTask(IndexTask endIndexTask, Map<Integer, IndexTask> keyTaskMap) {

        Map<Integer, IndexTask> modelTaskMap = new HashMap<>(); //进行模型任务过滤
        for (IndexTask task : keyTaskMap.values()) {
            if (task.getNode().getNodeCategory() == Category.Model || task.getNode().getNodeCategory() == Category.If) { //执行任务
                modelTaskMap.put(task.getNode().getKey(), task);
            }
        }

        IndexTaskResult result = new IndexTaskResult();
        int count = modelTaskMap.keySet().size();
        for (int i = 0; i < count; i++) {
            IndexTask indexTask = getCanExecuteTask(modelTaskMap);
            // 进行一次缓存处理.
            if (indexTask.getNode().getNodeCategory() == Category.If) {//如果是IF节点
                JSONObject outJson = InparamterUtil.getIfOutParam(indexTask);
                //获取input条件值
                if (isIFYes(indexTask)) {//如果条件是真
                    outJson.put("if", "yes");
                } else {
                    outJson.put("if", "no");
                }
                indexTask.getIndexNodeProcess().setNodeStatus(1);
                indexTask.getIndexNodeProcess().setNodeOutputParamExp(outJson.toJSONString());//输出
                indexNodeProcessMapper.updateByPrimaryKeySelective(indexTask.getIndexNodeProcess());
//                updateCachTask(indexTask, indexTask.getNode().getKey());//刷新缓存
                continue; //执行下次
            }
            if (isContainNotExe(indexTask)) {//直接不执行
                indexTask.getIndexNodeProcess().setNodeStatus(3);
                indexTask.getIndexNodeProcess().setNodeOutputParamExp("收IF调减影响，此节点不执行");//输出
                indexNodeProcessMapper.updateByPrimaryKeySelective(indexTask.getIndexNodeProcess());
                continue; //执行下次
            }

            ModelInfo modelInfo = modelInfoMapper.selectByPrimaryKey(indexTask.getNode().getModelId());
            Map<String, String> input = modelInputService.getModelInputParamter(indexTask);
            if (InparamterUtil.error.equals(input.get(InparamterUtil.code))) {
                result.setResultCode(IndexTaskResult.INDEX_ERROR);
                result.setMsg(input.get(InparamterUtil.msg));
                return result;
            }
            //成功则继续
            String inputSting = input.get(InparamterUtil.msg);
            indexTask.getIndexNodeProcess().setNodeInputParamExp(inputSting);
            result = indexTask.execute(inputSting, modelInfo.getDllPath());
            indexTask.getIndexNodeProcess().setNodeOutputParamExp(result.getMsg());
            if (result.getResultCode() == IndexTaskResult.INDEX_ERROR) {
                //如果失败，则记录失败信息并返回
                indexTask.getIndexNodeProcess().setNodeStatus(2);
                indexNodeProcessMapper.updateByPrimaryKeySelective(indexTask.getIndexNodeProcess());
//                updateCachTask(indexTask,indexTask.getNode().getKey());
                break;
            } else {
                indexTask.getIndexNodeProcess().setNodeStatus(1);
                indexNodeProcessMapper.updateByPrimaryKeySelective(indexTask.getIndexNodeProcess());
//                updateCachTask(indexTask, indexTask.getNode().getKey());//刷新缓存
                modelTaskMap.remove(indexTask.getNode().getKey());//执行一个进行清除
                if (endIndexTask != null) {//则为调试结束节点
                    if (endIndexTask.getNode().getKey().intValue() == indexTask.getNode().getKey().intValue()) {
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 处理子节点
     * @param indexEngine
     * @param keyTaskMap
     * @param indexDefinitions
     */
    private void doExecuteChildTask(IndexEngine indexEngine, Map<Integer, IndexTask> keyTaskMap, IndexDefinitions indexDefinitions) {
        Map<Integer, IndexTask> childTaskMap = new HashMap<>(); //进行输出节点过滤
        for (IndexTask task1 : keyTaskMap.values()) {
            if (task1.getNode().getNodeCategory() == Category.Child) {//子节点任务
                childTaskMap.put(task1.getNode().getKey(), task1);
            }
        }

        for(IndexTask childTask : childTaskMap.values()){//进行复制
            String[] inAndOut = modelInputService.getChildInputParamter(childTask);
            childTask.getIndexNodeProcess().setNodeInputParamExp(inAndOut[0]);
            childTask.getIndexNodeProcess().setNodeOutputParamExp(inAndOut[1]);
            childTask.getIndexNodeProcess().setNodeStatus(1);
            indexNodeProcessMapper.updateByPrimaryKeySelective(childTask.getIndexNodeProcess());
        }

    }

    /**
     * 判断是否直接不执行
     *
     * @param indexTask
     * @return
     */
    private boolean isContainNotExe(IndexTask indexTask) {
        for (IndexTask task : indexTask.getPreIndexTask()) {
            if (task.getNode().getNodeCategory() == Category.Model) {
                if (task.getIndexNodeProcess().getNodeStatus() == 3) {
                    return true;
                }
            } else if (task.getNode().getNodeCategory() == Category.If) {//如果前置节点是IF节点
                JSONObject jsonObject = JSONObject.parseObject(task.getIndexNodeProcess().getNodeOutputParamExp());
                String yesOrNo = jsonObject.getString("if");
                for (NodeLink link : indexTask.getNode().getInNodeLinks()) {
                    if (link.getFromNode().getNodeCategory() == Category.If) {//
                        if (!yesOrNo.equals(link.getText())) {//
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isIFYes(IndexTask indexTask) {
        double inputValue = InparamterUtil.getIfInputParam(indexTask);
        double conditionValue = Double.valueOf(indexTask.getNode().getSettingNum());
        if (indexTask.getNode().getSignType() == SignType.LessThan) {
            if (inputValue < conditionValue) {
                return true;
            }
        } else if (indexTask.getNode().getSignType() == SignType.LessThanOrEqual) {
            if (inputValue <= conditionValue) {
                return true;
            }
        } else if (indexTask.getNode().getSignType() == SignType.Equal) {//等于
            if (inputValue == conditionValue) {
                return true;
            }
        } else if (indexTask.getNode().getSignType() == SignType.GreaterOrEqual) {
            if (inputValue > conditionValue) {
                return true;
            }
        } else if (indexTask.getNode().getSignType() == SignType.MoreThan) {
            if (inputValue >= conditionValue) {
                return true;
            }
        }
        return false;
    }



    /**
     * 处理输出任务
     */
    private IndexTaskResult doExecuteOutTask(IndexEngine indexEngine,Map<Integer, IndexTask> keyTaskMap,IndexDefinitions indexDefinitions) {

        Map<Integer, IndexTask> outTaskMap = new HashMap<>(); //进行输出节点过滤
        for (IndexTask task1 : keyTaskMap.values()) {
            if (task1.getNode().getNodeCategory() == Category.Grid || task1.getNode().getNodeCategory() == Category.Picture || task1.getNode().getNodeCategory() == Category.End || task1.getNode().getNodeCategory() == Category.Out) {//输出任务
                outTaskMap.put(task1.getNode().getKey(), task1);
            }
        }
        IndexTaskResult result = new IndexTaskResult();
        String outMsg = "";
        try {
            for (IndexTask task : outTaskMap.values()) {
                String input = "";
                if (task.getNode().getNodeCategory() == Category.End || task.getNode().getNodeCategory() == Category.Out) {
                    input = modelInputService.getEndNodeValue(task);
                    outMsg = input;
                } else {
                    //获取输入参数
                    input = modelInputService.getOutNodeInputParamter(task).toJSONString();
                }
                task.getIndexNodeProcess().setNodeInputParamExp(input);
                task.getIndexNodeProcess().setNodeStatus(1);
                IndexNodeProcess indexNodeProcess = task.getIndexNodeProcess();
                //保存数据库
                indexNodeProcessMapper.updateByPrimaryKeySelective(indexNodeProcess);
                if( task.getNode().getNodeCategory() == Category.Picture ){//表示是图形，进行处理输出处理
                    IndexInfoProcess indexInfoProcess = indexEngine.getIndexRuntimeService().getIndexInfoProcess(task.getIndexNodeProcess().getIndexProcessId());
                    //写入本地文件
                    modelInputService.writeJsFile(input,task,indexInfoProcess,indexDefinitions);
                }
            }
            result.setResultCode(IndexTaskResult.INDEX_SUCCESS);
            result.setMsg(outMsg);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setResultCode(IndexTaskResult.INDEX_ERROR);
            result.setMsg("输出格式错误");
        }
        return result;
    }


    /**
     * 获取指标实例输入参数
     *
     * @param key
     *@param indexInfoId
     * @return
     */
    private String getParamByKey(IndexEngine indexEngine, String key,Integer indexInfoId ) {
        IndexInfoProcess indexInfoProcess = indexEngine.getIndexRuntimeService().getIndexInfoProcess(indexInfoId);
        String param = indexInfoProcess.getIndexProcessParam();
        if (StringUtils.isNotEmpty(param)) {
            JSONObject paramObject = JSONObject.parseObject(param);
            if (paramObject.containsKey(key)) {
                return paramObject.getString(key);
            }
        }
        return null;
    }

    /**
     * 根据数据源获取参数
     *
     * @param id
     * @param type
     * @return
     */
    private String getDataByDataSouce(Integer id, NodeType type) {
        ParamProcess paramProcess = null;
        if (type == NodeType.File) {//
            DatasourceFile file = fileService.findById(String.valueOf(id));
            paramProcess = new ParamFileImpl(file.getFilePath());
        } else if (type == NodeType.Sql) {
            DatasourceSql sql = datasourceSqlService.queryObjectByKey(id);
            DatasourceDb db = datasourceDbMapper.selectByPrimaryKey(sql.getDatasourceId());
            paramProcess = new ParamSqlImpl(db, sql);
        }
        String data = null;
        if (paramProcess != null) {
            data = paramProcess.getDataJSONObject().toJSONString();
        }
        return data;
    }

    /**
     * 获取可执行的任务
     *
     * @return
     */
    private IndexTask getCanExecuteTask(Map<Integer, IndexTask> modelTaskMap) {
        IndexTask reIndex = null;
        for (IndexTask task : modelTaskMap.values()) {
            if (isCanExecute(task)) {
                reIndex = task;
                break;
            }
        }
        return reIndex;
    }

    /**
     * 判断任务是否可以执行
     *
     * @param indexTask
     * @return
     */
    private boolean isCanExecute(IndexTask indexTask) {
        NodeStatus status = NodeStatus.getStatus(indexTask.getIndexNodeProcess().getNodeStatus());
        if (status == NodeStatus.Initialization) {//节点未执行


            List<IndexTask> preIndexTasks = indexTask.getPreIndexTask();
            //所有的前置任务节点都已经执行完成，那么此节点认为可以执行
            for (IndexTask task : preIndexTasks) {
                NodeStatus nodeStatus = NodeStatus.getStatus(task.getIndexNodeProcess().getNodeStatus());
                if (nodeStatus == NodeStatus.Fail || nodeStatus == NodeStatus.Initialization) {
                    return false;
                }

//                //如果前置任务节点类型
//                if(task.getNode().getNodeCategory()==Category.If){//如果前置节点是IF节点，则判断IF的前置节点是否都已经完成
//                   if(!ifNodeCanExe(task)){
//                       return  false ;
//                   }
//                }else if(task.getNode().getNodeCategory()==Category.Model){
//                    NodeStatus nodeStatus = NodeStatus.getStatus(task.getIndexNodeProcess().getNodeStatus());
//                    if(nodeStatus ==NodeStatus.Faile||nodeStatus==NodeStatus.Initialization){
//                        return false ;
//                    }
//                }
            }
            return true;
        } else if (status == NodeStatus.Fail) {//表示已经执行一次，可以执行
            return true;
        } else if (status == NodeStatus.Success) {
            return true;
        }
        return false;
    }

    private boolean ifNodeCanExe(IndexTask indexTask) {
        for (IndexTask ifPreTask : indexTask.getPreIndexTask()) {
            if (ifPreTask.getNode().getNodeCategory() == Category.If) {
                if (!ifNodeCanExe(ifPreTask)) {
                    return false;
                }
            } else if (ifPreTask.getNode().getNodeCategory() == Category.Constant) {

            } else if (ifPreTask.getNode().getNodeCategory() == Category.Model) {
                NodeStatus nodeStatus = NodeStatus.getStatus(ifPreTask.getIndexNodeProcess().getNodeStatus());
                if (nodeStatus == NodeStatus.Fail || nodeStatus == NodeStatus.Initialization) {
                    return false;
                }
            }
        }
        return true;
    }

}
