<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
	<title>评估系统-sql新增</title>
<%-- 	<script type="text/javascript" src="<c:url value='/resources/js/platform/modelList.js?version=${jsversion}'/>"></script>
 --%>	
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
		
		<!-- 新建文件 -->
        <div class="panel" style="margin-bottom: 5px;">
	        <div class="panel-heading">
			<strong>sql信息</strong>
		</div>
	    <div class="panel-body">
			<form id="fileEditForm" method="post">
			  <input type="hidden" name="id" id="id" value="${bean.id }"/>
			  <input type="hidden" name="datasourceId" id="datasourceId" value="${bean.datasourceId }"/>
			  <div class="form-group">
			    <label for="fileName">sql名称</label>
			    <input autofocus="autofocus" type="text" class="form-control" id="sqlName" name="sqlName" placeholder="" value="${bean.sqlName }">
			  </div>
			    
			  <div class="form-group">
			    <label for="fileName">数据源名称</label>
			    <input type="text" class="form-control" id="sourceName" name="sourceName" placeholder="" value="${bean.sourceName}" readonly="readonly">
			  </div>
			  
			  <div class="form-group">
			    <label for="fileContent">sql语句</label>
			    <textarea class="form-control" id="sqlStr" name="sqlStr" placeholder="">${bean.sqlStr }</textarea>
			  </div>
			   
			 </form>
			 
			 <button class="btn btn-primary" type="button" onclick="save();">调试&保存</button>
			<button class="btn btn-default" type="button" onclick="back();">返回</button>
		</div>
	</div>
	<script type="text/javascript">
	function back(){
		window.history.go(-1);
	}

	/***********************************  保存时操作的相关改造 ******************************************/
	// 改造一下保存事件  添加以下内容
	function save(){

		var id=$("#id").val();
		var datasourceId=$("#datasourceId").val();
		var sqlName=$("#sqlName").val();
		var sqlStr=$("#sqlStr").val();
		
		if(sqlName==""){
			tipMsg("sql名称不能为空");
			return;
		}
		if(sqlStr==""){
			tipMsg("sql语句不能为空");
			return;
		}
		
		var param = {id:id,
				datasourceId:datasourceId,
				sqlName:sqlName,
				sqlStr:sqlStr};
		
		console.log(JSON.stringify(param));

		myajax(ctx + '/sql/saveSql', param, function(data) {
			tipMsg(data.msg);
			if(data.status==1){
				setTimeout(function() {
					openProgress("页面刷新中...");
					setTimeout(function() {
						back();
					}, 500);
				}, 3000);
			}
		});
	}

	</script>
</body>
</html>