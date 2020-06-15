package com.tf.base.platform.service;

import com.tf.base.common.domain.StructDefine;
import com.tf.base.common.persistence.StructCategoryMapper;
import com.tf.base.common.persistence.StructDefineMapper;
import com.tf.base.common.utils.DateUtil;
import com.tf.base.platform.domain.ModelInfoQueryResult;
import com.tf.base.platform.domain.StructInfoParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StructDefineService {
    @Autowired
    private StructDefineMapper structDefineMapper;
    public int queryCount(StructInfoParams params) {

        return structDefineMapper.queryCount(params);
    }

    public List<StructDefine> queryList(StructInfoParams params, int start) {

        List<StructDefine> list= structDefineMapper.queryList(params, start);

        return list;
    }
}
