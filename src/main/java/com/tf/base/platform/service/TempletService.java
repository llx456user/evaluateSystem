package com.tf.base.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tf.base.common.domain.IndexCategory;
import com.tf.base.common.domain.TempletCategory;
import com.tf.base.common.domain.TempletInfo;
import com.tf.base.common.persistence.TempletCategoryMapper;
import com.tf.base.common.persistence.TempletInfoMapper;
import com.tf.base.platform.domain.TempletInfoParams;

@Service
public class TempletService {

	@Autowired
	private TempletCategoryMapper templetCategoryMapper;
	
	@Autowired
	private TempletInfoMapper templetInfoMapper;
	public int templetSave(TempletInfo templetInfo) {
		
//		return templetInfoMapper.templetSave(templetInfo);
		return templetInfoMapper.insertSelective(templetInfo);
	}
	public List<TempletCategory> categoryList() {
		TempletCategory t = new TempletCategory();
		t.setIsdelete(0);
		return templetCategoryMapper.select(t);
	}
	public int queryCount(TempletInfoParams params) {
		return templetInfoMapper.queryCount(params);
	}
	public List<TempletInfo> queryList(TempletInfoParams params, int start) {
		return templetInfoMapper.queryList(params,start);
	}
	public void categoryCreate(TempletCategory category){
		templetCategoryMapper.insert(category);
    }

    public void categoryUpdate(TempletCategory category){
    	TempletCategory updateObj = templetCategoryMapper.selectByPrimaryKey(category.getId());
        if(null != updateObj) {
            updateObj.setUpdateUid(category.getUpdateUid());
            updateObj.setUpdateTime(category.getUpdateTime());
            updateObj.setCategoryName(category.getCategoryName());
            templetCategoryMapper.updateByPrimaryKey(updateObj);
        }
    }

    public void categoryRemove(Integer categoryId){
    	templetCategoryMapper.deleteById(categoryId);
    	templetInfoMapper.deleteByCategoryId(categoryId);
    }
	public int templetUpdate(TempletInfo templetInfo) {
		
		return templetInfoMapper.updateByPrimaryKeySelective(templetInfo);
	}
	

}
