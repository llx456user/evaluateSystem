package com.tf.base.platform.service;

import com.tf.base.common.domain.*;
import com.tf.base.common.persistence.*;
import com.tf.base.common.persistence.daoImpl.ModelParmeterDao;
import com.tf.base.common.service.BaseService;
import com.tf.base.common.utils.DateUtil;
import com.tf.base.common.utils.JSONUtil;
import com.tf.base.common.utils.StringUtil;
import com.tf.base.platform.domain.ModelInfoParams;
import com.tf.base.platform.domain.ModelInfoQueryResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.aspectj.asm.AsmManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ModelInfoService {
	
	@Autowired
	private BaseService baseService;
	
	@Autowired
	private ModelInfoMapper modelInfoMapper;
	@Autowired
	private ModelCategoryMapper modelCategoryMapper;
	@Autowired
	private ModelParmeterMapper modelParmeterMapper;
	@Autowired
	private ModelParmeterDao modelParmeterDao;
	@Autowired
	private DatasourceDbMapper datasourceDbMapper;
	@Autowired
    private IndexInfoMapper indexInfoMapper;
	@Autowired
	private StructParmeterMapper structParmeterMapper ;
	public int queryCount(ModelInfoParams params) {
		
		return modelInfoMapper.queryCount(params);
	}

	public List<ModelInfoQueryResult> queryList(ModelInfoParams params,int start) {
		
		List<ModelInfoQueryResult> list= modelInfoMapper.queryList(params, start);
		if(list!=null){
			for(ModelInfoQueryResult r:list){
				r.setUpdateTimeStr(DateUtil.TimeToString(r.getUpdateTime()));
				r.setModelStatusStr(r.getModelStatus()==1?"未上传":"已上传");
			}
		}
		return list;
	}

	public int saveCategory(String categoryName){
		
		Integer userid = Integer.parseInt(baseService.getUserId());
		Date now = new Date();
		ModelCategory mc = new ModelCategory();
		mc.setCategoryName(categoryName);
		mc.setCreateTime(now);
		mc.setCreateUid(userid);
		mc.setIsdelete(0);
		mc.setUpdateTime(now);
		mc.setUpdateUid(userid);
		return modelCategoryMapper.insertSelective(mc);
	} 
	public int updateCategory(String categoryId,String categoryName){
		Integer userid = Integer.parseInt(baseService.getUserId());
		Date now = new Date();
		ModelCategory mc = modelCategoryMapper.selectByPrimaryKey(categoryId);
		mc.setCategoryName(categoryName);
		mc.setUpdateTime(now);
		mc.setUpdateUid(userid);
		return modelCategoryMapper.updateByPrimaryKeySelective(mc);
	}
	public int deleteCategory(String categoryId){
		Integer userid = Integer.parseInt(baseService.getUserId());
		Date now = new Date();
		ModelCategory mc = modelCategoryMapper.selectByPrimaryKey(categoryId);
		mc.setIsdelete(1);
		mc.setUpdateTime(now);
		mc.setUpdateUid(userid);
		//删除模型分类
		int i= modelCategoryMapper.updateByPrimaryKeySelective(mc);
		if(i==1){
			//删除模型
			modelInfoMapper.updateIsdelete(Integer.parseInt(categoryId));
		}
		return i;
	}
	
	/**
	 * 模型保存
	 * @param reqJson
	 * @return  1-成功 0-失败  400-模型名称不能重复 401-同一个模型内参数名不能重复 402-模型描述不能为空  403-模型参数类型不能为空
	 */
	public int saveModelInfo(JSONObject reqJson){
		ModelInfo modelInfo=JSONUtil.toObject(reqJson.toString(), ModelInfo.class);
		if(!checkName(modelInfo.getId()+"", modelInfo.getModelName(),modelInfo.getModelCategoryid())){
			return 400;
		}
		JSONArray inArray=JSONArray.fromObject(reqJson.get("iptVarJson")) ;
		JSONArray outArray=JSONArray.fromObject(reqJson.get("optVarJson"));
		if(!checkParmeterName(inArray, outArray)){
			return 401;
		}
		if(modelInfo.getModelContent().equals("")||modelInfo.getModelContent().length()==0){
			return 402;
		}
		if(!checkParmeterType(inArray, outArray)){
			return 403;
		}

		int ret=0;
		Integer userid = Integer.parseInt(baseService.getUserId());
		Date now = new Date();
		if(modelInfo.getId()==null){//insert
			modelInfo.setIsdelete(0);
			modelInfo.setCreateTime(now);
			modelInfo.setCreateUid(userid);
			modelInfo.setUpdateTime(now);
			
//			ret=modelInfoMapper.insertSelective(modelInfo);
			modelInfoMapper.insertSelective(modelInfo);
			//入参
//			JSONArray inArray=JSONArray.fromObject(reqJson.get("iptVarJson")) ;
			List<ModelParmeter> inParmentList=saveParamter(modelInfo, inArray, 0);
			//出参
//			JSONArray outArray=JSONArray.fromObject(reqJson.get("optVarJson"));
			List<ModelParmeter> outParmentList=saveParamter(modelInfo, outArray, 1);
			//结构体参数
//            JSONArray structArray=JSONArray.fromObject(reqJson.get("structVarJson"));
//			List<ModelParmeter> structList=saveStructParamter(modelInfo,structArray);
			//TODO 生成xml
			List<ModelParmeter> parmeterList = new ArrayList<>();
			parmeterList.addAll(inParmentList);
			parmeterList.addAll(outParmentList);
			//添加结构体
//			parmeterList.addAll(structList);

			for(int i=0;i<parmeterList.size();i++){
				if(parmeterList.get(i).getIsArray()){
					modelInfo.setIsSingleFun(false);
					break;
				}else {
					modelInfo.setIsSingleFun(true);
				}

			}


			ModelInfoUtil modelInfoUtil= new ModelInfoUtil();
			String[] retXml=modelInfoUtil.createModelXML(modelInfo,parmeterList);
			modelInfo.setXmlPath(retXml[0]);
			modelInfo.setXmlname(retXml[1]);
			
			modelInfoMapper.updateByPrimaryKeySelective(modelInfo);
            ret=modelInfo.getId();
			
		}else{//update
			modelInfo.setUpdateTime(now);
			modelInfo.setUpdateUid(userid);
			
			ret=modelInfoMapper.updateByPrimaryKeySelective(modelInfo);
			//删除模型关联的 参数
			Example example=new Example(ModelParmeter.class);
			example.createCriteria().andEqualTo("modelId",modelInfo.getId());
			modelParmeterMapper.deleteByExample(example);

//			根据id查询xml文件
			Example ep=new Example(ModelInfo.class);
			ep.createCriteria().andEqualTo("id",modelInfo.getId());
			List<ModelInfo> list=modelInfoMapper.selectByExample(ep);
			if(list!=null&&list.size()>0){
				ModelInfo m=list.get(0);
				modelInfo.setXmlPath(m.getXmlPath());
			}

			//入参
//			JSONArray inArray=JSONArray.fromObject(reqJson.get("iptVarJson"));
			List<ModelParmeter> inParmentList=saveParamter(modelInfo, inArray, 0);
			//出参
//			JSONArray outArray=JSONArray.fromObject(reqJson.get("optVarJson"));
			List<ModelParmeter> outParmentList=saveParamter(modelInfo, outArray, 1);

			//结构体参数
//			JSONArray structArray=JSONArray.fromObject(reqJson.get("structVarJson"));
//			List<ModelParmeter> structList=saveStructParamter(modelInfo, structArray);

			// 更新xml
			List<ModelParmeter> parmeterList = new ArrayList<>();
			parmeterList.addAll(inParmentList);
			parmeterList.addAll(outParmentList);
			//添加结构体
//			parmeterList.addAll(structList);


			for(int i=0;i<parmeterList.size();i++){
				if(parmeterList.get(i).getIsArray()){
					modelInfo.setIsSingleFun(false);
					break;
				}else {
					modelInfo.setIsSingleFun(true);
				}

			}

			ModelInfoUtil modelInfoUtil= new ModelInfoUtil();
			String[] retXml=modelInfoUtil.updateXML(modelInfo,parmeterList);
			modelInfo.setXmlPath(retXml[0]);
			modelInfo.setXmlname(retXml[1]);

			modelInfoMapper.updateByPrimaryKeySelective(modelInfo);
		}
		return ret;
	}
	/**
	 * 验证模型名称是否已经存在数据库中
	 * @param id
	 * @param modelName
	 * @return
	 */
	public boolean checkName(String id,String modelName,Integer modelCategoryid){
		Example example=new Example(ModelInfo.class);
		Criteria criteria=example.createCriteria();
		criteria.andEqualTo("modelName", modelName).andEqualTo("modelCategoryid",modelCategoryid)
		.andEqualTo("isdelete", 0);
		if(!StringUtil.isEmpty(id)){
			criteria.andNotEqualTo("id", id);
		}
		List<ModelInfo> list=modelInfoMapper.selectByExample(example);
		if(list==null||list.size()<=0){
			return true;
		}
		return false;
	}

	/**
	 * 验证统一模型下的参数名字是否有相同的
	 * @param inArray
	 * @param outArray
	 * @return
	 */
	public boolean checkParmeterName(JSONArray inArray,JSONArray outArray){
		try{
			List<ModelParmeter> list=new ArrayList<>();
			for(int i=0;i<inArray.size();i++){
				JSONObject inJo=(JSONObject) inArray.get(i);
				ModelParmeter inParamter=JSONUtil.toObject(inJo.toString(), ModelParmeter.class);
				list.add(inParamter);
			}
			for(int i=0;i<outArray.size();i++){
				JSONObject outJo=(JSONObject) outArray.get(i);
				ModelParmeter outParamter=JSONUtil.toObject(outJo.toString(), ModelParmeter.class);
				list.add(outParamter);
			}
			
			if(list==null||list.size()==0){
				return false;
			}
			Set<String> set=new HashSet<>();
			
			for(int i=0;i<list.size();i++){
				if(set.contains(list.get(i).getParmeterName())){
					return false;
				}
				set.add(list.get(i).getParmeterName());
			}
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 验证统一模型下的参数类型是否有空值
	 * @param inArray
	 * @param outArray
	 * @return
	 */
	public boolean checkParmeterType(JSONArray inArray,JSONArray outArray){
		try{
			List<ModelParmeter> list=new ArrayList<>();
			for(int i=0;i<inArray.size();i++){
				JSONObject inJo=(JSONObject) inArray.get(i);
				ModelParmeter inParamter=JSONUtil.toObject(inJo.toString(), ModelParmeter.class);
				list.add(inParamter);
			}
			for(int i=0;i<outArray.size();i++){
				JSONObject outJo=(JSONObject) outArray.get(i);
				ModelParmeter outParamter=JSONUtil.toObject(outJo.toString(), ModelParmeter.class);
				list.add(outParamter);
			}

			if(list==null||list.size()==0){
				return false;
			}
			Set<String> set=new HashSet<>();

			for(int i=0;i<list.size();i++){
				if(list.get(i).getParmeterType()==""){
					return false;
				}
			}
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 判断类型是否为数组
	 * @param inParamter
	 * @return
	 */
	private boolean isArray(ModelParmeter inParamter){
		if("int*".equals(inParamter.getParmeterType())||"long*".equals(inParamter.getParmeterType())||"float*".equals(inParamter.getParmeterType())||"double*".equals(inParamter.getParmeterType())||"string*".equals(inParamter.getParmeterType())){
			return  true ;
		}
		if(inParamter.getParmeterName().startsWith("*")){
			return  true ;
		}
		return  false ;
	}
	/**
	 * 
	 * @param modelInfo
	 * @param paramterArray  参数json数组
	 * @param inOrOut  0-入参  1-出参
	 */
	private List<ModelParmeter> saveParamter(ModelInfo modelInfo,JSONArray paramterArray,int inOrOut){
		List<ModelParmeter> list=new ArrayList<ModelParmeter>();
		
		Integer userid = Integer.parseInt(baseService.getUserId());
		Date now = new Date();
		for(int i=0;i<paramterArray.size();i++){
			JSONObject inJo=(JSONObject) paramterArray.get(i);
			ModelParmeter jgt=null;
			List<ModelParmeter> subParmeterList=null; //结构体里的参数集合
			ModelParmeter inParamter=JSONUtil.toObject(inJo.toString(), ModelParmeter.class);
			inParamter.setCreateTime(now);
			inParamter.setCreateUid(userid);
			inParamter.setInoutType(inOrOut);
			inParamter.setIsdelete(0);
			inParamter.setModelId(modelInfo.getId());
			inParamter.setIsArray(isArray(inParamter));//设置数组类型
//			if(inParamter.getParmeterType().contains("struct"))
//			{ //类型是结构体
//				//定义结构体开始
////				JSONObject varJson=(JSONObject) inJo.get("varJson");
////				if(varJson!=null){
////					jgt=new ModelParmeter();
////					jgt.setParmeterName(varJson.getString("name"));
////					jgt.setParmeterType("struct");
////					jgt.setCreateTime(now);
////					jgt.setCreateUid(userid);
////					jgt.setInoutType(2);
////					jgt.setIsdelete(0);
////					jgt.setModelId(modelInfo.getId());
////
////					inParamter.setParmeterType("struct_"+jgt.getParmeterName());
////				}
//
//				//结构体里的参数
////				JSONArray varSubArray=JSONArray.fromObject(varJson.get("varJson"));
////				if(varSubArray!=null&&varSubArray.size()>0){
////					subParmeterList=new ArrayList<>();
////					for(int j=0;j<varSubArray.size();j++){
////						JSONObject subJo=(JSONObject) varSubArray.get(j);
////						ModelParmeter subParamter=JSONUtil.toObject(subJo.toString(), ModelParmeter.class);
////						subParamter.setCreateTime(now);
////						subParamter.setCreateUid(userid);
////						subParamter.setInoutType(2);
////						subParamter.setIsdelete(0);
////						subParamter.setModelId(modelInfo.getId());
////
////						subParmeterList.add(subParamter);
////					}
////				}
//
//			}
			modelParmeterMapper.insertSelective(inParamter);
			list.add(inParamter);
//			if(jgt!=null){
//				//先检查这个结构体是够已存在
//				ModelParmeter jgtDb=modelParmeterDao.getJgtByModelIdAndName(jgt.getModelId()+"", jgt.getParmeterName());
//				if(jgtDb==null){
//					modelParmeterMapper.insertSelective(jgt);
//					list.add(jgt);
//					if(subParmeterList!=null){
//						for(ModelParmeter subParamter:subParmeterList){
//							subParamter.setParentId(jgt.getId());
//							modelParmeterMapper.insertSelective(subParamter);
//							list.add(subParamter);
//						}
//					}
//				}else{
//					list.add(jgtDb);
//					List<ModelParmeter>subParmenterList=modelParmeterDao.getSubParmeterList(jgtDb.getId()+"");
//					list.addAll(subParmenterList);
//				}
//			}
		}
		return list;
	}

    /**
     * 保存结构体参数，参数类型默认为2，表示为结构体
     * @param modelInfo
     * @param paramterArray
     * @return
     */
    private List<ModelParmeter> saveStructParamter(ModelInfo modelInfo,JSONArray paramterArray){
        List<ModelParmeter> list=new ArrayList<ModelParmeter>();
        Integer userid = Integer.parseInt(baseService.getUserId());
        Date now = new Date();
        for(int i=0;i<paramterArray.size();i++){
            JSONObject inJo=(JSONObject) paramterArray.get(i);
            ModelParmeter jgt=null;
            List<ModelParmeter> subParmeterList=null; //结构体里的参数集合
            ModelParmeter inParamter=JSONUtil.toObject(inJo.toString(), ModelParmeter.class);
            inParamter.setCreateTime(now);
            inParamter.setCreateUid(userid);
            inParamter.setInoutType(2);
            inParamter.setIsdelete(0);
			inParamter.setParmeterType("struct");
            inParamter.setModelId(modelInfo.getId());
            //结构体里的参数
            JSONArray varSubArray=JSONArray.fromObject(inJo.get("varJson"));
            modelParmeterMapper.insertSelective(inParamter);
            list.add(inParamter);
            if(varSubArray!=null&&varSubArray.size()>0){
                subParmeterList=new ArrayList<>();
                for(int j=0;j<varSubArray.size();j++){
                    JSONObject subJo=(JSONObject) varSubArray.get(j);
                    ModelParmeter subParamter=JSONUtil.toObject(subJo.toString(), ModelParmeter.class);
                    subParamter.setCreateTime(now);
                    subParamter.setCreateUid(userid);
                    subParamter.setInoutType(2);
                    subParamter.setIsdelete(0);
                    subParamter.setModelId(modelInfo.getId());
                    subParamter.setParentId(inParamter.getId());
                    subParmeterList.add(subParamter);
                }
                //插入结构体的子类型
                if(subParmeterList.size()>0){
                    modelParmeterMapper.insertList(subParmeterList);
                    list.addAll(subParmeterList);
                }
            }
        }
        return  list ;
    }


	/**
	 * 
	 * @param id
	 * @param templatePath  模板路径
	 * @return String[0]--文件名称   String[1] --文件路径+文件名
	 */
	public String[] makeModelKj(String id,String templatePath) {
		String [] ret=new String[2];
		Example example=new Example(ModelParmeter.class);
		example.createCriteria().andEqualTo("modelId",id);
		List<ModelParmeter> list=modelParmeterMapper.selectByExample(example);
		SolutionUtil solutionUtil = new SolutionUtil();
//		String templatePath = "D:\\project\\lwq\\evaluateSystem\\evaluateSystem\\src\\main\\webapp\\TemplateProject\\";
//		String projectName = "project" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		ModelInfo modelInfo = modelInfoMapper.selectByPrimaryKey(id);
        String   projectName = modelInfo.getModelName()+modelInfo.getModelVersion();
		projectName= projectName.replaceAll("\\.","");

		Map<String, List<StructParmeter>> sMap = new HashMap<>();
		for(ModelParmeter modelParmeter : list){
			if(ModelParmeterTool.isStruct(modelParmeter.getParmeterType())){
				List<StructParmeter> pList = structParmeterMapper.queryStructParmeterByStructid(modelParmeter.getParmeterType());
				sMap.put(modelParmeter.getParmeterType(),pList);
			}
		}
		solutionUtil.makeSolution(list,sMap, templatePath, solutionUtil.PROJECT_PATH, projectName,modelInfo);
		try {
			//按照模型名称及版本号进行命名

			solutionUtil.zip(solutionUtil.PROJECT_PATH + projectName, solutionUtil.PROJECT_PATH, projectName + ".zip");
			ret[0]=projectName + ".zip";
			ret[1]=solutionUtil.PROJECT_PATH + projectName+".zip";
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public int deleteModelInfo(String modelInfoId){
		Integer userid = Integer.parseInt(baseService.getUserId());
		Date now = new Date();
		ModelInfo model = modelInfoMapper.selectByPrimaryKey(modelInfoId);
		model.setIsdelete(1);
		model.setUpdateTime(now);
		model.setUpdateUid(userid);
		//删除模型分类
		return modelInfoMapper.updateByPrimaryKeySelective(model);
	}
	/**
	 * 跟根据模型Id 查询模型参数集合
	 * @param modelInfoId
	 * @return
	 */
	public Map<String,List<ModelParmeter>> getModelParmeter(String modelInfoId){
		 Map<String,List<ModelParmeter>> map=new HashMap<>();
		Example example=new Example(ModelParmeter.class);
		example.createCriteria()
		.andEqualTo("modelId", modelInfoId)
		.andEqualTo("isdelete",0);
		List<ModelParmeter> list=modelParmeterMapper.selectByExample(example);
		//入参
		List<ModelParmeter>inParList=new ArrayList<>();
		//出参
		List<ModelParmeter>outParList=new ArrayList<>();
		//结构体
		List<ModelParmeter> structList=new ArrayList<>();
		if(list!=null){
			for(ModelParmeter m :list){
				if(m.getInoutType()==0){
					inParList.add(m);
				}else if(m.getInoutType()==1){//出参
					outParList.add(m);
				}else if(m.getInoutType()==2&&m.getParmeterType().equals("struct")){
					//结构体
					structList.add(m);
				}
			}
		}

		map.put("inParList", inParList);
		map.put("outParList", outParList);
		map.put("structList", structList);
		return map;
	}

    /**
     * 跟根据模型Id 查询模型参数集合
     * @param modelid
     * @return
     */
    public List<ModelParmeter> getModelPramas(String modelid){
        Map<String,List<ModelParmeter>> map=new HashMap<>();
        Example example=new Example(ModelParmeter.class);
        example.createCriteria()
                .andEqualTo("modelId", modelid)
                .andEqualTo("isdelete",0);
        return modelParmeterMapper.selectByExample(example);
    }


	
	public int updateDll(String modelInfoId,String dllPath){
		ModelInfo modelInfo=modelInfoMapper.selectByPrimaryKey(modelInfoId);
		if(modelInfo!=null){
			modelInfo.setDllPath(dllPath);
			String dllName= dllPath.substring(dllPath.lastIndexOf("/")+1);
			modelInfo.setDllname(dllName);
			modelInfo.setModelStatus(2);
			Date now = new Date();
			modelInfo.setUpdateTime(now);
			return modelInfoMapper.updateByPrimaryKeySelective(modelInfo);
		}
		return 0;
	}

    /**
     * 获取模型
     * @param modelId
     * @return
     */
	public ModelInfo findModelByID(int modelId){
       return modelInfoMapper.selectByPrimaryKey(modelId);
    }
	public List findModel(){
		List modelList=modelCategoryMapper.selectWsc();
		return  modelList;
	}
	public List findDbType(){
		List dbTypeList=datasourceDbMapper.selectDbType();
		return  dbTypeList;
	}

	public List<ModelInfo> selectModelByCatId(String catId){
		return  modelInfoMapper.selectModelByCatId(catId);
	}

	public List<IndexInfo> selectIndexName(Map map) {
		List<IndexInfo>  IName = indexInfoMapper.selectIndexName(map);
		return IName;
	}
	public List<ModelCategory> categoryList() {
		ModelCategory category = new ModelCategory();
		category.setIsdelete(0);
		return modelCategoryMapper.select(category);
	}

}
