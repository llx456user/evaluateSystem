package com.tf.base.platform.service;

import com.tf.base.common.domain.*;
import com.tf.base.common.persistence.*;
import com.tf.base.common.service.BaseService;
import com.tf.base.common.service.ServiceStatus;
import com.tf.base.common.utils.DateUtil;
import com.tf.base.datasource.service.FileService;
import com.tf.base.go.define.DeploymentIndex;
import com.tf.base.go.define.IndexDefinitions;
import com.tf.base.go.define.IndexNode;
import com.tf.base.go.engine.IndexEngine;
import com.tf.base.go.service.IndexTaskResult;
import com.tf.base.platform.domain.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import com.tf.base.common.domain.IndexInfo;

import java.util.*;

/**
 * Created by wanquan on 2018/3/24.
 */
@Service
public class IndexService {
    @Autowired
    private BaseService baseService;
    @Autowired
    IndexInfoMapper indexInfoMapper;
    @Autowired
    IndexNodeProcessMapper indexNodeProcessMapper;
    @Autowired
    IndexCategoryMapper indexCategoryMapper;
    @Autowired
    DatasourceFileTitleMapper datasourceFileTitleMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private IndexModelMapper indexModelMapper;
    @Autowired
    @Qualifier("indexEngine")
    private IndexEngine indexEngine;
    @Autowired
    private TempletInfoMapper templetInfoMapper;


    /**
     * 获取index按照ID
     *
     * @param id
     * @return
     */
    public IndexInfo getIndexInfoById(Integer id) {
        return indexInfoMapper.selectByPrimaryKey(id);
    }

    public IndexNodeProcess getIndexNodeProcessByKey(Integer indexId, Integer nodeKey) {
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("indexId", indexId);
        paramMap.put("nodeKey", nodeKey);
        List<IndexNodeProcess> list = indexNodeProcessMapper.getNodeProcess(paramMap);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return new IndexNodeProcess();
    }


    /**
     * 获取文件列标题
     *
     * @param fileid
     * @param filetype 类型：1-文件，2-sql
     */
    public List<DatasourceFileTitle> getFileTitle(Integer fileid, Integer filetype) {
        DatasourceFileTitle pFileParam = new DatasourceFileTitle();
        pFileParam.setType(filetype);
        pFileParam.setFileId(fileid);
        pFileParam.setIsdelete(0);
        return datasourceFileTitleMapper.select(pFileParam);
    }

    public List<IndexCategory> categoryList() {
        IndexCategory category = new IndexCategory();
        category.setIsdelete(0);
        return indexCategoryMapper.select(category);
    }

    public void categoryCreate(IndexCategory category) {
        indexCategoryMapper.insert(category);
    }

    public void categoryUpdate(IndexCategory category) {
        IndexCategory updateObj = indexCategoryMapper.selectByPrimaryKey(category.getId());
        if (null != updateObj) {
            updateObj.setUpdateUid(category.getUpdateUid());
            updateObj.setUpdateTime(category.getUpdateTime());
            updateObj.setCategoryName(category.getCategoryName());
            indexCategoryMapper.updateByPrimaryKey(updateObj);
        }
    }

    public void categoryRemove(Integer categoryId) {
        indexCategoryMapper.deleteById(categoryId);

        indexInfoMapper.deleteByCategoryId(categoryId);
    }

    public int deleteIndex(String id){
        Integer pId = Integer.valueOf(id);
        Integer userid = Integer.parseInt(baseService.getUserId());
        IndexInfo bean = indexInfoMapper.selectByPrimaryKey(pId);
        bean.setIsdelete(1);
        bean.setUpdateUid(userid);
        bean.setUpdateTime(new Date());
        return  indexInfoMapper.updateByPrimaryKeySelective(bean);
    }

    public int queryCount(ModelInfoParams params) {
        return indexInfoMapper.getCount(params);
    }

