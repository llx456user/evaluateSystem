package com.tf.base.platform.controller;

import com.tf.base.common.domain.*;
import com.tf.base.common.service.BaseService;
import com.tf.base.common.service.CommonService;
import com.tf.base.common.service.ServiceStatus;
import com.tf.base.datasource.service.DatasourceSqlService;
import com.tf.base.datasource.service.FileService;
import com.tf.base.go.type.NodeType;
import com.tf.base.platform.domain.*;
import com.tf.base.platform.service.IndexService;
import com.tf.base.platform.service.ModelInfoService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/platform/")
public class IndexController {

    @Autowired
    private BaseService baseService;
    @Autowired
    private ModelInfoService modelInfoService;

    @Autowired
    private IndexService indexService;
    @Autowired
    private FileService fileService;
    @Autowired
    private DatasourceSqlService datasourceSqlService;
    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "selectFileColumns", method = RequestMethod.POST)
    @ResponseBody
    public List selectFileColumns(String fileId, String fileType) {
        List<DatasourceFileTitle> list = new ArrayList<DatasourceFileTitle>();
        if (NodeType.File.getValue().equals(fileType)) {
            DatasourceFileTitle tiltle = new DatasourceFileTitle();
            tiltle.setColumnName("filepath");
            list.add(tiltle);
            list.addAll(indexService.getFileTitle(Integer.valueOf(fileId), 1));
        } else {
            list = indexService.getFileTitle(Integer.valueOf(fileId), 2);
        }
        return list;
    }

    @RequestMapping(value = "getModelParams", method = RequestMethod.POST)
    @ResponseBody
    public Map getModelParams(String modelId) {
        Map map = new HashMap();
        List<ModelParmeter> list = modelInfoService.getModelPramas(modelId);
        map.put("formulaList", list);
        map.put("modelinfo", modelInfoService.findModelByID(Integer.valueOf(modelId)));
        return map;
    }


    @RequestMapping(value = "indexList", method = RequestMethod.GET)
    public String indexList(Model model) {
        return "platform/indexList";
    }

    @RequestMapping(value = "indexAdd", method = RequestMethod.GET)
    public String indexAdd(Model model, String categoryId) {
        model.addAttribute("categoryId", categoryId);
        IndexInfo bean = new IndexInfo();
        bean.setIndexCategoryid(Integer.valueOf(categoryId));
        model.addAttribute("bean", bean);
        readyIndexInfo(model);
        return "platform/indexAdd";
    }

    private void readyIndexInfo(Model model) {
        //模型
        List<ModelCategoryParam> mcList = new ArrayList<>();
        List<ModelCategory> mcs = modelInfoService.findModel();
        for (ModelCategory mc : mcs) {
            ModelCategoryParam mcg = new ModelCategoryParam();
            mcg.setModelCategory(mc);
            mcg.setModelInfoList(modelInfoService.selectModelByCatId(String.valueOf(mc.getId())));
            mcList.add(mcg);
        }
        model.addAttribute("mcList", mcList);
        //数据源
        List<DatasourceDb> dbList = modelInfoService.findDbType();
        List<IndexDbInfoParams> dbTypeList = new ArrayList<>();
        for (DatasourceDb db : dbList) {
            IndexDbInfoParams dbInfoParams = new IndexDbInfoParams();
            dbInfoParams.setDatasourceDb(db);
            dbInfoParams.setDatasourceSqlList(datasourceSqlService.queryListBySourceID(db.getId()));
            dbTypeList.add(dbInfoParams);
        }
        model.addAttribute("dbTypeList", dbTypeList);
        //文件分类
        List<DatasourceCategory> categories = fileService.queryCategoryList();
        List<IndexFileInfoParams> indexFileList = new ArrayList<>();
        for (DatasourceCategory dc : categories) {
            IndexFileInfoParams indexFileInfoParams = new IndexFileInfoParams();
            indexFileInfoParams.setDatasourceCategory(dc);
            indexFileInfoParams.setDatasourceFileList(fileService.queryFileListByCategoryID(dc.getId()));
            indexFileList.add(indexFileInfoParams);
        }
//
        model.addAttribute("indexFileList", indexFileList);
        //评估结果数据
        List<DatasourceFile> accessFiles = fileService.queryFileListByCategoryID(FileService.FileCategoryType_Assess);
        model.addAttribute("accessFiles", accessFiles);
        //数据筛选
        List<DatasourceFile> dataFiles = fileService.queryFileListByCategoryID(FileService.FileCategoryType_Data);
        model.addAttribute("dataFiles", dataFiles);
    }

    @RequestMapping(value = "indexEdit", method = RequestMethod.GET)
    public String indexEdit(Model model, String id) {
        if (!StringUtils.isEmpty(id)) {

            Integer pId = Integer.valueOf(id);
            IndexInfo bean = indexService.getIndexInfoById(pId);
            model.addAttribute("bean", bean);
            readyIndexInfo(model);
        }
        return "platform/indexAdd";
    }


    @RequestMapping(value = "deleteIndex", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteIndex(String id) {
        int i = indexService.deleteIndex(id);
        return i > 0 ? commonService.returnMsg(1, "删除成功") : commonService.returnMsg(0, "删除失败");
    }

    @RequestMapping(value = "selectModel", method = RequestMethod.POST)
    @ResponseBody
    public List selectModel(String catId) {
        List<ModelInfo> mxList = modelInfoService.selectModelByCatId(catId);
        return mxList;
    }

    @RequestMapping(value = "getIndexInfo", method = RequestMethod.POST)
    @ResponseBody
    public IndexInfo getIndexInfo(String id) {
        return indexService.getIndexInfoById(Integer.valueOf(id));
    }

    //指标保存
    @RequestMapping(value = "indexSave", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> indexSave(IndexInfo indexInfo, HttpServletRequest request) {

        String evalPath = request.getSession().getServletContext().getRealPath("\\resources\\js\\assessdata\\");
        ServiceStatus serviceStatus = indexService.saveIndex(indexInfo, evalPath);
        return commonService.returnMsg(serviceStatus.getStatus().getValue(), serviceStatus.getMsg());
    }

    //指标自动保存
    @RequestMapping(value = "autoindexSave", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> autoindexSave(IndexInfo indexInfo, HttpServletRequest request) {
        String evalPath = request.getSession().getServletContext().getRealPath("\\resources\\js\\assessdata\\");
        ServiceStatus serviceStatus = indexService.autosaveIndex(indexInfo, evalPath);
        return commonService.returnMsg(serviceStatus.getStatus().getValue(), serviceStatus.getMsg());
    }

    //指标保存
    @RequestMapping(value = "copyIndexSave", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> copyIndexSave(IndexInfo indexInfo, HttpServletRequest request) {

        String evalPath = request.getSession().getServletContext().getRealPath("\\resources\\js\\assessdata\\");
        ServiceStatus serviceStatus = indexService.copySaveIndex(indexInfo, evalPath);
        return commonService.returnMsg(serviceStatus.getStatus().getValue(), serviceStatus.getMsg());
    }

    //节点调试
    @RequestMapping(value = "indexNodeDebug", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> indexNodeDebug(IndexInfoParam indexInfo, HttpServletRequest request) {
        String evalPath = request.getSession().getServletContext().getRealPath("\\resources\\js\\assessdata\\");
        //获取节点key，按照key进行调试
        ServiceStatus serviceStatus = indexService.debugIndexNode(indexInfo, evalPath);
        return commonService.returnMsg(serviceStatus.getStatus().getValue(), serviceStatus.getMsg());
    }

    @RequestMapping(value = "indexPage", method = RequestMethod.GET)
    public String indexPage(Model model) {
        return "platform/indexList";
    }

    @RequestMapping(value = "tplPage", method = RequestMethod.GET)
    public String tplPage(Model model, String frameid) {
        model.addAttribute("frameid", frameid);
        return "platform/tplList";
    }

    @RequestMapping(value = "indexTest", method = RequestMethod.GET)
    public String indexTest(Model model, String indexId, String nodeKey) {
        IndexNodeProcess info = indexService.getIndexNodeProcessByKey(Integer.valueOf(indexId), Integer.valueOf(nodeKey));
        model.addAttribute("bean", info);
        return "platform/indexTest";
    }

    @ResponseBody
    @RequestMapping(value = "indexList", method = RequestMethod.POST)
    public Map<String, Object> indexList(ModelInfoParams params) {
        Map<String, Object> result = new HashMap<String, Object>();
        int total = indexService.queryCount(params);
        result.put("total", total);
        if (total == 0) {
            result.put("rows", new ArrayList<IndexInfo>());
            return result;
        } else {
            int start = (params.getPage() - 1) * params.getRows();
            List<IndexInfoQueryResult> rows = indexService.queryList(params, start);
            result.put("rows", rows);
            return result;
        }
    }

    @ResponseBody
    @RequestMapping(value = "indexChildList", method = RequestMethod.POST)
    public Map<String, Object> indexChildList(ModelInfoParams params) {
        Map<String, Object> result = new HashMap<String, Object>();
        int total = indexService.queryIndexChildCount(params);
        result.put("total", total);
        if (total == 0) {
            result.put("rows", new ArrayList<IndexModel>());
            return result;
        } else {
            int start = (params.getPage() - 1) * params.getRows();
            List<IndexModelQueryResult> rows = indexService.queryIndexChildList(params, start);
            result.put("rows", rows);
            return result;
        }
    }

    @ResponseBody
    @RequestMapping(value = "categoryList", method = RequestMethod.POST)
    public Object categoryList() {
        return indexService.categoryList();
    }

    @ResponseBody
    @RequestMapping(value = "categoryCreate", method = RequestMethod.POST)
    public String categoryCreate(IndexCategory category) {
        category.setIsdelete(0);
        category.setCreateTime(new Date());
        category.setCreateUid(Integer.parseInt(baseService.getUserId()));
        indexService.categoryCreate(category);
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "categoryUpdate", method = RequestMethod.POST)
    public String categoryUpdate(IndexCategory category) {
        category.setUpdateTime(new Date());
        category.setUpdateUid(Integer.parseInt(baseService.getUserId()));
        indexService.categoryUpdate(category);
        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "categoryRemove", method = RequestMethod.POST)
    public String categoryRemove(Integer categoryId) {
        indexService.categoryRemove(categoryId);
        return "success";
    }

    //另存为
    @ResponseBody
    @RequestMapping(value = "otherSaveTpl", method = RequestMethod.POST)
    public Map<String, Object> otherSaveTpl(String tplName, String id, String indexData) {
        indexService.otherSaveTpl(id, indexData, tplName);
        return commonService.returnSucessMsg("保存成功");
    }


    @RequestMapping(value = "setCopyIndex",method = RequestMethod.GET)
    public String setCopyIndex(Model model, String categoryId, String indexframeid) {
        model.addAttribute("indexframeid", indexframeid);
        model.addAttribute("categoryId", categoryId);
        return "platform/copyIndex";
    }


    @RequestMapping(value = "setindexValue",method = RequestMethod.GET)
    public Object setmodelValue(Model model, String indexId, String categoryId) {




            Integer pId = Integer.valueOf(indexId);
            IndexInfo bean = indexService.getIndexInfoById(pId);

            model.addAttribute("categoryId", categoryId);
            bean.setIndexCategoryid(Integer.valueOf(categoryId));
            bean.setIndexContent("");
            bean.setIndexName("");



            model.addAttribute("bean", bean);
            readyIndexInfo(model);
        return "platform/copyIndexInfo";



    }
}