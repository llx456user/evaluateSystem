<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<meta http-equiv="Pragma" content="no-cache"> 
<meta http-equiv="Cache-Control" content="no-cache"> 
<meta http-equiv="Expires" content="0"> 
<base href="<%=basePath%>">
<title>分析与评估系统</title>
<link href="<c:url value='/resources/css/login/login.css'/> " type=text/css rel=stylesheet />
<script type="text/javascript" src="<c:url value='/resources/plugins/jquery-1.11.3.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/login/login.js'/>"></script>
<script>
if(top!=window){
	top.location = window.location;
}
</script>
</head>

<body>
<div class="login_box">
     <!-- <div class="login_l_img">
          <img src="${ctx }/resources/images/login-img.png" />
      </div>-->
      <div class="login">
          <!--div class="login_logo"><a href="#"><img src="${ctx }/resources/images/login_logo.png" /></a></div>-->
          <div class="login_name">
              <p>分析与评估系统</p>
          </div>
          <form method="post" action="userInfoContorller/login">
          	  <font color="red">${error }</font>
              <input name="name" type="text"  value="用户名" onfocus="this.value=''" onblur="if(this.value==''){this.value='用户名'}">
              <span id="password_text" onclick="this.style.display='none';document.getElementById('password').style.display='block';document.getElementById('password').focus().select();" >密码</span>
			  <input name="password" type="password" id="password" style="display:none;" onblur="if(this.value==''){document.getElementById('password_text').style.display='block';this.style.display='none'};"/>
              <input value="登录" style="width:100%;" type="submit">
          </form>
          <div class="login_foot">
              <p>版本信息:1.A.00</p>
              <p>北京电子工程总体研究所</p>
          </div>
      </div>
</div>
</body>
</html>
