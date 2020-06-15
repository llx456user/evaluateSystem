
// 显示列表

$(document).ready(function() {
	

	$(".datasource button").click(function(){
		if($(this).hasClass("btn-mysql")){
			$("#addDbPage").modal('show');
		}
	});
	
	$("#addDbPage").off('hidden.zui.modal').on('hidden.zui.modal', function() {
		  clearForm();
	});
	
	

});



function setParams(data){
	$("#form input[name=dbId]").val(data.id);
	$("#form input[name=sourceName]").val(data.sourceName);
	$("#form input[name=sourceType]").val(data.sourceType);
	$("#form input[name=server]").val(data.config.server);
	$("#form input[name=url]").val(data.config.url);
	$("#form input[name=username]").val(data.config.username);
	$("#form input[name=password]").val(data.config.password);
}

function getParams(){
	
	var dbId = $("#form input[name=dbId]").val();
	var sourceName = $("#form input[name=sourceName]").val();
	var sourceType = $("#form input[name=sourceType]").val();
	var server = $("#form input[name=server]").val();
	var url = $("#form input[name=url]").val();
	var username = $("#form input[name=username]").val();
	var password = $("#form input[name=password]").val();
	
	var config = {
			server:server,
			url:url,
			username:username,
			password:password
		};
	return {
		id:dbId,
		sourceName:sourceName,
		sourceType:sourceType,
		dbType:'0',
		config:JSON.stringify(config)
	};
	
}

function clearForm(){
	
	$("#form").get(0).reset();
	$("#form input[name=dbId]").val("");
}

function toEdit(el){
    setTimeout("$('#categoryName').focus()",300);
	var $el = $(el).parent().parent();
	var config = $el.find("input[name=config]").val();
	var sourceName = $el.find("input[name=sourceName]").val();
	var sourceType = $el.find("input[name=sourceType]").val();
	var dbId = $el.find("input[name=dbId]").val();
	var data = {
			id:dbId,
			sourceName:sourceName,
			sourceType:sourceType,
			config:JSON.parse(config)
			
	};
	
	console.log(data)
	setParams(data);
	$("#addDbPage").modal('show');
}

function save(){
	var param = getParams();
	var i = openProgressExt("保存中...");
	if(formValidate("form")){
		myajax(ctx + '/datasource/dbSave',param,function(data){
			//appendHTML(param);
			closeProgressExt(i);
			tipMsg(data.msg);
			if(data.status == 1){
				$("#addDbPage").modal('hide');
			}
			setTimeout(function(){
				reload();
			},1000);
		});
	}
}


function del(id){
	myconfirm('确定删除此条记录？(此操作不可逆)',{},function(b){
		if(b){
			var param = {id:id};
			var i = openProgressExt("删除中...");
			myajax(ctx + '/datasource/dbDel',param,function(data){
				closeProgressExt(i);
				tipMsg(data.msg);
				setTimeout(function(){
					reload();
				},1000)
			});
		}
	});
}
function test(){
	if(!formValidate("form")){
		return;
	}
	var p = getParams();
	console.log(p);
	var config = JSON.parse(p.config);
	var param = {
			sourceName:p.sourceName,
			sourceType:p.sourceType,
			server:config.server,
			url:config.url,
			username:config.username,
			password:config.password
	};
	console.log(param);
	myajax(ctx + '/datasource/dbTest',param,function(data){
		tipMsg(data.msg);
	});
	
}

function reload(){
	openProgressExt("加载中...");
	location.reload();
	
}

function appendHTML(data){
	var tpl = $("#hiddenDataSourceTpl").html();
	var tmp = tpl.format(data.sourceName,JSON.parse(data.config).url,data.updateTime);
	$("#datasourceList").append(tmp);
}

String.prototype.format=function()  
{  
  if(arguments.length==0) return this;  
  for(var s=this, i=0; i<arguments.length; i++)  
    s=s.replace(new RegExp("\\{"+i+"\\}","g"), arguments[i]);  
  return s;  
};  