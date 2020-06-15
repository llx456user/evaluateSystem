/**
 * 
 */
$(function(){

	//导航
	var st = 180;
    $('#nav_all>li').mouseenter(function () { 
    	var x = $(this).offset().top;   
    	var y =$(this).offset().left;
    	
        $(this).find('ul').css({position:'fixed',left:y}).stop(false, true).slideDown(st);
    }).mouseleave(function () {
        $(this).find('ul').stop(false, true).slideUp(st);
    });
	//日期
	if($(".form-date").length > 0){
		for (var i = 0,len = $(".form-date").length; i < len; i++ ) {
			var el = $(".form-date")[i];
			$(el).datetimepicker({
	  			language:  "zh-CN",
	  			weekStart: 1,
	  			todayBtn:  1,
	  			autoclose: 1,
	  			todayHighlight: 1,
	  			startView: 2,
	  			minView: 2,
	  			forceParse: 0,
	  			format: "yyyy-mm-dd"
	  		});
			
			if (el.value === '') {
				$(el).datetimepicker('setDate', new Date());
			} 
		}
	}
	if($(".form-date-empty").length > 0){
		for (var i = 0,len = $(".form-date-empty").length; i < len; i++ ) {
			var el = $(".form-date-empty")[i];
			$(el).datetimepicker({
	  			language:  "zh-CN",
	  			weekStart: 1,
	  			todayBtn:  1,
	  			autoclose: 1,
	  			todayHighlight: 1,
	  			startView: 2,
	  			minView: 2,
	  			forceParse: 0,
	  			format: "yyyy-mm-dd"
	  		});
		}
	}
	if($(".form-datetime-empty").length > 0){
		for (var i = 0,len = $(".form-datetime-empty").length; i < len; i++ ) {
			var el = $(".form-datetime-empty")[i];
			$(el).datetimepicker({
	  			language:  "zh-CN",
	  			//weekStart: 1,
	  			todayBtn:  1,
	  			autoclose: 1,
	  			todayHighlight: 1,
	  			//startView: 2,
	  			//minView: 2,
	  			//forceParse: 0,
	  			showMeridian: true,
	  			format: "yyyy-mm-dd hh:ii:ss"
	  		});
		}
	}
	

	//分页
	/*if($(".yxdPageCode").length > 0){
		$(".yxdPageCode").createPage({
		pageCount:parseInt($("#totalPage").val()),
		current:parseInt($("#currentPage").val()),
		backFn:function(p){
			console.log('当前第' + p + '页');
				nextPage(p,10);
			}
		});
	}*/
	
	var nextPage = function(nextPageIndex, pageSize) {
  	  var url;
  	  if (document.forms[0]) {
  		  url = document.forms[0].getAttribute('action');
	  	  if (url.indexOf('?') > -1) {
	  		url += '&currentPage=';
	  	  } else {
	      url += '?currentPage=';
	  	  }
	      url = url + nextPageIndex + "&showCount=" + pageSize;
	      document.forms[0].action = url;
  	      return document.forms[0].submit();
  	  }
  	};
  	
});

var dialog_msg_xcvbnm;
var myTrigger_xcvbnm;
var tipmsg_xcvbnm;
var dialog_msg_layer_index;
// 打开遮罩加载页面
function openProgress(msg){
	dialog_msg_xcvbnm = bootbox.dialog({
	    message: '<p class="text-center">'+msg+'</p>',
	    closeButton: false
	});
}

// 关闭遮罩加载页面
function closeProgress(){
	dialog_msg_xcvbnm.modal('hide');
}

function openProgressExt(msg){
	var randomDialog_index = dialog_msg_xcvbnm + Math.random();
	randomDialog_index = bootbox.dialog({
	    message: '<p class="text-center"><img src="'+ctx+'/resources/images/timg1.gif" style="width:30px;height:30px;margin-right:5px;"/>'+msg+'</p>',
	    closeButton: false
	});
	return randomDialog_index;
}

function closeProgressExt(randomDialog_index){
	randomDialog_index.modal('hide');
}

//打开窗口
function openDialog(title,width,height,url,callback){
	
	myTrigger_xcvbnm = $.zui.modalTrigger;
	var options = {
			title:title,
			url:url,
			width:width,
			height:height,
			position:'center',
			type:'iframe'
	};
	if(callback && typeof(callback) === "function"){
        options.onHide = callback;
    }
	myTrigger_xcvbnm.show(options);
	
}


//关闭窗口
function closeDialog(){
	myTrigger_xcvbnm.close();
}

