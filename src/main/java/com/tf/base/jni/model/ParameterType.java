package com.tf.base.jni.model;

/**
 * Created by HP on 2018/4/24.
 */
public class ParameterType {

    private static final String STRING = "string";
    private static final String LONG = "long";
    private static final String DOUBLE = "double";
    private static final String FLOAT = "float";
    private static final String INTEGER = "integer";
    private static final String SHORT = "short";
    private static final String BYTE = "byte";
    private static final String BOOLEAN = "boolean";
    private static final String CHAR = "char";

    public static final boolean isStruct(String type) {
        return null != type && type.trim().length() > 0 && !isString(type) && !isDouble(type) && !isFloat(type) && !isLong(type) && !isInteger(type) && !isShort(type) && !isChar(type) && !isByte(type) && !isBoolean(type);
    }

    public static final boolean isString(String type) {
        return null != type && STRING.equals(type.toLowerCase());
    }

    public static final boolean isDouble(String type) {
        return null != type && DOUBLE.equals(type.toLowerCase());
    }

    public static final boolean isFloat(String type) {
        return null != type && FLOAT.equals(type.toLowerCase());
    }

    public static final boolean isLong(String type) {
        return null != type && LONG.equals(type.toLowerCase());
    }

    public static final boolean isInteger(String type) {
        return null != type && INTEGER.equals(type.toLowerCase());
    }

    public static final boolean isShort(String type) {
        return null != type && SHORT.equals(type.toLowerCase());
    }

    public static final boolean isChar(String type) {
        return null != type && CHAR.equals(type.toLowerCase());
    }

    public static final boolean isByte(String type) {
        return null != type && BYTE.equals(type.toLowerCase());
    }

    public static final boolean isBoolean(String type) {
        return null != type && BOOLEAN.equals(type.toLowerCase());
    }

    public static  final  String getParamType(String paramType){
        if(paramType.startsWith("int")){
            return  INTEGER ;
        }else  if(paramType.startsWith("long")){
            return LONG ;
        }else  if(paramType.startsWith("float")){
            return FLOAT;
        }else  if(paramType.startsWith("double")){
            return DOUBLE ;
        }else  if(paramType.startsWith("string")){
            return  STRING ;
        }
        return   paramType ;
    }
}
