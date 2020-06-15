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

		.header{
			height:85px;
			width:100%;
			border-radius:0px;
		}
		.header h1{
			color:#fff;
			padding:15px 0 0 20px;
		}
		.header .logo{
			height:60px;
			background:#145ccd;
		}
		.header .toolbar{

			/* background:#145ccd;
            height:40px; */
		}
		.header .toolbar .header-icon{
			width:32px;
			height:30px;
			display:inline-block;
			/* background:#f1f1f1; */
			text-align:center;
			border-radius:3px 3px 0 0;
			padding: 10px 0 0 0;
		}
		/* 转移到主题里了
        .header .toolbar .header-icon:hover{
            background: #039BE5;
        }
         .header .toolbar span.firstPage{
            margin-left:20px;
            margin-top:10px;
            background: #039BE5;
            padding: 0px 0 0 0;
        } */

		span.backPage.header-icon {
			cursor: pointer;
			margin-left: 5px;
		}
		span.backPage i.icon.icon-arrow-left.icon-2x {
			font-size: 24px;
		}
		span.refreshPage.header-icon {
			cursor: pointer;
			margin-left: 5px;
		}
		span.refreshPage i.icon.icon-refresh.icon-2x {
			font-size: 24px;
		}

		.header .toolbar span.right{
			float:right;
			width:250px;
			height:40px;
			margin-right:20px;
		}

		.header .toolbar span.right .dropdown{
			margin-top:10px;
			margin-left:40px;
		}
		.header .toolbar span.right .menu{
			margin-top:10px;
		}

		#menu{
			width:0px;
			float:right;
			/*width:150px;*/
			margin-right:5px;
			overflow:hidden;
		}

		#dropdown ul,#menu ul{
			width:150px;
			margin:0;
			padding:0;
		}
		/*
        转移到主题里了
        */
		.header .toolbar .firstPage:hover,.header .toolbar .dropdown:hover,.header .toolbar .menu:hover{
			cursor:pointer;
		}
		.brand-title{
			font-size: 20px;
			padding-left: 20px;
		}

		.theme-box{
			display: inline-block;;
		}
		.theme-box .theme{
			width:30px;
			height:20px;
			display: inline-block;;
			border-radius:3px;
			margin-bottom:-3px;
			cursor:pointer;
		}
		.theme-box .theme:nth-child(1){
			background: #0d0e44;
		}
		.theme-box .theme:nth-child(2){
			background: #3280fc;
		}
		.theme-box .theme:nth-child(3){
			background: #4caf50;
		}
		.theme-box .theme:nth-child(4){
			background: #333;
		}
		.theme-active{
			border: 1px solid #fff;
		}
		.theme-active i{
			margin-left: 8px;
		}

		.nav-tabs>li.active>a, .nav-tabs>li.active>a:focus, .nav-tabs>li.active>a:hover {
			font-weight: bold;
			background-color: #3280fc;
			border: 1px solid #ddd;
			border-bottom-color: transparent;
            color: white;

		}
		.header .toolbar .backPage.header-icon,.header .toolbar .refreshPage.header-icon {
		    padding: 3px 0 0 0;
		}
	</style>
</head>
<body>

<header id="header" class="bg-primary">
	<div class="navbar navbar-inverse navbar-fixed-top header" id="navbar" role="banner" >
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
					<span class="brand-title">分析与评估系统</span> &nbsp;
					<small class="zui-version"></small>
				</a>


			</div>

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

		<div class="toolbar">

			<span class="firstPage header-icon"><i class="icon icon-home icon-2x" title="打开首页"></i></span>
			<span class="backPage header-icon"><i class="icon icon-arrow-left icon-2x" title="返回"></i></span>
			<span class="refreshPage header-icon"><i class="icon icon-refresh icon-2x" title="刷新"></i></span>
			<span class="right" >
					<span class="theme-box" title="选择主题颜色">
						<span class="theme theme-active" theme="_blue.css"></span>
						<span class="theme" theme="blue.css"></span>
						<span class="theme" theme="green.css"></span>
						<span class="theme" theme="black.css"></span>
					</span>
					<span  title="关闭" class="dropdown header-icon"><i class="icon icon-double-angle-down"></i></span>
					<span  title="主菜单" class="menu header-icon"><i class="icon icon-th-large"></i></span>
				</span>
		</div>

		<div id="menu" >
			<ul>
				<c:forEach items="${menus }" var="menu" varStatus="sta">
                    <c:choose>
                     <c:when test="${fn:length(menu.subResources) > 0 }">
                         <li class="hasChildren">${menu.resourcename }

                    </c:when>
                        <c:otherwise>
                            <li name='${menu.resourcename }' url='${menu.resourceurl}'>${menu.resourcename }</li>
                        </c:otherwise>
                    </c:choose>
			<%--<li class="hasChildren">${menu.resourcename }--%>
						<c:if test="${fn:length(menu.subResources) > 0 }">
							<!-- <span class="caret"></span> -->
							<i class="icon icon-caret-right" style="color:#fff;"></i>

							<ul class="children">
								<c:forEach items="${menu.subResources }" var="childmenu">
									<li name='${childmenu.resourcename }' url='${childmenu.resourceurl}'>${childmenu.resourcename }</li>
								</c:forEach>
							</ul>
						</c:if>
					</li>

				</c:forEach>
			</ul>
		</div>

		<div id="dropdown" >
			<ul>
				<li>关闭当前</li>
				<li>全部关闭</li>
				<li>关闭其他</li>
				<li>关闭左侧</li>
				<li>关闭右侧</li>
			</ul>
		</div>

	</div>

