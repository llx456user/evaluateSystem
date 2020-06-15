package com.tf.base.platform.domain;

import com.tf.base.common.domain.ModelCategory;
import com.tf.base.common.domain.ModelInfo;

import java.util.List;

public class ModelCategoryParam {
    private ModelCategory modelCategory ;

    private List<ModelInfo> modelInfoList ;

    public ModelCategory getModelCategory() {
        return modelCategory;
    }

    public void setModelCategory(ModelCategory modelCategory) {
        this.modelCategory = modelCategory;
    }

    public List<ModelInfo> getModelInfoList() {
        return modelInfoList;
    }

    public void setModelInfoList(List<ModelInfo> modelInfoList) {
        this.modelInfoList = modelInfoList;
    }
}
