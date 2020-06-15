package com.tf.base.common.persistence;

import com.tf.base.common.domain.IndexInfo;
import com.tf.base.platform.domain.ModelInfoParams;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

public interface IndexInfoMapper extends MySqlMapper<IndexInfo>, Mapper<IndexInfo> {

    /**
     * 判断指标是否存在
     * @param map
     * @return
     */
    int countExistIndexName(Map<String, Object> map);

    int getCount(@Param("params")ModelInfoParams params);

    List<IndexInfo> selectList(@Param("params")ModelInfoParams params, @Param("start")int start);

    void deleteByCategoryId(Integer categoryId);

    public  List<IndexInfo> selectIndexName(@Param("map") Map map);
    public  List<IndexInfo> selectIndexList(@Param("indexName") String indexName);
    public  List<IndexInfo> updateIndexContent(@Param("indexName") String indexName,@Param("indexContent") String indexContent);
    public  IndexInfo selectIndexNamebykey(String indexid);


}