    /**
     * 测试列表
     *
     * @param params
     * @param start
     * @return
     */
    public List<IndexInfoQueryResult> queryList(ModelInfoParams params, int start) {
        List<IndexInfoQueryResult> resultList = new ArrayList<>();
        List<IndexInfo> list = indexInfoMapper.selectList(params, start);
        if (list != null) {
            for (IndexInfo indexInfo : list) {
                IndexInfoQueryResult target = new IndexInfoQueryResult();
                BeanUtils.copyProperties(indexInfo, target);
                target.setUpdateTimeStr(DateUtil.TimeToString(indexInfo.getUpdateTime()));
                target.setIndexStatusStr(indexInfo.getIndexComplete() == 1 ? "未测试" : "测试通过");
                resultList.add(target);
            }
        }
        return resultList;
    }

    public ServiceStatus debugIndexNode(IndexInfoParam indexInfo,String evalPath) {
        //首先判断指标是否已经保存
        if (indexInfo.getId() == null || indexInfo.getId() == 0) {//没有保存过，需要保存
            return new ServiceStatus(ServiceStatus.Status.Fail, "请先保存指标");
        }
        ServiceStatus status = saveIndex(indexInfo,evalPath);
        // 如果保存不通过，则需要重新保存处理，每次调试都要清空数据
        if(status.getStatus()==ServiceStatus.Status.Fail){
            return  status ;
        }

        //更新指标信息
        indexInfoMapper.updateByPrimaryKeySelective(indexInfo);
        IndexTaskResult result = indexEngine.singleStepExecution(indexInfo.getNodekey(), indexInfo.getId(),evalPath);
        if (result.getResultCode() == IndexTaskResult.INDEX_SUCCESS) {
            return new ServiceStatus(ServiceStatus.Status.Success, "调试成功");
        }
        return new ServiceStatus(ServiceStatus.Status.Fail, "调试失败：" + result.getMsg());
    }

    /**
     * 保存指标
     *
     * @param indexInfo
     * @return
     */
    public ServiceStatus saveIndex(IndexInfo indexInfo,String evalPath) {
        Integer userid = Integer.parseInt(baseService.getUserId());
        IndexDefinitions indexDefine = DeploymentIndex.getIndexDefinition(indexInfo,evalPath);
        IndexNode lineNode = indexDefine.getNotLineNode();
        if (lineNode != null) {//判断节点是否连线
            //节点没有练线
            return new ServiceStatus(ServiceStatus.Status.Fail, lineNode.getNodeText() + "没有连线");
        }
        List<IndexNode> dataNodes = indexDefine.getDataNode();
        if (dataNodes.size() == 0) {//没有数据输入
            return new ServiceStatus(ServiceStatus.Status.Fail, "至少设置一个数据源");
        }

        if (!indexDefine.isUniqueEndNode()) {//限定只有一个供项目指标选择的节点
            return new ServiceStatus(ServiceStatus.Status.Fail, "项目有且只有一个输出到项目指标节点");
        }

        if(!indexDefine.checkIfNode()){
            return new ServiceStatus(ServiceStatus.Status.Fail, "IF节点设置不正确，请确认线和条件!");
        }

        if(!indexDefine.checkName()){//默认值和名字
            return new ServiceStatus(ServiceStatus.Status.Fail, "请检查常量和数据源的名字！");
        }
        if(!indexDefine.checkTitle()){//图表的标题
            return new ServiceStatus(ServiceStatus.Status.Fail, "请设置图表的标题！");
        }
        Date now = new Date();
        if (indexInfo.getId() != null) {//如果为空则为保存，否则为更新

            IndexInfo info = indexInfoMapper.selectByPrimaryKey(indexInfo.getId());
            boolean isUpdate = true ;
            if(indexInfo.getIndexData().equals(info.getIndexData())){
                isUpdate=false ;
            }
            indexInfo.setUpdateTime(now);
            indexInfo.setUpdateUid(userid);
            indexInfoMapper.updateByPrimaryKeySelective(indexInfo);
            if(isUpdate){//如果没有改变,则不需要自动保存
                saveInsertIndexNode(indexDefine, indexInfo.getId());
            }
            return new ServiceStatus(ServiceStatus.Status.Success, String.valueOf(indexInfo.getId()));
        } else {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("indexName", indexInfo.getIndexName());
            if (indexInfoMapper.countExistIndexName(paramMap) <= 0) {
                indexInfo.setCreateUid(userid);
                indexInfo.setCreateTime(now);
                indexInfo.setUpdateUid(userid);
                indexInfo.setUpdateTime(now);
                indexInfo.setIndexComplete((byte) 0);
                indexInfo.setIsdelete(0);
                indexInfoMapper.insertSelective(indexInfo);
                saveInsertIndexNode(indexDefine, indexInfo.getId());
                return new ServiceStatus(ServiceStatus.Status.Success, String.valueOf(indexInfo.getId()));
            }
            return new ServiceStatus(ServiceStatus.Status.Fail, "指标名称已存在");
        }
    }


