<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
	<title>评估系统-评估信息</title>

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
					  <button type="button" class="btn btn-default" onclick="searchData();">搜索</button>
					  <button type="button" class="btn btn-primary" onclick="addIndex();"><i class="icon-plus"></i> 新建评估</button>
				</div>
			</form>
		</div> 
		<table class="datatable table-bordered" id="gridPanel"></table>
		<!-- 分页 -->
		<div class="panel-footer yxdPageCode"></div>
		</div>
		<div style="clear: both;"></div>
	</div>
	<!-- 添加评估 -->
	<div class="modal fade" id="addIndexPage">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
					<h4 class="modal-title">添加评估</h4>
				</div>
				<div class="modal-body">
					<p>
					<table style="margin: 0 auto;">
						<tr>
							<td>名称</td>
							<td style="width: 300px;">
								<input type="text" class="form-control" name="assessName" style="width: 300px;"/>
							</td>
						</tr>
						<tr>
							<td>版本</td>
							<td style="width: 300px;">
								<input type="text" class="form-control" name="assessVersion" style="width: 300px;"/>
							</td>
						</tr>
						<tr>
							<td>备注</td>
							<td  style="width: 300px;">
								<textarea class="form-control" name="remarks" style="width: 300px;"></textarea>
							</td>
						</tr>
					</table>
					</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary OK">确定</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">


	$(document).ready(function() {
		loadData();
		
		$("#selectPageSize").change(function(){
			$('#gridPanel').Datagrid('options').other.pageSize = $(this).val();
			searchData();
		});
	});

	function searchData(){
		$('#gridPanel').Datagrid('options').queryParams = {assessName:$("#searchField").val()};
		$('#gridPanel').Datagrid('reload');
	}
	function loadData(){ 
		var options = {
				url:ctx+"/project/evaluateList",
				queryParams:{assessName:$("#searchField").val()},
				other:{
					pageSize:10,
				    currentPage:1,
				    data: {
				        cols: [
	                    { field: 'id',hidden:"true"},
	                    {  title: '序号.', width: 30,formatter: function(value, row,index){
	                    	return index+1;
	                    }},
	                    { field: 'assessName', title: '评估时间', width: 110, halign:'center',align:'center' },
	                    { field: 'assessVersion', title: '评估状态', width: 100, halign:'center',align:'center' },
	                    { field: 'assessContent', title: '评估说明', width: 250, halign:'center',align:'center' },
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
		var look         = "<a href='#'  class='easyui-linkbutton' onclick=startEvaluate('"+row.id+"');>开始评估</a>";
		return look;
		
	}
	
	function addIndex(){
		var el = $("#addIndexPage");
		el.modal({'show': true, 'backdrop': 'static', 'position': '50'});
		el.find(".OK").off('click').on('click', function () {
	        
	        
	        var indexName = el.find("input[name=assessName]").val();
	        var assessVersion = el.find("input[name=assessVersion]").val();
	        var remarks = el.find("textarea[name=remarks]").val();
	        if (indexName == '') {
	            tipMsg('请输入名称');
	            return;
	        }
	        if (assessVersion == '') {
	            tipMsg('请输入版本');
	            return;
	        }
	        if (remarks == '') {
	            tipMsg('请输入备注信息');
	            return;
	        }
	        
	        var json = {assessName:indexName,assessVersion:assessVersion,assessContent:remarks
	    	        ,projectId:'${projectId}',indexId:'${indexId}'};
	        myajax(ctx+"/project/addProjectEvaluate",json,
		     function(data){
	        	         if(data.status==1){
	        	        	 el.modal('hide');//确认关闭
	        	             tipMsg(data.msg);
	        	             searchData();
	        	         }else{
	        	             tipMsg(data.msg);
	        	         }
	        	    
	        	     });
	        
	    });
	}

	
	function startEvaluate(id){
		//TODO 开始评估
	}
	
	</script>
</body>
</html>