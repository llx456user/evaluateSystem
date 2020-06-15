package com.tf.base.platform.service;

import com.tf.base.common.domain.IndexModel;
import com.tf.base.common.persistence.IndexModelMapper;
import com.tf.base.go.type.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class IndexModelService {
    @Autowired
    private IndexModelMapper indexModelMapper ;

    /**
     * 获取文件类型
     * @param indexId
     * @return
     */
   public  List<IndexModel> getIndexFileParam(Integer indexId) {
        Example example = new Example(IndexModel.class);
        example.createCriteria().andEqualTo("indexId", indexId).
                andEqualTo("nodeCategory", Category.Datasource.getValue()).
                andEqualTo("nodeType","file");
       return  indexModelMapper.selectByExample(example);
    }

    /**
     * 获取常量类型
     * @param indexId
     * @return
     */
    public  List<IndexModel> getIndexConstantParam(Integer indexId){
        Example example = new Example(IndexModel.class);
        example.createCriteria().andEqualTo("indexId", indexId).
                andEqualTo("nodeCategory", Category.Constant.getValue());
        return  indexModelMapper.selectByExample(example);
    }

}
