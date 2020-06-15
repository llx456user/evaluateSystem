// 显示列表

$(document).ready(function () {
    
});

function save() {
    var assessName= document.getElementById("assessName").value;
    var assessVersion= document.getElementById("assessVersion").value;
    var assessContent=document.getElementById("assessContent").value;
    // var assessParam=document.getElementById("assessParam").value;
    var projectId=document.getElementById("projectId").value;
    var indexId=document.getElementById("indexId").value;
    var nodeId=document.getElementById("nodeId").value;
    var id=document.getElementById("assessId").value;
    // var filePath=document.getElementById("filePath").value;

    var param={
    		assessName:assessName,
    		assessVersion:assessVersion,
    		assessContent:assessContent,
    		// assessParam:assessParam,
    		projectId:projectId,
    		indexId:indexId,
    		nodeId:nodeId,
    		// filePath:filePath,
        id:id
    }

    var i = openProgressExt("保存中...");
    //return;
    $.ajax({
        url:ctx + '/assess/assessSave',
        type:'POST',
        data:param,
        success:function(data){
            closeProgressExt(i);
            if(data.status==1){
                tipMsg(data.msg);
                setTimeout(function(){
                	back();
                },1000);
            }else{
                tipMsg(data.msg);
            }
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
	    url: ctx + '/assess/upload', 
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



