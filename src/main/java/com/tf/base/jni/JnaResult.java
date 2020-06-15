package com.tf.base.jni;

public class JnaResult {
    public final static int JNA_SUCESS=1;
    public final static int JNA_ERROR =0 ;
    //返回码1表示正确返回，0表示错误返回
    private int resultcode ;
    //正确返回为
    private  String msg ;

    public int getResultcode() {
        return resultcode;
    }

    public void setResultcode(int resultcode) {
        this.resultcode = resultcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
