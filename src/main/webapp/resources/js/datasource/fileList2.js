
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
                    { field: 'filePath',hidden:"true"},
                    {  title: '序号', width: 30,formatter: function(value, row,index){
                    	return index+1;
                    }},
                    { field: 'fileName', title: '文件名称', width: 110, halign:'center',align:'center' },
                    { field: 'fileVersion', title: '文件版本', width: 50, halign:'center',align:'center' },
                    { field: 'fileSize', title: '文件大小', width: 60, halign:'center',align:'center' },
                    { field: 'showname', title: '上传用户', width: 80, halign:'center',align:'center' },
                    { field: 'updateTimeStr', title: '上传时间', width: 100, halign:'center',align:'center' }, 
                    { field: 'field4', title: '文件操作', width: 60,formatter : function(value, row) { 
                    	return getLink(value, row);}
                    }
                
            ]}
			},
			tables:{
				checkable: true,
			    sortable: false,
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
	var look         = "<a href='#'  class='easyui-linkbutton' onclick=look('"+row.id+"');><i class='icon-search'></i></a>";
	var edit         = "<a href='#'  class='easyui-linkbutton' onclick=edit('"+row.id+"');><i class='icon-edit'></i></a>";
	var del         = "<a href='#'  class='easyui-linkbutton' onclick=del('"+row.id+"');><i class='icon-trash'></i></a>";
	var linkbtn = '';
	if($("#treeMenu ul li.active").attr("cateid")!= 'root'){
		linkbtn = linkbtn + edit+ ' / ';
	}
	return look  ;
	
}

function rowChangeColor(value,row){
	 
	 var show ="";
		 show = "<font color=red>"+value+"</font><br>" ;
	 return show;  
}

function search(){
	searchData();
}
function select(){
	
	var pId = $("#pId").val();
	 var frameid = $("#frameid").val();
	var rows = $('#gridPanel').Datagrid('getChecked');
	var len = rows.length;
        if (len != 1) {
            tipMsg('请选择一条文件信息！');
            return;
        }
        //var frameid = $("#frameid").val();alert(frameid);
       top.document.getElementById(frameid).contentWindow.callBackSelectFiles(pId,rows[0].filePath,rows[0].fileName);
       top.closeDialog();
}


function look(id){
	var url = ctx + '/datasource/fileLook?id=' + id;
	openDialog('查看','80%','90%',url,function(){
		
	});
}


function back(){
	
	window.history.go(-1);
}


