package com.tf.base.index.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.tf.base.common.domain.IndexInfo;
import com.tf.base.common.domain.IndexInfoProcess;
import com.tf.base.common.domain.ModelInfo;
import com.tf.base.common.persistence.IndexInfoMapper;
import com.tf.base.index.domain.RecentProjectInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tf.base.common.persistence.AssessInfoMapper;
import com.tf.base.index.domain.CountInfo;
import com.tf.base.index.domain.RecentAssessInfo;
import tk.mybatis.mapper.entity.Example;

@Service
public class WelcomeService {
	
	@Autowired
	private AssessInfoMapper assessInfoMapper;

	@Autowired
	private IndexInfoMapper indexInfoMapper;

	public void test(){
		System.out.println("aaa");
	}

	public List<String> categoryCount(String userid) {
		return assessInfoMapper.categoryCount(userid);
	}

	public List<RecentProjectInfo> recentAssessCount(String userid) {

		List<RecentProjectInfo> recentInfo=assessInfoMapper.recentAssessCount(userid);

//			if (recentInfo.size()>0) {
//				for (int i = 0; i < recentInfo.size(); i++) {
//
////					Integer indexid=Integer.parseInt(recentInfo.get(i).getIndex_id());
////					Example example = new Example(IndexInfo.class);
////					example.createCriteria().andEqualTo("id", indexid);
////					IndexInfo indexInfo=indexInfoMapper.selectByPrimaryKey(example);
//
//
//
////					指标名称多个无法确认暂时屏蔽
//				/*	String indexid = recentInfo.get(i).getIndex_id();
//					if(indexid==null){
//						recentInfo.get(i).setIndex_name("");
//					}else {
//						IndexInfo indexInfo = indexInfoMapper.selectIndexNamebykey(indexid);
//						recentInfo.get(i).setIndex_name(indexInfo.getIndexName());
//					}*/
//
//
//
//
//
//				}
//			}
		return recentInfo;
	}
	
	public List<String> recentYearList(int num){
		String month = null;
		String dateString;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		dateString = sdf.format(cal.getTime());
		List<String> rqList = new ArrayList<>();
		System.out.println("倒序前\n");
		for (int i = 0; i < num; i++) {
		dateString = sdf.format(cal.getTime());
		 
		System.out.println("dateString"+dateString);
		 
		rqList.add(dateString.substring(0, 7));
		//xfzeList.add(xfze);

		cal.add(Calendar.MONTH, -1);
		}

		// 倒序
		Collections.reverse(rqList);
		System.out.println("倒序后\n");
		for(int i=0;i<rqList.size();i++){
		System.out.println("倒序后日期："+rqList.get(i));
		}
		return rqList;
	}

	public List<CountInfo> countProject(String userid,String startMonth, String endMonth) {
		
		return assessInfoMapper.countProject(userid,startMonth,endMonth);
	}

	public List<CountInfo> countModel(String userid,String startMonth, String endMonth) {
		return assessInfoMapper.countModel(userid,startMonth,endMonth);
	}

	public List<CountInfo> countIndex(String userid,String startMonth, String endMonth) {
		return assessInfoMapper.countIndex(userid,startMonth,endMonth);
	}
}
