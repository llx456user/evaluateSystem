<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="mobai.com/el-common" prefix="el" %>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>评估系统-评估管理添加</title>
	<!-- 引入 ECharts 文件 -->
	<%-- <script src="<c:url value='/resources/plugins/echarts/echarts.js'/>"></script> --%>
	<script src="<c:url value='/resources/plugins/echarts.min.js'/>"></script>
	<script src="<c:url value='/resources/plugins/echarts-gl.min.js'/>"></script>
	<script src="<c:url value='/resources/plugins/tablesMergeCell.js'/>"></script>
	<script src="<c:url value='/resources/js/assess/FileSaver.js'/>"></script>
	<script src="<c:url value='/resources/js/assess/jquery.wordexport.js'/>"></script>
	<script src="<c:url value='/resources/js/assess/html2canvas.js'/>"></script>
	<script src="<c:url value='/resources/js/assess/jspdf.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/assess/assessNodeView.js?version=${jsversion}'/>"></script>
	<style>
	.active{
		border:1px solid red;
	}
	.indexBtnImg{
		border:1px solid #D2F2EA;    
	    border-radius: 5px;
	    background-repeat: no-repeat;
	    width:90px;
	    height:90px;
	    margin:5px 0 0 5px;
	    background-color: #D2F2EA !important;
	}
	.indexBtnImg:hover{
		border-color:gray;
		cursor: pointer;
		background-color: #efefef !important;
	}
	.indexBtnImg strong{
	    display: block;
	    font-weight: bold;
	    color: #343b42;
	    padding-top: 55px;
	}
	#datasetBtn{
		background: url('${ctx}/resources/images/btn-database1.png') no-repeat center top 10px;
	}
	#modelBtn{
		background: url('${ctx}/resources/images/btn-module1.png') no-repeat center top 10px;
	}
	#chartBtn{
		background: url('${ctx}/resources/images/btn-chart1.png') no-repeat center top 10px;
	}
	#gridBtn{
		background: url('${ctx}/resources/images/grid1.png') no-repeat center top 10px;
	}
	#indexBtn{
		background: url('${ctx}/resources/images/indexTemp1.png') no-repeat center top 10px;
	}
	
	.boxF, .boxS, .boxT, .overlay
        {
            width: 50px;
            height: 75px;
            overflow: hidden;
        }
        .boxF, .boxS
        {
            visibility: hidden;
        }
        .boxF
        {
            transform: rotate(120deg);
            float: left;
            margin-left: 10px;
            -ms-transform: rotate(120deg);
            -moz-transform: rotate(120deg);
            -webkit-transform: rotate(120deg);
        }
        .boxS
        {
            transform: rotate(-60deg);
            -ms-transform: rotate(-60deg);
            -moz-transform: rotate(-60deg);
            -webkit-transform: rotate(-60deg);
        }
        .boxT
        {
            transform: rotate(-60deg);
            background: no-repeat 50% center;
            background-size: 125% auto;
            -ms-transform: rotate(-60deg);
            -moz-transform: rotate(-60deg);
            -webkit-transform: rotate(-60deg);
            visibility: visible;
            background-color: #73797D;
            padding-top:30px;
            color:#fff;
        }
        #contants{
        	padding: 3px 0px 8px 8px;
        }
        .boxY{
        	 transform: rotate(-45deg);
            width:45px;
            height:45px;
            -ms-transform: rotate(-45deg);
            -moz-transform: rotate(-45deg);
            -webkit-transform: rotate(-45deg);
            visibility: visible;
            background-color: #73797D;
            padding-top:10px;
            color:#fff;
        }
        .boxZ{
        	 transform: rotate(45deg);
        	  -ms-transform: rotate(45deg);
            -moz-transform: rotate(45deg);
            -webkit-transform: rotate(45deg);
        }
        #if{
        	padding: 20px 0px 0px 20px;
        }
        
        .dropdown-menu{
        	text-align: left;
        }
        
        .showGrid{
        	width:90px;
        	height:90px;
        	background:#ddd;
        	border-radius:5px;
        }
        .xiaoTitle{
        	font-family: '黑体';font-weight: bold;font-size: 21.3px;
        }
        
        body{
        	font-family: '宋体';font-size: 18.7px;
        }
        
        .table-bordered td, .table-bordered th{
        	font-family: '宋体';font-size: 18.7px;
        }
        
        .picNo{font-size: 16px;margin: 0 auto;text-align: center;display: block;font-weight: bold;margin-bottom: 10px;}
	</style>