</header>

<!-- 远程内容加载图标 -->
<div class="text-muted loader loading"><i class="icon icon-spin icon-spinner-indicator"></i> 加载中...</div>

<div class="container-fluid" style="margin-top: 60px;position: fixed;width: 100%;height: 100%;"><!--  -->
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
		<div id="tabs" class="col-lg-10 col-sm-10 embed-responsive" style="overflow: auto;height: 100%;width: 100%;margin-top: 10px;color: #d7bbff">
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
<script>
    var hide = true;
    $(function(){

        //  ----------- 鍒锋柊棣栭〉 ------------------------
        $(".firstPage").click(function(){
            //window.location.reload();
            tabsPlugs.addTab("欢迎您",null);
        });
        $(".backPage").click(function(){
            $(".tab-content .tab-pane.active").find("iframe").get(0).contentWindow.history.go(-1);
        });
        $(".refreshPage").click(function(){
            $(".tab-content .tab-pane.active").find("iframe").get(0).contentWindow.location.reload(true);
        });


        // ---------  鍙充晶婊戝姩鑿滃崟 ---------------------
        $(".menu").click(function(e){
            if($("#menu").css('width') == '150px'){
                closeMenu();
            }else{
                $('#menu').animate({width:150},"fast");
                closeDropdown();
            }

            $(document).one("click", function(){
                closeMenu();
            });
            e.stopPropagation();
        });
        $("#menu").on("click", function(e){

            e.stopPropagation();
        });


        $("#menu").on('click','.hasChildren',function(){

            $(this).children("ul").show();

        });

        $("#menu ul li").click(function(){
            $(this).addClass("active");
            $(this).siblings().find(".children").hide();
            $(this).siblings().removeClass("active");
            $(this).siblings().find(".children").find("li").removeClass("active");

            if(!$(this).hasClass("hasChildren")){
                var name = $(this).attr('name');
                var url = $(this).attr('url');
                setTimeout(function(){
                    closeMenu();

                    //TODO  璋冪敤璺宠浆浜嬩欢
                    jump(name,url);
                },100);
            }
        });

        //   -------  涓嬫媺鍏抽棴鑿滃崟 -------------------
        $(".dropdown").click(function(e){
            //鍏抽棴鑿滃崟
            closeMenu();
            //灞曠ず涓嬫媺
            $("#dropdown").slideDown('fast');
            $(document).one("click", function(){
                $('#dropdown').slideUp('fast');
            });
            e.stopPropagation();
        });
        $("#dropdown").on("click", function(e){
            e.stopPropagation();
        });

        $("#dropdown ul li").click(function(){
            var index = $("#dropdown ul li").index($(this));
            setTimeout(function(){
                $("#dropdown").fadeOut('fast');
                //TODO 璋冪敤璺宠浆浜嬩欢

                closeTabs(index);
            },100);
        });

        function closeMenu(){
            $(".children").fadeOut('fast');
            $('#menu').animate({width:0},"fast");
            $("#menu ul li").removeClass("active");
        }
        function closeDropdown(){
            $('#dropdown').slideUp('fast');
        }
        function closeTabs(index){
            var _self = tabsPlugs;
            var $curelement = $(".nav-tabs li.active a");
            if(0 == index){

                if($('.nav-tabs li').index($curelement.parent()) != 0) _self.closeTab($curelement.prop('title'));
            }
            else if(1 == index){
                $(".nav-tabs").children().each(function(){
                    if($('.nav-tabs li').index($(this))!= 0){
                        _self.closeTab($(this).find(".tabTitle").prop("title"));
                    }
                });
            }

            else if(2 == index){

                $curelement.parent().siblings().each(function(){
                    if($('.nav-tabs li').index($(this))!= 0){
                        _self.closeTab($(this).find(".tabTitle").prop("title"));
                    }
                });
            }
            else if(3 == index){

                $curelement.parent().prevAll().each(function(){
                    if($('.nav-tabs li').index($(this))!= 0){
                        _self.closeTab($(this).find(".tabTitle").prop("title"));
                    }
                });
            }
            else if(4 == index){

                $curelement.parent().nextAll().each(function(){
                    if($('.nav-tabs li').index($(this))!= 0){
                        _self.closeTab($(this).find(".tabTitle").prop("title"));
                    }
                });
            }
        }


        $(".dropdown").mouseleave(function(){
            // console.log(eventPositiont("dropdown"));
            // if(!eventPositiont("dropdown")){
			//
            //     $("#dropdown").hide();
            // }
        });

        $("#dropdown").mouseleave(function(){
            $(this).hide();
        });

        $("#menu").mouseleave(function(){
            closeMenu();
        });

    });

    function doAjaxLogin (){
    	alert("session超时，请重新登录!");
    	window.location.reload();
    }

</script>

</html>