<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
	<title>评估系统-指标调试</title>
	<style>
	.active{
		border:1px solid red;
	}
	
	</style>
</head>
<body>
	<div class="container">

		 <%-- <div class="panel" style="margin-bottom: 5px;min-height:200px;">
		 	<div class="panel-heading">
				入参
			</div>
		    <div class="panel-body">
				<textarea class="form-control" id="nodeInputParam" name="nodeInputParam" placeholder="">${bean.nodeInputParam }</textarea>
		    </div>
		  </div>
		   <div class="panel" style="margin-bottom: 5px;min-height:200px;">
		 	<div class="panel-heading">
		 		<strong>出参</strong>
			</div>
		    <div class="panel-body">
				<textarea class="form-control" id="nodeOutParam" name="nodeOutParam" placeholder="">${bean.nodeOutputParam }</textarea>
		    </div>
		  </div> --%>
		  <table id="table" class="table table-bordered" style="margin-left: 5px;;">
	  		
		  </table>
		  <input type="hidden" id="ipt" value='${bean.nodeInputParam }'/>
		  <input type="hidden" id="opt" value='${bean.nodeOutputParam }'/>
		  <button type="button" class="btn btn-default" data-dismiss="modal" id="closeTestResult" style="margin-left: 5px;margin-bottom: 10px;">关闭</button>
	</div>


	<script type="text/javascript">
	
	//实例  {p1:[1,2,3],p2:2,p3:3}
	var iptParams = $("#ipt").val();
	var optParams = $("#opt").val();
	$(function(){
		
		$("#closeTestResult").click(function(){
			top.closeDialog();
		});
		
		//iptParams = '{"ip1":[1,2,3],"ip2":2,"ip3":3}';
		//optParams = '{"ip1":[1,2,3,4],"ip2":2,"ip3":3}';
		
		var params = {};
		
		var iptParamsJson = {};
		var optParamsJson = {};
		var k = 0;
		var iptLen = 0 , optLen = 0;
		if(iptParams != ''){
			params = JSON.parse(iptParams);
			
			
			for(var key in  params){
				/* iptParamsJson['入参：' + key]=params[key];
				params[key] = '入参：' + key; */
				iptParamsJson['' + key]=params[key];
				params[key] = '' + key;
				iptLen++;
			}
			k++;
		}
		if(optParams != ''){
			params = JSON.parse(optParams);
			for(var key in  params){
				/* optParamsJson['出参：' + key]=params[key];
				params[key] = '出参：' + key; */
				optParamsJson['' + key]=params[key];
				params[key] = '' + key;
				optLen++;
			}
			k++;
		}
		// console.log(iptParams);
		// console.log("=============入参：");
		// console.log(iptParamsJson);
		// console.log("==============出参：");
		// console.log(optParamsJson);
		if(k == 2){
			params = mergeJsonObject(iptParamsJson,optParamsJson);
		}
		
		if(!isEmptyObject(params)){
			createTable(params,iptLen,optLen);
		}
		
		
		
	});

	function getJsonLength(jsonData) {
		var length;
		for(var ever in jsonData) {
		    length++;
		}
		return length;
	}
	
	function createTable(params,iptLen,optLen){
		var th =[];
		
		
		var maxLen = getMaxLen(params);
		var keys = [];
		// console.log(params);
		for(var i in params){
			th.push('<td>'+i+'</td>');
			keys.push(i);
		}
		var thStr = '<tr>'+  th.join('')  + '</tr>';
		
		var trStr = '';
		for(var i = 0 ; i< maxLen;i++){
			
			var tr= [];
			for(var j = 0 ; j<keys.length ; j++){
				// console.log(keys[j]);
				var value = getValue(keys[j],params,i);
				// console.log(value);
				tr.push('<td>'+value+'</td>');
			}
			trStr += '<tr>'+tr.join('') + '</tr>';
			
		}
		
		var html = thStr + trStr;
		html = "<tr><th colspan='"+iptLen+"'>入参</th><th colspan='"+optLen+"'>出参</th></tr>" + html;
		$("#table").html(html);
	}
	
	function isEmptyObject(e) {  
	    var t;  
	    for (t in e)  
	        return !1;  
	    return !0  
	} 
	
	function getMaxLen(json){
		var isArr = false;
		var maxLen = 1;
		for(var key in  json){
			if(json[key] instanceof Array){
				isArr = true;
				var tmpLen = json[key].length;
				if(tmpLen > maxLen){
					maxLen = tmpLen;
				}
			}
		}
		
		return maxLen;
	}
	
	function getValue(key,json,i){
		var vArr =  json[key];
		if(vArr instanceof  Array){
			return vArr[i] == undefined ? '' : vArr[i];
		}
		return i == 0 ? vArr:'';
	}
	var mergeJsonObject = function (jsonbject1, jsonbject2) {
		var resultJsonObject={};
		for(var attr in jsonbject1){
		resultJsonObject[attr]=jsonbject1[attr];
		}
		for(var attr in jsonbject2){
		resultJsonObject[attr]=jsonbject2[attr];
		}
		return resultJsonObject;
		};

	</script>
</body>
</html>