    /**
     * 复制创建保存指标
     *
     * @param indexInfo
     * @return
     */
    public ServiceStatus copySaveIndex(IndexInfo indexInfo,String evalPath) {
        Integer userid = Integer.parseInt(baseService.getUserId());
        IndexDefinitions indexDefine = DeploymentIndex.getIndexDefinition(indexInfo,evalPath);
        IndexNode lineNode = indexDefine.getNotLineNode();
        if (lineNode != null) {//判断节点是否连线
            //节点没有练线
            return new ServiceStatus(ServiceStatus.Status.Fail, lineNode.getNodeText() + "没有连线");
        }
        List<IndexNode> dataNodes = indexDefine.getDataNode();
        if (dataNodes.size() == 0) {//没有数据输入
            return new ServiceStatus(ServiceStatus.Status.Fail, "至少设置一个数据源");
        }

        if (!indexDefine.isUniqueEndNode()) {//限定只有一个供项目指标选择的节点
            return new ServiceStatus(ServiceStatus.Status.Fail, "项目有且只有一个输出到项目指标节点");
        }

        if(!indexDefine.checkIfNode()){
            return new ServiceStatus(ServiceStatus.Status.Fail, "IF节点设置不正确，请确认线和条件!");
        }

        if(!indexDefine.checkName()){//默认值和名字
            return new ServiceStatus(ServiceStatus.Status.Fail, "请检查常量和数据源的名字！");
        }
        if(!indexDefine.checkTitle()){//图表的标题
            return new ServiceStatus(ServiceStatus.Status.Fail, "请设置图表的标题！");
        }

        Date now = new Date();
        indexInfo.setId(null);
        if (indexInfo.getId() != null) {//如果为空则为保存，否则为更新
            indexInfo.setUpdateTime(now);
            indexInfo.setUpdateUid(userid);
            indexInfoMapper.updateByPrimaryKeySelective(indexInfo);
            saveInsertIndexNode(indexDefine, indexInfo.getId());
            return new ServiceStatus(ServiceStatus.Status.Success, String.valueOf(indexInfo.getId()));
        } else {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("indexName", indexInfo.getIndexName());
            if (indexInfoMapper.countExistIndexName(paramMap) <= 0) {
                indexInfo.setCreateUid(userid);
                indexInfo.setCreateTime(now);
                indexInfo.setUpdateUid(userid);
                indexInfo.setUpdateTime(now);
                indexInfo.setIndexComplete((byte) 0);
                indexInfo.setIsdelete(0);
                indexInfoMapper.insertSelective(indexInfo);
                saveInsertIndexNode(indexDefine, indexInfo.getId());
                return new ServiceStatus(ServiceStatus.Status.Success, String.valueOf(indexInfo.getId()));
            }
            return new ServiceStatus(ServiceStatus.Status.Fail, "指标名称已存在");
        }
    }

