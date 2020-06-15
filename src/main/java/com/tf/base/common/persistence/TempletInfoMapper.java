package com.tf.base.common.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tf.base.common.domain.TempletInfo;
import com.tf.base.platform.domain.TempletInfoParams;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface TempletInfoMapper extends MySqlMapper<TempletInfo>, Mapper<TempletInfo> {

	int templetSave(@Param("params")TempletInfo templetInfo);

	int queryCount(@Param("params")TempletInfoParams params);

	List<TempletInfo> queryList(@Param("params")TempletInfoParams params, @Param("start")int start);

	int deleteByCategoryId(Integer categoryId);

	public  void insertData(@Param("indexList") String indexList,@Param("tplName") String tplName);
	public  List<TempletInfo> selectTemplet(@Param("tplName") String tplName);
	public  List<TempletInfo> updateTemplet(@Param("tplName") String tplName,@Param("index") String index);
}