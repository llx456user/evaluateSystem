package com.tf.base.platform.service;


import com.tf.base.common.domain.WordCategory;
import com.tf.base.common.domain.WordTemplate;
import com.tf.base.common.persistence.WordCategoryMapper;
import com.tf.base.common.persistence.WordTemplateMapper;
import com.tf.base.common.service.BaseService;
import com.tf.base.common.utils.DateUtil;
import com.tf.base.platform.domain.WordTemplateParams;
import com.tf.base.platform.domain.WordTemplateQueryResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WordTemplateService {

    @Autowired
    private WordCategoryMapper wordCategoryMapper;

    @Autowired
    private WordTemplateMapper wordTemplateMapper;

    @Autowired
    private BaseService baseService;


    /**
     * 查询模板数量
     *
     * @param wordTemplateParams
     * @return
     */
    public int queryCount(WordTemplateParams wordTemplateParams) {
        return wordTemplateMapper.getCount(wordTemplateParams);
    }

    /**
     * 查询模板信息
     *
     * @param wordTemplateParams
     * @return
     */
    public List<WordTemplateQueryResult> queryList(WordTemplateParams wordTemplateParams, int start) {
        List<WordTemplateQueryResult> resultList = new ArrayList<>();
        List<WordTemplateQueryResult> list = wordTemplateMapper.selectList(wordTemplateParams, start);
        if (list != null) {
            for (WordTemplate wordTemplate : list) {
                WordTemplateQueryResult wordTemplateQueryResult = new WordTemplateQueryResult();
                BeanUtils.copyProperties(wordTemplate, wordTemplateQueryResult);
                wordTemplateQueryResult.setCreateTimeStr(DateUtil.TimeToString(wordTemplate.getCreateTime()));
                wordTemplateQueryResult.setUpdateTimeStr(DateUtil.TimeToString(wordTemplate.getUpdateTime()));
                resultList.add(wordTemplateQueryResult);
            }
        }
        return resultList;
    }


    /**
     * 查询模板分类
     * @return
     */
    public List<WordCategory> wordCategoryList() {
        WordCategory category = new WordCategory();
        category.setIsdelete(0);
        return wordCategoryMapper.select(category);
    }

    /**
     * 新增模板分类
     * @param category
     */
    public void wordCategoryCreate(WordCategory category) {
        wordCategoryMapper.insert(category);
    }

    /**
     * 删除模板分类
     * @param categoryId
     */
    public void wordCategoryRemove(Integer categoryId) {
        wordCategoryMapper.deleteById(categoryId);

        wordTemplateMapper.deleteByCategoryId(categoryId);
    }

    /**
     * 编辑模板分类
     * @param category
     */
    public void wordCategoryUpdate(WordCategory category) {
        WordCategory updateObj = wordCategoryMapper.selectByPrimaryKey(category.getId());
        if (null != updateObj) {
            updateObj.setUpdateUid(category.getUpdateUid());
            updateObj.setUpdateTime(category.getUpdateTime());
            updateObj.setName(category.getName());
            wordCategoryMapper.updateByPrimaryKey(updateObj);
        }
    }

    /**
     * 删除表格模板
     * @param id
     * @return
     */
    public int deleteWordIndex(String id){
        Integer pId = Integer.valueOf(id);
        Integer userid = Integer.parseInt(baseService.getUserId());
        WordTemplate bean = wordTemplateMapper.selectByPrimaryKey(pId);
        bean.setIsdelete(1);
        bean.setUpdateUid(userid);
        bean.setUpdateTime(new Date());
        return  wordTemplateMapper.updateByPrimaryKeySelective(bean);
    }

    /**
     * 保存模板
     * @param df
     * @param cover
     * @return
     */
    public int wordFileSave(WordTemplate df, String cover) {
        Integer userid = Integer.parseInt(baseService.getUserId());
        Date now = new Date();
        try {
            //新增
            if(df.getId() == null || "".equals(df.getId().toString())){
                WordTemplate param = new WordTemplate();
                param.setFileName(df.getFileName());
                param.setCategoryId(df.getCategoryId());
                param.setIsdelete(0);
                if(cover==null || !"1".equals(cover)){
                    int i = wordTemplateMapper.selectCount(param);
                    if(i > 0){
                        return -1;
                    }
                }else{
                    WordTemplate tmp = wordTemplateMapper.selectOne(param);
                    wordTemplateMapper.delete(param);
                }
                param.setFileName(df.getFileName());
                df.setCategoryId(df.getCategoryId());
                df.setCreateTime(now);
                df.setFilePath(df.getFilePath());
                df.setUpdateTime(now);
                df.setUpdateUid(userid);
                df.setCreateUid(userid);
                df.setIsdelete(0);
                wordTemplateMapper.insertSelective(df);

                return 1;
            }else{//修改
                df.setUpdateTime(now);
                df.setUpdateUid(userid);
                df.setFilePath(df.getFilePath());
                wordTemplateMapper.updateByPrimaryKeySelective(df);

                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 查找数据
     * @param id
     * @return
     */
    public WordTemplate findById(String id) {
        WordTemplate df = wordTemplateMapper.selectByPrimaryKey(id);
        return df;
    }
}