</head>
<body>
	<div class="container">
		<div id="pTitle" style="text-align: center;font-family: '黑体';font-size: 21.3px;">[${projectInfo.projectName }]评估报告</div>
		<!-- 新建评估 -->
        <div class="panel" style="margin-bottom: 5px;">
	        <div class="panel-heading">
				<span class="xiaoTitle">项目描述</span>
				<div style="float:right;margin-top: -6px;">
				<button class="btn btn-primary" type="button" onclick="export1('${id}');">导出</button>
				<button class="btn btn-primary" type="button" onclick="back();">返回</button>
				</div>
			</div>
		    <div class="panel-body" id="pDesc">
		    	概述：${projectInfo.projectContent }
			</div>
	</div>
    <div class="panel" style="margin-bottom: 5px;">
	        <div class="panel-heading">
				<div class="xiaoTitle">指标体系</div>
			</div>
		   <!--
		    <div class="panel-body" id="treeMenu" style="height:350px;width:100%">
			</div>
			-->
		    <div  id="myDiagramDiv" style=" vertical-align: top; padding: 5px;width:100%; height:300px"></div>
		    <font class="picNo">图 1</font>
	</div>
    <div class="panel" style="margin-bottom: 5px;">
	        <div class="panel-heading">
				<div class="xiaoTitle">评估结果</div>
			</div>
		    <div class="panel-body">
		    	<table class="table table-bordered" id="table"></table>
			</div>
	</div>
    <div class="panel" style="margin-bottom: 5px;">
	        <div class="panel-heading">
				<div class="xiaoTitle">指标结果</div>
			</div>
		    <div class="panel-body">
	    	    <c:set var="c" value="2"></c:set>
		    	<c:forEach var="e" items="${assessProjectIndexs }" varStatus="sta">
		    	    <div class="topp">
		    		<div style="font-family: '宋体';font-size: 18.7px;font-weight: bold;">${sta.count}、[${e.indexName }]${e.indexValue }</div>
			    	<c:forEach items="${e.picTypes }" var="y" varStatus="sta">

					<c:if test="${e.indexValue!=null}">
			    	<div id="${e.id }_${y.nodeType }${e.indexId}_${y.nodeKey}" class="echartsDiv" t="${y.nodeType}" nodeKey="${y.nodeKey}" indexId="${e.indexId}" projectId="${projectId}"  assessId="${assessId}" style="width: 90%;height:350px;"></div>
				    	<%-- <div style="margin-left: 36%;margin-bottom: 20px;">
				    	<button onclick="addSize('${e.id }_${y.nodeType }${e.indexId}_${y.nodeKey}')" class="btn btn-primary">放大</button>
				    	<button onclick="reduceSize('${e.id }_${y.nodeType }${e.indexId}_${y.nodeKey}')" class="btn btn-primary">缩小</button>
				    	</div> --%>
				    	<font class="picNo">图 ${c }</font>
				    	<c:set value="${c + 1 }" var="c"></c:set>
					</c:if>
			    	</c:forEach>
			    	<c:forEach items="${e.tables }" var="y">
			    	<table class="table table-bordered mytable" json='${el:toJsonString(y)}'>
			    		<tr><td colspan="${y.colspan }">${y.title }</td></tr>
			    		<c:forEach items="${y.data }" var="d">
			    		<tr>
			    		<c:forEach items="${d }" var="z">
			    		<td>${z }</td>
			    		</c:forEach>
			    		</tr>
			    		</c:forEach>
			    	</table>
			    	</c:forEach>
			    	</div>
			    	<%-- <div id="${e.id }xxx" class="echartsDiv" t="scatter" indexid="1" style="width: 100%;height:350px;"></div> --%>
		    	</c:forEach>
			</div>
	</div>
    <%-- <div class="panel" style="margin-bottom: 5px;">
	        <div class="panel-heading">
				<strong>图表展示</strong>
			</div>
		    <div class="panel-body">
		    	<c:forEach items="${picTypes }" var="e">
		    	<div id="${e }" style="width: 100%;height:350px;"></div>
		    	</c:forEach>
		    	<div id="pie" style="width: 100%;height:350px;"></div>
		    	<div id="bar" style="width: 100%;height:350px;"></div>
		    	<div id="line" style="width: 100%;height:350px;"></div>
		    	<div id="Hline" style="width: 100%;height:350px;"></div>
		    	<div id="scatter" style="width: 100%;height:350px;"></div>
			</div>
	</div> --%>
	<!-- <div id="scatter3d" style="width: 100%;height:350px;"></div> -->
	<div id="pInfo">
		评估时间：${assessTime }
		&nbsp;&nbsp;&nbsp;&nbsp;评估人员：${showName }
	</div>
	<input type="hidden" id="assessResult" value="${assessResult }"/>
	<input type="hidden" id="projectId" value="${projectId }"/>
	
</div>
<!-- <div id="hiddenDiv"></div> -->
<iframe id='hiddenIframe' name="hiddenIframe" style="display: none;"></iframe>
<form id="myform" method="post">
<input type="hidden" id="info" name="info"/>
<input type="hidden" id="title" name="title"/>
</form>

<script type="text/javascript">
//最小值
Array.prototype.min = function(){
	var min = this[0];
	var len = this.length;
	for(var i=1; i<len; i++){
		if(this[i] < min){
			min = this[i];
		}
	}
	return min;
}
 
//最大值
Array.prototype.max = function(){
	var max = this[0];
	var len = this.length;
	for(var i=1; i<len; i++){
		if(this[i] > max){
			max = this[i];
		}
	}
	return max;
}
function addSize(id){
	var oldHeight = $("#"+id).height();
	var oldWidth = $("#"+id).width();
	var newHeight = oldHeight + 50;
	var newWidth = oldWidth + 50;
	$("#"+id).css("height",newHeight);
	$("#"+id).css("width",newWidth);
	 echarts.init(document.getElementById(id)).resize();
}
function reduceSize(id){
	var oldHeight = $("#"+id).height();
	var oldWidth = $("#"+id).width();
	var newHeight = oldHeight - 50;
	var newWidth = oldWidth - 50;
	$("#"+id).css("height",newHeight);
	$("#"+id).css("width",newWidth);
	 echarts.init(document.getElementById(id)).resize();
}
function back(){
	window.history.go(-1);
}

