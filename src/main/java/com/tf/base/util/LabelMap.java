package com.tf.base.util;

import com.alibaba.fastjson.JSON;
import com.tf.base.common.domain.IndexNodeProcess;
import com.tf.base.common.persistence.IndexNodeProcessMapper;
import com.tf.base.project.domain.ProjectLabel;
import com.tf.base.project.domain.WordPicture;
import com.tf.base.project.domain.WordTable;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabelMap {

    private  Map<String,ProjectLabel> projectLabelMap ;// 标签映射
    private  Map<String,Object> projectLabelValueMap;// 标签值映射
    private   List<ProjectLabel> projectLabelList ;
    private  JSONObject jsonT;
    private int assessId;
    private  Map<String,Object> initParam ;//初始化参数，为业务扩展用
    private  IndexNodeProcessMapper indexNodeProcessMapper ;

    /**
     *构造函数
     * @param jsonT 前台传递的json数据
     * @param projectLabelList 项目标签列表
     * @param assessId  评估ID
     */
    public   LabelMap(JSONObject jsonT,List<ProjectLabel> projectLabelList,int assessId,Map<String,Object> initParam, IndexNodeProcessMapper indexNodeProcessMapper){
        this.projectLabelMap = new HashMap<>();
        this.projectLabelValueMap= new HashMap<>() ;
        this.assessId=assessId;
        this.jsonT=jsonT;
        this.projectLabelList=projectLabelList;
        this.initParam=initParam ;
        this.indexNodeProcessMapper = indexNodeProcessMapper ;
        build();
    }


    /**
     * 构造逻辑
     */
    private  void build(){
        //获取基本信息
        String infoStr = jsonT.getString("info");
        JSONObject json = JSONObject.fromObject(infoStr);
        //
        JSONArray indexResultJson = json.getJSONArray("indexResult");//质保结果
        //1、构建描述信息
        projectLabelMap.put("desc",getBasicLabel("desc"));
        projectLabelValueMap.put("desc",json.getString("desc").trim());
        //2、构建指标体系
        projectLabelMap.put("indexSer",getBasicLabel("indexSer"));
        projectLabelValueMap.put("indexSer",new WordPicture(json.getString("tmpH"),json.getString("tmpW"),json.getString("indexSer")));
        //3、构建评估结果
        projectLabelMap.put("accessResult",getBasicLabel("accessResult"));
        projectLabelValueMap.put("accessResult",initParam.get(String.valueOf(assessId)));
        //4、构建输出类型标签
        for(ProjectLabel label : projectLabelList){
            if(label.getType()==ProjectLabel.out){
                projectLabelMap.put(label.getLabel(),label);
            }
        }

        //5、构建指标,够造表格
        for (Object object : indexResultJson) {
            JSONObject jsonObj = (JSONObject) object;
            JSONArray indexPicArrJson = null;
            JSONArray indexTableArrJson = null;
            String indexContent = null;
            if (jsonObj.get("indexContent") != null) {
                indexContent = jsonObj.getString("indexContent");
                indexPicArrJson = jsonObj.getJSONArray("indexPicArr");
                indexTableArrJson = jsonObj.getJSONArray("indexTableArr");
            } else {
                indexPicArrJson = jsonObj.getJSONArray("indexPicArr");
                indexTableArrJson = jsonObj.getJSONArray("indexTableArr");
            }

            for (Object object2 : indexPicArrJson) {
                JSONObject jsonObj2 = (JSONObject) object2;
                String indexLabel =jsonObj2.getString("indexLabel");
                String indexPic = jsonObj2.getString("indexPic");
                String indexPicW = jsonObj2.getString("indexPicW");
                String indexPicH = jsonObj2.getString("indexPicH");
                ProjectLabel label = getBasicLabel(indexLabel);
                if(label!=null){
                    projectLabelMap.put(label.getLabel(),label);
                    projectLabelValueMap.put(label.getLabel(),new WordPicture(indexPicH,indexPicW,indexPic));
                }
            }

            for (Object object2 : indexTableArrJson) {
                JSONObject jsonObj2 = (JSONObject) object2;
                String title = jsonObj2.getString("title");
                String indexLabel =jsonObj2.getString("indexLabel");
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
                ProjectLabel label = getBasicLabel(indexLabel);
                if(label!=null){
                    projectLabelMap.put(label.getLabel(),label);
                    projectLabelValueMap.put(label.getLabel(),new WordTable(title,headers,dataset));
                }
            }
        }
    }

    private ProjectLabel getBasicLabel(String basicLabel){
        for(ProjectLabel label :projectLabelList){
            if(basicLabel.equals(label.getLabel())){
                return  label ;//找到直接进行替换
            }
        }
        return null ;
    }





    /**
     * 根据标签的tag,获取标签
     * @param labelTag
     * @return
     */
    private ProjectLabel  getLabel(String labelTag){
        return  projectLabelMap.get(labelTag) ;
    }


    /**
     * 获取标签值
     * @param labelTag
     * @rurn
     */
    public Object getLabelValue(String labelTag ){
        if(!projectLabelValueMap.containsKey(labelTag)){
            String[] paramArray = labelTag.split("_");
            Map pMap = new HashMap();
            pMap.put("indexId",paramArray[1]);
            pMap.put("businessId",assessId);
            pMap.put("nodeKey",paramArray[2]);
            List<IndexNodeProcess> list =  indexNodeProcessMapper.getNodeProcessByBusinessId(pMap);
            if(list==null ||list.size()==0){
                return  "";
            }
            return  list.get(0).getNodeInputParam()==null?"": list.get(0).getNodeInputParamExp();//支持输出类型支持
        }
        return  projectLabelValueMap.get(labelTag) ;
    }

}
