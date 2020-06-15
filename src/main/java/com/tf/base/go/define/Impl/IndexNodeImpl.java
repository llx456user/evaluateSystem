package com.tf.base.go.define.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tf.base.go.define.IndexNode;
import com.tf.base.go.define.NodeLink;
import com.tf.base.go.type.Category;
import com.tf.base.go.type.NodeType;
import com.tf.base.go.type.SignType;

import java.util.*;

public class IndexNodeImpl implements IndexNode {
    private Integer key;
    private Category category;
    private NodeType nodeType;
    private String nodeText;
    private Integer modelId;
    //符合标识



    private SignType signType;
    //条件值
    private String settingNum;
    //输入服务
    private JSONObject inServices;
    //输出服务
    private JSONObject outServices;
    //参数名
    private  String paramName ;
    //参数默认值
    private  String defaultValue ;

    private  String xTitle;

    private  String yTitle;

    private  String zTitle;

    private  String pictureTitle;

    //输入参数连线列表
    private List<NodeLink> inNodeLinks = new ArrayList<>();
    //输出参数连线列表
    private List<NodeLink> outNodeLinks = new ArrayList<>();
    //前置节点
    private Map<Integer,IndexNode> preNodes = new HashMap<>();
    //后置节点
    private Map<Integer,IndexNode> nextNodes = new HashMap<>();

