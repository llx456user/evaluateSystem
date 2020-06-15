package com.tf.base.platform.domain;

import com.tf.base.common.domain.IndexModel;

public class IndexModelQueryResult extends IndexModel {
    //更新时间
    private String updateTimeStr;
    //测试状态
    private String indexStatusStr;

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public String getIndexStatusStr() {
        return indexStatusStr;
    }

    public void setIndexStatusStr(String indexStatusStr) {
        this.indexStatusStr = indexStatusStr;
    }
}
