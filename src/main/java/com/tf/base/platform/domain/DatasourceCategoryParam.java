package com.tf.base.platform.domain;

import com.tf.base.common.domain.DataFile;
import com.tf.base.common.domain.DatasourceCategory;

import java.util.List;

public class DatasourceCategoryParam   {
    //分类包含分类
    private List<DatasourceCategoryParam> datasourceCategoryParamList ;
    //文件列表
    private  List<DataFile> dataFileList ;
    //文件分类
    private  DatasourceCategory  datasourceCategory ;

    public List<DatasourceCategoryParam> getDatasourceCategoryParamList() {
        return datasourceCategoryParamList;
    }

    public void setDatasourceCategoryParamList(List<DatasourceCategoryParam> datasourceCategoryParamList) {
        this.datasourceCategoryParamList = datasourceCategoryParamList;
    }

    public List<DataFile> getDataFileList() {
        return dataFileList;
    }

    public void setDataFileList(List<DataFile> dataFileList) {
        this.dataFileList = dataFileList;
    }

    public DatasourceCategory getDatasourceCategory() {
        return datasourceCategory;
    }

    public void setDatasourceCategory(DatasourceCategory datasourceCategory) {
        this.datasourceCategory = datasourceCategory;
    }
}