function openLayerDialog(title,width,height,url,callback){
	var w,h ;
	w = String(width).indexOf("%") !=-1 ? width : width +'px';
		
	h =  String(height).indexOf("%") !=-1 ?height : height +'px';
	var options = {
			  type: 2,
			  title: title,
			  shadeClose: true,
			  shade: 0.8,
			  area: [w,h],
			  content: url
			};
	if(callback && typeof(callback) === "function"){
        options.end = callback;
    }
	dialog_msg_layer_index = layer.open(options); 
}

//关闭窗口
function closeLayerDialog(){
	//myTrigger_xcvbnm.close();
	layer.close(dialog_msg_layer_index);
}
function myconfirm(msg,setting,callback){
	var length = arguments.length;
	var callback_ = callback ? callback : setting;
	var options = {
	    message: msg,
	    buttons: {
	        confirm: {
	            label: '确认',
	            className: 'btn-success'
	        },
	        cancel: {
	            label: '取消',
	            className: 'btn-danger'
	        }
	    },
	    callback: callback_
	};
	if(length === 3 && setting){
		options.buttons = {
				
				confirm: {
		            label: setting.ok,
		            className: 'btn-success'
		        },
		        cancel: {
		            label: setting.cancel,
		            className: 'btn-danger'
		        }
		};
	}
	bootbox.confirm(options);

}

function myalert(msg,setting,callback){
	var length = arguments.length;
	var callback_ = callback ? callback : setting;
	var options = {
		message: msg,
		buttons: {
			confirm: {
				label: '确认',
				className: 'btn-success'
			}
		},
		callback: callback_
	};
	if(length === 3 && setting){
		options.buttons = {

			confirm: {
				label: setting.ok,
				className: 'btn-success'
			},
			cancel: {
				label: setting.cancel,
				className: 'btn-danger'
			}
		};
	}
	bootbox.confirm(options);

}


function tipMsg(msg){
	tipmsg_xcvbnm = new $.zui.Messager(msg, {
	    icon: 'info-sign',
	    placement: 'top' // 定义显示位置
	}).show();
}

function disableAllChecked(tableId){
	$("#"+tableId+" table thead tr").eq(0).find(" th.check-all").removeClass("check-btn").removeClass("check-all").prop("style","width:30px;");
}

function disableRowChecked(tableId,index){
	$("#"+tableId+" table tbody tr").eq(index).find(" td.check-row").removeClass("check-btn").removeClass("check-row").prop("style","width:30px;");
}

/*var formValidate = function(options){
	if(options.rules){
		var obj = options.rules;
		for(var name in obj){
			var rule = obj[name];
			if('required' == rule){
				$("name=["+name+"]").append("必填项")
			}
		}
	}
}*/

var formValidate = function(formId){
	if(formId){
		var form = $("#" + formId);
		var i = 0;
		form.find("input,textare,select").each(function(){
			//var placeholder = $(this).attr("placeholder");
			var placeholder = $(this).attr("label");
			var msg = $(this).attr("msg");
			var regx = $(this).attr("regx");
			var required = $(this).attr("required");
			var isNull = false;
			var isWrong = false;
			if(required || $(this).hasClass("required") || $(this).parent().hasClass("required") || $(this).parent().prev().hasClass("required")){
				if($(this).val() == ""){
					isNull = true;
					
				}
			}
			
			if(regx){
				if( regx == "required"){
					if($(this).val() == ""){
						isNull = true;
					}
				}
				if(!regx.test($(this).val())){
					isWrong = true;
		        }
			}
			
			if(isNull){
				tipMsg(placeholder ? placeholder + "为必填项":"该项为必填项");
				$(this).focus();
				i++;
				return false;
			}
			if(isWrong){
				tipMsg(msg ? msg : (placeholder ? placeholder +"未通过验证":"该项未通过验证"));
				$(this).focus();
				i++;
				return false;
			}
		});
		if(i > 0){
			return false;
		}
		
		return true;
	}
	return false;
}

function myajax(url,json,sucCallback,failCallback){
	var options = {
		    url:url,
		    type:'POST', //GET
		    async:true,    //或false,是否异步
		    data:json,
		    timeout:30000,    //超时时间
		    dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
		    success:function(data,textStatus,jqXHR){
		        console.log(data)
		        console.log(textStatus)
		        console.log(jqXHR)
		    },
		    error:function(xhr,textStatus){
		        console.log('错误')
		        console.log(xhr)
		        console.log(textStatus)
		    }
		};
	if(sucCallback){
		options.success = sucCallback;
	}
	if(failCallback){
		options.error = failCallback;
	}
	$.ajax(options);
}

function repeatCategory(id){
	window.location.reload();
}