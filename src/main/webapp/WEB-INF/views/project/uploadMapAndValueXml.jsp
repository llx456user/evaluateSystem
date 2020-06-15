<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/index/includeZui.jsp" %>
<html>
<head>
    <title>评估系统-Word模板管理查询</title>
    <script type="text/javascript" src="<c:url value='/resources/js/project/dataTemplateList.js'/>"></script>
    <style>
        .active {
            border: 1px solid red;
        }
    </style>
</head>
<body>
<div class="container">
    <!-- 表格数据 -->
    <div class="panel" style="float: right;width: 100%;">
        <div class="panel-heading">
            <label>上传文件</label>
        </div>
        <div class="panel-heading">
            <form role="form" class="form-inline" style="text-align:right">
                <%--<div style="float:right;">--%>
                    <%--<div class="form-group">--%>
                        <input type="hidden" id="projectId" value="${projectId }"/>
                        <button type="button" class="btn btn-primary" onclick="uploadMapXmlOpen(event);">上传映射文件</button>
                        <button type="button" class="btn btn-primary" onclick="uploadValueXmlOpen(event);">上传赋值附件</button>
                    <%--</div>--%>
                <%--</div>--%>
            </form>
        </div>
    </div>
    <div style="clear: both;"></div>
</div>
<script type="text/javascript">
    var $currentTr = '';
    var mapXmlPath ='';
    var valueXmlPath = '';
    var projectId = document.getElementById("projectId").value;
    function uploadMapXmlOpen(event) {
        $("#uploadMapXMLPage").modal('show')
        $currentTr = $(event.target).parent().parent()
        console.log($currentTr);
    }

    function uploadValueXmlOpen(event) {
        $("#uploadValueXMLPage").modal('show')
        $currentTr = $(event.target).parent().parent()
        console.log($currentTr);
    }
    function uploadValueXml() {
        var file = $("#fileXml").val();
        console.log(file);
        if(file == ""){
            tipMsg("请选择附件，再进行上传");
            return;
        }
        var index = $("#fileXml").val().lastIndexOf('.')+1;
        var fileType = $("#fileXml").val().substring(index);
        if(fileType.toLowerCase() != 'xml'){
            tipMsg("请上传xml附件");
            return;
        }
        var i = openProgressExt("上传中...");
        $("#uploadXmlForm").ajaxSubmit({
            //type: 'post',
            url: ctx + '/datasource/upload',
            success: function(data) {
                closeProgressExt(i);
                if(data != ''){
                    console.log(data)
                    valueXmlPath = data;
                    console.log(projectId,valueXmlPath)
                    $("#uploadValueXMLPage").modal('hide');
                    tipMsg('附件上传成功');
                    analysisXml();

                } else {
                    tipMsg('附件上传失败');
                }
            }
        });
    }
    function uploadMapXml(){
        var file = $("#file").val();
        console.log(file);
        if(file == ""){
            tipMsg("请选择附件，再进行上传");
            return;
        }
        var index = $("#file").val().lastIndexOf('.')+1;
        var fileType = $("#file").val().substring(index);
        if(fileType.toLowerCase() != 'xml'){
            tipMsg("请上传xml附件");
            return;
        }
        var i = openProgressExt("上传中...");
        $("#uploadForm").ajaxSubmit({
            //type: 'post',
            url: ctx + '/datasource/upload',
            success: function(data) {
                closeProgressExt(i);
                if(data != ''){
                    console.log(data)
                    mapXmlPath = data;
                    console.log(projectId,mapXmlPath)
                    $("#uploadMapXMLPage").modal('hide');
                    tipMsg('附件上传成功');
                    analysisXml()
                } else {
                    tipMsg('附件上传失败');
                }
            }
        });
    }
    function analysisXml(){
        if(mapXmlPath != '' && valueXmlPath != ''){
            var tempMapXmlName= ''
            var mapXmlPathIndex = mapXmlPath.lastIndexOf('\\')+1;
            var tempMapXmlName = mapXmlPath.substring(mapXmlPathIndex)

            var tempValueXmlName= ''
            var valueXmlPathIndex = valueXmlPath.lastIndexOf('\\')+1;
            var tempValueXmlName = valueXmlPath.substring(valueXmlPathIndex)
            window.location.href = ctx + '/project/choseProjectConstantTemplateValue?mapXmlPath=' + tempMapXmlName+'&valueXmlPath='+tempValueXmlName+'&projectId='+projectId;
            return;
        }
        var param = {
            mapXmlPath:mapXmlPath,
            valueXmlPath:valueXmlPath,
            projectId:projectId
        }
        // var i = openProgressExt("页面加载中...");
        // $.ajax({
        //     url:ctx + '/project/choseProjectConstantTemplateValue',
        //     data:{reqJson:JSON.stringify(param)},
        //     dataType:'JSON',
        //     success:function(data){
        //         closeProgressExt(i);
        //     }
        // });
    }
</script>
<div class="modal fade" id="uploadMapXMLPage">
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
                        <button class="btn btn-primary" type="button" onclick="uploadMapXml();" >上传附件</button>
                    </div>
                </form>
                </p>
            </div>

        </div>
    </div>
</div>

<div class="modal fade" id="uploadValueXMLPage">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">上传</h4>
            </div>
            <div class="modal-body">
                <p>
                <form class="form-inline" id="uploadXmlForm" method="post" enctype="multipart/form-data">
                    <input type="hidden"  id="modeXmllInfoId">
                    <input type="hidden" name="filePath" id="fileXmlPath" />
                    <div class="form-group">
                        <label for="file">选择附件</label>
                        <input type="file" class="form-control" id="fileXml" name="file" placeholder="">
                        <button class="btn btn-primary" type="button" onclick="uploadValueXml();" >上传附件</button>
                    </div>
                </form>
                </p>
            </div>

        </div>
    </div>
</div>

</body>
</html>