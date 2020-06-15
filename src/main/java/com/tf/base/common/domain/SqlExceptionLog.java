package com.tf.base.common.domain;

import java.util.Date;

public class SqlExceptionLog {
    private Integer id;

    private String systemName;

    private String sqlId;

    private String sqlSource;

    private String sqlParameter;

    private String sqlType;

    private String exceptionMessage;

    private Date createTime;

    private String exceptionStack;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName == null ? null : systemName.trim();
    }

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId == null ? null : sqlId.trim();
    }

    public String getSqlSource() {
        return sqlSource;
    }

    public void setSqlSource(String sqlSource) {
        this.sqlSource = sqlSource == null ? null : sqlSource.trim();
    }

    public String getSqlParameter() {
        return sqlParameter;
    }

    public void setSqlParameter(String sqlParameter) {
        this.sqlParameter = sqlParameter == null ? null : sqlParameter.trim();
    }

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType == null ? null : sqlType.trim();
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage == null ? null : exceptionMessage.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getExceptionStack() {
        return exceptionStack;
    }

    public void setExceptionStack(String exceptionStack) {
        this.exceptionStack = exceptionStack == null ? null : exceptionStack.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", systemName=").append(systemName);
        sb.append(", sqlId=").append(sqlId);
        sb.append(", sqlSource=").append(sqlSource);
        sb.append(", sqlParameter=").append(sqlParameter);
        sb.append(", sqlType=").append(sqlType);
        sb.append(", exceptionMessage=").append(exceptionMessage);
        sb.append(", createTime=").append(createTime);
        sb.append(", exceptionStack=").append(exceptionStack);
        sb.append("]");
        return sb.toString();
    }
}