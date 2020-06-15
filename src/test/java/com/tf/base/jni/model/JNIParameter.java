package com.tf.base.jni.model;


/**
 * Created by HP on 2018/4/24.
 */
public class JNIParameter {

    private String name;//json ke

    private String type;//code key as java class or c struct

    private boolean isArray = false;

    public JNIParameter() {
    }

    public JNIParameter(String name, String type) {
        this(name, type, false);
    }

    public JNIParameter(String name, String type, boolean isArray) {
        this.name = name;
        this.type = type;
        this.isArray = isArray;
    }

    public JNIParameter(String name, String type, JNIParameter[] parameters) {
        this(name, type, parameters, false);
    }

    public JNIParameter(String name, String type, JNIParameter[] parameters, boolean isArray) {
        this.name = name;
        this.type = type;
        this.isArray = isArray;
        this.parameters = parameters;
    }

    private boolean isInput = true;

    public boolean isInput() {
        return isInput;
    }

    public boolean getStruct() {
        return ParameterType.isStruct(this.type);
    }

    public boolean getQuotation(){
        return ParameterType.isString(type);
    }

    public String getCppType(){
        if(ParameterType.isString(type)){
            return "char";
        }else if(ParameterType.isInteger(type)){
            return "int";
        }else if(ParameterType.isLong(type)){
            return "int";
        }else if(ParameterType.isShort(type)){
            return "int";
        }else if(ParameterType.isDouble(type)){
            return "double";
        }else if(ParameterType.isFloat(type)){
            return "float";
        }else if(ParameterType.isStruct(type)){
            return type;
        }else{
            return type;
        }
    }

    public boolean getString(){
        return ParameterType.isString(type);
    }

    public boolean getNumber(){
        return getDouble()|| getInt();
    }

    public boolean getInt(){
        return  ParameterType.isLong(type) || ParameterType.isInteger(type) || ParameterType.isShort(type) || ParameterType.isByte(type);
    }

    public boolean getDouble(){
        return ParameterType.isDouble(type) || ParameterType.isFloat(type);
    }

    JNIParameter[] parameters;

    public void parse() {
        //todo
        if (ParameterType.isStruct(type)) {
            if (null != parameters) {
                for (int i = 0; i < parameters.length; i++) {
                    parameters[i].parse();
                }
            }
        } else if (ParameterType.isString(type)) {
        } else if (ParameterType.isDouble(type)) {
        } else if (ParameterType.isFloat(type)) {
        } else if (ParameterType.isLong(type)) {
        } else if (ParameterType.isInteger(type)) {
        } else if (ParameterType.isShort(type)) {
        } else if (ParameterType.isChar(type)) {
        } else if (ParameterType.isByte(type)) {
        } else if (ParameterType.isBoolean(type)) {

        } else {

        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getArray() {
        return isArray;
    }

    public void setIsArray(boolean isArray) {
        this.isArray = isArray;
    }

    public JNIParameter[] getParameters() {
        return parameters;
    }

    public void setParameters(JNIParameter[] parameters) {
        this.parameters = parameters;
    }
}
