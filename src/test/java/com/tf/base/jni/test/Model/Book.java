package com.tf.base.jni.test.Model;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by HP on 2018/4/24.
 */
public class Book {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Book parse(JSONObject bookJson) {
        return null;
    }
}
