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
    $.post(ctx + "/assess/assessCategoryList",{"projectId":$("#projectId").val()}, function (resultData) {
        /*for(var i in resultData){
            resultData[i].title = resultData[i].indexName;
            resultData[i].url = "#";
        }
        var rootNode = [{
            id: 'root',
            title: '根节点',
            url: '#',
            open: true,
            children: resultData
        }];
        $('#treeMenu').tree({
            data: rootNode
        });*/
    	for(var i in resultData){
            resultData[i].title = resultData[i].indexName;
            resultData[i].url = "#";
            resultData[i].parentid = resultData[i].parentid;
            resultData[i].open= true;
        }
    	$('#treeMenu').tree({
    		data: arrayToJson(resultData)
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
 var key=tmpMap[treeArray[i]["parentid"]];
  
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

function getParams() {
    var $el = $("#treeMenu li a.active");
    var id = $el.parent().attr('data-id');
    var params = {};
    params.nodeId = id;
    params.projectId = $("#projectId").val();
    params.assessName = $("#parameterKey").val();
    return params;
}
function searchData() {
    var params = getParams();
    $('#gridPanel').Datagrid('options').queryParams = params;
    $('#gridPanel').Datagrid('reload');
}
function loadGridData() {
    var options = {
        url: ctx + "/assess/assessList",
        queryParams:{projectId:$("#projectId").val()},
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
                    {field: 'assessName', title: '评估名称', width: 110, halign: 'center', align: 'center'},
                    {field: 'assessStatusStr', title: '评估状态', width: 110, halign: 'center', align: 'center'},
                    {field: 'assessContent', title: '评估描述', width: 80, halign: 'center', align: 'center'},
                    {
                        field: 'updateTime', title: '更新时间', width: 80, halign: 'center', align: 'center'
                        // formatter: function (value, row, index) {
                        //     return timestampToTime(value);
                        // }
                    }
                    
                     ,
                     {
                         field: 'field4', title: '评估操作', width: 60,sort:false,
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
//    var look = "<a href='#'  class='icon-search' onclick=startAssess('"+row.id+"');>开始</a>";
//     var view = "<a href='#'  class='icon-edit' onclick=assessNode('"+row.id+"');>查看</a>	";
//     var edit = "<a href='#'  class='icon-edit' onclick=exportAssess('"+row.id+"');>报告</a>	";
//     var del = "<a href='#'  class='icon-trash' onclick=delAssess('"+row.id+"');>删除</a>	";

    var look = "<a href='#'  class='easyui-linkbutton' onclick=exportAssess('"+row.id+"');>报告</a>";
    var del = "<a href='#'  class='easyui-linkbutton' onclick=delAssess('"+row.id+"');>删除</a>";
    return look+ ' / '  + del;
}

function rowChangeColor(value, row) {
    var show = "";
    show = "<font color=red>" + value + "</font><br>";
    return show;
}

function referenceTemplateAssess() {
    var $el = $("#treeMenu li a.active");
    var id = $el.parent().attr('data-id');
    if (undefined == id) {
        tipMsg('请选择节点！');
        return;
    }
    if ('root' == id) {
        tipMsg('根节点不能创建评估！');
        return;
    }
    var i = openProgressExt("数据模板选择页面加载中...");
    var projectId = $("#projectId").val();
    $.ajax({
        url:ctx + '/assess/assessSave',
        type:'POST',
        data:{nodeId:id},
        success:function(data){
            closeProgressExt(i);
            if(data.status==1){
                // window.location.href = ctx + '/assess/assessProjectParam?id='+data.id;
                window.location.href = ctx + '/project/dataTemplateSelect?projectInfoId=' + projectId + '&id='+data.id;
            }else{
                tipMsg(data.msg);
            }
        }
    });
}
function addAssess() {
    var projectName = document.getElementById("projectName").value;
    var $el = $("#treeMenu li a.active");
    var id = $el.parent().attr('data-id');
    if (undefined == id) {
        tipMsg('请选择节点！');
        return;
    }
    if ('root' == id) {
        tipMsg('根节点不能创建评估！');
        return;
    }
    var i = openProgressExt("创建评估中...");
    $.ajax({
        url:ctx + '/assess/assessSave',
        type:'POST',
        data:{nodeId:id},
        success:function(data){
            closeProgressExt(i);
            if(data.status==1){
            	window.location.href = ctx + '/assess/assessStart?nodeId=' + id+'&id='+data.id+'&projectName='+encodeURI(encodeURI(projectName));
            }else{
                tipMsg(data.msg);
            }
        }
    });
    
    
}
//TODO 开始评估
function startAssess(id){
	window.location.href = ctx + '/assess/assessStart?id=' + id;
}

/**
 * 查看
 * @param id
 */
function assessNode(id){
    window.location.href = ctx + '/assess/assessNode?id=' + id;
}
function exportAssess(id){
	window.location.href = ctx + '/assess/assessView?id=' + id;
}

function delAssess(id){
	myconfirm('确定删除此记录？(此操作不可逆)',{},function(b){
		if(b){
			var i = openProgressExt("删除中...");
			var param = {
					id : id
				};
			myajax(ctx + '/assess/assessDel', param, function(data) {
				closeProgressExt(i);
				tipMsg(data.msg);
				 searchData();
			});
		}
	});
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
