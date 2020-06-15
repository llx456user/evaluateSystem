<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
	<title>评估系统-模型添加</title>
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

		.addGroupBtn{
			color:#3280fc;
		}
		.addGroupBtn:hover{
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

		.groupTrash{
			color:#3280fc;
			margin-left:10px;
		}
		.groupTrash:hover{
			cursor:pointer;
			color:#1970fc;
		}
		.divLine{
			margin-bottom: 10px;
			border-bottom: 1px solid #f1f1f1;
		}
	</style>
</head>
<body>
<div class="container">

	<!-- 新建文件 -->
	<div class="panel" style="margin-bottom: 5px;">
		<div class="panel-heading">
			<strong style="font-size: ${16}">${projectName}数据信息</strong>
		</div>
		<div class="panel-body">
			<form id="fileEditForm" method="post">
				<input type="hidden" name="id" id="id" value="${projectId }"/>
				<input type="hidden" name="projectName" id="projectName" value="${projectName }"/>
				<div style="float:right; padding-bottom: 10px">
					<c:if test="${initialTemplate == true}">
						<button type="button" class="btn btn-primary" onclick="normalTemplates();">普通模板</button>
						<button type="button" class="btn btn-primary" onclick="groupTemplates();">分组模板</button>
					</c:if>
					<button type="button" class="btn btn-primary" onclick="showAddTemplateList();">数据管理</button>
				</div>
				<!--  改造一下保存事件  添加以下内容 start -->
				<div id="normalTemplates" style="display:${isNormalTemplate == true || initialTemplate == true?"block":"none"}">
					<div class="form-group">
						<label>数据项</label>
						<table class="table table-borderless">
							<thead>
							<tr>
								<th>数据项名称</th>
								<th></th>
								<th>操作</th>
							</tr>
							</thead>
							<tbody  id="iptVariableTb">
							<!--判断新建还是删除-->
							<c:choose>
								<c:when test="${inparList.size() > 0}">

									<c:forEach items="${inparList }" var="in" varStatus="stat">
										<tr>
											<td style="width: 75%">
												<input type="text" class="form-control" name="paramName" value="${in.paramName }"  style=" text-indent: 4px; background: transparent;">
											</td>
											<td>
												<input type="hidden" class="form-control" name="id" value="${in.id }" >
											</td>
											<td><i title="添加" onclick="addBtnIpt(event)" class="icon icon-plus-sign icon-2x addBtn iptVar"></i><i title="删除" class="icon icon-trash icon-2x iptVar trash"></i></td>
										</tr>
									</c:forEach>

								</c:when>
								<c:otherwise>
									<tr>
										<td style="width: 75%">
											<input type="text" class="form-control" name="paramName" value=""  style=" text-indent: 4px; background: transparent;">
										</td>
										<td>
											<input type="hidden" class="form-control" name="id">
										</td>
										<td><i onclick="addBtnIpt(event)" title="添加" class="icon icon-plus-sign icon-2x addBtn iptVar"></i><i title="删除" class="icon icon-trash icon-2x iptVar trash"></i></td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
					</div>
					<div class="form-group">
						<label>数据源</label>
						<table class="table table-borderless">
							<thead>
							<tr>
								<th>数据名称</th>
								<th></th>
								<th>操作</th>
							</tr>
							</thead>
							<tbody id="optVariableTb">
							<c:choose>
								<c:when test="${outparList.size() > 0 }">

									<c:forEach items="${outparList }" var="out" varStatus="stat">
										<tr>
											<td style="width: 75%">
												<input type="text" class="form-control" name="paramName" value="${out.paramName }" style=" text-indent: 4px; background: transparent;">
											</td>
											<td>
												<input type="hidden" class="form-control" name="id" value="${out.id }">
											</td>
											<td>
												<i title="添加" onclick="addBtnOpt(event)" class="icon icon-plus-sign icon-2x addBtn iptVar"></i>
												<i title="删除" class="icon icon-trash icon-2x iptVar trash"></i>
												<%--<i title="上传" onclick="uploadOpen(event)" class="icon icon-upload-alt icon-2x iptVar uoloadBtn"></i>--%>
											</td>
										</tr>
									</c:forEach>

								</c:when>
								<c:otherwise>
									<tr>
										<td style="width: 75%">
											<input type="text" class="form-control" name="paramName" value="" style=" text-indent: 4px; background: transparent;">
										</td>
										<td>
											<input type="hidden" class="form-control" name="id" >
										</td>
										<td>
											<i title="添加" onclick="addBtnOpt(event)" class="icon icon-plus-sign icon-2x addBtn optVar"></i>
											<i title="删除" class="icon icon-trash icon-2x iptVar trash"></i>
											<%--<i title="上传" onclick="uploadOpen(event)" class="icon icon-upload-alt icon-2x iptVar uoloadBtn"></i>--%>
										</td>
									</tr>
								</c:otherwise>
							</c:choose>

							</tbody>
						</table>
					</div>
					<button class="btn btn-primary" type="button" onclick="save();">保存</button>
					<button class="btn btn-default" type="button" onclick="back();">返回</button>
				</div>

				<%--分组初始化模板--%>
				<div id="initialGroupTemplates" style="display:none">
					<div class="form-group" style="padding-top: 20px">
						<button type="button" class="btn btn-primary" onclick="addGroup();">新增分组</button>
					</div>
					<div class="form-group">
						<label>分组名称</label>
						<input type="text" class="form-control" id="initialGroupName" name="initialGroupName" placeholder="请输入分组名称">
					</div>
					<div class="form-group">
						<label>数据项</label>
						<table class="table table-borderless">
							<thead>
							<tr>
								<th>数据项名称</th>
								<th></th>
								<th>操作</th>
							</tr>
							</thead>
							<tbody id="iptInitialGroupVariableTb">
							<tr>
								<td style="width: 75%">
									<input type="text" class="form-control" name="paramName" value=""  style=" text-indent: 4px; background: transparent;">
								</td>
								<td>
									<input type="hidden" class="form-control" name="id">
								</td>
								<td>
									<i onclick="addGroupBtnIpt(event)" title="添加" class="icon icon-plus-sign icon-2x addGroupBtn iptVar"></i>
									<i title="删除" onclick="deleteGroup(event)" class="icon icon-trash icon-2x iptVar groupTrash"></i>
								</td>
							</tr>
							</tbody>
						</table>
					</div>
					<div class="form-group">
						<label>数据源</label>
						<table class="table table-borderless">
							<thead>
							<tr>
								<th>数据名称</th>
								<th></th>
								<th>操作</th>
							</tr>
							</thead>
							<tbody id="optInitialGroupVariableTb">
							<tr>
								<td style="width: 75%">
									<input type="text" class="form-control" name="paramName" value="" style=" text-indent: 4px; background: transparent;">
								</td>
								<td>
									<input type="hidden" class="form-control" name="id" >
								</td>
								<td>
									<i title="添加" onclick="addGroupBtnOpt(event)" class="icon icon-plus-sign icon-2x addGroupBtn optVar"></i>
									<i title="删除" onclick="deleteGroup(event)" class="icon icon-trash icon-2x iptVar groupTrash"></i>
								</td>
							</tr>
							</tbody>
						</table>
					</div>
					<button class="btn btn-primary" type="button" onclick="saveInitialGroupTemplate();">保存</button>
					<button class="btn btn-default" type="button" onclick="back();">返回</button>
				</div>


				<%--分组模板--%>
				<div id="groupTemplates" style="display:${isGroupTemplate == true?"block":"none"}">
					<div class="form-group" style="padding-top: 20px">
						<button type="button" class="btn btn-primary" onclick="addGroup();">新增分组</button>
					</div>
						<c:forEach items="${groupParam }" var="group" varStatus="stat">
							<div class="form-group" style="display: flex">
								<label style="font-size: ${17-stat.count}; padding-top: 4px">分组名称</label>
								<input style="width: 80%; margin-left: 20px" type="text" class="form-control" id="groupName${group.dataTemplateNumber}" name="groupName${group.dataTemplateNumber}" value="${group.dataTemplateName}">
								<button style="margin-left: 20px" type="button" class="btn btn-primary" onclick="deleteGroupTemplate(${group.dataTemplateNumber});">删除分组</button>
							</div>
							<div class="form-group">
								<label style="font-size: ${17-stat.count}">数据项</label>
								<table class="table table-borderless">
									<thead>
									<tr>
										<th style="font-size: ${16-stat.count}">数据项名称</th>
										<th></th>
										<th>操作</th>
									</tr>
									</thead>
									<tbody  id="iptGroupVariableTb${group.dataTemplateNumber}" >
										<c:forEach items="${group.cParamList }" var="in" varStatus="stats">
											<tr>
												<td style="width: 75%">
													<input type="text" class="form-control" name="paramName" value="${in.paramName }"  style=" text-indent: 4px; background: transparent;">
												</td>
												<td>
													<input type="hidden" class="form-control" name="id" value="${in.id }" >
												</td>
												<td>
													<i title="添加" onclick="addGroupBtnIpt(event)" class="icon icon-plus-sign icon-2x addGroupBtn iptVar"></i>
													<i title="删除" onclick="deleteGroup(event)" class="icon icon-trash icon-2x iptVar groupTrash"></i>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div class="form-group">
								<label style="font-size: ${17-stat.count}">数据源</label>
								<table class="table table-borderless">
									<thead>
									<tr>
										<th style="font-size: ${16-stat.count}">数据名称</th>
										<th></th>
										<th>操作</th>
									</tr>
									</thead>
									<tbody id="optGroupVariableTb${group.dataTemplateNumber}" >
										<c:forEach items="${group.fParamList }" var="out" varStatus="stat">
											<tr>
												<td style="width: 75%">
													<input type="text" class="form-control" name="paramName" value="${out.paramName }" style=" text-indent: 4px; background: transparent;">
												</td>
												<td>
													<input type="hidden" class="form-control" name="id" value="${out.id }">
												</td>
												<td>
													<i title="添加" onclick="addGroupBtnOpt(event)" class="icon icon-plus-sign icon-2x addGroupBtn iptVar"></i>
													<i title="删除" onclick="deleteGroup(event)" class="icon icon-trash icon-2x iptVar groupTrash"></i>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div id="divLine" class="divLine"></div>
						</c:forEach>
					<button class="btn btn-primary" type="button" onclick="saveGroup(event);">保存</button>
					<button class="btn btn-default" type="button" onclick="back();">返回</button>
				</div>

			</form>
		</div>
	</div>
</div>
<script type="text/javascript">

	$(document).ready(
			function () {
				// setInterval(savemodel,50000);
			}
	);

	function back(){

		window.history.go(-1);
	}

	var tmpStructJson = [];
	$(function(){

		$("#iptVariableTb").on('click','.trash',function(){
			console.log("变量删除")
			var tr = document.getElementsByTagName('tbody')[0].getElementsByTagName('tr');
			if(tr.length==1){
				tipMsg('最后一行无法删除');
			}else {
				$(this).parent().parent().remove();
			}
		});
		$("#optVariableTb").on('click','.trash',function(){
			console.log("附件删除")
			var tr = document.getElementsByTagName('tbody')[1].getElementsByTagName('tr');
			if(tr.length==1){
				tipMsg('最后一行无法删除');
			}
			else {
				$(this).parent().parent().remove();
			}

		});

	});


	/***********************************  保存时操作的相关改造 ******************************************/
	function deleteGroup(event) {
        var temp = $(event.target).parent().parent().parent().attr("id");
        console.log(temp)
		$("#"+ temp).on('click','.groupTrash',function(){
			console.log("变量删除")
			var tr = document.getElementById(temp).getElementsByTagName('tr');
			console.log(tr)
			if(tr.length==1){
				tipMsg('最后一行无法删除');
			}else {
				$(this).parent().parent().remove();
			}
		});
	}

	// 改造一下保存事件  添加以下内容
	function save(){
        constrantCheck = true;
        fileCheck = true;
	    var ipt = "iptVarJson";
	    var opt = "optVarJson";
        //常量
		var iptVarJson =  getJson("#iptVariableTb",ipt);
		//附件
		var optVarJson =  getJson("#optVariableTb",opt);

		if (fileCheck == false || constrantCheck == false){
			tipMsg("请输入常量名称或附件名称");
			return;
		}

		var projectId = $("#id").val();
		var param = {
			projectId:projectId,
		};

		param.iptVarJson = iptVarJson;
		param.optVarJson = optVarJson;
		console.log(JSON.stringify(param));
		var i = openProgressExt("保存中...");
		//return;
		$.ajax({
			url:ctx + '/project/saveProjectParam',
			data:{reqJson:JSON.stringify(param)},
			dataType:'JSON',
			success:function(data){
				closeProgressExt(i);
				if(data.status==1){
					tipMsg(data.msg);
					setTimeout(function() {
						back();
					}, 1000);
				}else{
					tipMsg(data.msg);
				}
			}
		});
	}
	// 保存新增分组数据
	function saveNewGroup() {
		constrantCheck = true;
		fileCheck = true;
		var ipt = "iptVarJson";
		var opt = "optVarJson";
		var dataTemplateName = $("#newGroupName").val();
		var projectName = $("#projectName").val();
		console.log(dataTemplateName + projectName);
		if (dataTemplateName == "" || dataTemplateName == null || dataTemplateName == undefined){
			tipMsg("请输入分组名称");
			return;
		}
		//常量
		var iptVarJson =  getJson("#iptNewGroupVariableTb",ipt);
		//附件
		var optVarJson =  getJson("#optNewGroupVariableTb",opt);

		if (fileCheck == false || constrantCheck == false){
			tipMsg("请输入常量名称或附件名称");
			return;
		}

		var projectId = $("#id").val();
		var param = {
			projectId:projectId,
			dataTemplateName:dataTemplateName
		};

		param.iptVarJson = iptVarJson;
		param.optVarJson = optVarJson;
		console.log(JSON.stringify(param));
		var i = openProgressExt("保存中...");
		//return;
		$.ajax({
			url:ctx + '/project/saveAddGroupProjectParam',
			data:{reqJson:JSON.stringify(param)},
			dataType:'JSON',
			success:function(data){
				closeProgressExt(i);
				if(data.status==1){
					tipMsg(data.msg);
					$("#newGroupTemplates").modal('hide');
					back();
					window.location.href = ctx + '/project/addOrEditProjectParam?projectInfoId=' + projectId +'&projectName='+encodeURI(encodeURI(projectName));
				}else{
					tipMsg(data.msg);
				}
			}
		});
	}
	function saveGroup(event) {
		console.log(event)
        var projectId = $("#id").val();
        var param = {
            projectId: projectId
        };
		var resultArr = [];
        myajax(ctx + '/project/getGroupTemplateParam', param, function (data) {
            for (var i = 0; i < data.length; i++) {
                var dataTemplateNumber = data[i].dataTemplateNumber;
                var arr = getGroupJson(dataTemplateNumber)
                resultArr = resultArr.concat(arr);
            }
            var param = {
                params: JSON.stringify(resultArr),
                projectId: projectId,
            }
            param = JSON.stringify(param)
            console.log(param);
            var j = openProgressExt("保存中...");
            $.ajax({
                url: ctx + '/project/groupTemplateHandle',
                type: 'POST',
                contentType: 'application/json',
                data: param,
                success: function (data) {
                    closeProgressExt(j)
                    if (data.status == 1) {
                        tipMsg(data.msg);
                        setTimeout(function(){
                            back();
                        },1000);
                    } else {
                        tipMsg(data.msg);
                    }
                }
            });
        });
	}
	function getGroupJson(val) {
	    var arr = [];
        var groupVarJson = [];
        var dataTemplateName = $("#groupName" + val).val();
        if (dataTemplateName == "" || dataTemplateName == null || dataTemplateName == undefined){
            tipMsg("请输入分组名称");
            return;
        }
        $("#iptGroupVariableTb" + val).find("tr").each(function(){
            var paramName = $(this).find("input[name=paramName]").val();
            var id = $(this).find("input[name=id]").val();
            var jsonObj = {
                dataTemplateNumber:val,
                dataTemplateName:dataTemplateName,
                paramName:paramName,
                paramType:"constrant",
                id:id,
            };
            groupVarJson.push(jsonObj);
        });
        $("#optGroupVariableTb" + val).find("tr").each(function(){
            var paramName = $(this).find("input[name=paramName]").val();
            var id = $(this).find("input[name=id]").val();
            var jsonObj = {
                dataTemplateNumber:val,
                dataTemplateName:dataTemplateName,
                paramName:paramName,
                paramType:"file",
                id:id,
            };
            groupVarJson.push(jsonObj);
        });
        arr.push(groupVarJson);
        // arr.push(optVarJson)
        console.log(arr)
        return arr;
    }
	// 保存模板初始化数据
	function saveInitialGroupTemplate() {
		constrantCheck = true;
		fileCheck = true;
		var ipt = "iptVarJson";
		var opt = "optVarJson";
		var dataTemplateName = $("#initialGroupName").val();
		var projectName = $("#projectName").val();
		console.log(dataTemplateName + projectName);
		if (dataTemplateName == "" || dataTemplateName == null || dataTemplateName == undefined){
			tipMsg("请输入分组名称");
			return;
		}
		//常量
		var iptVarJson =  getJson("#iptInitialGroupVariableTb",ipt);
		//附件
		var optVarJson =  getJson("#optInitialGroupVariableTb",opt);

		if (fileCheck == false || constrantCheck == false){
			tipMsg("请输入常量名称或附件名称");
			return;
		}

		var projectId = $("#id").val();
		var param = {
			projectId:projectId,
			dataTemplateName:dataTemplateName
		};

		param.iptVarJson = iptVarJson;
		param.optVarJson = optVarJson;
		console.log(JSON.stringify(param));
		var i = openProgressExt("保存中...");
		//return;
		$.ajax({
			url:ctx + '/project/saveAddGroupProjectParam',
			data:{reqJson:JSON.stringify(param)},
			dataType:'JSON',
			success:function(data){
				closeProgressExt(i);
				if(data.status==1){
					tipMsg(data.msg);
					$("#newGroupTemplates").modal('hide');
					back();
					window.location.href = ctx + '/project/addOrEditProjectParam?projectInfoId=' + projectId +'&projectName='+encodeURI(encodeURI(projectName));
				}else{
					tipMsg(data.msg);
				}
			}
		});
	}
 	var constrantCheck = true;
	var fileCheck = true;
	function getJson(el,tag){
		var json = [];
		$(el).find("tr").each(function(){
			var paramName = $(this).find("input[name=paramName]").val();
			var id = $(this).find("input[name=id]").val();
			if (tag == "iptVarJson" && constrantCheck == true) {
				if (paramName == "") {
					constrantCheck = false;
					return
				}
			}
			if (tag == "optVarJson" && fileCheck == true) {
				if (paramName == "") {
					fileCheck = false;
					return;
				}
			}
			var jsonObj = {
				paramName:paramName,
				id:id,
			};
			json.push(jsonObj);
		});
		return json;
	}
	function addBtnIpt(event){
		var tr = document.getElementsByTagName('tbody')[0].getElementsByTagName('tr');
		var $tr = $(event.target).parent().parent();
		var $tmp = $tr.clone(true);
		$tmp.find("input[name=array]").prop("checked",false);
		$tmp.find("input").val("");
		if(!$tmp.find("i").hasClass("trash")){
			$tmp.find(".addBtn").after('<i class="icon icon-trash icon-2x iptVar trash"></i>');
		}
		$(event.target).closest("tr").after($tmp);
	}



	function addBtnOpt(event){
		var tr = document.getElementsByTagName('tbody')[1].getElementsByTagName('tr');
		var $tr = $(event.target).parent().parent();
		var $tmp = $tr.clone(true);
		$tmp.find("input[name=array]").prop("checked",false);
		$tmp.find("input").val("");
		if(!$tmp.find("i").hasClass("trash")){
			$tmp.find(".addBtn").after('<i class="icon icon-trash icon-2x iptVar trash"></i>');
		}
		$(event.target).closest("tr").after($tmp);
	}



	function addGroupBtnIpt(event){
		var $tr = $(event.target).parent().parent();
		var $tmp = $tr.clone(true);
		$tmp.find("input[name=array]").prop("checked",false);
		$tmp.find("input").val("");
		if(!$tmp.find("i").hasClass("groupTrash")){
			$tmp.find(".addGroupBtn").after('<i class="icon icon-trash icon-2x iptVar groupTrash"></i>');
		}
		$(event.target).closest("tr").after($tmp);
	}



	function addGroupBtnOpt(event){
		console.log(event)
		var $tr = $(event.target).parent().parent();
		var $tmp = $tr.clone(true);
		$tmp.find("input[name=array]").prop("checked",false);
		$tmp.find("input").val("");
		if(!$tmp.find("i").hasClass("groupTrash")){
			$tmp.find(".addGroupBtn").after('<i class="icon icon-trash icon-2x iptVar groupTrash"></i>');
		}
		$(event.target).closest("tr").after($tmp);
	}




	var $currentTr = '';
	function uploadOpen(event) {
		$("#uploadDllPage").modal('show')
		$currentTr = $(event.target).parent().parent()
		console.log($currentTr);
	}

	function upload(){
		var file = $("#file").val();
		console.log(file);
		if(file == ""){
			tipMsg("请选择附件，再进行上传");
			return;
		}
		var i = openProgressExt("上传中...");
		$("#uploadForm").ajaxSubmit({
			//type: 'post',
			url: ctx + '/datasource/upload',
			success: function(data) {
				closeProgressExt(i);
				if(data != ''){
					$currentTr.find("input[name=paramValue]").val(data);
					var index = $("#file").val().lastIndexOf('\\')+1;
					$currentTr.find("input[name=paramName]").val($("#file").val().substring(index));
					console.log($("#file").val());
					$("#uploadDllPage").modal('hide');
					tipMsg('附件上传成功');
				} else {
					tipMsg('附件上传失败');
				}
			}
		});
	}
	function showAddTemplateList() {
		if (${initialTemplate == true}) {
			tipMsg("请先设置数据模板!")
			return;
		}
		var isGroupTemplate = false;
		if(${isGroupTemplate == true}){
			isGroupTemplate = true;
		}
		var showAddTemplateFlg = true;
		var projectInfoId = $("#id").val();
		var projectName = $("#projectName").val();
		window.location.href = ctx + '/project/dataTemplateList?projectInfoId=' + projectInfoId+ '&showAddTemplateFlg='+showAddTemplateFlg+'&projectName='+encodeURI(encodeURI(projectName))+ '&isGroupTemplate='+isGroupTemplate;
	}
	function groupTemplates() {
		console.log("点击了分组模板");
		var display = document.getElementById("initialGroupTemplates").style.display;
		console.log(display);
		if (display == "none") {
			document.getElementById("normalTemplates").style.display = "none";
			document.getElementById("initialGroupTemplates").style.display = "block";
		}
	}
	function normalTemplates() {
		console.log("点击了普通模板");
		var display = document.getElementById("normalTemplates").style.display;
		console.log(display);
		if (display == "none") {
			document.getElementById("normalTemplates").style.display = "block";
			document.getElementById("initialGroupTemplates").style.display = "none";
		}
	}
	function addGroup() {
		console.log("点击了新增分组");
        $("#newGroupTemplates").modal('show')
	}
	function deleteGroupTemplate(dataTemplateNumber) {
		console.log(dataTemplateNumber)
		var projectId = $("#id").val();
		myconfirm('确定删除此记录？(此操作不可逆)',{},function(b){
			if(b){
				var param = {
					projectId : projectId,
					dataTemplateNumber: dataTemplateNumber
				};
				myajax(ctx + '/project/delGroupTemplate', param, function(data) {
					tipMsg(data.msg);
					setTimeout(function() {
						openProgress("页面刷新中...");
						setTimeout(function() {
							location.reload();
						}, 500);
					}, 3000);
				});
			}
		});
	}

</script>

<div class="modal fade" id="newGroupTemplates">
    <div class="modal-dialog" style="width: 80%">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">新增分组</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label>分组名称</label>
                    <input type="text" class="form-control" id="newGroupName" name="newGroupName" placeholder="请输入分组名称">
                </div>
                <div class="form-group">
                    <label>数据项</label>
                    <table class="table table-borderless">
                        <thead>
                        <tr>
                            <th>数据项名称</th>
                            <th></th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody  id="iptNewGroupVariableTb">
                        <tr>
                            <td style="width: 75%">
                                <input type="text" class="form-control" name="paramName" value=""  style=" text-indent: 4px; background: transparent;">
                            </td>
                            <td>
                                <input type="hidden" class="form-control" name="id">
                            </td>
                            <td>
                                <i onclick="addGroupBtnIpt(event)" title="添加" class="icon icon-plus-sign icon-2x addGroupBtn iptVar"></i>
                                <i title="删除" onclick="deleteGroup(event)" class="icon icon-trash icon-2x iptVar groupTrash"></i>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="form-group">
                    <label>数据源</label>
                    <table class="table table-borderless">
                        <thead>
                        <tr>
                            <th>数据名称</th>
                            <th></th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="optNewGroupVariableTb">
                        <tr>
                            <td style="width: 75%">
                                <input type="text" class="form-control" name="paramName" value="" style=" text-indent: 4px; background: transparent;">
                            </td>
                            <td>
                                <input type="hidden" class="form-control" name="id" >
                            </td>
                            <td>
                                <i title="添加" onclick="addGroupBtnOpt(event)" class="icon icon-plus-sign icon-2x addGroupBtn optVar"></i>
                                <i title="删除" onclick="deleteGroup(event)" class="icon icon-trash icon-2x iptVar groupTrash"></i>
                                <%--<i title="上传" onclick="uploadOpen(event)" class="icon icon-upload-alt icon-2x iptVar uoloadBtn"></i>--%>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <button class="btn btn-primary" type="button" onclick="saveNewGroup();">保存</button>
            </div>
        </div>
    </div>
</div>

</body>

</html>