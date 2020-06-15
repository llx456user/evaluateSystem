package com.tf.base;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class TestString {
    public static void main(String[] args) {
        String test1="v1.023432";
        test1=test1.replaceAll("\\.","");
        System.out.println(test1+"ceshi");

        String p = "2" ;
        System.out.println(getDouble2String(p));
    }

    private static   String getDouble2String(String value){
        BigDecimal b = new BigDecimal(Double.valueOf(value).doubleValue());
        double Value = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(Value) ;
    }
}
