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
		<!--
        <div class="panel" style="margin-bottom: 5px;">
	        <div class="panel-heading">
			<strong>指标项</strong>
		</div>
	    <div class="panel-body">
			<form id="fileEditForm" method="post">
			  <input type="hidden" name="id" id="id" value="${bean.id }"/>
			  <input type="hidden" name=projectId id="projectId" value="${bean.projectId }"/>

			  <div class="form-group">
			    <label for="indexName">指标名称</label>
			    <input type="text" class="form-control" id="indexName" name="indexName" placeholder="" value="${bean.indexName }" readonly="readonly">
			  </div>
			  
			  <div class="form-group">
			    <label for="remark">指标备注</label>
			    <textarea class="form-control" id="remark" name="remark" placeholder="">${bean.remark }</textarea>
			  </div>
			  
			  <div class="form-group">
			    <label for="fileContent">计算类型</label>
			    <select class="form-control">
			    	<option value="1">AHP</option>
			    </select>
			  </div>


			 </form>
			 
			
		</div>
	</div>-->
	<!-- 子节点权重 -->
	<div class="panel" style="margin-bottom: 5px;" id="multiDroppableContainer">
        <div class="panel-heading">
		<strong>AHP设置</strong>
		</div>
	    <div class="panel-body">
	    	 <table class="table table-striped" id="table">
			  <tr>
			  	<td>#</td>
			  	<td>BB</td>
			  	<td>CC</td>
			  	<td>DD</td>
			  </tr>
			  <tr>
			  	<td>BB</td>
			  	<td>1</td>
			  	<td>2</td>
			  	<td>2</td>
			  </tr>
			</table>
			 <button class="btn btn-primary" type="button" onclick="save();">保存</button>
			 <button class="btn btn-default" type="button" onclick="back();">关闭</button>
		</div>
		<form id="fileEditForm" method="post">
			<input type="hidden" name="id" id="id" value="${bean.id }"/>
			<input type="hidden" name=projectId id="projectId" value="${bean.projectId }"/>
		</form>
	</div>
	
	
	<script type="text/javascript">
	var selectedIndexIds = [];
	function back(){
		top.closeLayerDialog();
	}

	/***********************************  保存时操作的相关改造 ******************************************/

	//计算
    function calc(){
        checkxArray=true ;
        var id=$("#id").val();
        var projectId=$("#projectId").val();
        var param={};
        var params = {id:id,
            projectId:projectId};
        param.paramsStr = (params);
        param.xArrayJsonStr = (getXarray());
        param.yArrayJsonStr = (getYarray());
        param.weightJsonStr = (getWeight());

        if(!checkxArray){
            tipMsg("请设置矩阵的所有值");
            return ;
        }
        $.ajax({
            url:ctx + '/project/calcProjectIndex',
            data:{reqJson:JSON.stringify(param)},
            dataType:'JSON',
            success:function(data){
                if(data.status==1){
                     setWeight(JSON.parse(data.info)) ;
                }else{
                    tipMsg(data.msg);
                }
            }
        });

	}


	// 改造一下保存事件  添加以下内容
	function save(){
        checkxArray=true ;
        checkweight=true ;
		var id=$("#id").val();
		var projectId=$("#projectId").val();
		var param={};
		var params = {id:id,
				projectId:projectId};
		param.paramsStr = (params);
		//param.xArrayJsonStr = (getXarray());
		//param.yArrayJsonStr = (getYarray());
		param.weightJsonStr = (getWeight());
       // if(!checkxArray){
        //    tipMsg("请设置矩阵的所有值");
		//	return ;
		//}
        if(!checkweight){
            tipMsg("请点击计算，计算权重值");
            return ;
        }
		$.ajax({
			url:ctx + '/project/saveWeightSet',
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

	

	

	
	
	var xArray = ${xArray};
	var yArray = ${yArray};
	var childsJson = ${childsJson};
	var weightJson = ${weightJson};
	var checkxArray=true ;
    var checkweight=true ;
	var keys = [];
	$(function(){
		
		var th = [];
		th.push('<td>#</td>');
		for(var key in yArray[0]){
	　　　　th.push('<td>' + childsJson[key] + '</td>');
			keys.push(key);
	　　}
		var thHtml = '<tr>' + th.join('') + '</tr>';
		
		var html = [];
		for(var i=0,l=xArray.length;i<l;i++){
			var td = [];
			
			td.push('<td>'  + childsJson[keys[i]] + '</td>');
		　　for(var key in xArray[i]){
				td.push('<td><input type="hidden" name="ipt" class="form-control" value="'  + xArray[i][key] + '"/></td>');
		　　}
			var tr = '<tr>' + td.join('') + '</tr>';
		    html.push(tr);

		}
		var weightHtml = [];
		weightHtml.push('<td>权重</td>');
		for(var key in weightJson){
			weightHtml.push('<td><input type="text" name="weightIpt" class="form-control" value="'  + weightJson[key] + '"/></td>');
			
		}
		
		$("#table").html( thHtml+weightHtml.join(''));
		
		//console.log(getXarray());
		//console.log(getYarray());
		console.log(getWeight());
	});
	function getByColNum(rows,colNum){
		var cols = [];
        for(var i=1;i<rows.length-1;i++){ //遍历表格的行
           for(var j=1;j<rows[i].cells.length;j++){  //遍历每行的列
        	   
        	   if(colNum == j)
             	cols.push($(rows[i].cells[colNum]).find("input[name=ipt]").val());
           }
        }
        return cols;
	}
	function getByRowNum(rows,rNum){
		var rowArr = [];
        for(var i=1;i<rows.length-1;i++){ //遍历表格的行
           for(var j=1;j<rows[i].cells.length;j++){  //遍历每行的列
        	   if(rNum == i)
           		rowArr.push($(rows[rNum].cells[j]).find("input[name=ipt]").val());
           }
        }
        return rowArr;
	}
	function getYarray(){
		var tab=document.getElementById("table");
        var rows=tab.rows;
        var len = rows[0].cells.length;
		var xarray = [];
		for(var i = 1;i<len;i++){
			var tmp = {};
			var cols = getByColNum(rows,i);
			for(var j=0,l=keys.length;j<l;j++){
				tmp[keys[j]] = cols[j];
			}
			xarray.push(tmp);
		}
		console.log(xarray);
		return xarray;
	}
	
	function getXarray(){
		var tab=document.getElementById("table");
        var rows=tab.rows;
        var len = rows.length;
		var xarray = [];
		for(var i = 1;i<len -1;i++){
			var tmp = {};
			var cols = getByRowNum(rows,i);
			for(var j=0,l=keys.length;j<l;j++){
			    if(cols[j]==undefined||cols[j]==null||cols[j]==''){
                    checkxArray=false;
				}
				tmp[keys[j]] = cols[j];
			}
			xarray.push(tmp);
		}
		console.log(xarray);
		return xarray;
	}
	
	function getWeight(){
		var tmp = {};
		$("#table").find("input[name=weightIpt]").each(function(i,el){
            var wValue=$(el).val() ;
            if(wValue==undefined||wValue==null||wValue==''){
                checkweight=false;
            }
			tmp[keys[i]] = $(el).val();
		});
		return tmp;
	}

	function  setWeight(json) {
        $("#table").find("input[name=weightIpt]").each(function(i,el){
            $(el).val(json[keys[i]]);
        });
    }
	</script>
</body>
</html>