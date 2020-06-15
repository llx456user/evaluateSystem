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

    $("#copyCategory").click(function () {
        var $el = $("#treeMenu li a.active");
        var categoryId = $el.parent().attr('data-id');
        var txt = $el.text();
        if (undefined == categoryId) {
            tipMsg('请选择指标分类！');
            return;
        }
        var parentId=$("#parentId").val(categoryId);


            var indexframeid= self.frameElement.getAttribute('id');
            var url = ctx + '/platform/setCopyIndex?indexframeid='+indexframeid+'&categoryId='+categoryId;
            top.openDialog('选择指标','1000px','650px',url,function(data){
                //TODO
                console.log(data);
            });

    });


    $("#addCategory").click(function () {
        var $el = $("#treeMenu li a.active");
        var id = $el.parent().attr('data-id');
        var txt = $el.text();
        if (undefined == id) {
            tipMsg('请选择指标分类！');
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
            tipMsg('请选择指标分类！');
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
            $.post(ctx+"/platform/categoryUpdate",category,function(result){
                if('success' == result){
                    tipMsg("修改成功！");
                    $("#addCategoryPage").modal('hide');
                    reloadTreeMenu();
                    searchData();
                }
            });
        }else{
            category.categoryParentid =  $("#parentId").val();
            category.categoryLevel = 0;//todo
            $.post(ctx+"/platform/categoryCreate",category,function(result){
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
            tipMsg('请选择指标分类！');
            return;
        }
        if ('root' == id) {
            tipMsg('根节点不能编辑');
            return;
        }
        myconfirm('确定删除此记录？(此操作不可逆)', function (b) {
            if (b) {
                $.post(ctx+"/platform/categoryRemove",{categoryId:id},function(result){
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
        /*for(var i in resultData){
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
        });*/
    	for(var i in resultData){
            resultData[i].title = resultData[i].categoryName;
            resultData[i].url = "#";
            resultData[i].categoryParentid = resultData[i].categoryParentid;
        }
    	var rootNode = [{
            id: 0,
            title: '指标分类',
            url: '#',
            open: true,
            children: arrayToJson(resultData)
        }];
    	$('#treeMenu').tree({
    		data: rootNode
    	});
    });
}


function arrayToJson(treeArray){
var r = [];
var tmpMap ={};

for (var i=0, l=treeArray.length; i<l; i++) {
 // 以每条数据的id作为obj的key值，数据作为value值存入到一个临时对象里面
 tmpMap[treeArray[i]["id"]]= treeArray[i]; 
} 

for (i=0, l=treeArray.length; i<l; i++) {
 var key=tmpMap[treeArray[i]["categoryParentid"]];
  
 //循环每一条数据的pid，假如这个临时对象有这个key值，就代表这个key对应的数据有children，需要Push进去
 if (key) {
  if (!key["children"]){
    key["children"] = [];
    key["children"].push(treeArray[i]);
  }else{
   key["children"].push(treeArray[i]);
  }    
 } else {
  //如果没有这个Key值，那就代表没有父级,直接放在最外层
  r.push(treeArray[i]);
 }
}
return r
}
function reloadTreeMenu(){
    $.post(ctx + "/platform/categoryList", function (resultData) {
        /*for(var i in resultData){
            resultData[i].title = resultData[i].categoryName;
            resultData[i].url = "#";
        }
        var rootNode = [{
            id: 0,
            title: '指标分类',
            url: '#',
            open: true,
            children: resultData
        }];*/
    	for(var i in resultData){
            resultData[i].title = resultData[i].categoryName;
            resultData[i].url = "#";
            resultData[i].categoryParentid = resultData[i].categoryParentid;
        }
    	var rootNode = [{
            id: 0,
            title: '指标分类',
            url: '#',
            open: true,
            children: arrayToJson(resultData)
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
    params.modelContent = $("#indexContent").val();
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
                        title: '序号', width: 30,sort:false,
                        formatter: function (value, row, index) {
                            return index + 1;
                        }
                    },
                    {field: 'indexName', title: '指标名称', width: 110, halign: 'center', align: 'center'},
                    {field: 'indexStatusStr', title: '测试状态', width: 50, halign: 'center', align: 'center'},
                    {field: 'indexContent', title: '指标内容', width: 100, halign: 'center', align: 'center'},
                    {
                        field: 'updateTimeStr', title: '更新时间', width: 80, halign: 'center', align: 'center'
                    },
                    {
                        field: 'field4', title: '指标操作', width: 40,sort:false,
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
   // var look = "<a href='#'  class='icon-search' onclick=alert('look');></a>	";
  //   var edit = "<a href='#'  class='icon-edit' onclick=addEditIndex('"+row.id+"');></a>	";
  // //  var test = "<a href='#'  class='icon-play' onclick=test('"+row.id+"');></a>	";
  //   var del = "<a href='#'  class='icon-trash' onclick=deleteIndex('"+row.id+"');></a>	";
    var edit = "<a href='#'  class='easyui-linkbutton' onclick=addEditIndex('"+row.id+"');>编辑</a>";
    var del = "<a href='#'  class='easyui-linkbutton' onclick=deleteIndex('"+row.id+"');>删除</a>";
    return  edit +' / '+  del;
}

function rowChangeColor(value, row) {
    var show = "";
    show = "<font color=red>" + value + "</font><br>";
    return show;
}

function deleteIndex(indexid) {
    myconfirm('确定删除此记录？(此操作不可逆)',{},function(b){
        if(b){
            var param = {
                id : indexid
            };
            myajax(ctx + '/platform/deleteIndex', param, function(data) {
                tipMsg(data.msg);
                setTimeout(function() {
                    openProgress("页面刷新中...");
                    setTimeout(function() {
                        location.reload();
                    }, 500);
                }, 1000);
            });
        }
    });

}

function addEditIndex(indexid) {
    //表示
    if(indexid != undefined){
        window.location.href = ctx + '/platform/indexEdit?id=' + indexid;
    }else{
        var $el = $("#treeMenu li a.active");
        var id = $el.parent().attr('data-id');
        if (undefined == id) {
            tipMsg('请选择指标分类！');
            return;
        }
        if ('root' == id) {
            tipMsg('根节点不能创建指标分类！');
            return;
        }
        window.location.href = ctx + '/platform/indexAdd?categoryId=' + id;
    }
}

function search(){
    searchData();
}

function save() {

}

function back() {
    window.history.go(-1);
}

function test(id){
	//TODO 获取指标信息
	window.location.href= ctx + '/platform/indexTest';
}


function callBackCopyIndex(indexId,category){
    // var json = {id: category,modelId:modelId};
    // myajax(ctx+"/platform/setmodelValue",json,
    //     function(data){});
    window.location.href = ctx + '/platform/setindexValue?categoryId='+category+ '&indexId='+indexId;
}