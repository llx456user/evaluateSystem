package com.tf.base.go.process;

import com.alibaba.fastjson.JSONObject;
import com.tf.base.util.ParamProcess;

public class SimpleStarterProcessor extends AbstractStarterProcessor {

    ParamProcess param;

    public SimpleStarterProcessor(){}

    public SimpleStarterProcessor(ParamProcess param) {
        this.param = param;
    }

    @Override
    public ParamProcess process(JSONObject jsonObject) {
        return this.param;
    }
}