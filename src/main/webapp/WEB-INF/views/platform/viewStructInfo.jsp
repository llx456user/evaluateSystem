<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>评估系统-结构体查看</title>
    <style>
        .active{
            border:1px solid red;
        }
        /*.addBtn{*/
            /*color:#3280fc;*/
        /*}*/
        /*.addBtn:hover{*/
            /*cursor:pointer;*/
            /*color:#1970fc;*/
        /*}*/

        .trash{
            color:#3280fc;
            margin-left:10px;
        }
        .trash:hover{
            cursor:pointer;
            color:#1970fc;
        }
    </style>
</head>
<body>
<div class="container">

    <!-- 新建文件 -->
    <div class="panel" style="margin-bottom: 5px;">
        <div class="panel-heading">
            <strong>结构体信息</strong>
        </div>
        <div class="panel-body">
            <form id="fileEditForm" method="post">
                <div class="form-group">
                    <label for="fileName">结构体名称</label>
                    <input type="text" class="form-control" id="structName" name="structName" placeholder="" value="${structDefine.structName }" readonly="readonly">
                </div>
                <div class="form-group">
                    <label for="fileVersion">结构体备注</label>
                    <input type="text" class="form-control" id="structRemark" name="structRemark" placeholder="" value="${structDefine.structRemark }" readonly="readonly">
                </div>


                <!--  改造一下保存事件  添加以下内容 start -->
                <div class="form-group">
                    <label for="">结构体</label>
                    <table class="table table-borderless">
                        <thead>
                        <tr>
                            <th>结构体变量名</th>
                            <th>参数类型</th>
                            <th>参数单位</th>
                            <th>参数含义</th>
                            <%--<th>操作</th>--%>
                        </tr>
                        </thead>
                        <tbody  id="stVariableTb">
                        <c:forEach items="${structList}" var="sc">
                            <tr>
                                <td>
                                    <input type="text" class="form-control" name="parmeterName" value="${sc.parmeterName}" readonly="readonly">
                                    <c:if test="${sc.parmeterUnit == 'array'}"><label id="asterisk" style="margin-top:-25px;margin-left:2px;">*</label></c:if>
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="parmeterType" value="${sc.parmeterType }" readonly="readonly">
                                </td>
                                <td><input type="text" class="form-control" name="parmeterUnit" value="${sc.parmeterUnit }" readonly="readonly"></td>
                                <td><input type="text" class="form-control" name="parmeterUnitEx" value="${sc.parmeterUnitEx }" readonly="readonly"></td>
                                <%--<td><i class="icon icon-plus-sign icon-2x addBtn iptVar"></i></td>--%>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>
                </div>


            </form>

            <button class="btn btn-default" type="button" onclick="back();">返回</button>
        </div>
    </div>

    <script type="text/javascript">

        function back(){

            window.history.go(-1);
        }
    </script>
</body>
</html>