package com.tf.base.project.service;

import com.tf.base.common.domain.IndexModel;
import com.tf.base.common.domain.ProjectIndex;
import com.tf.base.common.domain.ProjectParamTemplate;
import com.tf.base.common.domain.ProjectParamTemplateValue;
import com.tf.base.common.persistence.IndexModelMapper;
import com.tf.base.common.persistence.ProjectIndexMapper;
import com.tf.base.common.persistence.ProjectParamTemplateMapper;
import com.tf.base.common.persistence.ProjectParamTemplateValueMapper;
import com.tf.base.common.service.BaseService;
import com.tf.base.common.utils.BeanUtils;
import com.tf.base.common.utils.StringUtil;
import com.tf.base.go.type.Category;
import com.tf.base.platform.domain.IndexModelExt;
import com.tf.base.project.domain.ProjectBatchAssessTemplateParam;
import com.tf.base.project.domain.ProjectIndexGroupTemplateParam;
import com.tf.base.project.domain.ProjectIndexParams;
import com.tf.base.project.domain.ProjectParamBean;
import com.tf.base.util.DescartesUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectIndexService {

    @Autowired
    private BaseService baseService;

    @Autowired
    private ProjectIndexMapper projectIndexMapper;

    @Autowired
    private IndexModelMapper indexModelMapper;

    @Autowired
    private ProjectParamTemplateMapper projectParamTemplateMapper;

    @Autowired
    private ProjectParamTemplateValueMapper projectParamTemplateValueMapper;

    /**
     * 新增指标
     * 关键字段不能为空 project_id、index_name、index_status、index_level、parentid
     *
     * @param bean
     * @return
     */
    public int addIndex(ProjectIndex bean) {
        Integer userid = Integer.parseInt(baseService.getUserId());
        Date now = new Date();

        bean.setCreateUid(userid);
        bean.setCreateTime(now);
        bean.setUpdateTime(now);
        bean.setUpdateUid(userid);

        projectIndexMapper.insertSelective(bean);
        return bean.getId();
    }

    /**
     * 根据项目id把指标信息组装成以下格式的json
     * [{ key: "1" ,name:"根节点"}, { key: "2", parent: "1",name:"一级节点1" ,color:green}, { key: "2", parent: "1" ,name:"一级节点2",color:red}]
     *
     * @param projectId
     * @return
     */
    public JSONArray getIndexJsonByProjectId(Integer projectId) {
        JSONArray ja = new JSONArray();
        ProjectIndex record = new ProjectIndex();
        record.setProjectId(projectId);
        record.setIsdelete(0);
        List<ProjectIndex> projectIndexs = projectIndexMapper.select(record);
        if (projectIndexs != null && projectIndexs.size() > 0) {
            for (ProjectIndex index : projectIndexs) {
                JSONObject jo = new JSONObject();
                jo.put("key", index.getId());
                jo.put("name", index.getIndexName());
                jo.put("color", "green");
                if (index.getParentid() > 0) {
                    jo.put("parent", index.getParentid());
                }
                ja.add(jo);
            }
        }
        return ja;

    }

//	public void updateParentNodeAhpAndWeight(ProjectIndex sub){
//		ProjectIndex projectIndex = projectIndexMapper.selectByPrimaryKey(sub.getParentid());
//		String ahp = projectIndex.getAhp();
//		String weight = projectIndex.getWeight();
//
//		ProjectIndex record =  new ProjectIndex();
//		record.setParentid(sub.getParentid());
//		record.setIsdelete(0);
//		List<ProjectIndex> projectIndexs = projectIndexMapper.select(record);
//
//		ProjectIndex save = new ProjectIndex();
//		save.setId(projectIndex.getId());
//		int k = 0;
//		if(!StringUtil.isEmpty(ahp)){
//			JSONObject json = JSONObject.fromObject(ahp);
//			JSONArray xArray = json.getJSONArray("xArray");
//			JSONArray yArray = json.getJSONArray("yArray");
//			JSONArray newxArray = new JSONArray();
//			JSONArray newyArray = new JSONArray();
//			for (int i = 0; i < xArray.size(); i++) {
//				JSONObject jsonObject = xArray.getJSONObject(i);
//				jsonObject.put(sub.getId() + "","");
//				newxArray.add(jsonObject);
//			}
//			for (int i = 0; i < yArray.size(); i++) {
//				JSONObject jsonObject = xArray.getJSONObject(i);
//				jsonObject.put(sub.getId() + "","");
//				newyArray.add(jsonObject);
//			}
//
//			JSONObject jsonObject2 = new JSONObject();
//			for (ProjectIndex projectIndex2 : projectIndexs) {
//				jsonObject2.put(projectIndex2.getId() + "","");
//			}
//			newxArray.add(jsonObject2);
//			newyArray.add(jsonObject2);
//
//			JSONObject ahpJson = new JSONObject();
//			ahpJson.put("xArray", newxArray);
//			ahpJson.put("yArray", newyArray);
//
//			save.setAhp(ahpJson.toString());
//			k++;
//		}
//		if(!StringUtil.isEmpty(weight)){
//			JSONObject jsonObject = JSONObject.fromObject(weight);
//			jsonObject.put(sub.getId() + "","");
//			save.setWeight(jsonObject.toString());
//			k++;
//		}
//		if(k>0)
//			projectIndexMapper.updateByPrimaryKeySelective(save);
//
//
//	}

    /**
     * 验证节点名称是否存在
     *
     * @return true--存在  false--不存在
     */
    public boolean indexNameExist(String indexName, Integer projectId, Integer parentId) {
        if (parentId == null) {
            return false;
        }

        Example example = new Example(ProjectIndex.class);
        example.createCriteria().andEqualTo("indexName", indexName)
                .andEqualTo("isdelete", 0).andEqualTo("projectId", projectId)
                .andEqualTo("parentid", parentId);
        List<ProjectIndex> list = projectIndexMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;

    }

    /**
     * 验证根节点是否存在
     *
     * @return true--存在  false--不存在
     */
    public boolean checkProjectRoot(Integer projectId) {

        Example example = new Example(ProjectIndex.class);
        example.createCriteria().andEqualTo("isdelete", 0).andEqualTo("projectId", projectId);
        List<ProjectIndex> list = projectIndexMapper.selectByExample(example);
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {

            ProjectIndex projectIndex = (ProjectIndex) iterator.next();
            if (projectIndex.getIndexLevel() == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据节点ID删除节点
     *
     * @param id
     */
    public int delIndex(Integer id) {
        ProjectIndex bean = new ProjectIndex();
        bean.setId(id);
        bean.setIsdelete(1);
        return projectIndexMapper.updateByPrimaryKeySelective(bean);
    }

    /**
     * @param id         节点id
     * @param ahpJson    {"xArray":[{"BBId": "1","CCId": "1","DDId": "1"},{"BBId": "1","CCId": "1","DDId": "1"},{"BBId": "1","CCId": "1","DDId": "1"}],
     *                   "yArray":[{"BBId": "1","CCId": "1","DDId": "1"},{"BBId": "1","CCId": "1","DDId": "1"},{"BBId": "1","CCId": "1","DDId": "1"}]}
     * @param weightJson 权重{"BBId":"111","CCId":"222","DDId":"333"}
     * @return
     */
    public int updateIndex(Integer id,
                           JSONObject ahpJson, JSONObject weightJson) {
        Iterator iterator = weightJson.keys();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String value = weightJson.getString(key);
            //更新子节点值
            updateChildWeight(Integer.valueOf(key), value);
        }
        ProjectIndex bean = projectIndexMapper.selectByPrimaryKey(id);
        if (ahpJson != null) {
            bean.setAhp(ahpJson.toString());
        }
        bean.setWeight(weightJson.toString());
        Integer userid = Integer.parseInt(baseService.getUserId());
        Date now = new Date();
        bean.setUpdateTime(now);
        bean.setUpdateUid(userid);
        return projectIndexMapper.updateByPrimaryKey(bean);
    }


    public void updateChildWeight(Integer id, String weight) {
        ProjectIndex bean = projectIndexMapper.selectByPrimaryKey(id);
        bean.setWeightCurrent(weight);
        Integer userid = Integer.parseInt(baseService.getUserId());
        Date now = new Date();
        bean.setUpdateTime(now);
        bean.setUpdateUid(userid);
        projectIndexMapper.updateByPrimaryKey(bean);
    }


    /**
     * 根据id查询节点信息
     * 如果节点aph 和 weight是空  会根据其子节点拼接出空结构
     *
     * @param id
     * @return
     */
    public ProjectIndex getById(Integer id) {
        ProjectIndex bean = projectIndexMapper.selectByPrimaryKey(id);
        //查询子节点
        List<ProjectIndex> subList = getChildIndex(id);
        if (bean != null && subList.size() > 0) {
            //拼接aph json
            if (StringUtil.isEmpty(bean.getAhp())) {
                JSONObject aphJson = new JSONObject();
                JSONArray xArray = new JSONArray();
                JSONArray yArray = new JSONArray();
                for (int i = 0; i < subList.size(); i++) {
                    JSONObject xJson = new JSONObject();
                    JSONObject yJson = new JSONObject();
                    for (ProjectIndex sub : subList) {
                        xJson.put(sub.getId().toString(), "");
                        yJson.put(sub.getId().toString(), "");
                    }
                    xArray.add(xJson);
                    yArray.add(xJson);
                }
                aphJson.put("xArray", xArray);
                aphJson.put("yArray", yArray);
                bean.setAhp(aphJson.toString());
            }

            //拼接权重json
            if (StringUtil.isEmpty(bean.getWeight())) {
                JSONObject weightJson = new JSONObject();
                for (ProjectIndex sub : subList) {
                    weightJson.put(sub.getId().toString(), "");
                }
                bean.setWeight(weightJson.toString());
            }
        }

        return bean;

    }

    /**
     * 根据id 查询子节点
     *
     * @param id
     * @return
     */
    public List<ProjectIndex> getChildIndex(Integer id) {
        List<ProjectIndex> subList = new ArrayList<>();
        Example example = new Example(ProjectIndex.class);
        example.createCriteria().andEqualTo("parentid", id)
                .andEqualTo("isdelete", 0);
        subList = projectIndexMapper.selectByExample(example);
        return subList;
    }

    /**
     * 获取项目参数
     *
     * @param projectId
     * @return
     */
    public List<ProjectParamBean> getAllindexByProjectId(Integer projectId) {
        Example example = new Example(ProjectIndex.class);
        example.createCriteria().andEqualTo("projectId", projectId)
                .andEqualTo("isdelete", 0);
        List<ProjectIndex> list = projectIndexMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            return new ArrayList<>();
        }
        List<ProjectParamBean> paramList = new ArrayList<>();
        for (ProjectIndex i : list) {
            if (i.getIndexId() != null && i.getIndexId() != 0 && i.getIndexValue() == null) { //说明是参数节点，需要获取参数
                //文件参数
                List<IndexModelExt> fParamList = new ArrayList<IndexModelExt>();
                //常量参数
                List<IndexModelExt> cParamList = new ArrayList<IndexModelExt>();
                Example indexExample = new Example(IndexModel.class);
                indexExample.createCriteria().andEqualTo("indexId", i.getIndexId())
                        .andEqualTo("isdelete", 0);
                List<IndexModel> indexModelList = indexModelMapper.selectByExample(indexExample);
                for (IndexModel indexModel : indexModelList) {
                    IndexModelExt indexModelExt = new IndexModelExt();
                    BeanUtils.copyPropertiesIgnoreNull(indexModel, indexModelExt);
                    indexModelExt.setNodeId(i.getId());
                    if ("file".equals(indexModel.getNodeType())) {
                        getProjectParamValue(indexModelExt, projectId, indexModelExt.getIndexId(), "file", indexModelExt.getNodeId());
                        fParamList.add(indexModelExt);
                    } else if (indexModel.getNodeCategory() == Category.Constant.getValue()) {//说明是常量
                        cParamList.add(indexModelExt);
                        getProjectParamValue(indexModelExt, projectId, indexModelExt.getIndexId(), "constrant", indexModelExt.getNodeId());
                    }

                }
                ProjectParamBean bean = new ProjectParamBean();
                bean.setIndexName(i.getIndexName());
                bean.setIndexId(i.getIndexId());
                bean.setfParamList(fParamList);
                bean.setcParamList(cParamList);
                paramList.add(bean);
            }
        }
        return paramList;
    }


    /**
     * 获取项目参数
     *
     * @param projectId
     * @return
     */
    public List<ProjectParamBean> getAllProjectParamByProjectId(Integer projectId) {

        Example example = new Example(ProjectIndex.class);
        example.createCriteria().andEqualTo("projectId", projectId)
                .andEqualTo("isdelete", 0);
        List<ProjectIndex> list = projectIndexMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            return new ArrayList<>();
        }
        List<ProjectParamBean> paramList = new ArrayList<>();
        String rootName = "";
        for (ProjectIndex i : list) {
            if (i.getParentid() == 0) {
                rootName = i.getIndexName();
            }
            if (i.getIndexId() != null && i.getIndexId() != 0 && i.getIndexValue() == null) { //说明是参数节点，需要获取参数
                //文件参数
                List<IndexModelExt> fParamList = new ArrayList<IndexModelExt>();
                //常量参数
                List<IndexModelExt> cParamList = new ArrayList<IndexModelExt>();
                List<String> parenNameList = new ArrayList<>();
                Example indexExample = new Example(IndexModel.class);
                indexExample.createCriteria().andEqualTo("indexId", i.getIndexId())
                        .andEqualTo("isdelete", 0);
                List<IndexModel> indexModelList = indexModelMapper.selectByExample(indexExample);
                for (IndexModel indexModel : indexModelList) {
                    IndexModelExt indexModelExt = new IndexModelExt();
                    BeanUtils.copyPropertiesIgnoreNull(indexModel, indexModelExt);
                    if ("file".equals(indexModel.getNodeType())) {
                        getProjectParamValue(indexModelExt, projectId, i.getIndexId(), "file", i.getId());
                        fParamList.add(indexModelExt);
                    } else if (indexModel.getNodeCategory() == Category.Constant.getValue()) {//说明是常量
                        getProjectParamValue(indexModelExt, projectId, i.getIndexId(), "constrant", i.getId());
                        cParamList.add(indexModelExt);
                    }

                }
                ProjectParamBean bean = new ProjectParamBean();
                bean.setRandomNumber((int) (Math.random() * 100) + i.getParentid());
                bean.setIndexName(i.getIndexName());
                bean.setParentId(i.getParentid());
                bean.setIndexId(i.getIndexId());
                bean.setRootName(rootName);
                bean.setfParamList(fParamList);
                bean.setcParamList(cParamList);
                checkNodeIsFinish(fParamList, bean);
                if (bean.getIsFinishFlg() == 1) {
                    checkNodeIsFinish(cParamList, bean);
                }
                // 获取节点的父节点名称
                parenNameList = getparenIndexName(parenNameList, i.getParentid(), list);
                Collections.reverse(parenNameList);
                bean.setParentNameList(parenNameList);
                // 获取第二级节点id
                if (i.getIndexLevel() == 2) {
                    bean.setParentId(i.getId());
                } else {
                    getLevel2Id(list, i.getParentid(), bean);
                }
                if (bean.getParentId() == null) {
                    bean.setParentId(i.getParentid());
                }
                paramList.add(bean);
            }
        }
        return paramList;
    }

    private void getLevel2Id(List<ProjectIndex> list, Integer parentId, ProjectParamBean bean) {
        for (ProjectIndex projectIndex : list) {
            if (projectIndex.getId().equals(parentId)) {
                if (projectIndex.getIndexLevel() == 2) {
                    bean.setParentId(projectIndex.getId());
                    break;
                } else {
                    getLevel2Id(list, projectIndex.getParentid(), bean);
                }
            }
        }
    }

    /**
     * 获取叶子节点的所有父节点名称
     *
     * @param parenNameList
     * @param parentId
     * @param list
     * @return
     */
    public List<String> getparenIndexName(List<String> parenNameList, Integer parentId, List<ProjectIndex> list) {
        for (ProjectIndex projectIndex : list) {
            if (projectIndex.getId().equals(parentId)) {
                if (projectIndex.getParentid() == 0) {
                    break;
                }
                parenNameList.add(projectIndex.getIndexName());
                getparenIndexName(parenNameList, projectIndex.getParentid(), list);
            }
        }
        return parenNameList;
    }

    /**
     * 检查节点是否完成参数设置
     *
     * @param fParamList
     * @param bean
     */
    private void checkNodeIsFinish(List<IndexModelExt> fParamList, ProjectParamBean bean) {
        bean.setIsFinishFlg(1);
        if (fParamList.size() > 0) {
            for (IndexModelExt f : fParamList) {
                if (f.getConstValueOrFileName() != null || f.getProjectParamId() != null || f.getGroupTemplateData() != null) {
                    continue;
                } else {
                    bean.setIsFinishFlg(0);
                    break;
                }
            }
        }
    }

    /**
     * 获取节点选择的参数
     *
     * @param indexModelExt
     * @return
     */
    private IndexModelExt getProjectParamValue(IndexModelExt indexModelExt, Integer projectId, Integer indexId, String paramType, Integer nodeId) {
        Example example1 = new Example(ProjectParamTemplate.class);
        indexModelExt.setNodeId(nodeId);
        example1.createCriteria().andEqualTo("projectId", projectId)
                .andEqualTo("indexId", indexId)
                .andEqualTo("paramName", indexModelExt.getParamName())
                .andEqualTo("nodeId", nodeId)
                .andEqualTo("paramType", paramType);
        List<ProjectParamTemplate> projectParamTemplateList = projectParamTemplateMapper.selectByExample(example1);
        if (projectParamTemplateList.size() > 0) {
            ProjectParamTemplate projectParamTemplate = projectParamTemplateList.get(0);
            indexModelExt.setProjectParamId(projectParamTemplate.getProjectParamId());
            indexModelExt.setIsSelfDefined(projectParamTemplate.getIsSelfDefined());
            indexModelExt.setGroupTemplateData(projectParamTemplate.getGroupTemplateData());
            // 自定义常量或附件的数值和附件名称
            if ("file".equals(projectParamTemplate.getParamType()) && projectParamTemplate.getIsSelfDefined() != null && projectParamTemplate.getIsSelfDefined() == 1) {
                indexModelExt.setConstValueOrFileName(projectParamTemplate.getProjectParamName());
                indexModelExt.setFilePath(projectParamTemplate.getProjectParamName() + "_" + projectParamTemplate.getParamValue());
            }
            if ("constrant".equals(projectParamTemplate.getParamType()) && projectParamTemplate.getIsSelfDefined() != null && projectParamTemplate.getIsSelfDefined() == 1) {
                indexModelExt.setConstValueOrFileName(projectParamTemplate.getParamValue());
            }
        }
        return indexModelExt;
    }

    /**
     * 根据id修改节点名称
     *
     * @param params
     * @return
     */
    public void updateName(ProjectIndexParams params) {

        projectIndexMapper.updateName(params);
    }

    /**
     * 获取分组模板数据关联选择的特定变量的值
     *
     * @param json
     * @return
     */
    public ProjectBatchAssessTemplateParam getGroupTemplateData(JSONObject json) {
        // 1.获取分组参数的笛卡尔积
        // 原始项目参信息数
        JSONArray paramJa = json.getJSONArray("params");
        // 项目所选分组参数信息
        JSONArray groupArr = json.getJSONArray("groupParams");
        // 项目id
        Integer projectId = Integer.valueOf(json.getString("projectId"));
        // 加载分组数据所选择的某套数据值
        Integer groupParamSuitNumber = Integer.valueOf(json.getString("groupParamSuitNumber"));
        // 分组数据结果集
        List<ProjectIndexGroupTemplateParam> list = new ArrayList<>();

        // 获取项目数据关联所选的参数及分组信息
        Set<String> groupDataSet = new HashSet<>();
        Set<String> dataTemplateNumberSet = new HashSet<>();
        groupArr.stream().forEach(group -> {
            JSONObject jo = (JSONObject) group;
            groupDataSet.add(jo.getString("value"));
            dataTemplateNumberSet.add(jo.getString("value").substring(0, 1));

        });
        // 取分组数据
        List<String> dataTemplateNumberList = new ArrayList<>(dataTemplateNumberSet);
        list = GetGroupParamData(projectId, groupParamSuitNumber, list, groupDataSet);

        // 笛卡尔积返回的结果
        List<List<JSONObject>> recursiveResult = new ArrayList<>();
        Integer dataTemplateNumberListIndex = 0;
        // 参数拷贝
        JSONArray jsonArray = getJsonArray(paramJa);
        // 用来判断是否为循环到最后一个分组
        Boolean[] arr = new Boolean[]{false};
        // 数据解析 返回分组的笛卡尔积
        getResult(recursiveResult, paramJa, jsonArray, list, dataTemplateNumberList, dataTemplateNumberListIndex, arr);

        // 2.获取分组名称的笛卡尔积
        List<String> assessNameResult = getAssessNameResultLists(projectId, groupParamSuitNumber, dataTemplateNumberSet);
        ProjectBatchAssessTemplateParam result = new ProjectBatchAssessTemplateParam();
        result.setAssessParamResultLists(recursiveResult);
        result.setAssessNameResultLists(assessNameResult);
        return result;
    }

    /**
     * 获取分组名称的笛卡尔积
     *
     * @param projectId
     * @param groupParamSuitNumber
     * @param dataTemplateNumberSet
     * @return
     */
    private List<String> getAssessNameResultLists(Integer projectId, Integer groupParamSuitNumber, Set<String> dataTemplateNumberSet) {
        // 1.获取大分组数数据名称
        String groupDataName = projectParamTemplateValueMapper.getGroupDataName(projectId, groupParamSuitNumber);
        // 2.获取每个分组的
        List<List<String>> lists = new ArrayList<>();
        dataTemplateNumberSet.stream().forEach(s -> {
            List<String> dataNameList = new ArrayList<>();
            List<ProjectParamTemplateValue> projectParamTemplateValueList = projectParamTemplateValueMapper.listDataName(projectId, Integer.parseInt(s), groupParamSuitNumber);
            projectParamTemplateValueList.stream().forEach(projectParamTemplateValue -> dataNameList.add(projectParamTemplateValue.getDataName()));
            lists.add(dataNameList);
        });
        List<List<String>> dataNameResult = new ArrayList<>();
        DescartesUtil.getDescartesStringResult(lists, dataNameResult, 0, new ArrayList<String>());
        List<String> assessNameResult = new ArrayList<>();
        for (int i = 0; i < dataNameResult.size(); i++) {
            StringBuffer strBuilder = new StringBuffer();
            strBuilder.append("_").append(groupDataName).append("_");
            dataNameResult.get(i).stream().forEach(s -> strBuilder.append(s).append("_"));
            assessNameResult.add(strBuilder.toString().substring(0, strBuilder.length() - 1));
        }
        return assessNameResult;
    }

    /**
     * 获取分组数据
     *
     * @param projectId
     * @param groupParamSuitNumber
     * @param list
     * @param groupDataSet
     */
    private List<ProjectIndexGroupTemplateParam> GetGroupParamData(Integer projectId, Integer groupParamSuitNumber, List<ProjectIndexGroupTemplateParam> list, Set<String> groupDataSet) {
        groupDataSet.stream().forEach(s -> {
            String[] dataTemplateNumberAndProjectParamId = s.split("_");
            ProjectIndexGroupTemplateParam projectIndexGroupTemplateParam = new ProjectIndexGroupTemplateParam();
            projectIndexGroupTemplateParam.setKey(s);
            projectIndexGroupTemplateParam.setValue(getGroupTemplateValue(projectId, groupParamSuitNumber, Integer.valueOf(dataTemplateNumberAndProjectParamId[0]), Integer.valueOf(dataTemplateNumberAndProjectParamId[1])));
            list.add(projectIndexGroupTemplateParam);
        });
        return list;
    }

    /**
     * 参数拷贝
     *
     * @param paramJa
     * @return
     */
    private JSONArray getJsonArray(JSONArray paramJa) {
        JSONArray jsonArray = new JSONArray();
        for (int n = 0; n < paramJa.size(); n++) {
            jsonArray.add(paramJa.get(n));
        }
        return jsonArray;
    }

    /**
     * 递归获取参数的笛卡尔积
     *
     * @param recursiveResult
     * @param paramJa
     * @param list
     * @param dataTemplateNumberList
     * @param dataTemplateNumberListIndex
     * @return
     */
    private void getResult(List<List<JSONObject>> recursiveResult, JSONArray paramJa, JSONArray jsonArray, List<ProjectIndexGroupTemplateParam> list, List<String> dataTemplateNumberList, Integer dataTemplateNumberListIndex, Boolean[] arr) {
        // 单分组有几套数据就有几次数据评估
        if (dataTemplateNumberList.size() == 1) {
            for (int i = 0; i < list.get(0).getValue().split(",").length; i++) {
                jsonArray = getJsonArray(paramJa);
                assembleData(jsonArray, list, dataTemplateNumberList, i, dataTemplateNumberListIndex);
                recursiveResult.add(jsonArray);
            }
        } else if (dataTemplateNumberListIndex < dataTemplateNumberList.size()) {
            if (dataTemplateNumberListIndex == dataTemplateNumberList.size() - 1) {
                int count = getCount(list, dataTemplateNumberList, dataTemplateNumberListIndex);
                for (int m = 0; m < count; m++) {
                    JSONArray jsonArray1 = getJsonArray(jsonArray);
                    assembleData(jsonArray1, list, dataTemplateNumberList, m, dataTemplateNumberListIndex);
                    recursiveResult.add(jsonArray1);
                }
                arr[0] = true;

            } else {
                int count = getCount(list, dataTemplateNumberList, dataTemplateNumberListIndex);
                for (int m = 0; m < count; m++) {
                    if (arr[0] == true) {
                        // 这里需要替换掉之前的替换的变量
                        arr[0] = false;
                        jsonArray = getNewJsonArray(paramJa, jsonArray, dataTemplateNumberList, dataTemplateNumberListIndex);
                    }
                    assembleData(jsonArray, list, dataTemplateNumberList, m, dataTemplateNumberListIndex);
                    getResult(recursiveResult, paramJa, jsonArray, list, dataTemplateNumberList, dataTemplateNumberListIndex + 1, arr);
                }
            }
        }

    }

    /**
     * 获取每个分组的数据套数
     *
     * @param list
     * @param dataTemplateNumberList
     * @param dataTemplateNumberListIndex
     * @return
     */
    private int getCount(List<ProjectIndexGroupTemplateParam> list, List<String> dataTemplateNumberList, Integer dataTemplateNumberListIndex) {
        String[] groupDatas = new String[0];
        for (int f = 0; f < list.size(); f++) {
            if (dataTemplateNumberList.get(dataTemplateNumberListIndex).equals(list.get(f).getKey().substring(0, 1))) {
                groupDatas = list.get(f).getValue().split(",");
                break;
            }
        }
        return groupDatas.length;
    }

    /**
     * 获取新的参数JSONArray
     *
     * @param paramJa
     * @param jsonArray
     * @param dataTemplateNumberList
     * @param dataTemplateNumberListIndex
     * @return
     */
    private JSONArray getNewJsonArray(JSONArray paramJa, JSONArray jsonArray, List<String> dataTemplateNumberList, Integer dataTemplateNumberListIndex) {
        for (int k = 0; k < paramJa.size(); k++) {
            JSONObject jo = (JSONObject) paramJa.get(k);
            String value = jo.getString("value").substring(0, 1);
            String key = jo.getString("key");
            if (value.equals(dataTemplateNumberList.get(dataTemplateNumberListIndex))) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject temp = (JSONObject) jsonArray.get(i);
                    if (key.equals(temp.getString("key"))) {
                        jsonArray.discard(i);
                        jsonArray.add(i, jo);
                    }
                }
            }
            if (dataTemplateNumberListIndex == 0 || dataTemplateNumberListIndex == dataTemplateNumberList.size() - 1) {
                jsonArray = getJsonArray(paramJa);
            }
        }
        return jsonArray;
    }


    /**
     * 组装数据
     *
     * @param jsonArray
     * @param list
     * @param dataTemplateNumberList
     * @param dataIndex
     * @param dataTemplateNumberListIndex
     */
    private void assembleData(JSONArray jsonArray, List<ProjectIndexGroupTemplateParam> list, List<String> dataTemplateNumberList, Integer dataIndex, Integer dataTemplateNumberListIndex) {
        for (int k = 0; k < jsonArray.size(); k++) {
            JSONObject jo = (JSONObject) jsonArray.get(k);
            String key = jo.getString("key");
            if (jo.getString("value").substring(0, 1).equals(dataTemplateNumberList.get(dataTemplateNumberListIndex))) {
                for (int m = 0; m < list.size(); m++) {
                    if (jo.getString("value").equals(list.get(m).getKey())) {
                        String[] groupDatas = list.get(m).getValue().split(",");
                        jsonArray.discard(k);
                        Map<String, String> map = new HashMap<>(16);
                        map.put("value", groupDatas[dataIndex]);
                        map.put("key", key);
                        jsonArray.add(k, JSONObject.fromObject(map));
                    }
                }
            }
        }
    }

    /**
     * 获取分组数据
     *
     * @param projectId
     * @param groupParamSuitNumber
     * @return
     */
    private String getGroupTemplateValue(Integer projectId, Integer groupParamSuitNumber, Integer dataTemplateNumber, Integer projectParamId) {
        String groupTemplateValue = "";
        Example example = new Example(ProjectParamTemplateValue.class);
        example.createCriteria().andEqualTo("projectId", projectId)
                .andEqualTo("groupParamSuitNumber", groupParamSuitNumber)
                .andEqualTo("dataTemplateNumber", dataTemplateNumber)
                .andEqualTo("projectParamId", projectParamId);
        example.setOrderByClause("param_suit_number");
        List<ProjectParamTemplateValue> list = projectParamTemplateValueMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            return groupTemplateValue;
        }
        StringBuffer strBuilder = new StringBuffer();
        if ("file".equals(list.get(0).getParamType())) {
            list.stream().forEach(projectParamTemplateValue -> {
                String[] fileNameAndPath = projectParamTemplateValue.getParamValue().split("_");
                strBuilder.append(fileNameAndPath[0]).append(",");
            });
            groupTemplateValue = strBuilder.substring(0, strBuilder.length() - 1);
        } else {
            groupTemplateValue = list.stream().map(projectParamTemplateValue -> projectParamTemplateValue.getParamValue()).collect(Collectors.joining(","));
        }


        return groupTemplateValue;
    }
}
