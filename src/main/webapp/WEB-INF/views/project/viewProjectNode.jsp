<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
	<title>评估系统-项目指标树</title>
<script type="text/javascript" src="<c:url value='/resources/js/project/projectNode.js?version=${jsversion}'/>"></script>
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
	
	div.indexDiv{
		width:150px;
		height:150px;
		border: 1px solid #ddd;
		float: left;
		margin-right:10px;
		cursor: move;
		border-radius:0 30px 0 30px;
	}
	.indexTitle{
		height:30px;
		background-color: #d1d1d1;
		text-align: center;
		color:#f00;
		font-size:16px;
		overflow: hidden;
		border-radius:0 30px 0 0;
	}
	.notcanMove .indexTitle{
		background: #f5f5f5;
	}
	</style>
</head>
<body>
	<div class="container">
	<!-- 选择指标 -->
		<div class="panel" style="margin-bottom: 5px;" id="multiDroppableContainer">
			<div class="panel-heading">
				<strong>项目指标树信息</strong>
			</div>
			<div id="myDiagramDiv" style=" vertical-align: top; padding: 5px;width:100%; height:100%"></div>
		</div>
	</div>
	<script type="text/javascript">
	$(document).ready(function() {
		var initNodeArray=JSON.parse('${projectNodeTreeJson}');
		init(initNodeArray);
	});
	var selectedIndexIds = [];
	function back(){
		window.history.go(-1);
	}
	function reload(){
		window.location.reload();
	}
	</script>
</body>
</html>