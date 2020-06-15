<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/index/includeZui.jsp" %>
<html>
<head>
    <title>评估系统-子指标管理查询</title>
    <script type="text/javascript" src="<c:url value='/resources/js/project/childIndexList.js'/>"></script>
    <style>
        .active {
            border: 1px solid red;
        }
    </style>
</head>
<body>
<div class="container">
    <!-- 表格数据 -->
    <div class="panel" style="float: right;width: 99%;">
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
                        <input type="text" class="form-control" id="parameterKey" placeholder="输入关键字搜索名称">
                    </div>
                    <button type="button" class="btn btn-default" onclick="searchIndexChild();">搜索</button>
                    <button type="button" class="btn btn-primary" onclick="selectIndexChild();"><i class="icon-plus"></i> 选择节点
                    </button>
                </div>
                <input type='hidden' id="frameid" value='${frameid }' />
                <input type='hidden' id="nodeId" value='${nodeId }' />
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
