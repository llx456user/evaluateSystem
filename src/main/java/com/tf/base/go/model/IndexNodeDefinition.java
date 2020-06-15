package com.tf.base.go.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tf.base.go.type.Category;
import com.tf.base.go.type.NodeType;

import java.util.*;

/**
 * Created by HP on 2018/4/12.
 */
public class IndexNodeDefinition {

    private static JSONObject buildServices(JSONArray array) {
        if (null != array && array.size() > 0) {
            JSONObject servicesJson = new JSONObject();
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

    public static IndexNodeDefinition build(JSONObject nodeJson) {
        if (null != nodeJson) {
            IndexNodeDefinition node = new IndexNodeDefinition();
            if (nodeJson.containsKey("id")) {
                node.setId(nodeJson.getInteger("id"));
            }
            if (nodeJson.containsKey("key")) {
                node.setKey(nodeJson.getInteger("key"));
            }
            if (nodeJson.containsKey("category")) {
                node.setCategory(nodeJson.getInteger("category"));
            }
            if (nodeJson.containsKey("type")) {
                node.setType(nodeJson.getString("type"));
            }
            if (nodeJson.containsKey("name")) {
                node.setName(nodeJson.getString("name"));
            }
            if (nodeJson.containsKey("filetype")) {
                node.setFileType(nodeJson.getString("filetype"));
            }
            if (nodeJson.containsKey("text")) {
                node.setText(nodeJson.getString("text"));
            }
            if (nodeJson.containsKey("loc")) {
                node.setLoc(nodeJson.getString("loc"));
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

    private Integer key;
    private Integer category;
    private String type;

    private Integer id;
    private String filePath;
    private String fileType;

    private String name;
    private String text;
    private String loc;

    private JSONObject inServices;
    private JSONObject outServices;

    private List<IndexNodeDefinition> nextNodes;
    private List<IndexNodeDefinition> preNodes;

    private List<IndexNodeLink> inIndexNodeLinks;
    private List<IndexNodeLink> outIndexNodeLinks;

    private int deep = 0;

    public void computeDeep(int preDeep) {
        this.deep = preDeep + 1;
        if (null != outIndexNodeLinks && outIndexNodeLinks.size() > 0) {
            for (int i = 0; i < outIndexNodeLinks.size(); i++) {
                IndexNodeDefinition node = outIndexNodeLinks.get(i).getToNode();
                if (null != node) {
                    node.computeDeep(this.deep);
                }
            }
        }
    }

    public Category getNodeCategory() {
        return Category.getCategory(this.category.intValue());
    }

    public NodeType getNodeType() {
        return NodeType.getType(this.type);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("{");
        sb.append(String.format("\"deep\":%d,", deep));
        if (null != id) {
            sb.append(String.format("\"id\":%d,", id));
        }
        if (null != key) {
            sb.append(String.format("\"key\":%d,", key));
        }
        if (null != category) {
            sb.append(String.format("\"category\":%d,", category));
        }
        if (null != type) {
            sb.append(String.format("\"type\":\"%s\",", type));
        }
        if (null != name) {
            sb.append(String.format("\"name\":\"%s\",", name));
        }
        if (null != fileType) {
            sb.append(String.format("\"fileType\":\"%s\",", fileType));
        }
        if (null != text) {
            sb.append(String.format("\"text\":\"%s\",", text));
        }
        if (null != loc) {
            sb.append(String.format("\"loc\":\"%s\",", loc));
        }
        if (null != inIndexNodeLinks && inIndexNodeLinks.size() > 0) {
            sb.append("\"inLinks\":[");
            for (int i = 0; i < inIndexNodeLinks.size(); i++) {
                IndexNodeLink inLink = inIndexNodeLinks.get(i);
                sb.append(String.format("{\"inPort\":\"%s\"}", inLink.getToPort()));
                if (i < inIndexNodeLinks.size() - 1) {
                    sb.append(",");
                }
            }
            sb.append("]");
        }
        if (null != outIndexNodeLinks && outIndexNodeLinks.size() > 0) {
            if (null != inIndexNodeLinks && inIndexNodeLinks.size() > 0) {
                sb.append(",");
            }
            sb.append("\"outLinks\":[");
            for (int i = 0; i < outIndexNodeLinks.size(); i++) {
                IndexNodeLink inLink = outIndexNodeLinks.get(i);
                sb.append(String.format("{\"outPort\":\"%s\",\"toPort\":\"%s\",\"toNode\":%s}", inLink.getFromPort(), inLink.getToPort(), null == inLink.getToNode() ? "" : inLink.getToNode().toString()));
                if (i < outIndexNodeLinks.size() - 1) {
                    sb.append(",");
                }
            }
            sb.append("]");
        }
        sb.append("}");
        return sb.toString();
    }

    private void addNextNode(IndexNodeDefinition node) {
        if (nextNodes == null) {
            nextNodes = new ArrayList<IndexNodeDefinition>();
        }
        nextNodes.add(node);
    }

    private void addPreNode(IndexNodeDefinition node) {
        if (preNodes == null) {
            preNodes = new ArrayList<IndexNodeDefinition>();
        }
        preNodes.add(node);
    }

    public void addInNodeLink(IndexNodeLink link) {
        if (null != link) {
            addPreNode(link.getFromNode());
            if (null == this.inIndexNodeLinks) {
                this.inIndexNodeLinks = new ArrayList<IndexNodeLink>();
            }
            this.inIndexNodeLinks.add(link);
        }
    }

    public void addOutNodeLink(IndexNodeLink link) {
        if (null != link) {
            addNextNode(link.getToNode());
            if (null == this.outIndexNodeLinks) {
                this.outIndexNodeLinks = new ArrayList<IndexNodeLink>();
            }
            this.outIndexNodeLinks.add(link);
            computeDeep(this.deep - 1);
        }
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public JSONObject getInServices() {
        return inServices;
    }

    public void setInServices(JSONObject inServices) {
        this.inServices = inServices;
    }

    public JSONObject getOutServices() {
        return outServices;
    }

    public void setOutServices(JSONObject outServices) {
        this.outServices = outServices;
    }

    public List<IndexNodeDefinition> getNextNodes() {
        return nextNodes;
    }

    public void setNextNodes(List<IndexNodeDefinition> nextNodes) {
        this.nextNodes = nextNodes;
    }

    public List<IndexNodeDefinition> getPreNodes() {
        return preNodes;
    }

    public void setPreNodes(List<IndexNodeDefinition> preNodes) {
        this.preNodes = preNodes;
    }

    public List<IndexNodeLink> getInIndexNodeLinks() {
        return inIndexNodeLinks;
    }

    public void setInIndexNodeLinks(List<IndexNodeLink> inIndexNodeLinks) {
        this.inIndexNodeLinks = inIndexNodeLinks;
    }

    public List<IndexNodeLink> getOutIndexNodeLinks() {
        return outIndexNodeLinks;
    }

    public void setOutIndexNodeLinks(List<IndexNodeLink> outIndexNodeLinks) {
        this.outIndexNodeLinks = outIndexNodeLinks;
    }

    public int getDeep() {
        return deep;
    }

    public void setDeep(int deep) {
        this.deep = deep;
    }
}
