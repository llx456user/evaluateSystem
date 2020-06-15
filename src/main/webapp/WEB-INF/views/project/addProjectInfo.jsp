<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
	<title>评估系统-项目新增</title>
<%-- 	<script type="text/javascript" src="<c:url value='/resources/js/platform/modelList.js?version=${jsversion}'/>"></script>
 --%>
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
		<%--<!-- 选择指标 -->--%>
		<%--<div class="panel" style="margin-bottom: 5px;" id="multiDroppableContainer">--%>
			<%--<div class="panel-heading">--%>
				<%--<strong>项目指标树信息</strong>--%>
			<%--</div>--%>
			<%--<div class="panel-body">--%>
				<%--<button class="btn btn-primary" type="button" onclick="addRootIndex(0);">新建根指标</button>--%>
			<%--</div>--%>
			<%--<div id="myDiagramDiv" style=" vertical-align: top; padding: 5px;width:100%; height:300px"></div>--%>
		<%--</div>--%>

		<!-- 新建文件 -->
        <div class="panel" style="margin-bottom: 5px;">
	        <div class="panel-heading">
			<strong>项目信息</strong>
		</div>
	    <div class="panel-body">
			<form id="fileEditForm" method="post">
			  <input type="hidden" name="id" id="id" value="${bean.id }"/>
			  <input type="hidden" name=categoryId id="categoryId" value="${bean.categoryId }"/>
			  <div class="form-group">
			    <label for="projectName">项目名称</label>
			    <input type="text" onkeydown="return disableTextSubmit(event)" autofocus="autofocus" class="form-control" id="projectName" name="projectName" placeholder="" value="${bean.projectName }">
			  </div>

			  <div class="form-group">
			    <label for="projectContent">项目描述</label>
			    <textarea class="form-control" id="projectContent" name="projectContent" placeholder="">${bean.projectContent }</textarea>
			  </div>
			 </form>

			 <button class="btn btn-primary" type="button" onclick="save();">保存</button>
			<button class="btn btn-default" type="button" onclick="back();">返回</button>
		</div>
	</div>
	</div>
	<script type="text/javascript">
	function back(){
		window.history.go(-1);
	}
	function reload(){
		window.location.reload();
	}

	/***********************************  保存时操作的相关改造 ******************************************/

	function disableTextSubmit(e) {
		var e = e || window.event
		if (e != null && e != undefined && e.keyCode == 13) {
			return false;
		}
	}
	// 改造一下保存事件  添加以下内容
	function save(){

		var id=$("#id").val();
		var categoryId=$("#categoryId").val();
		var projectName=$("#projectName").val();
		var projectContent=$("#projectContent").val();

		if(projectName==""){
			tipMsg("项目名称不能为空");
			return;
		}
		if(projectContent==""){
            tipMsg("项目描述不能为空");
            return;
		}


		var param = {id:id,
				categoryId:categoryId,
				projectName:projectName,
				projectContent:projectContent};
		//param.indexIds = selectedIndexIds;
		console.log(JSON.stringify(param));

		$.ajax({
			url:ctx + '/project/saveProjectInfo',
			data:{reqJson:JSON.stringify(param)},
			dataType:'JSON',
			success:function(data){
				if(data.status>0){
					tipMsg(data.msg);
					setTimeout(function() {
						openProgress("页面刷新中...");
						setTimeout(function() {
							back();
						}, 500);
					}, 1000);
				}else{
					tipMsg(data.msg);
				}
			}
		});
	}
	</script>

</body>
</html>