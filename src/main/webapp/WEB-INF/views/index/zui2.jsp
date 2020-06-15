<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>
<html>
<head>
<title>分析与评估系统</title>
<style type="text/css">
.zui-navbar-collapse{right:20px;position:absolute;}
</style>
</head>
<body>

	<header id="header" class="bg-primary">
		<div class="navbar navbar-inverse navbar-fixed-top" id="navbar" role="banner" style="">
			<div class="container">
				<div class="navbar-header">
					<button class="navbar-toggle collapsed" type="button" data-toggle="collapse" data-target=".zui-navbar-collapse">
						<span class="sr-only">切换导航</span> 
						<span class="icon-bar"></span> 
						<span class="icon-bar"></span> 
						<span class="icon-bar"></span>
					</button>
					<a href="${ctx}/index" class="navbar-brand">
						<span class="path-zui-36">
							<i class="path-1"></i>
							<i class="path-2"></i>
						</span> 
						<i data-toggle="tooltip" id="compactTogger" data-placement="bottom" class="icon icon-home"></i>
						<span class="brand-title">分析与评估系统</span> &nbsp;
						<small class="zui-version"></small> 
					</a>
					
					
				</div>
				<nav style="float: left;margin-left: 50px;	">
					<c:forEach items="${menus }" var="menu" varStatus="sta">
						<div class="btn-group">
						  <button type="button" class="btn btn-success dropdown-toggle" data-toggle="dropdown">
						   ${menu.resourcename } 
						  <c:if test="${fn:length(menu.subResources) > 0 }">
						  	<span class="caret"></span>
						  </c:if> 
						  
						  </button>
						   <c:if test="${fn:length(menu.subResources) > 0 }">
							   <ul class="dropdown-menu" role="menu">
							    <c:forEach items="${menu.subResources }" var="childmenu">
						  	
									<li><a href="javascript:jump('${childmenu.resourcename }','${childmenu.resourceurl}');">${childmenu.resourcename }</a></li>
								</c:forEach>
							  </ul>
						  	
						  </c:if> 
						  
						</div>
					</c:forEach>
					
				</nav>
				<nav class="collapse navbar-collapse zui-navbar-collapse">
					<ul class="nav navbar-nav navbar-right">
						<li id="navDownloadLink">
							<a href="javascript:void(0)"><span>欢迎您，<c:out value="${sessionScope.user.showname}"></c:out></span></a>
						</li>
						<li><a id="changeBtn" title="修改密码" href="javascript:void(0)"><span>&nbsp;[修改密码]</span></a></li>
						<li><a title="退出" href="${ctx}/logout"><span>&nbsp;[退出]</span></a></li>
					</ul>
				</nav>
			</div>
		</div>

	</header>
    
	<!-- 远程内容加载图标 -->
    <div class="text-muted loader loading"><i class="icon icon-spin icon-spinner-indicator"></i> 加载中...</div>
    
	<div class="container-fluid" style="margin-top: 20px;position: fixed;width: 100%;height: 100%;"><!--  -->
		<div class="row" style="width: 100%;height: 100%;">
			<%-- <div class="col-sm-2 col-lg-2 scrollbar-hover embed-responsive" 
				style="margin-left: -19px;overflow: auto;height: 100%;" id="menuDiv">
				<nav class="menu embed-responsive-item" data-toggle="menu">
					<ul class="nav nav-primary">
						<c:forEach items="${menus }" var="menu" varStatus="sta">
							<li class="nav-parent <c:if test="${sta.index==0 }">show </c:if>">
								<a href="javascript:;"><i class="icon-time"></i> ${menu.resourcename }</a>
								<ul class="nav">
									<c:forEach items="${menu.subResources }" var="childmenu">
										<li><a href="javascript:jump('${childmenu.resourcename }','${childmenu.resourceurl}');">${childmenu.resourcename }</a></li>
									</c:forEach>
								</ul></li>
						</c:forEach>
					</ul>
				</nav>
			</div> --%>
			<!-- style="width:100%;height:90%;"  -->
			<div id="tabs" class="col-lg-10 col-sm-10 embed-responsive" style="overflow: auto;height: 100%;width: 100%;margin-top: 10px;">
				<!-- <iframe class="embed-responsive-item" id="thisframe" src="" scrolling="yes" frameborder="0" style="padding-bottom: 3.5%;width: 100%;height: 100%;"></iframe> -->
				
			</div>
		</div>
		
		
	</div>

	<!-- 修改密码内容 -->
	<div id="changepwdDiv">
		<form id="changepwdFrom">
			<table class="table table-striped table-bordered">
				<tr>
					<td align="right" width="120px">原密码：</td>
					<td><input type="password" class="form-control"
						id="oldpassword" name="oldpassword" /></td>
				</tr>
				<tr>
					<td align="right" width="120px">新密码：</td>
					<td colspan="3"><input type="password" class="form-control"
						id="newpassword" name="newpassword" /></td>
				</tr>
				<tr>
					<td align="right" width="120px">确认密码：</td>
					<td colspan="3"><input type="password" class="form-control"
						id="newpassword_a" name="newpassword_a" /></td>
				</tr>
				<tr>
					<td colspan="4" align="center"><input
						style="margin: 0px 10px;" class="btn btn-default" id="saveBtn"
						name="saveBtn" type="button" value="修改" /> <input
						style="margin: 0px 10px;" class="btn btn-default" id="outBtn"
						name="outBtn" type="button" value="退出" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div class="modal fade" id="ajaxLoginDialog">
	  <div class="modal-dialog modal-sm">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
	        <h4 class="modal-title">请登录系统</h4>
	      </div>
	      <div class="modal-body">
	      <div align="center" border="false">
          		<table cellpadding=3>
          		    <tr>
              			<td>用户名：</td>
              			<td> <input  id="ajaxLoginUsername" class="form-control"/> </td>
            		</tr>
            		<tr>
             				<td>密码：</td>
              			<td><input type="password" id="ajaxLoginPassword" class="form-control"/></td>
            		</tr>
           		 </table>
	        </div>
	        </div>
	        <div class="modal-footer">
	           <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	           <button type="button" class="btn btn-primary" onclick="excuteAjaxLogin()">登录</button>
	       </div>
	    </div>
	  </div>
	</div>
