<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/index/includeZui.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>评估系统-评估管理开始评估</title>
    <script type="text/javascript"
            src="<c:url value='/resources/js/project/addProjectTemplateParam.js?version=${jsversion}'/>"></script>
    <style>
        .active {
            border: 1px solid red;
        }

        .indexBtnImg {
            border: 1px solid #D2F2EA;
            border-radius: 5px;
            background-repeat: no-repeat;
            width: 90px;
            height: 90px;
            margin: 5px 0 0 5px;
            background-color: #D2F2EA !important;
        }

        .indexBtnImg:hover {
            border-color: gray;
            cursor: pointer;
            background-color: #efefef !important;
        }

        .indexBtnImg strong {
            display: block;
            font-weight: bold;
            color: #343b42;
            padding-top: 55px;
        }

        #datasetBtn {
            background: url('${ctx}/resources/images/btn-database1.png') no-repeat center top 10px;
        }

        #modelBtn {
            background: url('${ctx}/resources/images/btn-module1.png') no-repeat center top 10px;
        }

        #chartBtn {
            background: url('${ctx}/resources/images/btn-chart1.png') no-repeat center top 10px;
        }

        #gridBtn {
            background: url('${ctx}/resources/images/grid1.png') no-repeat center top 10px;
        }

        #indexBtn {
            background: url('${ctx}/resources/images/indexTemp1.png') no-repeat center top 10px;
        }

        .boxF, .boxS, .boxT, .overlay {
            width: 50px;
            height: 75px;
            overflow: hidden;
        }

        .boxF, .boxS {
            visibility: hidden;
        }

        .boxF {
            transform: rotate(120deg);
            float: left;
            margin-left: 10px;
            -ms-transform: rotate(120deg);
            -moz-transform: rotate(120deg);
            -webkit-transform: rotate(120deg);
        }

        .boxS {
            transform: rotate(-60deg);
            -ms-transform: rotate(-60deg);
            -moz-transform: rotate(-60deg);
            -webkit-transform: rotate(-60deg);
        }

        .boxT {
            transform: rotate(-60deg);
            background: no-repeat 50% center;
            background-size: 125% auto;
            -ms-transform: rotate(-60deg);
            -moz-transform: rotate(-60deg);
            -webkit-transform: rotate(-60deg);
            visibility: visible;
            background-color: #73797D;
            padding-top: 30px;
            color: #fff;
        }

        #contants {
            padding: 3px 0px 8px 8px;
        }

        .boxY {
            transform: rotate(-45deg);
            width: 45px;
            height: 45px;
            -ms-transform: rotate(-45deg);
            -moz-transform: rotate(-45deg);
            -webkit-transform: rotate(-45deg);
            visibility: visible;
            background-color: #73797D;
            padding-top: 10px;
            color: #fff;
        }

        .boxZ {
            transform: rotate(45deg);
            -ms-transform: rotate(45deg);
            -moz-transform: rotate(45deg);
            -webkit-transform: rotate(45deg);
        }

        #if {
            padding: 20px 0px 0px 20px;
        }

        .dropdown-menu {
            text-align: left;
        }

        .showGrid {
            width: 90px;
            height: 90px;
            background: #ddd;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<div class="container">
    <!-- 新建评估 -->
    <div class="panel" style="margin-bottom: 5px;">
        <div class="panel-heading">
            <strong>评估信息</strong>
        </div>
        <div class="panel-body">

            <c:forEach items="${paramList }" var="p">
                <div class="form-group">
                    <label for="assessName">---<span style="color: red">${p.indexName }</span>---</label>
                </div>
                <form class="form">
                    <c:choose>
                        <c:when test="${p.cParamList.size() > 0}">
                            <c:forEach items="${p.cParamList }" var="c">
                                <div class="form-group">
                                    <label for="projectParam">常量:${c.paramName}</label>
                                    <select name="projectParam" class="form-control" key="${c.nodeKey }_${c.paramName}_${c.nodeId}_${c.indexId} ">
                                        <option value="">-请选择-</option>
                                        <c:forEach items="${inparList }" var="in" varStatus="stat">
                                            <option value="${in.paramName}"
                                                    <c:if test="${c.projectParamValue == in.paramValue}">selected</c:if>>${in.paramValue}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </c:forEach>
                        </c:when>
                        <%--<c:otherwise>--%>
                            <%--<div class="form-group">--%>
                                <%--<label for="assessParam">常量</label>--%>
                                <%--<select name="projectParam" class="form-control">--%>
                                    <%--<option value="">-请选择-</option>--%>
                                    <%--<c:forEach items="${inparList }" var="in" varStatus="stat">--%>
                                        <%--<option value="${in.paramValue}">${in.paramName}</option>--%>
                                    <%--</c:forEach>--%>
                                <%--</select>--%>
                            <%--</div>--%>
                        <%--</c:otherwise>--%>
                    </c:choose>
                </form>
                <c:choose>
                    <c:when test="${p.fParamList.size() > 0}">
                        <c:forEach items="${p.fParamList }" var="f">
                            <div class="form-group">
                                <label for="projectParam">附件:${f.paramName}</label>
                                <select name="projectParam" class="form-control" key="${f.nodeKey }_${f.paramName}_${f.nodeId}_${f.indexId} ">
                                    <option value="">-请选择-</option>
                                    <c:forEach items="${outparList }" var="out" varStatus="stat">
                                        <option value="${out.paramValue}"
                                                <c:if test="${f.projectParamValue == out.paramValue}">selected</c:if>>${out.paramName}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </c:forEach>
                    </c:when>
                    <%--<c:otherwise>--%>
                        <%--<div class="form-group">--%>
                            <%--<label for="fileshow_${f.id }">附件</label>--%>
                            <%--<select name="projectParam" class="form-control">--%>
                                <%--<option value="">-请选择-</option>--%>
                                <%--<c:forEach items="${outparList }" var="out" varStatus="stat">--%>
                                    <%--<option value="${out.paramValue}">${out.paramName}--%>
                                    <%--</option>--%>
                                <%--</c:forEach>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</c:otherwise>--%>
                </c:choose>

            </c:forEach>

            <input type="hidden" id="projectId" value="${projectId }"/>
            <input type="hidden" id="indexId" value="${indexId }"/>
            <%--<input type="hidden" id="nodeId" value="${nodeId }"/>--%>
            <%--<input type="hidden" id="assessId" value="${id }"/>--%>

            <button class="btn btn-primary" type="button" onclick="saveProjectParamTemplate();">保存</button>
            <button class="btn btn-primary" type="button" onclick="back();">返回</button>
        </div>
    </div>

</div>
</body>
</html>