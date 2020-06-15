<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>
<html>
<head>
	<title>DemoPro</title>
	<script type="text/javascript" src="<c:url value='/resources/js/index/welcome.js'/>"></script>
	<style type="text/css">
	.panel{width: 700px;}
	</style>
</head>
<body>
	<h2>Basic Project</h2>
	<div class="container">
	<div style="margin:20px 0;"></div>
	<div class="panel">
	        <div class="panel-heading">
			<strong>项目架构</strong>
			</div>
		    <div class="panel-body">
		    <p style="font-size:14px">基础项目架构说明</p>
			<ul>
				<li>MVC框架使用 SPRING MVC</li>
				<li>前台显示使用 JQuery和 ZUI</li>
				<li>持久化层使用 MYBATIS</li>
				<li>数据库使用 MYSQL</li>
				<li>项目构建管理使用 MAVEN</li>
			</ul>
		    </div>
		
	</div>
	
	<div class="panel">
		<div class="panel-heading">
		订单列表
		</div> 
		<div class="panel-body">
			<table class="datatable table-bordered" id="projectInfoTbl" style="width: 700px;"></table>
		</div>
		<!-- 分页 -->
		<div class="panel-footer yxdPageCode"></div>
		</div>
	</div>
	</div>
</body>
</html>