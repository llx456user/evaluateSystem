<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
	<title>评估系统-项目新增</title>
<%-- 	<script type="text/javascript" src="<c:url value='/resources/js/platform/modelList.js?version=${jsversion}'/>"></script>
 --%>	
	<style>
	.active{
		border:1px solid red;
	}
	.addBtn{
		color:#3280fc;
	}
	.addBtn:hover{
		cursor:pointer;
		color:#1970fc;
	}

	.trash{
		color:#3280fc;
		margin-left:10px;
	}
	.trash:hover{
		cursor:pointer;
		color:#1970fc;
	}
	
	div.indexDiv{
		width:150px;
		height:150px;
		border: 1px solid #ddd;
		float: left;
		margin-right:10px;
		cursor: move;
		border-radius:0 30px 0 30px;
	}
	.indexTitle{
		height:30px;
		background-color: #d1d1d1;
		text-align: center;
		color:#f00;
		font-size:16px;
		overflow: hidden;
		border-radius:0 30px 0 0;
	}
	.notcanMove .indexTitle{
		background: #f5f5f5;
	}
	</style>
</head>
<body>
	<div class="container">
		
		<!-- 新建文件 -->
        <div class="panel" style="margin-bottom: 5px;">
	        <div class="panel-heading">
			<strong>项目信息</strong>
		</div>
	    <div class="panel-body">
			<form id="fileEditForm" method="post">
			  <input type="hidden" name="id" id="id" value="${bean.id }"/>
			  <input type="hidden" name=categoryId id="categoryId" value="${bean.categoryId }"/>
			  <div class="form-group">
			    <label for="fileName">项目名称</label>
			    <input type="text" class="form-control" id="projectName" name="projectName" placeholder="" value="${bean.projectName }">
			  </div>
			  
			  <div class="form-group">
			    <label for="fileContent">项目内容</label>
			    <textarea class="form-control" id="projectContent" name="projectContent" placeholder="">${bean.projectContent }</textarea>
			  </div>
			  
			  <div class="form-group">
			    <label for="fileContent">指标</label>
			    
			  </div>
			  
			   
			 </form>
			 
			 <button class="btn btn-primary" type="button" onclick="save();">保存</button>
			<button class="btn btn-default" type="button" onclick="back();">返回</button>
		</div>
	</div>
	<!-- 选择指标 -->
	
	<div class="panel" style="margin-bottom: 5px;" id="multiDroppableContainer">
        <div class="panel-heading">
		<strong>指标信息</strong>
		</div>
	    <div class="panel-body">
			<!-- 左侧分类 -->
		    <div class="panel" style="float: left;width:20%;">
		        <div class="panel-heading">
		            指标分类
		        </div>
		        <input id="searchInputExample" autofocus="autofocus" type="search" class="form-control search-input" placeholder="搜索">
		        <ul class="tree tree-lines tree-folders" id="treeMenu">
		        </ul>
		    </div>
		    
		     <div class="panel" style="float: left;width: 50%;">
		        <div class="panel-heading">
		           	选择指标
		        </div>
		        <div class="panel-body" style="min-height:200px;" id="indexListDiv">
		        </div>
		    </div>
				    
			<div class="panel" style="float: left;width:29%;">
		        <div class="panel-heading">
		           	指定指标(双击移除)
		        </div>
		        <div class="panel-body droppable-target" style="min-height:200px;" id="selectedIndexList">
		        </div>
		    </div>
				    	
		</div>
	</div>
	<script type="text/javascript">
	var selectedIndexIds = [];
	function back(){
		
		window.history.go(-1);
	}

	/***********************************  保存时操作的相关改造 ******************************************/
	// 改造一下保存事件  添加以下内容
	function save(){

		var id=$("#id").val();
		var categoryId=$("#categoryId").val();
		var projectName=$("#projectName").val();
		var projectContent=$("#projectContent").val();
		
		if(projectName==""){
			tipMsg("项目名称不能为空");
			return;
		}
		
		var param = {id:id,
				categoryId:categoryId,
				projectName:projectName,
				projectContent:projectContent};
		param.indexIds = selectedIndexIds;
		console.log(JSON.stringify(param));
		
		$.ajax({
			url:ctx + '/project/saveProjectInfo',
			data:{reqJson:JSON.stringify(param)},
			dataType:'JSON',
			success:function(data){
				if(data.status==1){
					tipMsg(data.msg);
					setTimeout(function() {
						openProgress("页面刷新中...");
						setTimeout(function() {
							back();
						}, 500);
					}, 1000);
				}else{
					tipMsg(data.msg);
				}
			}
		});
	}

	function initTree() {
	    generateTreeMenu();
	    $('#treeMenu').on('click', 'a', function () {
//			if($(this).parent().hasClass("has-list")){
//				return;
//			}
	        //$('#treeMenu li.active').removeClass('active');
	        //$(this).closest('li').addClass('active');
	        $("#treeMenu li a.active").removeClass('active');
	        $(this).addClass('active');
	        searchData();
	    });
	    $('#searchInputExample').searchBox({
	        escToClear: true, // 设置点击 ESC 键清空搜索框
	        onSearchChange: function (searchKey, isEmpty) {
	            $("#treeMenu li a").parent().hide();
	            $("#treeMenu li a").each(function (o) {
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
	function generateTreeMenu() {
	    $.post(ctx + "/platform/categoryList", function (resultData) {
	        for(var i in resultData){
	            resultData[i].title = resultData[i].categoryName;
	            resultData[i].url = "#";
	        }
	        var rootNode = [{
	            id: 0,
	            title: '指标分类',
	            url: '#',
	            open: true,
	            children: resultData
	        }];
	        $('#treeMenu').tree({
	            data: rootNode
	        });
	    });
	}
	
	function getParams() {
	    var $el = $("#treeMenu li a.active");
	    var id = $el.parent().attr('data-id');
	    var params = {};
	    params.categoryId = id;
	    return params;
	}
	function searchData() {
	    var params = getParams();
	    params.page = 1;
	    params.rows =1000000000;
	    var url =  ctx + "/platform/indexList";
	    myajax(url,params,function(data){
	    	
	    	var divs = [];
	    	$.each(data.rows,function(i,o){
	    		var div = '<div class="indexDiv" data-id="'+o.id+'"><div class="indexTitle">'+o.indexName+'</div><p>'+o.indexContent+'</p></div>';
	    		divs.push(div);
	    	});
    		$("#indexListDiv").html(divs.join(""));
    		
    		changeIndexItem();
	    });
	}
	
	$(function(){
		initTree();
		
		$("#selectedIndexList ").off('dblclick').on('dblclick','.indexDiv',function(){
			selectedIndexIds.splice($.inArray($(this).attr("data-id"),selectedIndexIds),1);
			$(this).remove();
			changeIndexItem();
		});
	});
	function changeIndexItem(){
		$("#indexListDiv .indexDiv").each(function(){
      	   if($.inArray($(this).attr("data-id"),selectedIndexIds) != -1){
      		   $(this).addClass("notcanMove");
      	   }else{
      		 $(this).removeClass("notcanMove");
      	   }
         });
	}
	</script>
	<script>
  
$('#multiDroppableContainer').droppable({
	selector: '.indexDiv', 
    target: '.droppable-target',
    before: function(e) {
        if($.inArray(e.element.attr("data-id"),selectedIndexIds) != -1 || selectedIndexIds.length>=3){
        	return false;
        }
    },
    start: function(e) {
        if($.inArray(e.element.attr("data-id"),selectedIndexIds) != -1 || selectedIndexIds.length>=3){
        	return false;
        }
    },
    drop: function(event) {
        if(event.target) {
           $("#selectedIndexList").append(event.element.clone());
           selectedIndexIds.push(event.element.attr("data-id"));
           event.target.addClass('notcanMove');
           changeIndexItem();
        }
    },
    drag: function(event) {
        if(event.target) {
        	if($.inArray(event.element.attr("data-id"),selectedIndexIds) != -1 || selectedIndexIds.length>=3){
            	return false;
            }
        }
    }
});
</script>
</body>
</html>