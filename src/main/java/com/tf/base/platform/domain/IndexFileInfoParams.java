package com.tf.base.platform.domain;

import com.tf.base.common.domain.DatasourceCategory;
import com.tf.base.common.domain.DatasourceFile;

import java.util.List;

public class IndexFileInfoParams {
    DatasourceCategory datasourceCategory ;
    List<DatasourceFile> datasourceFileList ;

    public DatasourceCategory getDatasourceCategory() {
        return datasourceCategory;
    }

    public void setDatasourceCategory(DatasourceCategory datasourceCategory) {
        this.datasourceCategory = datasourceCategory;
    }

    public List<DatasourceFile> getDatasourceFileList() {
        return datasourceFileList;
    }

    public void setDatasourceFileList(List<DatasourceFile> datasourceFileList) {
        this.datasourceFileList = datasourceFileList;
    }
}
