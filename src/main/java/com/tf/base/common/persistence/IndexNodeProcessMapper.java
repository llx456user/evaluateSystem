package com.tf.base.common.persistence;

import com.tf.base.common.domain.IndexNodeProcess;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

public interface IndexNodeProcessMapper extends Mapper<IndexNodeProcess>, MySqlMapper<IndexNodeProcess> {
    List<IndexNodeProcess> getNodeProcess(Map map);

    List<IndexNodeProcess> getNodeProcessByBusinessId(Map map);

}