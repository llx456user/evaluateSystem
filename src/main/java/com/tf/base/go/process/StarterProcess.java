package com.tf.base.go.process;

import com.alibaba.fastjson.JSONObject;
import com.tf.base.util.ParamProcess;

/**
 * Created by HP on 2018/4/18.
 */
public interface StarterProcess {

    ParamProcess process(JSONObject jsonObject);

    void saveInputJsonValue(JSONObject inputJsonValue);

    void saveOutputJsonValue(JSONObject outJsonValue);
}
