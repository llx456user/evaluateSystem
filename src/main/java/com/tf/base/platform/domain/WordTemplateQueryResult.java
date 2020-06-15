package com.tf.base.platform.domain;

import com.tf.base.common.domain.WordTemplate;

public class WordTemplateQueryResult extends WordTemplate {
    // 上传时间
    private String createTimeStr;

    private String updateTimeStr;

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }
}