var treeImg ;


function getColorByTheme(){
	var themeurl = $("#zuiTheme").attr("href");
	var curTheme = themeurl.split("theme/")[1];
	if(curTheme.split(".")[0] == "_blue"){
		return '#4a3670';
	}else if(curTheme.split(".")[0] == "blue"){
		return '#0288d1';
	}else if(curTheme.split(".")[0] == "black"){
		return '#222';
	}else if(curTheme.split(".")[0] == "green"){
		return '#4caf50';
	}
}


$(function(){
	//评估结果
	/* var assessResult = $("#assessResult").val();
	
	assessResult = '{"ip1":[1,3,3,4],"ip2":[2,2],"ip3":3}';
	var params = {};
	if(assessResult != ''){
		params = JSON.parse(assessResult);
	}
	
	if(!isEmptyObject(params)){
		createTable(params);
	} */

    var initNodeArray=JSON.parse('${projectNodeTreeJson}');
    for(var i in initNodeArray){
   	　　initNodeArray[i].color = getColorByTheme();
   	}

    treeImg=init(initNodeArray);




	$(".echartsDiv").each(function(){
		if($(this).attr("t") == "pie"){
			var indexId = $(this).attr("indexId");
			//饼图
			genPie($(this).attr("id"),ctx + '/generate/pic?type=pie&indexId='+indexId+'&nodeKey='+$(this).attr("nodeKey")+'&assessId='+$(this).attr("assessId"));
		}else if($(this).attr("t") == "bar"){
            //柱状图
			genBar($(this).attr("id"),ctx + '/generate/pic?type=bar&indexId='+$(this).attr("indexId")+'&nodeKey='+$(this).attr("nodeKey")+'&assessId='+$(this).attr("assessId"));
		}else if($(this).attr("t") == "line"){
            //折线图
			genLine($(this).attr("id"),ctx + '/generate/pic?type=line&indexId='+$(this).attr("indexId")+'&nodeKey='+$(this).attr("nodeKey")+'&assessId='+$(this).attr("assessId"));
		}else if($(this).attr("t") == "Hline"){
			genHline($(this).attr("id"),ctx + '/generate/pic?type=Hline&indexId='+$(this).attr("indexId")+'&nodeKey='+$(this).attr("nodeKey")+'&assessId='+$(this).attr("assessId"));
		}else if($(this).attr("t") == "scatter"){
            //散点图
			//genScatter($(this).attr("id"),ctx + '/generate/pic?type=scatter&indexId='+$(this).attr("indexId")+'&nodeKey='+$(this).attr("nodeKey")+'&assessId='+$(this).attr("assessId"));
		}else if($(this).attr("t") == "hscatter"){
			gen3dScatter($(this).attr("id"),ctx + '/generate/pic?type=hscatter&indexId='+$(this).attr("indexId")+'&nodeKey='+$(this).attr("nodeKey")+'&assessId='+$(this).attr("assessId"));
		}
	});


	/* if(document.getElementById("pie")){
		genPie(ctx + '/generate/pic?type=pie');
	}
	if(document.getElementById("bar")){
		genBar(ctx + '/generate/pic?type=bar');
	}
	if(document.getElementById("line")){
		genBar(ctx + '/generate/pic?type=line');
	}
	if(document.getElementById("Hline")){
		genBar(ctx + '/generate/pic?type=Hline');
	}
	if(document.getElementById("scatter")){
		genBar(ctx + '/generate/pic?type=scatter');
	} */
	
	
	//示例
	/* initMyChart_bar(["p1","p2","p3"],["1","2","3"])
	initMyChart_pie(["p1","p2","p3"],[{"value":"1","name":"p1"},{"value":"2","name":"p2"},{"value":"3","name":"p3"}]);
	initMyChart_line(["p1","p2","p3"],["1","2","3"]);
	initMyChart_Hline(["p1","p2","p3"],["1","2","3"]);
	initMyChart_scatter([{"name":"p1","value":[1,4,5]},{"name":"p2","value":[5,6,5]}]); */
	
	
	/*  var myChart = echarts.init(document.getElementById("scatter3d"));
	$.get('${ctx }/resources/js/t.json', function (data) {
	    var symbolSize = 2.5;
	    option = {
	        grid3D: {},
	        xAxis3D: {
	            type: 'category'
	        },
	        yAxis3D: {},
	        zAxis3D: {},
	        dataset: {
	            dimensions: [
	                'Income',
	                'Life Expectancy',
	                'Population',
	                'Country',
	                {name: 'Year', type: 'ordinal'}
	            ],
	            source: data
	        },
	        series: [
	            {
	                type: 'scatter3D',
	                symbolSize: symbolSize,
	                encode: {
	                    x: 'Country',
	                    y: 'Life Expectancy',
	                    z: 'Income',
	                    tooltip: [0, 1, 2, 3, 4]
	                }
	            }
	        ]
	    };

	    myChart.setOption(option);
	}); */
});



