package com.tf.base.common.service;

/**
 * Created by wanquan on 2018/3/24.
 */
public class ServiceStatus {
    private Status status;
    private String msg;

    public ServiceStatus(Status status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public enum Status {

        Success(1), Fail(0);

        private int status;

        Status(int status) {
            this.status = status;
        }

        public int getValue() {
            return status;
        }

        public String toString() {
            return new Integer(this.status).toString();
        }
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
