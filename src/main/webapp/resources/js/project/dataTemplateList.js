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
    var assessId = $("#assessId").val();
    console.log("assessId" + assessId)
    var options = {
        url: ctx + "/project/dataTemplateList",
        queryParams:getParams(),
        other: {
            pageSize: 10,
            currentPage: 1,
            data: {
                cols: [
                    {field: 'id', hidden: "true"},
                    {field: 'paramSuitNumber', hidden: "true"},
                    {
                        field:"checked", title: '选择', hidden:assessId != ""?false:true, width: 40,sort:false,
                        formatter: function (value, row, index) {
                            return '<input type="radio" name="myradio" value="'+row.paramSuitNumber+'" />';
                        }
                    },
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

    var edit = "<a href='#'  class='easyui-linkbutton' onclick=editProjectTemplateValue('"+row.paramSuitNumber+"','"+row.dataTemplateNumber+"');>编辑</a>";
    var del = "<a href='#'  class='easyui-linkbutton' onclick=delProjectTemplateValue('"+row.paramSuitNumber+"','"+row.dataTemplateNumber+"');>删除</a>";
    return edit+' / ' + del;
}

function search(){
    searchData();
}

function back() {
    window.history.go(-1);
}

function setProjectTemplateValue(){
    var projectId = document.getElementById("projectId").value;
    var projectName = document.getElementById("projectName").value;
    console.log("projectName" + projectName)
    console.log("projectId" + projectId)
    pageLoade()
    window.location.href = ctx + '/project/setProjectTemplateValue?projectInfoId=' + projectId+'&projectName='+encodeURI(encodeURI(projectName));;
}

function editProjectTemplateValue(paramSuitNumber) {
    console.log("paramSuitNumber->" + paramSuitNumber)
    var projectId = document.getElementById("projectId").value;
    pageLoade()
    window.location.href = ctx + '/project/editProjectTemplateValue?projectInfoId=' + projectId+ '&paramSuitNUmber='+paramSuitNumber;
    ;
}
function delProjectTemplateValue(paramSuitNumber){
    console.log("paramSuitNumber->" + paramSuitNumber)
    var projectId = document.getElementById("projectId").value;
    myconfirm('确定删除此记录？(此操作不可逆)',{},function(b){
        if(b){
            var param = {
                projectInfoId : projectId,
                paramSuitNumber: paramSuitNumber,
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

function selectDataTemplate(){
    var projectId = document.getElementById("projectId").value;
    var id = document.getElementById("assessId").value;
    var paramSuitNUmber = $("#templateId").val();
    var assessName= document.getElementById("assessName").value;
    var projectName= document.getElementById("projectName").value;
    var assessContent= document.getElementById("assessContent").value;
    console.log("projectId" + projectId + "assessId" + id + "paramSuitNUmber" + paramSuitNUmber)
    if(paramSuitNUmber == "" || paramSuitNUmber == undefined){
        tipMsg("请选择数据!");
        return;
    }
    pageLoade()
    window.location.href = ctx + '/assess/assessProjectParam?id='+id+ '&paramSuitNUmber='+paramSuitNUmber+'&assessName='+encodeURI(encodeURI(assessName))+'&assessContent='+encodeURI(encodeURI(assessContent))+'&projectName='+encodeURI(encodeURI(projectName));
}
function projectTemplate() {
    var projectId = document.getElementById("projectId").value;
    var projectName = document.getElementById("projectName").value;
    console.log("projectName" + projectName)
    pageLoade()
    window.location.href = ctx + '/project/addOrEditProjectParam?projectInfoId=' + projectId +'&projectName='+encodeURI(encodeURI(projectName));
}

function pageLoade() {
    setTimeout(function() {
        openProgress("页面加载中...");
        setTimeout(function() {
            // window.history.go(-2);
        }, 500);
    }, 1000);
}

function uploadXmlOpen() {
    var projectId = document.getElementById("projectId").value;
    window.location.href = ctx + '/project/uploadMapAndValueXml?projectId=' + projectId
}