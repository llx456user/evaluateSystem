<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
	<title>评估系统-项目评估树</title>
<script type="text/javascript" src="<c:url value='/resources/js/assess/assessNode.js?version=${jsversion}'/>"></script>
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
        <div id="myDiagramDiv" style=" vertical-align: top; padding: 5px;width:100%; height:300px"></div>
	</div>
		<!-- 评估信息 -->
		<div class="panel" style="margin-bottom: 5px;">
			<div class="panel-heading">
				<strong>评估信息</strong>
			</div>
			<div class="panel-body">
				<div class="form-group">
					<label for="assessName">评估名称</label>
					<input type="text" class="form-control" id="assessName" placeholder="" value="${info.assessName }">
				</div>
				<div class="form-group">
					<label for="assessContent">评估备注</label>
					<textarea class="form-control" id="assessContent" placeholder="">${info.assessContent }</textarea>
				</div>
			</div>
		</div>
	<script type="text/javascript">
	$(document).ready(function() {
		var initNodeArray=JSON.parse('${projectNodeTreeJson}');
		init(initNodeArray);
	});

	function back(){
		
		window.history.go(-1);
	}

	</script>
	</div>
</body>
</html>