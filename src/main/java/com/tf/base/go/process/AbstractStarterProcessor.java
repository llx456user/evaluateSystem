package com.tf.base.go.process;

import com.alibaba.fastjson.JSONObject;

import java.util.logging.Logger;

public abstract class AbstractStarterProcessor implements StarterProcess {

    private Logger logger = Logger.getLogger(StarterProcessorService.class.getName());

    @Override
    public void saveInputJsonValue(JSONObject inputJsonValue) {
        //todo
        if (null != inputJsonValue) {
            logger.info(inputJsonValue.toString());
        }
    }

    @Override
    public void saveOutputJsonValue(JSONObject outJsonValue) {
        //todo
        if (null != outJsonValue) {
            logger.info(outJsonValue.toString());
        }

    }
}