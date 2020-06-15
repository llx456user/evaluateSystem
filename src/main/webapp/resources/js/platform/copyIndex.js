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

    initTree();

    $("#addCategory").click(function () {
        var $el = $("#treeMenu li a.active");
        var id = $el.parent().attr('data-id');
        var txt = $el.text();
        if (undefined == id) {
            tipMsg('请选择指标分类！');
            return;
        }
        setTimeout("$('#categoryName').focus()", 300);
        $("#parentId").val(id);
        $("#categoryId").val("");
        $("#categoryName").val("");
        $("#addCategoryPage").modal('show');
    });

    $("#editCategory").click(function () {
        var $el = $("#treeMenu li a.active");
        var id = $el.parent().attr('data-id');
        var txt = $el.text();
        if (undefined == id) {
            tipMsg('请选择指标分类！');
            return;
        }
        if ('root' == id) {
            tipMsg('根节点不能编辑');
            return;
        }
        setTimeout("$('#categoryName').focus()", 300);
        $("#addCategoryPage h4").text("编辑分类");
        $("#categoryId").val(id);
        $("#categoryName").val("");
        $("#addCategoryPage").modal('show');
    });
    $("#saveCategoryButton").click(function () {
        var category = {};
        var id = $("#categoryId").val();
        var name = $("#categoryName").val();
        category.categoryName = name;
        if (id && id != '') {
            category.id = id;
            $.post(ctx + "/platform/categoryUpdate", category, function (result) {
                if ('success' == result) {
                    tipMsg("修改成功！");
                    $("#addCategoryPage").modal('hide');
                    reloadTreeMenu();
                    searchData();
                }
            });
        } else {
            category.categoryParentid = $("#parentId").val();
            category.categoryLevel = 0;//todo
            $.post(ctx + "/platform/categoryCreate", category, function (result) {
                if ('success' == result) {
                    tipMsg("创建成功！");
                    $("#addCategoryPage").modal('hide');
                    reloadTreeMenu();
                    searchData();
                }
            });
        }
    });


    $("#delCategory").click(function () {
        var $el = $("#treeMenu li a.active");
        var id = $el.parent().attr('data-id');
        if (undefined == id) {
            tipMsg('请选择指标分类！');
            return;
        }
        if ('root' == id) {
            tipMsg('根节点不能编辑');
            return;
        }
        myconfirm('确定删除此记录？(此操作不可逆)', function (b) {
            if (b) {
                $.post(ctx + "/platform/categoryRemove", {categoryId: id}, function (result) {
                    if (result == 'success') {
                        tipMsg('删除成功');
                        reloadTreeMenu();
                        searchData();
                    }
                });
            }
        });
    });
});

function initTree() {
    generateTreeMenu();
    $('#treeMenu').on('click', 'a', function () {
//		if($(this).parent().hasClass("has-list")){
//			return;
//		}
//$('#treeMenu li.active').removeClass('active');
//$(this).closest('li').addClass('active');
        $("#treeMenu li a.active").removeClass('active');
        $(this).addClass('active');
        searchData();
    });
    $('#searchInputExample').searchBox({
        escToClear: true, // 设置点击 ESC 键清空搜索框
        onSearchChange: function (searchKey, isEmpty) {
            $("#treeMenu li a").parent().hide();
            $("#treeMenu li a").each(function (o) {
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

function generateTreeMenu() {
    $.post(ctx + "/platform/categoryList", function (resultData) {
        for (var i in resultData) {
            resultData[i].title = resultData[i].categoryName;
            resultData[i].url = "#";
        }
        var rootNode = [{
            id: 0,
            title: '指标分类',
            url: '#',
            open: true,
            children: resultData
        }];
        $('#treeMenu').tree({
            data: rootNode
        });
    });
}

function reloadTreeMenu() {
    $.post(ctx + "/platform/categoryList", function (resultData) {
        for (var i in resultData) {
            resultData[i].title = resultData[i].categoryName;
            resultData[i].url = "#";
        }
        var rootNode = [{
            id: 0,
            title: '指标分类',
            url: '#',
            open: true,
            children: resultData
        }];
        $('#treeMenu').data('zui.tree').reload(rootNode);
    });
}

function getParams() {
    var $el = $("#treeMenu li a.active");
    var id = $el.parent().attr('data-id');
    var params = {};
    params.categoryId = id;
    params.modelName = $("#parameterKey").val();
    return params;
}

function searchData() {
    var params = getParams();
    $('#gridPanel').Datagrid('options').queryParams = params;
    $('#gridPanel').Datagrid('reload');
}

function loadGridData() {
    var options = {
        url: ctx + "/platform/indexList",
        other: {
            pageSize: 10,
            currentPage: 1,
            data: {
                cols: [
                    {field: 'id', hidden: "true"},
                    {
                        field: 'radio', width: 20, formatter: function (value, row, index) {
                            return '<input type="radio" name="myradio" value="' + row.id + '" />';
                        }
                    },
                    {field: 'indexName', title: '指标名称', width: 110, halign: 'center', align: 'center'},
                    {field: 'indexStatusStr', title: '测试状态', width: 50, halign: 'center', align: 'center'},
                    {field: 'indexContent', title: '指标内容', width: 80, halign: 'center', align: 'center'},
                    {
                        field: 'updateTimeStr', title: '更新时间', width: 80, halign: 'center', align: 'center',
// formatter: function (value, row, index) {
//     return rowChangeColor(value, row);
// }
                    }
                ]
            }
        },
        tables: {
            checkable: false,
            sortable: false,
//checkedClass: "checked",
            colHover: true,
            hoverClass: 'hover',
            minFixedLeftWidth: 300
        },
        onLoadSuccess: function () {
            $("div#datatable-gridPanel table.table-datatable tr").click(function () {

                var $this = $(this);
                var radio = $this.find("td:eq(1) input[name='myradio']");
                radio.prop("checked", true);
            });
        }
    };
    $('#gridPanel').Datagrid('load', options);
}

function getLink(value, row) {
    var look = "<a href='#'  class='icon-search' onclick=alert('look');></a>	";
    var edit = "<a href='#'  class='icon-edit' onclick=addEditIndex('" + row.id + "');></a>	";
    var test = "<a href='#'  class='icon-play' onclick=test('" + row.id + "');></a>	";
    var del = "<a href='#'  class='icon-trash' onclick=alert('look');></a>	";
    return look + edit + test + del;
}

// function rowChangeColor(value, row) {
//     var show = "";
//     show = "<font color=red>" + value + "</font><br>";
//     return show;
// }

function selectIndex(indexid) {
//表示
    /*var rows = $('#gridPanel').Datagrid('getChecked');
    var len = rows.length;
    if (len != 1) {
    tipMsg('请选择一条指标信息！');
    return;
    }*/
    var value = getCheckedRadio("myradio");
    if (value == null) {
        tipMsg('请选择一条指标信息！');
        return;
    }
    var indexframeid = $("#indexframeid").val();
    var categoryId = $("#category").val();
    top.document.getElementById(indexframeid).contentWindow.callBackCopyIndex(value, categoryId);
    top.closeDialog();

}

function getCheckedRadio(name) {
    var radios = document.getElementsByName(name);
    var len = radios.length;
    for (var i = 0; i < len; i++) {
        if (radios[i].checked) {
            return radios[i].value;
        }
    }
    return null;
}

function save() {

}

function back() {
//window.history.go(-1);
    top.closeLayerDialog();
}

function test(id) {
//TODO 获取指标信息
    window.location.href = ctx + '/platform/indexTest';
}