    /**
     * 自动保存指标
     *
     * @param indexInfo
     * @return
     */
    public ServiceStatus autosaveIndex(IndexInfo indexInfo,String evalPath) {
        Integer userid = Integer.parseInt(baseService.getUserId());
        IndexDefinitions indexDefine = DeploymentIndex.getIndexDefinition(indexInfo,evalPath);
        IndexNode lineNode = indexDefine.getNotLineNode();
        if (lineNode != null) {//判断节点是否连线
            //节点没有练线
            return new ServiceStatus(ServiceStatus.Status.Fail, "自动保存失败，"+lineNode.getNodeText() + "没有连线");
        }
        List<IndexNode> dataNodes = indexDefine.getDataNode();
        if (dataNodes.size() == 0) {//没有数据输入
            return new ServiceStatus(ServiceStatus.Status.Fail, "自动保存失败，至少设置一个数据源");
        }

        if (!indexDefine.isUniqueEndNode()) {//限定只有一个供项目指标选择的节点
            return new ServiceStatus(ServiceStatus.Status.Fail, "自动保存失败，项目有且只有一个输出到项目指标节点");
        }

        if(!indexDefine.checkIfNode()){
            return new ServiceStatus(ServiceStatus.Status.Fail, "自动保存失败，IF节点设置不正确，请确认线和条件!");
        }

        if(!indexDefine.checkName()){//默认值和名字
            return new ServiceStatus(ServiceStatus.Status.Fail, "自动保存失败，请检查常量和数据源的名字！");
        }
        if(!indexDefine.checkTitle()){//图表的标题
            return new ServiceStatus(ServiceStatus.Status.Fail, "自动保存失败，请设置图表的标题！");
        }

        Date now = new Date();
        if (indexInfo.getId() != null) {//如果为空则为保存，否则为更新
            IndexInfo info = indexInfoMapper.selectByPrimaryKey(indexInfo.getId());
            boolean isUpdate = true ;
            if(indexInfo.getIndexData().equals(info.getIndexData())){
                isUpdate=false ;
            }
            indexInfo.setUpdateTime(now);
            indexInfo.setUpdateUid(userid);
            indexInfoMapper.updateByPrimaryKeySelective(indexInfo);
            if(isUpdate){//如果没有改变,则不需要自动保存
                saveInsertIndexNode(indexDefine, indexInfo.getId());
            }
            return new ServiceStatus(ServiceStatus.Status.Success, String.valueOf(indexInfo.getId()));
        } else {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("indexName", indexInfo.getIndexName());
            if (indexInfoMapper.countExistIndexName(paramMap) <= 0) {
                indexInfo.setCreateUid(userid);
                indexInfo.setCreateTime(now);
                indexInfo.setUpdateUid(userid);
                indexInfo.setUpdateTime(now);
                indexInfo.setIndexComplete((byte) 0);
                indexInfo.setIsdelete(0);
                indexInfoMapper.insertSelective(indexInfo);
                saveInsertIndexNode(indexDefine, indexInfo.getId());
                return new ServiceStatus(ServiceStatus.Status.Success, String.valueOf(indexInfo.getId()));
            }
            return new ServiceStatus(ServiceStatus.Status.Fail, "自动保存失败，指标名称已存在");
        }
    }
    private void saveInsertIndexNode(IndexDefinitions indexDefine, Integer indexid) {
        Integer userid = Integer.parseInt(baseService.getUserId());
        Date now = new Date();
        Example example = new Example(IndexModel.class);
        example.createCriteria().andEqualTo("indexId", indexid);
        indexModelMapper.deleteByExample(example);
        List<IndexModel> list = new ArrayList<>();
        for (IndexNode node : indexDefine.getNodes()) {
            IndexModel indexModel = new IndexModel();
            indexModel.setIndexId(indexid);
            indexModel.setEndNode(node.isEndNode() ? 1 : 0);
            indexModel.setModelId(node.getModelId());
            indexModel.setNodeCategory(node.getNodeCategory().getValue());
            indexModel.setNodeKey(node.getKey());
            indexModel.setNodeText(node.getNodeText());
            if (node.getNodeType() != null) {
                indexModel.setNodeType(node.getNodeType().getValue());
            }
            if (node.getSettingNum() != null) {
                indexModel.setSettingNum(node.getSettingNum());
            }
            if (node.getSignType() != null) {
                indexModel.setSignType(node.getSignType().getValue());
            }
            if (node.getParamName() != null) {
                indexModel.setParamName(node.getParamName());
            }
            if (node.getDefaultValue() != null) {
                indexModel.setDefaultValue(node.getDefaultValue());
            }
            if (node.getPictureTitle() != null) {
                indexModel.setPictureTitle(node.getPictureTitle());
            }
            if (node.getxTitle() != null) {
                indexModel.setxTitle(node.getxTitle());
            }
            if (node.getyTitle() != null) {
                indexModel.setyTitle(node.getyTitle());
            }
            indexModel.setStartNode(node.isStartNode() ? 1 : 0);
            indexModel.setCreateUid(userid);
            indexModel.setCreateTime(now);
            indexModel.setUpdateUid(userid);
            indexModel.setUpdateTime(now);
            indexModel.setIsdelete(0);
            list.add(indexModel);
        }
        if (list.size() > 0) {
            indexModelMapper.insertList(list);
        }
    }

