package com.tf.base.go.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tf.base.go.define.NodeLink;
import com.tf.base.go.service.IndexTask;
import com.tf.base.go.service.PictureUtil;
import com.tf.base.go.type.NodeType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinkLineBean {
    private List<List<String>> xArray;//x轴
    private List<List<String>> yArray;//y轴
    private List<List<String>> zArray;//z轴
    private List<NodeLink> nodeLinks;//线
    private NodeLink sLink;//结构体线
    private NodeLink lLink;//标签线
    private List<String> lineNames ;//线的名字
    Map<Integer, IndexTask> nodeMap ;

    public LinkLineBean(List<NodeLink> nodeLinks, Map<Integer, IndexTask> nodeMap) {
        xArray = new ArrayList<>();
        yArray = new ArrayList<>();
        zArray = new ArrayList<>();
        sLink = null;
        lLink = null;
        this.nodeLinks = nodeLinks;
        this.nodeMap = nodeMap;
        this.lineNames = new ArrayList<>();
        bulid();
    }


    private void bulid() {
        for (NodeLink nLink : nodeLinks) {
            if ("struct".equals(nLink.getToPort())) {
                this.sLink = nLink;
            } else if (nLink.isX()) {
                xArray.add(getValue(nLink));
            } else if (nLink.isY()) {
                lineNames.add(nLink.getToPort());//默认线的名字
                yArray.add(getValue(nLink));
            } else if (nLink.isL()) {
                this.lLink = nLink;
            } else {
                zArray.add(getValue(nLink));
            }
        }
    }

    /**
     * 获取x轴的序列
     *
     * @return
     */
    public List<List<String>> getXArray() {
        return this.xArray;
    }


    /**
     * 获取y轴的序列
     *
     * @return
     */
    public List<List<String>> getYArray() {
        return this.yArray;
    }

    /**
     * 获取z轴序列
     *
     * @return
     */
    List<List<String>> getZArray() {
        return this.zArray;
    }

    /**
     * 获取结构体连线
     * @return
     */
    public NodeLink getStructLinks() {
        return this.sLink;
    }

    /**
     * 获取label连线
     * @return
     */
    public NodeLink getLabelLinks() {
        return this.lLink;
    }

    public List<String> getLabelValues(){
        return  getValue(this.lLink);
    }


    /**
     * 获取线性图的数据
     * @return
     */
    public  JSONObject getLineDatas(String pictureType){
        JSONObject jsonObject = new JSONObject();
        JSONArray datas = new JSONArray();
        JSONArray legendData = new JSONArray();
        for (int i=0;i<yArray.size();i++){
//             JSONObject jsonObject = new JSONObject();
//             jsonObject.put("name",getLableName(i));
             JSONArray datavalue = new JSONArray();
             List<String> xArr =getX(i,yArray.get(i).size());
             List<String> yArr = yArray.get(i);
            legendData.add(getLableName(i));
             for(int j=0;j<xArr.size();j++){
                 JSONArray value = new JSONArray();
                 value.add(xArr.get(j));
                 value.add(yArr.get(j));
                 datavalue.add(value);
             }
//            jsonObject.put("data",datavalue);
//            datas.add(jsonObject)  ;
            if(pictureType.equals(NodeType.Hline.getValue())){
                datas.add(PictureUtil.getLineJson(datavalue,i,getLableName(i),true));
            }else{
                datas.add(PictureUtil.getLineJson(datavalue,i,getLableName(i),false));
            }
        }

////      object.put("name",tJson.getString("label"));//标题
////       object.put("data",datavalue);//值
//        if(pictureType.equals(NodeType.Line.getValue())){
//            datas.add(PictureUtil.getLineJson(datavalue,i,tJson.getString("label"),false));
//        }else{
//            datas.add(PictureUtil.getLineJson(datavalue,i,tJson.getString("label"),true));
//        }
        jsonObject.put("datas",datas);
        jsonObject.put("legendData",legendData);
        return  jsonObject;
    }


    /**
     * 获取x轴
     * @param i
     * @param count
     * @return
     */
   private  List<String> getX(int i,int count){
        List<String> xArr = new ArrayList<>();
        if(this.xArray.size()==0||this.xArray.size()<=i){
            for(int j=0;j<count;j++){
                xArr.add(j+1+"");
            }
            return  xArr ;
        }

        return  xArray.get(i);
   }




    /**
     * 获取label名称
     * @param i
     * @return
     */
    private String getLableName(int i){
        if(this.lLink!=null&&getLabelValues().size()>i){
            return  getLabelValues().get(i);
        }
        if(this.lineNames.size()>i){
            return this.lineNames.get(i);
        }
        return  "";
    }



    /**
     * 获取值
     *
     * @param link
     * @return
     */
    private List<String> getValue(NodeLink link) {
        String outputParamString = nodeMap.get(link.getFromNode().getKey()).getIndexNodeProcess().getNodeOutputParamExp();
        JSONObject outJsonObject = JSONObject.parseObject(outputParamString);
        List<String> outList = new ArrayList<>();
        Object object = outJsonObject.get(link.getFromPort());
        if (object instanceof JSONArray) {//输出是数组
            JSONArray out = (JSONArray) object;
            for (int i = 0; i < out.size(); i++) {
                outList.add(out.getString(i));
            }
        } else {
            outList.add(outJsonObject.getString(link.getFromPort()));
        }
        return outList;
    }

}
