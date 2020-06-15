<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/index/includeZui.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>评估系统-评估管理开始评估</title>
    <script type="text/javascript"
            src="<c:url value='/resources/js/assess/assessStart.js?version=${jsversion}'/>"></script>
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
            <div class="form-group">
                <div style="padding-bottom: 20px;float: right;">
                    <button class="btn btn-primary" type="button" onclick="paramUpload();">加载数据</button>
                    <button class="btn btn-primary" type="button" onclick="paramGroupUpload();">加载分组数据</button>
                </div>
                <div class="form-group">
                    <label for="assessName">评估名称（必填）</label>
                    <input type="text" class="form-control" id="assessName" placeholder="" value="${info.assessName }">
                </div>
                <div class="form-group">
                    <label for="assessContent">评估描述</label>
                    <textarea class="form-control" id="assessContent" placeholder="">${info.assessContent }</textarea>
                </div>
            </div>
        </div>
        <div class="panel-body">
            <nav class="menu" data-ride="menu">
                <ul id="treeMenu" class="tree tree-menu" data-ride="tree">
                    <li><a href="#"><i class="icon icon-th"></i>${projectName}项目指标</a></li>
                    <c:forEach items="${paramList }" var="pa" varStatus="paStatus">
                        <c:if test="${paStatus.index == 0}">
                            <div style="width: 100%;">
                                <label style="font-size: ${17}">
                                        ${pa.projectParamBeans.get(0).rootName}
                                </label>
                            </div>
                        </c:if>
                        <c:forEach items="${pa.projectParamBeans }" var="p" varStatus="pStatus">
                            <%--<c:if test="${pStatus.index == 0}">--%>
                            <c:forEach items="${p.parentNameList }" var="n" varStatus="status">
                                <div style="width: 100%;padding-left: ${20*(status.index + 1)}">
                                    <c:choose>
                                        <c:when test="${pStatus.index == 0}">
                                            <label style="font-size: ${17-status.count}">
                                                    ${n}
                                            </label>
                                        </c:when>
                                        <c:otherwise>
                                            <%--<c:if test="${p.parentNameList.size() != pa.projectParamBeans.get(pStatus.index-1).parentNameList.size()}">--%>
                                                <c:set var="flag" value="0"></c:set>
                                                <c:forEach
                                                        items="${pa.projectParamBeans.get(pStatus.index-1).parentNameList }"
                                                        var="child" varStatus="childStatus">
                                                    <c:if test="${child == n}">
                                                        <c:set var="flag" value="1"></c:set>
                                                    </c:if>
                                                    <c:if test="${flag==0 && childStatus.count == pa.projectParamBeans.get(pStatus.index-1).parentNameList.size()}">
                                                        <label style="font-size: ${17-status.count}">
                                                                ${n}
                                                        </label>
                                                    </c:if>
                                                </c:forEach>
                                            <%--</c:if>--%>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </c:forEach>
                            <li  class="open">
                                <a href="#" style="padding-left: ${20*(p.parentNameList.size() + 1)}"><span style="font-size:${16-p.parentNameList.size()};">${p.indexName }</span></a>
                                <ul style="padding-left: ${20*(p.parentNameList.size() + 2)}">
                                    <form class="form">
                                        <c:forEach items="${p.cParamList }" var="c">

                                            <div class="form-group">
                                                <label for="assessParam">${c.paramName }</label>
                                                <input type="text" class="form-control" key="${c.id }"
                                                       name="assessParam"
                                                       placeholder="" value="${c.paramValue }">

                                            </div>

                                        </c:forEach>
                                    </form>
                                    <c:forEach items="${p.fParamList }" var="f">

                                        <label for="fileshow_${f.id }">${f.paramName }</label>
                                        <div name="myuploader" id='myuploader_${f.id}' class="uploader">
                                            <div class="uploader-message text-center">
                                                <div class="content"></div>
                                                <button type="button" class="close">×</button>
                                            </div>

                                            <div hidden="hidden" id="fileshow_${f.id }"
                                                 class="uploader-files file-list file-list-lg"
                                                 data-drag-placeholder=""></div>
                                                <%--<div  class="alert alert-primary-inverse"><input id="filepreview_${f.id }"/>-</div>--%>
                                            <div class="input-control has-icon-left has-icon-right">
                                                <input id="filepreview_${f.id }" type="text" class="form-control"
                                                       readonly="readonly" value="${f.projectParamName}">
                                            </div>
                                                <%--<input id="filepreview_${f.id }" class="alert alert-primary-inverse"/>--%>
                                            <div class="uploader-actions">
                                                <div class="uploader-status pull-right text-muted"></div>
                                                <button id="uploaderBtn_${f.id }" type="button"
                                                        class="btn btn-link uploader-btn-browse"
                                                        onfocus="zuiupload(${f.id })"
                                                        onclick="zuiupload(${f.id })"><i class="icon icon-plus"></i>
                                                    选择文件
                                                </button>
                                                    <%--onclick="zuiupload(${f.id })"--%>
                                                    <%--<button type="button" class="btn btn-link uploader-btn-start"><i class="icon icon-cloud-upload"></i> 开始上传</button>--%>
                                                <button class="btn btn-link" type="button"
                                                        onclick="selectFiles(${f.id });">
                                                    选择服务器文件
                                                </button>
                                            </div>
                                        </div>


                                        <input type="hidden" key="${f.id }" name="assessParam" id="filePath_${f.id }"
                                               value="${f.paramValue}"/>

                                    </c:forEach>
                                </ul>
                            </li>
                        </c:forEach>
                    </c:forEach>
                    <input type="hidden" id="projectId" value="${projectId }"/>
                    <input type="hidden" id="indexId" value="${indexId }"/>
                    <input type="hidden" id="nodeId" value="${nodeId }"/>
                    <input type="hidden" id="assessId" value="${id }"/>
                    <input type="hidden" id="projectName" value="${projectName}"/>

                    <button class="btn btn-primary" type="button" onclick="assess(1);">评估</button>
                    <%--<button class="btn btn-primary" type="button" onclick="paramUpload();">加载模板</button>--%>
                    <button class="btn btn-primary" type="button" onclick="back();">返回</button>
                </ul>
            </nav>
        </div>
    </div>

</div>
</body>
</html>