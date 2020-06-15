<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/index/includeZui.jsp" %>
<html>
<head>
    <title>评估系统-Word模板管理查询</title>
    <script type="text/javascript" src="<c:url value='/resources/js/project/dataGroupTemplateList.js'/>"></script>
    <style>
        .active {
            border: 1px solid red;
        }
    </style>
</head>
<body>
<div class="container">
    <!-- 表格数据 -->
    <div class="panel" style="float: right;width: 100%;">
        <div class="panel-heading">
            <label>${projectName}模板</label>
        </div>
        <div class="panel-heading">
            <form role="form" class="form-inline">
				<span style="margin-left:20px;" class="form-group">
					显示
					<select class="form-control" id="selectPageSize">
                        <option value="10">10</option>
                        <option value="20">20</option>
                        <option value="50">50</option>
                        <option value="100">100</option>
                    </select>
					项结果
				</span>

                <div style="float:right;">
                    <div class="form-group">
                        <input type="text" class="form-control" id="parameterKey" placeholder="数据名称">
                        <input type="hidden" id="projectId" value="${projectId }"/>
                        <input type="hidden" id="projectName" value="${projectName}">
                        <input type="hidden" id="showChooseFlag" value="${showChooseFlag }"/>
                        <input type="hidden" id="groupDataTemplateNumber" value="${groupDataTemplateNumber}">
                        <input type="hidden" name="templateId" id="templateId"/>
                        <input type="hidden" id="assessId" />
                    </div>
                    <button type="button" class="btn btn-default" onclick="search();">搜索</button>
                    <button type="button" class="btn btn-primary" onclick="setGroupProjectTemplateValue();"><i class="icon-plus"></i>新建数据</button>
                </div>
            </form>
        </div>
        <table class="datatable table-bordered" id="gridPanel"></table>
        <!-- 分页 -->
        <div class="panel-footer yxdPageCode"></div>
    </div>
    <div style="clear: both;"></div>
</div>
</body>
</html>