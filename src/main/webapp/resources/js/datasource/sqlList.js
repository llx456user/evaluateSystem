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
		datasourceId : $("#treeMenu li.active").attr("cateid"),
		sqlName:$("#sqlName").val(),
		dbName:$("#dbName").val()
	};

}
function searchData() {
	var params = getParams();
	$('#gridPanel').Datagrid('options').queryParams = params;
	$('#gridPanel').Datagrid('reload');
}

function loadData() {
	var options = {
		url : ctx + "/sql/sqlList",
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
					field : 'sqlName',
					title : 'sql名称',
					width : 80,
					halign : 'center',
					align : 'center'
				},  {
					field : 'sourceName',
					title : '数据源名称',
					width : 80,
					halign : 'center',
					align : 'center'
				},{
					field : 'sqlStr',
					title : 'sql语句信息',
					width : 100,
					halign : 'center',
					align : 'center'
				}, {
					field : 'updateTimeStr',
					title : '更新时间',
					width : 100,
					halign : 'center',
					align : 'center',
					formatter : function(value, row, index) {
						return rowChangeColor(value, row);
					}
				}, {
					field : 'field4',
					title : '模型操作',
					width : 60,
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

	var look = "<a href='#'  class='easyui-linkbutton' onclick=vewSql('"+row.id+"');>查看</a>";
	var edit = "<a href='#'  class='easyui-linkbutton' onclick=addOrEditSql('"+row.id+"');>编辑</a>";
	var del = "<a href='#'  class='easyui-linkbutton' onclick=deleteSql('"+row.id+"');>删除</a>";

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

function saveSql() {
	var id = $("#id").val();
	var datasourceId = $("#datasourceId").val();
	var sqlName = $("#sqlName").val();
	var sqlStr = $("#sqlStr").val();
	
	var param = {
			id : id,
			datasourceId : datasourceId,
			sqlName:sqlName,
			sqlStr:sqlStr
	};
	myajax(ctx + '/sql/saveSql', param, function(data) {
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

function deleteSql(id) {
	myconfirm('确定删除此记录？(此操作不可逆)',{},function(b){
		if(b){
			var param = {
					id : id
				};
			myajax(ctx + '/sql/deleteSql', param, function(data) {
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


function addOrEditSql(id){

	if(id != undefined){
		window.location.href = ctx + '/sql/toEditSql?id=' + id;
	}else{
		var $el= $("#treeMenu li.active");
		var datasourceId = $el.attr('cateid');
		if( undefined == datasourceId){
			tipMsg('请选择数据源！');
			return;
		}
		window.location.href = ctx + '/sql/toAddSql?datasourceId='+datasourceId;
	}
}

function vewSql(id){
	window.location.href = ctx + '/sql/vewSql?id='+id ;
}

function testSql(id) {
	var param = {
		id : id
	};
	myajax(ctx + '/sql/test', param, function(data) {
		tipMsg(data.msg);
	});
}
