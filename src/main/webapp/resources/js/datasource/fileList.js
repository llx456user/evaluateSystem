
// 显示列表

$(document).ready(function() {
	
	
	$("#selectPageSize").change(function(){
		$('#gridPanel').Datagrid('options').other.pageSize = $(this).val();
		searchData();
	});
	loadData();
	
	$("#searchBtn").click (function() {
		searchData();
	});
	
	init();
});
function init(){
	$('#treeMenu').on('click', 'a', function() {
		if($(this).parent().hasClass("has-list")){
			return;
		}
	    $('#treeMenu li.active').removeClass('active');
	    $(this).closest('li').addClass('active');
	    searchData();
	});
	$('#searchInputExample').searchBox({
	    escToClear: true, // 设置点击 ESC 键清空搜索框
	    onSearchChange: function(searchKey, isEmpty) {
	    	
	    	$("#treeMenu li a").parent().hide();
	        $("#treeMenu li a").each(function(o){
	        	console.log('搜索框文本变更：', $(this).text());
	        	if($(this).text().indexOf(searchKey) != -1){
	        		$(this).parent().show();
	        		if($(this).parent().parents("li").hasClass("has-list")){
	        			$(this).parent().parents("li").addClass("open").show();
	        		}
	        	}
	        });
	    }
	});
	
	$("#addCategoryPage").on('hidden.zui.modal',function(){
		$("#categoryId").val("");
		$("#categoryName").val("");
	})
	
	$("#addCategoryPage").on('shown.zui.modal',function(){
		
	})
	
	
}
function getParams(){
	return {categoryid:$("#treeMenu li.active").attr("cateid"),
		field:$("#searchField").val()};
	
}
function searchData(){
	var params = getParams();
	$('#gridPanel').Datagrid('options').queryParams = params;
	$('#gridPanel').Datagrid('reload');
}

function loadData(){
	var options = {
			url:ctx+"/datasource/fileList",
//			queryParams:getParams(),
			other:{
				pageSize:10,
			    currentPage:1,
			    data: {
			        cols: [
                    { field: 'id',hidden:"true"},
                    {  title: '序号', width: 30, sort:false,formatter: function(value, row,index){
                    	return index+1;
                    }},
                    { field: 'fileName', title: '文件名称', width: 110, halign:'center',align:'center' },
                    { field: 'fileVersion', title: '文件版本', width: 50, halign:'center',align:'center' },
                    { field: 'fileSize', title: '文件大小', width: 60, halign:'center',align:'center' },
                    { field: 'showname', title: '上传用户', width: 60, halign:'center',align:'center' },
                    { field: 'fileContent', title: '文件说明', width: 60, halign:'center',align:'center' },
                    { field: 'updateTimeStr', title: '上传时间', width: 100, halign:'center',align:'center' }, 
                    { field: 'field4', title: '文件操作', width: 60,sort:false,formatter : function(value, row) {
                    	return getLink(value, row);}
                    }
                
            ]}
			},
			tables:{
				checkable: false,
			    sortable: true,
			    //checkedClass: "checked",
			    colHover:true,
			    hoverClass:'hover',
			    minFixedLeftWidth: 300
			   
			},
			onLoadSuccess:function(data){
				if(data.rows.length == 0){
					$('.table-datatable tbody').html('<tr><td>没有相关数据</td></tr>');
				}
			}
	};
	$('#gridPanel').Datagrid('load',options);
}

function getLink(value, row){ 
	var look         = "<a href='#' title=\"查看\" class='easyui-linkbutton' onclick=look('"+row.id+"');><i class='icon-search'></i></a>";
	var edit         = "<a href='#'  title=\"编辑\" class='easyui-linkbutton' onclick=edit('"+row.id+"');><i class='icon-edit'></i></a>";
	var del         = "<a href='#'  title=\"删除\" class='easyui-linkbutton' onclick=del('"+row.id+"');><i class='icon-trash'></i></a>";
	var linkbtn = '';
	if($("#treeMenu ul li.active").attr("cateid")!= 'root'){
		linkbtn = linkbtn + edit+ ' / ';
	}
	return look  + ' / ' + linkbtn + del;
	
}

function rowChangeColor(value,row){
	 
	 var show ="";
		 show = "<font color=red>"+value+"</font><br>" ;
	 return show;  
}

function saveCategory(){
	var categoryName = $("#categoryName").val();
	var categoryId = $("#categoryId").val();
	var param = {categoryName:categoryName,categoryId:categoryId};
	myajax(ctx + '/datasource/saveCategory',param,function(data){
		tipMsg(data.msg);
		
		$("#addCategoryPage").modal('hide');
		setTimeout(function(){
			openProgress("页面刷新中...");
			setTimeout(function(){
				location.reload();
			},500);
		},1000);
	});
}
function addCategory() {
    setTimeout("$('#categoryName').focus()",300);
    $("#addCategoryPage input").val("");
    $("#addCategoryPage").modal('show');
}
function editCategory(el){
    setTimeout("$('#categoryName').focus()",300);
    $("#addCategoryPage input").val("");
    $("#addCategoryPage h4").text("编辑分类");
	var cateid = $(el).parent().attr("cateid");
	// var name = $(el).prev().text();
	$("#categoryId").val(cateid);
	// $("#categoryName").val(name);
	$("#addCategoryPage").modal('show');
}

function delCategory(el){
	var cateid = $(el).parent().attr("cateid");
	myconfirm('确定删除此记录？(此操作不可逆)',{},function(b){
		if(b){
			var param = {categoryId:cateid};
			myajax(ctx + '/datasource/delCategory',param,function(data){
				tipMsg(data.msg);
				setTimeout(function(){
					location.reload();
				},1000);
			});
		}
	});
}
function search(){
	searchData();
}
function add(){
	
	var $el= $("#treeMenu li.active");
	var id = $el.attr('cateid');
	if( undefined == id){
		tipMsg('请选择文件分类！');
		return;
	}
	if('root' == id){
		tipMsg('请选择自定义子分类！');
		return;
	}
	window.location.href = ctx + '/datasource/fileAdd?categoryId=' + id;
}

function save(){
	var param = $('#fileEditForm').serialize();
	var filePath = $("#filePath").val();
	if(filePath == ""){
		tipMsg("请先上传文件，再进行保存");
		return;
	}
	if($("#fileVersion").val() == ""){
		tipMsg("文件版本不能为空.");
		return;
	}
	var i = openProgressExt("保存中...");
	myajax(ctx + '/datasource/fileSave',param,function(data){
		closeProgressExt(i);
		if(data.status == -1){
			myconfirm('有相同文件名和版本的文件存在，确认要替换吗？(此操作不可逆)',{},function(b){
				if(b){
					var j = openProgressExt("保存中...");
					myajax(ctx + '/datasource/fileSave?cover=1',param,function(data){
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
	var url = ctx + '/datasource/fileLook?id=' + id;
	top.openDialog('查看','1000px','600px',url,function(){
		
	});
}

function edit(id){
	window.location.href = ctx + '/datasource/fileEdit?id=' + id;
}

function del(id){
	myconfirm('确定删除此条记录？(此操作不可逆)',{},function(b){
		if(b){
			var param = {id:id};
			myajax(ctx + '/datasource/fileDel',param,function(data){
				tipMsg(data.msg);
				setTimeout(function(){
					searchData();
				},1000)
			});
		}
	});
}

function back(){
	
	window.history.go(-1);
}

function upload(){
	var file = $("#file").val();
	if(file == ""){
		tipMsg("请选择文件，再进行上传");
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

