<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
	<title>评估系统-项目查看</title>

	<style>
	.active{
		border:1px solid red;
	}
	.addBtn{
		color:#3280fc;
	}
	.addBtn:hover{
		cursor:pointer;
		color:#1970fc;
	}

	.trash{
		color:#3280fc;
		margin-left:10px;
	}
	.trash:hover{
		cursor:pointer;
		color:#1970fc;
	}
	</style>
</head>
<body>
	<div class="container">
		
		<!-- 新建文件 -->
        <div class="panel" style="margin-bottom: 5px;">
	        <div class="panel-heading">
			<strong>项目信息</strong>
		</div>
	    <div class="panel-body">
			<form id="fileEditForm" method="post">
			  <div class="form-group">
			    <label for="fileName">项目名称</label>
			    <input type="text" class="form-control" id="projectName" name="projectName" placeholder="" value="${bean.projectName }" readonly="readonly">
			  </div>
			  
			  <div class="form-group">
			    <label for="fileContent">项目内容</label>
			    <textarea class="form-control" id="projectContent" name="projectContent" placeholder="" readonly="readonly">${bean.projectContent }</textarea>
			  </div>
			
			  
			   
			 </form>
			 
			<button class="btn btn-default" type="button" onclick="back();">返回</button>
		</div>
	</div>
	<div class="panel">
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
				</div>
			</form>
		</div> 
		<table class="datatable table-bordered" id="gridPanel"></table>
		<!-- 分页 -->
		<div class="panel-footer yxdPageCode"></div>
		</div>
		<div style="clear: both;"></div>
	</div>
	
	<script type="text/javascript">

	function back(){
		
		window.history.go(-1);
	}
	$(document).ready(function() {
		
		
		$("#selectPageSize").change(function(){
			$('#gridPanel').Datagrid('options').other.pageSize = $(this).val();
			searchData();
		});
		loadData();
		
		$("#searchBtn").click (function() {
			searchData();
		});
		
	});
	function getParams(){
		return {
			field:$("#searchField").val()};
		
	}
	function searchData(){
		var params = getParams();
		$('#gridPanel').Datagrid('options').queryParams = params;
		$('#gridPanel').Datagrid('reload');
	}

	function loadData(){
		var options = {
				url:ctx+"/datasource/fileList",
//				queryParams:getParams(),
				other:{
					pageSize:10,
				    currentPage:1,
				    data: {
				        cols: [
	                    { field: 'id',hidden:"true"},
	                    {  title: '序号.', width: 30,formatter: function(value, row,index){
	                    	return index+1;
	                    }},
	                    { field: 'fileName', title: '指标名称', width: 110, halign:'center',align:'center' },
	                    { field: 'fileVersion', title: '更新时间', width: 100, halign:'center',align:'center' },
	                    { field: 'fileSize', title: '指标说明', width: 250, halign:'center',align:'center' },
	                    { field: 'field4', title: '操作', width: 60,formatter : function(value, row) { 
	                    	return getLink(value, row);}
	                    }
	                
	            ]}
				},
				tables:{
					checkable: false,
				    sortable: false,
				    //checkedClass: "checked",
				    colHover:true,
				    hoverClass:'hover',
				    minFixedLeftWidth: 300
				   
				},
				onLoadSuccess:function(data){
					if(data.rows.length == 0){
						$('.table-datatable tbody').html('<tr><td>没有相关数据</td></tr>');
					}
				}
		};
		$('#gridPanel').Datagrid('load',options);
	}

	function getLink(value, row){ 
		var evaluate         = "<a href='#'  class='easyui-linkbutton' onclick=toEvaluate('"+row.id+"');><i class='icon-search'></i>开始评估</a>";
		var result         = "<a href='#'  class='easyui-linkbutton' onclick=showResult('"+row.id+"');><i class='icon-edit'></i>结果</a>";
		
		return evaluate  + ' / ' + result;
		
	}

	function rowChangeColor(value,row){
		 
		 var show ="";
			 show = "<font color=red>"+value+"</font><br>" ;
		 return show;  
	}
	
	function toEvaluate(indexId){
		window.location.href = ctx + '/project/toProjectEvaluate?indexId='+indexId;
	}
	function showResult(indexId){
		var url = ctx + '/project/evaluateResult?indexId='+indexId;
		openDialog("评估结果","600","400",url,function(){
			searchData();
		})
	}
	</script>
</body>
</html>