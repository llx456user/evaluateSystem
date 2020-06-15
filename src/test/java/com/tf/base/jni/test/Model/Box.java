package com.tf.base.jni.test.Model;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by HP on 2018/4/24.
 */
public class Box {
    private String name;
    private Pen[] pens;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pen[] getPens() {
        return pens;
    }

    public void setPens(Pen[] pens) {
        this.pens = pens;
    }

    public static Box parse(JSONObject boxJson) {
        return null;
    }
}