function genPie(id,url){
	$.ajax({
        url: url,
        async: true,
        type: 'GET',
        dataType: 'json',
        timeout: 10000,
        cache: false,
        success: function (data) {
            //成功时执行初始化资金方日进件量图表函数
            initMyChart_pie(id,data.title,data.invNames,data.loanCounts);
        }
    });

}
function genBar(id,url){
	$.ajax({
        url: url,
        async: true,
        type: 'GET',
        dataType: 'json',
        timeout: 10000,
        cache: false,
        success: function (data) {
            //成功时执行初始化资金方日进件量图表函数
            console.log(data)
            initMyChart_bar(id,data.title,data.xtitle,data.ytitle,data.invNames,data.loanCounts);
        }
    });
}
function genLine(id,url){
	var now = new Date().getTime();
	$.ajax({
        url: url,
        async: true,
        type: 'GET',
        dataType: 'json',
        timeout: 10000,
        cache: false,
        success: function (data) {
        	
            //成功时执行初始化资金方日进件量图表函数
            initMyChart_line(id,data.title,data.xtitle,data.ytitle,data.invNames,data.loanCounts);
        }
    });

}
function genHline(id,url){
	$.ajax({
        url: url,
        async: true,
        type: 'GET',
        dataType: 'json',
        timeout: 10000,
        cache: false,
        success: function (data) {
            //成功时执行初始化资金方日进件量图表函数
            initMyChart_Hline(id,data.title,data.xtitle,data.ytitle,data.invNames,data.loanCounts);
        }
    });

}
function genScatter(id,url){
	$.ajax({
        url: url,
        async: true,
        type: 'GET',
        dataType: 'json',
        timeout: 10000,
        cache: false,
        success: function (data) {
            //成功时执行初始化资金方日进件量图表函数
            initMyChart_scatter(id,data.title,data.xtitle,data.ytitle,data.loanCounts);
        }
    });

}
function gen3dScatter(id,url){
	$.ajax({
        url: url,
        async: true,
        type: 'GET',
        dataType: 'json',
        timeout: 10000,
        cache: false,
        success: function (data) {
            //成功时执行初始化资金方日进件量图表函数
            initMyChart_3dscatter(id,data.title,data.xtitle,data.ytitle,data.ztitle,data.loanCounts);
        }
    });

}
var myCharts = [];

