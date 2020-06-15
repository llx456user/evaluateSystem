package com.tf.base.go.type;


public enum SignType {
    Error("error"),//错误
    MoreThan(">"),//大于
    GreaterOrEqual(">="),        //大于等于
    Equal("=="),       //等于
    LessThanOrEqual("<="),     //小于等于
    LessThan("<");        //小于

    private SignType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    /**
     * 获取分类
     *
     * @param value
     * @return
     */
    public static SignType getType(String value) {
        if (null == value) {
            return Error;
        } else if (value.equals(MoreThan.getValue())) {
            return MoreThan;
        } else if (value.equals(GreaterOrEqual.getValue())) {
            return GreaterOrEqual;
        } else if (value.equals(Equal.getValue())) {
            return Equal;
        } else if (value.equals(LessThanOrEqual.getValue())) {
            return LessThanOrEqual;
        } else if (value.equals(LessThan.getValue())) {
            return LessThan;
        }
        return Error;
    }
    private String value;
}
