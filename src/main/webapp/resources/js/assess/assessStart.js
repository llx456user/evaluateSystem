$(document).ready(function () {

    // zuiupload();
});


    // var filePath_pId;
    function zuiupload(pId) {
        filePath_pId=pId;
        var options = {
            // 初始化选项
			chunk_size:0,
            autoUpload: true,            // 当选择文件后立即自动进行上传操作
            url: ctx + '/assess/upload',  // 文件上传提交地址
            browse_button: '#uploaderBtn_'+ pId,
            drop_element: 'self',
            multi_selection:false,
            deleteActionOnDone:function(file, doRemoveFile){
            	return true;
			},
            limitFilesCount:false,
            filters:{
                prevent_duplicates:false
            },
            responseHandler: function (responseObject, file) {
                if (responseObject.response != '') {
                    $("#filepreview_" + pId).val(file.name);
                    $("#filePath_" + pId).val(responseObject.response);
                }
            }
        };
        $('#myuploader_'+pId).uploader(options);
    }



//原先提交方式
// function upload(pId){
// 	var file = $("#file_"+pId).val();
// 	if(file == ""){
// 		tipMsg("请选择文件，再进行上传");
// 		return;
// 	}
//
// 	var i = openProgressExt("上传中...");
// 	$("#uploadForm_"+pId).ajaxSubmit({
// 		//type: 'post',
// 	    url: ctx + '/assess/upload',
// 	    success: function(data) {
// 	    	closeProgressExt(i);
// 	    	if(data != '')
// 	    	tipMsg('上传成功');
// 	    	else
// 	    	tipMsg('上传失败');
// 	    	$("#filePath_"+pId).val(data);
// 	    }
// 	});
// }

function selectFiles(pId){
	var fid= self.frameElement.getAttribute('id');
	var url = ctx + '/datasource/fileList2?frameid='+fid+"&pId="+pId;
	top.openDialog('选择文件','1000px', '600px',url,function(data){
		//TODO
		console.log(data);
	});
}
function callBackSelectFiles(pId,filePath,fileName){

	$("#filePath_"+pId).val(filePath);
	// $("#fileshow_"+pId).attr('data-drag-placeholder',filePath);
    $("#filepreview_" + pId).val(fileName);
}

// 加载模板
// function paramUpload() {
//     var assessId = document.getElementById("assessId").value;
//     var projectId = document.getElementById("projectId").value;
//     var i = openProgressExt("加载中...");
//     var showAddTemplateFlg = true;
//     // window.location.href = ctx + '/assess/assessProjectParam?id='+assessId;
//     window.location.href = ctx + '/project/dataTemplateSelect?projectInfoId=' + projectId + '&id='+assessId+'&showAddTemplateFlg='+showAddTemplateFlg;
// }

