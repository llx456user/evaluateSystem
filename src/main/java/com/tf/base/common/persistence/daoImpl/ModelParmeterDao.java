package com.tf.base.common.persistence.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tf.base.common.domain.ModelParmeter;
import com.tf.base.common.persistence.ModelParmeterMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class ModelParmeterDao {

	@Autowired
	private ModelParmeterMapper modelParmeterMapper;

	/**
	 * 
	 * @param modelInfoId 模型id
	 * @param parmeterName 结构体名称
	 * @return
	 */
	public ModelParmeter getJgtByModelIdAndName(String modelInfoId,String parmeterName){
		Example example=new Example(ModelParmeter.class);
		example.createCriteria()
		.andEqualTo("modelId", modelInfoId)
		.andEqualTo("parmeterName", parmeterName)
		.andEqualTo("parmeterType", "struct");
		List<ModelParmeter> list=modelParmeterMapper.selectByExample(example);
		if(list!=null&&list.size()<0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 通过父id查询结构体包含参数列表
	 * @param jgtId
	 * @return
	 */
	public List<ModelParmeter> getSubParmeterList(String jgtId){
		Example example=new Example(ModelParmeter.class);
		example.createCriteria()
		.andEqualTo("parentId", jgtId);
		List<ModelParmeter> list=modelParmeterMapper.selectByExample(example);
		return list;
	}

}
