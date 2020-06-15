package com.tf.base.go.service;

import com.tf.base.common.domain.IndexInfo;
import com.tf.base.go.define.IndexDefinitions;
import com.tf.base.go.engine.IndexEngine;

/**
 * 此服务提供对定义文件，部署文件，过程文件，输入参数的存取服务
 */
public interface IndexRepositoryService {

    /**
     * 获取流程定义
     * @return
     */
    IndexDefinitions getIndexDefinitions(IndexInfo indexInfo,String evalPath);

//    /**
//     * 构建初始定义信息
//     * @param indexInfo
//     */
//    void build(IndexInfo indexInfo,String evalPath);

}
