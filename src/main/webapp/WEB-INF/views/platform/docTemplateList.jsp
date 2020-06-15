<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/index/includeZui.jsp" %>
<html>
<head>
    <title>评估系统-Word模板管理查询</title>
    <script type="text/javascript" src="<c:url value='/resources/js/platform/docTemplateList.js'/>"></script>
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
        <div class="panel-heading">
            模板分类
            <span style="float: right;">
            <a id="copyCategory" style="cursor: pointer;" title="参考创建"><i class="icon-copy"></i></a>
	    	&nbsp;
	    	<a id="addCategory" style="cursor: pointer;" title="添加分类"><i class="icon-plus"></i></a>
	    	&nbsp;
	    	<a id="editCategory" style="cursor: pointer;" title="编辑分类"><i class="icon-edit"></i></a>
	    	&nbsp;
	    	<a id="delCategory" style="cursor: pointer;" title="删除分类"><i class="icon-trash"></i></a>
                <!-- &nbsp;
                <a onclick="repeatCategory('treeMenu');" style="cursor: pointer;"><i class="icon-repeat"></i></a> -->
	    	</span>
        </div>
        <input id="searchInputExample" autofocus="autofocus" type="search" class="form-control search-input" placeholder="搜索">
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
                        <input type="text" class="form-control" id="parameterKey" placeholder="模板名称">
                        <%--<input type="text" class="form-control" id="indexContent" placeholder="模板内容">--%>
                    </div>
                    <button type="button" class="btn btn-default" onclick="search();">搜索</button>
                    <button type="button" class="btn btn-primary" onclick="wordTemplateAdd();"><i class="icon-plus"></i> 上传模板
                    </button>
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
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                    <span class="sr-only">关闭</span>
                </button>
                <h4 class="modal-title">新建分类</h4>
            </div>
            <div class="modal-body">
                <p>

                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="categoryName" class="col-sm-2">名称</label>

                        <div class="col-md-6 col-sm-10">
                            <input type="text" class="form-control" id="categoryName" placeholder="请输入分类名称">
                            <input type="hidden" id="categoryId"/>
                            <input type="hidden" id="parentId"/>
                        </div>
                    </div>
                </form>
                </p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="saveCategoryButton">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>

            </div>
        </div>
    </div>
</div>
</body>
</html>