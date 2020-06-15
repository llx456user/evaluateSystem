package com.tf.base.go.define;

import com.tf.base.common.domain.IndexInfo;
import com.tf.base.go.define.Impl.IndexDefinitionsImpl;

public class DeploymentIndex {
    private IndexInfo indexInfo;

    public DeploymentIndex(IndexInfo indexInfo) {
        this.indexInfo = indexInfo;
    }

    /**
     * 获取指标定义
     *
     * @return
     */
    public static IndexDefinitions getIndexDefinition(IndexInfo indexInfo,String evalPath ) {
        IndexDefinitionsImpl indexDefinitions = new IndexDefinitionsImpl(indexInfo,evalPath);
        return indexDefinitions;
    }


}
