<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>
<html>
<head>
	<title>评估系统-sql查询</title>
	<script type="text/javascript" src="<c:url value='/resources/js/datasource/sqlList.js?version=${jsversion}'/>"></script>
	<style type="text/css">
		/* .tree-menu li.active{
            background: #3280fc;
        } */
		/* .tree-menu  li>a{
            float: left;
        }

        .tree-menu li:not(.active):hover{
            background: #eee;
        }
        .tree-menu li a:hover{
            background: none;
        }
        .tree-menu  li > i{
            height:36px;
            display: inline-block;
            width: 20px;
            padding-top: 10px;
            color:#666;
        }

        .tree-menu>li>a{
            border: none;
        } */
	</style>
</head>
<body>
<div class="container">
	<!-- 左侧分类 -->
	<div class="panel" style="float: left;width:20%;">
		<div class="panel-heading">
			数据源
			<!-- <button class="btn btn-primary" type="button" style="float: right;" onclick="repeatCategory('treeMenu');"><i class="icon-repeat"></i></button> -->
		</div>
		<input id="searchInputExample" autofocus="autofocus" type="search" class="form-control search-input" placeholder="搜索">
		<nav class="menu" data-ride="menu">
			<ul id="treeMenu" class="tree tree-menu" data-ride="tree">
				<c:forEach items="${dbList }" var="db">
					<li cateid="${db.id }"><a href="#" style="width:270px;height: 35px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;" title="${db.sourceName }">
						<i class="icon icon-file-text"></i>${db.sourceName }</a>
					</li>
				</c:forEach>
			</ul>
		</nav>
	</div>

	<!-- 表格数据 -->
	<div class="panel" style="float: right;width: 79%;">

		<div class="panel-heading">
			<form id="dd" role="form" class="form-inline">
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
						<input type="text" class="form-control" id="sqlName" placeholder="sql名称">
						<input type="text" class="form-control" id="dbName" placeholder="数据库名称">
					</div>
					<button type="button" class="btn btn-default" onclick="search();">搜索</button>
					<button type="button" class="btn btn-primary" onclick="addOrEditSql();"><i class="icon-plus"></i> 新增</button>
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