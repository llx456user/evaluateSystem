package com.tf.base.project.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tf.base.common.domain.*;
import com.tf.base.common.persistence.AssessParamMapper;
import com.tf.base.common.persistence.ProjectIndexAssessMapper;
import com.tf.base.common.utils.DateUtil;
import com.tf.base.common.utils.StringUtil;
import com.tf.base.platform.service.IndexModelService;
import com.tf.base.project.domain.AssessTable;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tf.base.common.persistence.AssessInfoMapper;
import com.tf.base.common.persistence.ProjectIndexMapper;
import com.tf.base.common.service.BaseService;
import com.tf.base.project.domain.AssessInfoParams;
import tk.mybatis.mapper.entity.Example;

@Service
public class AssessService {

	@Autowired
	private ProjectIndexMapper projectIndexMapper;
	@Autowired
	private AssessInfoMapper assessInfoMapper;
	@Autowired
	private  ProjectIndexAssessMapper projectIndexAssessMapper ;
	@Autowired
	private  AssessParamMapper assessParamMapper ;
	@Autowired
	private IndexModelService indexModelService ;
	@Autowired
    private BaseService baseService;

	/**
	 * 叶子节点--必须关联指标   过程节点设置ahp+权重
	 * @param nodeId
	 * @return 0-验证通过   -1-叶子节点没有关联指标 -2=没有设置权重
	 */
	private int checkNode(Integer nodeId){
		int ret=0;
		List<ProjectIndex> list=new ArrayList<>();
		ProjectIndex current=projectIndexMapper.selectByPrimaryKey(nodeId);
		Map<Integer,Integer> lastMap = new HashMap();
		list.add(current);
		list=getSubNode(list, nodeId,lastMap);
		if (list.size()==1) {
			if (list.get(list.size()-1).getIndexId() == null&&list.get(list.size()-1).getIndexChildId()==null) {
				ret = -1;
				return ret;
			}
		}
		for(ProjectIndex p :list){


			if(p.getIsLastNode()==1){
				if(p.getIndexId()==null&&list.get(list.size()-1).getIndexChildId()==null){
					ret=-1;
					break;
				}
				if(StringUtil.isEmpty(p.getWeightCurrent())){//设置权重
					ret=-2;
					break;
				}
			}else {
			    if(p.getParentid()!=null&&p.getParentid()!=0){//根节点以外的中间节点，都需要进行权重设置
                    if(StringUtil.isEmpty(p.getWeightCurrent())){
                        ret=-2;
                        break;
                    }
                }
			}
		}
		return ret;
	}
	/**
	 * 递归查询当前节点下的子节点
	 * @param list
	 * @param nodeId
	 * @return
	 */
	public List<ProjectIndex> getSubNode(List<ProjectIndex> list,Integer nodeId,Map<Integer,Integer>lastMap){
		Example example = new Example(ProjectIndex.class);
		example.createCriteria().andEqualTo("parentid", nodeId).andEqualTo("isdelete", 0);
		List<ProjectIndex> subList=projectIndexMapper.selectByExample(example);
		if(subList!=null&&subList.size()>0){
//			list.addAll(subList);
			for(ProjectIndex p:subList){
				getSubNode(list, p.getId(),lastMap);
//				list.addAll(getSubNode(list, p.getId()));
				if(lastMap.keySet().contains(p.getId())){
					p.setIsLastNode(1);
				}
				list.add(p);
			}
		}else{
			//当前节点是叶子节点
			lastMap.put(nodeId,nodeId);


		}
		return list;

	}





	/**
	 * 根据项目id把指标信息组装成以下格式的json
	 *[{ key: "1" ,name:"根节点"}, { key: "2", parent: "1",name:"一级节点1" ,color:green}, { key: "2", parent: "1" ,name:"一级节点2",color:red}]
	 * @param sssertId
	 * @return
	 */
	public JSONArray getIndexJsonByAssertId(Integer sssertId){
		JSONArray ja=new JSONArray();
		ProjectIndexAssess record =  new ProjectIndexAssess();
		record.setAssessId(sssertId);
		record.setIsdelete(0);
		List<ProjectIndexAssess> assessList = projectIndexAssessMapper.select(record);
		if(assessList!=null&&assessList.size()>0){
			for(ProjectIndexAssess index:assessList){
				JSONObject jo=new JSONObject();
				jo.put("key", index.getProjectIndexId());
				String name = index.getIndexName()+":"+(index.getIndexValue()==null?"":index.getIndexValue());
				jo.put("name", name);
				jo.put("color", "green");
				if(index.getParentid()>0){
					jo.put("parent", index.getParentid());
				}
				ja.add(jo);
			}
		}
		return ja;
	}


	public AssessTable getAssessTableByAssertId(Integer assertId){
		AssessTable assessTable = new AssessTable();
		ProjectIndexAssess record =  new ProjectIndexAssess();
		record.setAssessId(assertId);
		record.setIsdelete(0);
		List<ProjectIndexAssess> assessList = projectIndexAssessMapper.select(record);
		if(assessList!=null&&assessList.size()>0){
			for(ProjectIndexAssess index:assessList){
				assessTable.addProjectIndexAssess(index);
			}
		}
		return assessTable;
	}

	public ProjectIndexAssess getProjectIndexAssess(Integer assertId){
		ProjectIndexAssess record =  new ProjectIndexAssess();
		record.setAssessId(assertId);
		record.setIsdelete(0);
		List<ProjectIndexAssess> assessList = projectIndexAssessMapper.select(record);
		if (assessList.size() > 0 ){
			record = assessList.get(0);
		}
		return record;
	}

