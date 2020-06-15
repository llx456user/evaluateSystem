package com.tf.base.go.service.Impl;

import com.tf.base.common.domain.IndexInfo;
import com.tf.base.go.define.DeploymentIndex;
import com.tf.base.go.define.IndexDefinitions;
import com.tf.base.go.engine.IndexEngine;
import com.tf.base.go.service.IndexRepositoryService;
import org.springframework.stereotype.Service;

@Service("indexRepositoryService")
public class IndexRepositoryServiceImpl implements IndexRepositoryService {

//    IndexDefinitions indexDefinitions = null;

//    @Override
//    public  void  build(IndexInfo indexInfo,String evalPath){
//        this.indexDefinitions = DeploymentIndex.getIndexDefinition(indexInfo,evalPath);
//    }


    @Override
    public IndexDefinitions getIndexDefinitions(IndexInfo indexInfo,String evalPath) {
        return DeploymentIndex.getIndexDefinition(indexInfo,evalPath);
    }
}
