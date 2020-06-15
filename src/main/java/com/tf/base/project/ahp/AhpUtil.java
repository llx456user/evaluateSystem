package com.tf.base.project.ahp;

import net.sf.json.JSONArray;

public class AhpUtil {

    public static float[] getAhp(JSONArray xArray){
        MatixcJsonBean matixcJsonBean = new MatixcJsonBean(xArray);
        AhpLevel ahpLevel = new AhpLevel(matixcJsonBean.getMatixcFloat());
        return ahpLevel.W ;
    }

}