    @Override
    public boolean hasLine() {
        //出入节点都为空，则为没有线
        if (inNodeLinks.size() == 0 && outNodeLinks.size() == 0) {
            return false;
        }
//        if(inNodeLinks.size()!=this.preNodes.size()){
//            return  false ;
//        }
        return true;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    @Override
    public Integer getKey() {
        return this.key;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    @Override
    public Integer getModelId() {
        return this.modelId;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public Category getNodeCategory() {
        return this.category;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    @Override
    public NodeType getNodeType() {
        return this.nodeType;
    }

    public void setNodeText(String text) {
        this.nodeText = text;
    }

    @Override
    public String getNodeText() {
        return this.nodeText;
    }

    @Override
    public Collection<IndexNode> getPreNodes() {
        return preNodes.values();
    }

    @Override
    public Collection<IndexNode> getNextNodes() {
        return nextNodes.values();
    }

    @Override
    public List<NodeLink> getInNodeLinks() {
        return this.inNodeLinks;
    }


    /**
     * 增加节点连线
     *
     * @param link
     */
    @Override
    public void addInNodeLink(NodeLink link) {
        if (link != null) {
            addPreNode(link.getFromNode());
        }
        this.inNodeLinks.add(link);
    }

    /**
     * 增加
     *
     * @param fromNode
     */
    private void addPreNode(IndexNode fromNode) {
        this.preNodes.put(fromNode.getKey(),fromNode);
    }

    /**
     * 增加下一个节点
     *
     * @param toNode
     */
    private void addNextNode(IndexNode toNode) {
        this.nextNodes.put(toNode.getKey(),toNode);
    }


    @Override
    public List<NodeLink> getOutNodeLink() {
        return this.outNodeLinks;
    }


    @Override
    public void addOutNodeLink(NodeLink link) {
        if (link != null) {
            addNextNode(link.getToNode());
        }
        this.outNodeLinks.add(link);
    }

    @Override
    public boolean isOutNode() {
        boolean isOut = true;
        //针对model节点来说
        if (getNodeCategory() != Category.Model) {
            isOut = false;
            return isOut;
        }
        //判断所有后置的节点是否都为图表
        if (this.getNextNodes().size() > 0) {
            for (IndexNode node : getNextNodes()) {
                if (node.getNodeCategory() != Category.Picture || node.getNodeCategory() != Category.Grid) {
                    isOut = false;
                }
            }
        } else {
            isOut = false;
        }
        return isOut;
    }

    @Override
    public boolean isEndNode() {
        if (getNodeCategory() == Category.End){
            return  true ;
        }
        return false;
    }

    @Override
    public boolean isStartNode() {
        boolean isStart = true;
        if (getNodeCategory() == Category.Model) {
            if (this.preNodes.keySet().size() > 0) {
                for (IndexNode node : preNodes.values()) {
                    if (node.getNodeCategory() != Category.Datasource) {
                        isStart = false;
                        break;
                    }
                }
            } else {
                isStart = false;
            }
        } else {
            isStart = false;
        }
        return isStart;
    }


    public static IndexNode build(JSONObject nodeJson) {
        if (null != nodeJson) {
            IndexNodeImpl node = new IndexNodeImpl();
            if (nodeJson.containsKey("id")) {
                //设置模型ID
                node.setModelId(nodeJson.getInteger("id"));
            }
            if (nodeJson.containsKey("key")) {
                //设置节点的Key
                node.setKey(nodeJson.getInteger("key"));
            }
            if (nodeJson.containsKey("category")) {
                //设置节点分类
                node.setCategory(Category.getCategory(nodeJson.getInteger("category")));
            }
            if (nodeJson.containsKey("type")) {
                //设置节点类型
                node.setNodeType(NodeType.getType(nodeJson.getString("type")));
            }
            if (nodeJson.containsKey("sign")) {
                //设置节点类型
                node.setSignType(SignType.getType(nodeJson.getString("sign")));
            }
            if (nodeJson.containsKey("settingNum")) {
                //设置节点类型
                node.setSettingNum(nodeJson.getString("settingNum"));
            }
            if (nodeJson.containsKey("paramName")) {
                //设置节点参数名称
                node.setParamName(nodeJson.getString("paramName"));
            }
            if (nodeJson.containsKey("defaultValue")) {
                //设置节点参数默认值
                node.setDefaultValue(nodeJson.getString("defaultValue"));
            }
            if (nodeJson.containsKey("pictureTitle")) {
                node.setPictureTitle(nodeJson.getString("pictureTitle"));
            }
            if (nodeJson.containsKey("xTitle")) {
                node.setxTitle(nodeJson.getString("xTitle"));
            }
            if (nodeJson.containsKey("yTitle")) {
                node.setyTitle(nodeJson.getString("yTitle"));
            }
            if (nodeJson.containsKey("zTitle")) {
                node.setzTitle(nodeJson.getString("zTitle"));
            }
            if (nodeJson.containsKey("text")) {//设置节点的文本信息
                node.setNodeText(nodeJson.getString("text"));
            }
            if (nodeJson.containsKey("outServices")) {
                node.setOutServices(buildServices(nodeJson.getJSONArray("outServices")));
            }
            if (nodeJson.containsKey("inServices")) {
                node.setInServices(buildServices(nodeJson.getJSONArray("inServices")));
            }
            return node;
        }
        return null;
    }

    private static JSONObject buildServices(JSONArray array) {
        if (null != array && array.size() > 0) {
            JSONObject servicesJson = new JSONObject(new LinkedHashMap<>());
            for (int i = 0; i < array.size(); i++) {
                JSONObject serviceJson = array.getJSONObject(i);
                if (null != serviceJson) {
                    servicesJson.put(serviceJson.getString("name"), serviceJson);
                }
            }
            return servicesJson;
        }
        return null;
    }
    @Override
    public String getxTitle() {
        return xTitle;
    }

    public void setxTitle(String xTitle) {
        this.xTitle = xTitle;
    }
    @Override
    public String getyTitle() {
        return yTitle;
    }

    public void setyTitle(String yTitle) {
        this.yTitle = yTitle;
    }

    @Override
    public String getPictureTitle() {
        return pictureTitle;
    }

    public void setPictureTitle(String pictureTitle) {
        this.pictureTitle = pictureTitle;
    }

    public void setSignType(SignType signType) {
        this.signType = signType;
    }

    @Override
    public SignType getSignType() {
        return this.signType;
    }

    public void setSettingNum(String settingNum) {
        this.settingNum = settingNum;
    }

    @Override
    public String getSettingNum() {
        return this.settingNum;
    }
    @Override
    public String getParamName() {
        return paramName;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }
    @Override
    public JSONObject getOutServices() {
        return this.outServices;
    }

    public void setOutServices(JSONObject outServices) {
        this.outServices = outServices;
    }

    @Override
    public JSONObject getInServices() {
        return this.inServices;
    }




    public void setInServices(JSONObject inServices) {
        this.inServices = inServices;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public  String getzTitle(){
        return  this.zTitle;
    }

    public void setzTitle(String zTitle) {
        this.zTitle = zTitle;
    }
}
