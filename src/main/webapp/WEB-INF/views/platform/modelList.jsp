<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>
<html>
<head>
	<title>评估系统-模型管理查询</title>
	<script type="text/javascript" src="<c:url value='/resources/js/platform/modelList.js?version=${jsversion}'/>"></script>
	<style type="text/css">
		/* .tree-menu li.active{
            background: #3280fc;
        } */
		.tree-menu  li>a{
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
		}
	</style>
</head>
<body>
<div class="container">
	<!-- 左侧分类 -->
	<div class="panel" style="float: left;width:20%;">
		<div class="panel-heading">
			模型分类
			<!-- <button class="btn btn-primary" type="button" style="float: right;" onclick="repeatCategory('treeMenu');"><i class="icon-repeat"></i></button> -->
			<a  style="float: right;cursor: pointer;" onclick="addCategory();" id="addCategory" title="添加分类"><i class="icon-plus"></i></a>
		</div>
		<input id="searchInputExample" autofocus="autofocus" type="search" class="form-control search-input" placeholder="搜索">
		<input type='hidden' id="modelframeid" value='modelframeid' />
		<nav class="menu" data-ride="menu">
			<ul id="treeMenu" class="tree tree-menu" data-ride="tree">
				<c:forEach items="${mcList }" var="mc">
					<li cateid="${mc.id }">
						<a href="#" title="${mc.categoryName }" style="width:170px;height: 30px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;"><i class="icon icon-file-text"></i>${mc.categoryName }</a>
						<i class="icon icon-copy" title="参考创建" onclick="copyModel(${mc.id });" style="width:15px;cursor: pointer;"></i>
						<i class="icon icon-edit" title="编辑分类名称" onclick="editCategory(${mc.id });" style="width:15px;margin-left:15px;cursor: pointer;"></i>
						<i class="icon icon-trash" title="删除分类名称" onclick="deleteCategory(${mc.id });" style="width:15px;margin-left:5px;cursor: pointer;"></i>
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
						<input type="text" class="form-control" id="modelName" placeholder="模型名字">
						<input type="text" class="form-control" id="modelContent" placeholder="模型内容">
					</div>
					<button type="button" class="btn btn-default" onclick="search();">搜索</button>
					<button type="button" class="btn btn-primary" onclick="addOrEditModelInfo();"><i class="icon-plus"></i> 新增</button>
				</div>
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
					<input type="hidden"  id="categoryId">
					<div class="form-group">
						<label for="categoryName" class="col-sm-2">名称</label>
						<div class="col-md-6 col-sm-10">
							<input type="text" class="form-control" id="categoryName" placeholder="请输入模型分类名称">
						</div>
					</div>
				</form>
				</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" onclick="saveCategory();">保存</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>

			</div>
		</div>
	</div>
</div>

<!-- 上传dll文件 -->
<div class="modal fade" id="uploadDllPage">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
				<h4 class="modal-title">上传</h4>
			</div>
			<div class="modal-body">
				<p>
				<form class="form-inline" id="uploadForm" method="post" enctype="multipart/form-data">
					<input type="hidden"  id="modelInfoId">
					<div class="form-group">
						<label for="file">选择文件</label>
						<input type="file" class="form-control" id="file" name="file" placeholder="">
						<button class="btn btn-primary" type="button" onclick="upload();" >上传</button>
					</div>
				</form>
				</p>
			</div>

		</div>
	</div>
</div>

</body>
</html>