    /**
     * 更新指标
     *
     * @param indexInfo
     * @return
     */
    public ServiceStatus update(IndexInfo indexInfo) {

        return new ServiceStatus(ServiceStatus.Status.Success, "指标名称已存在");
    }


    /**
     * 获取文件指标信息
     *
     * @return
     */
    public IndexFileInfoParams getIndexFileInfoParams() {
        IndexFileInfoParams indexFileInfoParams = new IndexFileInfoParams();
        // 获取分类
        List<DatasourceCategory> categoryList = fileService.queryCategoryList();
        for (DatasourceCategory dc : categoryList) {


        }
        return indexFileInfoParams;
    }

    /**
     *模板保存
     * @param id
     * @param indexData
     * @param tplName
     */
    public void otherSaveTpl(String id,  String indexData, String tplName) {
        Integer userid = Integer.parseInt(baseService.getUserId());
        Date now = new Date();
        IndexInfo indexInfo = getIndexInfoById(Integer.valueOf(id));
        Example example = new Example(TempletInfo.class);
        example.createCriteria().andEqualTo("templetCategoryid", indexInfo.getIndexCategoryid()).andEqualTo("templetName",tplName);
        List<TempletInfo> templetlist  = templetInfoMapper.selectByExample(example);
        TempletInfo templetInfo = new TempletInfo();
        if (templetlist != null && templetlist.size() > 0) {
//            templetInfo.setId(Integer.parseInt(id));
            templetInfo=templetlist.get(0);
            templetInfo.setTempletData(indexData);
            templetInfo.setUpdateUid(userid);
            templetInfo.setUpdateTime(now);
            templetInfoMapper.updateByPrimaryKeySelective(templetInfo);
        } else {
            templetInfo.setIndexid(Integer.parseInt(id));
            templetInfo.setTempletData(indexData);
            templetInfo.setTempletName(tplName);
            templetInfo.setCreateUid(userid);
            templetInfo.setCreateTime(now);
            templetInfo.setUpdateUid(userid);
            templetInfo.setUpdateTime(now);
            templetInfo.setIsdelete(0);
            templetInfo.setTempletCategoryid(indexInfo.getIndexCategoryid());
            templetInfoMapper.insertSelective(templetInfo);
        }
    }

    public int queryIndexChildCount(ModelInfoParams params) {
        return indexModelMapper.getIndexChildCount(params);
    }

    public List<IndexModelQueryResult> queryIndexChildList(ModelInfoParams params, int start) {
        List<IndexModelQueryResult> resultList = new ArrayList<>();
        List<IndexModel> list = indexModelMapper.selectIndexChildList(params, start);
        if (list != null) {
            for (IndexModel indexInfo : list) {
                IndexModelQueryResult target = new IndexModelQueryResult();
                BeanUtils.copyProperties(indexInfo, target);
                target.setUpdateTimeStr(DateUtil.TimeToString(indexInfo.getUpdateTime()));
                resultList.add(target);
            }
        }
        return resultList;
    }
}
