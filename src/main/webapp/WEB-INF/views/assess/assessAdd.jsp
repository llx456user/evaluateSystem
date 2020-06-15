<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>评估系统-评估管理添加</title>
	<script type="text/javascript" src="<c:url value='/resources/js/assess/assessAdd.js?version=${jsversion}'/>"></script>
	<style>
	.active{
		border:1px solid red;
	}
	.indexBtnImg{
		border:1px solid #D2F2EA;    
	    border-radius: 5px;
	    background-repeat: no-repeat;
	    width:90px;
	    height:90px;
	    margin:5px 0 0 5px;
	    background-color: #D2F2EA !important;
	}
	.indexBtnImg:hover{
		border-color:gray;
		cursor: pointer;
		background-color: #efefef !important;
	}
	.indexBtnImg strong{
	    display: block;
	    font-weight: bold;
	    color: #343b42;
	    padding-top: 55px;
	}
	#datasetBtn{
		background: url('${ctx}/resources/images/btn-database1.png') no-repeat center top 10px;
	}
	#modelBtn{
		background: url('${ctx}/resources/images/btn-module1.png') no-repeat center top 10px;
	}
	#chartBtn{
		background: url('${ctx}/resources/images/btn-chart1.png') no-repeat center top 10px;
	}
	#gridBtn{
		background: url('${ctx}/resources/images/grid1.png') no-repeat center top 10px;
	}
	#indexBtn{
		background: url('${ctx}/resources/images/indexTemp1.png') no-repeat center top 10px;
	}
	
	.boxF, .boxS, .boxT, .overlay
        {
            width: 50px;
            height: 75px;
            overflow: hidden;
        }
        .boxF, .boxS
        {
            visibility: hidden;
        }
        .boxF
        {
            transform: rotate(120deg);
            float: left;
            margin-left: 10px;
            -ms-transform: rotate(120deg);
            -moz-transform: rotate(120deg);
            -webkit-transform: rotate(120deg);
        }
        .boxS
        {
            transform: rotate(-60deg);
            -ms-transform: rotate(-60deg);
            -moz-transform: rotate(-60deg);
            -webkit-transform: rotate(-60deg);
        }
        .boxT
        {
            transform: rotate(-60deg);
            background: no-repeat 50% center;
            background-size: 125% auto;
            -ms-transform: rotate(-60deg);
            -moz-transform: rotate(-60deg);
            -webkit-transform: rotate(-60deg);
            visibility: visible;
            background-color: #73797D;
            padding-top:30px;
            color:#fff;
        }
        #contants{
        	padding: 3px 0px 8px 8px;
        }
        .boxY{
        	 transform: rotate(-45deg);
            width:45px;
            height:45px;
            -ms-transform: rotate(-45deg);
            -moz-transform: rotate(-45deg);
            -webkit-transform: rotate(-45deg);
            visibility: visible;
            background-color: #73797D;
            padding-top:10px;
            color:#fff;
        }
        .boxZ{
        	 transform: rotate(45deg);
        	  -ms-transform: rotate(45deg);
            -moz-transform: rotate(45deg);
            -webkit-transform: rotate(45deg);
        }
        #if{
        	padding: 20px 0px 0px 20px;
        }
        
        .dropdown-menu{
        	text-align: left;
        }
        
        .showGrid{
        	width:90px;
        	height:90px;
        	background:#ddd;
        	border-radius:5px;
        }
	</style>
</head>
<body>
	<div class="container">
		<!-- 新建评估 -->
        <div class="panel" style="margin-bottom: 5px;">
	        <div class="panel-heading">
			<strong>评估信息</strong>
		</div>
	    <div class="panel-body">
			<form>
			  <div class="form-group">
			    <label for="assessName">评估名称</label>
			    <input type="text" class="form-control" id="assessName" placeholder="" value="${info.assessName }">
			  </div>
			  <div class="form-group">
			    <label for="assessVersion">评估版本</label>
			    <input type="text" class="form-control" id="assessVersion" placeholder="" value="${info.assessVersion }">
			  </div>
			  <div class="form-group">
			    <label for="assessContent">评估备注</label>
			    <textarea class="form-control" id="assessContent" placeholder="">${info.assessContent }</textarea>
			  </div>

				<!--
			  <div class="form-group">
			    <label for="assessParam">评估参数</label>
			    <textarea class="form-control" id="assessParam" placeholder="">${info.assessParam }</textarea>
			  </div>-->
			  <input type="hidden" id="projectId" value="${projectId }"/>
			  <input type="hidden" id="indexId" value="${indexId }"/>
			  <input type="hidden" id="nodeId" value="${nodeId }"/>
			  <input type="hidden" id="assessId" value="${id }"/>
			 <!-- <input type="hidden" name="filePath" id="filePath" />-->
			 </form>
			<!--
			 <form class="form-inline" id="uploadForm" method="post" enctype="multipart/form-data">
			  <div class="form-group">
			    <label for="file">选择文件</label>
			    <input type="file" class="form-control" id="file" name="file" placeholder="">
			    <button class="btn btn-primary" type="button" onclick="upload();" >上传</button>
			  </div>
			 </form>
			 -->
			<button class="btn btn-primary" type="button" onclick="save();">保存</button>
			<button class="btn btn-primary" type="button" onclick="back();">关闭</button>
		</div>
	</div>
    

</body>
</html>