<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/index/includeZui.jsp" %>
<html>
<head>
    <title>评估系统-评估管理查询</title>
    <script type="text/javascript" src="<c:url value='/resources/js/project/assessListTest.js'/>"></script>
    <style>
        .active {
            border: 1px solid red;
        }
        .labelStyle{
            /*padding-left: 10px;*/
            padding-top: 5px;
        }
        .defineSelfLabelStyle{
            padding-left: 10px;
            padding-top: 5px;
        }
        .spanStyle{
            color: red;
        }
    </style>
</head>
<body>
<div class="container">
    <!-- 左侧分类 -->
    <div class="panel" style="float: left;width:100%;">
        <%--<ul class="tree tree-lines tree-folders" id="treeMenu"></ul>--%>
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
                                            <%--<C:if test="${p.parentNameList.size() != pa.projectParamBeans.get(pStatus.index-1).parentNameList.size()}">--%>
                                                <c:set var="flag" value="0"></c:set>
                                                <c:forEach items="${pa.projectParamBeans.get(pStatus.index-1).parentNameList }" var="child" varStatus="childStatus">
                                                    <C:if test="${child == n}">
                                                        <c:set var="flag" value="1"></c:set>
                                                    </C:if>
                                                    <C:if test="${flag==0 && childStatus.count == pa.projectParamBeans.get(pStatus.index-1).parentNameList.size()}">
                                                        <label style="font-size: ${17-status.count}">
                                                                ${n}
                                                        </label>
                                                    </C:if>
                                                </c:forEach>
                                            <%--</C:if>--%>
                                        </c:otherwise>
                                    </c:choose>

                                </div>
                            </c:forEach>
                        <%--</c:if>--%>
                        <li id="${p.cParamList.size()>0?p.cParamList.get(0).nodeId:p.fParamList.size()>0?p.fParamList.get(0).nodeId:""}">
                            <a href="#" style="padding-left: ${20*(p.parentNameList.size() +1)}"><span id="${projectId}_${p.cParamList.size()>0?p.cParamList.get(0).nodeId:p.fParamList.size()>0?p.fParamList.get(0).nodeId:""}"
                                              style="font-size:${16-p.parentNameList.size()};color: ${p.isFinishFlg == 1?"green":"red"};">${p.indexName }</span></a>
                            <ul style="padding-left: ${20*(p.parentNameList.size() + 2)}">
                                <c:choose>
                                    <c:when test="${p.cParamList.size() > 0}">
                                        <c:forEach items="${p.cParamList }" var="c">
                                            <li>
                                                <div class="form-group">
                                                    <label for="projectParam" class="labelStyle">数据项:${c.paramName}</label>
                                                    <label class="defineSelfLabelStyle">
                                                        <input type="checkbox" name="projectParam_${p.indexId}_${c.nodeId}"
                                                            ${c.isSelfDefined == 1?"checked":""}
                                                               onchange="showInputOrUpload(${c.id + c.nodeId + p.randomNumber})">自定义
                                                    </label>
                                                    <select name="projectParam_${p.indexId}_${c.nodeId}" class="form-control" id="projectParam_${c.id + c.nodeId + p.randomNumber}"
                                                        <%--disabled = "${c.isSelfDefined == 1?true:false}"--%>
                                                        ${c.isSelfDefined == 1?"disabled":""}
                                                            key="${c.nodeKey }_${c.paramName}_${c.nodeId}_${c.indexId}_constrant ">
                                                        <option value="">-请选择-</option>
                                                        <c:forEach items="${inparList }" var="in"
                                                                   varStatus="stat">
                                                            <option value="${in.id}"
                                                                    <c:if test="${c.projectParamId == in.id}">selected</c:if>>${in.paramName}</option>
                                                        </c:forEach>
                                                    </select>
                                                    </select>
                                                    <div id="inputDiv_${c.id + c.nodeId + p.randomNumber}" style="display:${c.isSelfDefined ==1?"block":"none"}; width: 100%" class="uploader">
                                                        <input style="width: 100%" key="${c.nodeKey }_${c.paramName}_${c.nodeId}_${c.indexId}_constrant "
                                                               onkeyup="value=value.replace(/[^\-?\d.]/g,'')"
                                                               name="projectParam_${p.indexId}_${c.nodeId}" value="${c.constValueOrFileName}">
                                                    </div>
                                                </div>
                                            </li>
                                        </c:forEach>
                                    </c:when>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${p.fParamList.size() > 0}">
                                        <c:forEach items="${p.fParamList }" var="f">
                                            <li>
                                                <div class="form-group">
                                                    <label for="projectParam" class="labelStyle">数据源:${f.paramName}</label>
                                                    <label class="defineSelfLabelStyle">
                                                        <input type="checkbox" name="projectParam_${p.indexId}_${f.nodeId}"
                                                            ${f.isSelfDefined == 1?"checked":""}
                                                               onchange="showInputOrUpload(${f.id+f.nodeId})"> 自定义
                                                    </label>
                                                    <select name="projectParam_${p.indexId}_${f.nodeId}" class="form-control" id="projectParam_${f.id+f.nodeId}"
                                                        ${f.isSelfDefined == 1?"disabled":""}
                                                            key="${f.nodeKey }_${f.paramName}_${f.nodeId}_${f.indexId}_file ">
                                                        <option value="">-请选择-</option>
                                                        <c:forEach items="${outparList }" var="out"
                                                                   varStatus="stat">
                                                            <option value="${out.id}" <c:if test="${f.projectParamId == out.id}">selected</c:if>>${out.paramName}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                        <%--自定义选择文件--%>
                                                    <div  name="myuploader" id='inputDiv_${f.id+f.nodeId}' class="uploader" style="display:${f.isSelfDefined ==1?"block":"none"}">
                                                        <div hidden="hidden" id="fileshow_${f.id }" class="uploader-files file-list file-list-lg" data-drag-placeholder=""></div>
                                                        <div class="input-control has-icon-left has-icon-right">
                                                            <input id="filepreview_${f.id+f.nodeId }" type="text" class="form-control" readonly="readonly" value="${f.constValueOrFileName}">
                                                        </div>
                                                        <div class="uploader-actions">
                                                            <div class="uploader-status pull-right text-muted"></div>
                                                            <button  id="uploaderBtn_${f.id+f.nodeId }" type="button"  class="btn btn-link uploader-btn-browse" onfocus="zuiupload(${f.id+f.nodeId})" onclick="zuiupload(${f.id+f.nodeId })"><i class="icon icon-plus"></i> 选择文件</button>
                                                            <button class="btn btn-link" type="button"  onclick="selectFiles(${f.id+f.nodeId });" >选择服务器文件</button>
                                                        </div>
                                                        <input type="hidden" key="${f.nodeKey }_${f.paramName}_${f.nodeId}_${f.indexId}_file " name="projectParam_${p.indexId}_${f.nodeId}" id="filePath_${f.id+f.nodeId }" value="${f.filePath}">
                                                    </div>
                                                        <%--自定义选择文件--%>
                                                </div>
                                            </li>
                                        </c:forEach>
                                    </c:when>
                                </c:choose>
                            </ul>
                                <%--<button class="btn btn-default" type="button" onclick="submitProjectParamTemplate(${p.fParamList.size()>0?p.fParamList.get(0).nodeId:p.cParamList.size()>0?p.cParamList.get(0).nodeId:""} );">保存</button>--%>
                            <input type="hidden" id="projectParamIndexId_${p.fParamList.size()>0?p.fParamList.get(0).nodeId:p.cParamList.size()>0?p.cParamList.get(0).nodeId:""}"
                                   value="${p.fParamList.size()>0?p.fParamList.get(0).indexId:p.cParamList.size()>0?p.cParamList.get(0).indexId:""}">
                        </li>
                    </c:forEach>

                </c:forEach>
                <%--</li>--%>
            </ul>
        </nav>
    </div>

    <input type="hidden" id="projectId" value="${projectId }"/>
    <input type="hidden" id="isGroupTemplate" value="${isGroupTemplate }"/>
    <button class="btn btn-primary" type="button" onclick="saveAllProjectParamTemplate();">保存</button>
    <button class="btn btn-primary" type="button" onclick="back();">返回</button>
</div>

</body>
</html>