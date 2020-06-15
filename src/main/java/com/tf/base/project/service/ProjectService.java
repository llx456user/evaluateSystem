package com.tf.base.project.service;

import com.tf.base.common.domain.*;
import com.tf.base.common.persistence.*;
import com.tf.base.common.service.BaseService;
import com.tf.base.common.utils.*;
import com.tf.base.platform.domain.ProjectParamTemplateValueParams;
import com.tf.base.project.domain.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ProjectService {
    @Autowired
    private ProjectInfoMapper projectInfoMapper;
    @Autowired
    private ProjectCategoryMapper projectCategoryMapper;

    @Autowired
    private BaseService baseService;
    @Autowired
    private AssessInfoMapper assessInfoMapper;

    @Autowired
    private IndexModelMapper indexModelMapper;

    @Autowired
    private ProjectParamMapper projectParamMapper;

    @Autowired
    private ProjectParamTemplateMapper projectParamTemplateMapper;

    @Autowired
    private ProjectParamTemplateValueMapper projectParamTemplateValueMapper;

    @Autowired
    private ProjectIndexMapper projectIndexMapper;

    public int queryCount(ProjectInfoParams params) {

        return projectInfoMapper.queryCount(params);
    }

    public int queryDataTemplaetCount(ProjectParamTemplateValueParams params) {
        return projectParamTemplateValueMapper.queryCount(params);
    }

    public List<ProjectParamTemplateValue> queryDataTemplaetList(ProjectParamTemplateValueParams params, int start) {
        List<ProjectParamTemplateValue> list = projectParamTemplateValueMapper.queryList(params, start);
        return list;
    }

    public List<ProjectParamTemplateValue> groupDataTemplateList(ProjectParamTemplateValueParams params) {
        List<ProjectParamTemplateValue> list = projectParamTemplateValueMapper.queryGroupDataTemplateList(params);
        return list;
    }

    public List<ProjectParamTemplateValue> listGroupDataTemplate(ProjectParamTemplateValueParams params) {
        List<ProjectParamTemplateValue> list = projectParamTemplateValueMapper.listGroupDataTemplate(params);
        return list;
    }

    public List<ProjectInfo> queryList(ProjectInfoParams params, int start) {

        List<ProjectInfo> list = projectInfoMapper.queryList(params, start);
        if (list != null) {
            for (ProjectInfo r : list) {
                r.setUpdateTimeStr(DateUtil.TimeToString(r.getUpdateTime()));
            }
        }
        return list;
    }

    public int saveCategory(String categoryName) {

        Integer userid = Integer.parseInt(baseService.getUserId());
        Date now = new Date();
        ProjectCategory mc = new ProjectCategory();
        mc.setCategoryName(categoryName);
        mc.setCreateTime(now);
        mc.setCreateUid(userid);
        mc.setIsdelete(0);
        mc.setUpdateTime(now);
        mc.setUpdateUid(userid);
        return projectCategoryMapper.insertSelective(mc);
    }

    public int updateCategory(String categoryId, String categoryName) {
        Integer userid = Integer.parseInt(baseService.getUserId());
        Date now = new Date();
        ProjectCategory mc = projectCategoryMapper.selectByPrimaryKey(categoryId);
        mc.setCategoryName(categoryName);
        mc.setUpdateTime(now);
        mc.setUpdateUid(userid);
        return projectCategoryMapper.updateByPrimaryKeySelective(mc);
    }

    public int deleteCategory(String categoryId) {
        Integer userid = Integer.parseInt(baseService.getUserId());
        Date now = new Date();
        ProjectCategory mc = projectCategoryMapper.selectByPrimaryKey(categoryId);
        mc.setIsdelete(1);
        mc.setUpdateTime(now);
        mc.setUpdateUid(userid);
        //删除模型分类
        int i = projectCategoryMapper.updateByPrimaryKeySelective(mc);
        if (i == 1) {
            //删除模型
            projectInfoMapper.updateIsdelete(Integer.parseInt(categoryId));
        }
        return i;
    }

    public int saveProjectInfo(JSONObject reqJson) {
        ProjectInfo projectInfo = JSONUtil.toObject(reqJson.toString(), ProjectInfo.class);

        int ret = 0;
        Integer userid = Integer.parseInt(baseService.getUserId());
        Date now = new Date();

        if (projectInfo.getId() == null) { //insert
            projectInfo.setIsdelete(0);
            projectInfo.setCreateTime(now);
            projectInfo.setCreateUid(userid);
            projectInfo.setUpdateTime(now);

            ret = projectInfoMapper.insertSelective(projectInfo);
            ret = projectInfo.getId();
        } else {//update
            projectInfo.setUpdateTime(now);
            projectInfo.setUpdateUid(userid);

            ret = projectInfoMapper.updateByPrimaryKeySelective(projectInfo);
            ret = projectInfo.getId();
        }
        return ret;

    }

    /**
     * 保存项目选择的模板id
     *
     * @param reqJson
     * @return
     */
    public int saveProjectTemplate(JSONObject reqJson) {
        ProjectInfo projectInfo = JSONUtil.toObject(reqJson.toString(), ProjectInfo.class);
        int ret = 0;
        Integer userid = Integer.parseInt(baseService.getUserId());
        Date now = new Date();
        projectInfo.setUpdateTime(now);
        projectInfo.setUpdateUid(userid);

        ret = projectInfoMapper.updateByPrimaryKeySelective(projectInfo);
        ret = projectInfo.getId();
        return ret;
    }

    public int deleteProjectInfo(String projectInfoId) {
        Integer userid = Integer.parseInt(baseService.getUserId());
        Date now = new Date();
        ProjectInfo project = projectInfoMapper.selectByPrimaryKey(projectInfoId);
        project.setIsdelete(1);
        project.setUpdateTime(now);
        project.setUpdateUid(userid);
        //删除模型分类
        return projectInfoMapper.updateByPrimaryKeySelective(project);
    }


    /**
     * 评估后修改项目时间
     */
    public void updataProjectTime(String assessId) {

        AssessInfo assessInfo = assessInfoMapper.selectByPrimaryKey(assessId);

        Example example = new Example(ProjectInfo.class);
        example.createCriteria().andEqualTo("id", assessInfo.getProjectId());
        Date projectData = new Date();
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setUpdateTime(projectData);
//		projectInfoMapper.updateByExample(projectInfo,example);
        projectInfoMapper.updateByExampleSelective(projectInfo, example);

    }


    //根据项目ID获取项目指标
    public List<ProjectLabel> getProjectLabels(Integer projectId) {
        List<ProjectLabel> projectLabelList = new ArrayList<>();
        projectLabelList.add(getDesLabel());//增加项目描述信息
        projectLabelList.add(getIndexSerLabel());//增加项目
        projectLabelList.add(getAccessResultLabel());//增加项目
        projectLabelList.addAll(getIndexOutLabel(projectId));    //增加指标
        return projectLabelList;
    }

    private ProjectLabel getDesLabel() {
        ProjectLabel desLabel = new ProjectLabel();
        desLabel.setLabel("desc");
        desLabel.setType(ProjectLabel.desc);
        desLabel.setDescription("项目描述");
        typeToName(desLabel);
        return desLabel;
    }

    private ProjectLabel getIndexSerLabel() {
        ProjectLabel indexSerLabel = new ProjectLabel();
        indexSerLabel.setLabel("indexSer");
        indexSerLabel.setType(ProjectLabel.indexSer);
        indexSerLabel.setDescription("指标体系");
        typeToName(indexSerLabel);
        return indexSerLabel;
    }

    //
    private ProjectLabel getAccessResultLabel() {
        ProjectLabel accessResultLabel = new ProjectLabel();
        accessResultLabel.setLabel("accessResult");
        accessResultLabel.setType(ProjectLabel.accessResult);
        accessResultLabel.setDescription("评估结果");
        typeToName(accessResultLabel);
        return accessResultLabel;
    }

    /**
     * 获取节点的输出指标
     *
     * @param projectId
     * @return
     */
    private List<ProjectLabel> getIndexOutLabel(int projectId) {
        List<ProjectLabel> projectLabels = new ArrayList<>();

        // 获取根节点名称
        String rootName = "";
        Example example = new Example(ProjectIndex.class);
        example.createCriteria().andEqualTo("projectId", projectId)
                .andEqualTo("isdelete", 0);
        List<ProjectIndex> projectIndexList = projectIndexMapper.selectByExample(example);
        if (projectIndexList.size() > 0) {
            for (ProjectIndex projectIndex : projectIndexList) {
                if (projectIndex.getParentid() == 0) {
                    rootName = projectIndex.getIndexName();
                    break;
                }
            }
        }
        List<IndexModel> list = indexModelMapper.selectLabelByProjectId(projectId);
        for (IndexModel indexModel : list) {
            ProjectLabel label = new ProjectLabel();
            label.setLabel(indexModel.getId() + "_" + indexModel.getIndexId() + "_" + indexModel.getNodeKey());//项目节点id+指标ID+nodeId
            String labelName = indexModel.getProjectNodeName() + "_" + indexModel.getIndexName() + "_" + indexModel.getNodeText();
            if (!StringUtil.isEmpty(indexModel.getPictureTitle())) {
                labelName = labelName + "_" + indexModel.getPictureTitle();
            }
            label.setDescription(labelName);
            if (indexModel.getNodeCategory() == 2) {//图形
                label.setType(5);
            } else if (indexModel.getNodeCategory() == 5) {//表格
                label.setType(6);
            } else if (indexModel.getNodeCategory() == 7) {//字符串
                label.setType(7);
            }
            label.setNodeId(indexModel.getId());
            label.setRootName(rootName);
            label.setNodeName(indexModel.getProjectNodeName());
            typeToName(label);
            List<String> parenNameList = new ArrayList<>();
            for (ProjectIndex projectIndex : projectIndexList) {
                if (projectIndex.getId().equals(indexModel.getId())) {
                    // 获取节点的父节点名称
                    parenNameList = getparenIndexName(parenNameList, projectIndex.getParentid(), projectIndexList);
                    Collections.reverse(parenNameList);
                    label.setParentNameList(parenNameList);
                    // 获取第二级节点id
                    if (projectIndex.getIndexLevel() == 2) {
                        label.setParentLevelTwoId(projectIndex.getId());
                    } else {
                        getLevel2Id(projectIndexList, projectIndex.getParentid(), label);
                    }
                    if (label.getParentLevelTwoId() == null) {
                        label.setParentLevelTwoId(projectIndex.getParentid());
                    }
                }
            }
            projectLabels.add(label);
        }
        return getNodeIndexLabels(projectLabels);
    }

    private List<ProjectLabel> getNodeIndexLabels(List<ProjectLabel> projectLabels) {
        List<ProjectLabel> projectLabelList = new ArrayList<>();
        Set<String> set = new TreeSet<>();
        for (ProjectLabel projectLabel : projectLabels) {
            if (!set.contains(projectLabel.getNodeName())) {
                set.add(projectLabel.getNodeName());
            }
        }
        for (String nodeName : set) {
            List<ProjectLabel> temp = new ArrayList<>();
            for (ProjectLabel projectLabel : projectLabels) {
                if (projectLabel.getNodeName().equals(nodeName)) {
                    temp.add(projectLabel);
                }
            }
            ProjectLabel projectLabel = new ProjectLabel();
            ProjectLabel tempLabel = temp.get(0);
            projectLabel.setRootName(tempLabel.getRootName());
            projectLabel.setParentLevelTwoId(tempLabel.getParentLevelTwoId());
            projectLabel.setParentNameList(tempLabel.getParentNameList());
            projectLabel.setNodeName(tempLabel.getNodeName());
            projectLabel.setNodeIndexList(temp);
            projectLabelList.add(projectLabel);
        }
        return projectLabelList;
    }

    private void getLevel2Id(List<ProjectIndex> list, Integer parentId, ProjectLabel bean) {
        for (ProjectIndex projectIndex : list) {
            if (projectIndex.getId().equals(parentId)) {
                if (projectIndex.getIndexLevel() == 2) {
                    bean.setParentLevelTwoId(projectIndex.getId());
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
    private List<String> getparenIndexName(List<String> parenNameList, Integer parentId, List<ProjectIndex> list) {
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
     * 类型转换
     *
     * @param projectLabel
     * @return
     */
    private ProjectLabel typeToName(ProjectLabel projectLabel) {
        switch (projectLabel.getType()) {
            case 1:
                projectLabel.setTypeName("文本");
                break;
            case 2:
            case 3:
                projectLabel.setTypeName("树");
                break;
            case 4:
                projectLabel.setTypeName("数值");
                break;
            case 5:
                projectLabel.setTypeName("图片");
                break;
            case 6:
                projectLabel.setTypeName("表格");
                break;
            default:
                projectLabel.setTypeName("文本");
        }
        return projectLabel;
    }

    /**
     * 根据项目Id 查询参数集合
     *
     * @param projectId
     * @return
     */
    public Map<String, List<ProjectParam>> getProjectParam(Integer projectId) {
        Map<String, List<ProjectParam>> map = new HashMap<>();
        Example example = new Example(ProjectParam.class);
        example.createCriteria()
                .andEqualTo("projectId", projectId);
        List<ProjectParam> list = projectParamMapper.selectByExample(example);
        getConstAndFileParam(map, list);
        return map;
    }

    private void getConstAndFileParam(Map<String, List<ProjectParam>> map, List<ProjectParam> list) {
        //入参
        List<ProjectParam> inParList = new ArrayList<>();
        //出参
        List<ProjectParam> outParList = new ArrayList<>();
        if (list.size() > 0) {
            for (ProjectParam m : list) {
                if ("constrant".equals(m.getParamType())) {
                    inParList.add(m);
                } else if ("file".equals(m.getParamType())) {//出参
                    outParList.add(m);
                }
            }
        }

        map.put("inParList", inParList);
        map.put("outParList", outParList);
    }

    public Map<String, List<ProjectParam>> getGroupProjectParam(Integer projectId, Integer dataTemplateNumber) {
        Map<String, List<ProjectParam>> map = new HashMap<>();
        Example example = new Example(ProjectParam.class);
        example.createCriteria()
                .andEqualTo("projectId", projectId)
                .andEqualTo("dataTemplateNumber", dataTemplateNumber);
        List<ProjectParam> list = projectParamMapper.selectByExample(example);
        getConstAndFileParam(map, list);
        return map;
    }

    /**
     * 查询数据模板分组数
     *
     * @param projectId
     * @return
     */
    public Boolean isGroupTemplate(Integer projectId) {
        Integer count = projectParamMapper.count(projectId);
        return count > 0;
    }

    /**
     * 获取数据分组模板数据
     *
     * @param projectId
     * @return
     */
    public List<ProjectGroupParamBean> getGroupParam(Integer projectId) {
        List<ProjectGroupParamBean> list = new ArrayList<>();
        List<ProjectParam> projectParamList = projectParamMapper.list(projectId);
        Set<Integer> set = new HashSet<>();
        projectParamList.stream().forEach(projectParam -> set.add(projectParam.getDataTemplateNumber()));

        set.stream().forEach(num -> {
            ProjectGroupParamBean projectGroupParamBean = new ProjectGroupParamBean();
            // 常量
            List<ProjectParam> cParList = new ArrayList<>();
            // 附件
            List<ProjectParam> fParList = new ArrayList<>();
            projectParamList.stream()
                    .filter(projectParam -> projectParam.getDataTemplateNumber().equals(num))
                    .forEach(projectParam -> {
                        if ("constrant".equals(projectParam.getParamType())) {
                            cParList.add(projectParam);
                        } else if ("file".equals(projectParam.getParamType())) {
                            fParList.add(projectParam);
                        }
                        projectGroupParamBean.setDataTemplateNumber(projectParam.getDataTemplateNumber());
                        projectGroupParamBean.setDataTemplateName(projectParam.getDataTemplateName());
                    });
            projectGroupParamBean.setcParamList(cParList);
            projectGroupParamBean.setfParamList(fParList);
            list.add(projectGroupParamBean);
        });
        return list;
    }

    /**
     * 根据项目Id 项目参数套数 查询参数集合
     *
     * @param projectId
     * @param paramSuitNumber
     * @param dataTemplateNumber
     * @return
     */
    public Map<String, List<ProjectParamTemplateValueExt>> getProjectParamTemplateValue(Integer projectId, Integer paramSuitNumber, Integer dataTemplateNumber, Integer groupDataTemplateNumber) {
        Map<String, List<ProjectParamTemplateValueExt>> map = new HashMap<>(16);
        Example example = new Example(ProjectParamTemplateValue.class);
        if (dataTemplateNumber == null) {
            example.createCriteria()
                    .andEqualTo("projectId", projectId)
                    .andEqualTo("paramSuitNumber", paramSuitNumber);
        } else {
            example.createCriteria()
                    .andEqualTo("projectId", projectId)
                    .andEqualTo("paramSuitNumber", paramSuitNumber)
                    .andEqualTo("groupParamSuitNumber", groupDataTemplateNumber)
                    .andEqualTo("dataTemplateNumber", dataTemplateNumber);
        }
        List<ProjectParamTemplateValue> list = projectParamTemplateValueMapper.selectByExample(example);
        //入参
        List<ProjectParamTemplateValueExt> inParList = new ArrayList<>();
        //出参
        List<ProjectParamTemplateValueExt> outParList = new ArrayList<>();
        if (list.size() > 0) {
            for (ProjectParamTemplateValue m : list) {
                ProjectParamTemplateValueExt ext = new ProjectParamTemplateValueExt();
                BeanUtils.copyPropertiesIgnoreNull(m, ext);
                ProjectParam projectParam = projectParamMapper.selectByPrimaryKey(m.getProjectParamId());
                if (projectParam != null) {
                    ext.setParamName(projectParam.getParamName());
                    if ("constrant".equals(m.getParamType())) {
                        inParList.add(ext);
                    } else if ("file".equals(m.getParamType())) {//出参
                        ext.setFileValueAndName(m.getParamValue());
                        ext.setParamValue(m.getParamValue().split("_")[1]);
                        outParList.add(ext);
                    }
                }
            }
        }
        // 判断模板是否增加了新的模板
        Example example1 = new Example(ProjectParam.class);
        if (dataTemplateNumber == null) {
            example1.createCriteria()
                    .andEqualTo("projectId", projectId);
        } else {
            example1.createCriteria()
                    .andEqualTo("projectId", projectId)
                    .andEqualTo("dataTemplateNumber", dataTemplateNumber);
        }

        List<ProjectParam> projectParamList = projectParamMapper.selectByExample(example1);
//        if (projectParamList.size() > list.size()){
        Set<Integer> idSet = new HashSet<>();
        for (ProjectParamTemplateValue item : list) {
            idSet.add(item.getProjectParamId());
        }
        for (ProjectParam projectParam : projectParamList) {
            if (!idSet.contains(projectParam.getId())) {
                // 不包含说明新增了模板
                ProjectParamTemplateValueExt inserParam = new ProjectParamTemplateValueExt();
                BeanUtils.copyPropertiesIgnoreNull(projectParam, inserParam);
                inserParam.setProjectParamId(projectParam.getId());
//                     inserParam.setId(null);
                inserParam.setParamSuitNumber(paramSuitNumber);
                if ("file".equals(projectParam.getParamType())) {
                    outParList.add(inserParam);
                } else {
                    inParList.add(inserParam);
                }
            }
        }
//        }
        map.put("inParList", inParList);
        map.put("outParList", outParList);
        return map;
    }


    /**
     * 保存项目参数模板选择的常量和附件
     *
     * @param json
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int saveProjectParamTemplate(JSONObject json) {
        int ret = 0;
//        Integer nodeId = Integer.valueOf(json.get("nodeId").toString());
        Integer projectId = Integer.valueOf((String) json.get("projectId"));
        String isGroupTemplate = (String) json.get("isGroupTemplate");

        JSONArray nodeArr = json.getJSONArray("nodeId");
        JSONArray paramJa = json.getJSONArray("params");
        ret = getRet(ret, nodeArr, projectId, paramJa, isGroupTemplate);
        return ret;
    }

    /**
     * 保存数据模板分组模板
     *
     * @param json
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int saveGroupTemplate(JSONObject json) {
        int ret = 0;
        Integer projectId = Integer.valueOf((String) json.get("projectId"));

        JSONArray groupTemplate = json.getJSONArray("params");
        for (int i = 0; i < groupTemplate.size(); i++) {
            JSONArray jsonArray = (JSONArray) groupTemplate.get(i);
            if (jsonArray.size() < 0) {
                continue;
            }
            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
            // 项目数据模板分组数
            int dataTemplateNumber = Integer.parseInt(jsonObject.getString("dataTemplateNumber"));
            // 查询该项目数据模板分组数旧数据
            List<ProjectParam> projectParamList = getGroupNumberTemplateInfo(projectId, dataTemplateNumber);
            Set<Integer> idSet = new HashSet<>();
            projectParamList.stream().forEach(projectParam -> idSet.add(projectParam.getId()));
            // 对新的的分组模板数据进行处理(新增、删除、更新)
            for (int j = 0; j < jsonArray.size(); j++) {
                ProjectParam newProjectParam = JSONUtil.toObject(jsonArray.get(j).toString(), ProjectParam.class);
                ProjectParam saveProjectParam = new ProjectParam();
                BeanUtils.copyPropertiesIgnoreNull(newProjectParam, saveProjectParam);
                System.out.println(newProjectParam.getId());
                if (newProjectParam.getId() == null) {
                    // 新增
                    saveProjectParam.setProjectId(projectId);
                    ret += projectParamMapper.insert(saveProjectParam);
                    continue;
                }
                // 更新
                if (idSet.contains(newProjectParam.getId())) {
                    ret += projectParamMapper.updateByPrimaryKeySelective(newProjectParam);
                    idSet.remove(newProjectParam.getId());
                }
            }
            if (idSet.size() > 0) {
                idSet.forEach(integer -> projectParamMapper.deleteByPrimaryKey(integer));
            }
        }
        return ret;
    }

    /**
     * 查询该项目旧分组数据
     *
     * @param projectId
     * @param dataTemplateNumber
     */
    private List<ProjectParam> getGroupNumberTemplateInfo(Integer projectId, int dataTemplateNumber) {
        Example example = new Example(ProjectParam.class);
        example.createCriteria().andEqualTo("projectId", projectId)
                .andEqualTo("dataTemplateNumber", dataTemplateNumber);
        List<ProjectParam> projectParamList = projectParamMapper.selectByExample(example);
        return projectParamList;
    }

    /**
     * 数据关联保存数据--防止高并发
     *
     * @param ret
     * @param nodeArr
     * @param projectId
     * @param paramJa
     * @return
     */
    public int getRet(int ret, JSONArray nodeArr, Integer projectId, JSONArray paramJa, String isGroupTemplate) {
        for (int i = 0; i < nodeArr.size(); i++) {
            JSONObject nodeJo = (JSONObject) nodeArr.get(i);
            Integer nodeId = Integer.valueOf(nodeJo.getString("nodeId"));
            // 判断项目是否设置过参数
            Example example = new Example(ProjectParamTemplate.class);
            example.createCriteria().andEqualTo("projectId", projectId)
                    .andEqualTo("nodeId", nodeId);
            List<ProjectParamTemplate> list = projectParamTemplateMapper.selectByExample(example);
            if (list.size() > 0) {
//            projectParamTemplateMapper.deleteByExample(example);
                for (ProjectParamTemplate item : list) {
                    ProjectParamTemplate projectParamTemplate = new ProjectParamTemplate();
                    projectParamTemplate.setId(item.getId());
                    projectParamTemplateMapper.deleteByPrimaryKey(projectParamTemplate);
                }
            }
        }

        // 保存参数模板选择值
        for (int i = 0; i < paramJa.size(); i++) {
            JSONObject jo = (JSONObject) paramJa.get(i);
            ProjectParamTemplate projectParamTemplate = new ProjectParamTemplate();
            projectParamTemplate.setProjectId(projectId);
            String nameAndNodeKey = jo.getString("key");
            // 分割id 说明： 0_测试_189_125 --- nodeKey_paramName_nodeId_indexId
            String[] arr = nameAndNodeKey.split("_");
            // 自定义选择
            if (jo.getString("isChooseSelf").equals("1")) {
                // 附件
                if (jo.getString("value").indexOf("\\") != -1) {
                    // 附件
                    projectParamTemplate.setParamType("file");
                    String fileNameAndPath = jo.getString("value");
                    // 自定义上传的附件和附件路径
                    String[] fileNameAndPathArr = fileNameAndPath.split("_");
                    // 自定义选择的文件路径
                    projectParamTemplate.setParamValue(fileNameAndPathArr[1]);
                    // 自定义选择的附件名称
                    projectParamTemplate.setProjectParamName(fileNameAndPathArr[0].trim());
                } else {
                    // 参数类型
                    projectParamTemplate.setParamType("constrant");
                    // 常量输入值
                    projectParamTemplate.setParamValue(jo.getString("value").trim());
                }
            } else {
                if ("true".equals(isGroupTemplate)) {
                    projectParamTemplate.setParamType(arr[4].trim());
                    // 参数选择的名称
                    projectParamTemplate.setProjectParamName(jo.getString("name").trim().substring(4));
                    // 参数选择的附件ID
                    projectParamTemplate.setGroupTemplateData(jo.getString("value").trim());
                } else {
                    // 参数选择
                    ProjectParam projectParam = new ProjectParam();
                    projectParam.setId(Integer.valueOf(jo.getString("value")));
                    projectParam = projectParamMapper.selectByPrimaryKey(projectParam);
                    // 参数选择的类型
                    if ("file".equals(projectParam.getParamType())) {
                        projectParamTemplate.setParamType(projectParam.getParamType());
                    } else {
                        projectParamTemplate.setParamType("constrant");
                    }
                    // 参数选择的值
                    projectParamTemplate.setParamValue(projectParam.getParamValue());
                    // 参数选择的名称
                    projectParamTemplate.setProjectParamName(projectParam.getParamName());
                    // 参数选择的附件ID
                    projectParamTemplate.setProjectParamId(Integer.valueOf(jo.getString("value").trim()));
                }
            }
            // 是否自定义选择flag
            projectParamTemplate.setIsSelfDefined(Integer.valueOf(jo.getString("isChooseSelf")));
            // nodeKey
            projectParamTemplate.setNodeKey(Integer.valueOf(arr[0].trim()));
            // 节点参数名称
            projectParamTemplate.setParamName(arr[1].trim());
            // nodeId
            projectParamTemplate.setNodeId(Integer.valueOf(arr[2].trim()));
            // indexId
            projectParamTemplate.setIndexId(Integer.valueOf(arr[3].trim()));
            ret += projectParamTemplateMapper.insertSelective(projectParamTemplate);
        }
        return ret;
    }

    /**
     * 保存项目参数
     *
     * @param reqJson
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int saveProjectParam(JSONObject reqJson) {
        int ret = 0;
        ProjectParam projectParam = JSONUtil.toObject(reqJson.toString(), ProjectParam.class);
        JSONArray inArray = JSONArray.fromObject(reqJson.get("iptVarJson"));
        JSONArray outArray = JSONArray.fromObject(reqJson.get("optVarJson"));
        if (!checkParmeterName(inArray, outArray)) {
            return 401;
        }
        // 判断项目是否存在参数
        Example example = new Example(ProjectParam.class);
        example.createCriteria().andEqualTo("projectId", projectParam.getProjectId());
        List<ProjectParam> projectParamList = projectParamMapper.selectByExample(example);

        List<ProjectParam> inParmentList = saveParamter(projectParam, inArray, "constrant");
        List<ProjectParam> outParmentList = saveParamter(projectParam, outArray, "file");
        ret = inParmentList.size() + outParmentList.size();
        // 如果之前的项目模板数量大于现在的数量  说明删除了数据，这个时候需要找到被删除的那条并且删除
        if (projectParamList.size() > ret) {
            List<ProjectParam> allExisitList = new ArrayList<>();
            allExisitList.addAll(inParmentList);
            allExisitList.addAll(outParmentList);
            Set<Integer> idSet = new HashSet<>();
            for (ProjectParam now : allExisitList) {
                idSet.add(now.getId());
            }
            for (ProjectParam before : projectParamList) {
                if (!idSet.contains(before.getId())) {
                    projectParamMapper.deleteByPrimaryKey(before);
                }
            }
        }

        return ret;
    }

    /**
     * 保存新增的项目分组参数
     *
     * @param reqJson
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int saveAddGroupProjectParam(JSONObject reqJson) {
        int ret = 0;
        ProjectParam projectParam = JSONUtil.toObject(reqJson.toString(), ProjectParam.class);
        JSONArray inArray = JSONArray.fromObject(reqJson.get("iptVarJson"));
        JSONArray outArray = JSONArray.fromObject(reqJson.get("optVarJson"));
        if (!checkParmeterName(inArray, outArray)) {
            return 401;
        }
        int maxDataTemplateNumber = projectParamMapper.queryMaxSuitCount(projectParam.getProjectId());
        List<ProjectParam> inParmentList = saveGroupTemplateParam(projectParam, inArray, "constrant", maxDataTemplateNumber + 1);
        List<ProjectParam> outParmentList = saveGroupTemplateParam(projectParam, outArray, "file", maxDataTemplateNumber + 1);
        ret = inParmentList.size() + outParmentList.size();
        return ret;
    }

    /**
     * 保存分组数据模板
     *
     * @param projectParam
     * @param paramterArray
     * @param paramType
     * @param dataTemplateNumber
     * @return
     */
    private List<ProjectParam> saveGroupTemplateParam(ProjectParam projectParam, JSONArray paramterArray, String paramType, int dataTemplateNumber) {
        List<ProjectParam> list = new ArrayList<ProjectParam>();

        for (int i = 0; i < paramterArray.size(); i++) {
            JSONObject inJo = (JSONObject) paramterArray.get(i);
            ProjectParam inParamter = JSONUtil.toObject(inJo.toString(), ProjectParam.class);
            inParamter.setProjectId(projectParam.getProjectId());
            inParamter.setDataTemplateName(projectParam.getDataTemplateName());
            inParamter.setDataTemplateNumber(dataTemplateNumber);
            inParamter.setParamType(paramType);
            if (inParamter.getId() != null && !"".equals(inParamter.getId())) {
                projectParamMapper.updateByPrimaryKeySelective(inParamter);
            } else {
                projectParamMapper.insertSelective(inParamter);
            }
            list.add(inParamter);
        }
        return list;
    }

    /**
     * 保存数据模板
     *
     * @param projectParam
     * @param paramterArray
     * @param paramType
     * @return
     */
    private List<ProjectParam> saveParamter(ProjectParam projectParam, JSONArray paramterArray, String paramType) {
        List<ProjectParam> list = new ArrayList<ProjectParam>();

        for (int i = 0; i < paramterArray.size(); i++) {
            JSONObject inJo = (JSONObject) paramterArray.get(i);
            ProjectParam inParamter = JSONUtil.toObject(inJo.toString(), ProjectParam.class);
            inParamter.setProjectId(projectParam.getProjectId());
            inParamter.setParamType(paramType);
            if (inParamter.getId() != null && !"".equals(inParamter.getId())) {
                projectParamMapper.updateByPrimaryKeySelective(inParamter);
            } else {
                projectParamMapper.insertSelective(inParamter);
            }
            list.add(inParamter);
        }
        return list;
    }

    /**
     * 保存编辑后的数据模板
     *
     * @param reqJson
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int editSetProjectParam(JSONObject reqJson) {
        int ret = 0;
        ProjectParamTemplateValue projectParamTemplateValue = JSONUtil.toObject(reqJson.toString(), ProjectParamTemplateValue.class);
        JSONArray inArray = JSONArray.fromObject(reqJson.get("iptVarJson"));
        JSONArray outArray = JSONArray.fromObject(reqJson.get("optVarJson"));
        updateProjectTemplateValue(projectParamTemplateValue, inArray, "constrant");
        updateProjectTemplateValue(projectParamTemplateValue, outArray, "file");
        // 此段代码是为了设置删除了模板后新编辑了数据后数据名称和数据备注不一样造成的户型两条数据
        updateTemplateValue(projectParamTemplateValue);

        ret = inArray.size() + outArray.size();
        return ret;
    }

    /**
     * 更新数据
     *
     * @param projectParamTemplateValue
     */
    private void updateTemplateValue(ProjectParamTemplateValue projectParamTemplateValue) {
        Example example = new Example(ProjectParamTemplateValue.class);
        if (projectParamTemplateValue.getDataTemplateNumber() == null) {
            example.createCriteria().andEqualTo("projectId", projectParamTemplateValue.getProjectId())
                    .andEqualTo("paramSuitNumber", projectParamTemplateValue.getParamSuitNumber());
        } else {
            example.createCriteria().andEqualTo("projectId", projectParamTemplateValue.getProjectId())
                    .andEqualTo("paramSuitNumber", projectParamTemplateValue.getParamSuitNumber())
                    .andEqualTo("dataTemplateNumber", projectParamTemplateValue.getDataTemplateNumber())
                    .andEqualTo("groupParamSuitNumber", projectParamTemplateValue.getGroupParamSuitNumber());
        }

        ProjectParamTemplateValue update = new ProjectParamTemplateValue();
        update.setDataName(projectParamTemplateValue.getDataName());
        update.setDataComment(projectParamTemplateValue.getDataComment());
        projectParamTemplateValueMapper.updateByExampleSelective(update, example);
    }


    /**
     * -
     * 更新编辑后的数据模板
     *
     * @param projectParamTemplateValue
     * @param paramterArray
     * @param paramType
     * @return
     */
    private void updateProjectTemplateValue(ProjectParamTemplateValue projectParamTemplateValue, JSONArray paramterArray, String paramType) {
        for (int i = 0; i < paramterArray.size(); i++) {
            JSONObject inJo = (JSONObject) paramterArray.get(i);
            projectParamTemplateValue.setId(Integer.valueOf(inJo.get("id").toString()));
            projectParamTemplateValue.setParamValue(inJo.get("paramValue").toString());
            projectParamTemplateValue.setProjectParamId(Integer.valueOf(inJo.get("projectParamId").toString()));
            projectParamTemplateValue.setParamType(paramType);
            ProjectParamTemplateValue update = projectParamTemplateValueMapper.selectByPrimaryKey(projectParamTemplateValue);
            // 更新
            if (update != null) {
                BeanUtils.copyPropertiesIgnoreNull(projectParamTemplateValue, update);
                projectParamTemplateValueMapper.updateByPrimaryKeySelective(update);
            } else {
                // 新增
                projectParamTemplateValue.setProjectParamId(Integer.valueOf(inJo.get("projectParamId").toString()));
                projectParamTemplateValue.setId(null);
                projectParamTemplateValue.setParamType(paramType);
                projectParamTemplateValueMapper.insertSelective(projectParamTemplateValue);
            }

        }
    }

    /**
     * 保存项目参数
     *
     * @param reqJson
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int saveSetProjectParam(JSONObject reqJson) {
        int ret = 0;
        ProjectParamTemplateValue projectParamTemplateValue = JSONUtil.toObject(reqJson.toString(), ProjectParamTemplateValue.class);
        JSONArray inArray = JSONArray.fromObject(reqJson.get("iptVarJson"));
        JSONArray outArray = JSONArray.fromObject(reqJson.get("optVarJson"));

        // 判断项目中模板最大的套数
        int maxSuitNumber = projectParamTemplateValueMapper.queryMaxSuitCount(projectParamTemplateValue.getProjectId());

        List<ProjectParamTemplateValue> inParmentList = saveSetParamter(projectParamTemplateValue, inArray, "constrant", maxSuitNumber + 1);
        List<ProjectParamTemplateValue> outParmentList = saveSetParamter(projectParamTemplateValue, outArray, "file", maxSuitNumber + 1);
        ret = inParmentList.size() + outParmentList.size();
        return ret;
    }

    /**
     * 保存分组数据模板设置的数据
     *
     * @param reqJson
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int saveGroupSetProjectParam(JSONObject reqJson) {
        int ret = 0;
        ProjectParamTemplateValue projectParamTemplateValue = JSONUtil.toObject(reqJson.toString(), ProjectParamTemplateValue.class);
        JSONArray inArray = JSONArray.fromObject(reqJson.get("iptVarJson"));
        JSONArray outArray = JSONArray.fromObject(reqJson.get("optVarJson"));
        // 判断项目中模板最大的套数
        int maxSuitNumber = projectParamTemplateValueMapper.queryMaxDataTemplateSuitCount(projectParamTemplateValue.getProjectId(), projectParamTemplateValue.getDataTemplateNumber(), projectParamTemplateValue.getGroupParamSuitNumber());
        inArray.stream().forEach(cf -> {
            ProjectParamTemplateValue insetParam = new ProjectParamTemplateValue();
            BeanUtils.copyPropertiesIgnoreNull(JSONUtil.toObject(cf.toString(), ProjectParamTemplateValue.class), insetParam);
            BeanUtils.copyPropertiesIgnoreNull(projectParamTemplateValue, insetParam);
            insetParam.setParamSuitNumber(maxSuitNumber + 1);
            insetParam.setParamType("constrant");
            projectParamTemplateValueMapper.insertSelective(insetParam);
        });
        outArray.stream().forEach(ff -> {
            ProjectParamTemplateValue insetParam = new ProjectParamTemplateValue();
            BeanUtils.copyPropertiesIgnoreNull(JSONUtil.toObject(ff.toString(), ProjectParamTemplateValue.class), insetParam);
            BeanUtils.copyPropertiesIgnoreNull(projectParamTemplateValue, insetParam);
            insetParam.setParamSuitNumber(maxSuitNumber + 1);
            insetParam.setParamType("file");
            projectParamTemplateValueMapper.insertSelective(insetParam);
        });
        ret = inArray.size() + outArray.size();
        // 更新大分组下的数据名称和数据备注
        ProjectParamTemplateValue projectParamTemplateValue1 = new ProjectParamTemplateValue();
        projectParamTemplateValue1.setGroupDataName(projectParamTemplateValue.getGroupDataName());
        projectParamTemplateValue1.setGroupDataComment(projectParamTemplateValue.getGroupDataComment());
        Example example = new Example(ProjectParamTemplateValue.class);
        example.createCriteria().andEqualTo("projectId", projectParamTemplateValue.getProjectId())
                .andEqualTo("groupParamSuitNumber", projectParamTemplateValue.getGroupParamSuitNumber());
        projectParamTemplateValueMapper.updateByExampleSelective(projectParamTemplateValue1, example);
        return ret;
    }


    /**
     * 保存新建的数据模板
     *
     * @param projectParamTemplateValue
     * @param paramterArray
     * @param paramType
     * @param paramSuitNumber
     * @return
     */
    private List<ProjectParamTemplateValue> saveSetParamter(ProjectParamTemplateValue projectParamTemplateValue, JSONArray paramterArray, String paramType, int paramSuitNumber) {
        List<ProjectParamTemplateValue> list = new ArrayList<ProjectParamTemplateValue>();
        for (int i = 0; i < paramterArray.size(); i++) {
            ProjectParamTemplateValue insetParam = new ProjectParamTemplateValue();
            JSONObject inJo = (JSONObject) paramterArray.get(i);
            ProjectParamTemplateValue projectParamTemplateValue1 = JSONUtil.toObject(inJo.toString(), ProjectParamTemplateValue.class);
            BeanUtils.copyPropertiesIgnoreNull(projectParamTemplateValue1, projectParamTemplateValue);
            projectParamTemplateValue.setParamSuitNumber(paramSuitNumber);
            projectParamTemplateValue.setParamType(paramType);
            BeanUtils.copyPropertiesIgnoreNull(projectParamTemplateValue, insetParam);
            list.add(insetParam);
        }
        projectParamTemplateValueMapper.insertList(list);
        return list;
    }

    /**
     * 验证统一项目下的参数名字是否有相同的
     *
     * @param inArray
     * @param outArray
     * @return
     */
    public boolean checkParmeterName(JSONArray inArray, JSONArray outArray) {
        try {
            List<ProjectParam> list = new ArrayList<>();
            for (int i = 0; i < inArray.size(); i++) {
                JSONObject inJo = (JSONObject) inArray.get(i);
                ProjectParam inParamter = JSONUtil.toObject(inJo.toString(), ProjectParam.class);
                list.add(inParamter);
            }
            for (int i = 0; i < outArray.size(); i++) {
                JSONObject outJo = (JSONObject) outArray.get(i);
                ProjectParam outParamter = JSONUtil.toObject(outJo.toString(), ProjectParam.class);
                list.add(outParamter);
            }

            if (list == null || list.size() == 0) {
                return false;
            }
            Set<String> set = new HashSet<>();

            for (int i = 0; i < list.size(); i++) {
                if ("".equals(list.get(i).getParamName())) {
                    continue;
                }
                if (set.contains(list.get(i).getParamName())) {
                    return false;
                }
                set.add(list.get(i).getParamName());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int delProjectTemplateValue(Integer projectInfoId, Integer paramSuitNumber, Integer dataTemplateNumber, Integer groupParamSuitNumber) {
        Example example = new Example(ProjectParamTemplateValue.class);
        // 普通模板删除
        if (groupParamSuitNumber == null && dataTemplateNumber == null) {
            example.createCriteria().andEqualTo("projectId", projectInfoId)
                    .andEqualTo("paramSuitNumber", paramSuitNumber);
        }
        // 分组模板分组列表删除
        if (dataTemplateNumber == null && groupParamSuitNumber != null) {
            example.createCriteria().andEqualTo("projectId", projectInfoId)
                    .andEqualTo("groupParamSuitNumber", groupParamSuitNumber);
        }

        // 分组模板中某套中某个分组某套数据删除
        if (dataTemplateNumber != null && groupParamSuitNumber != null) {
            example.createCriteria().andEqualTo("projectId", projectInfoId)
                    .andEqualTo("paramSuitNumber", paramSuitNumber)
                    .andEqualTo("dataTemplateNumber", dataTemplateNumber)
                    .andEqualTo("groupParamSuitNumber", groupParamSuitNumber);
        }

        return projectParamTemplateValueMapper.deleteByExample(example);
    }

    /**
     * 获取分组下拉框选择参数
     *
     * @param projectId
     * @return
     */
    public ProjectGroupSelectParam getGroupTemplateInfo(Integer projectId) {
        ProjectGroupSelectParam projectGroupSelectParam = new ProjectGroupSelectParam();
        List<ProjectGroupTemplateParam> cParamList = new ArrayList<>();
        List<ProjectGroupTemplateParam> fParamList = new ArrayList<>();
        Set<Integer> dataTemplateNumberSet = new HashSet<>();
        List<ProjectParam> list = projectParamMapper.listGroupTemplate(projectId);
        list.stream().forEach(projectParam -> {
            ProjectGroupTemplateParam projectGroupTemplateParam = new ProjectGroupTemplateParam();
            projectGroupTemplateParam.setId(String.valueOf(projectParam.getDataTemplateNumber()));
            projectGroupTemplateParam.setPid("0");
            projectGroupTemplateParam.setName(projectParam.getDataTemplateName());
            cParamList.add(projectGroupTemplateParam);
            fParamList.add(projectGroupTemplateParam);
            dataTemplateNumberSet.add(projectParam.getDataTemplateNumber());

        });
        List<String> fileAndConstrant = Arrays.asList("constrant", "file");
        fileAndConstrant.stream().forEach(paramType -> {
            dataTemplateNumberSet.stream().forEach(dataTemplateNumber -> {
//                List<ProjectParamTemplateValue> projectParamTemplateValueList = projectParamTemplateValueMapper.listGroupTemplateInfo(projectId, dataTemplateNumber);
//                projectParamTemplateValueList.stream().forEach(projectParamTemplateValue -> {
//                    ProjectGroupTemplateParam projectGroupTemplateParam = new ProjectGroupTemplateParam();
//                    projectGroupTemplateParam.setId(dataTemplateNumber + "_" + projectParamTemplateValue.getParamSuitNumber());
//                    projectGroupTemplateParam.setPid(dataTemplateNumber.toString());
//                    projectGroupTemplateParam.setName(projectParamTemplateValue.getDataName());
//                    if ("constrant".equals(paramType)){
//                        cParamList.add(projectGroupTemplateParam);
//                    }else {
//                        fParamList.add(projectGroupTemplateParam);
//                    }
                Example example = new Example(ProjectParam.class);
                example.createCriteria().andEqualTo("projectId", projectId)
                        .andEqualTo("dataTemplateNumber", dataTemplateNumber)
                        .andEqualTo("paramType", paramType);
                List<ProjectParam> paramList = projectParamMapper.selectByExample(example);
                paramList.stream().forEach(projectParam -> {
                    ProjectGroupTemplateParam cParam = new ProjectGroupTemplateParam();
//                        cParam.setId(dataTemplateNumber + "_" + projectParamTemplateValue.getParamSuitNumber() + "_" + projectParam.getId());
//                        cParam.setPid(dataTemplateNumber + "_" + projectParamTemplateValue.getParamSuitNumber());
                    cParam.setId(dataTemplateNumber + "_" + projectParam.getId());
                    cParam.setPid(dataTemplateNumber.toString());
                    cParam.setName(projectParam.getParamName());
                    if ("constrant".equals(paramType)) {
                        cParamList.add(cParam);
                    } else {
                        fParamList.add(cParam);
                    }
                });
            });
        });
//        });
        projectGroupSelectParam.setcParamList(cParamList);
        projectGroupSelectParam.setfParamList(fParamList);
        return projectGroupSelectParam;
    }

    public List<ProjectParam> getGroupDataTemplate(Integer projectId) {
        List<ProjectParam> projectParamList = projectParamMapper.listGroupTemplate(projectId);
        return projectParamList;
    }

    public ProjectParamTemplateValue getDataNameAndComment(Integer projectInfoId, Integer groupParamSuitNumber) {
        Example example = new Example(ProjectParamTemplateValue.class);
        example.createCriteria().andEqualTo("projectId", projectInfoId)
                .andEqualTo("groupParamSuitNumber", groupParamSuitNumber);
        List<ProjectParamTemplateValue> list = projectParamTemplateValueMapper.selectByExample(example);
        return list.size() > 0 ? list.get(0) : new ProjectParamTemplateValue();
    }

    /**
     * 数据模板删除分组
     *
     * @param projectId
     * @param dataTemplateNumber
     * @return
     */
    public int delGroupTemplate(Integer projectId, Integer dataTemplateNumber) {
        Example example = new Example(ProjectParam.class);
        example.createCriteria().andEqualTo("projectId", projectId)
                .andEqualTo("dataTemplateNumber", dataTemplateNumber);
        return projectParamMapper.deleteByExample(example);
    }

    public int saveDataNameAndComment(JSONObject json) {
        Integer projectId = Integer.valueOf(json.getString("projectInfoId"));
        Integer groupParamSuitNumber = Integer.valueOf(json.getString("groupParamSuitNumber"));
        String dataName = json.getString("dataName");
        String dataComment = json.getString("dataComment");
        Example example = new Example(ProjectParamTemplateValue.class);
        example.createCriteria().andEqualTo("projectId", projectId)
                .andEqualTo("groupParamSuitNumber", groupParamSuitNumber);
        ProjectParamTemplateValue projectParamTemplateValue = new ProjectParamTemplateValue();
        projectParamTemplateValue.setGroupDataName(dataName);
        projectParamTemplateValue.setGroupDataComment(dataComment);
        return projectParamTemplateValueMapper.updateByExampleSelective(projectParamTemplateValue, example);
    }

    public int saveChoseConstantProjectParam(JSONObject json) {
        int ret = 0;
        Integer projectId = Integer.valueOf(json.getString("projectId"));
        String mapXmlPath = json.getString("mapXmlPath");
        String valueXmlPath = json.getString("valueXmlPath");
        String nowDay = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        nowDay = sdf.format(new Date());
        ApplicationProperties app = new ApplicationProperties();
        String tempMapXmlPath = app.getValueByKey("file.upload.path") + nowDay + "\\" + mapXmlPath;
        String tempValueXmlPath = app.getValueByKey("file.upload.path") + nowDay + "\\" + valueXmlPath;
        AssumptionAnalysis assumptionAnalysis = new AssumptionAnalysis(tempMapXmlPath, tempValueXmlPath);
        List<DataBean> list = assumptionAnalysis.getDataBeans();
        Map<String, List<ProjectParam>> parMap = getProjectParam(projectId);
        List<ProjectParam> inparList = parMap.get("inParList");
        List<ProjectParam> outparList = parMap.get("outParList");
        // 获取选择的数据
        JSONArray choseDateIndex = json.getJSONArray("choseDateIndex");
        // 判断项目中模板最大的套数
        int maxSuitNumber = projectParamTemplateValueMapper.queryMaxSuitCount(projectId);
        for (int i = 0; i < choseDateIndex.size(); i++) {
            maxSuitNumber++;
            Integer index = (Integer) choseDateIndex.get(i);
            for (int j = 0; j < list.size(); j++) {
                if (j == index) {
                    for (ProjectParam projectParam:inparList) {
                        ProjectParamTemplateValue projectParamTemplateValue = new ProjectParamTemplateValue();
                        projectParamTemplateValue.setProjectId(projectParam.getProjectId());
                        projectParamTemplateValue.setDataName(list.get(j).getDataName());
                        projectParamTemplateValue.setProjectParamId(projectParam.getId());
                        projectParamTemplateValue.setParamValue(assumptionAnalysis.getValue(projectParam.getParamName(), list.get(j)));
                        projectParamTemplateValue.setParamType(projectParam.getParamType());
                        projectParamTemplateValue.setParamSuitNumber(maxSuitNumber);
                        ret += projectParamTemplateValueMapper.insert(projectParamTemplateValue);
                    }
                }
            }
        }
        return ret;
    }
}
