package com.tf.base.jni;

public class ParameterTypeUtil {
    public static final String[] INT_TYPE_SET = {"int", "long"};
    public static final String[] FLOAT_TYPE_SET = {"float", "double"};


    /**
     * 判断是否为基本类型
     *
     * @param type
     * @return
     */
    public static boolean isBasicType(String type) {
        for (String baseType : INT_TYPE_SET) {
            if (baseType.equals(type)) {
                return true;
            }
        }
        for (String baseType : FLOAT_TYPE_SET) {
            if (baseType.equals(type)) {
                return true;
            }
        }
        return false;
    }


}
