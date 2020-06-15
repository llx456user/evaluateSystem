<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/index/includeZui.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>评估系统-模板标签查看</title>
    <%--<script type="text/javascript" src="<c:url value='/resources/js/project/bindWordTemplateList.js'/>"></script>--%>
    <style>
        .active {
            border: 1px solid red;
        }

        .trash {
            color: #3280fc;
            margin-left: 10px;
        }

        .trash:hover {
            cursor: pointer;
            color: #1970fc;
        }
    </style>
</head>
<body>
<div class="container">

    <!-- 新建文件 -->
    <div class="panel" style="margin-bottom: 5px;">
        <div class="panel-heading">
            <strong>项目标签</strong>
        </div>

        <div class="form-group">
            <table class="table table-borderless">
                <thead>
                <tr>
                    <th>标签名称</th>
                    <th>标签描述</th>
                    <th>标签类型</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${normalList }" var="normal">
                    <tr>
                        <td>
                            <input type="text" class="form-control"
                                   name="labelName"
                                   value="${normal.label }"
                                   readonly="readonly">
                        </td>
                        <td>
                            <input type="text" class="form-control"
                                   name="labelDesc"
                                   value="${normal.description }"
                                   readonly="readonly">
                        </td>
                        <td>
                            <input type="text" class="form-control"
                                   name="labelType"
                                   value="${normal.typeName }"
                                   readonly="readonly">
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>


        <div class="panel-body">
            <%--<form id="fileEditForm" method="post">--%>
            <nav class="menu" data-ride="menu">
                <ul id="treeMenu" class="tree tree-menu" data-ride="tree">
                    <c:forEach items="${projectLabelList }" var="pa" varStatus="paStatus">
                        <c:if test="${paStatus.count == 1}">
                            <div style="width: 100%;">
                                <label style="font-size: ${17}">
                                        ${pa.projectLabelList.get(0).rootName}
                                </label>
                            </div>
                        </c:if>
                        <c:forEach items="${pa.projectLabelList }" var="label" varStatus="pStatus">
                            <c:if test="${label.parentNameList.size() > 0}">
                                <c:forEach items="${label.parentNameList }" var="n" varStatus="status">
                                    <div style="width: 100%;padding-left: ${20*(status.index + 1)}">
                                        <c:choose>
                                            <c:when test="${pStatus.index == 0}">
                                                <label style="font-size: ${17-status.count}">
                                                        ${n}
                                                </label>
                                            </c:when>
                                            <c:otherwise>
                                                <c:if test="${label.parentNameList.size() != pa.projectLabelList.get(pStatus.index-1).parentNameList.size()}">
                                                    <c:set var="flag" value="0"></c:set>
                                                    <c:forEach
                                                            items="${pa.projectLabelList.get(pStatus.index-1).parentNameList }"
                                                            var="child" varStatus="childStatus">
                                                        <c:if test="${child == n}">
                                                            <c:set var="flag" value="1"></c:set>
                                                        </c:if>
                                                        <c:if test="${flag==0 && childStatus.count == pa.projectLabelList.get(pStatus.index-1).parentNameList.size()}">
                                                            <label style="font-size: ${17-status.count}">
                                                                    ${n}
                                                            </label>
                                                        </c:if>
                                                    </c:forEach>
                                                </c:if>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </c:forEach>
                            </c:if>
                            <li>
                                <a href="#" style="padding-left: ${20*(label.parentNameList.size() +1)}"><span
                                        style="font-size:${16-label.parentNameList.size()}">${label.nodeName }</span></a>
                                <ul style="padding-left: ${20*(label.parentNameList.size() + 2)}">
                                    <c:choose>
                                        <c:when test="${label.nodeIndexList.size() > 0}">
                                            <c:forEach items="${label.nodeIndexList }" var="indexLabel"
                                                       varStatus="status">
                                                <li>
                                                    <div class="form-group">
                                                        <table class="table table-borderless">
                                                            <thead>
                                                            <C:if test="${status.index == 0}">
                                                                <tr>
                                                                    <th>标签名称</th>
                                                                    <th>标签描述</th>
                                                                    <th>标签类型</th>
                                                                </tr>
                                                            </C:if>
                                                            </thead>
                                                            <tbody id="iptVariableTb">
                                                            <tr>
                                                                <td>
                                                                    <input type="text" class="form-control"
                                                                           name="labelName"
                                                                           value="${indexLabel.label }"
                                                                           readonly="readonly">
                                                                </td>
                                                                <td>
                                                                    <input type="text" class="form-control"
                                                                           name="labelDesc"
                                                                           value="${indexLabel.description }"
                                                                           readonly="readonly">
                                                                </td>
                                                                <td>
                                                                    <input type="text" class="form-control"
                                                                           name="labelType"
                                                                           value="${indexLabel.typeName }"
                                                                           readonly="readonly">
                                                                </td>
                                                            </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </c:when>
                                    </c:choose>
                                </ul>
                            </li>
                        </c:forEach>
                    </c:forEach>
                </ul>
            </nav>
            <button class="btn btn-default" type="button" onclick="back();">返回</button>
        </div>
    </div>
</div>
<script type="text/javascript">

    function back() {

        window.history.go(-1);
    }
</script>
</body>
</html>