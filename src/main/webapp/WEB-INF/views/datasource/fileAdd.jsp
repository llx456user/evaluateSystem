<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>评估系统-文件管理添加</title>
	<script type="text/javascript" src="<c:url value='/resources/js/datasource/fileList.js?version=${jsversion}'/>"></script>
	<style>
	.active{
		border:1px solid red;
	}
	
	</style>
</head>
<body>
	<div class="container">
		
		<!-- 新建文件 -->
        <div class="panel" style="margin-bottom: 5px;">
	        <div class="panel-heading">
			<strong>文件信息</strong>
		</div>
	    <div class="panel-body">
			<form id="fileEditForm" method="post">
			  <input type="hidden" name="id" id="id" value="${df.id }"/>
			  <input type="hidden" name="categoryId" id="categoryId" value="${categoryId }"/>
			  <div class="form-group">
			    <label for="fileName">文件名称</label>
			    <input type="text" class="form-control" id="fileName" name="fileName" placeholder="" readonly="readonly" value="${df.fileName }">
			  </div>
			  <div class="form-group">
			    <label for="fileVersion">文件版本</label>
			    <input type="text" class="form-control" id="fileVersion" name="fileVersion" placeholder="" value="${df.fileVersion }" required="true">
			  </div>
			  <div class="form-group">
			    <label for="fileSize">文件大小</label>
			    <input type="text" class="form-control" id="fileSize" name="fileSize" placeholder="" readonly="readonly" value="${df.fileSize }">
			  </div>
			  <div class="form-group">
			    <label for="fileContent">文件说明</label>
			    <textarea class="form-control" id="fileContent" name="fileContent" placeholder="">${df.fileContent }</textarea>
			  </div>
			    <input type="hidden" name="filePath" id="filePath" value="${df.filePath }"/>
			 </form>
			 <form class="form-inline" id="uploadForm" method="post" enctype="multipart/form-data">
			  <div class="form-group">
			    <label for="file">选择文件</label>
			    <input type="file" class="form-control" id="file" name="file" placeholder="">
			    <button class="btn btn-primary" type="button" onclick="upload();" >上传</button>
			  </div>
			 </form>
			 <button class="btn btn-primary" type="button" onclick="save();">保存</button>
			<button class="btn btn-default" type="button" onclick="back();">返回</button>
		</div>
	</div>
	<script type="text/javascript">
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
			         
			   $("#fileName").val(name);
			   $("#fileSize").val(size.toFixed(1) + ' ' + dw);
		});
	})
	</script>
</body>
</html>