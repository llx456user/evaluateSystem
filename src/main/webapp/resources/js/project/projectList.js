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
		projectName:$("#projectName").val(),
		projectContent:$("#projectContent").val()
	};

}
function searchData() {
	var params = getParams();
	$('#gridPanel').Datagrid('options').queryParams = params;
	$('#gridPanel').Datagrid('reload');
}

function loadData() {
	var options = {
		url : ctx + "/project/projectList",
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
					width : 20,
                    sort:false,
					formatter : function(value, row, index) {
						return index + 1;
					}
				}, {
					field : 'projectName',
					title : '项目名称',
					width : 80,
					halign : 'center',
					align : 'center'
				},  {
					field : 'projectContent',
					title : '项目内容',
					width : 100,
					halign : 'center',
					align : 'center'
				}, {
					field : 'updateTimeStr',
					title : '更新时间',
					width : 80,
					halign : 'center',
					align : 'center'
					// formatter : function(value, row, index) {
					// 	return rowChangeColor(value, row);
					// }
				}, {
					field : 'field4',
					title : '操作',
					width : 160,
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
	// var look = "<a href='#'  class='easyui-linkbutton' onclick=vewProjectInfo('"+row.id+"');>查看</a>";
	var edit = "<a href='#'  class='easyui-linkbutton' onclick=projectNodeTree('"+row.id+"');>编辑</a>";
    var projectNodeTree = "<a href='#'  class='easyui-linkbutton' onclick=projectEvaluation('"+row.id+"','"+row.projectName.trim()+"');>评估</a>";
	var del = "<a href='#'  class='easyui-linkbutton' onclick=deleteProjectInfo('"+row.id+"');>删除</a>";
	var bind = "<a href='#'  class='easyui-linkbutton' onclick=bindWordTemplate('"+row.id+"');>报告模板</a>";
	var param = "<a href='#'  class='easyui-linkbutton' onclick=addOrEditProjectParam('"+row.id+"','"+row.projectName.trim()+"');>数据模板</a>";
	var paramTemplate = "<a href='#'  class='easyui-linkbutton' onclick=addOrEditProjectParamTemplate('"+row.id+"','"+row.projectName.trim()+"');>数据关联</a>";
	return projectNodeTree+' / '+edit+' / '+ del +' / ' + bind + ' / ' + param + ' / ' + paramTemplate;
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
	myajax(ctx + '/project/saveProjectCategory', param, function(data) {
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
function addCategory() {
    setTimeout("$('#categoryName').focus()",300);
    $("#addCategoryPage input").val("");
    $("#addCategoryPage").modal('show');
}
function editCategory(categoryId) {
    setTimeout("$('#categoryName').focus()",300);
    $("#addCategoryPage h4").text("编辑分类");
    $("#addCategoryPage input").val("");
	$("#addCategoryPage").modal('show');
	$("#categoryId").val(categoryId);
}
function deleteCategory(categoryId) {
	myconfirm('确定删除此记录？(此操作不可逆)',{},function(b){
		if(b){
			var param = {
					categoryId : categoryId
				};
			myajax(ctx + '/project/deleteProjectCategory', param, function(data) {
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


function addOrEditProjectInfo(projectInfoId){

	if(projectInfoId != undefined){
		window.location.href = ctx + '/project/toEditProjectInfo?projectInfoId=' + projectInfoId;
	}else{
		var $el= $("#treeMenu li.active");
		var categoryId = $el.attr('cateid');
		if( undefined == categoryId){
			tipMsg('请选择项目分类！');
			return;
		}
		window.location.href = ctx + '/project/toAddProjectInfo?categoryId='+categoryId ;
	}
}

function bindWordTemplate(projectInfoId){

	window.location.href = ctx + '/project/addWordTemplate?projectInfoId=' + projectInfoId;

}

function addOrEditProjectParam(projectInfoId,projectName) {
	var showAddTemplateFlg = false
	console.log("projectName", projectName, "projectInfoId", projectInfoId);
	// window.location.href = ctx + '/project/dataTemplateList?projectInfoId=' + projectInfoId+ '&showAddTemplateFlg='+showAddTemplateFlg +'&projectName='+encodeURI(encodeURI(projectName));
	window.location.href = ctx + '/project/addOrEditProjectParam?projectInfoId=' + projectInfoId +'&projectName='+encodeURI(encodeURI(projectName));
}

function addOrEditProjectParamTemplate(projectInfoId,projectName) {
	console.log("projectName" + projectName);
	window.location.href = ctx + '/project/addOrEditProjectParamTemplate?projectInfoId=' + projectInfoId+'&projectName='+encodeURI(encodeURI(projectName));
}

function deleteProjectInfo(projectInfoId) {
	myconfirm('确定删除此记录？(此操作不可逆)',{},function(b){
		if(b){
			var param = {
					projectInfoId : projectInfoId
				};
			myajax(ctx + '/project/deleteProjectInfo', param, function(data) {
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

function vewProjectInfo(projectInfoId){
	window.location.href = ctx + '/project/vewProjectInfo?projectInfoId='+projectInfoId ;
}


function projectNodeTree(projectInfoId){
    window.location.href = ctx + '/project/projectNodeTree?projectInfoId='+projectInfoId ;
}


function projectEvaluation(projectInfoId,projectName){
//	alert("评估");
//     window.location.href = ctx + '/project/projectNodeTree?projectInfoId='+projectInfoId ;
    var param = {
        projectInfoId : projectInfoId
    };
    myajax(ctx + '/assess/toassessList', param, function(data) {
    	if (data.msg==0){
            window.location.href = ctx + '/assess/assessList?projectId='+projectInfoId+'&projectName='+encodeURI(encodeURI(projectName));;
    	} else {
            tipMsg("请先完成指标树编辑");
		}
    });



}
