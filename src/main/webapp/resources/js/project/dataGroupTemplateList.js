// 显示列表

$(document).ready(function () {
    $("#selectPageSize").change(function () {
        $('#gridPanel').Datagrid('options').other.pageSize = $(this).val();
        searchData();
    });

    $("#searchBtn").click(function () {
        searchData();
    });
    loadGridData();

});

function getParams() {
    var params = {};
    var projectId = document.getElementById("projectId").value;
    params.projectId = projectId;
    params.dataName = $("#parameterKey").val();
    console.log(params)
    return params;
}
function searchData() {
    var params = getParams();
    $('#gridPanel').Datagrid('options').queryParams = params;
    $('#gridPanel').Datagrid('reload');
}
function loadGridData() {
    var showChooseFlag = $("#showChooseFlag").val();
    console.log("showChooseFlag" ,showChooseFlag)
    var options = {
        url: ctx + "/project/listGroupDataTemplate",
        queryParams:getParams(),
        other: {
            pageSize: 100,
            currentPage: 1,
            data: {
                cols: [
                    {field: 'id', hidden: "true"},
                    {field: 'paramSuitNumber', hidden: "true"},
                    {
                        field:"checked", title: '选择', hidden:showChooseFlag == "true"?false:"true", width: 40,sort:false,
                        formatter: function (value, row, index) {
                            return '<input type="radio" name="myradio" value="'+row.groupParamSuitNumber+'" />';
                        }
                    },
                    {
                        title: '序号.', width: 30,sort:false,
                        formatter: function (value, row, index) {
                            return index + 1;
                        }
                    },
                    {
                        field: 'groupDataName', title: '数据名称', width: 110, halign: 'center', align: 'center',
                    },
                    {field: 'groupDataComment', title: '数据备注', width: 80, halign: 'center', align: 'center'},
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
        onLoadSuccess:function(data){
            if(data.rows.length == 0){
                $('.table-datatable tbody').html('<tr><td>没有相关数据</td></tr>');
            }
            $("div#datatable-gridPanel table.table-datatable tr").click(function(){

                var $this = $(this);
                var radio = $this.find("td:eq(2) input[name='myradio']");
                radio.prop("checked",true);
                console.log(radio.val());
                $("#templateId").val(radio.val());
            });
        },
    };
    $('#gridPanel').Datagrid('load', options);
}

function getLink(value, row) {

    var edit = "<a href='#'  class='easyui-linkbutton' onclick=editProjectTemplateValue('"+row.groupParamSuitNumber+"');>编辑</a>";
    var del = "<a href='#'  class='easyui-linkbutton' onclick=delProjectTemplateValue('"+row.paramSuitNumber+"','"+row.groupParamSuitNumber+"');>删除</a>";
    return edit+' / ' + del;
}

function search(){
    searchData();
}

function back() {
    window.history.go(-1);
}

function setGroupProjectTemplateValue(){
    var projectId = document.getElementById("projectId").value;
    var projectName = document.getElementById("projectName").value;
    var groupDataTemplateNumber = document.getElementById("groupDataTemplateNumber").value;
    console.log("projectName" + projectName)
    console.log("projectId" + projectId)
    window.location.href = ctx + '/project/setProjectTemplateValue?projectInfoId=' + projectId+'&projectName='+encodeURI(encodeURI(projectName))+ '&groupDataTemplateNumber='+groupDataTemplateNumber;
}

function editProjectTemplateValue(groupParamSuitNumber) {
    console.log("   groupParamSuitNumber->" + groupParamSuitNumber)
    var projectId = document.getElementById("projectId").value;
    var projectName = document.getElementById("projectName").value;
    // pageLoade()
    window.location.href = ctx + '/project/editGroupProjectTemplateValue?projectInfoId=' + projectId+ '&groupParamSuitNumber='+groupParamSuitNumber+'&projectName='+encodeURI(encodeURI(projectName));
    ;
}
function delProjectTemplateValue(paramSuitNumber,groupParamSuitNumber){
    console.log("paramSuitNumber->" + paramSuitNumber + "   dataTemplateNumber->" + groupParamSuitNumber)
    var projectId = document.getElementById("projectId").value;
    myconfirm('确定删除此记录？(此操作不可逆)',{},function(b){
        if(b){
            var param = {
                projectInfoId : projectId,
                paramSuitNumber: paramSuitNumber,
                groupParamSuitNumber: groupParamSuitNumber
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
function selectGroupDataTemplate() {
    var projectId = document.getElementById("projectId").value;
    var id = document.getElementById("assessId").value;
    var groupParamSuitNUmber = $("#templateId").val();
    var assessName= document.getElementById("assessName").value;
    var assessContent= document.getElementById("assessContent").value;
    var projectName= document.getElementById("projectName").value;
    console.log("projectName" + projectName,"projectId" + projectId + "assessId" + id + "groupParamSuitNUmber" + groupParamSuitNUmber)
    if(groupParamSuitNUmber == "" || groupParamSuitNUmber == undefined){
        tipMsg("请选择数据!");
        return;
    }
    pageLoade()
    window.location.href = ctx + '/assess/assessGroupProjectParam?id='+id+ '&groupParamSuitNUmber='+groupParamSuitNUmber+'&assessName='+encodeURI(encodeURI(assessName))+'&assessContent='+encodeURI(encodeURI(assessContent))+'&projectName='+encodeURI(encodeURI(projectName));
}

function pageLoade() {
    setTimeout(function() {
        openProgress("页面加载中...");
        setTimeout(function() {
            // window.history.go(-2);
        }, 500);
    }, 1000);
}