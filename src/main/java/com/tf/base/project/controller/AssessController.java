package com.tf.base.project.controller;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.rtf.RtfWriter2;
import com.tf.base.common.domain.*;
import com.tf.base.common.persistence.*;
import com.tf.base.common.service.BaseService;
import com.tf.base.common.service.CommonService;
import com.tf.base.common.utils.*;
import com.tf.base.go.define.IndexDefinitions;
import com.tf.base.go.define.IndexNode;
import com.tf.base.go.engine.Impl.IndexEngineImpl;
import com.tf.base.go.service.Impl.ModelInputService;
import com.tf.base.go.service.IndexRepositoryService;
import com.tf.base.go.type.NodeType;
import com.tf.base.project.domain.*;
import com.tf.base.project.service.AssessService;
import com.tf.base.project.service.ProjectIndexAssessTree;
import com.tf.base.project.service.ProjectIndexService;
import com.tf.base.project.service.ProjectService;
import com.tf.base.util.LabelMap;
import com.tf.base.util.WordTemplateImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

@Controller
@RequestMapping("/assess/")
public class AssessController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BaseService baseService;
    @Autowired
    private WordHelper wordHelper;
    @Autowired
    private CommonService commonService;
    @Autowired
    private AssessService assessService;
    @Autowired
    private AssessInfoMapper assessInfoMapper;
    @Autowired
    private ProjectIndexMapper projectIndexMapper;
    @Autowired
    private ProjectIndexAssessMapper projectIndexAssessMapper;
    @Autowired
    private ProjectInfoMapper projectInfoMapper;
    @Autowired
    private IndexInfoMapper indexInfoMapper;
    @Autowired
    private IndexInfoProcessMapper indexInfoProcessMapper;
    @Autowired
    private IndexModelMapper indexModelMapper;
    @Autowired
    private AssessParamMapper assessParamMapper;
    @Autowired
    private IndexEngineImpl indexEngine;
    @Autowired
    private IndexNodeProcessMapper indexNodeProcessMapper;
    @Autowired
    private WordTemplateMapper wordTemplateMapper;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectParamTemplateMapper projectParamTemplateMapper;
    @Autowired
    private ProjectParamTemplateValueMapper projectParamTemplateValueMapper;
    @Autowired
    private ProjectIndexService projectIndexService;

    @Autowired
    @Qualifier("indexRepositoryService")
    IndexRepositoryService indexRepositoryService;

    @RequestMapping(value = "assessList", method = RequestMethod.GET)
    public String assessList(Model model, String projectId, String projectName) {
        model.addAttribute("projectId", projectId);
        try {
            projectName = java.net.URLDecoder.decode(projectName, "UTF-8");
            model.addAttribute("projectName", projectName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "assess/assessList";
    }

    @ResponseBody
    @RequestMapping(value = "toassessList", method = RequestMethod.POST)
    public Map<String, Object> toassessList(Model model, String projectInfoId) {
        List weight_current = assessService.selectNodetree(projectInfoId);
        Map<String, Object> result = new HashMap<String, Object>();
        for (int i = 1; i < weight_current.size(); i++) {
            Object w = weight_current.get(i);
            if (w == null) {
                //禁止进入评估页面
                result.put("msg", 1);
                return result;
            }
        }
//进入评估页面
        result.put("msg", 0);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "assessList", method = RequestMethod.POST)
    public Map<String, Object> assessList(AssessInfoParams params) {
        Map<String, Object> result = new HashMap<String, Object>();
        int total = assessService.queryCount(params);
        result.put("total", total);
        if (total == 0) {
            result.put("rows", new ArrayList<AssessInfo>());
            return result;
        } else {
            int start = (params.getPage() - 1) * params.getRows();
            List<AssessInfo> rows = assessService.queryList(params, start);
            if (rows != null && rows.size() > 0) {
                for (AssessInfo r : rows) {
                    //0:未评估；2：评估成功，3：评估失败
                    String statusStr = "";
                    switch (r.getAssessStatus()) {
                        case 0:
                            statusStr = "未评估";
                            break;
                        case 2:
                            statusStr = "评估成功";
                            break;
                        case 3:
                            statusStr = "评估失败";
                            break;
                        default:
                            break;
                    }
                    r.setAssessStatusStr(statusStr);
                }
            }
            result.put("rows", rows);
            return result;
        }
    }

    @ResponseBody
    @RequestMapping(value = "assessCategoryList", method = RequestMethod.POST)
    public List<ProjectIndex> categoryList(String projectId) {
        return assessService.categoryList(projectId);
    }

    @ResponseBody
    @RequestMapping(value = "assessTreeList", method = RequestMethod.POST)
    public List<ProjectIndex> assessTreeList(String projectId) {

        //指标体系
        ProjectIndex projectIndexParams = new ProjectIndex();
        projectIndexParams.setProjectId(Integer.parseInt(projectId));
        projectIndexParams.setIsdelete(0);
        List<ProjectIndex> projectIndexs = projectIndexMapper.select(projectIndexParams);
        return projectIndexs;
    }


    @RequestMapping(value = "assessAdd", method = RequestMethod.GET)
    public String assessAdd(Model model, String id) {
        ProjectIndex info = projectIndexMapper.selectByPrimaryKey(id);
        model.addAttribute("projectId", info.getProjectId());
        model.addAttribute("indexId", info.getIndexId());
        model.addAttribute("nodeId", info.getId());

        return "assess/assessAdd";
    }

    @RequestMapping(value = "assessStart", method = RequestMethod.GET)
    public String assessStart(Model model, String nodeId, String id, String projectName) {//开始评估
        try {
            projectName = java.net.URLDecoder.decode(projectName, "UTF-8");
            model.addAttribute("projectName", projectName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        AssessInfo assessInfo = assessInfoMapper.selectByPrimaryKey(id);
        model.addAttribute("info", assessInfo);

        model.addAttribute("projectId", assessInfo.getProjectId());
        model.addAttribute("indexId", assessInfo.getIndexId());
        model.addAttribute("nodeId", assessInfo.getNodeId());

        List<ProjectIndex> nodeList = getProjectNodes(assessInfo);


        List<AssessParamBean> paramList = new ArrayList<AssessParamBean>();

        Example example = new Example(ProjectIndex.class);
        example.createCriteria().andEqualTo("projectId", assessInfo.getProjectId());
        List<ProjectIndex> allProjectIndex = projectIndexMapper.selectByExample(example);
        String rootName = "";
        for (ProjectIndex projectIndex : allProjectIndex) {
            if (projectIndex.getParentid() == 0) {
                rootName = projectIndex.getIndexName();
            }
        }
        for (ProjectIndex i : nodeList) {
           // if (i.getIsLastNode() == 1 && i.getIndexId() != null && i.getIndexId() != 0 && i.getIndexValue() == null) { //说明是参数节点，需要获取参数
            if (i.getIndexId() != null && i.getIndexId() != 0) { //说明是参数节点，需要获取参数
                //文件参数
                List<AssessParamExt> fParamList = new ArrayList<AssessParamExt>();
                //常量参数
                List<AssessParamExt> cParamList = new ArrayList<AssessParamExt>();
                //
                List<String> parenNameList = new ArrayList<>();

                this.getParam(assessInfo.getId(), i.getId(), fParamList, cParamList);
                AssessParamBean bean = new AssessParamBean();

                bean.setIndexName(i.getIndexName());
                bean.setRootName(rootName);
                bean.setfParamList(fParamList);
                bean.setcParamList(cParamList);
                // 获取节点的父节点名称
                parenNameList = projectIndexService.getparenIndexName(parenNameList, i.getParentid(), allProjectIndex);
                Collections.reverse(parenNameList);
                bean.setParentNameList(parenNameList);
                // 获取第二级节点id
                if (i.getIndexLevel() == 2) {
                    bean.setParentId(i.getId());
                } else {
                    getLevel2Id(allProjectIndex, i.getParentid(), bean);
                }
                if (bean.getParentId() == null) {
                    bean.setParentId(i.getParentid());
                }
                paramList.add(bean);

            }
        }

        List<AssessParamParentBean> list = getProjectParamParentBeans(paramList);
        model.addAttribute("paramList", list);
        model.addAttribute("id", id);
        return "assess/assessStart";
    }

    /**
     * 已相同父节点的节点放在一起
     *
     * @param paramList
     * @return
     */
    private List<AssessParamParentBean> getProjectParamParentBeans(List<AssessParamBean> paramList) {
        List<AssessParamParentBean> list = new ArrayList<>();
        Set<Integer> set = new TreeSet<>();
        paramList.forEach(assessParamBean -> set.add(assessParamBean.getParentId()));
        for (Integer integer : set) {
            List<AssessParamBean> temp = new ArrayList<>();
            for (int i = 0; i < paramList.size(); i++) {
                if (paramList.get(i).getParentId().equals(integer)) {
                    temp.add(paramList.get(i));
                }
            }
            AssessParamParentBean assessParamParentBean = new AssessParamParentBean();
            assessParamParentBean.setProjectParamBeans(temp);
            list.add(assessParamParentBean);
        }
        return list;
    }

    private void getLevel2Id(List<ProjectIndex> list, Integer parentId, AssessParamBean bean) {
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

    private void getParam(Integer assessId, Integer lastNodeId,
                          List<AssessParamExt> fParamList, List<AssessParamExt> cParamList) {
        //获取参数
        Example example = new Example(AssessParam.class);
        example.createCriteria().andEqualTo("assessId", assessId)
                .andEqualTo("nodeId", lastNodeId);
        List<AssessParam> assessParams = assessParamMapper.selectByExample(example);

        if (assessParams != null && assessParams.size() > 0) {
            for (AssessParam a : assessParams) {
                AssessParamExt assessParamExt = new AssessParamExt();
                BeanUtils.copyProperties(a, assessParamExt);
                if ("file".equals(a.getParamType())) {
                    fParamList.add(assessParamExt);
                } else {
                    cParamList.add(assessParamExt);
                }
            }
        }
    }


    @RequestMapping(value = "assessEdit", method = RequestMethod.GET)
    public String templetEdit(Model model, String id) {
        model.addAttribute("id", id);
        //TODO

        return "assess/assessAdd";
    }

    @RequestMapping(value = "assessNode", method = RequestMethod.GET)
    public String assessNode(Model model, String id) {
        AssessInfo assessInfo = assessInfoMapper.selectByPrimaryKey(id);
        model.addAttribute("info", assessInfo);
        model.addAttribute("projectNodeTreeJson", assessService.getIndexJsonByAssertId(Integer.valueOf(id)));
        return "assess/assessNode";
    }

    @RequestMapping(value = "assessView", method = RequestMethod.GET)
    public String assessView(Model model, String id) {
        model.addAttribute("id", id);
        AssessInfo assessInfo = assessInfoMapper.selectByPrimaryKey(id);
        //项目信息
        int projectId = assessInfo.getProjectId();
        ProjectInfo projectInfo = projectInfoMapper.selectByPrimaryKey(projectId);
        model.addAttribute("projectInfo", projectInfo);
        //评估结果
        model.addAttribute("assessResult", assessInfo.getAssessResult());
        //指标树
        model.addAttribute("projectNodeTreeJson", assessService.getIndexJsonByAssertId(Integer.valueOf(id)));
        //指标信息
        Example example = new Example(ProjectIndexAssess.class);
        example.createCriteria().andEqualTo("assessId", id)
                .andEqualTo("projectId", assessInfo.getProjectId());
        List<ProjectIndexAssess> projectIndexs1 = projectIndexAssessMapper.selectByExample(example);
        List<ProjectIndexAssess> projectIndexs = new ArrayList<>();
        for (ProjectIndexAssess ps : projectIndexs1) {
            if (!StringUtil.isEmpty(ps.getIndexValue())) {//过滤掉指标值为空的
                projectIndexs.add(ps);
            }
        }

        ProjectIndexAssessTree projectIndexAssessTree = new ProjectIndexAssessTree(projectIndexs);
        List<ProjectIndexAssessBean> projectIndexAssessBeans = projectIndexAssessTree.getpProjectIndexAssessBeans();
        List<AssessProjectIndex> assessProjectIndexs = new ArrayList<>();
        for (ProjectIndexAssessBean projectIndex2 : projectIndexAssessBeans) {
            AssessProjectIndex assessProjectIndex = new AssessProjectIndex();
            BeanUtils.copyProperties(projectIndex2, assessProjectIndex);
            assessProjectIndex.setNo(projectIndex2.getNo());
            //图表展示需要的类型
            if (projectIndex2.getIndexId() != null && !"".equals(projectIndex2.getIndexId())) {
                List<IndexModel> indexModels = indexModelMapper.selectNodeTypeByIndexId(projectIndex2.getIndexId());
                List<IndexModelBean> picTypes = new ArrayList<>();
                List<JSONObject> tables = new ArrayList<>();
                for (IndexModel indexModel : indexModels) {
                    IndexModelBean indexModelBean = new IndexModelBean();
                    BeanUtils.copyProperties(indexModel, indexModelBean);
                    indexModelBean.setProjectNodeId(projectIndex2.getProjectIndexId());
                    Map pMap = new HashMap();
                    pMap.put("indexId", indexModel.getIndexId());
                    pMap.put("businessId", id);
                    pMap.put("nodeKey", indexModel.getNodeKey());
                    if (indexModel.getNodeCategory().intValue() == 2) {
                        //如果存在评估中,则进行
                        if(isExistPic(pMap)){
                            picTypes.add(indexModelBean);
                        }

                    } else if (indexModel.getNodeCategory().intValue() == 5) {//表格
                        JSONObject tableJson = getTableJson(pMap);
                        if (tableJson != null) {
                            tableJson.put("indexLabel", indexModelBean.getProjectNodeId() + "_" + indexModelBean.getIndexId() + "_" + indexModelBean.getNodeKey());
                            tables.add(tableJson);
                        }
                    }
                }
                assessProjectIndex.setPicTypes(picTypes);
                assessProjectIndex.setTables(tables);
            }
            assessProjectIndexs.add(assessProjectIndex);
        }
        model.addAttribute("assessProjectIndexs", assessProjectIndexs);
        String showName = assessInfoMapper.getShowNameByUid(assessInfo.getCreateUid());
        model.addAttribute("showName", showName);
        model.addAttribute("projectId", projectId);
        model.addAttribute("assessId", assessInfo.getId());
        model.addAttribute("assessTime", assessInfo.getCreateTime());
        return "assess/assessView";
    }


    /**
     * 判断是否在子节点中存在
     * @param pMap
     * @return
     */
    private  boolean isExistPic(Map pMap){
        List<IndexNodeProcess> pList = indexNodeProcessMapper.getNodeProcessByBusinessId(pMap);
        if (pList != null && pList.size() > 0) {
            return true ;
        }
        return  false ;
    }


    private JSONObject getTableJson(Map pMap) {
        List<IndexNodeProcess> pList = indexNodeProcessMapper.getNodeProcessByBusinessId(pMap);
        if (pList != null && pList.size() > 0) {
            IndexNodeProcess indexNodeProcess = pList.get(0);
            String jsonString = indexNodeProcess.getNodeInputParamExp();

            JSONObject json = JSONObject.fromObject(jsonString);
            json.put("id", indexNodeProcess.getId());
            return json;
        }
        return null;
    }


    //保存
    @RequestMapping(value = "assessSave", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> assessSave(String nodeId) {

        int id = assessService.assessSaveTo(Integer.parseInt(nodeId));

        Map<String, Object> result = new HashMap<String, Object>();

        if (id > 0) {
            result.put("status", 1);
            result.put("id", id);
            result.put("msg", "保存成功");
        } else if (id == -1) {
            result.put("status", -1);
            result.put("msg", "叶子节点必须设置指标或者子节点");
        } else if (id == -2) {
            result.put("status", -2);
            result.put("msg", "中间节点和叶子节点需要设置权重");
        }

        return result;
    }

    @RequestMapping(value = "assessDel", method = RequestMethod.POST)
    @ResponseBody
    public Map assessDel(String id) {
        Map<String, Object> result = new HashMap<String, Object>();
        AssessInfo t = new AssessInfo();
        t.setId(Integer.parseInt(id));
        t.setIsdelete(1);
        int i = assessInfoMapper.updateByPrimaryKeySelective(t);
        if (i > 0) {
            result.put("status", 1);
            result.put("msg", "删除成功");
        } else {
            result.put("status", 0);
            result.put("msg", "删除失败");
        }

        return result;
    }

    @RequestMapping(value = "AllListByCateId", method = RequestMethod.POST)
    @ResponseBody
    public List<AssessInfo> AllListByCateId(Integer nodeId) {
        Map<String, Object> result = new HashMap<String, Object>();
        AssessInfo t = new AssessInfo();
        t.setIsdelete(0);
        t.setNodeId(nodeId);
        List<AssessInfo> templetInfos = assessInfoMapper.select(t);

        return templetInfos;
    }

    @RequestMapping(value = "dldoc", method = RequestMethod.POST)
    @ResponseBody
    public void dldoc(HttpServletResponse response, HttpServletRequest request) {
        String base64 = request.getParameter("base64");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date now = new Date();
        ApplicationProperties app = new ApplicationProperties();
        String filePath = app.getValueByKey("file.upload.path") + dateFormat.format(now) + File.separator;

        File folder = new File(filePath);

        if (!folder.exists()) {
            folder.mkdirs();
        }


        try {
            String imgPath = filePath + System.currentTimeMillis() + ".png";
            base64 = base64.replace("data:image/png;base64,", "");
            //生成图片
            Base64ImageUtil.base64ChangeImage(base64, imgPath);

            String docxPath = System.currentTimeMillis() + ".doc";
            File docFile = new File(filePath + docxPath);
            docFile.createNewFile();
//
//    		WordUtils.exportDocx(docxPath, "ssss", 14, imgPath);
//    		wordHelper.insertImg(docFile.getPath(),docFile.getPath(), imgPath);
            Rectangle rectPageSize = new Rectangle(PageSize.A4);
            rectPageSize = rectPageSize.rotate();

            Document doc = new Document();
            RtfWriter2.getInstance(doc, new FileOutputStream(docFile.getPath()));
            doc.open();
            Image png = Image.getInstance(imgPath);

            float wf = 0;
            if (png.getWidth() > rectPageSize.getWidth()) {
                wf = rectPageSize.getWidth() / png.getWidth();
            }
            float hf = 0;
            if (png.getHeight() > rectPageSize.getHeight()) {
                hf = rectPageSize.getHeight() / png.getHeight();
            }
            float wscale = wf > 0 ? ((wf) * 100) : 100;
            //png.scalePercent(wscale);
            png.scaleAbsoluteWidth(rectPageSize.getWidth() * wf);
            png.scaleAbsoluteHeight(rectPageSize.getHeight() * hf);
            doc.add(png);
            doc.close();


            String fileName = "评估报告.doc";
            //设置向浏览器端传送的文件格式
            response.setContentType("application/x-download");

            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename="
                    + fileName);
            FileInputStream fis = null;
            OutputStream os = null;
            os = response.getOutputStream();
            fis = new FileInputStream(docFile.getPath());
            byte[] b = new byte[1024 * 10];
            int i = 0;
            while ((i = fis.read(b)) > 0) {
                os.write(b, 0, i);
            }
            os.flush();
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "assessGroupProjectParam", method = RequestMethod.GET)
    public String assessGroupProjectParam(Model model, String id, Integer groupParamSuitNUmber, String assessName, String assessContent, String projectName) {
        model.addAttribute("id", id);
        AssessInfo assessInfo = getAssessInfo(model, id, assessName, assessContent, projectName);
        model.addAttribute("groupParamSuitNUmber", groupParamSuitNUmber);

        List<ProjectIndex> nodeList = getProjectNodes(assessInfo);

        Example example = new Example(ProjectIndex.class);
        example.createCriteria().andEqualTo("projectId", assessInfo.getProjectId());
        List<ProjectIndex> allProjectIndex = projectIndexMapper.selectByExample(example);
        String rootName = "";
        for (ProjectIndex projectIndex : allProjectIndex) {
            if (projectIndex.getParentid() == 0) {
                rootName = projectIndex.getIndexName();
            }
        }

        Boolean isGroupTemplateFlag = true;
        List<AssessParamBean> paramList = getAssessParamBeans(groupParamSuitNUmber, assessInfo, nodeList, allProjectIndex, rootName, isGroupTemplateFlag);
        List<AssessParamParentBean> list = getProjectParamParentBeans(paramList);
        model.addAttribute("paramList", list);
        return "assess/assessGroupTemplateStart";
    }

    /**
     * 获取项目节点信息
     *
     * @param assessInfo
     * @return
     */
    private List<ProjectIndex> getProjectNodes(AssessInfo assessInfo) {
        List<ProjectIndex> nodeList = new ArrayList<>();
        Map<Integer, Integer> lastMap = new HashMap<>();
        ProjectIndex current = projectIndexMapper.selectByPrimaryKey(assessInfo.getNodeId());
        nodeList.add(current);
        nodeList = assessService.getSubNode(nodeList, assessInfo.getNodeId(), lastMap);

        if (nodeList.size() == 1) {
            nodeList.get(0).setIsLastNode(1);
        }
        return nodeList;
    }

    /**
     * 参数设置
     *
     * @param model
     * @param id
     * @param assessName
     * @param assessContent
     * @param projectName
     * @return
     */
    private AssessInfo getAssessInfo(Model model, String id, String assessName, String assessContent, String projectName) {
        AssessInfo assessInfo = assessInfoMapper.selectByPrimaryKey(id);
        model.addAttribute("info", assessInfo);

        model.addAttribute("projectId", assessInfo.getProjectId());
        model.addAttribute("indexId", assessInfo.getIndexId());
        model.addAttribute("nodeId", assessInfo.getNodeId());

        try {
            assessContent = java.net.URLDecoder.decode(assessContent, "UTF-8");
            model.addAttribute("assessContent", assessContent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            projectName = java.net.URLDecoder.decode(projectName, "UTF-8");
            model.addAttribute("projectName", projectName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            assessName = java.net.URLDecoder.decode(assessName, "UTF-8");
            model.addAttribute("assessName", assessName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return assessInfo;
    }


    @RequestMapping(value = "assessProjectParam", method = RequestMethod.GET)
    public String assessProjectParam(Model model, String id, Integer paramSuitNUmber, String assessName, String assessContent, String projectName) {
        AssessInfo assessInfo = getAssessInfo(model, id, assessName, assessContent, projectName);

        List<ProjectIndex> nodeList = getProjectNodes(assessInfo);

        Example example = new Example(ProjectIndex.class);
        example.createCriteria().andEqualTo("projectId", assessInfo.getProjectId());
        List<ProjectIndex> allProjectIndex = projectIndexMapper.selectByExample(example);
        String rootName = "";
        for (ProjectIndex projectIndex : allProjectIndex) {
            if (projectIndex.getParentid() == 0) {
                rootName = projectIndex.getIndexName();
            }
        }
        Boolean isGroupTemplateFlag = false;
        List<AssessParamBean> paramList = getAssessParamBeans(paramSuitNUmber, assessInfo, nodeList, allProjectIndex, rootName, isGroupTemplateFlag);
        List<AssessParamParentBean> list = getProjectParamParentBeans(paramList);
        model.addAttribute("paramList", list);
        model.addAttribute("id", id);
        return "assess/assessTemplateStart";
    }

    /**
     * 获取节点参数信息
     *
     * @param paramSuitNUmber
     * @param assessInfo
     * @param nodeList
     * @param allProjectIndex
     * @param rootName
     * @param isGroupTemplateFlag
     * @return
     */
    private List<AssessParamBean> getAssessParamBeans(Integer paramSuitNUmber, AssessInfo assessInfo, List<ProjectIndex> nodeList, List<ProjectIndex> allProjectIndex, String rootName, Boolean isGroupTemplateFlag) {
        List<AssessParamBean> paramList = new ArrayList<>();
        for (ProjectIndex i : nodeList) {
            if (i.getIsLastNode() == 1 && i.getIndexId() != null && i.getIndexId() != 0 && i.getIndexValue() == null) { //说明是参数节点，需要获取参数
                //文件参数
                List<AssessParamExt> fParamList = new ArrayList<>();
                //常量参数
                List<AssessParamExt> cParamList = new ArrayList<>();

                List<String> parenNameList = new ArrayList<>();

                this.getProjectParam(assessInfo.getId(), i.getId(), fParamList, cParamList, i.getProjectId(), paramSuitNUmber, isGroupTemplateFlag);
                AssessParamBean bean = new AssessParamBean();

                bean.setIndexName(i.getIndexName());
                bean.setfParamList(fParamList);
                bean.setcParamList(cParamList);
                bean.setRootName(rootName);
                // 获取节点的父节点名称
                parenNameList = projectIndexService.getparenIndexName(parenNameList, i.getParentid(), allProjectIndex);
                Collections.reverse(parenNameList);
                bean.setParentNameList(parenNameList);
                // 获取第二级节点id
                if (i.getIndexLevel() == 2) {
                    bean.setParentId(i.getId());
                } else {
                    getLevel2Id(allProjectIndex, i.getParentid(), bean);
                }
                if (bean.getParentId() == null) {
                    bean.setParentId(i.getParentid());
                }
                paramList.add(bean);

            }
        }
        return paramList;
    }

    /**
     * 获取项目节点信息
     *
     * @param assessId
     * @param lastNodeId
     * @param fParamList
     * @param cParamList
     * @param projectId
     * @param paramSuitNUmber
     */
    private void getProjectParam(Integer assessId, Integer lastNodeId,
                                 List<AssessParamExt> fParamList, List<AssessParamExt> cParamList, Integer projectId, Integer paramSuitNUmber, Boolean isGroupTemplateFlag) {
        //获取参数
        Example example = new Example(AssessParam.class);
        example.createCriteria().andEqualTo("assessId", assessId)
                .andEqualTo("nodeId", lastNodeId);
        List<AssessParam> assessParams = assessParamMapper.selectByExample(example);

        if (assessParams != null && assessParams.size() > 0) {
            for (AssessParam a : assessParams) {
                AssessParamExt assessParamExt = new AssessParamExt();
                BeanUtils.copyProperties(a, assessParamExt);
                if ("file".equals(a.getParamType())) {
                    getParamValue(projectId, assessParamExt, paramSuitNUmber, isGroupTemplateFlag);
                    fParamList.add(assessParamExt);
                } else {
                    getParamValue(projectId, assessParamExt, paramSuitNUmber, isGroupTemplateFlag);
                    cParamList.add(assessParamExt);
                }
            }
        }
    }

    /**
     * 获取节点设置的参数模板
     *
     * @param projectId
     * @param a
     * @param paramSuitNUmber
     * @return
     */
    private AssessParam getParamValue(Integer projectId, AssessParamExt a, Integer paramSuitNUmber, Boolean isGroupTemplateFlag) {
        Example example = new Example(ProjectParamTemplate.class);
        String paramType = a.getParamType().trim();
        if ("constant".equals(paramType)) {
            paramType = "constrant";
        }
        example.createCriteria().andEqualTo("projectId", projectId)
                .andEqualTo("nodeId", a.getNodeId())
                .andEqualTo("paramType", paramType)
                .andEqualTo("paramName", a.getParamName().trim())
                .andEqualTo("indexId", a.getIndexId())
                .andEqualTo("nodeKey", a.getNodeKey());
        List<ProjectParamTemplate> list = projectParamTemplateMapper.selectByExample(example);
        if (list.size() > 0) {
            ProjectParamTemplate projectParamTemplate = list.get(0);
            if (paramSuitNUmber != null) {
                // 模板选择
                if (projectParamTemplate.getIsSelfDefined() == 0) {
                    // 普通模板
                    if (isGroupTemplateFlag == false) {
                        Example example1 = new Example(ProjectParamTemplateValue.class);
                        example1.createCriteria().andEqualTo("projectId", projectId)
                                .andEqualTo("projectParamId", projectParamTemplate.getProjectParamId())
                                .andEqualTo("paramSuitNumber", paramSuitNUmber)
                                .andEqualTo("paramType", projectParamTemplate.getParamType());
                        List<ProjectParamTemplateValue> projectParamTemplateValueList = projectParamTemplateValueMapper.selectByExample(example1);
                        if (projectParamTemplateValueList.size() > 0) {
                            ProjectParamTemplateValue projectParamTemplateValue = projectParamTemplateValueList.get(0);
                            if ("file".equals(a.getParamType())) {
                                String[] fileValueAndName = projectParamTemplateValue.getParamValue().split("_");
                                if (fileValueAndName.length >= 2) {
                                    a.setParamValue(fileValueAndName[0]);
                                    a.setProjectParamName(fileValueAndName[1]);
                                } else {
                                    a.setParamValue(fileValueAndName[0]);
                                    a.setProjectParamName(projectParamTemplate.getProjectParamName());
                                }
                            } else {
                                a.setParamValue(projectParamTemplateValue.getParamValue());
                            }
                        }
                    } else {
                        // 分组模板显示数据关联选择的常量或附件名称
                        if ("file".equals(a.getParamType())) {
                            a.setProjectParamName(projectParamTemplate.getProjectParamName());
                            a.setParamValue(projectParamTemplate.getGroupTemplateData());
                        } else {
                            a.setProjectParamName(projectParamTemplate.getGroupTemplateData());
                            a.setParamValue(projectParamTemplate.getProjectParamName());
                        }

                    }
                } else {
                    // 自定义
                    a.setParamValue(projectParamTemplate.getParamValue());
                    if ("file".equals(a.getParamType())) {
                        a.setProjectParamName(projectParamTemplate.getProjectParamName());
                    } else {
                        a.setProjectParamName(projectParamTemplate.getParamValue());
                    }
                }
            } else {
                a.setParamValue(projectParamTemplate.getParamValue());
                if ("file".equals(a.getParamType())) {
                    a.setProjectParamName(projectParamTemplate.getProjectParamName());
                }
            }
        }
        return a;
    }

    /**
     * @param id       第一次的ID
     * @param assessId 后续的评估Id
     * @return
     */
    private AssessParam getAssessParamBy(Integer id, Integer assessId) {
        /**
         * 获取评估参数
         */
        AssessParam param = assessParamMapper.selectByPrimaryKey(id);
        //获取对应的评估编号
        Example example = new Example(AssessParam.class);
        example.createCriteria().andEqualTo("nodeId", param.getNodeId()).andEqualTo("assessId", assessId)
                .andEqualTo("indexId", param.getIndexId()).andEqualTo("paramName", param.getParamName())
                .andEqualTo("paramType", param.getParamType()).andEqualTo("nodeKey", param.getNodeKey());
        List<AssessParam> list = assessParamMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return new AssessParam();
    }

    @RequestMapping(value = "assessHandle", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> assessHandle(@RequestBody JSONObject json, HttpServletRequest request) {
        String evalPath = request.getSession().getServletContext().getRealPath("\\resources\\js\\assessdata\\");
        Integer assessId = null;
        //1、保存参数value值
        JSONArray paramJa = json.getJSONArray("params");
        for (int i = 0; i < paramJa.size(); i++) {
            AssessParam param;
            JSONObject jo = (JSONObject) paramJa.get(i);
            if (json.get("isFirstFlag") != null) {
                if ("true".equals(json.getString("isFirstFlag"))) {
                    param = assessParamMapper.selectByPrimaryKey(jo.get("key"));
                } else {
                    param = getAssessParamBy(Integer.valueOf(jo.getString("key")), Integer.valueOf(json.getString("assessId")));
                }
            } else {
                param = assessParamMapper.selectByPrimaryKey(jo.get("key"));
            }

            assessId = param.getAssessId();
            param.setParamValue(jo.getString("value"));
            assessParamMapper.updateByPrimaryKey(param);
        }


        if (assessId == null) {
            assessId = Integer.valueOf((String) json.get("assessId"));
        }
        //2、获取评估对象
        AssessInfo assessInfo = assessInfoMapper.selectByPrimaryKey(assessId);
        assessInfo.setAssessContent((String) json.get("assessContent"));
        assessInfo.setAssessName((String) json.get("assessName"));
//    	assessInfo.setIndexId(param.getIndexId());

        Integer currentLevel = 0; //当前节点 level
        Integer lastLevel = 0; //叶子节点level
        try {
            //3、获取评估的节点集合
            List<ProjectIndex> nodeList = new ArrayList<>();
            ProjectIndex current = projectIndexMapper.selectByPrimaryKey(assessInfo.getNodeId());
            currentLevel = current.getIndexLevel();
            nodeList.add(current);
            Map<Integer, Integer> lastMap = new HashMap<>();
            nodeList = assessService.getSubNode(nodeList, assessInfo.getNodeId(), lastMap);


            List<ProjectIndex> lastIndeList = new ArrayList<>();
            Map<Integer, List<ProjectIndex>> wNode = new HashMap<>();

            if (nodeList.size() == 1) {
                nodeList.get(0).setIsLastNode(1);
            }

            //设置子节点个数
            List<ProjectIndex> childList = new ArrayList<>();
            //3.1循环节点集合 找到叶子节点 去调用评估service获取叶子节点的评估结果
            for (ProjectIndex i : nodeList) {
                if (i.getIndexId() != null && i.getIndexId() != 0) { //表明是指标节点
//                    if (i.getIndexId() != null && i.getIndexId() != 0 && i.getIsLastNode() == 1) { //表明是指标节点，去掉叶子节点判断
                    if (i.getIndexLevel() > lastLevel) {
                        lastLevel = i.getIndexLevel();
                    }
                    lastIndeList.add(i);
                    Example example = new Example(AssessParam.class);
                    example.createCriteria().andEqualTo("nodeId", i.getId()).andEqualTo("assessId", assessInfo.getId());
                    List<AssessParam> list = assessParamMapper.selectByExample(example);
                    com.alibaba.fastjson.JSONObject jo = new com.alibaba.fastjson.JSONObject();
                    for (AssessParam p : list) {
                        if (p.getParamValue() != null) {
                            jo.put(p.getNodeKey() + "", p.getParamValue());
                        }
                    }
                    IndexInfoProcess process = indexEngine.startIndexByProject(assessId, i.getIndexId(), jo, evalPath);
                    if (process.getIndexStatus() != 2 || StringUtil.isEmpty(process.getIndexProcessData())) {
                        String reMsg = process.getIndexProcessData();
                        if (StringUtil.isEmpty(process.getIndexProcessData())) {
                            reMsg = "执行指标错误";
                        }
                        return commonService.returnMsg(0, reMsg);
                    }
                    // 查询条件，orderid是bean的属性名，不是DB的字段名
                    Example projectIndexAssessExample = new Example(ProjectIndexAssess.class);
                    projectIndexAssessExample.createCriteria().andEqualTo("projectIndexId", i.getId()).andEqualTo("assessId", assessInfo.getId())
                            .andEqualTo("projectId", assessInfo.getProjectId());
                    List<ProjectIndexAssess> pIndexAssessList = projectIndexAssessMapper.selectByExample(projectIndexAssessExample);
                    if (pIndexAssessList.size() > 0) {
                        ProjectIndexAssess pAssess = pIndexAssessList.get(0);
                        pAssess.setIndexValue(process.getIndexProcessData());
                        projectIndexAssessMapper.updateByPrimaryKey(pAssess);
                    }
                }else if(i.getIndexChildId() != null && i.getIndexChildId() != 0){//说明设置了子节点
                    childList.add(i);
                }else {//不是权重节点
                    if (wNode.containsKey(i.getIndexLevel())) {
                        List<ProjectIndex> tList = wNode.get(i.getIndexLevel());
                        tList.add(i);
                        wNode.put(i.getIndexLevel(), tList);
                    } else {
                        List<ProjectIndex> wList = new ArrayList<>();
                        wList.add(i);
                        wNode.put(i.getIndexLevel(), wList);
                    }
                }
            }
            //计算当前评估节点的 层级数
            List<ProjectIndex> tempList = new ArrayList<>();
            for (int i = lastLevel; i >= currentLevel; i--) {
                if (wNode.containsKey(i)) {
                    List<ProjectIndex> wNodeList = wNode.get(i);
                    for (ProjectIndex pIndex : wNodeList) {
                        calIndexValue(pIndex, assessInfo);
                    }
                }
            }
            assessInfo.setAssessStatus(2);

            //进行子节点评估赋值
            for( ProjectIndex child: childList){
                ProjectIndex parentIndex = getParent(child.getParentid(), nodeList);
                if(parentIndex!=null){
                    assessChild(child,parentIndex,assessId,evalPath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            assessInfo.setAssessStatus(3);
        } finally {
            assessInfoMapper.updateByPrimaryKey(assessInfo);
        }
        return assessInfo.getAssessStatus() == 2 ? commonService.returnMsg(1, "操作成功") : commonService.returnMsg(1, "操作失败");
    }


    private  ProjectIndex getParent(Integer parentId,List<ProjectIndex> list ){
        for (ProjectIndex projectIndex: list) {
            if(parentId.equals(projectIndex.getId())){
                return  projectIndex;
            }
        }
        return  null ;
    }

    /**
     * 评估节点
     * @param child 子节点
     * @param parent 父节点
     * @param parentAssessId
     * @param evalPath
     */
    private  void assessChild(ProjectIndex child,ProjectIndex parent,Integer parentAssessId,String evalPath){

        //1、获取子节点的父节点
        //2、插入评估数据
        Date now = new Date();
        Integer userId = Integer.parseInt(baseService.getUserId());
        AssessInfo assessInfo= new AssessInfo();
        assessInfo.setUpdateTime(now);
        assessInfo.setCreateTime(now);
        assessInfo.setUpdateUid(userId);
        assessInfo.setCreateUid(userId);
        assessInfo.setAssessStatus(2);//
        assessInfo.setIsdelete(0);
        assessInfo.setProjectId(child.getProjectId());
        assessInfo.setNodeId(child.getId());
        assessInfo.setAssessName(child.getIndexName());
        assessInfoMapper.insertSelective(assessInfo);
        //3.1复制节点信息
        Integer assessInfoId = assessInfo.getId();
        Example example = new Example(ProjectIndexAssess.class);
        example.createCriteria().andEqualTo("assessId", parentAssessId);
        List<ProjectIndexAssess> list = projectIndexAssessMapper.selectByExample(example);//获取到了评估的实例
        //3.2按照输入插入项目指标节点
        for (int i = 0; i < list.size(); i++) {
            ProjectIndexAssess projectIndexAssess = list.get(i);
            projectIndexAssess.setId(null);
            projectIndexAssess.setAssessId(assessInfoId);
            projectIndexAssessMapper.insertSelective(projectIndexAssess);
        }
        // 3.3 插入指标实例节点
        Example processExample = new Example(IndexInfoProcess.class);
        processExample.createCriteria().andEqualTo("indexId",parent.getIndexId())
                .andEqualTo("businessId",parentAssessId)
                .andEqualTo("processType",1);
        List<IndexInfoProcess> proList = indexInfoProcessMapper.selectByExample(processExample);
        if(proList.size()>0){
            //添加对子指标的支撑是否显示的支撑
            IndexInfo pIndexInfo = indexInfoMapper.selectByPrimaryKey(parent.getIndexId());
            //构建初始化环境
            IndexDefinitions indexDefinitions = indexRepositoryService.getIndexDefinitions(pIndexInfo,evalPath);//构建初始化环境
            IndexInfoProcess process = proList.get(0);
            Integer oldId = process.getId();
            process.setId(null);
            process.setBusinessId(assessInfoId);
            indexInfoProcessMapper.insertSelective(process);

            IndexModel childModel = indexModelMapper.selectByPrimaryKey(child.getIndexChildId());
            //子节点
            IndexNode childNode = indexDefinitions.getNodeByKey(childModel.getNodeKey());

            //插入指定实例的子节点
            Example nodeProcessExample = new Example(IndexNodeProcess.class);
            nodeProcessExample.createCriteria().andEqualTo("indexProcessId",oldId);
            List<IndexNodeProcess> nodeList = indexNodeProcessMapper.selectByExample(nodeProcessExample);
            for (IndexNodeProcess indexNodeProcess:nodeList) {
                //获取定义的节点
                IndexNode indexNode = indexDefinitions.getNodeByKey(indexNodeProcess.getNodeKey());
                //是否子节点
                if(isChildNode(childNode,indexNode)){
                    //指标节点Id
                    Integer indexNodeId = indexNodeProcess.getId();
                    indexNodeProcess.setId(null);
                    indexNodeProcess.setIndexProcessId(process.getId());
                    indexNodeProcessMapper.insertSelective(indexNodeProcess);
                    //是否是图
                    if(isPicture(indexNode)){
                        String oldFileName =parentAssessId+"_"+process.getIndexId()+"_"+indexNodeProcess.getNodeKey()+"_"+indexNode.getNodeType().getValue()+".js";
                        String newFileName =assessInfoId+"_"+process.getIndexId()+"_"+indexNodeProcess.getNodeKey()+"_"+indexNode.getNodeType().getValue()+".js";
                        try {
                            FileUtils.copyFile(new File(evalPath+File.separator+oldFileName),new File(evalPath+File.separator+newFileName));
                        } catch (IOException e) {//复制文件错误
                            e.printStackTrace();
                        }
                    }
                    if(pIndexInfo.getVisibleChild()==0){
                        indexNodeProcessMapper.deleteByPrimaryKey(indexNodeId);
                    }

                }
            }
        }
    }


    /**
     * 判断是否是子节点
     * @param node
     * @param indexNode
     * @return
     */
    private boolean isChildNode(IndexNode node,IndexNode indexNode){
        Collection<IndexNode> nodes = indexNode.getPreNodes();;
        for(IndexNode n : nodes){
            if(n.getKey().equals(node.getKey())){
                return  true ;
            }
        }
        return  false ;
    }





    /**
     * 判断是否为当前节点的子节点
     * @param indexNode
     * @return
     */
    private boolean isPicture(IndexNode indexNode ){
        if(indexNode.getNodeType()== NodeType.Bar||indexNode.getNodeType()== NodeType.Line||indexNode.getNodeType()== NodeType.Hline
                ||indexNode.getNodeType()== NodeType.Scatter||indexNode.getNodeType()== NodeType.Hscatter){
            return  true ;
        }
        return  false;
    }


    /**
     * 计算当前节点的权重值
     *
     * @param index
     */
    private void calIndexValue(ProjectIndex index, AssessInfo assessInfo) {
        Example example = new Example(ProjectIndexAssess.class);
        example.createCriteria().
                andEqualTo("parentid", index.getId()).andEqualTo("assessId", assessInfo.getId())
                .andEqualTo("projectId", assessInfo.getProjectId());
        List<ProjectIndexAssess> list = projectIndexAssessMapper.selectByExample(example);
        BigDecimal b = BigDecimal.ZERO;
        for (ProjectIndexAssess last : list) {
            if (last.getIndexValue() != null) {
                b = b.add(new BigDecimal(last.getIndexValue()).multiply(new BigDecimal(last.getWeightCurrent())));
            }
        }
//		index.setIndexValue(b.toString());
//		projectIndexMapper.updateByPrimaryKey(index);
        Example projectIndexAssessExample = new Example(ProjectIndexAssess.class);
        projectIndexAssessExample.createCriteria().andEqualTo("projectIndexId", index.getId()).andEqualTo("assessId", assessInfo.getId())
                .andEqualTo("projectId", assessInfo.getProjectId());
        List<ProjectIndexAssess> pIndexAssessList = projectIndexAssessMapper.selectByExample(projectIndexAssessExample);
        if (pIndexAssessList.size() > 0) {
            ProjectIndexAssess pAssess = pIndexAssessList.get(0);
            pAssess.setIndexValue(b.toString());
            projectIndexAssessMapper.updateByPrimaryKey(pAssess);
        }
    }


    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null)
            return null;

        Map<String, Object> map = new HashMap<String, Object>();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter != null ? getter.invoke(obj) : null;
            map.put(key, value);
        }

        return map;
    }


    public List<Map<String, Object>> parseTree(List<Map<String, Object>> list) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        Map<String, Object> tmpMap = new HashMap<String, Object>();
        for (int i = 0, l = list.size(); i < l; i++) {
            tmpMap.put(String.valueOf(list.get(i).get("id")), list.get(i));
        }
        for (int i = 0, l = list.size(); i < l; i++) {
            Map<String, Object> map = list.get(i);
            //tmpMap存储的均为id为key的键值对，如果以pid为key可以取出对象，则表明该元素是父级元素
            if (tmpMap.get(map.get("parentid").toString()) != null && (map.get("id") != map.get("parentid"))) {
                //给当前这个父级map对象中添加key为children的ArrayList
                if ((tmpMap.get(map.get("parentid").toString()) != null) && ((Map<String, Object>) tmpMap.get(map.get("parentid").toString())).get("children") == null) {
                    ((Map<String, Object>) tmpMap.get(map.get("parentid").toString())).put("children", new ArrayList<Map<String, Object>>());
                }
                Map<String, Object> tmap = (Map<String, Object>) tmpMap.get(map.get("parentid").toString());
                ArrayList<Map<String, Object>> tArrayList = (ArrayList<Map<String, Object>>) tmap.get("children");
                tArrayList.add(list.get(i));
                //没有父节点
            } else {
                resultList.add(list.get(i));
            }
        }

        for (int i = 0, l = list.size(); i < l; i++) {
            System.out.println("原始数据是否变化：" + list.get(i));
        }

        Iterator<Entry<String, Object>> iterator = tmpMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, Object> entry = iterator.next();
            System.out.println("遍历之后：" + entry.getKey() + ":" + entry.getValue());
        }

        System.out.println(JSONArray.fromObject(resultList));
        return resultList;
    }


    int tmpChildrenLen = 0;

    public int getChildrenNum(String id, JSONObject json) {

        if (json.containsKey("children") && json.getJSONArray("children") != null && json.getJSONArray("children").size() > 0) {
            if (json.getString("id") == id) {
                //console.log(d.children.length);
                tmpChildrenLen += json.getJSONArray("children").size();
            }
            JSONArray tmp = json.getJSONArray("children");
            for (Object object : tmp) {
                JSONObject jsonObject = (JSONObject) object;
                getChildrenNum(jsonObject.getString("id"), jsonObject);
            }

        }
        return tmpChildrenLen;

    }

    public void createTable(JSONObject jsonObject, StringBuffer table) {

        tmpChildrenLen = 1;
        String title = jsonObject.getString("indexName");
        String id = jsonObject.getString("id");
        String td = "<td rowspan=\"" + getChildrenNum(id, jsonObject) + "\">" + title + "</td>";
        table.append("<tr>").append(td).append("</tr>");
        if (jsonObject.containsKey("children") && jsonObject.getJSONArray("children") != null && jsonObject.getJSONArray("children").size() > 0) {
            //console.log(data.children);

            JSONArray tmp = jsonObject.getJSONArray("children");
            for (Object object : tmp) {
                JSONObject json = (JSONObject) object;
                createTable(json, table);
            }

        }

    }

    public void exportWord(ExportDomain exportDomain, HttpServletResponse response, String exportWay) {
        CustomXWPFDocument document = new CustomXWPFDocument();
        try {
            JSONObject jsonT = JSONObject.fromObject(exportDomain);

            String infoStr = jsonT.getString("info");

            JSONObject json = JSONObject.fromObject(infoStr);

            JSONArray indexResultJson = json.getJSONArray("indexResult");

            WordBean wordBean = new WordBean(document);

            XWPFParagraph p = wordBean.createParagraph();
            //标题
            wordBean.addTitle(exportDomain.getTitle());
            wordBean.br();
            //概述
            wordBean.addTitle_2("1、" + json.getString("desc").trim());
            wordBean.br();
            //指标体系
            wordBean.addTitle_2("2、" + "指标体系");
            wordBean.addPicture(json.getString("indexSer"), json.getString("tmpW"), json.getString("tmpH"));
            wordBean.br();
            wordBean.addBody("图1 指标体系", true);
            //评估结果
            wordBean.addTitle_2("3、" + "评估结果");
            wordBean.addAssessResultTable(assessService.getAssessTableByAssertId(Integer.valueOf(exportDomain.getId())));
//			wordBean.addPicture(json.getString("accessResult"),json.getString("tmpW"),json.getString("tmpH"));
            wordBean.br();
            //指标结果
            int titleIndex = 1;
            int picIndex = 2;
            int tableIndex = 1;
            for (Object object : indexResultJson) {
                JSONObject jsonObj = (JSONObject) object;
                JSONArray indexPicArrJson = null;
                JSONArray indexTableArrJson = null;
                String indexContent = null;
                if (jsonObj.get("indexContent") != null) {
                    indexContent = jsonObj.getString("indexContent");
                    indexPicArrJson = jsonObj.getJSONArray("indexPicArr");
                    indexTableArrJson = jsonObj.getJSONArray("indexTableArr");
                    wordBean.addContent("3." + indexContent);
                } else {
                    indexPicArrJson = jsonObj.getJSONArray("indexPicArr");
                    indexTableArrJson = jsonObj.getJSONArray("indexTableArr");
                }
//					wordBean.addContent("3." + indexContent);

                wordBean.br();
                titleIndex++;
                for (Object object2 : indexPicArrJson) {
                    JSONObject jsonObj2 = (JSONObject) object2;

                    String indexPic = jsonObj2.getString("indexPic");
                    String indexPicW = jsonObj2.getString("indexPicW");
                    String indexPicH = jsonObj2.getString("indexPicH");
                    wordBean.addPicture(indexPic, indexPicW, indexPicH);
                    wordBean.br();
                    wordBean.addBody("图" + picIndex, true);
                    picIndex++;
                }

                for (Object object2 : indexTableArrJson) {
                    JSONObject jsonObj2 = (JSONObject) object2;

                    String title = jsonObj2.getString("title");
                    JSONArray jsonArray = jsonObj2.getJSONArray("data");

                    String[] headers = {};
                    if (jsonArray.size() > 0) {
                        JSONArray jsonArray2 = jsonArray.getJSONArray(0);
                        headers = new String[jsonArray2.size()];
                        for (int i = 0; i < jsonArray2.size(); i++) {
                            headers[i] = jsonArray2.getString(i);
                        }

                    }
                    List<Map<String, Object>> dataset = new ArrayList<>();
                    for (int i = 1; i < jsonArray.size(); i++) {
                        JSONArray row = jsonArray.getJSONArray(i);
                        Map<String, Object> tmpMap = new HashMap<>();
                        for (int j = 0; j < headers.length; j++) {

                            tmpMap.put(headers[j], row.getString(j));
                        }
                        dataset.add(tmpMap);
                    }

                    System.out.println(JSONArray.fromObject(headers));
                    System.out.println(JSONArray.fromObject(dataset));
                    wordBean.addTableHasMainTitle(title, headers, dataset, p, exportWay);
                    wordBean.br();
                    wordBean.addBody("表" + tableIndex, true);
                    tableIndex++;
                }

            }
            //评估人员
            wordBean.addBody(json.getString("foot"), false);
            wordBean.br();

//			String[] headers = {"标题1","标题2","标题3","标题4"};
//			List<Map<String,Object>> dataset = new ArrayList<>();
//			Map<String,Object> map1 = new HashMap<>();
//			map1.put("标题1", "字段1");
//			map1.put("标题2", "字段2");
//			map1.put("标题3", "字段3");
//			map1.put("标题4", "字段4");
//			Map<String,Object> map2 = new HashMap<>();
//			map2.put("标题1", "字段11");
//			map2.put("标题2", "字段21");
//			map2.put("标题3", "字段31");
//			map2.put("标题4", "字段41");
//			dataset.add(map1);
//			dataset.add(map2);
//			wordBean.addTable(headers, dataset, p);
//			wordBean.br();
//			wordBean.addPicture(base64Info,imgWidth,imgHeight);

            if (("export").equals(exportWay)) {
                document.write(response.getOutputStream());

            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @RequestMapping("/exportword")
    public void exportword(ExportDomain exportDomain, HttpServletResponse response) {

        String exportWay = "export";
        String filename = exportDomain.getTitle();
        try {
            // path是指欲下载的文件的路径。

            // 取得文件名。
//			String filename = file.getName();
//			// 以流的形式下载文件。
//			InputStream fis = new BufferedInputStream(new FileInputStream(file));
//			byte[] buffer = new byte[fis.available()];
//			fis.read(buffer);
//			fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            String fileName = URLEncoder.encode(filename, "UTF-8");
            if (fileName.length() > 0) { //解决IE 6.0 bug
                fileName = new String(filename.getBytes("GBK"), "ISO-8859-1");
            }
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".docx");
//			response.addHeader("Content-Length", "" + file.length());
            response.setContentType("application/octet-stream");
            exportWord(exportDomain, response, exportWay);
//			OutputStream outs = new BufferedOutputStream(response.getOutputStream());
//			outs.write(buffer);
//			outs.flush();
//			outs.close();
//			file.delete();
        } catch (IOException e) {
            log.error("下载文档(WordUtil)出错：【msg：" + e.getMessage() + "】 ");
            e.printStackTrace();
        }
    }


    /**
     * 模板导出
     *
     * @param exportDomain
     * @param response
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    @RequestMapping("/exportTemplateWord")
    public void exportTemplateWord(ExportDomain exportDomain, HttpServletResponse response) throws IOException, InvalidFormatException {
        String exportWay = "templateExport";
        Map<String, Object> param = new HashMap<String, Object>();

        ProjectIndexAssess projectIndexAssess = assessService.getProjectIndexAssess(Integer.valueOf(exportDomain.getId()));
        Integer id = projectIndexAssess.getProjectId();


        // 获取项目对应的模板id
        ProjectInfo projectInfo = projectInfoMapper.selectByPrimaryKey(id);
        WordTemplate wordTemplate = wordTemplateMapper.selectByPrimaryKey(projectInfo.getTemplateId());
        // 获取模板路径
        String wordTemplatePath = wordTemplate.getFilePath();
        WordTemplateImpl wordTemplateImpl = new WordTemplateImpl();
        // 获取模板标签
        Map<String, String> tagMap = wordTemplateImpl.getTagInfo(wordTemplatePath);
        // 获取项目标签
        List<ProjectLabel> projectLabelList = projectService.getProjectLabels(id);

        // 数据转换
        JSONObject jsonT = JSONObject.fromObject(exportDomain);
        String infoStr = jsonT.getString("info");
        JSONObject json = JSONObject.fromObject(infoStr);

        //构建模板标签
        Map<String, Object> initParam = new HashMap<>();
        initParam.put(exportDomain.getId(), assessService.getAssessTableByAssertId(Integer.valueOf(exportDomain.getId())));
        LabelMap labelMap = new LabelMap(jsonT, projectLabelList, Integer.valueOf(exportDomain.getId()), initParam, indexNodeProcessMapper);

        // 判断调用什么方法替换模板标签
        for (ProjectLabel projectLabel : projectLabelList) {
            for (Map.Entry<String, String> entry : tagMap.entrySet()) {
                if (projectLabel.getLabel().equals(entry.getKey())) {
                    switch (projectLabel.getType()) {
                        case 1:
                        case 7:
                            // 文本替换
                            param = wordTemplateImpl.textReplace(param, labelMap.getLabelValue(projectLabel.getLabel()), projectLabel.getLabel());
                            break;
                        case 2:
                            // 树替换
                            param = wordTemplateImpl.tree2Replace(param, labelMap.getLabelValue(projectLabel.getLabel()), projectLabel.getLabel());
                            break;
                        case 3:
                            // 树替换
                            AssessTable assessTable = assessService.getAssessTableByAssertId(Integer.valueOf(exportDomain.getId()));
                            param = wordTemplateImpl.tree3Replace(param, assessTable, projectLabel.getLabel());
                            break;
                        case 4:
                            // 数值替换
                            param = wordTemplateImpl.numberReplace(param, labelMap.getLabelValue(projectLabel.getLabel()), projectLabel.getLabel());
                            break;
                        case 5:
                            // 图片替换
                            param = wordTemplateImpl.pictureReplace(param, labelMap.getLabelValue(projectLabel.getLabel()), projectLabel.getLabel());
                            break;
                        case 6:
                            // 表格替换
                            param = wordTemplateImpl.tableReplace(param, labelMap.getLabelValue(projectLabel.getLabel()), projectLabel.getLabel());
                            break;
                        default:
                            // 文本替换
                            param = wordTemplateImpl.textReplace(param, labelMap.getLabelValue(projectLabel.getLabel()), projectLabel.getLabel());
                    }
                }
            }
        }

//        for (Map.Entry<String, String> tagEntry : tagMap.entrySet()) {
//            if (!param.containsKey(tagEntry.getKey())){
//                param.put(tagEntry.getKey(),"");
//            }
//        }
        // 读取模板内容
        CustomXWPFDocument doc = null;
        OPCPackage pack = POIXMLDocument.openPackage(wordTemplatePath);
        doc = new CustomXWPFDocument(pack);

        WordBean wordBean = new WordBean(doc);
        // 模板标签替换
        doc = wordBean.generateWord(param, exportWay);

        String templateFileName = wordTemplatePath.substring(wordTemplatePath.lastIndexOf("\\") + 1);
        response.reset();
        String fileName = URLEncoder.encode(templateFileName, "UTF-8");
        if (fileName.length() > 0) { //解决IE 6.0 bug
            fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
        }
        // 设置response的Header
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentType("application/octet-stream");

        // 模板下载
        doc.write(response.getOutputStream());
    }

    @RequestMapping(value = "/exportexcel")
    public void exportExcel(String id, HttpServletRequest request, HttpServletResponse response) {

        IndexNodeProcess indexNodeProcess = indexNodeProcessMapper.selectByPrimaryKey(id);

        if (indexNodeProcess != null) {
            JSONObject json = JSONUtil.toJSONObject(indexNodeProcess.getNodeInputParamExp());

            String title = json.getString("title");
            Integer colspan = json.getInt("colspan");
            JSONArray data = json.getJSONArray("data");

            JSONArray headersJsonArray = (JSONArray) data.get(0);
            String[] headers = new String[headersJsonArray.size()];
            String[] fieldNames = new String[headersJsonArray.size()];
            List<Map<String, Object>> dataList = new ArrayList<>();
            for (int i = 0; i < headers.length; i++) {
                headers[i] = headersJsonArray.getString(i);
                fieldNames[i] = "key" + i;
            }
            for (int i = 1; i < data.size(); i++) {
                JSONArray strs = (JSONArray) data.get(i);
                Map<String, Object> map = new HashMap<>();
                for (int j = 0; j < fieldNames.length; j++) {
                    map.put(fieldNames[j], strs.get(j));
                }
                dataList.add(map);
            }
            try {
                ExcelUtils.exportExcel(title, headers, fieldNames, dataList, response, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    /**
     * 保存项目选择的模板信息
     *
     * @param reqJson
     * @return
     */
    @RequestMapping(value = "templateExport")
    @ResponseBody
    public Map<String, Object> templateExport(String reqJson) {
        JSONObject json = JSONUtil.toJSONObject(reqJson);
        ProjectInfo projectInfo = JSONUtil.toObject(reqJson.toString(), ProjectInfo.class);
        projectInfo = projectInfoMapper.selectByPrimaryKey(projectInfo.getId());
        if (projectInfo.getTemplateId() == null || "".equals(projectInfo.getTemplateId())) {
            return commonService.returnMsg(0, "请先绑定模板!");
        } else {
            return commonService.returnMsg(1, "已绑定模板");
        }
    }
}
