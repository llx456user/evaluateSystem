<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
	<title>评估系统-模型查看</title>
<%-- 	<script type="text/javascript" src="<c:url value='/resources/js/platform/modelList.js?version=${jsversion}'/>"></script>
 --%>	
	<style>
	.active{
		border:1px solid red;
	}
	/*.addBtn{*/
		/*color:#3280fc;*/
	/*}*/
	/*.addBtn:hover{*/
		/*cursor:pointer;*/
		/*color:#1970fc;*/
	/*}*/

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
			  <div class="form-group">
			    <label for="fileName">模型名称</label>
			    <input type="text" class="form-control" id="modelName" name="modelName" placeholder="" value="${modelInfo.modelName }" readonly="readonly">
			  </div>
			  <div class="form-group">
			    <label for="fileVersion">模型版本</label>
			    <input type="text" class="form-control" id="modelVersion" name="modelVersion" placeholder="" value="${modelInfo.modelVersion }" readonly="readonly">
			  </div>
			  <div class="form-group">
			    <label for="fileContent">模型内容</label>
			    <textarea class="form-control" id="modelContent" name="modelContent" placeholder="" readonly="readonly">${modelInfo.modelContent }</textarea>
			  </div>
			  <div class="form-group">
			    <label for="fileContent">dll名称</label>
			    <input type="text" class="form-control" id="dllName" name="dllName" placeholder="" value="${modelInfo.dllname }" readonly="readonly">
			    
			  </div>
			  
			  <!--  改造一下保存事件  添加以下内容 start -->
			  <div class="form-group">
			    <label for="">入参</label>
			    <table class="table table-borderless">
				<thead>
					<tr>
					  <th>参数变量名</th>
					  <th>参数类型</th>
					  <th>参数单位</th>
					  <th>参数含义</th>
					</tr>
				  </thead>
				  <tbody  id="iptVariableTb">
				  <c:forEach items="${inparList }" var="in">
				   <tr>
					  <td>
						  <input type="text" class="form-control" name="parmeterName" value="${in.parmeterName }" readonly="readonly">
						  <%--<c:if test="${in.parmeterUnit == 'array'}"><label id="asterisk" style="margin-top:-25px;margin-left:2px;">*</label></c:if>--%>
					  </td>
					  <td>
						<input type="text" class="form-control" name="parmeterType" value="${in.parmeterType }" readonly="readonly">
					  </td>
					  <td><input type="text" class="form-control" name="parmeterUnit" value="${in.parmeterUnit }" readonly="readonly"></td>
					  <td><input type="text" class="form-control" name="parmeterUnitEx" value="${in.parmeterUnitEx }" readonly="readonly"></td>
					  <%--<td><c:if test="${in.inoutType!=2 }"><i class="icon icon-plus-sign icon-2x addBtn iptVar"></i></c:if></td>--%>
					</tr>
				  </c:forEach>
					
				  </tbody>
				</table>
			  </div>
			  <div class="form-group">
			    <label for="fileContent">出参</label>
			    <table class="table table-borderless">
				<thead>
					<tr>
					  <th>参数变量名</th>
					  <th>参数类型</th>
					  <th>参数单位</th>
					  <th>参数含义</th>
					</tr>
				  </thead>
				  <tbody id="optVariableTb">
				  <c:forEach items="${outparList }" var="out">
				   <tr>
					  <td>
						  <input type="text" class="form-control" name="parmeterName" value="${out.parmeterName }" readonly="readonly">
					  </td>
					  <td>
						<input type="text" class="form-control" name="parmeterType" value="${out.parmeterType }" readonly="readonly"> 
					  </td>
					  <td><input type="text" class="form-control" name="parmeterUnit" value="${out.parmeterUnit }" readonly="readonly"></td>
					  <td><input type="text" class="form-control" name="parmeterUnitEx" value="${out.parmeterUnitEx }" readonly="readonly"></td>
					  <%--<td><c:if test="${in.inoutType!=2 }"><i class="icon icon-plus-sign icon-2x addBtn iptVar"></i></c:if></td>--%>
					</tr>
				  </c:forEach>
				  </tbody>
				</table>
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