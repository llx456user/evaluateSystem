package com.tf.base.project.ahp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

public class AhpTest {

    @Test
   public  void testAhp(){
        String jsonstr = "{\"xArray\":[{\"42\":\"1\",\"43\":\"2\",\"44\":\"2\"},{\"42\":\"0.5\",\"43\":\"1\",\"44\":\"2\"},{\"42\":\"0.5\",\"43\":\"0.5\",\"44\":\"1\"}],\"yArray\":[{\"42\":\"1\",\"43\":\"0.5\",\"44\":\"0.5\"},{\"42\":\"2\",\"43\":\"1\",\"44\":\"0.5\"},{\"42\":\"2\",\"43\":\"2\",\"44\":\"1\"}]}";
        JSONObject json = JSONObject.fromObject(jsonstr);
        JSONArray xArray = json.getJSONArray("xArray");
        float[] f = AhpUtil.getAhp(xArray);
        for (float f1:f
             ) {
            System.out.println(f1);
        }
    }

}
