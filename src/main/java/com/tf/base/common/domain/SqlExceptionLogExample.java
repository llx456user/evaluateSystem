package com.tf.base.common.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SqlExceptionLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SqlExceptionLogExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSystemNameIsNull() {
            addCriterion("systemName is null");
            return (Criteria) this;
        }

        public Criteria andSystemNameIsNotNull() {
            addCriterion("systemName is not null");
            return (Criteria) this;
        }

        public Criteria andSystemNameEqualTo(String value) {
            addCriterion("systemName =", value, "systemName");
            return (Criteria) this;
        }

        public Criteria andSystemNameNotEqualTo(String value) {
            addCriterion("systemName <>", value, "systemName");
            return (Criteria) this;
        }

        public Criteria andSystemNameGreaterThan(String value) {
            addCriterion("systemName >", value, "systemName");
            return (Criteria) this;
        }

        public Criteria andSystemNameGreaterThanOrEqualTo(String value) {
            addCriterion("systemName >=", value, "systemName");
            return (Criteria) this;
        }

        public Criteria andSystemNameLessThan(String value) {
            addCriterion("systemName <", value, "systemName");
            return (Criteria) this;
        }

        public Criteria andSystemNameLessThanOrEqualTo(String value) {
            addCriterion("systemName <=", value, "systemName");
            return (Criteria) this;
        }

        public Criteria andSystemNameLike(String value) {
            addCriterion("systemName like", value, "systemName");
            return (Criteria) this;
        }

        public Criteria andSystemNameNotLike(String value) {
            addCriterion("systemName not like", value, "systemName");
            return (Criteria) this;
        }

        public Criteria andSystemNameIn(List<String> values) {
            addCriterion("systemName in", values, "systemName");
            return (Criteria) this;
        }

        public Criteria andSystemNameNotIn(List<String> values) {
            addCriterion("systemName not in", values, "systemName");
            return (Criteria) this;
        }

        public Criteria andSystemNameBetween(String value1, String value2) {
            addCriterion("systemName between", value1, value2, "systemName");
            return (Criteria) this;
        }

        public Criteria andSystemNameNotBetween(String value1, String value2) {
            addCriterion("systemName not between", value1, value2, "systemName");
            return (Criteria) this;
        }

        public Criteria andSqlIdIsNull() {
            addCriterion("sqlId is null");
            return (Criteria) this;
        }

        public Criteria andSqlIdIsNotNull() {
            addCriterion("sqlId is not null");
            return (Criteria) this;
        }

        public Criteria andSqlIdEqualTo(String value) {
            addCriterion("sqlId =", value, "sqlId");
            return (Criteria) this;
        }

        public Criteria andSqlIdNotEqualTo(String value) {
            addCriterion("sqlId <>", value, "sqlId");
            return (Criteria) this;
        }

        public Criteria andSqlIdGreaterThan(String value) {
            addCriterion("sqlId >", value, "sqlId");
            return (Criteria) this;
        }

        public Criteria andSqlIdGreaterThanOrEqualTo(String value) {
            addCriterion("sqlId >=", value, "sqlId");
            return (Criteria) this;
        }

        public Criteria andSqlIdLessThan(String value) {
            addCriterion("sqlId <", value, "sqlId");
            return (Criteria) this;
        }

        public Criteria andSqlIdLessThanOrEqualTo(String value) {
            addCriterion("sqlId <=", value, "sqlId");
            return (Criteria) this;
        }

        public Criteria andSqlIdLike(String value) {
            addCriterion("sqlId like", value, "sqlId");
            return (Criteria) this;
        }

        public Criteria andSqlIdNotLike(String value) {
            addCriterion("sqlId not like", value, "sqlId");
            return (Criteria) this;
        }

        public Criteria andSqlIdIn(List<String> values) {
            addCriterion("sqlId in", values, "sqlId");
            return (Criteria) this;
        }

        public Criteria andSqlIdNotIn(List<String> values) {
            addCriterion("sqlId not in", values, "sqlId");
            return (Criteria) this;
        }

        public Criteria andSqlIdBetween(String value1, String value2) {
            addCriterion("sqlId between", value1, value2, "sqlId");
            return (Criteria) this;
        }

        public Criteria andSqlIdNotBetween(String value1, String value2) {
            addCriterion("sqlId not between", value1, value2, "sqlId");
            return (Criteria) this;
        }

        public Criteria andSqlSourceIsNull() {
            addCriterion("sqlSource is null");
            return (Criteria) this;
        }

        public Criteria andSqlSourceIsNotNull() {
            addCriterion("sqlSource is not null");
            return (Criteria) this;
        }

        public Criteria andSqlSourceEqualTo(String value) {
            addCriterion("sqlSource =", value, "sqlSource");
            return (Criteria) this;
        }

        public Criteria andSqlSourceNotEqualTo(String value) {
            addCriterion("sqlSource <>", value, "sqlSource");
            return (Criteria) this;
        }

        public Criteria andSqlSourceGreaterThan(String value) {
            addCriterion("sqlSource >", value, "sqlSource");
            return (Criteria) this;
        }

        public Criteria andSqlSourceGreaterThanOrEqualTo(String value) {
            addCriterion("sqlSource >=", value, "sqlSource");
            return (Criteria) this;
        }

        public Criteria andSqlSourceLessThan(String value) {
            addCriterion("sqlSource <", value, "sqlSource");
            return (Criteria) this;
        }

        public Criteria andSqlSourceLessThanOrEqualTo(String value) {
            addCriterion("sqlSource <=", value, "sqlSource");
            return (Criteria) this;
        }

        public Criteria andSqlSourceLike(String value) {
            addCriterion("sqlSource like", value, "sqlSource");
            return (Criteria) this;
        }

        public Criteria andSqlSourceNotLike(String value) {
            addCriterion("sqlSource not like", value, "sqlSource");
            return (Criteria) this;
        }

        public Criteria andSqlSourceIn(List<String> values) {
            addCriterion("sqlSource in", values, "sqlSource");
            return (Criteria) this;
        }

        public Criteria andSqlSourceNotIn(List<String> values) {
            addCriterion("sqlSource not in", values, "sqlSource");
            return (Criteria) this;
        }

        public Criteria andSqlSourceBetween(String value1, String value2) {
            addCriterion("sqlSource between", value1, value2, "sqlSource");
            return (Criteria) this;
        }

        public Criteria andSqlSourceNotBetween(String value1, String value2) {
            addCriterion("sqlSource not between", value1, value2, "sqlSource");
            return (Criteria) this;
        }

        public Criteria andSqlParameterIsNull() {
            addCriterion("sqlParameter is null");
            return (Criteria) this;
        }

        public Criteria andSqlParameterIsNotNull() {
            addCriterion("sqlParameter is not null");
            return (Criteria) this;
        }

        public Criteria andSqlParameterEqualTo(String value) {
            addCriterion("sqlParameter =", value, "sqlParameter");
            return (Criteria) this;
        }

        public Criteria andSqlParameterNotEqualTo(String value) {
            addCriterion("sqlParameter <>", value, "sqlParameter");
            return (Criteria) this;
        }

        public Criteria andSqlParameterGreaterThan(String value) {
            addCriterion("sqlParameter >", value, "sqlParameter");
            return (Criteria) this;
        }

        public Criteria andSqlParameterGreaterThanOrEqualTo(String value) {
            addCriterion("sqlParameter >=", value, "sqlParameter");
            return (Criteria) this;
        }

        public Criteria andSqlParameterLessThan(String value) {
            addCriterion("sqlParameter <", value, "sqlParameter");
            return (Criteria) this;
        }

        public Criteria andSqlParameterLessThanOrEqualTo(String value) {
            addCriterion("sqlParameter <=", value, "sqlParameter");
            return (Criteria) this;
        }

        public Criteria andSqlParameterLike(String value) {
            addCriterion("sqlParameter like", value, "sqlParameter");
            return (Criteria) this;
        }

        public Criteria andSqlParameterNotLike(String value) {
            addCriterion("sqlParameter not like", value, "sqlParameter");
            return (Criteria) this;
        }

        public Criteria andSqlParameterIn(List<String> values) {
            addCriterion("sqlParameter in", values, "sqlParameter");
            return (Criteria) this;
        }

        public Criteria andSqlParameterNotIn(List<String> values) {
            addCriterion("sqlParameter not in", values, "sqlParameter");
            return (Criteria) this;
        }

        public Criteria andSqlParameterBetween(String value1, String value2) {
            addCriterion("sqlParameter between", value1, value2, "sqlParameter");
            return (Criteria) this;
        }

        public Criteria andSqlParameterNotBetween(String value1, String value2) {
            addCriterion("sqlParameter not between", value1, value2, "sqlParameter");
            return (Criteria) this;
        }

        public Criteria andSqlTypeIsNull() {
            addCriterion("sqlType is null");
            return (Criteria) this;
        }

        public Criteria andSqlTypeIsNotNull() {
            addCriterion("sqlType is not null");
            return (Criteria) this;
        }

        public Criteria andSqlTypeEqualTo(String value) {
            addCriterion("sqlType =", value, "sqlType");
            return (Criteria) this;
        }

        public Criteria andSqlTypeNotEqualTo(String value) {
            addCriterion("sqlType <>", value, "sqlType");
            return (Criteria) this;
        }

        public Criteria andSqlTypeGreaterThan(String value) {
            addCriterion("sqlType >", value, "sqlType");
            return (Criteria) this;
        }

        public Criteria andSqlTypeGreaterThanOrEqualTo(String value) {
            addCriterion("sqlType >=", value, "sqlType");
            return (Criteria) this;
        }

        public Criteria andSqlTypeLessThan(String value) {
            addCriterion("sqlType <", value, "sqlType");
            return (Criteria) this;
        }

        public Criteria andSqlTypeLessThanOrEqualTo(String value) {
            addCriterion("sqlType <=", value, "sqlType");
            return (Criteria) this;
        }

        public Criteria andSqlTypeLike(String value) {
            addCriterion("sqlType like", value, "sqlType");
            return (Criteria) this;
        }

        public Criteria andSqlTypeNotLike(String value) {
            addCriterion("sqlType not like", value, "sqlType");
            return (Criteria) this;
        }

        public Criteria andSqlTypeIn(List<String> values) {
            addCriterion("sqlType in", values, "sqlType");
            return (Criteria) this;
        }

        public Criteria andSqlTypeNotIn(List<String> values) {
            addCriterion("sqlType not in", values, "sqlType");
            return (Criteria) this;
        }

        public Criteria andSqlTypeBetween(String value1, String value2) {
            addCriterion("sqlType between", value1, value2, "sqlType");
            return (Criteria) this;
        }

        public Criteria andSqlTypeNotBetween(String value1, String value2) {
            addCriterion("sqlType not between", value1, value2, "sqlType");
            return (Criteria) this;
        }

        public Criteria andExceptionMessageIsNull() {
            addCriterion("exceptionMessage is null");
            return (Criteria) this;
        }

        public Criteria andExceptionMessageIsNotNull() {
            addCriterion("exceptionMessage is not null");
            return (Criteria) this;
        }

        public Criteria andExceptionMessageEqualTo(String value) {
            addCriterion("exceptionMessage =", value, "exceptionMessage");
            return (Criteria) this;
        }

        public Criteria andExceptionMessageNotEqualTo(String value) {
            addCriterion("exceptionMessage <>", value, "exceptionMessage");
            return (Criteria) this;
        }

        public Criteria andExceptionMessageGreaterThan(String value) {
            addCriterion("exceptionMessage >", value, "exceptionMessage");
            return (Criteria) this;
        }

        public Criteria andExceptionMessageGreaterThanOrEqualTo(String value) {
            addCriterion("exceptionMessage >=", value, "exceptionMessage");
            return (Criteria) this;
        }

        public Criteria andExceptionMessageLessThan(String value) {
            addCriterion("exceptionMessage <", value, "exceptionMessage");
            return (Criteria) this;
        }

        public Criteria andExceptionMessageLessThanOrEqualTo(String value) {
            addCriterion("exceptionMessage <=", value, "exceptionMessage");
            return (Criteria) this;
        }

        public Criteria andExceptionMessageLike(String value) {
            addCriterion("exceptionMessage like", value, "exceptionMessage");
            return (Criteria) this;
        }

        public Criteria andExceptionMessageNotLike(String value) {
            addCriterion("exceptionMessage not like", value, "exceptionMessage");
            return (Criteria) this;
        }

        public Criteria andExceptionMessageIn(List<String> values) {
            addCriterion("exceptionMessage in", values, "exceptionMessage");
            return (Criteria) this;
        }

        public Criteria andExceptionMessageNotIn(List<String> values) {
            addCriterion("exceptionMessage not in", values, "exceptionMessage");
            return (Criteria) this;
        }

        public Criteria andExceptionMessageBetween(String value1, String value2) {
            addCriterion("exceptionMessage between", value1, value2, "exceptionMessage");
            return (Criteria) this;
        }

        public Criteria andExceptionMessageNotBetween(String value1, String value2) {
            addCriterion("exceptionMessage not between", value1, value2, "exceptionMessage");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("createTime is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("createTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("createTime =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("createTime <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("createTime >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createTime >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("createTime <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("createTime <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("createTime in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("createTime not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("createTime between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("createTime not between", value1, value2, "createTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}