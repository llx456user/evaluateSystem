package com.tf.base.project.controller;

import java.util.*;

import com.tf.base.common.utils.BigDecimalUtils;
import com.tf.base.project.ahp.AhpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tf.base.common.domain.AssessInfo;
import com.tf.base.common.domain.ProjectIndex;
import com.tf.base.common.domain.ProjectInfo;
import com.tf.base.common.persistence.AssessInfoMapper;
import com.tf.base.common.persistence.ProjectIndexMapper;
import com.tf.base.common.service.BaseService;
import com.tf.base.common.service.CommonService;
import com.tf.base.common.utils.StringUtil;
import com.tf.base.project.domain.AssessInfoParams;
import com.tf.base.project.domain.ProjectIndexParams;
import com.tf.base.project.domain.ProjectInfoParams;
import com.tf.base.project.service.ProjectIndexService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class ProjectIndexController {

	@Autowired
	private BaseService baseService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private ProjectIndexService projectIndexService;

	@Autowired
	private ProjectIndexMapper projectIndexMapper;
	@Autowired
	private AssessInfoMapper assessInfoMapper;

	/**
	 * 跳转编辑
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/project/editProjectIndex", method = RequestMethod.GET)
	public String editProjectIndex(Model model,String id ) {
		ProjectIndex projectIndex = projectIndexService.getById(Integer.parseInt(id));

		String ahp = projectIndex.getAhp();
		JSONObject ahpJson = JSONObject.fromObject(ahp);
		JSONArray xArray = ahpJson.getJSONArray("xArray");
		JSONArray yArray = ahpJson.getJSONArray("yArray");
		String weight = projectIndex.getWeight();
		JSONObject weightJson = JSONObject.fromObject(weight);
		model.addAttribute("bean", projectIndex);
		model.addAttribute("xArray", xArray);
		model.addAttribute("yArray", yArray);
		model.addAttribute("weightJson", weightJson);

		List<ProjectIndex> projectIndexs  = projectIndexService.getChildIndex(Integer.parseInt(id));

		JSONObject childsJson = new JSONObject();
		for (ProjectIndex projectIndex2 : projectIndexs) {
			childsJson.put(projectIndex2.getId(), projectIndex2.getIndexName());
		}
		model.addAttribute("childsJson", childsJson);
		return "project/editProjectIndex";
	}

	/**
	 * 跳转编辑
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/project/weightSet", method = RequestMethod.GET)
	public String weightSet(Model model,String id ) {
		ProjectIndex projectIndex = projectIndexService.getById(Integer.parseInt(id));

		String ahp = projectIndex.getAhp();
		JSONObject ahpJson = JSONObject.fromObject(ahp);
		JSONArray xArray = ahpJson.getJSONArray("xArray");
		JSONArray yArray = ahpJson.getJSONArray("yArray");
		String weight = projectIndex.getWeight();
		JSONObject weightJson = JSONObject.fromObject(weight);
		model.addAttribute("bean", projectIndex);
		model.addAttribute("xArray", xArray);
		model.addAttribute("yArray", yArray);
		model.addAttribute("weightJson", weightJson);

		List<ProjectIndex> projectIndexs  = projectIndexService.getChildIndex(Integer.parseInt(id));

		JSONObject childsJson = new JSONObject();
		for (ProjectIndex projectIndex2 : projectIndexs) {
			childsJson.put(projectIndex2.getId(), projectIndex2.getIndexName());
		}
		model.addAttribute("childsJson", childsJson);
		return "project/weightSet";
	}
	/*
	* 校验根节点是否存在
	*
	* */
	@RequestMapping(value = "/project/checkRoot", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkRoot(ProjectIndexParams params) {
//		String projectId=params.getProjectId();
		Map msg=new HashMap();
		boolean b = projectIndexService.checkProjectRoot( Integer.parseInt(params.getProjectId()));
		if (b){
			msg=commonService.returnMsg(0, "根节点存在");
		}else {
			msg=commonService.returnMsg(1, "创建成功");
		}
		return msg;
	}
	/**
	 * 添加
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/project/addProjectIndex", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addProjectIndex(ProjectIndexParams params) {

		boolean b = projectIndexService.indexNameExist(params.getIndexName(),Integer.parseInt(params.getProjectId()),params.getParentId());
		if(b){
			return commonService.returnMsg(0, "节点名称已存在.请更换");
		}
		ProjectIndex bean = new ProjectIndex();
		bean.setProjectId(Integer.parseInt(params.getProjectId()));
		if(params.getParentId()!=0){
			ProjectIndex parentBean = projectIndexMapper.selectByPrimaryKey(params.getParentId());
			if(parentBean!=null){
				bean.setIndexLevel(parentBean.getIndexLevel()+1);
			}
		}else{
			bean.setIndexLevel(1);
		}

		bean.setParentid(params.getParentId());
		bean.setIndexName(params.getIndexName());
//		bean.setRemark(params.getRemarks());
		bean.setIndexStatus(0);
		bean.setIsdelete(0);
		bean.setAhp("{\"xArray\":[],\"yArray\":[]}");
		JSONObject weightJson=new JSONObject();
		bean.setWeight(weightJson.toString());
		int beanId = projectIndexService.addIndex(bean);
		if (bean.getParentid() == 0) { //是跟节点
			return beanId > 0 ? commonService.returnMsg(1, "保存成功", bean.getId() + "") : commonService.returnMsg(0, "保存失败");
		}
		ProjectIndex projectIndex = projectIndexMapper.selectByPrimaryKey(params.getParentId());
		String ahp = projectIndex.getAhp();
		String weight = projectIndex.getWeight();

		ProjectIndex record =  new ProjectIndex();
		record.setParentid(params.getParentId());
		record.setIsdelete(0);
		List<ProjectIndex> projectIndexs = projectIndexMapper.select(record);

		ProjectIndex save = new ProjectIndex();
		save.setId(projectIndex.getId());
		int k = 0;
		if(!StringUtil.isEmpty(ahp)){
			JSONObject json = JSONObject.fromObject(ahp);
			JSONArray xArray = json.getJSONArray("xArray");
			JSONArray yArray = json.getJSONArray("yArray");
			JSONArray newxArray = new JSONArray();
			JSONArray newyArray = new JSONArray();
			for (int i = 0; i < xArray.size(); i++) {
				JSONObject jsonObject = xArray.getJSONObject(i);
				jsonObject.put(beanId + "","");
				newxArray.add(jsonObject);
			}
			for (int i = 0; i < yArray.size(); i++) {
				JSONObject jsonObject = xArray.getJSONObject(i);
				jsonObject.put(beanId + "","");
				newyArray.add(jsonObject);
			}

			JSONObject jsonObject2 = new JSONObject();
			for (ProjectIndex projectIndex2 : projectIndexs) {
				jsonObject2.put(projectIndex2.getId() + "","");
			}
			newxArray.add(jsonObject2);
			newyArray.add(jsonObject2);

			JSONObject ahpJson = new JSONObject();
			ahpJson.put("xArray", newxArray);
			ahpJson.put("yArray", newyArray);
			save.setAhp(ahpJson.toString());
			k++;
		}

		if(!StringUtil.isEmpty(weight)){
			JSONObject jsonObject = JSONObject.fromObject(weight);
			jsonObject.put(beanId + "","");
			save.setWeight(jsonObject.toString());
			k++;
		}
		if(k>0)
			projectIndexMapper.updateByPrimaryKeySelective(save);
		return beanId > 0 ? commonService.returnMsg(1, "保存成功",bean.getId()+"") : commonService.returnMsg(0, "保存失败");
	}
	@RequestMapping(value = "/project/deleteProjectIndex", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteProjectIndex(ProjectIndexParams params) {
		int i = 0;
		try {
			ProjectIndex tmp = projectIndexMapper.selectByPrimaryKey(params.getId());//等待删除的指标
			ProjectIndex parentIndex = projectIndexMapper.selectByPrimaryKey(tmp.getParentid());//父指标
			i=projectIndexService.delIndex(Integer.parseInt(params.getId()));//删除指标

			ProjectIndex record =  new ProjectIndex();
			record.setParentid(tmp.getParentid());
			record.setIsdelete(0);
			List<ProjectIndex> bIndexs = projectIndexMapper.select(record);//获取兄弟指标

			if(!StringUtil.isEmpty(parentIndex.getAhp())){
				JSONArray xArray = new JSONArray();
				JSONArray yArray = new JSONArray();
				JSONObject jsonObject = new JSONObject();
				for ( ProjectIndex tIndex :bIndexs) {
					jsonObject.put(tIndex.getId()+"","");
				}
				for(int pCount=0;pCount<bIndexs.size();pCount++){
					xArray.add(jsonObject);
					yArray.add(jsonObject);
				}
				JSONObject ahpJson = new JSONObject();
				ahpJson.put("xArray",xArray);
				ahpJson.put("yArray",yArray);
				parentIndex.setAhp(ahpJson.toString());
				parentIndex.setWeight(jsonObject.toString());
				projectIndexMapper.updateByPrimaryKeySelective(parentIndex);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return i > 0 ? commonService.returnMsg(1, "删除成功") : commonService.returnMsg(0, "删除失败");
	}

	@RequestMapping(value = "/project/setIndexId", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setIndexId(ProjectIndexParams params) {
		int i=0;
		try {
			ProjectIndex tmp = projectIndexMapper.selectByPrimaryKey(params.getId());
			tmp.setIndexId(Integer.valueOf(params.getIndexId()));
			tmp.setUpdateTime(new Date());
			i=projectIndexMapper.updateByPrimaryKeySelective(tmp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i > 0 ? commonService.returnMsg(1, "操作成功") : commonService.returnMsg(0, "操作失败");
	}

	@RequestMapping(value = "/project/setIndexChildId", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setIndexChildId(ProjectIndexParams params) {
		int i=0;
		try {
			ProjectIndex tmp = projectIndexMapper.selectByPrimaryKey(params.getId());
			tmp.setIndexChildId(Integer.valueOf(params.getIndexId()));
			tmp.setUpdateTime(new Date());
			i=projectIndexMapper.updateByPrimaryKeySelective(tmp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i > 0 ? commonService.returnMsg(1, "操作成功") : commonService.returnMsg(0, "操作失败");
	}

	@RequestMapping(value = "/project/setWeightCurrent", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setWeightCurrent(ProjectIndexParams params) {
		int i=0;
		try {
			ProjectIndex tmp = projectIndexMapper.selectByPrimaryKey(params.getId());
			tmp.setWeightCurrent(params.getWeightCurrent());
			i=projectIndexMapper.updateByPrimaryKeySelective(tmp);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return i > 0 ? commonService.returnMsg(1, "操作成功") : commonService.returnMsg(0, "操作失败");
	}

	@RequestMapping(value = "/project/calcProjectIndex", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> calcProjectIndex(String reqJson
	) {
		int i=1;
		JSONObject weightJson=new  JSONObject() ;
		try{
			JSONObject json = JSONObject.fromObject(reqJson);
			JSONArray xArray = json.getJSONArray("xArrayJsonStr");
			JSONArray yArray = json.getJSONArray("yArrayJsonStr");
			 weightJson = json.getJSONObject("weightJsonStr");
			Iterator iterator = weightJson.keys();
			float[] weight = AhpUtil.getAhp(xArray);
			for (int j=0;j<weight.length;j++){
				weight[j]=BigDecimalUtils.formatFloat(weight[j]);

			}
			int colum =0 ;
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				weightJson.put(key,String.valueOf(weight[colum]));
//			String value = jsonObject.getString(key);
//                    System.out.println(key + "=" + value);
//			matixc_float[i][colum]=Float.valueOf(value);
				colum++;
			}
		}catch (Exception e){
			e.printStackTrace();
			i=0;
		}



		return i > 0 ? commonService.returnMsg(1, "ahp成功",weightJson.toString()) : commonService.returnMsg(0, "ahp失败");
	}
	/**
	 * 保存
	 * @param reqJson
	 * @return
	 */
	@RequestMapping(value = "/project/saveProjectIndex", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveProjectIndex(String reqJson
			) {
		JSONObject json = JSONObject.fromObject(reqJson);
		ProjectIndexParams params = (ProjectIndexParams) JSONObject.toBean(json.getJSONObject("paramsStr"),ProjectIndexParams.class);
		JSONArray xArray = json.getJSONArray("xArrayJsonStr");
		JSONArray yArray = json.getJSONArray("yArrayJsonStr");
		JSONObject weightJson = json.getJSONObject("weightJsonStr");
		int i = 0;
		JSONObject ahpJson = new JSONObject();
		ahpJson.element("xArray", xArray);
		ahpJson.element("yArray", yArray);
		i = projectIndexService.updateIndex(Integer.parseInt(params.getId()), ahpJson, weightJson);
		return i > 0 ? commonService.returnMsg(1, "编辑成功") : commonService.returnMsg(0, "编辑失败");
	}

	@RequestMapping(value = "/project/saveWeightSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveWeightSet(String reqJson
			) {
		JSONObject json = JSONObject.fromObject(reqJson);
		ProjectIndexParams params = (ProjectIndexParams) JSONObject.toBean(json.getJSONObject("paramsStr"),ProjectIndexParams.class);
		JSONObject weightJson = json.getJSONObject("weightJsonStr");
		int i = 0;
		i = projectIndexService.updateIndex(Integer.parseInt(params.getId()), null, weightJson);
		return i > 0 ? commonService.returnMsg(1, "编辑成功") : commonService.returnMsg(0, "编辑失败");
	}

	@RequestMapping("/project/startEvaluate")
	public String startEvaluate(Model model,String indexId,String projectId){
		model.addAttribute("projectId", projectId);
		model.addAttribute("indexId", indexId);
		return "project/evaluateResult2";
	}
	@RequestMapping("/project/evaluateList")
	@ResponseBody
	public Map<String, Object> evaluateList(AssessInfoParams params,int page,int rows) {

		Map<String, Object> result = new HashMap<String, Object>();

		int start = (page - 1) * rows;
		List<AssessInfo> rowslist = new ArrayList<AssessInfo>();

		int total = assessInfoMapper.queryCount(params);

		if (total == 0) {
			result.put("rows", rowslist);
			result.put("total", total);
			return result;
		}
		rowslist = assessInfoMapper.queryList(params, start);

		result.put("total", total);
		result.put("rows", rowslist);
		return result;
	}
	@RequestMapping("/project/addProjectEvaluate")
	@ResponseBody
	public Map<String, Object> addProjectEvaluate(AssessInfo params) {

		Map<String, Object> result = new HashMap<String, Object>();

		Date now = new Date();
		Integer userId = Integer.parseInt(baseService.getUserId());

		params.setUpdateTime(now);
		params.setCreateTime(now);
		params.setUpdateUid(userId);
		params.setCreateUid(userId);

		params.setAssessStatus(0);//weipinggu
		params.setIsdelete(0);

		int i = assessInfoMapper.insertSelective(params);

		return i > 0? commonService.returnMsg(1, "添加成功"):commonService.returnMsg(0, "添加失败");
	}


	@RequestMapping("/project/setProjectIndex")
	public String setProjectIndex(Model model,String frameid,String nodeId){
		model.addAttribute("frameid", frameid);
		model.addAttribute("nodeId", nodeId);
		return "project/indexList";
	}

	@RequestMapping("/project/setProjectChildIndex")
	public String setProjectChildIndex(Model model,String frameid,String nodeId){
		model.addAttribute("frameid", frameid);
		model.addAttribute("nodeId", nodeId);
		return "project/childIndexList";
	}

	@RequestMapping("/project/updatename")
	@ResponseBody
	public Map<String, Object> updatename(ProjectIndexParams params) {
		int i = 0;
		boolean b = projectIndexService.indexNameExist(params.getIndexName(),Integer.parseInt(params.getProjectId()),params.getParentId());
		if(b){
			return commonService.returnMsg(0, "节点名称已存在.请更换");
		}
		projectIndexService.updateName(params);
		i++;
		return i > 0 ? commonService.returnMsg(1, "修改成功") : commonService.returnMsg(0, "修改失败");

	}
}
