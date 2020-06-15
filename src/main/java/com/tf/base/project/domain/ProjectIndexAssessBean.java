package com.tf.base.project.domain;

import com.tf.base.common.domain.ProjectIndexAssess;

public class ProjectIndexAssessBean extends ProjectIndexAssess {
    //设置编号
    private  String no ;
    //设置树的层级
    private  int treeLevel;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public int getTreeLevel() {
        return treeLevel;
    }

    public void setTreeLevel(int treeLevel) {
        this.treeLevel = treeLevel;
    }
}
