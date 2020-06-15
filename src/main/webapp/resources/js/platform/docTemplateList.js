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
            tipMsg('请选择模板分类！');
            return;
        }
        var parentId=$("#parentId").val(categoryId);


            var indexframeid= self.frameElement.getAttribute('id');
            var url = ctx + '/platform/setCopyIndex?indexframeid='+indexframeid+'&categoryId='+categoryId;
            top.openDialog('选择模板','1000px','650px',url,function(data){
                //TODO
                console.log(data);
            });

    });


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
        category.name = name;
        if(id && id != ''){
            category.id = id;
            $.post(ctx+"/platform/wordCategoryUpdate",category,function(result){
                if('success' == result){
                    tipMsg("修改成功！");
                    $("#addCategoryPage").modal('hide');
                    reloadTreeMenu();
                    searchData();
                }
            });
        }else{
            category.parentid =  $("#parentId").val();
            category.level = 0;//todo
            $.post(ctx+"/platform/wordCategoryCreate",category,function(result){
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
                $.post(ctx+"/platform/wordCategoryRemove",{categoryId:id},function(result){
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
    $.post(ctx + "/platform/wordCategoryList", function (resultData) {
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
            title: '模板分类',
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
    $.post(ctx + "/platform/wordCategoryList", function (resultData) {
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
            title: '模板分类',
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
    console.log(params)
    // params.modelContent = $("#indexContent").val();
    return params;
}
function searchData() {
    var params = getParams();
    $('#gridPanel').Datagrid('options').queryParams = params;
    $('#gridPanel').Datagrid('reload');
}
function loadGridData() {
    var options = {
        url: ctx + "/platform/docTemplateList",
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
                    {field: 'fileName', title: '模板名称', width: 110, halign: 'center', align: 'center'},
                    {field: 'createTimeStr', title: '上传时间', width: 80, halign: 'center', align: 'center'},
                    {field: 'updateTimeStr', title: '更新时间', width: 80, halign: 'center', align: 'center'},
                    {field: 'fileContent', title: '模板说明', width: 80, halign: 'center', align: 'center'},
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
    // var edit = "<a href='#'  class='easyui-linkbutton' onclick=addEditIndex('"+row.id+"');>编辑</a>";
    // var del = "<a href='#'  class='easyui-linkbutton' onclick=deleteIndex('"+row.id+"');>删除</a>";
    // return del;
    // var look = "<a href='#' title=\"查看\" class='easyui-linkbutton' onclick=look('"+row.id+"');><i class='icon-search'></i></a>";
    var edit = "<a href='#'  title=\"编辑\" class='easyui-linkbutton' onclick=edit('"+row.id+"');><i class='icon-edit'></i></a>";
    var del = "<a href='#'  title=\"删除\" class='easyui-linkbutton' onclick=deleteIndex('"+row.id+"');><i class='icon-trash'></i></a>";
    var linkbtn = '';
    if($("#treeMenu ul li.active").attr("cateid")!= 'root'){
        linkbtn = linkbtn + edit+ ' / ';
    }
    return linkbtn + del;
}

function rowChangeColor(value, row) {
    var show = "";
    show = "<font color=red>" + value + "</font><br>";
    return show;
}

function deleteIndex(indexid) {
    console.log("数据id" + indexid)
    myconfirm('确定删除此记录？(此操作不可逆)',{},function(b){
        if(b){
            var param = {
                id : indexid
            };
            myajax(ctx + '/platform/deleteWordIndex', param, function(data) {
                tipMsg(data.msg);
                setTimeout(function() {
                    openProgress("页面刷新中...");
                    setTimeout(function() {
                        location.reload();
                        searchData();
                    }, 500);
                }, 1000);
            });
        }
    });
}

function upload(){
    var file = $("#file").val();
    console.log(file)
    if(file == ""){
        tipMsg("请选择模板文件，再进行上传");
        return;
    }
    if (!RegExp("doc").test(file)) {
       tipMsg("请上传word文件！");
       return;
    }
    var i = openProgressExt("上传中...");
    $("#uploadForm").ajaxSubmit({
        //type: 'post',
        url: ctx + '/datasource/upload',
        success: function(data) {
            closeProgressExt(i);
            if(data != '')
                tipMsg('上传成功');
            else
                tipMsg('上传失败');
            $("#filePath").val(data);
        }
    });
}

function saveWordTemplate(){
    var param = $('#fileEditForm').serialize();
    console.log(param);
    var filePath = $("#filePath").val();
    if(filePath == ""){
        tipMsg("请先上传模板文件，再进行保存");
        return;
    }
    // if($("#fileVersion").val() == ""){
    //     tipMsg("模板版本不能为空.");
    //     return;
    // }
    var i = openProgressExt("保存中...");
    myajax(ctx + '/platform/wordFileSave',param,function(data){
        closeProgressExt(i);
        if(data.status == -1){
            myconfirm('有相同模板文件名和版本的文件存在，确认要替换吗？(此操作不可逆)',{},function(b){
                if(b){
                    var j = openProgressExt("保存中...");
                    myajax(ctx + '/platform/wordFileSave?cover=1',param,function(data){
                        closeProgressExt(j);
                        tipMsg(data.msg);
                        setTimeout(function(){
                            back();
                        },1000);
                    });
                }
            });
        }else{
            tipMsg(data.msg);
            setTimeout(function(){
                back();
            },1000);
        }

    });
}

function look(id){
    var url = ctx + '/platform/wordFileLook?id=' + id;
    top.openDialog('查看','1000px','600px',url,function(){

    });
}

function edit(id){
    window.location.href = ctx + '/platform/wordFileEdit?id=' + id;
}


function wordTemplateAdd(indexid) {

    var $el = $("#treeMenu li a.active");
    var id = $el.parent().attr('data-id');
    if (undefined == id) {
        tipMsg('请选择模板分类！');
        return;
    }
    if ('root' == id) {
        tipMsg('根节点不能创建模板分类！');
        return;
    }
    window.location.href = ctx + '/platform/wordTemplateAdd?categoryId=' + id;

}

function search(){
    searchData();
}

function back() {
    window.history.go(-1);
}

function callBackCopyIndex(indexId,category){
    // var json = {id: category,modelId:modelId};
    // myajax(ctx+"/platform/setmodelValue",json,
    //     function(data){});
    window.location.href = ctx + '/platform/setindexValue?categoryId='+category+ '&indexId='+indexId;
}