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
		
		
	<div class="panel">
		<table class="datatable table-bordered" id="gridPanel"></table>
		<!-- 分页 -->
		<div class="panel-footer yxdPageCode"></div>
		</div>
		<div style="clear: both;"></div>
	</div>
	
	<script type="text/javascript">


	$(document).ready(function() {
		loadData();
	});


	function loadData(){ 
		var options = {
				url:ctx+"/datasource/fileList",
				queryParams:{
					field:'${indexId}'},
				other:{
					pageSize:10,
				    currentPage:1,
				    data: {
				        cols: [
	                    { field: 'id',hidden:"true"},
	                    {  title: '序号.', width: 30,formatter: function(value, row,index){
	                    	return index+1;
	                    }},
	                    { field: 'fileName', title: '评估时间', width: 110, halign:'center',align:'center' },
	                    { field: 'fileVersion', title: '评估状态', width: 100, halign:'center',align:'center' },
	                    { field: 'fileSize', title: '评估说明', width: 250, halign:'center',align:'center' },
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
		var look         = "<a href='#'  class='easyui-linkbutton' onclick=look('"+row.id+"');><i class='icon-search'></i>评估</a>";
		return look;
		
	}

	
	function look(id){
		//TODO 评估查看
	}
	
	</script>
</body>
</html>