// 显示列表

$(document).ready(function() {

    $("#selectPageSize").change(function() {
        $('#gridPanel').Datagrid('options').other.pageSize = $(this).val();
        searchData();
    });
    loadData();

    $("#searchBtn").click(function() {
        searchData();
    });

    init();
});
function init() {
    $('#treeMenu').on('click', 'a', function() {
        if ($(this).parent().hasClass("has-list")) {
            return;
        }
        $('#treeMenu li.active').removeClass('active');
        $(this).closest('li').addClass('active');
        searchData();
    });
    $('#searchInputExample').searchBox({
        escToClear : true, // 设置点击 ESC 键清空搜索框
        onSearchChange : function(searchKey, isEmpty) {

            $("#treeMenu li a").parent().hide();
            $("#treeMenu li a").each(function(o) {
                console.log('搜索框文本变更：', $(this).text());
                if ($(this).text().indexOf(searchKey) != -1) {
                    $(this).parent().show();
                    if ($(this).parent().parents("li").hasClass("has-list")) {
                        $(this).parent().parents("li").addClass("open").show();
                    }
                }
            });
        }
    });

}
function getParams() {
    return {
        categoryId : $("#treeMenu li.active").attr("cateid"),
        structName:$("#structName").val(),
        structRemark:$("#structRemark").val()
    };

}
function searchData() {
    var params = getParams();

    $('#gridPanel').Datagrid('options').queryParams = params;
    $('#gridPanel').Datagrid('reload');
}

function loadData() {
    var options = {
        url : ctx + "/platform/structList",
        // queryParams:{categoryId:categoryId},
        other : {
            pageSize : 10,
            currentPage : 1,
            data : {
                cols : [
                    {
                    field : 'field1',
                    hidden : "true"
                },
                    // {
                    //     title : 'No.',
                    //     width : 30,
                    //     formatter : function(value, row, index) {
                    //         return index + 1;
                    //     }
                    // },

                    {field: 'radio', width: 20,formatter:function(value,row,index){
                            return '<input type="radio" name="myradio" value="'+row.id+'" />';
                        }},
                     {
                    field : 'structName',
                    title : '结构体名称',
                    width : 70,
                    halign : 'center',
                    align : 'center'
                }, {
                    field : 'structRemark',
                    title : '结构体备注',
                    width : 100,
                    halign : 'center',
                    align : 'center'
                }, {
                    field : 'field4',
                    title : '结构体操作',
                    width : 40,
                    formatter : function(value, row) {
                        return getLink(value, row);
                    }
                }

                ]
            }
        },
        tables : {
            checkable: false,
            singleSelect:true,
            sortable : false,
            // checkedClass: "checked",
            colHover : true,
            hoverClass : 'hover',
            minFixedLeftWidth : 300

        }
        ,
        onLoadSuccess:function(){
            $("div#datatable-gridPanel table.table-datatable tr").click(function(){

                var $this = $(this);
                var radio = $this.find("td:eq(1) input[name='myradio']");
                radio.prop("checked",true);
            });
        }
    };
    $('#gridPanel').Datagrid('load', options);
}

function getLink(value, row) {

    var look = "<a href='#'  class='easyui-linkbutton' onclick=vewStructInfo('"+row.id+"');>查看</a>";
    return look;

}

function rowChangeColor(value, row) {

    var show = "";
    show = "<font color=red>" + value + "</font><br>";
    return show;
}

function search(){
    searchData();
}

function vewStructInfo(structInfoId){
    window.location.href = ctx + '/platform/vewStructInfo?structInfoId='+structInfoId ;
}

function select(){
	
    // var pId = $("#pId").val();
	 // var frameid = $("#frameid").val();
    // var rows = $('#gridPanel').Datagrid('getChecked');
    // var len = rows.length;
    //     if (len != 1) {
    //         tipMsg('请选择一条文件信息！');
    //         return;
    //     }
    //    top.document.getElementById(frameid).contentWindow.callBackSelectStruct(rows[0].structName);
    //
    //    // parent.callBackSelectStruct(rows[0].structName);
    //     //parent.window.callBackSelectStruct(rows[0].structName);
    //     // top.closeLayerDialog();
    //
    // top.closeDialog();
    //
    var value = getCheckedRadio("myradio");
    if(value == null){
        tipMsg('请选择一条指标信息！');
        return;
    }
    // var frameid = $("#frameid").val();
    var structname=structIdaa(value);
    // top.document.getElementById(frameid).contentWindow.callBackSelectStruct(structname);
    // // top.closeDialog();
    // top.closeLayerDialog();
}
function structIdaa(structId) {
    var structname="";
    var json = {structId:structId};
    myajax(ctx+"/platform/setStructId",json,
        function(data){
            structname=data.structname;
            var frameid = $("#frameid").val();
            top.document.getElementById(frameid).contentWindow.callBackSelectStruct(structname);
            top.closeLayerDialog();
        });
}
function callbackdata(){
	return "123";
}
function getCheckedRadio(name){
    var radios = document.getElementsByName(name);
    var len = radios.length;
    for(var i = 0; i<len;i++){
        if(radios[i].checked){
            return radios[i].value;
        }
    }
    return null;
}