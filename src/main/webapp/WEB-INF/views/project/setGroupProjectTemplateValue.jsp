<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>评估系统-新建分组数据模板</title>
    <style>

    </style>
</head>
<body>
<div class="container">
    <div class="panel" style="margin-bottom: 5px;float: right;width: 100%;">
        <div class="panel-heading">
            <strong style="font-size: ${16}">${projectName}数据信息</strong>
            <input type="hidden" name="id" id="id" value="${projectId }"/>
            <input type="hidden" name="saveFlag" id="saveFlag"/>
            <input type="hidden" name="projectName" id="projectName" value="${projectName }"/>
            <input type="hidden" name="groupDataTemplateNumber" id="groupDataTemplateNumber" value="${groupDataTemplateNumber }"/>
            <button type="button" style="margin-left: 80%" class="btn btn-primary" onclick="saveDataNameAndComment();">保存</button>
            <div class="form-group">
                <label style="font-size: ${16}">数据名称</label>
                <input type="text" class="form-control" id="groupDataName" name="groupDataName"value="${groupDataName}">
            </div>
            <div class="form-group">
                <label style="font-size: ${16}">数据备注</label>
                <textarea class="form-control" id="groupDataComment" name="groupDataComment" >${groupDataComment}</textarea>
            </div>
        </div>
        <c:forEach items="${groupParam }" var="group" varStatus="grouStat">
        <div class="panel-heading">
            <form id="fileEditForm" method="post">
                <div id="groupTemplates${group.dataTemplateNumber}">
                    <div class="form-group">
                        <label style="font-size: ${16}">分组名称</label>
                        <input type="text" class="form-control" id="groupTemplateName${group.dataTemplateNumber}" value="${group.dataTemplateName}" readonly>
                    </div>
                    <div>
                        <button type="button" class="btn btn-primary" onclick="addGroupProjectTemplate(${group.dataTemplateNumber});"><i class="icon-plus"></i>新增分组模板数据</button>
                    </div>
                </div>
            </form>
        </div>
        <table class="datatable table-bordered" id="gridPanel${group.dataTemplateNumber}"></table>
    </div>
    <div style="clear: both;"></div>
    </c:forEach>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        loadGridData();
    });

    function loadMyData(i, total,data){
        var projectId = document.getElementById("id").value;
        var groupDataTemplateNumber = document.getElementById("groupDataTemplateNumber").value;

        var params = {};
        params.projectId = projectId;
        params.dataTemplateNumber = data[i].dataTemplateNumber;
        params.groupDataTemplateNumber = groupDataTemplateNumber;
        console.log(JSON.stringify(params),i);
        var options = {
            url: ctx + "/project/groupDataTemplateList",
            queryParams:params,
            other: {
                pageSize: 10,
                currentPage: 1,
                data: {
                    cols: [
                        {field: 'id', hidden: "true"},
                        {field: 'paramSuitNumber', hidden: "true"},
                        {
                            title: '序号.', width: 30,sort:false,
                            formatter: function (value, row, index) {
                                return index + 1;
                            }
                        },
                        {
                            field: 'dataName', title: '数据名称', width: 110, halign: 'center', align: 'center',
                        },
                        {field: 'dataComment', title: '数据备注', width: 80, halign: 'center', align: 'center'},
                        {
                            field: 'field4', title: '操作', width: 40,sort:false,
                            formatter: function (value, row) {
                                return getLink(value, row);
                            }
                        },
                    ]
                }
            },
            tables: {
                checkable: false,
                sortable: true,
                colHover: true,
                hoverClass: 'hover',
                minFixedLeftWidth: 300
            },
            onLoadSuccess:function(val){
                console.log(val,i);
                if(val.rows.length == 0){
                    // $('.table-datatable tbody').html('<tr><td>没有相关数据</td></tr>');
                    $('#datatable-gridPanel'+ data[i].dataTemplateNumber).find('tbody').html('<tr><td>没有相关数据</td></tr>');
                }else {
                    $("#saveFlag").val(true)
                }
                if(i < total) {
                    loadMyData(i+1, total, data)
                }
            },
        };
        $('#gridPanel' + data[i].dataTemplateNumber).Datagrid('load', options)
    }
    function loadGridData() {
        var projectId = document.getElementById("id").value;
        var param = {
            projectId: projectId
        };
        // var k = openProgressExt("加载中...");
        myajax(ctx + '/project/getGroupDataTemplate', param, function (data) {
            if (data.length >0) {
                loadMyData(0, data.length - 1, data)
            }
        });

    }

    function getLink(value, row) {

        var edit = "<a href='#'  class='easyui-linkbutton' onclick=editProjectTemplateValue('"+row.paramSuitNumber+"','"+row.dataTemplateNumber+"');>编辑</a>";
        var del = "<a href='#'  class='easyui-linkbutton' onclick=delProjectTemplateValue('"+row.paramSuitNumber+"','"+row.dataTemplateNumber+"','"+row.groupParamSuitNumber+"');>删除</a>";
        return edit+' / ' + del;
    }

    function editProjectTemplateValue(paramSuitNumber,dataTemplateNumber) {
        console.log("paramSuitNumber->" + paramSuitNumber + "   dataTemplateNumber->" + dataTemplateNumber)
        var projectId = document.getElementById("id").value;
        var groupDataTemplateNumber = document.getElementById("groupDataTemplateNumber").value;
        pageLoade()
        window.location.href = ctx + '/project/editProjectTemplateValue?projectInfoId=' + projectId+ '&paramSuitNUmber='+paramSuitNumber+ '&dataTemplateNumber='+dataTemplateNumber+ '&groupDataTemplateNumber='+groupDataTemplateNumber;

    }
    function delProjectTemplateValue(paramSuitNumber,dataTemplateNumber,groupParamSuitNumber){
        console.log("paramSuitNumber->" + paramSuitNumber + "   dataTemplateNumber->" + dataTemplateNumber)
        if(dataTemplateNumber == "null"){
            console.log("dataTemplateNumber是null");
            dataTemplateNumber = 0
        }
        var projectId = document.getElementById("id").value;
        myconfirm('确定删除此记录？(此操作不可逆)',{},function(b){
            if(b){
                var param = {
                    projectInfoId : projectId,
                    paramSuitNumber: paramSuitNumber,
                    dataTemplateNumber: dataTemplateNumber,
                    groupParamSuitNumber:groupParamSuitNumber
                };
                myajax(ctx + '/project/delProjectTemplateValue', param, function(data) {
                    tipMsg(data.msg);
                    setTimeout(function() {
                        openProgress("页面刷新中...");
                        setTimeout(function() {
                            location.reload();
                        }, 500);
                    }, 3000);
                });
            }
        });
    }

    function pageLoade() {
        setTimeout(function() {
            openProgress("页面加载中...");
            setTimeout(function() {
                // window.history.go(-2);
            }, 500);
        }, 1000);
    }
    function addGroupProjectTemplate(value) {
        console.log(value)
        var dataName = $("#groupDataName").val();
        if (dataName == ""){
            tipMsg("请输入数据名称!");
            return;
        }
        var dataComment = $("#groupDataComment").val();
        if (dataComment == ""){
            tipMsg("请输入数据备注!");
            return;
        }
        var projectInfoId = $("#id").val();
        var projectName = $("#projectName").val();
        var groupDataTemplateNumber = $("#groupDataTemplateNumber").val();
        window.location.href = ctx + '/project/addGroupProjectTemplate?projectInfoId=' + projectInfoId
            +'&projectName='+encodeURI(encodeURI(projectName))
            +'&dataName='+encodeURI(encodeURI(dataName))
            +'&dataComment='+encodeURI(encodeURI(dataComment))
            +'&dataTemplateNumber='+value
            +'&groupDataTemplateNumber='+groupDataTemplateNumber;
    }

    function saveDataNameAndComment() {
        var dataName = $("#groupDataName").val();
        if (dataName == ""){
            tipMsg("请输入数据名称!");
            return;
        }
        var dataComment = $("#groupDataComment").val();
        if (dataComment == ""){
            tipMsg("请输入数据备注!");
            return;
        }
        var saveFlag = $("#saveFlag").val();
        if(saveFlag != "true"){
            tipMsg("请新增分组模板数据后再保存!");
            return;
        }
        var groupParamSuitNumber = $("#groupDataTemplateNumber").val();
        var projectId = document.getElementById("id").value;
        var param = {
            projectInfoId : projectId,
            groupParamSuitNumber:groupParamSuitNumber,
            dataName:dataName,
            dataComment:dataComment
        };
        var i = openProgressExt("保存中...");
        $.ajax({
            url:ctx + '/project/saveDataNameAndComment',
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
    function back(){
        window.history.go(-1);
    }
    /***********************************  保存时操作的相关改造 ******************************************/
</script>

</body>

</html>