</body>

<script>
	$(document).ready(function() {
		//当单击菜单某个选项时，在右边出现对用的内容
		$('.nav-parent ul li a').click(function() {
			$('.nav-parent ul li').removeClass("active");
			$(this).parent().addClass("active");

		});

		tabsPlugs.init('tabs');
		
		$("#changepwdDiv").hide();
		
		$("#menuDiv").parent().height($(document).height() - 50);
	});
	
	$(window).resize(function(){
		$("#menuDiv").parent().height($(document).height() - 50);
	});

	function jump(title,url) {
		//$("#thisframe").attr('src', ctx + "/" + url);
		tabsPlugs.addTab(title,url);
	}
	
	
	

	$("#changeBtn").on("click", function() {
		 $("#changepwdDiv").show();

		var index = layer.open({
			type : 1,
			shade : 0.3,
			title : '修改密码',
			area : [ '440px', '290px' ],
			shadeClose : true,
			content : $("#changepwdDiv")
		});

		$("#saveBtn").off().on("click", function() {
			var $oldpassword = $("#oldpassword");
			if ($oldpassword.val() === "") {
				layer.tips('旧密码不能为空', '#oldpassword', {
					tipsMore : true,
					tips : [ 2, '#FF0033' ],
					time : 2000
				});
				$oldpassword.focus();
				return false;
			}
			var $newpassword = $("#newpassword");
			if ($newpassword.val() === "") {
				layer.tips('新密码不能为空', '#newpassword', {
					tipsMore : true,
					tips : [ 2, '#FF0033' ],
					time : 2000
				});
				$newpassword.focus();
				return false;
			}
			var $newpassword_a = $("#newpassword_a");
			if ($newpassword_a.val() === "") {
				layer.tips('确认密码不能为空', '#newpassword_a', {
					tipsMore : true,
					tips : [ 2, '#FF0033' ],
					time : 2000
				});
				$newpassword_a.focus();
				return false;
			}
			if ($newpassword_a.val() !== $newpassword.val()) {
				layer.tips('两次输入密码不一致', '#newpassword', {
					tipsMore : true,
					tips : [ 2, '#FF0033' ],
					time : 2000
				});
				$newpassword.focus();
				return false;
			}

			$.ajax({
				type : "POST",
				url : ctx + "/update_password.ajax",
				dataType : "json",
				data : $("#changepwdFrom").serialize(),
				success : function(data) {
					layer.alert(data.msg, {
						icon : data.status,
						closeBtn : 0,
						shift : 3
					//动画类型
					}, function(index2) {
						if (data.status == 1) {
							window.location.href = "${ctx}/logout";
						}
						layer.close(index2)
					});
				}
			});
		});
		$("#outBtn").off().on("click", function() {
			$("#oldpassword").val("");
			$("#newpassword").val("");
			$("#newpassword_a").val("");
			layer.close(index);
		});
	});
</script>
</html>