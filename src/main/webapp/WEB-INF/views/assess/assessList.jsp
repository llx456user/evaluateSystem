<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/index/includeZui.jsp" %>
<html>
<head>
    <title>评估系统-评估管理查询</title>
    <script type="text/javascript" src="<c:url value='/resources/js/assess/assessList.js'/>"></script>
    <style>
        .active {
            border: 1px solid red;
        }
    </style>
</head>
<body>
<div class="container">
    <!-- 左侧分类 -->
    <div class="panel" style="float: left;width:20%;">
        <%--<div class="panel-heading">--%>
        <%--项目节点<button class="btn btn-primary" type="button" style="float: right;" onclick="repeatCategory('treeMenu');"><i class="icon-repeat"></i></button>--%>
        <%--<button class="btn btn-primary" type="button" style="float: right;" onclick="window.history.go(-1)"><i class="icon-reply"></i></button>--%>
        <%--</div>--%>
        <div class="panel-heading">
            项目节点
            <span style="float: right;">
	    	<!-- <a title="返回" style="cursor: pointer;" onclick="window.history.go(-1)"><i class="icon-reply"></i></a>
	    	&nbsp;
	    	<a onclick="repeatCategory('treeMenu');" title="刷新当前页面" style="cursor: pointer;"><i class="icon-repeat"></i></a> -->
	    	</span>
        </div>
        <input id="searchInputExample" autofocus="autofocus" type="search" class="form-control search-input"
               placeholder="搜索">
        <ul class="tree tree-lines tree-folders" id="treeMenu">
        </ul>
    </div>
    <!-- 表格数据 -->
    <div class="panel" style="float: right;width: 79%;">
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
                        <input type="text" class="form-control" id="parameterKey" placeholder="输入关键字">
                        <%--<span class="dropdown">--%>
                        <%--<a class="btn" type="button" data-toggle="dropdown">评估状态选择 <span class="caret"></span></a>--%>
                        <%--<ul class="dropdown-menu">--%>
                        <%--<li><a href="###" value="0">未评估</a></li>--%>
                        <%--<li><a href="###" value="2">评估成功</a></li>--%>
                        <%--<li><a href="###" value="3">评估失败</a></li>--%>
                        <%--</ul>--%>

                        <%--</span>--%>
                        <button type="button" class="btn btn-default" onclick="searchData();">搜索</button>
                        <button type="button" class="btn btn-primary" onclick="addAssess();"><i class="icon-plus"></i>
                            新建评估
                        <%--</button>--%>
                        <%--<button type="button" class="btn btn-primary" onclick="referenceTemplateAssess();"> 参考模板评估--%>
                        <%--</button>--%>
                    </div>
                </div>
            </form>
        </div>
        <table class="datatable table-bordered" id="gridPanel"></table>
        <!-- 分页 -->
        <div class="panel-footer yxdPageCode"></div>
    </div>
    <input type="hidden" id="projectId" value="${projectId }"/>
    <input type="hidden" id="projectName" value="${projectName }"/>
    <div style="clear: both;"></div>
</div>

</body>
</html>