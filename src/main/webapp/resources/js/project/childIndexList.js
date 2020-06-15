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

});
function getParams() {
    var $el = $("#treeMenu li a.active");
    var id = $el.parent().attr('data-id');
    var params = {};
    params.categoryId = id;
    params.modelName = $("#parameterKey").val();
    return params;
}
function searchData() {
    var params = getParams();
    $('#gridPanel').Datagrid('options').queryParams = params;
    $('#gridPanel').Datagrid('reload');
}
function loadGridData() {
    var options = {
        url: ctx + "/platform/indexChildList",
        other: {
            pageSize: 10,
            currentPage: 1,
            data: {
                cols: [
                    {field: 'id', hidden: "true"},
                    {field: 'radio', width: 20,formatter:function(value,row,index){
                    	return '<input type="radio" name="myradio" value="'+row.id+'" />';
                    }},
                    {field: 'nodeText', title: '节点名称', width: 110, halign: 'center', align: 'center'},
                    {
                        field: 'updateTimeStr', title: '更新时间', width: 80, halign: 'center', align: 'center',

                    }
                ]
            }
        },
        tables: {
            checkable: false,
            sortable: false,
            //checkedClass: "checked",
            colHover: true,
            hoverClass: 'hover',
            minFixedLeftWidth: 300
        },
        onLoadSuccess:function(){
        	$("div#datatable-gridPanel table.table-datatable tr").click(function(){

        		var $this = $(this);
        		var radio = $this.find("td:eq(1) input[name='myradio']");
        		radio.prop("checked",true);
        	});
        }
    };
    $('#gridPanel').Datagrid('load', options);
}
function searchIndexChild() {
    searchData();
}
function selectIndexChild(indexid) {
	var value = getCheckedRadio("myradio");
	if(value == null){
		tipMsg('请选择一条子节点信息！');
        return;
	}
       var frameid = $("#frameid").val();
       var nodeId = $("#nodeId").val();
       top.document.getElementById(frameid).contentWindow.callBackSetChildIndex(value,nodeId);
       top.closeDialog();

}
function getCheckedRadio(name){
	var radios = document.getElementsByName(name);
	var len = radios.length;
	for(var i = 0; i<len;i++){
		if(radios[i].checked){
			return radios[i].value;
		}
	}
	return null;
}

function back() {
    top.closeLayerDialog();
}
