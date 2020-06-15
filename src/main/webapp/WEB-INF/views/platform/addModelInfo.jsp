<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
	<title>评估系统-模型添加</title>
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
	</style>
</head>
<body>
	<div class="container">
		
		<!-- 新建文件 -->
        <div class="panel" style="margin-bottom: 5px;">
	        <div class="panel-heading">
			<strong>模型信息</strong>
		</div>
	    <div class="panel-body">
			<form id="fileEditForm" method="post">
			  <input type="hidden" name="id" id="id" value="${bean.id }"/>
			  <input type="hidden" name=modelCategoryid id="modelCategoryid" value="${bean.modelCategoryid }"/>
			  <div class="form-group">
			    <label for="fileName">模型名称(只能输入英文)</label>
			    <input autofocus="autofocus" type="text" class="form-control" id="modelName" name="modelName" placeholder="" value="${bean.modelName }" >
			  </div>
			  <div class="form-group">
			    <label for="fileVersion">模型版本</label>
			    <input type="text" class="form-control" id="modelVersion" name="modelVersion" placeholder="" value="${bean.modelVersion }">
			  </div>
			  <div class="form-group">
			    <label for="fileContent">模型描述</label>
			    <textarea class="form-control" id="modelContent" name="modelContent" placeholder="">${bean.modelContent }</textarea>
			  </div>
			  <!--  改造一下保存事件  添加以下内容 start -->
			  <div class="form-group">
			    <label for="fileContent">入参</label>
			    <table class="table table-borderless">
				<thead>
					<tr>
					  <th>参数变量名</th>
						<%--<th></th>--%>
					  <th>参数类型</th>
					  <th>参数单位</th>
					  <th>参数含义</th>
					  <th>操作</th>
					</tr>
				  </thead>
				  <tbody  id="iptVariableTb">
				  <!--判断新建还是删除-->
				  <c:choose>
				  <c:when test="${bean.id != null}">

					  <c:forEach items="${inparList }" var="in" varStatus="stat">
						  <tr>
							  <td>
								  <input type="text" class="form-control" name="parmeterName" value="${in.parmeterName }"  style=" text-indent: 4px; background: transparent;">
								  <%--<c:if test="${in.parmeterUnit == 'array'}"><label id="asterisk" style="margin-top:-25px;margin-left:2px;">*</label></c:if>--%>
								  <%--<p style="margin-top:-25px;margin-left:20px;">数组:<input type="checkbox" id="male"  name="array" ><p>--%>

							  </td>
							  <%--<td><input type="checkbox" name="array" onchange="cheeseAllorNot(event)" <c:if test="${in.isArray == true}">checked</c:if>> </td>--%>
							  <td>
								  <select name="parmeterType" class="form-control">
									  <option value="">-请选择-</option>
									  <option value="int" <c:if test="${in.parmeterType == 'int'}">selected</c:if>>int</option>
									  <option value="int*" <c:if test="${in.parmeterType == 'int*'}">selected</c:if>>int*</option>
									  <option value="long" <c:if test="${in.parmeterType == 'long'}">selected</c:if>>long</option>
									  <option value="long*" <c:if test="${in.parmeterType == 'long*'}">selected</c:if>>long*</option>
									  <option value="float" <c:if test="${in.parmeterType == 'float'}">selected</c:if>>float</option>
									  <option value="float*" <c:if test="${in.parmeterType == 'float*'}">selected</c:if>>float*</option>
									  <option value="double" <c:if test="${in.parmeterType == 'double'}">selected</c:if>>double</option>
									  <option value="double*" <c:if test="${in.parmeterType == 'double*'}">selected</c:if>>double*</option>
									  <option value="string" <c:if test="${in.parmeterType == 'string'}">selected</c:if>>string</option>
									  <option value="string*" <c:if test="${in.parmeterType == 'string*'}">selected</c:if>>string*</option>
									  <c:forEach items="${structList }" var="strp" varStatus="stat">
										  <%-- <option value="${strp.parmeterName }" <c:if test="${in.parmeterType == strp.parmeterName}">selected</c:if>>${strp.parmeterName }</option> --%>
										  
										  <c:if test="${in.parmeterType == strp.structName}">
										  <option value="${strp.structName}" selected>${strp.structName }</option>
										  </c:if>
										  
									  </c:forEach>
									  <option value="struct">new struct</option>
								  </select>
							  </td>
							  <td>
								  <%--<input type="text" class="form-control" name="parmeterUnit" value="${in.parmeterUnit }" >--%>

								  <%--<select name="parmeterUnit" class="form-control" onchange="entryParmeterUnit(event)">--%>
									  <%--<option value="">-请选择-</option>--%>
									  <%--<option value="array" <c:if test="${in.parmeterUnit == 'array'}">selected</c:if>>array</option>--%>
									  <%--<option value="not_array" <c:if test="${in.parmeterUnit == 'not_array'}">selected</c:if>>not array</option>--%>
								  <%--</select>--%>
							  <input type="text" class="form-control" name="parmeterUnit" value="${in.parmeterUnit }" >
							  </td>
							  <td><input type="text" class="form-control" name="parmeterUnitEx" value="${in.parmeterUnitEx }" ></td>
							  <td><c:if test="${!stat.last}"><i title="添加" onclick="addBtnIpt(event)" class="icon icon-plus-sign icon-2x addBtn iptVar"></i><i title="删除" class="icon icon-trash icon-2x iptVar trash"></i></c:if></td>
						  </tr>
					  </c:forEach>

				  </c:when>
				  <c:otherwise>
					  <tr>
                          <td>
                          <input type="text" class="form-control" name="parmeterName" value="inParam1"  style=" text-indent: 4px; background: transparent;">
						  </td>
						  <%--<td><input type="checkbox" name="array" onchange="cheeseAllorNot(event)"></td>--%>
						  <td>
							  <select name="parmeterType" class="form-control">
								  <option value="">-请选择-</option>
								  <option value="int">int</option>
								  <option value="int*" selected>int*</option>
								  <option value="long">long</option>
								  <option value="long*">long*</option>
								  <option value="float">float</option>
								  <option value="float*">float*</option>
								  <option value="double">double</option>
								  <option value="double*">double*</option>
								  <option value="string">string</option>
								  <option value="string*">string*</option>
								  <option value="struct">new struct</option>
							  </select>
						  </td>
						  <td>
							  <%--<select name="parmeterUnit" class="form-control" onchange="entryParmeterUnit(event)">--%>
								  <%--<option value="">-请选择-</option>--%>
								  <%--<option value="array">array</option>--%>
								  <%--<option value="not_array">not array</option>--%>
							  <%--</select>--%>
						  <input type="text" class="form-control" name="parmeterUnit">
						  </td>
						  <td><input type="text" class="form-control" name="parmeterUnitEx"></td>
						  <td><i onclick="addBtnIpt(event)" title="添加" class="icon icon-plus-sign icon-2x addBtn iptVar"></i><i title="删除" class="icon icon-trash icon-2x iptVar trash"></i></td>
					  </tr>
				  </c:otherwise>
				  </c:choose>
				  </tbody>
				</table>
			  </div>
			  <div class="form-group">
			    <label for="fileContent">出参</label>
			    <table class="table table-borderless">
				<thead>
					<tr>
					  <th>参数变量名</th>
						<%--<th></th>--%>
					  <th>参数类型</th>
					  <th>参数单位</th>
					  <th>参数含义</th>
					  <th>操作</th>
					</tr>
				  </thead>
				  <tbody id="optVariableTb">
				  <c:choose>
					  <c:when test="${bean.id != null}">

						  <c:forEach items="${outparList }" var="out" varStatus="stat">
							  <tr>
								  <td><input type="text" class="form-control" name="parmeterName" value="${out.parmeterName }"  style=" text-indent: 4px; background: transparent;">
									  <%--<c:if test="${out.parmeterUnit == 'array'}"><label id="asterisk" style="margin-top:-25px;margin-left:2px;">*</label></c:if>--%>
								  </td>
                                  <%--<td><input type="checkbox" name="array" onchange="cheeseAllorNot(event)" <c:if test="${out.isArray == true}">checked</c:if> > </td>--%>
								  <td>
									  <select name="parmeterType" class="form-control">
										  <option value="">-请选择-</option>
										  <option value="int" <c:if test="${out.parmeterType == 'int'}">selected</c:if>>int</option>
										  <option value="int*" <c:if test="${out.parmeterType == 'int*'}">selected</c:if>>int*</option>
										  <option value="long" <c:if test="${out.parmeterType == 'long'}">selected</c:if>>long</option>
										  <option value="long*" <c:if test="${out.parmeterType == 'long*'}">selected</c:if>>long*</option>
										  <option value="float" <c:if test="${out.parmeterType == 'float'}">selected</c:if>>float</option>
										  <option value="float*" <c:if test="${out.parmeterType == 'float*'}">selected</c:if>>float*</option>
										  <option value="double" <c:if test="${out.parmeterType == 'double'}">selected</c:if>>double</option>
										  <option value="double*" <c:if test="${out.parmeterType == 'double*'}">selected</c:if>>double*</option>
										  <option value="string" <c:if test="${out.parmeterType == 'string'}">selected</c:if>>string</option>
										  <option value="string*" <c:if test="${out.parmeterType == 'string*'}">selected</c:if>>string*</option>
										  <c:forEach items="${structList }" var="strp" varStatus="stat">
										  <c:if test="${out.parmeterType == strp.structName}">
										  <option value="${strp.structName}" selected>${strp.structName }</option>
										  </c:if>
											  
										  </c:forEach>
										  <option value="struct">new struct</option>
									  </select>
								  </td>
								  <td>
									  <%--<input type="text" class="form-control" name="parmeterUnit" value="${out.parmeterUnit }" >--%>
										  <%--<select name="parmeterUnit" class="form-control" onchange="outParmeterUnit(event)">--%>
											  <%--<option value="">-请选择-</option>--%>
											  <%--<option value="array" <c:if test="${out.parmeterUnit == 'array'}">selected</c:if>>array</option>--%>
											  <%--<option value="not_array" <c:if test="${out.parmeterUnit == 'not_array'}">selected</c:if>>not array</option>--%>
										  <%--</select>--%>
								  <input type="text" class="form-control" name="parmeterUnit" value="${out.parmeterUnit }" >

								  </td>
								  <td><input type="text" class="form-control" name="parmeterUnitEx" value="${out.parmeterUnitEx }" ></td>
								  <td><c:if test="${!stat.last}"><i title="添加" onclick="addBtnOpt(event)" class="icon icon-plus-sign icon-2x addBtn iptVar"></i><i title="删除" class="icon icon-trash icon-2x iptVar trash"></i></c:if></td>
							  </tr>
						  </c:forEach>

					  </c:when>
					  <c:otherwise>
						  <tr>
							  <td><input type="text" class="form-control" name="parmeterName" value="oParam1" style=" text-indent: 4px; background: transparent;"></td>
							  <%--<td><input type="checkbox" name="array" onchange="cheeseAllorNot(event)"></td>--%>
							  <td>
								  <select name="parmeterType" class="form-control">
									  <option value="">-请选择-</option>
									  <option value="int">int</option>
									  <option value="int*" selected>int*</option>
									  <option value="long">long</option>
									  <option value="long*">long*</option>
									  <option value="float">float</option>
									  <option value="float*">float*</option>
									  <option value="double">double</option>
									  <option value="double*">double*</option>
									  <option value="string">string</option>
									  <option value="string*">string*</option>
									  <option value="struct">new struct</option>
								  </select>
							  </td>
							  <td>
								  <%--<select name="parmeterUnit" class="form-control" onchange="outParmeterUnit(event)">--%>
									  <%--<option value="">-请选择-</option>--%>
									  <%--<option value="array">array</option>--%>
									  <%--<option value="not_array">not array</option>--%>
								  <%--</select>--%>
							  <input type="text" class="form-control" name="parmeterUnit" >
							  </td>
							  <td><input type="text" class="form-control" name="parmeterUnitEx"></td>
							  <td><i title="添加" onclick="addBtnOpt(event)" class="icon icon-plus-sign icon-2x addBtn optVar"></i><i title="删除" class="icon icon-trash icon-2x iptVar trash"></i></td>
						  </tr>
					  </c:otherwise>
				  </c:choose>


					
				  </tbody>
				</table>
			  </div>
			   
			 </form>
			 
			 <button class="btn btn-primary" type="button" onclick="save();">保存</button>
			<button class="btn btn-default" type="button" onclick="back();">返回</button>
		</div>
	</div>


	<div class="modal fade" id="newStructDialog">
	  <div class="modal-dialog modal-lg">
		<div class="modal-content">
		  <div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
			<h4 class="modal-title">结构体编辑</h4>
		  </div>
		  <div class="modal-body">
			<p>
			  <div class="form-group">
			    <label for="fileContent">结构体名称</label>
			    <input type="text" class="form-control" name="structName" />
			  </div>
				<div class="form-group">
					
					<table class="table table-borderless">
					<thead>
						<tr>
						  <th>参数变量名</th>
						  <th>参数类型</th>
						  <th>参数单位</th>
						  <th>参数含义</th>
						  <th>操作</th>
						</tr>
					  </thead>
					  <tbody id="VariableTb" class="VariableTb">
						<tr>
						  <td><input type="text" class="form-control" name="parmeterName"></td>
						  <td>
							<select name="parmeterType" class="form-control">
								<option value="">-请选择-</option>
								<option value="int">int</option>
								<option value="int*">int*</option>
								<option value="long">long</option>
								<option value="long*">long*</option>
								<option value="float">float</option>
								<option value="float*">float*</option>
								<option value="double">double</option>
								<option value="double*">double*</option>
							</select>
						  </td>
						  <td><input type="text" class="form-control" name="parmeterUnit"></td>
						  <td><input type="text" class="form-control" name="parmeterUnitEx"></td>
						  <td><i class="icon icon-plus-sign icon-2x addBtn optVar"></i><i class="icon icon-trash icon-2x iptVar trash"></i></td>
						</tr>
						
					  </tbody>
					</table>
				  </div>
			</p>
		  </div>
		  <div class="modal-footer">
			  <button type="button" class="btn btn-primary" id="OK">确认</button>
			<button type="button" class="btn btn-default" data-dismiss="modal" id="cancel">取消</button>

		  </div>
		</div>
	  </div>
	</div>
	</div>
	<script type="text/javascript">
		$(document).ready(
		    function () {
				setInterval(savemodel,50000);
            }
		);

	function back(){
		
		window.history.go(-1);
	}

	var structName_="";
	function selectStruct($self){
		// structName_="";
		var fid= self.frameElement.getAttribute('id');
		var url = ctx + '/platform/structList2?frameid='+fid;
		top.openLayerDialog('选择结构体','80%','80%',url,function(data){
			//TODO
			if(structName_!=''){
				$self.append('<option value="'+structName_+'" selected>'+structName_+'</option>')
			}
			
		});
	}

    //等待 后台存储结构体信息 structId为选择的结构体Id
    function callBackSelectStruct(structname){
        structName_=structname;

    }
    // function structIdaa(structId) {
    //     var json = {structId:structId};
    //     myajax(ctx+"/platform/setStructId",json,
    //         function(data){
    //             structName_=data.structname;
    //         });
    // }
	var tmpStructJson = [];
	$(function(){
		$("#file").change(function(){
			
			var filePath = $(this).val();
			var f = document.getElementById("file").files;  
			         //名称  
			        var name = (f[0].name);  
			         //大小 字节  
			         var size = f[0].size;
			         var dw = 'Bytes';
			         if(size >= 1024){
			        	 dw = 'KB';
				        size =  (size / 1024);
			         }
			         if(size >= 1024){
			        	 dw = 'MB';
			        	 size = size / 1024;
			         }
			         
			   $("#dllname").val(name);
		});

		

		// 改造一下保存事件  添加以下内容 start
		
		// $("#iptVariableTb").on('click','.addBtn',function(){
		// 	var tr = document.getElementsByTagName('tbody')[0].getElementsByTagName('tr');
		// 	$("#iptVariableTb").append(createTrParam(this,"inParam",tr.length+1));
		// });
        //
		// $("#optVariableTb").on('click','.addBtn',function(){
		// 	var tr = document.getElementsByTagName('tbody')[1].getElementsByTagName('tr');
		// 	$("#optVariableTb").append(createTrParam(this,"oParam",tr.length+1));
		//
		// });
		// $("#VariableTb").on('click','.addBtn',function(){
		// 	$("#VariableTb").append(createTr(this));
		//
		// });

		//创建一列
		function createTrParam(el,paramNamePre,paramCount){
			var $tr = $(el).parent().parent();
			var $tmp = $tr.clone(true);
            $tmp.find("input[name=array]").prop("checked",false);
			$tmp.find("input").val("");
			$tmp.find("input[name=parmeterName]").val(paramNamePre+paramCount);
			
            //$tmp.find("select").val("");
            $tmp.find("#asterisk").remove("#asterisk");
			if(!$tmp.find("i").hasClass("trash")){
				$tmp.find(".addBtn").after('<i class="icon icon-trash icon-2x iptVar trash"></i>');
			}

			return $tmp;
		}
		//创建一列
		function createTr(el){
			var $tr = $(el).parent().parent();
			var $tmp = $tr.clone(true);
            $tmp.find("input[name=array]").prop("checked",false);
			$tmp.find("input").val("");
            $tmp.find("select").val("");
            $tmp.find("#asterisk").remove("#asterisk");
			if(!$tmp.find("i").hasClass("trash")){
				$tmp.find(".addBtn").after('<i class="icon icon-trash icon-2x iptVar trash"></i>');
			}

			return $tmp;
		}
		

		$("#iptVariableTb").on('click','.trash',function(){
            var tr = document.getElementsByTagName('tbody')[0].getElementsByTagName('tr');
            if(tr.length==1){
                tipMsg('最后一行无法删除');
			}else {
                $(this).parent().parent().remove();
			}


		});
		$("#optVariableTb").on('click','.trash',function(){
            var tr = document.getElementsByTagName('tbody')[1].getElementsByTagName('tr');
            if(tr.length==1){
                tipMsg('最后一行无法删除');
			}
            else {
                $(this).parent().parent().remove();
            }

		});
		$("#VariableTb").on('click','.trash',function(){
			$(this).parent().parent().remove();
		});


            $("select[name=parmeterType]").mousedown(function(){//当按下鼠标按钮的时候
                this.sindex = this.selectedIndex;//把当前选中的值得索引赋给下拉选中的索引
                this.selectedIndex = -1;//把下拉选中的索引改变为-1,也就是没有!
            });

            $("select[name=parmeterType]").mouseout(function(){//当鼠标移开的时候
                var index = this.selectedIndex;//获取下拉选中的索引
                if(index == -1){//如果为-1,就是根本没有选
                    this.selectedIndex = this.sindex;//就把下拉选中的索引改变成之前选中的值得索引,就默认选择的是之前选中的值
                }});

            $("select[name=parmeterType]").change(function(){
			var $self = $(this);
			if($self.val() == "struct"){
				selectStruct($self);
                $self.blur();//失去焦点
				//弹窗
				/*$("#newStructDialog").modal('show');

				 $("#OK").off('click').on('click',function(){

					$("#newStructDialog").modal('hide');

					var varJson = getJson("#VariableTb");
					var name = $("#newStructDialog").find("input[name=structName]").val();

					var last = tmpStructJson.length + 10;
					
					var tmpStructJsonObj = {parmeterName:name,parmeterType:'struct',varJson:(varJson)};
					tmpStructJson.push(tmpStructJsonObj);
					//$self.append('<option value="'+name+'">'+name+'</option>');
					
					$("select[name=parmeterType]").each(function(){
						if($(this).parents().hasClass("VariableTb")){
							$(this).append('<option value="'+name+'">'+name+'</option>');
						}else if(!$(this).parents().hasClass("VariableTb")){
                            $(this).find("option:last").remove();
						    //倒数第一行插入
							$(this).append('<option value="'+name+'">'+name+'</option>');
                            $(this).append('<option value="struct">new struct</option>');
						}
					});
					$self.find("option[value="+name+"]").attr("selected",true);
					console.log( "临时json:"+ JSON.stringify(tmpStructJson));
					clearStructDialog();
				});

				$("#cancel").off('click').on('click',function(){
					$("#newStructDialog").modal('hide');
				}); */
			}
		});



		function clearStructDialog(){
			$("#newStructDialog").find("input[name=structName]").val("");
			$("#newStructDialog").each(function(){
			
				$(this).find("input[name=parmeterName]").val("");
				$(this).find("[name=parmeterType] option:selected").val("");
				$(this).find("input[name=parmeterUnit]").val("");
				$(this).find("input[name=parmeterUnitEx]").val("");
			});
		}

		// 改造一下保存事件  添加以下内容 end
		
	});

	/***********************************  保存时操作的相关改造 ******************************************/
	// 改造一下保存事件  添加以下内容
	function save(){
	    //入參
		var iptVarJson =  getJson("#iptVariableTb");
		//出參
		var optVarJson =  getJson("#optVariableTb");
		//结构体定义
		//var structVarJson = tmpStructJson;

		//console.log(iptVarJson);
		//console.log(optVarJson);
		

		var id=$("#id").val();
		var modelCategoryid=$("#modelCategoryid").val();
		var modelName=$("#modelName").val();
		var modelVersion=$("#modelVersion").val();
		var modelContent=$("#modelContent").val();
		
		
		/* if(dllPath == ""){
			tipMsg("请先上传文件，再进行保存");
			return;
		} */
		if(modelName==""){
			tipMsg("模型名称不能为空");
			return;
		}
		if(modelName.trim().length!=0){
		    // /^[\u4e00=\u9fff]+$/;
            var reg=/([\u4E00-\u9FA5])+/;
		    if(reg.test(modelName.trim().toString())){
                tipMsg("模型名称不能包含中文");
                return;
			}
		}
		var param = {id:id,
				modelCategoryid:modelCategoryid,
				modelName:modelName,
				modelVersion:modelVersion,
				modelContent:modelContent};//你原来的传递参数

		//然后把这两个 搞成字符串传递给后台   
		//后台接受字符串 解析成JSON操作
		//param.iptVarJson = JSON.stringify(iptVarJson);
		//param.optVarJson = JSON.stringify(optVarJson);
		param.iptVarJson = iptVarJson;
		param.optVarJson = optVarJson;
        //param.structVarJson=structVarJson;
		console.log(JSON.stringify(param));
		console.log( "临时json:"+ JSON.stringify(tmpStructJson));
		var i = openProgressExt("保存中...");
		//return;
		$.ajax({
			url:ctx + '/platform/saveModelInfo',
			data:{reqJson:JSON.stringify(param)},
			dataType:'JSON',
			success:function(data){
				closeProgressExt(i);
				//console.log(data);
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

	function getJson(el){
		var json = [];
		$(el).find("tr").each(function(){
			var parmeterName = $(this).find("input[name=parmeterName]").val();
            var isArray = $(this).find("input[name=array]").prop("checked");
            var parmeterType = $(this).find("[name=parmeterType] option:selected").val();
			var parmeterUnit = $(this).find("[name=parmeterUnit]").val();
			var parmeterUnitEx = $(this).find("input[name=parmeterUnitEx]").val();
			var structJson = null;
			var jsonObj = {
					parmeterName:parmeterName,
                    isArray:isArray,
					parmeterType:parmeterType,
					parmeterUnit:parmeterUnit,
					parmeterUnitEx:parmeterUnitEx
				};
			// if(parmeterType > 5){
			// 	for(var i= 0;i<tmpStructJson.length;i++){
			// 		if(tmpStructJson[i].value == parmeterType){
			// 			jsonObj.parmeterType = 'struct';
			// 			structJson = tmpStructJson[i];
			// 			jsonObj.varJson  = structJson;
			// 			break;
			// 		}
			// 	}
			// }
			json.push(jsonObj);
		});
		return json;
	}
    //
    // function entryParmeterUnit(event){
    //
	 //   var opt= $(event.target).val();
    //
	 //   if(opt=="array"){
	 //       $(event.target).closest("tr").find("input[name=parmeterName]").closest("td").append("<label id=\"asterisk\" style=\"margin-top:-25px;margin-left:2px;\">*</label>");
    //    }else {
    //        $(event.target).closest("tr").find("td").find("#asterisk").remove("#asterisk");
    //    }
    //     }
    // function outParmeterUnit(event){
    //     var inp=$(event.target).closest("tr").find("input[name=parmeterName]").val();
    //     var opt= $(event.target).val();
    //     if(opt=="array"){
    //         $(event.target).closest("tr").find("input[name=parmeterName]").closest("td").append("<label id=\"asterisk\" style=\"margin-top:-25px;margin-left:2px;\">*</label>");
    //     }else {
    //         $(event.target).closest("tr").find("td").find("#asterisk").remove("#asterisk");
    //     }
    // }


    // function entryParmeterUnit(event){
    //    // var opt= $(event.target).val();
    //
    //
    //
    //
    // 	if(checked==false||checked==undefined){
    //
    //        var checked=$(event.target).prop("checked",true);
    //        alert(checked);
    // 	}else {
    //        // var checked=$(event.target).removeAttr("checked");
    //        var checked=$(event.target).prop("checked",false);
    // 	}
    // }
    //     function cheeseAllorNot(event){
    //      // $(event.target).closest("tr").find("input[name=array]").prop("checked",true);
    //     var choose=$(event.target).closest("tr").find("input[name=array]").prop('checked');
    //     if(choose){
    //
    //
    //         $(event.target).closest("tr").find("input[name=array]").prop('checked',true);
    //         choose=$(event.target).closest("tr").find("input[name=array]").prop('checked')
    //
    //     }else {
    //
    //         // $(event.target).closest("tr").find("input[name=array]").removeProp('checked');
    //         choose=$(event.target).closest("tr").find("input[name=array]").prop('checked',false)
    //     }
    //     }


    function addBtnIpt(event){
		var tr = document.getElementsByTagName('tbody')[0].getElementsByTagName('tr');
        var $tr = $(event.target).parent().parent();
        var $tmp = $tr.clone(true);
        $tmp.find("input[name=array]").prop("checked",false);
        $tmp.find("input").val("");
        $tmp.find("input[name=parmeterName]").val("inParam"+(tr.length+1));
        //$tmp.find("select").val("");
        $tmp.find("#asterisk").remove("#asterisk");
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
        $tmp.find("input[name=parmeterName]").val("oParam"+(tr.length+1));
        //$tmp.find("select").val("");
        $tmp.find("#asterisk").remove("#asterisk");
        if(!$tmp.find("i").hasClass("trash")){
            $tmp.find(".addBtn").after('<i class="icon icon-trash icon-2x iptVar trash"></i>');
        }
        $(event.target).closest("tr").after($tmp);
    }


        function savemodel(){
            //入參
            var iptVarJson =  getJson("#iptVariableTb");
            //出參
            var optVarJson =  getJson("#optVariableTb");
            //结构体定义
            //var structVarJson = tmpStructJson;

            //console.log(iptVarJson);
            //console.log(optVarJson);


            var id=$("#id").val();
            var modelCategoryid=$("#modelCategoryid").val();
            var modelName=$("#modelName").val();
            var modelVersion=$("#modelVersion").val();
            var modelContent=$("#modelContent").val();


            /* if(dllPath == ""){
                tipMsg("请先上传文件，再进行保存");
                return;
            } */
            if(modelName==""){
                // tipMsg("模型名称不能为空");
                return;
            }
            if(modelName.trim().length!=0){
                // /^[\u4e00=\u9fff]+$/;
                var reg=/([\u4E00-\u9FA5])+/;
                if(reg.test(modelName.trim().toString())){
                    // tipMsg("模型名称不能包含中文");
                    return;
                }
            }
            var param = {id:id,
                modelCategoryid:modelCategoryid,
                modelName:modelName,
                modelVersion:modelVersion,
                modelContent:modelContent};//你原来的传递参数

            //然后把这两个 搞成字符串传递给后台
            //后台接受字符串 解析成JSON操作
            //param.iptVarJson = JSON.stringify(iptVarJson);
            //param.optVarJson = JSON.stringify(optVarJson);
            param.iptVarJson = iptVarJson;
            param.optVarJson = optVarJson;
            //param.structVarJson=structVarJson;
            console.log(JSON.stringify(param));
            console.log( "临时json:"+ JSON.stringify(tmpStructJson));
            // var i = openProgressExt("保存中...");
            //return;
            $.ajax({
                url:ctx + '/platform/autoSaveModelInfo',
                data:{reqJson:JSON.stringify(param)},
                dataType:'JSON',
                success:function(data){
                    var id=$("#id").val();
                    if (id=="") {
                        if (data.status == 1) {
                            var id = $("#id").val(data.msg.substr(4, data.msg.length));
                        }
                    }

                    // closeProgressExt(i);
                    //console.log(data);
                    // if(data.status==1){
                    //     tipMsg(data.msg);
                    // }else{
                    //     tipMsg(data.msg);
                    // }
                }
            });
        }

	</script>

</body>

</html>