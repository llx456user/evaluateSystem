package com.tf.base.go.process;

import com.alibaba.fastjson.JSONObject;
import com.tf.base.go.model.IndexNodeDefinition;
import com.tf.base.go.model.IndexNodeLink;
import com.tf.base.util.ParamProcess;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class NodeStarter {

    private IndexNodeDefinition node;

    private boolean isStarted = false;

    private JSONObject inputJsonValue;
    private JSONObject outputJsonValue;
    private StarterProcess processor;

    private Collection<NodeStarterLink> inputLinks;
    private Collection<NodeStarterLink> outputLinks;

    public void addOutputLink(NodeStarterLink outputLink) {
        if (null == this.outputLinks) {
            this.outputLinks = new ArrayList<>();
        }
        this.outputLinks.add(outputLink);
    }

    public void addInputLink(NodeStarterLink inputLink) {
        if (null == this.inputLinks) {
            this.inputLinks = new ArrayList<>();
        }
        this.inputLinks.add(inputLink);
    }

    public void formatInputJson() {
        if (null == inputJsonValue && null != inputLinks && inputLinks.size() > 0) {
            inputJsonValue = new JSONObject();
            for (NodeStarterLink link : inputLinks) {
                if (null != link && null != link.getFromStarter()) {
                    NodeStarter inputStarter = link.getFromStarter();
                    if (StringUtils.isNotBlank(link.getFromPort()) && null != inputStarter.getOutputJsonValue()) {
                        Object value = inputStarter.getOutputJsonValue().get(link.getFromPort());
                        inputJsonValue.put(link.getToPort(), value);
                    }
                }
            }
        }
    }

    private boolean contain(String[] array, String key) {
        if (null != array && StringUtils.isNotBlank(key)) {
            for (String each : array) {
                if (key.equals(each)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void formatOutJson(ParamProcess paramProcess) {
        if (null != paramProcess && null != outputLinks && outputLinks.size() > 0) {
            this.outputJsonValue = new JSONObject();
            String[] columnNameArray = paramProcess.getColumnsName();
            for (NodeStarterLink link : outputLinks) {
                if (null != link && null != link.getFromPort()) {
                    String key = link.getFromPort();
                    if (!contain(columnNameArray, key)) {
                        outputJsonValue.put(key, 0);
                    } else if (link.isDoublePortType()) {
                        outputJsonValue.put(key, paramProcess.getColumnsDoubleValues(key));
                    } else if (link.isIntPortType()) {
                        outputJsonValue.put(key, paramProcess.getColumnsIntValue(key));
                    } else if (link.isFloatPortType()) {
                        outputJsonValue.put(key, paramProcess.getColumnsFloatValues(key));
                    } else if (link.isLongPortType()) {
                        outputJsonValue.put(key, paramProcess.getColumnsLongValues(key));
                    } else {
                        outputJsonValue.put(key, paramProcess.getColumnsDoubleValues(key));
                    }
                }
            }
        }
    }

    public boolean canStart() {
        if (null == inputLinks || inputLinks.size() == 0) {
            return true;
        } else {
            for (NodeStarterLink link : inputLinks) {
                if (null != link && null != link.getFromStarter() && !link.getFromStarter().isStarted()) {
                    return false;
                }
            }
            return true;
        }
    }

    public void preStart() {
        if (null == inputLinks || inputLinks.size() == 0) {
            for (NodeStarterLink link : inputLinks) {
                if (null != link && null != link.getFromStarter()) {
                    link.getFromStarter().start();
                }
            }
        }
    }

    public void start() {
        if (!isStarted) {

            if (!canStart()) {
                preStart();
            }

            formatInputJson();

            //process execute
            if (null != processor) {
                processor.saveInputJsonValue(inputJsonValue);
                ParamProcess param = processor.process(inputJsonValue);
                formatOutJson(param);
                processor.saveOutputJsonValue(outputJsonValue);
            }

            isStarted = true;
        }
    }

    public void allowStart() {
        isStarted = false;
    }


    public boolean isStarted() {
        return isStarted;
    }

    public void setProcessor(StarterProcess processor) {
        this.processor = processor;
    }

    public JSONObject getInputJsonValue() {
        return inputJsonValue;
    }

    public void setInputJsonValue(JSONObject inputJsonValue) {
        this.inputJsonValue = inputJsonValue;
    }

    public JSONObject getOutputJsonValue() {
        return outputJsonValue;
    }

    public void setOutputJsonValue(JSONObject outputJsonValue) {
        this.outputJsonValue = outputJsonValue;
    }

    public IndexNodeDefinition getNode() {
        return node;
    }

    public void setNode(IndexNodeDefinition node) {
        this.node = node;
    }
}
