package com.tf.base.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 统计控制层
 *
 * @author yangkun 20171121
 */
@Controller
public class StatisticsController {


    /**
     * 各资金方日进件量统计 fro echarts
     * @return
     */
    @RequestMapping(value = "/statistics/dayLoanCountForEc")
    @ResponseBody
    public JSONObject dayLoanCountForEc(){
        JSONObject jo=new JSONObject();

        String[] invNames={"华融","小诺","口袋","大丰收"};
        Integer[] loanCounts={10000,20000,30000,40000};

        jo.put("invNames",invNames);
        jo.put("loanCounts",loanCounts);
        return jo;
    }
    @RequestMapping(value = "/statistics/dayFangkuanCountForEc")
    @ResponseBody
    public JSONObject dayFangkuanCountForEc(){
        JSONObject jo=new JSONObject();

        String[] invNames={"华融","小诺","口袋","大丰收"};
        JSONArray  fangkuanCounts=new JSONArray();
        JSONObject jo1=new JSONObject();
        jo1.put("value",400);
        jo1.put("name","华融");

        JSONObject jo2=new JSONObject();
        jo2.put("value",310);
        jo2.put("name","小诺");

        JSONObject jo3=new JSONObject();
        jo3.put("value",234);
        jo3.put("name","口袋");

        JSONObject jo4=new JSONObject();
        jo4.put("value",335);
        jo4.put("name","大丰收");

        fangkuanCounts.add(jo1);
        fangkuanCounts.add(jo2);
        fangkuanCounts.add(jo3);
        fangkuanCounts.add(jo4);

        jo.put("invNames",invNames);
        jo.put("fangkuanCounts",fangkuanCounts);
        return jo;
    }
}
