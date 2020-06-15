package com.tf.base.platform.service;

import com.tf.base.common.domain.*;
import com.tf.base.common.persistence.StructCategoryMapper;
import com.tf.base.common.persistence.StructDefineMapper;
import com.tf.base.common.persistence.StructParmeterMapper;
import com.tf.base.common.service.BaseService;
import com.tf.base.common.utils.JSONUtil;
import com.tf.base.common.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class StructInfoService {
    @Autowired
    private StructCategoryMapper structCategoryMapper;
    @Autowired
    private BaseService baseService;
    @Autowired
    private StructDefineMapper structDefineMapper;
    @Autowired
    private StructParmeterMapper structParmeterMapper;

    public int saveCategory(String categoryName) {

        Integer userid = Integer.parseInt(baseService.getUserId());
        Date now = new Date();
        StructCategory sc = new StructCategory();
        sc.setCategoryName(categoryName);
        sc.setCreateTime(now);
        sc.setCreateUid(userid);
        sc.setIsdelete(0);
        sc.setUpdateTime(now);
        sc.setUpdateUid(userid);
        return structCategoryMapper.insertSelective(sc);
    }

    public int updateCategory(String categoryId, String categoryName) {
        Integer userid = Integer.parseInt(baseService.getUserId());
        Date now = new Date();
        StructCategory sc = structCategoryMapper.selectByPrimaryKey(categoryId);
        sc.setCategoryName(categoryName);
        sc.setUpdateTime(now);
        sc.setUpdateUid(userid);
        return structCategoryMapper.updateByPrimaryKeySelective(sc);
    }


    public int deleteCategory(String categoryId) {
        Integer userid = Integer.parseInt(baseService.getUserId());
        Date now = new Date();
        StructCategory sc = structCategoryMapper.selectByPrimaryKey(categoryId);
        sc.setIsdelete(1);
        sc.setUpdateTime(now);
        sc.setUpdateUid(userid);
        //删除结构体分类
        int i = structCategoryMapper.updateByPrimaryKeySelective(sc);
        if (i == 1) {
            //删除结构体
            structDefineMapper.updateIsdelete(Integer.parseInt(categoryId));
        }
        return i;
    }

    /**
     *
     *
     * @param
     * @return
     */
    public Map<String, List<StructParmeter>> getStructParmeter(String structId) {
        Map<String, List<StructParmeter>> map = new HashMap<>();
        Example example = new Example(StructParmeter.class);
        example.createCriteria()
                .andEqualTo("structId", structId)
                .andEqualTo("isdelete", 0);
        List<StructParmeter> list = structParmeterMapper.selectByExample(example);
        //结构体
        List<StructParmeter> structList = new ArrayList<>();
        if (list != null) {
            for (StructParmeter s : list) {

                //结构体
                structList.add(s);

            }
        }
        map.put("structList", structList);
        return map;
    }

    /**
     * 结构体保存
     *
     * @param reqJson
     * @return 1-成功 0-失败  400-结构体名称不能重复 401-同一个结构体类参数名不能重复 402-模结构体描述不能为空
     */
    public int saveStructInfo(JSONObject reqJson) {
        StructDefine structDefine = JSONUtil.toObject(reqJson.toString(), StructDefine.class);
        if (!checkName(structDefine.getId() + "", structDefine.getStructName())) {
            return 400;
        }
        JSONArray stVariableTb = JSONArray.fromObject(reqJson.get("stVariableTb"));
//        JSONArray outArray = JSONArray.fromObject(reqJson.get("optVarJson"));
//        if (!checkParmeterName(inArray, outArray)) {
//            return 401;
//        }
        if (structDefine.getStructRemark().equals("") || structDefine.getStructRemark().length() == 0) {
            return 402;
        }

        int ret = 0;
        Integer userid = Integer.parseInt(baseService.getUserId());
        Date now = new Date();
        if(structDefine.getId()==null){
            structDefine.setIsdelete(0);
            structDefine.setCreateTime(now);
            structDefine.setCreateUid(userid);
            structDefine.setUpdateTime(now);

            ret=structDefineMapper.insertSelective(structDefine);
             //入参
            List<StructParmeter> inParmentList=saveParamter(structDefine, stVariableTb, 0);

            //出参

//            List<StructParmeter> outParmentList=saveParamter(structDefine, outArray, 1);

            structDefineMapper.updateByPrimaryKeySelective(structDefine);

        }else{
            structDefine.setUpdateTime(now);
            structDefine.setCreateUid(userid);
            ret=structDefineMapper.updateByPrimaryKeySelective(structDefine);

            Example example=new Example(StructParmeter.class);
            example.createCriteria().andEqualTo("structId",structDefine.getId());
            structParmeterMapper.deleteByExample(example);

            //入参

            List<StructParmeter> inParmentList=saveParamter(structDefine, stVariableTb, 0);
            //出参

//            List<StructParmeter> outParmentList=saveParamter(structDefine, outArray, 1);

            structDefineMapper.updateByPrimaryKeySelective(structDefine);
        }

        return ret;
    }

    public boolean checkName(String id,String structName){
        Example example=new Example(StructDefine.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("structName", structName)
                .andEqualTo("isdelete", 0);
        if(!StringUtil.isEmpty(id)){
            criteria.andNotEqualTo("id", id);
        }
        List<StructDefine> list=structDefineMapper.selectByExample(example);
        if(list==null||list.size()<=0){
            return true;
        }
        return false;
    }

    /**
     * 验证统一结构体下的参数名字是否有相同的
     * @param inArray
     * @param outArray
     * @return
     */
    public boolean checkParmeterName(JSONArray inArray,JSONArray outArray){
        try{
            List<StructParmeter> list=new ArrayList<>();
            for(int i=0;i<inArray.size();i++){
                JSONObject inJo=(JSONObject) inArray.get(i);
                StructParmeter inParamter=JSONUtil.toObject(inJo.toString(), StructParmeter.class);
                list.add(inParamter);
            }
            for(int i=0;i<outArray.size();i++){
                JSONObject outJo=(JSONObject) outArray.get(i);
                StructParmeter outParamter=JSONUtil.toObject(outJo.toString(), StructParmeter.class);
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
     *
     * @param structDefine
     * @param paramterArray  参数json数组
     * @param inOrOut  0-入参  1-出参
     */
    private List<StructParmeter> saveParamter(StructDefine structDefine,JSONArray paramterArray,int inOrOut){
        List<StructParmeter> list=new ArrayList<StructParmeter>();

        Integer userid = Integer.parseInt(baseService.getUserId());
        Date now = new Date();
        for(int i=0;i<paramterArray.size();i++){
            JSONObject inJo=(JSONObject) paramterArray.get(i);
            StructParmeter jgt=null;
            List<StructParmeter> subParmeterList=null; //结构体里的参数集合
            StructParmeter inParamter=JSONUtil.toObject(inJo.toString(), StructParmeter.class);
            inParamter.setCreateTime(now);
            inParamter.setCreateUid(userid);
            inParamter.setIsdelete(0);
            inParamter.setStructId(structDefine.getId());

            structParmeterMapper.insertSelective(inParamter);
            list.add(inParamter);

        }
        return list;
    }
    public int deleteStructInfo(String modelInfoId){
        Integer userid = Integer.parseInt(baseService.getUserId());
        Date now = new Date();
        StructDefine structDefine = structDefineMapper.selectByPrimaryKey(modelInfoId);
        structDefine.setIsdelete(1);
        structDefine.setUpdateTime(now);
        structDefine.setUpdateUid(userid);

        return structDefineMapper.updateByPrimaryKeySelective(structDefine);
    }
}