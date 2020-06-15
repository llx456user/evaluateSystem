<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="jsversion" value="<%=(new java.util.Date()).toLocaleString()%>" />

<link rel="stylesheet" type="text/css" href="<c:url value='/resources/dist/css/zui.2018.css?v=1'/>" />
<link rel="stylesheet" href="${ctx }/resources/dist/lib/datetimepicker/datetimepicker.css">
<link rel="stylesheet" href="${ctx }/resources/dist/lib/datatable/zui.datatable.css">
<link rel="stylesheet" href="${ctx }/resources/dist/lib/bootbox/bootbox.css">
<link rel="stylesheet" href="${ctx}/resources/dist/theme/_blue.css" id="zuiTheme">
<%-- <link rel="stylesheet" href="${ctx}/resources/dist/css/app.css"> --%>

<%-- <link rel="stylesheet" href="${ctx }/resources/plugins/pagination/mricode.pagination.css"> --%>
<link rel="stylesheet" href="${ctx }/resources/plugins/jquerypage/css/jquery.page.css">

<script type="text/javascript" src="<c:url value='/resources/plugins/jquery-1.11.3.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/index/public.js'/>"></script>
<!-- zui -->
<script type="text/javascript" src="<c:url value='/resources/dist/js/zui.2018.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/dist/js/searchbox.js'/>"></script>
<script src="${ctx }/resources/dist/lib/bootbox/bootbox.min.js"></script>
<script src="${ctx }/resources/dist/lib/datatable/zui.datatable.min.js"></script>
<script src="${ctx }/resources/dist/lib/datetimepicker/datetimepicker.js"></script>
<!-- plugs -->
<script type="text/javascript" src="<c:url value='/resources/plugins/layer/layer.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/plugins/My97DatePicker/WdatePicker.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/plugins/form/jquery.form.js'/>"></script>
<script src="${ctx}/resources/plugins/jqueryValidate/jquery.validate.min.js"></script>
<script type="text/javascript" src="<c:url value='/resources/plugins/jquery.serializejson.js'/>"></script>
<%-- <script src="${ctx}/resources/plugins/pagination/mricode.pagination.js"></script> --%>
<script type="text/javascript" src="<c:url value='/resources/plugins/jquerypage/js/jquery.page.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/plugins/yxddatagrid/js/yxddatagrid.js'/>"></script>
<%-- <script type="text/javascript" src="<c:url value='/resources/plugins/yxddatagrid/js/Datagrid.min.js'/>"></script> --%>
<!-- 自定义 -->
<script type="text/javascript" src="<c:url value='/resources/js/comm/public.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/comm/operCookies.js'/>"></script>
<script type="text/javascript" src="${ctx }/resources/js/comm/common.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/comm/theme.js"></script>
<script src="${ctx}/resources/js/index/yxdtabs.js"></script>
<%-- <script src="${ctx}/resources/js/comm/dataTableUtil.js"></script>
<script src="${ctx}/resources/js/comm/global.js"></script> --%>
<script type="text/javascript" src="<c:url value='/resources/plugins/go.js'/>"></script>

<!-- base -->
<script>
var ctx="${ctx}";
var currentUser = "${user.username}"; 
</script>	
<style type="text/css">
.easyui-my97{border-radius: 5px;border: 1px solid #95b8e7;height: 22px; width: 130px;vertical-align: middle;white-space: nowrap;}
.container{margin-top: 5px;}
.datetimepicker th.switch,.datetimepicker table tr td span{font-size: 14px;}
</style>
<!--[if lt IE 9]>
  <script src="${ctx}/resources/dist/lib/ieonly/html5shiv.js"></script>
  <script src="${ctx}/resources/dist/lib/ieonly/respond.js"></script>
  <script src="${ctx}/resources/dist/lib/ieonly/excanvas.js"></script>
<![endif]-->
<!-- uploader -->
<link rel="stylesheet" href="${ctx}/resources/dist/lib/uploader/zui.uploader.min.css">
<link rel="stylesheet" href="${ctx}/resources/dist/lib/uploader/zui.uploader.css">
<script src="${ctx}/resources/dist/lib/uploader/zui.uploader.min.js"></script>
<script src="${ctx}/resources/dist/lib/uploader/zui.uploader.js"></script>

