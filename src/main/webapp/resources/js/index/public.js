$(function(){
	$.ajaxSetup({
        type: "POST",  
        error: function(jqXHR, textStatus, errorThrown){ 
//        	window.top.location.href="sysError";
        }
	})
	
	if ($.fn.datagrid){
		$.fn.datagrid.defaults.onLoadError = function() {
			
//			window.top.location.href="sysError";
		};
	}
})
 if(!window.console){        window.console = {};    }    if(!window.console.log){        window.console.log = function(msg){};    }