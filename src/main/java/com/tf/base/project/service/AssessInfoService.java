package com.tf.base.project.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tf.base.common.domain.AssessInfo;
import com.tf.base.common.persistence.AssessInfoMapper;
import com.tf.base.common.service.BaseService;
import com.tf.base.common.utils.DateUtil;
import com.tf.base.project.domain.AssessInfoParams;

/**
 * 评估结果信息service
 *
 */
@Service
public class AssessInfoService {

	@Autowired
	private AssessInfoMapper assessInfoMapper;
	@Autowired
	private BaseService baseService;
	
	public int queryCount(AssessInfoParams params) {

		return assessInfoMapper.queryCount(params);
	}

	public List<AssessInfo> queryList(AssessInfoParams params, int start) {

		List<AssessInfo> list = assessInfoMapper.queryList(params, start);
		if (list != null) {
			for (AssessInfo r : list) {
				r.setUpdateTimeStr(r.getUpdateTime());
			}
		}
		return list;
	}
	
	/**
	 * 新增评估信息
	 * @param bean
	 * @return
	 */
	public int save(AssessInfo bean){
		Integer userid = Integer.parseInt(baseService.getUserId());
		Date now = new Date();
		
		bean.setCreateTime(now);
		bean.setCreateUid(userid);
		bean.setIsdelete(0);
		bean.setUpdateTime(now);
		bean.setUpdateUid(userid);
		return assessInfoMapper.insertSelective(bean);
	}
	 
}