	public int assessSaveTo(Integer nodeId){
		AssessInfo Info = new AssessInfo();
		//评估名称自动生成，按照节点名称+时间
		Date now =new Date();
		ProjectIndex node = projectIndexMapper.selectByPrimaryKey(nodeId);
//		Info.setAssessName(node.getIndexName() + DateUtil.TimeToString(new Date()));
		Info.setAssessName(node.getIndexName());
		Info.setProjectId(node.getProjectId());
		Info.setAssessStatus(0);
		Info.setNodeId(nodeId);
		Info.setCreateTime(now);
		Info.setCreateUid(Integer.parseInt(baseService.getUserId()));
		Info.setUpdateTime(now);
		Info.setUpdateUid(Integer.parseInt(baseService.getUserId()));
		Info.setIsdelete(0);
		Info.setIndexId(node.getIndexId());

		return assessSave(Info);
	}

	public int assessSave(AssessInfo assessInfo) {

		//先验证 指标树各节点有效性（叶子节点--必须关联指标   过程节点设置ahp+权重）
		int checkRet=checkNode(assessInfo.getNodeId());
		if(checkRet!=0){
			return checkRet;
		}

		//保存各个节点信息

		int reuslt =assessInfoMapper.insertSelective(assessInfo) ;

		Example example = new Example(ProjectIndex.class);
		example.createCriteria().andEqualTo("projectId", assessInfo.getProjectId());
		example.setOrderByClause("index_level,parentid   asc"); //排序
		List<ProjectIndex> list = projectIndexMapper.selectByExample(example);

		List<ProjectIndexAssess>projectIndexAssessList = new ArrayList<>();
		Map<Integer,Integer> parentKey = new HashMap<>();
		Integer parentID = assessInfo.getNodeId();
		List<AssessParam> apList =  new ArrayList<>();
		for(ProjectIndex projectIndex :list ){
			ProjectIndexAssess projectIndexAssess = new ProjectIndexAssess();
			BeanUtils.copyProperties(projectIndex,projectIndexAssess);
			projectIndexAssess.setAssessId(assessInfo.getId());
			projectIndexAssess.setProjectIndexId(projectIndex.getId());
			projectIndexAssessList.add(projectIndexAssess);
			if(projectIndex.getParentid()!=null){
				if(projectIndex.getIndexId()!=null){
//					if(projectIndex.getParentid().intValue()==parentID.intValue()){
						parentKey.put(projectIndex.getId(),projectIndex.getId());
						addAssessParam(assessInfo, apList, projectIndex);
//					}else if(parentKey.containsKey(projectIndex.getParentid())){//
//						parentKey.put(projectIndex.getId(),projectIndex.getId());
//						addAssessParam(assessInfo, apList, projectIndex);
//					}
				}
			}
		}
		if(projectIndexAssessList.size()>0){//插入
			projectIndexAssessMapper.insertList(projectIndexAssessList);
		}

		if(apList.size()>0){//插入默认参数
			assessParamMapper.insertList(apList);
		}
		//保存参数信息
		return assessInfo.getId();
	}

	/**
	 * 增加评估参数
	 * @param assessInfo
	 * @param apList
	 * @param projectIndex
	 */
	private void addAssessParam(AssessInfo assessInfo, List<AssessParam> apList, ProjectIndex projectIndex) {
		AssessParam assessParam=null ;
		List<IndexModel> fileListParam = indexModelService.getIndexFileParam(projectIndex.getIndexId());
		for(IndexModel fModel:fileListParam){
			assessParam = new AssessParam();
            assessParam.setAssessId(assessInfo.getId());
            assessParam.setIndexId(projectIndex.getIndexId());
            assessParam.setNodeId(projectIndex.getId());
            assessParam.setNodeKey(fModel.getNodeKey());
            assessParam.setParamName(fModel.getParamName());
            assessParam.setParamType("file");
            apList.add(assessParam);
        }
		List<IndexModel> constantParamLsit = indexModelService.getIndexConstantParam(projectIndex.getIndexId());
		for(IndexModel cIndexModel:constantParamLsit){
			assessParam = new AssessParam();
            assessParam.setAssessId(assessInfo.getId());
            assessParam.setIndexId(projectIndex.getIndexId());
            assessParam.setNodeId(projectIndex.getId());
            assessParam.setNodeKey(cIndexModel.getNodeKey());
            assessParam.setParamName(cIndexModel.getParamName());
            assessParam.setParamType("constant");
            apList.add(assessParam);
        }
	}


	public List<ProjectIndex> categoryList(String projectId) {
		ProjectIndex t = new ProjectIndex();
		t.setIsdelete(0);
		t.setProjectId(Integer.parseInt(projectId));
		return projectIndexMapper.select(t);
	}
	public int queryCount(AssessInfoParams params) {
		return assessInfoMapper.queryCount(params);
	}
	public List<AssessInfo> queryList(AssessInfoParams params, int start) {
		return assessInfoMapper.queryList(params,start);
	}

	public int assessUpdate(AssessInfo templetInfo) {

		return assessInfoMapper.updateByPrimaryKeySelective(templetInfo);
	}
	public List selectNodetree(String params) {
		ProjectIndex projectIndex=new ProjectIndex();
		Integer i=Integer.parseInt(params);
		projectIndex.setProjectId(i);
		List weight_current=projectIndexMapper.selectNodetree(params);
		return weight_current;
	}
}
