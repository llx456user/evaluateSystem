
// 显示列表

$(document).ready(function() {
	loadData();
	
	$("#searchBtn").click (function() {
		searchData();
	});
});

function getParams(){
	return {field1:$("#field1").val()};
}
function searchData(){
	var params = getParams();
	$('#gridPanel').Datagrid('options').queryParams = params;
	$('#gridPanel').Datagrid('reload');
}

function loadData(){
	var options = {
			url:ctx+"/demo/query",
			other:{
				pageSize:10,
			    currentPage:1,
			    data: {
			        cols: [
                    { field: 'field1',hidden:"true"},
                    { field: 'field2', title: '字段一', width: 110, halign:'center',align:'center' },
                    { field: 'field3', title: '格式化字段', width: 150, halign:'center',align:'center' , 
                    	formatter: function(value, row,index) { return rowChangeColor(value, row);}
                    }, 
                    { field: 'field4', title: '操作字段', width: 60,formatter : function(value, row) { 
                    	return getLink(value, row);}
                    }
                
            ]}
			},
			tables:{
				checkable: false,
			    sortable: false,
			    //checkedClass: "checked",
			    colHover:true,
			    hoverClass:'hover',
			    minFixedLeftWidth: 300
			   
			}
	};
	$('#gridPanel').Datagrid('load',options);
}

function getLink(value, row){ 
	
	var look         = "<a href='#'  class='easyui-linkbutton' onclick=alert('look');>查看</a><br>";
	return look ;
	
}

function rowChangeColor(value,row){
	 
	 var show ="";
		 show = "<font color=red>"+value+"</font><br>" ;
	 return show;  
}