<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>
<html>
<head>
	<title>评估系统-文件管理查询</title>
	<script type="text/javascript" src="<c:url value='/resources/js/datasource/fileList2.js?version=${jsversion}'/>"></script>
	<style type="text/css">
	
	</style>
</head>
<body>
	<div class="container">
	<!-- 左侧分类 -->
	<div class="panel" style="float: left;width:20%;">
	  <div class="panel-heading">
	    	文件分类 
	    	<button class="btn btn-primary" type="button" style="float: right;" onclick="repeatCategory('treeMenu');"><i class="icon-repeat"></i></button>
	    	<button class="btn btn-primary" data-toggle="modal" data-target="#addCategoryPage" type="button" style="float: right;display: none;" id="addCategory"><i class="icon-plus"></i></button>
	  </div>
	 	<input id="searchInputExample" autofocus="autofocus" type="search" class="form-control search-input" placeholder="搜索">
		<nav class="menu" data-ride="menu">
		  <ul id="treeMenu" class="tree tree-menu" data-ride="tree">
		   	<li>
		      <a href="#"><i class="icon icon-file-o"></i>自定义分类</a>
		      <ul>
		      	<c:forEach items="${categories }" var="e">
		        <li cateid="${e.id }"><a href="#" style="width:100%;"><i class="icon icon-file-text"></i>${e.categoryName }</a>

		        <i class="icon icon-edit" onclick="editCategory(this);" style="width:20px;cursor: pointer;display: none;"></i>
		        <i class="icon icon-trash" onclick="delCategory(this);" style="width:20px;margin-left:5px;cursor: pointer;display: none;"></i>

		        </li>
		      	</c:forEach>
		      </ul>
		    </li>
		  </ul>
		</nav>
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
					    <input type="text" class="form-control" id="searchField" placeholder="输入关键字">
					  </div>
					  <button type="button" class="btn btn-default" onclick="search();">搜索</button>
					  <button type="button" class="btn btn-primary" onclick="select();"><i class="icon-plus"></i> 选择文件</button>
				</div>
				<input type='hidden' id="pId" value='${pId }' />
				<input type='hidden' id="frameid" value='${frameid }' />
				
			</form>
		</div> 
		<table class="datatable table-bordered" id="gridPanel"></table>
		<!-- 分页 -->
		<div class="panel-footer yxdPageCode"></div>
		</div>
		<div style="clear: both;"></div>
	</div>
	
	<div class="modal fade" id="addCategoryPage">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
	        <h4 class="modal-title">新建分类</h4>
	      </div>
	      <div class="modal-body">
	        <p>
	        <form class="form-horizontal">
			  <div class="form-group">
			    <label for="categoryName" class="col-sm-2">名称</label>
			    <div class="col-md-6 col-sm-10">
			      <input type="text" class="form-control" id="categoryName" placeholder="请输入上传文件分类名称">
			      <input type="hidden" name="categoryId" id="categoryId"/>
			    </div>
			  </div>
			</form>
	        </p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="button" class="btn btn-primary" onclick="saveCategory();">保存</button>
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>