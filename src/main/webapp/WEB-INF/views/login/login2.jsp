<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>  
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head id="Head1">
    <base href="<%=basePath%>">
    <title>Base系统</title>
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/plugins/jeasyui/themes/default/easyui.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/plugins/jeasyui/themes/icon.css'/>" />
	<script type="text/javascript" src="<c:url value='/resources/plugins/jquery-1.11.3.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/plugins/jeasyui/jquery.easyui.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/resources/js/login/login.js'/>"></script>
    <script>
    if(top!=window){
    	top.location = window.location;
    }
    </script>
    <link href="<c:url value='/resources/css/login/alogin.css'/> " type=text/css rel=stylesheet />

<body>
 <form id="myForm" method="post" action="userInfoContorller/login">
    <div class="Main">
        <ul>
            <li class="top"></li>
            <li class="top2"></li>
            <li class="topA"></li>
            <li class="topB"><span>
                <!-- <img src="./resources/images/login/logo.gif?v1" alt="" style="" /> -->
                <div style="margin: 50px 0 0 100px;"><h1>Demo系统</h1></div>
            </span></li>
            <li class="topC"></li>
            <li class="topD">
                <ul class="login">
                    <li><span class="left">用户名：</span> <span style="left">
                        <input id="name" name="name" type="text"
                         class="txt" />  
                    </span></li>
                    <br/>
                    <br/>
                    <li><span class="left">密 码：</span> <span style="left">
                       <input id="password" type="Password" name="password" class="txt" />  
                    </span></li>
                    
                </ul>
            </li>
            <li class="topE"></li>
            <li class="middle_A"></li>
            <li class="middle_B"></li>
            <li class="middle_C">
            <span class="btn">
               　　　　　　　　　 <input type="submit" value=""  style="background-image:url('./resources/images/login/btnlogin.gif');width: 105px;height: 36px;"/>  
            </span>
            </li>
            <li class="middle_D"></li>
            <li class="bottom_A"></li>
            <li class="bottom_B">
                                              Demo系统
            </li>
        </ul>
    </div>
    </form>
	   
</body>
</html>