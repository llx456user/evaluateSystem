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
            <strong>数据信息</strong>
        </div>
        <div class="panel-body">
            <form id="fileEditForm" method="post">
                <input type="hidden" name="id" id="id" value="${projectId }"/>
                <input type="hidden" name="paramSuitNUmber" id="paramSuitNUmber" value="${paramSuitNUmber }"/>
                <input type="hidden" name="dataTemplateNumber" id="dataTemplateNumber" value="${dataTemplateNumber }"/>
                <input type="hidden" name="groupDataTemplateNumber" id="groupDataTemplateNumber" value="${groupDataTemplateNumber }"/>
                <div class="form-group">
                    <label>数据名称</label>
                    <input type="text" class="form-control" id="dataName" name="dataName" placeholder="" value="${dataName}">
                </div>
                <div class="form-group">
                    <label>数据备注</label>
                    <textarea class="form-control" id="dataComment" name="dataComment" placeholder="">${dataComment}</textarea>
                </div>
                <!--  改造一下保存事件  添加以下内容 start -->
                <div class="form-group">
                    <label>数据项</label>
                    <table class="table table-borderless">
                        <thead>
                        <tr>
                            <th>数据项名称</th>
                            <th></th>
                            <th>数值</th>
                        </tr>
                        </thead>
                        <tbody  id="iptVariableTb">
                        <c:forEach items="${inparList }" var="in" varStatus="stat">
                            <tr>
                                <td style="width: 50%">
                                    <input type="text" class="form-control" name="paramName" value="${in.paramName }"  style=" text-indent: 4px;" readonly><%--background: transparent;--%>
                                </td>
                                <td>
                                    <input type="hidden" class="form-control" name="id" value="${in.id }">
                                    <input type="hidden" class="form-control" name="projectParamId" value="${in.projectParamId }">
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="paramValue" onkeyup="value=value.replace(/[^\-?\d.]/g,'')" value="${in.paramValue}">
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="form-group">
                    <label>数据源</label>
                    <table class="table table-borderless">
                        <thead>
                        <tr>
                            <th>数据名称</th>
                            <th></th>
                            <th>附件名称</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="optVariableTb">
                        <c:forEach items="${outparList }" var="out" varStatus="stat">
                            <tr>
                                <td style="width: 50%">
                                    <input type="text" class="form-control" name="paramName" value="${out.paramName }"  readonly>
                                </td>
                                <td>
                                    <input type="hidden" class="form-control" name="id" value="${out.id }">
                                    <input type="hidden" class="form-control" name="projectParamId" value="${out.projectParamId }">
                                    <input type="hidden" class="form-control" name="fileValueAndName" value="${out.fileValueAndName }">
                                </td>
                                <td>
                                    <input type="text" class="form-control" name="paramValue" value="${out.paramValue }" readonly>
                                </td>
                                <td>
                                    <i title="上传" onclick="uploadOpen(event)" class="icon icon-upload-alt icon-2x iptVar uoloadBtn"></i>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

            </form>

            <button class="btn btn-primary" type="button" onclick="save();">保存</button>
            <button class="btn btn-default" type="button" onclick="back();">返回</button>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(
        function () {
            // setInterval(savemodel,50000);
        }
    );

    function back(){

        window.history.go(-1);
    }

    var tmpStructJson = [];
    $(function(){

        $("#iptVariableTb").on('click','.trash',function(){
            console.log("变量删除")
            var tr = document.getElementsByTagName('tbody')[0].getElementsByTagName('tr');
            if(tr.length==1){
                tipMsg('最后一行无法删除');
            }else {
                $(this).parent().parent().remove();
            }
        });
        $("#optVariableTb").on('click','.trash',function(){
            console.log("附件删除")
            var tr = document.getElementsByTagName('tbody')[1].getElementsByTagName('tr');
            if(tr.length==1){
                tipMsg('最后一行无法删除');
            }
            else {
                $(this).parent().parent().remove();
            }

        });

    });

    /***********************************  保存时操作的相关改造 ******************************************/
    // 改造一下保存事件  添加以下内容
    function save(){
        constrantCheck = true;
        fileCheck = true;
        var projectId = $("#id").val();
        var dataName = $("#dataName").val();
        var paramSuitNumber = $("#paramSuitNUmber").val();
        var dataTemplateNumber = $("#dataTemplateNumber").val();
        var groupParamSuitNumber = $("#groupDataTemplateNumber").val();

        if (dataName == ""){
            tipMsg("请输入数据名称!");
            return;
        }
        var dataComment = $("#dataComment").val();
        if (dataComment == ""){
            tipMsg("请输入数据备注!");
            return;
        }
        var ipt = "iptVarJson";
        var opt = "optVarJson";
        //常量
        var iptVarJson =  getJson("#iptVariableTb",ipt);
        //附件
        var optVarJson =  getJson("#optVariableTb",opt);

        if (fileCheck == false || constrantCheck == false){
            tipMsg("请输入常量数值或上传附件!");
            return;
        }

        var param = {
            projectId:projectId,
            paramSuitNumber:paramSuitNumber,
            dataTemplateNumber:dataTemplateNumber,
            dataName:dataName,
            dataComment:dataComment,
            groupParamSuitNumber:groupParamSuitNumber
        };

        param.iptVarJson = iptVarJson;
        param.optVarJson = optVarJson;
        console.log(JSON.stringify(param));
        var i = openProgressExt("保存中...");
        //return;
        $.ajax({
            url:ctx + '/project/editSetProjectParam',
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
    var constrantCheck = true;
    var fileCheck = true;
    function getJson(el,tag){
        var json = [];
        $(el).find("tr").each(function(){
            var paramName = $(this).find("input[name=paramName]").val();
            if (tag == "optVarJson") {
                var paramValue = $(this).find("input[name=fileValueAndName]").val();
            }else {
                var paramValue = $(this).find("input[name=paramValue]").val();
            }
            var projectParamId = $(this).find("input[name=projectParamId]").val();
            var id = $(this).find("input[name=id]").val();
            if (tag == "iptVarJson") {
                if (paramValue == "") {
                    constrantCheck = false;
                }
            }
            if (tag == "optVarJson") {
                if (paramValue == "") {
                    fileCheck = false;
                }
            }
            var jsonObj = {
                id:id,
                projectParamId:projectParamId,
                paramName:paramName,
                paramValue:paramValue,
            };
            json.push(jsonObj);
        });
        return json;
    }
    var $currentTr = '';
    function uploadOpen(event) {
        $("#uploadDllPage").modal('show')
        $currentTr = $(event.target).parent().parent()
        console.log($currentTr);
    }

    function upload(){
        var file = $("#file").val();
        console.log(file);
        if(file == ""){
            tipMsg("请选择附件，再进行上传");
            return;
        }
        var i = openProgressExt("上传中...");
        $("#uploadForm").ajaxSubmit({
            //type: 'post',
            url: ctx + '/datasource/upload',
            success: function(data) {
                closeProgressExt(i);
                if(data != ''){
                    // $currentTr.find("input[name=paramValue]").val(data);
                    var index = $("#file").val().lastIndexOf('\\')+1;
                    $currentTr.find("input[name=paramValue]").val($("#file").val().substring(index));
                    // 文件路径加文件名
                    $currentTr.find("input[name=fileValueAndName]").val(data + "_"+ $("#file").val().substring(index));
                    console.log($("#file").val());
                    console.log(data + "_"+ $("#file").val().substring(index));
                    $("#uploadDllPage").modal('hide');
                    tipMsg('附件上传成功');
                } else {
                    tipMsg('附件上传失败');
                }
            }
        });
    }
</script>


<div class="modal fade" id="uploadDllPage">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">上传</h4>
            </div>
            <div class="modal-body">
                <p>
                <form class="form-inline" id="uploadForm" method="post" enctype="multipart/form-data">
                    <input type="hidden"  id="modelInfoId">
                    <input type="hidden" name="filePath" id="filePath" />
                    <div class="form-group">
                        <label for="file">选择附件</label>
                        <input type="file" class="form-control" id="file" name="file" placeholder="">
                        <button class="btn btn-primary" type="button" onclick="upload();" >上传附件</button>
                    </div>
                </form>
                </p>
            </div>

        </div>
    </div>
</div>

</body>

</html>