function initMyChart_bar(id,title,xtitle,ytitle,invNames,loanCounts){
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById(id),'default');
    myCharts.push({id:id,myChart:myChart});
    var datas = [];
	 var legendData = [];
	 for(var i = 0;i < loanCounts.length; i++) {
	 	var d = loanCounts[i];
	 	legendData.push(d.name);
	 	datas.push({
            name: d.name,
            type: 'bar',
            data: d.data,
            itemStyle:{
                normal:{
                    /* color:'#87CEFF' */
                }
            }
        })
	 }
    // 指定图表的配置项和数据
    var option = {
    		title : {
		        text: title
		    },
        tooltip: {},
        legend: {
            data:legendData
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '5%',
            containLabel: true
        },
        
        // toolbox: {
	    //     show : true,
	    //     feature: {
	    //         dataZoom: {
	    //             yAxisIndex: false
	    //         },
	    //         saveAsImage: {
	    //             pixelRatio: 2
	    //         }
	    //     }
	    // },
        toolbox: {
            show : true,
            feature : {
                dataZoom: {
                    yAxisIndex: false
                },
                mark : {show: true},
                dataZoom : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        xAxis: {
			name:xtitle,
            data: invNames,
            axisLabel: {  
         	   interval:0,  
         	   rotate:40  
         	} 
        },
        yAxis: {
			name:ytitle
		},
        series:datas
    };


    myChart.setOption(option);
}



function initMyChart_pie(id,title,invNames,loanCounts){
	 var myChart = echarts.init(document.getElementById(id));
	 myCharts.push({id:id,myChart:myChart});
	option = {
		    title : {
		        text: title,
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient : 'vertical',
		        x : 'left',
		        data:invNames
		    },
        grid: {
            left: '3%',
            right: '1%',
            bottom: '5%',
            containLabel: true
        },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            magicType : {
		                show: true, 
		                type: ['pie', 'funnel'],
		                option: {
		                    funnel: {
		                        x: '25%',
		                        width: '50%',
		                        funnelAlign: 'left',
		                        max: 1548
		                    }
		                }
		            },
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    series : [{
			    name:'统计',
			    type:'pie',
			    radius : '55%',
			    center: ['50%', '60%'],
			    data:loanCounts
			}]	    
		};
	myChart.setOption(option);                   
}
function initMyChart_line(id,title,xtitle,ytitle,invNames,loanCounts){
	 var myChart = echarts.init(document.getElementById(id));
	 myCharts.push({id:id,myChart:myChart});
	 var datas = [];
	 var legendData = [];
	 var now = new Date().getTime();
	 

	 for(var i = 0;i < loanCounts.length; i++) {
	 	var d = loanCounts[i];
	 	legendData.push(d.name);
	 	datas.push({
            name:d.name,
            type:'line',
            showSymbol:false,
            hoverAnimation:false,
            data:d.data
            /* ,
            markPoint : {
                data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name: '平均值'}
                ]
            } */
        });
	 }
	 
	 var cha = Number(d.data.max())  + Number(d.data.min());
	 var pj = Math.ceil(cha / d.data.length);
	 
	 option = {
			    title : {
			        text: title
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    /* legend: {
			        data:legendData
			    }, */
        /*  grid: {
             left: '3%',
             right: '1%',
             bottom: '5%',
             containLabel: true
         }, */
			    // toolbox: {
			    //     show : true,
			    //     feature : {
			    //     	 dataZoom: {
			    //     		 xAxisIndex: 'none'
			    //          },
			    //         mark : {show: true},
			    //         dataView : {show: true, readOnly: false},
			    //         magicType : {show: true, type: ['line', 'bar']},
			    //         restore : {show: true},
			    //         saveAsImage : {show: true}
			    //     }
			    // },
         /* toolbox: {
             show : true,
             feature : {
                 dataZoom: {
                     yAxisIndex: false
                 },
                 mark : {show: true},
                 dataZoom : {show: true},
                 dataView : {show: true, readOnly: false},
                 magicType : {show: true, type: ['line', 'bar']},
                 restore : {show: true},
                 saveAsImage : {show: true}
             }
         }, */
			    //calculable : true,
			    xAxis : [
			        {
						name:xtitle,
			            type : 'value'//,
			           // xAxisIndex:1,
			            //boundaryGap : false,
                       // scale:true
			            // data :invNames
			        }
			    ],
			    yAxis : [
			        {
						name:ytitle,
			            type : 'value'/* ,
			            axisLabel : {
			                formatter: '{value} '
			            }, */
                       // scale:true,
			            //min:(Number(d.data.min()) - pj) <= 0 ? Number(d.data.min()) : (Number(d.data.min()) - pj),
			            // min:Number(d.data.min()) - pj,
                        // max:Number(d.data.max()) + pj
			        }
			    ],
			    series :datas
			};
			                    
	myChart.setOption(option); 
	alert(new Date().getTime() - now)
	 return ; 
}
function initMyChart_Hline(id,title,xtitle,ytitle,invNames,loanCounts){
	 var myChart = echarts.init(document.getElementById(id));
	 myCharts.push({id:id,myChart:myChart});
	var datas = [];
	var legendData = [];
	for(var i = 0;i < loanCounts.length; i++){
		var d = loanCounts[i];
		legendData.push(d.name);
		datas.push({
            name:d.name,
            type:'line',
            smooth:true,
            itemStyle: {
                normal: {
                    lineStyle: {
                        shadowColor : 'rgba(0,0,0,0.4)'
                    }
                }
            },
            data:d.data
        });
	}
	 option = {
			 title : {
			        text: title
			    },
			    legend: {
			        data:legendData
			    },
         grid: {
             left: '3%',
             right: '1%',
             bottom: '5%',
             containLabel: true
         },
			    // toolbox: {
			    //     show : true,
			    //     feature : {
			    //     	dataZoom: {
			    //             yAxisIndex: false
			    //         },
			    //         mark : {show: true},
			    //         dataView : {show: true, readOnly: false},
			    //         magicType : {show: true, type: ['line', 'bar']},
			    //         restore : {show: true},
			    //         saveAsImage : {show: true}
			    //     }
			    // },
         toolbox: {
             show : true,
             feature : {
                 dataZoom: {
                     yAxisIndex: false
                 },
                 mark : {show: true},
                 dataZoom : {show: true},
                 dataView : {show: true, readOnly: false},
                 magicType : {show: true, type: ['line', 'bar']},
                 restore : {show: true},
                 saveAsImage : {show: true}
             }
         },
			    calculable : true,
			    tooltip : {
			        trigger: 'axis',
			        formatter: "参数值 : <br/>{b} : {c}"
			    },
			    yAxis : [
			        {
						name:ytitle,
			            type : 'value',
			            axisLabel : {
			                formatter: '{value}'
			            }
			        }
			    ],
			    xAxis : [
			        {
						name:xtitle,
			            type : 'value',
			            axisLine : {onZero: false},
			            // axisLabel : {
			            //     formatter: '{value} '
			            // },
			            boundaryGap : false
			            // data :invNames
			        }
			    ],
			    series : datas
			};

	myChart.setOption(option);                   
}

function initMyChart_scatter(id,title,xtitle,ytitle,loanCounts){
	 var myChart = echarts.init(document.getElementById(id));
	 myCharts.push({id:id,myChart:myChart});
	 var datas = [];
	 var legendData = [];
	 alert(0)
	 console.log(loanCounts);
	 // for(var i in loanCounts){
		//  console.log('散点图');
		//  console.log(i);
		//  console.log(loanCounts[i].value);
		//  legendData.push(loanCounts[i].name);
      //    datas.push({
      //        name:loanCounts[i].name,
      //        type:'scatter',
      //        data:[loanCounts[i].value]
      //    });
		//  }
		 /* for(var i=0;i<loanCounts.length;i++){
             console.log('散点图');
             console.log(i);
             console.log(loanCounts[i].value);
             legendData.push(loanCounts[i].name);
             datas.push({
            	 symbolSize: 5,
                 name:loanCounts[i].name,
                 type:'scatter',
                 data:[loanCounts[i].value]
             });
		 } */
	 option = {
			    title : {
			        text: title
			    },
			    tooltip : {
			        trigger: 'axis',
			        showDelay : 0,
			   
			        axisPointer:{
			            show: true,
			            type : 'cross',
			            lineStyle: {
			                type : 'dashed',
			                width : 1
			            }
			        }
			    },
         grid: {
             left: '3%',
             right: '1%',
             bottom: '5%',
             containLabel: true
         },
			    legend: {
			        data:legendData,
					left: 'center'
			    },
			    toolbox: {
			        show : true,
			        feature : {
			        	dataZoom: {
			                yAxisIndex: false
			            },
			            mark : {show: true},
			            dataZoom : {show: true},
			            dataView : {show: true, readOnly: false},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    xAxis : [
			        {
						name:xtitle,
			            type : 'value',
			            scale:true,
			            axisLabel : {
			                formatter: '{value}'
			            }
			        }
			    ],
			    yAxis : [
			        {
						name:ytitle,
			            type : 'value',
			            scale:true,
			            axisLabel : {
			                formatter: '{value} '
			            }
			        }
			    ],
			    series : datas
			};
			                    
			                    
	myChart.setOption(option);                   
}
function initMyChart_3dscatter(id,title,xtitle,ytitle,ztitle,loanCounts){
	var myChart = echarts.init(document.getElementById(id));
    myCharts.push({id:id,myChart:myChart});
	    var symbolSize = 2.5;
	    option = {
    		title : {
		        text: title
		    },
    		tooltip: {},
	        grid3D: {},
	        xAxis3D: {
	            type: 'category'
	        },
	        yAxis3D: {},
	        zAxis3D: {},
	        dataset: {
	            dimensions: [
                'X',
                'Y',
                'Z',
                {name: 'name', type: 'ordinal'}
	            ],
	            source: loanCounts
	        },
	        series: [
	            {
	                type: 'scatter3D',
	                symbolSize: symbolSize,
	                encode: {
	                	x: xtitle,
	                    y: ytitle,
	                    z: ztitle
	                }
	            }
	        ]
	    };

	    myChart.setOption(option);
}

$(function(){
	$.post(ctx + "/assess/assessTreeList",{projectId:$("#projectId").val()}, function (resultData) {
  		/*
    	for(var i in resultData){
            resultData[i].title = resultData[i].indexName;
            resultData[i].url = "#";
            resultData[i].parentid = resultData[i].parentid;
            resultData[i].open = true;
        }
    	$('#treeMenu').tree({
    		data: arrayToJson(resultData)
    	}); */
    	
    	generateResult(arrayToJson(resultData));
    	
    	var tmpJson = new Array();
		for(var i in resultData){
			var tmpObj = new Object();
			
            tmpObj.name = resultData[i].indexName;
            tmpObj.value = resultData[i].indexValue;
            tmpObj.parentid = resultData[i].parentid;
            tmpObj.id = resultData[i].id;
            tmpObj.symbol= 'circle';
            
             /* tmpObj.itemStyle= {
                normal: {
                	 label: {
                		 show: true,
                         position: 'right',
                         formatter: "{b}"
                     },
                     color: '#fa6900',
                     brushType: 'stroke',
                     borderWidth: 2,
                     borderColor: '#cc66ff',
                },
                emphasis: {
                    borderWidth: 0
                }
            };  */
            
            tmpJson.push(tmpObj);
            //resultData[i].symbolSize= [60, 60];
        } 
        /* var tmpJson = new Array();
    	for(var i in resultData){
    		var tmpObj = new Object();
    		tmpObj.name = resultData[i].indexName;
    		tmpObj.parentid = resultData[i].parentid;
    		tmpObj.id = resultData[i].id;
    		tmpJson.push(tmpObj);
        } */
    	
        //console.log(JSON.stringify(arrayToJson(tmpJson)));
        
        var arrJson = arrayToJson(tmpJson);
        addLabel(arrJson)
        console.log(arrJson);
    	// generateTree(arrJson);
    });
})

function addLabel(arrJson){
	
    for(var i in arrJson){
    	arrJson[i].itemStyle = {
            normal: {
                label: {
                    show: true,
                    position: 'right',
                    formatter: "{b}"
                },
                color: '#fa6900',
                borderWidth: 2,
                borderColor: '#cc66ff'

            },
            emphasis: {
                borderWidth: 0
            }
        };
    	if(arrJson[i].children){
    		addLabel(arrJson[i].children)
    	}
    }
    
}

function generateResult(resultData){
	console.log(resultData[0]);
	formatTr(resultData[0]);

}

var tmpChildrenLen = 0;
function  getChildrenNum(title, d){

	if(d.children){
		if(d.id == title){
			//console.log(d.children.length);
			tmpChildrenLen += d.children.length;
		}
		var tmp = d.children;
		for(var j in tmp){
			getChildrenNum(tmp[j].id,tmp[j]);
		}
					
	}
	return tmpChildrenLen;
}


function formatTr(data){
	tmpChildrenLen = 1;
	var title = data.indexName;
	var weightCurrent = data.weightCurrent == null?"":data.weightCurrent;
	var indexValue = data.indexValue==null?"":data.indexValue;
	var id = data.id;
	var tr = $("<tr></tr>");
	var td = '<td rowspan="'+getChildrenNum(id,data)+'">'+title+' [权重：'+weightCurrent+'；值：'+indexValue+']</td>'
	$(tr).append(td).appendTo($("#table"));
	if(data.children){
		//console.log(data.children);

		var tmp = data.children;
		// for(var j in tmp){
		// 	//getChildrenNum(tmp[j].title,tmp[j]);
		// 	formatTr(tmp[j]);
		// }
		for(var j=0;j<tmp.length;j++){
            formatTr(tmp[j]);
		}
		
	}
}

var treeMenu;
function generateTree(treedata){
	
	 var myChart = echarts.init(document.getElementById('treeMenu'),'dark');
	 treeMenu  = myChart;
	var option = {
		    title : {
		        text: '指标体系'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{b}: {c}"
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : false,

		    series : [
		        {
		            name:'树图',
		            type:'tree',
		            orient: 'horizontal',  // vertical horizontal
		            rootLocation: {x: 100, y: '60%'}, // 根节点位置  {x: 'center',y: 10}
		            nodePadding: 20,
		            symbol: 'circle',
		            symbolSize: 40,
		            /* itemStyle: {
		                normal: {
		                    label: {
		                        show: true,
		                        position: 'right',
		                        textStyle: {
		                            color: '#cc9999',
		                            fontSize: 15,
		                            fontWeight:  'bolder'
		                        }
		                    },
		                    lineStyle: {
		                        color: '#000',
		                        width: 1,
		                        type: 'broken' // 'curve'|'broken'|'solid'|'dotted'|'dashed'
		                    }
		                },
		                emphasis: {
		                    label: {
		                        show: true
		                    }
		                }
		            }, */
		            data:/*  [{
		            	"name": "[根节点]",
		            	"value": "",
		            	"parentid": 0,
		            	"id": 17,
		            	"symbol": "circle",
		            	"children": [{
		            		"name": "[一级节点a]",
		            		"value": "",
		            		"parentid": 17,
		            		"id": 18,
		            		"symbol": "circle",
		            		"children": [{
		            			"name": "[叶子节点a]",
		            			"value": "",
		            			"parentid": 18,
		            			"id": 22,
		            			"symbol": "circle",
		            			"children": [{
		            				"name": "[test]null",
		            				"value": null,
		            				"parentid": 22,
		            				"id": 23,
		            				"symbol": "circle"
		            			}, {
		            				"name": "[test2]null",
		            				"value": null,
		            				"parentid": 22,
		            				"id": 24,
		            				"symbol": "circle"
		            			}]
		            		}]
		            	}, {
		            		"name": "[一级节点b]null",
		            		"value": null,
		            		"parentid": 17,
		            		"id": 19,
		            		"symbol": "circle"
		            	}]
		            }] */treedata
		        }
		    ]
		};
	myChart.setOption(option);          	                    
}

function arrayToJson(treeArray){
	var r = [];
	var tmpMap ={};

	for (var i=0, l=treeArray.length; i<l; i++) {
	 // 以每条数据的id作为obj的key值，数据作为value值存入到一个临时对象里面
	 tmpMap[treeArray[i]["id"]]= treeArray[i]; 
	} 

	for (i=0, l=treeArray.length; i<l; i++) {
	 var key=tmpMap[treeArray[i]["parentid"]];
	  
	 //循环每一条数据的pid，假如这个临时对象有这个key值，就代表这个key对应的数据有children，需要Push进去
	 if (key) {
	  if (!key["children"]){
	    key["children"] = [];
	    key["children"].push(treeArray[i]);
	  }else{
	   key["children"].push(treeArray[i]);
	  }    
	 } else {
	  //如果没有这个Key值，那就代表没有父级,直接放在最外层
	  r.push(treeArray[i]);
	 }
	}
	return r
	}

function createTable(params){
	var th =[];
	
	
	
	var maxLen = getMaxLen(params);
	var keys = [];
	console.log(params);
	for(var i in params){
		th.push('<td>'+i+'</td>');
		keys.push(i);
	}
	var thStr = '<tr>'+  th.join('')  + '</tr>';
	
	var trStr = '';
	for(var i = 0 ; i< maxLen;i++){
		
		var tr= [];
		for(var j = 0 ; j<keys.length ; j++){
			console.log(keys[j]);
			var value = getValue(keys[j],params,i);
			console.log(value);
			tr.push('<td>'+value+'</td>');
		}
		trStr += '<tr>'+tr.join('') + '</tr>';
		
	}
	
	var html = thStr + trStr;
	$("#table").html(html);
	
	
	//开始合并
	for(var i =0; i<keys.length;i++){
		
		$('#table').tablesMergeCell({
            cols: [0,i]
        });
	}
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

function export2(){
	//初始化pdf，设置相应格式
    var doc = new jsPDF("p", "mm", "a4");
    //doc.setFillColor(0,0,0);
    doc.setFillColor(255,255,255);
	$('.container').css("background", "#fff");

    //这里设置的是a4纸张尺寸
    //doc.addImage(imgData, 'JPEG', 0, 0,210,297);
    
    doc.addHTML($('.container'), function(){
    	doc.output("save", '[${projectInfo.projectName }]评估报告.pdf');
	})

    //输出保存命名为content的pdf
    //doc.save('[${projectInfo.projectName }]评估报告.pdf');
}
function export3(){
	
	html2canvas( $('.container') , 
			{
		  		onrendered: function(canvas) 
		  		{
		    		//document.body.appendChild(canvas);
		    		//$('#down_button').attr( 'href' , canvas.toDataURL() ) ;

					 var dataUrl = canvas.toDataURL();

		    		//var newImg = document.createElement("img");
                    //newImg.src =  dataUrl;
                    //document.body.appendChild(newImg);
					//$("#hiddenDiv").html(newImg);
					
					/* setTimeout(function(){
						$("#hiddenDiv").wordExport();
					},5000);  */
					
					  //通过html2canvas将html渲染成canvas，然后获取图片数据
		            //var imgData = canvas.toDataURL('image/jpeg');
					
					 formSubmit(dataUrl);
		  		}	
		  	});
}
function formSubmit(elementValue) {

    var turnForm = document.createElement("form");   
    //一定要加入到body中！！   
    document.body.appendChild(turnForm);
    turnForm.method = 'post';
 turnForm.action = ctx +'/assess/dldoc';
 turnForm.target = 'hiddenIframe';
 //创建隐藏表单
 var newElement = document.createElement("input");
    newElement.setAttribute("name","base64");
    newElement.setAttribute("type","hidden");
    newElement.setAttribute("value",elementValue);
    turnForm.appendChild(newElement);

    turnForm.submit();
}

function export1(pid){
	html2canvas( $('#table') , 
			{
		  		onrendered: function(canvas) 
		  		{
					 var dataUrl = canvas.toDataURL();
					 jietuSuc(dataUrl,pid);
		  		}	
		  	});
}
function getValueById(id){
	for(var i in myCharts){
		var mychartObj = myCharts[i];
		var oid = mychartObj.id;
		if(oid == id){
			return mychartObj;
		}
	}
	return null;
}
function jietuSuc(tableBase64Data,pid){
	
	var jsonArr= [];
	var tmpW = 0;
	var tmpH = 0;
	var i = 0;
	$(".topp").each(function(i,o){
		var tmpJsonArr= [];
		var indexContent = $(this).children("div:first-child").html();
		$(this).find(".echartsDiv").each(function(){
			var id = $(this).attr("id");
			var mychartObj = getValueById(id);
			if(mychartObj != null){
				
				var mychart = mychartObj.myChart;
				var picBase64Info = mychart.getDataURL();
				var w = $("#" + id).width()
				var h = $("#" + id).height();
				var v = w/600;
				w = w > 600 ? 600 : w;
				h = h > 600 ? Math.ceil(h/v) : h;
				if(id.search("hscatter")!=-1){
					h = Math.ceil(h/v);
				}
			 	var tmpJson = {
			 			
			 			indexPic:picBase64Info,
			 			indexPicW:w,
			 			indexPicH:h
			 	};
			 	
			 	tmpJsonArr.push(tmpJson);
			 	if(i == 0){
				 	tmpW = tmpJson.indexPicW;
				 	tmpH = tmpJson.indexPicH;
			 	}
			}
		 	i++;
		});
		
		var tableJsonArr= [];
		$(this).find(".mytable").each(function(){
			var tableJson = $(this).attr('json');
			tableJsonArr.push(JSON.parse(tableJson));
		});
		
		var tt = {indexContent:indexContent,indexPicArr:tmpJsonArr,indexTableArr:tableJsonArr};
		jsonArr.push(tt);
	});
	
	var w1 = $("#myDiagramDiv").width();
	var h1 = $("#myDiagramDiv").height();
	var v1 = w1/600;
	w1 = w1 > 600 ? 600 : w1;
	h1 = h1 > 600 ? Math.ceil(h1/v1) : h1;

	var json = {
			tmpW:w1,
			tmpH:h1,
			title:$("#pTitle").html(),
			desc:$("#pDesc").html(),
			foot:$("#pInfo").html().replace(/&nbsp;/g,""),
			indexSer:treeImg,//图
			accessResult:tableBase64Data ,//图
			indexResult:	jsonArr//图
			
	};
	console.log(json)
	$("#info").val(JSON.stringify(json));
	$("#title").val(json.title);
	
	$("#myform").prop("action", ctx +'/assess/exportword?type=word&id='+pid).submit(); 
}

// function maskingKeyboard() {
//     if (event.keyCode == 46) {
//         event.keyCode = 0;
//         event.returnValue = false;
//     }
// }
// document.onkeydown=function(event){
//     if (event.keyCode == 46) {
//         event.keyCode = 0;
//         event.returnValue = false;
//     }
// }
</script>
</body>
</html>