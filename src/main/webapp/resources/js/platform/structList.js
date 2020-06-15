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
                cols : [ {
                    field : 'field1',
                    hidden : "true"
                }, {
                    title : '序号',
                    width : 30,
                    formatter : function(value, row, index) {
                        return index + 1;
                    }
                }, {
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
                    sort:false,
                    formatter : function(value, row) {
                        return getLink(value, row);
                    }
                }

                ]
            }
        },
        tables : {
            checkable : false,
            sortable : true,
            // checkedClass: "checked",
            colHover : true,
            hoverClass : 'hover',
            minFixedLeftWidth : 300

        }
    };
    $('#gridPanel').Datagrid('load', options);
}

function getLink(value, row) {

    var look = "<a href='#'  class='easyui-linkbutton' onclick=vewStructInfo('"+row.id+"');>查看</a>";
    var edit = "<a href='#'  class='easyui-linkbutton' onclick=addOrEditStructInfo('"+row.id+"');>编辑</a>";
    var del = "<a href='#'  class='easyui-linkbutton' onclick=deleteStructInfo('"+row.id+"');>删除</a>";
    return look+ ' / '+edit+' / '+ del;

}

function rowChangeColor(value, row) {

    var show = "";
    show = "<font color=red>" + value + "</font><br>";
    return show;
}

function search(){
    searchData();
}

function saveCategory() {
    var categoryId = $("#categoryId").val();
    var categoryName = $("#categoryName").val();
    var param = {
        categoryId : categoryId,
        categoryName : categoryName
    };
    myajax(ctx + '/platform/saveStructCategory', param, function(data) {
        tipMsg(data.msg);

        $("#addCategoryPage").modal('hide');
        setTimeout(function() {
            openProgress("页面刷新中...");
            setTimeout(function() {
                location.reload();
            }, 500);
        }, 3000);
    });
}

function editCategory(categoryId) {
    setTimeout("$('#categoryName').focus()",300);
    $("#addCategoryPage h4").text("编辑分类");
    $("#addCategoryPage input").val("");
    $("#addCategoryPage").modal('show');
    $("#categoryId").val(categoryId);
}
function addCategory() {
    setTimeout("$('#categoryName').focus()",300);
    $("#addCategoryPage input").val("");
    $("#addCategoryPage").modal('show');
}
function deleteCategory(categoryId) {
    myconfirm('确定删除此记录？(此操作不可逆)',{},function(b){
        if(b){
            var param = {
                categoryId : categoryId
            };
            myajax(ctx + '/platform/deleteStructCategory', param, function(data) {
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




function deleteStructInfo(structInfoId) {
    myconfirm('确定删除此记录？(此操作不可逆)',{},function(b){
        if(b){
            var param = {
                structInfoId : structInfoId
            };
            myajax(ctx + '/platform/deleteStructInfo', param, function(data) {
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

function vewStructInfo(structInfoId){
    window.location.href = ctx + '/platform/vewStructInfo?structInfoId='+structInfoId ;
}


function addOrEditStructInfo(structInfoId) {
    if(structInfoId !=undefined){
        window.location.href=ctx+'/platform/toEditStructInfo?structInfoId=' + structInfoId;
    } else{
        var $el= $("#treeMenu li.active");
        var categoryId = $el.attr('cateid');
        if( undefined == categoryId){
            tipMsg('请选择模型分类！');
            return;
        }
        window.location.href = ctx + '/platform/toAddStructInfo?categoryId='+categoryId ;
    }
}
function getJson(el){
    var json = [];
    $(el).find("tr").each(function(){
        var parmeterName = $(this).find("input[name=parmeterName]").val();
        var isArray = $(this).find("input[name=array]").prop("checked");
        var parmeterType = $(this).find("[name=parmeterType] option:selected").val();
        var parmeterUnit = $(this).find("[name=parmeterUnit]").val();
        var parmeterUnitEx = $(this).find("input[name=parmeterUnitEx]").val();
        var structJson = null;
        var jsonObj = {
            parmeterName:parmeterName,
            isArray:isArray,
            parmeterType:parmeterType,
            parmeterUnit:parmeterUnit,
            parmeterUnitEx:parmeterUnitEx
        };

        json.push(jsonObj);
    });
    return json;
}
function clearStructDialog(){
    $("#newStructDialog").find("input[name=structName]").val("");
    $("#newStructDialog").each(function(){

        $(this).find("input[name=parmeterName]").val("");
        $(this).find("[name=parmeterType] option:selected").val("");
        $(this).find("input[name=parmeterUnit]").val("");
        $(this).find("input[name=parmeterUnitEx]").val("");
    });
}

// 改造一下保存事件  添加以下内容 start

$("#iptVariableTb").on('click','.addBtn',function(){
    $("#iptVariableTb").append(createTr(this));

});

$("#optVariableTb").on('click','.addBtn',function(){
    $("#optVariableTb").append(createTr(this));

});
$("#VariableTb").on('click','.addBtn',function(){
    $("#VariableTb").append(createTr(this));

});

function createTr(el){
    var $tr = $(el).parent().parent();

    var $tmp = $tr.clone(true);
    $tmp.find("input[name=array]").prop("checked",false);
    $tmp.find("input").val("");
    $tmp.find("select").val("");

    $tmp.find("#asterisk").remove("#asterisk");

    if(!$tmp.find("i").hasClass("trash")){

        $tmp.find(".addBtn").after('<i class="icon icon-trash icon-2x iptVar trash"></i>');
    }

    return $tmp;
}
$("#iptVariableTb").on('click','.trash',function(){
    $(this).parent().parent().remove();
});
$("#optVariableTb").on('click','.trash',function(){
    $(this).parent().parent().remove();
});
$("#VariableTb").on('click','.trash',function(){
    $(this).parent().parent().remove();
});


