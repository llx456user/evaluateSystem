package com.tf.base.go.process;

import com.alibaba.fastjson.JSONObject;
import com.tf.base.go.model.IndexDefinition;
import com.tf.base.go.model.IndexNodeDefinition;
import com.tf.base.go.model.IndexNodeLink;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IndexManager {

    @Autowired
    private StarterProcessorService starterProcessorService;

    private Map<Integer, NodeStarter> keyStartMap = new HashMap<Integer, NodeStarter>();

    public void build(IndexDefinition index) {
        if (null != index) {
            buildStartMap(index.getNodes());
            buildLinkList(index.getLinkList());
        }
    }

    private void buildStartMap(List<IndexNodeDefinition> nodeList) {
        if (null != nodeList && nodeList.size() > 0) {
            for (IndexNodeDefinition node : nodeList) {
                StarterProcess process = starterProcessorService.buildNodeProcessor(node, null);
                NodeStarter starter = new NodeStarter();
                starter.setProcessor(process);
                starter.setNode(node);
                keyStartMap.put(node.getKey(), starter);
            }
        }
    }

    public void configStarter(JSONObject configJson) {
        if (null != configJson && !configJson.isEmpty()) {
            Set<String> keySet = configJson.keySet();
            for (String key : keySet) {
                if (StringUtils.isNotBlank(key)) {
                    NodeStarter starter = keyStartMap.get(Integer.parseInt(key));
                    if (null != starter) {
                        JSONObject config = configJson.getJSONObject(key);
                        if (null != config) {
                            StarterProcess process = starterProcessorService.buildNodeProcessor(starter.getNode(), config);
                            starter.setProcessor(process);
                        }
                    }
                }
            }
        }
    }

    private void buildLinkList(List<IndexNodeLink> linkList) {
        if (null != linkList && linkList.size() > 0) {
            for (IndexNodeLink nodeLink : linkList) {
                buildStarterLink(nodeLink);
            }
        }
    }

    private void buildStarterLink(IndexNodeLink nodeLink) {
        if (null != nodeLink) {
            NodeStarter fromStarter = keyStartMap.get(nodeLink.getFromKey());
            NodeStarter toStarter = keyStartMap.get(nodeLink.getToKey());

            NodeStarterLink starterLink = new NodeStarterLink(nodeLink);
            starterLink.setToStarter(toStarter);
            starterLink.setFromStarter(fromStarter);
            if (null != toStarter) {
                toStarter.addInputLink(starterLink);
            }
            if (null != fromStarter) {
                fromStarter.addOutputLink(starterLink);
            }
        }
    }

    public Collection<NodeStarter> getStarterList() {
        return keyStartMap.values();
    }

    public void allowStart() {
        if (null != getStarterList() && getStarterList().size() > 0) {
            for (NodeStarter node : getStarterList()) {
                node.allowStart();
            }
        }
    }

    public void start(JSONObject paramJson) {
        configStarter(paramJson);
        for (NodeStarter starter : getStarterList()) {
            starter.start();
        }
    }

}
