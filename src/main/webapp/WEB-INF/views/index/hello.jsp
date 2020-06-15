<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx2" value="${pageContext.request.contextPath}" />
<script>
    var ctx2="${ctx2}";
</script>
<script type="text/javascript" src="<c:url value='/resources/js/comm/base-loading.js'/>"></script>
<%@ include file="/WEB-INF/views/index/include.jsp"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>

<html>
<head>
    <title></title>
    <style type="text/css">
        .container-fluid .row > div{
            border-radius:5px;
            -webkit-border-radius:5px;
            -moz-border-radius:5px;
        }
        .info-box{
            width:100%;
            height:80px;
            margin-bottom: 10px;
            cursor: pointer;
        }
        .info-box-icon{
            width: 30%;
            float: left;
            height:80px;
            text-align: center;
            padding-top: 15px;
        }
        .info-box-content{
            height:80px;
            background: #f1f1f1;
            width: 100%;
            text-align: center;
            padding-top: 10px;
        }
        .info-box-text{
            display: block;
            font-size: 20px;
            font-weight: bolder;
        }
        .info-box-number{
            font-size: 16px;
        }
        #ProjectCount,#ModelCount,#IndexCount{
            border:none;
        }
    </style>
</head>
<body>
<div class="content-wrapper">
    <%--     <div class="content-header">
            <ul class="breadcrumb">
                <li><a href="${ctx}/welcome"><i class="icon icon-home"></i></a></li>
            </ul>
        </div>
     --%>    <div class="content-body" style="margin-top: 10px;">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-4 col-sm-4 col-lg-4 col-xs-12">
                <div class="info-box" _href="project/projectList" _title="项目管理" title="点击进入项目管理">
                    <div class="info-box-icon bg-info">
                        <i class="icon icon-list-alt icon-4x"></i>
                    </div>
                    <div class="info-box-content">
                        <span class="info-box-text">项目</span>
                        <span class="info-box-number">${ProjectCounts }·
                                        <small>条</small>
                                    </span>
                    </div>
                </div>
            </div>
            <div class="col-md-4 col-sm-4 col-lg-4 col-xs-12">
                <div class="info-box" _href="platform/modelList" _title="模型管理" title="点击进入模型管理">
                    <div class="info-box-icon bg-primary">
                        <i class="icon icon-edit-sign icon-4x"></i>
                    </div>
                    <div class="info-box-content">
                        <span class="info-box-text">模型</span>
                        <span class="info-box-number">${ModelCounts }
                                        <small>条</small>
                                    </span>
                    </div>
                </div>
            </div>
            <div class="col-md-4 col-sm-4 col-lg-4 col-xs-12">
                <div class="info-box" _href="platform/indexList" _title="指标管理" title="点击进入指标管理">
                    <div class="info-box-icon bg-warning">
                        <i class="icon icon-bar-chart-alt icon-4x"></i>
                    </div>
                    <div class="info-box-content">
                        <span class="info-box-text">指标</span>
                        <span class="info-box-number">${IndexCounts}
                                        <small>条</small>
                                    </span>
                    </div>
                </div>
            </div>
            <!-- <div class="col-md-3 col-sm-6 col-xs-12">
                <div class="info-box">
                    <div class="info-box-icon bg-danger">
                        <i class="icon icon-yen icon-4x"></i>
                    </div>
                    <div class="info-box-content">
                        <span class="info-box-text">还款总额</span>
                        <span class="info-box-number">18953
                                    <small>W</small>
                                </span>
                    </div>
                </div>
            </div> -->
        </div>
        <div class="row">
            <div class="col-md-6 col-lg-6 col-xs-12">
                <div class="panel">
                    <div class="panel-heading">
                        <div class="panel-title">项目统计</div>
                    </div>
                    <div class="panel-body" id="ProjectCount" style="height:320px;">
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-lg-6 col-xs-12">
                <div class="panel" style="height:355px;">
                    <!--  <div class="panel-heading">
                         <div class="panel-title">近期评估</div>
                     </div>
                     <div class="panel-body" id="AssessCount" style="height:60%;"> -->
                    <table class="table table-condensed datatable">
                        <thead>
                        <tr>
                            <th class="flex-col" data-width="30" data-sort="false">序号</th>
                            <th class="flex-col" data-width="140" data-sort="false">项目名称</th>
                            <th class="flex-col" data-width="140" data-sort="false">节点名称</th>
                            <th class="flex-col" data-width="140" data-sort="false">项目创建时间</th>
                            <th class="flex-col" data-width="140" data-sort="false">评估时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${recentAssessInfos }" var="e" varStatus="sta">
                            <tr _value="${e.id }" _href="platform/assessList" _title="项目管理">
                                <td >${sta.count }</td>
                                <td >${e.project_name }</td>
                                <td >${e.assess_name }</td>
                                <td >${e.create_time }</td>
                                <td >${e.update_time }</td>

                            </tr>

                        </c:forEach>
                        </tbody>
                    </table>
                    <!--  </div> -->
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 col-lg-6 col-xs-12">
                <div class="panel">
                    <div class="panel-heading">
                        <div class="panel-title">模型统计</div>
                    </div>
                    <div class="panel-body" id="ModelCount" style="height:320px;">
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-lg-6 col-xs-12">
                <div class="panel">
                    <div class="panel-heading">
                        <div class="panel-title">指标统计</div>
                    </div>
                    <div class="panel-body" id="IndexCount" style="height:320px;">

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</div>
<script type="text/javascript">
    function assessSucceed(assessId) {
        window.location.href = ctx + '/assess/assessList?projectId='+assessId;
        // var url = $(this).attr("_href");
        // var title = $(this).attr("_title");
        // top.jump(title,url);
    }
    $(function () {
        $('table.datatable').datatable({sortable: true});
        $.ajax({
            url: ctx + "/count/model",
            async: true,
            type: 'GET',
            dataType: 'json',
            timeout: 10000,
            cache: false,
            success: function (data) {
                //成功时执行初始化资金方日进件量图表函数
                initMyChart_Model(data.invNames,data.loanCounts);
            }
        });
        $.ajax({
            url: ctx + "/count/project",
            async: true,
            type: 'GET',
            dataType: 'json',
            timeout: 10000,
            cache: false,
            success: function (data) {
                //成功时执行初始化资金方日进件量图表函数
                initMyChart_Project(data.invNames,data.loanCounts);
            }
        });

        $.ajax({
            url: ctx + "/count/index",
            async: true,
            type: 'GET',
            dataType: 'json',
            timeout: 10000,
            cache: false,
            success: function (data) {
                initMyChart_Index(data.invNames,data.loanCounts);
            }
        });

        $(".info-box").click(function(){
            var url = $(this).attr("_href");
            var title = $(this).attr("_title");
            top.jump(title,url);
        });
        // $("tr").click(function(){
        //
        //     // $(this).hasClass("hover"),
        //     // $(this).removeClass("hover"),
        //
        //     var projectId=$(this).attr("_value");
        //     var url = $(this).find("div").attr("_href")+"?projectId="+projectId;
        //     var title = $(this).find("div").attr("_title");
        //     top.jump(title,url);
        // });
    })

    function initMyChart_Model(invNames,loanCounts){
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('ModelCount'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '模型'
            },
            tooltip: {},
            legend: {
                data:['统计个数']
            },
            xAxis: {
                data: invNames,
                axisLabel: {
                    interval:0,
                    rotate:40
                }
            },
            yAxis: {},
            series: [{
                name: '月统计数',
                type: 'bar',
                data: loanCounts,
                itemStyle:{
                    normal:{
                        color:'#87CEFF'
                    }
                }
            }]
        };


        myChart.setOption(option);
    }
    function initMyChart_Project(invNames,loanCounts){
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('ProjectCount'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '项目'
            },
            tooltip: {},
            legend: {
                data:['统计个数']
            },
            xAxis: {
                data: invNames,
                axisLabel: {
                    interval:0,
                    rotate:40
                }
            },
            yAxis: {},
            series: [{
                name: '月统计数',
                type: 'bar',
                data: loanCounts,
                itemStyle:{
                    normal:{
                        color:'#87CEFF'
                    }
                }
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    }

    function initMyChart_Index(invNames,loanCounts) {
        var myChart = echarts.init(document.getElementById('IndexCount'));

        var option = {
            title: {
                text: '指标'
            },
            tooltip: {},
            legend: {
                data:['统计个数']
            },
            xAxis: {
                data: invNames,
                axisLabel: {
                    interval:0,
                    rotate:40
                }
            },
            yAxis: {},
            series: [{
                name: '月统计数',
                type: 'bar',
                data: loanCounts,
                itemStyle:{
                    normal:{
                        color:'#87CEFF'
                    }
                }
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    }

    ////////////

    // 处理点击事件并且跳转到相应的百度搜索页面
    //    myChartPie.on('click', function (params) {
    //        window.open('https://www.baidu.com/s?wd=' + encodeURIComponent(params.name));
    //    });

</script>
</body>
</html>