//TODO 评估
function assess(value){
	console.log(value)
	var arr=getFormJson("assessParam");
	for(var i=0;i<arr.length;i++){
		if(arr[i].value==""){
			tipMsg('参数不能为空！');
	        return;
		}
	}
	 var assessContent= document.getElementById("assessContent").value;
	 var assessName= document.getElementById("assessName").value;
     var assessId=document.getElementById("assessId").value;
	 var param={
			 params:JSON.stringify(arr),
			 assessContent:assessContent,
             assessId:assessId,
             assessName:assessName
	 }
	 param=JSON.stringify(param)
//	var param=JSON.stringify(arr);
	console.log(param);
	
	 var i = openProgressExt("评估中...");
	 
	$.ajax({
        url:ctx + '/assess/assessHandle',
        type:'POST',
        contentType : 'application/json',
        data:param,
        success:function(data){
            closeProgressExt(i);
            if(data.status==1){
                tipMsg(data.msg);
                setTimeout(function(){
                    if (value == 1){
                        back();
                    } else {
                        backTemplate();
                    }
                },1000);
            }else{
                tipMsg(data.msg);
            }
        }
    });
	
}
function groupAssess() {
    var arr=getFormJson("assessParam");
    for(var i=0;i<arr.length;i++){
        if(arr[i].value==""){
            tipMsg('参数不能为空！');
            return;
        }
    }
    console.log(JSON.stringify(arr))
    // 解析分组数据
    var templateArr = [];
    for(var j=0;j<arr.length;j++){
        if(arr[j].value.indexOf("_") > 0){
            templateArr.push(arr[j]);
        }
    }
    console.log(templateArr);
    //请求后台查询分组数据
    var projectId = document.getElementById("projectId").value;
    var groupParamSuitNumber = document.getElementById("groupParamSuitNUmber").value;
    var assessContent= document.getElementById("assessContent").value;
    var assessName= document.getElementById("assessName").value;


    if (templateArr.length > 0){
        var groupParam = {
            params:JSON.stringify(arr),
            groupParams:JSON.stringify(templateArr),
            projectId: projectId,
            groupParamSuitNumber:groupParamSuitNumber
        };
        groupParam=JSON.stringify(groupParam)
        $.ajax({
            url:ctx + '/project/getGroupTemplateData',
            type:'POST',
            contentType : 'application/json',
            data:groupParam,
            success:function(data){
                if (data){
                    console.log(data)
                    if (data) {
                        var assessParamArr = data.assessParamResultLists;
                        var assessNameArr = data.assessNameResultLists;
                        console.log(assessParamArr)
                        console.log(assessNameArr)
                        assessBatch(0,assessParamArr.length,assessParamArr,assessContent,assessName,1,assessNameArr);
                    }else {
                        tipMsg("请设置分组模板数据!")
                    }

                }

            }
        });
    }
}
// 同步循环调用评估 每次评估调用assessSave方法生成一条评估记录
function assessBatch(index,total,result,assessContent,assessName,suitNumber,assessNameArr) {
    var id=document.getElementById("nodeId").value;
    var assessId=document.getElementById("assessId").value;
    var k = openProgressExt("共" + total +"套数据,评估第"+suitNumber+"套中...");
    var assessDataName = assessName + "_" + assessNameArr[index];

    // 第一次评估
    if (index ==0){
        var temArr = result[index];
        console.log(temArr)
        var param={
            params:JSON.stringify(temArr),
            assessContent:assessContent,
            assessId:assessId,
            assessName:assessDataName,
            isFirstFlag:true
        }
        param=JSON.stringify(param)
        console.log(param,suitNumber)
        $.ajax({
            url:ctx + '/assess/assessHandle',
            type:'POST',
            contentType : 'application/json',
            data:param,
            success:function(data){
                closeProgressExt(k);
                if(data.status==1){
                    tipMsg("第"+ suitNumber + "套" + data.msg);
                }else{
                    tipMsg("第"+ suitNumber + "套" + data.msg);
                }
                if (index+1 < total){
                    assessBatch(index+1,total,result,assessContent,assessName,suitNumber+1,assessNameArr);
                }
                if (suitNumber == total){
                    setTimeout(function(){
                        backTemplate();
                    },1000);
                }
            }
        });
    } else {
        // 第二次，三次.....评估
        $.ajax({
            url:ctx + '/assess/assessSave',
            type:'POST',
            data:{nodeId:id},
            success:function(data){
                if(data.status==1){
                    var temArr = result[index];
                    console.log(temArr)
                    var param={
                        params:JSON.stringify(temArr),
                        assessContent:assessContent,
                        assessId:data.id,
                        assessName:assessDataName,
                        isFirstFlag:false
                    }
                    param=JSON.stringify(param)
                    console.log(param,suitNumber)
                    $.ajax({
                        url:ctx + '/assess/assessHandle',
                        type:'POST',
                        contentType : 'application/json',
                        data:param,
                        success:function(data){
                            closeProgressExt(k);
                            if(data.status==1){
                                tipMsg("第"+ suitNumber + "套" + data.msg);
                            }else{
                                tipMsg("第"+ suitNumber + "套" + data.msg);
                            }
                            if (index+1 < total){
                                assessBatch(index+1,total,result,assessContent,assessName,suitNumber+1,assessNameArr);
                            }
                            if (suitNumber == total){
                                setTimeout(function(){
                                    backTemplate();
                                },1000);
                            }
                        }
                    });
                }else{
                    tipMsg("第"+ suitNumber + "套" + data.msg);
                    if (suitNumber == total){
                        setTimeout(function(){
                            backTemplate();
                        },1000);
                    }
                }
            }
        });
    }
}

function getFormJson(inputName){
	var arr = [];
	$("input[name="+inputName+"]").each(function(){
		var json = {
			key:$(this).attr("key"),
			value:$(this).val()
		};
		arr.push(json);
	});

	return arr;
	}
function back(){
	window.history.go(-1);
}

function backTemplate() {
    window.history.go(-3);
}

// 加载模板
function paramUpload() {
    var assessId = document.getElementById("assessId").value;
    var projectId = document.getElementById("projectId").value;
    var assessName= document.getElementById("assessName").value;
    var projectName= document.getElementById("projectName").value;
    var assessContent= document.getElementById("assessContent").value;
    var i = openProgressExt("加载中...");
    var showAddTemplateFlg = true;
    // window.location.href = ctx + '/assess/assessProjectParam?id='+assessId;
    window.location.href = ctx + '/project/dataTemplateSelect?projectInfoId=' + projectId + '&id='+assessId+'&showAddTemplateFlg='+showAddTemplateFlg+'&assessName='+encodeURI(encodeURI(assessName))+'&assessContent='+encodeURI(encodeURI(assessContent))+'&projectName='+encodeURI(encodeURI(projectName));
}

function paramGroupUpload() {
    var assessId = document.getElementById("assessId").value;
    var projectId = document.getElementById("projectId").value;
    var assessName= document.getElementById("assessName").value;
    var assessContent= document.getElementById("assessContent").value;
    var projectName= document.getElementById("projectName").value;
    var i = openProgressExt("加载中...");
    window.location.href = ctx + '/project/groupDataTemplateSelect?projectInfoId=' + projectId + '&id='+assessId+'&assessName='+encodeURI(encodeURI(assessName))+'&assessContent='+encodeURI(encodeURI(assessContent))+'&projectName='+encodeURI(encodeURI(projectName));
}
