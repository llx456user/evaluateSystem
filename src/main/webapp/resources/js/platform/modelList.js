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
		modelName:$("#modelName").val(),
		modelContent:$("#modelContent").val()
	};

}
function searchData() {
	var params = getParams();
	$('#gridPanel').Datagrid('options').queryParams = params;
	$('#gridPanel').Datagrid('reload');
}

function loadData() {
	var options = {
		url : ctx + "/platform/modelList",
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
					sort:false,
					formatter : function(value, row, index) {
						return index + 1;
					}
				}, {
					field : 'modelName',
					title : '模型名称',
					width : 110,
					halign : 'center',
					align : 'center'
				}, {
					field : 'modelVersion',
					title : '模型版本',
					width : 40,
					halign : 'center',
					align : 'center'
				}, {
					field : 'modelContent',
					title : '模型内容',
					width : 60,
					halign : 'center',
					align : 'center'
				}, {
					field : 'updateTimeStr',
					title : '更新时间',
					width : 70,
					halign : 'center',
					align : 'center'
				},{
					field : 'modelStatusStr',
					title : '模型状态',
					width : 40,
					halign : 'center',
					align : 'center'
				}, {
					field : 'field4',
					title : '模型操作',
					width : 90,
					formatter : function(value, row) {
						return getLink(value, row);
					},
                    sort:false
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
			minFixedLeftWidth : 300,
            rowCreator:true


        }
	};
	$('#gridPanel').Datagrid('load', options);
}

function getLink(value, row) {

	var look = "<a href='#'  class='easyui-linkbutton' onclick=vewModeIfo('"+row.id+"');>查看</a>";
	var edit = "<a href='#'  class='easyui-linkbutton' onclick=addOrEditModelInfo('"+row.id+"');>编辑</a>";
	var del = "<a href='#'  class='easyui-linkbutton' onclick=deleteModelInfo('"+row.id+"');>删除</a>";
	var make = "<a href='#'  class='easyui-linkbutton' onclick=make('"+row.id+"');>生成</a>";
	var upload = "<a href='#'  class='easyui-linkbutton' onclick=uploadOpen('"+row.id+"');>上传</a>";

	return look+ ' / '+edit+' / '+ del+' / '+make+' / '+upload;

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
	myajax(ctx + '/platform/saveModelCategory', param, function(data) {
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
			myajax(ctx + '/platform/deleteModelCategory', param, function(data) {
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


function addOrEditModelInfo(modelInfoId){

	if(modelInfoId != undefined){
		window.location.href = ctx + '/platform/toEditModelInfo?modelInfoId=' + modelInfoId;
	}else{
		var $el= $("#treeMenu li.active");
		var categoryId = $el.attr('cateid');
		if( undefined == categoryId){
			tipMsg('请选择模型分类！');
			return;
		}
		window.location.href = ctx + '/platform/toAddModelInfo?categoryId='+categoryId ;
	}
}

function make(id){
	var param = {
			id : id
		};
	
    httpPost(ctx + "/platform/makeModelKj", param);
}
//发送POST请求跳转到指定页面
function httpPost(URL, PARAMS) {
    var temp = document.createElement("form");
    temp.action = URL;
    temp.method = "post";
    temp.style.display = "none";

    for (var x in PARAMS) {
        var opt = document.createElement("textarea");
        opt.name = x;
        opt.value = PARAMS[x];
        temp.appendChild(opt);
    }

    document.body.appendChild(temp);
    temp.submit();

    return temp;
}
function deleteModelInfo(modelInfoId) {
	myconfirm('确定删除此记录？(此操作不可逆)',{},function(b){
		if(b){
			var param = {
					modelInfoId : modelInfoId
				};
			myajax(ctx + '/platform/deleteModelInfo', param, function(data) {
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

function vewModeIfo(modelInfoId){
	window.location.href = ctx + '/platform/vewModeIfo?modelInfoId='+modelInfoId ;
}

function uploadOpen(modelInfoId) {
	$("#uploadDllPage").modal('show');
	$("#modelInfoId").val(modelInfoId);
}

function upload(){
	$("#uploadForm").ajaxSubmit({ 
		//type: 'post', 
	    url: ctx + '/platform/upload', 
	    success: function(data) { 
	    	if(data != ''){
	    		$("#uploadDllPage").modal('hide');
	    		//上传文件成功了，在更新模型文件路径，文件名称、状态等字段
	    		var modelInfoId = $("#modelInfoId").val();
	    		var param = {
	    				modelInfoId : modelInfoId,
	    				dllPath:data
	    			};
	    		myajax(ctx + '/platform/updateDll', param, function(data2) {
					tipMsg(data2.msg);
					setTimeout(function() {
						openProgress("页面刷新中...");
						setTimeout(function() {
							location.reload();
						}, 500);
					}, 3000);
				});
	    		
	    	}
	    }
	});
}



function copyModel(categoryId) {
    var modelframeid= self.frameElement.getAttribute('id');
    var url = ctx + '/platform/setCopyModel?modelframeid='+modelframeid+'&categoryId='+categoryId;
    top.openDialog('选择模型','1000px','650px',url,function(data){
        //TODO
        console.log(data);
    });
}


//等待 后台存储model信息 modelId为选择的模型Id
function callBackSetModel(modelInfoId,category){
    // var json = {id: category,modelId:modelId};
    // myajax(ctx+"/platform/setmodelValue",json,
    //     function(data){});
    window.location.href = ctx + '/platform/setmodelValue?categoryId='+category+ '&modelInfoId='+modelInfoId;
}