<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>评估系统-模型添加</title>
    <style>
        .active{
            border:1px solid red;
        }
        .addBtn{
            color:#3280fc;
        }
        .addBtn:hover{
            cursor:pointer;
            color:#1970fc;
        }

        .trash{
            color:#3280fc;
            margin-left:10px;
        }
        .trash:hover{
            cursor:pointer;
            color:#1970fc;
        }
        .uoloadBtn{
            color:#3280fc;
            margin-left:10px;
        }
        .uoloadBtn:hover{
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
            <strong>文件上传数据信息</strong>
        </div>
        <div class="panel-body">
            <form id="fileEditForm" method="post">
                <input type="hidden" name="id" id="id" value="${projectId }"/>
                <input type="hidden" name="mapXmlPath" id="mapXmlPath" value="${mapXmlPath }"/>
                <input type="hidden" name="valueXmlPath" id="valueXmlPath" value="${valueXmlPath }"/>

                <!--  改造一下保存事件  添加以下内容 start -->
                <div class="form-group">
                    <table class="table table-borderless">
                        <thead>
                        <tr>
                            <th>数据名称</th>
                            <th>选择</th>
                        </tr>
                        </thead>
                        <tbody  id="iptVariableTb">
                        <!--判断新建还是删除-->
                        <c:forEach items="${inparList }" var="in" varStatus="stat">
                            <tr>
                                <td style="width: 50%">
                                    <input type="text" class="form-control" name="paramName" value="${in.dataName }"  style=" text-indent: 4px;" readonly><%--background: transparent;--%>
                                </td>
                                <td>
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" name="xmlValueChose" id="${stat.index }" onchange="getChoseDataName(${stat.index })">
                                        </label>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

            </form>
            <div class="modal-footer">
                <button class="btn btn-primary" type="button" onclick="save();">提交</button>
                <button class="btn btn-default" type="button" onclick="back();">返回</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var dataName = []
    function getChoseDataName(value) {
        console.log()
        if ($("#"+value).is(":checked")) {
            dataName.push(value)
        }else {
            dataName = removeElement(dataName,value)
        }

        console.log(dataName)
    }

    function removeElement(arr, item) {
        var result=[];
        arr.forEach(function(i){
            if(i!=item){
                result.push(i);
            }
        });
        return result;
    }

    function back(){

        window.history.go(-2);
    }


    /***********************************  保存时操作的相关改造 ******************************************/
    // 改造一下保存事件  添加以下内容
    function save(){
        var projectId = $("#id").val();
        var mapXmlPath = $("#mapXmlPath").val();
        var valueXmlPath = $("#valueXmlPath").val();
        if(dataName.length == 0){
            tipMsg("请选择数据项!")
            return
        }

        var param = {
            projectId:projectId,
            mapXmlPath:mapXmlPath,
            valueXmlPath:valueXmlPath,
            choseDateIndex:dataName
        };

        console.log(JSON.stringify(param));
        var i = openProgressExt("提交中...");
        $.ajax({
            url:ctx + '/project/saveChoseConstantProjectParam',
            data:{reqJson:JSON.stringify(param)},
            dataType:'JSON',
            success:function(data){
                closeProgressExt(i);
                if(data.status==1){
                    tipMsg(data.msg);
                    setTimeout(function() {
                        back();
                    }, 1000);
                }else{
                    tipMsg(data.msg);
                }
            }
        });
    }


</script>

</body>

</html>