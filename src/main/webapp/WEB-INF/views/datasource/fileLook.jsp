<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>评估系统-文件管理查看</title>
	<script type="text/javascript" src="<c:url value='/resources/js/datasource/fileList.js?version=${jsversion}'/>"></script>
	<style>
	.active{
		border:1px solid red;
	}
	.panel{
		width: 32%;margin-left:10px;
		height:360px;
		float: left;;
	}
	.detail{
		height:300px;
		width:280px;
		background: #999;
		border-radius:5px;
		padding-top: 5px;
	}
	.detail .base{
		background: #ddd;
		width: 250px;
		height:100px;
		border-radius:5px;
		margin: 0 auto;
		margin-top: 10px;
		padding: 10px;
	}
	.detail .desc{
		text-align: center;
		margin-top: 10px;
	}
	</style>
</head>
<body>
	<div class="container">
		
		<!-- 新建文件 -->
        <div class="panel" style="margin-bottom: 5px; ">
	        <div class="panel-heading">
				<strong>中间指标</strong>
			</div>
		    <div class="panel-body">
			</div>
		</div>
		<div class="panel" style="margin-bottom: 5px; ">
	        <div class="panel-heading">
				<strong>详细信息</strong>
			</div>
		    <div class="panel-body">
			</div>
		</div>
		<div class="panel" style="margin-bottom: 5px; ">
	        <div class="panel-heading">
				<strong>输入文件</strong>
			</div>
		    <div class="panel-body">
		    	<div class="detail">
   					<div class="base">
   						<div>${df.fileName }</div>
   						<div>${df.fileVersion }</div>
   						<div>${df.fileSize }</div>
   					</div>
   					<div class="desc">
   						文件说明：<span>${df.fileContent }</span>
   					</div>
   				</div>
			</div>
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