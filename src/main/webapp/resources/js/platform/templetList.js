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
            tipMsg('请选择模板分类！');
            return;
        }
        setTimeout("$('#categoryName').focus()",300);
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
            tipMsg('请选择模板分类！');
            return;
        }
        if ('root' == id) {
            tipMsg('根节点不能编辑');
            return;
        }
        setTimeout("$('#categoryName').focus()",300);
        $("#addCategoryPage h4").text("编辑分类");
        $("#categoryId").val(id);
        $("#categoryName").val("");
        $("#addCategoryPage").modal('show');
    });
    $("#saveCategoryButton").click(function(){
        var category = {};
        var id = $("#categoryId").val();
        var name = $("#categoryName").val();
        category.categoryName = name;
        if(id && id != ''){
            category.id = id;
            $.post(ctx+"/platform/templetCategoryUpdate",category,function(result){
                if('success' == result){
                    tipMsg("修改成功！");
                    $("#addCategoryPage").modal('hide');
                    reloadTreeMenu();
                    searchData();
                }
            });
        }else{
            category.categoryParentid =  $("#parentId").val() == 'root' ? 0 :$("#parentId").val();
            category.categoryLevel = 0;//todo
            $.post(ctx+"/platform/templetCategoryCreate",category,function(result){
                if('success' == result){
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
            tipMsg('请选择模板分类！');
            return;
        }
        if ('root' == id) {
            tipMsg('根节点不能编辑');
            return;
        }
        myconfirm('确定删除此记录？(此操作不可逆)', function (b) {
            if (b) {
                $.post(ctx+"/platform/templetCategoryRemove",{categoryId:id},function(result){
                    if(result == 'success'){
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
        for(var i in resultData){
            resultData[i].title = resultData[i].categoryName;
            resultData[i].url = "#";
        }
        var rootNode = [{
            id: 'root',
            title: '模板分类',
            url: '#',
            open: true,
            children: resultData
        }];
        $('#treeMenu').tree({
            data: rootNode
        });
    });
}
function reloadTreeMenu(){
    $.post(ctx + "/platform/templetCategoryList", function (resultData) {
        for(var i in resultData){
            resultData[i].title = resultData[i].categoryName;
            resultData[i].url = "#";
        }
        var rootNode = [{
            id: 'root',
            title: '模板分类',
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
    params.templetName = $("#parameterKey").val();
    params.templetContent = $("#templetContent").val();
    return params;
}
function searchData() {
    var params = getParams();
    $('#gridPanel').Datagrid('options').queryParams = params;
    $('#gridPanel').Datagrid('reload');
}
function loadGridData() {
    var options = {
        url: ctx + "/platform/templetList",
        other: {
            pageSize: 10,
            currentPage: 1,
            data: {
                cols: [
                    {field: 'id', hidden: "true"},
                    {
                        title: '序号', width: 30,
                        formatter: function (value, row, index) {
                            return index + 1;
                        }
                    },
                    {field: 'templetName', title: '模板名称', width: 100, halign: 'center', align: 'center'},
                    {field: 'templetContent', title: '模板内容', width: 120, halign: 'center', align: 'center'},
                    {
                        field: 'updateTime', title: '更新时间', width: 80, halign: 'center', align: 'center'
                        // formatter: function (value, row, index) {
                        //     return timestampToTime(value);
                        // }
                    },
                    {
                        field: 'field4', title: '模板操作', width: 30,sort:false,
                        formatter: function (value, row) {
                            return getLink(value, row);
                        }
                    }
                ]
            }
        },
        tables: {
            checkable: false,
            sortable: true,
            //checkedClass: "checked",
            colHover: true,
            hoverClass: 'hover',
            minFixedLeftWidth: 300
        }
    };
    $('#gridPanel').Datagrid('load', options);
}

function getLink(value, row) {
    // var look = "<a href='#'  class='icon-search' onclick=look('"+row.id+"');></a>";
    var look = "<a href='#' title=\"查看\" class='easyui-linkbutton' onclick=look('"+row.id+"');><i class='icon-search'></i></a>";
    var edit = "<a href='#'  title=\"编辑\" class='easyui-linkbutton' onclick=edit('"+row.id+"');><i class='icon-edit'></i></a>";
    // var del  = "<a href='#'  title=\"删除\" class='easyui-linkbutton' onclick=del('"+row.id+"');><i class='icon-trash'></i></a>";
    // var edit = "<a href='#'  class='icon-edit' onclick=edit('"+row.id+"');></a>	";
    // var del = "<a href='#'  class='icon-trash' onclick=del('"+row.id+"');></a>	";
    var del = "<a href='#'  class='easyui-linkbutton' onclick=del('"+row.id+"');>删除</a>";
    // return look + ' / '+ edit + ' / ' + del;
    return del;
}

function rowChangeColor(value, row) {
    var show = "";
    show = "<font color=red>" + value + "</font><br>";
    return show;
}

function add() {
    var $el = $("#treeMenu li a.active");
    var id = $el.parent().attr('data-id');
    if (undefined == id) {
        tipMsg('请选择模板分类！');
        return;
    }
    if ('root' == id) {
        tipMsg('根节点不能创建模板！');
        return;
    }
    window.location.href = ctx + '/platform/templetAdd?categoryId=' + id;
}

function look(id){
	window.location.href = ctx + '/platform/templetView?id=' + id;
}

function edit(id){
	window.location.href = ctx + '/platform/templetEdit?id=' + id;
}

function del(id){
	myconfirm('确定删除此记录？(此操作不可逆)',{},function(b){
		if(b){
			var i = openProgressExt("删除中...");
			var param = {
					id : id
				};
			myajax(ctx + '/platform/templetDel', param, function(data) {
				closeProgressExt(i);
				tipMsg(data.msg);
				 searchData();
			});
		}
	});
}

function save() {

}

function back() {
    window.history.go(-1);
}
function timestampToTime(timestamp) {
	if(timestamp == null || timestamp == ''){
		return timestamp;
	}
	var date = new Date(timestamp);
	Y = date.getFullYear() + '-';
	M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
	D = date.getDate() + ' ';
	h = date.getHours() + ':';
	m = date.getMinutes() + ':';
	s = date.getSeconds(); 
    return Y+M+D+h+m+s;
}
