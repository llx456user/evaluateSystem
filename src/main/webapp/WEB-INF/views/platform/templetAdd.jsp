<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>评估系统-模板管理添加</title>
	<script type="text/javascript" src="<c:url value='/resources/js/platform/templetAdd.js?version=${jsversion}'/>"></script>
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
		<!-- 新建指标 -->
        <div class="panel" style="margin-bottom: 5px;">
	        <div class="panel-heading">
			<strong>模板信息</strong>
		</div>
	    <div class="panel-body">
			<form>
			  <div class="form-group">
			    <label for="templetName">模板名称</label>
			    <input type="text" class="form-control" id="templetName" placeholder="" value="${info.templetName }">
			  </div>
			  <div class="form-group">
			    <label for="templetContent">模板内容</label>
			    <textarea class="form-control" id="templetContent" placeholder="">${info.templetContent }</textarea>
			  </div>
			  <input type="hidden" id="categoryId" value="${categoryId }"/>
			  <input type="hidden" id="templetId" value="${id }"/>
			<button class="btn btn-primary" type="button" onclick="save();">保存</button>
			<button class="btn btn-primary" type="button" onclick="back();">关闭</button>
			 </form>
		</div>
	</div>
    

	<!-- 多模型关联设计 -->
	<div class="panel">
		<div class="panel-heading">
			模型关联设计
		</div> 
		<div class="panel-body">
			<div style="width: 100%;position: relative;border:1px solid #ddd;">
				<div style="float:left;width:103px;height:680px;border:1px solid #ddd;text-align:center;background:#ddd;">
					<div class="btn-group">
						<div class="indexBtnImg dropdown-toggle" data-toggle="dropdown" id="modelBtn" style="margin-right:6px;">
							<strong>模型</strong>
						</div>
						<%--<ul class="dropdown-menu" role="menu" id="modelDropdown">--%>
							<%--<li class="dropdown-submenu"><a href="###" data-id="1">模型系列</a>--%>
						        <ul class="dropdown-menu">
									<c:forEach items="${mcList }" var="mc">
										<li class="dropdown-submenu" cateid="${mc.id }" >
											<a href="#" id="model_test4" style="width:200px;" cateid="${mc.id }"  onclick="slemodel('${mc.id }')">
											  ${mc.categoryName } </a>


										<ul class="dropdown-menu" id="model_test1">
										<%--<li>--%>
											<%--<a href="###" id="model_test1" type="selectModel" draggable="true" ondragstart="drag(event)" class="dragClass"></a>--%>
										<%--</li>--%>
										</ul>
									</li>
									</c:forEach>

						        	<%--<li><a href="###" id="model_test1" type="selectModel" draggable="true" ondragstart="drag(event)" class="dragClass">测试1</a></li>--%>
						        </ul>
						    <%--</li>--%>
					  	<%--</ul>--%>
					</div>
					<div id="myPaletteDiv" style="height: 180px; margin-top:10px;border: solid 0px gray;display: none; "></div>
					
				</div>
				<div id="show" style="height:680px; vertical-align: top; padding: 5px; width:90%; margin-left: 110px;	" ondrop="drop(event)" ondragover="allowDrop(event)">
				</div>
				
			</div>
		</div>
	</div>

</body>
</html>