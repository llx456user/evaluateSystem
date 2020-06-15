package com.tf.base.go.type;

/**
 * 节点分类
 */
public enum NodeStatus {

    Fail(2),//执行失败
    Success(1),//执行成功
    IFFalse(2),//IF不执行
    Initialization(0); //初始化

    private NodeStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    /**
     * 获取分类
     *
     * @param value
     * @return
     */
    public static NodeStatus getStatus(int value) {
        if (value == Fail.getValue()) {
            return Fail;
        } else if (value == Success.getValue()) {
            return Success;
        }
        return Initialization;
    }

    private int value;
}

