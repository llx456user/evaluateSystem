package com.tf.base.platform.domain;

import com.tf.base.common.domain.DatasourceDb;
import com.tf.base.common.domain.DatasourceSql;

import java.util.List;

public class IndexDbInfoParams {

    private DatasourceDb datasourceDb;

    private List<DatasourceSql> datasourceSqlList;

    public DatasourceDb getDatasourceDb() {
        return datasourceDb;
    }

    public void setDatasourceDb(DatasourceDb datasourceDb) {
        this.datasourceDb = datasourceDb;
    }

    public List<DatasourceSql> getDatasourceSqlList() {
        return datasourceSqlList;
    }

    public void setDatasourceSqlList(List<DatasourceSql> datasourceSqlList) {
        this.datasourceSqlList = datasourceSqlList;
    }
}
