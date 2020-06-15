package com.tf.base.go.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IndexTaskResult {
    public final static int INDEX_SUCCESS =1;
    public final static int INDEX_ERROR =0 ;
    //返回码1表示正确返回，0表示错误返回
    private int resultCode;
    //正确返回为
    private  String msg ;

    private Map<Integer,IndexTask> modelTaskMap ;//模型任务
    private  Map<Integer,IndexTask> outTaskMap  ;//输出任务

    public Map<Integer, IndexTask> getModelTaskMap() {
        return modelTaskMap;
    }

    public void setModelTaskMap(Map<Integer, IndexTask> modelTaskMap) {
        this.modelTaskMap = modelTaskMap;
    }

    public Map<Integer, IndexTask> getOutTaskMap() {
        return outTaskMap;
    }

    public void setOutTaskMap(Map<Integer, IndexTask> outTaskMap) {
        this.outTaskMap = outTaskMap;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
