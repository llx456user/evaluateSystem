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
			<strong>sql信息</strong>
		</div>
	    <div class="panel-body">
			<form id="fileEditForm" method="post">
			  <div class="form-group">
			    <label for="fileName">sql名称</label>
			    <input type="text" class="form-control" id="sqlName" name="sqlName" placeholder="" value="${bean.sqlName }" readonly="readonly">
			  </div>
			   <div class="form-group">
			    <label for="fileName">数据源名称</label>
			    <input type="text" class="form-control" id="sourceName" name="sourceName" placeholder="" value="${bean.sourceName}" readonly="readonly">
			  </div>
			  
			  <div class="form-group">
			    <label for="fileContent">sql语句</label>
			    <textarea class="form-control" id="sqlStr" name="sqlStr" placeholder="" readonly="readonly">${bean.sqlStr }</textarea>
			  </div>
			 </form>
			 
			<button class="btn btn-default" type="button" onclick="back();">返回</button>
		</div>
	</div>
	
	</div>
	
	<script type="text/javascript">

	function back(){
		
		window.history.go(-1);
	}

	
	</script>
</body>
</html>