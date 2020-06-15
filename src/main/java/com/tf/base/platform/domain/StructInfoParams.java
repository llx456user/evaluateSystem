package com.tf.base.platform.domain;

import com.tf.base.common.domain.Pager;

public class StructInfoParams extends Pager {

    private String structName;
    private String structRemark;
    private String categoryId;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getStructName() {
        return structName;
    }

    public void setStructName(String structName) {
        this.structName = structName;
    }

    public String getStructRemark() {
        return structRemark;
    }

    public void setStructRemark(String structRemark) {
        this.structRemark = structRemark;
    }

}
