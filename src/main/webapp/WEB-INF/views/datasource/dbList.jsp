<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>
<html>
<head>
	<title>评估系统-数据库管理查询</title>
	<script type="text/javascript" src="<c:url value='/resources/js/datasource/dbList.js?version=${jsversion}'/>"></script>
	<style type="text/css">
	.datasource{
		text-align:center;
	}
	.btn-mysql {
	    background-color: transparent;
	    border: 0px;
	    width: 141px;
	    height: 141px;
	    background-image: url('${ctx}/resources/images/mysql1.png');
	}
	.btn-oracle {
	    width: 141px;
	    height: 141px;
	    background-color: transparent;
	    background-image: url('${ctx}/resources/images/oracle1.png');
	}
	.btn-sqlserver {
	    width: 141px;
	    height: 141px;
	    background-color: transparent;
	    background-image: url('${ctx}/resources/images/sqlserver1.png');
	}
	
	.btn-mysql:hover {
		background-image: url('${ctx}/resources/images/dbground1.png');
	}
	.btn-oracle:hover {
		background-image: url('${ctx}/resources/images/qidai2.png');
	}
	.btn-sqlserver:hover {
		background-image: url('${ctx}/resources/images/qidai2.png');
	}
	
	.popover{
		position:relative;
		display: inline-block;
		margin-right: 10px;
	}
	#datasourceList .popover:hover{
		border-color: #999;
	}
	h3.popover-title{
		font-weight: bold;
	}
	h3.popover-title i{
		cursor:pointer;
		margin-left: 10px;
	}
	h3.popover-title i:hover{
		color: #999;
	}
	.popover-content{
		width: 210px;
	}
	.popover-content p{
		width:170px;
		overflow:hidden; 
		text-overflow:ellipsis; 
		white-space:nowrap; 
		word-break:keep-all; 
	}
	</style>
</head>
<body>
	<div class="container">

	<!-- 数据源信息 -->
	<div class="panel">
		<div class="panel-heading">
			数据库（3）
		</div> 
		<div class="panel-body datasource">
			<button class="btn m-b-xs w-xs btn-info  btn-mysql " title="新建数据连接">
	            <span style="font-size:18px;"><i class="m"></i></span>
	        </button>
			<button class="btn m-b-xs w-xs btn-info btn-circular btn-oracle " style="border: none;margin-left: 25px" title="新建数据连接">
	            <span style="font-size:18px;"><i class="m"></i></span>
	        </button>
			<button class="btn m-b-xs w-xs  btn-circular btn-sqlserver " style="border: none;margin-left: 25px" title="新建数据连接">
            	<span style="font-size:18px;"><i class="m"></i></span>
	        </button>
		</div>
	</div>
	<!-- 数据库信息 -->
	<div class="panel">
		<div class="panel-heading">
			数据源(${total })
		</div> 
		<div class="panel-body" id="datasourceList">
			<c:forEach items="${rows }" var="e">
			<div class="popover left col-md-4 col-xs-6 col-sm-6 col-lg-2">
			  <input type="hidden" name="config" value='${e.config }'/>
			  <input type="hidden" name="sourceType" value='${e.sourceType }'/>
			  <input type="hidden" name="sourceName" value='${e.sourceName }'/>
			  <input type="hidden" name="dbId" value='${e.id }'/>
			  
			  <h3 class="popover-title">${e.sourceName }<i class="icon icon-trash pull-right" title="删除" onclick="del('${e.id }');"></i><i class="icon icon-edit pull-right" title="修改" onclick="toEdit(this);"></i></h3>
			  <div class="popover-content">
			    
			    <p>${e.url }</p>
			    <p></p>
			    <h4 style="float: left;"> <small>${e.updateTimeStr }</small></h4>
			    <img src="${ctx}/resources/images/mysql1.png" style="width:35px;height:35px;float: right;margin-bottom: 10px;"/>
			  </div>
			</div>
			</c:forEach>
			
		</div>
	</div>
	
	<!-- 弹窗 -->
	<div class="modal fade" id="addDbPage">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
	        <h4 class="modal-title">新建数据库连接</h4>
	      </div>
	      <div class="modal-body">
	        <p>
	        <form class="form-horizontal" id="form">
	          <input type="hidden" id="dbId" name="dbId"/>
			  <div class="form-group">
			    <label for="sourceName" class="col-sm-2">名称</label>
			    <div class="col-md-6 col-sm-10">
			      <input type="text" class="form-control" id="sourceName" name="sourceName" placeholder="请输入数据源名称" required="true" label="数据源" >
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="sourceType" class="col-sm-2">类型</label>
			    <div class="col-md-6 col-sm-10">
			      <input type="text" class="form-control" id="sourceType" name="sourceType" placeholder="例：jdbc" required="true" label="类型" >
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="server" class="col-sm-2">服务器</label>
			    <div class="col-md-6 col-sm-10">
			      <input type="text" class="form-control" id="server" name="server" placeholder="例：com.mysql.jdbc.Driver" required="true" label="服务器">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="url" class="col-sm-2">URL</label>
			    <div class="col-md-6 col-sm-10">
			      <input type="text" class="form-control" id="url" name="url" placeholder="例：jdbc:mysql://hostname:port/db" required="true" label="URL">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="username" class="col-sm-2">用户名</label>
			    <div class="col-md-6 col-sm-10">
			      <input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名" required="true" label="用户名">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="password" class="col-sm-2">密码</label>
			    <div class="col-md-6 col-sm-10">
			      <input type="text" class="form-control" id="password" name="password" placeholder="请输入密码" required="true" label="密码">
			    </div>
			  </div>
			</form>
	        </p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-success" onclick="test();">测试</button>
	        <button type="button" class="btn btn-primary" onclick="save();">保存</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<div id="hiddenDataSourceTpl" style="display: none;">
		<div class="popover left">
		  <h3 class="popover-title">{0}</h3>
		  <div class="popover-content">
		    
		    <p>{1}</p>
		    <p></p>
		    <h4 style="float: left;"> <small>{2}</small></h4>
		    <img src="${ctx}/resources/images/mysql1.png" style="width:35px;height:35px;float: right;margin-bottom: 10px;"/>
		  </div>
		</div>
	</div>
	<script type="text/javascript">
	</script>
</body>
</html>