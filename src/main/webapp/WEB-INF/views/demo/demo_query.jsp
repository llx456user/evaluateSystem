<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>
<html >
<head>
	<title>Base系统-Demo查询</title>
	<script type="text/javascript" src="<c:url value='/resources/js/demo/demo_query.js?version=${jsversion}'/>"></script>
</head>
<body>
	<div class="container">
		<!-- 查询条件 -->
        <div class="panel" style="margin-bottom: 5px;">
	        <div class="panel-heading">
			<strong>查询条件</strong>
		</div>
	    <div class="panel-body">
			<form name="queryForm" style="margin:10;text-align: center;" id="queryForm" method="post" action="">
					<table cellpadding="5" border="0" cellspacing="0">
						<colgroup>
						<col width="10%" />
						<col width="12%" />
						<col width="12%" />
						<col width="10%" />
						<col width="13%" />
						<col width="10%" />
						<col width="12%" />
						<col width="10%" />
					</colgroup>
					 	<tr>
							<td align="right">字段一：</td>
							<td align="left"><input class="form-control" type="text" name="field1" id="field1" style="width:130px;"></input></td>
							
						</tr>
						<tr>
							<td   align="right" style="margin-right: 15px;">
								<a href="javascript:void(0)" class="btn btn-primary" icon="icon-search" id="searchBtn">查询</a>
							</td>
						</tr>
						
						
					</table>
				</form>
		</div>
	</div>

	<!-- 表格数据 -->
	<div class="panel">
		<div class="panel-heading">
		订单列表
		</div> 
		<div class="panel-body">
			<table class="datatable table-bordered" id="gridPanel"></table>
		</div>
		<!-- 分页 -->
		<div class="panel-footer yxdPageCode"></div>
		</div>
	</div>
</body>
</html>