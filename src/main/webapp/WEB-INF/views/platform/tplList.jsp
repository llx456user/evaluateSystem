<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/index/includeZui.jsp" %>
<html>
<head>
    <title>评估系统-模板查询</title>
    <style>
        .active {
            border: 1px solid red;
        }

        .tpl-el {
            display: inline-block;
            width: 200px;
            height: 30px;
            border-radius: 3px;
            /* background: #03b8cf; */
            text-align: center;
            color: #fff;
            font-weight: bold;
            margin-right: 10px;
        }

        .tpl-el label {
            width: 150px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            word-break: keep-all;
            vertical-align: middle;
            padding-top: 6px;
        }

        .tpl-el input {
            vertical-align: middle;
        }

    </style>
</head>
<body>
<div class="container">
    <!-- 左侧分类 -->
    <div class="panel" style="float: left;width:20%;margin-left: 1%;">
        <div class="panel-heading">
            模板分类
            <!-- <span style="float: right;">
            <a id="addCategory"><i class="icon-plus"></i></a>
            &nbsp;
            <a id="editCategory"><i class="icon-edit"></i></a>
            &nbsp;
            <a id="delCategory"><i class="icon-trash"></i></a>
            </span> -->
        </div>
        <input id="searchInputExample" autofocus="autofocus" type="search" class="form-control search-input"
               placeholder="搜索">
        <ul class="tree tree-lines tree-folders" id="treeMenu">
        </ul>
    </div>
    <!-- 模板数据 -->
    <div class="panel" style="float: right;width: 79%;min-height:400px;height: 95%;">
        <div class="panel-heading">

            <form id="dd" role="form" class="form-inline">
                <input type='hidden' id="frameid2" value='${frameid }' />
                <span style="margin-left:20px;" class="form-group">
					模板列表
				</span>
                <div style="float:right;">
                    <button type="button" id="" class="btn btn-primary" onclick="OK();"><i class="icon-check"></i> 确定</button>
                    <!-- <button type="button" class="btn btn-default" onclick="back();">返回</button> -->
                </div>
            </form>
        </div>
        <div class="panel-body" id="tplList">

        </div>
    </div>
    <div style="clear: both;"></div>
    <input type='hidden' id="frameid"/>
</div>

<script type="text/javascript">

    var nodeDataArray = [];//节点信息数组
    var linkDataArray = [];//连线信息数组
    //    $("div#tplList").click(function(){
    //
    //        var $this = $(this);
    //        var radio = $this.find("td:eq(1) input[name='myradio']");
    //        radio.prop("checked",true);
    //    });
    function OK() {
        //TODO
        //


        var frameid = $("#frameid2").val();

        var radio_tag = document.getElementsByName("tplName");

        for (var i = 0; i < radio_tag.length; i++) {
            if (radio_tag[i].checked) {

                var indexid=$(':radio[name="tplName"]:checked').parent("div").attr("tplId");
                // var indexid=$("input[checked=checked]").parent(".tplId").attr("tplId");

                top.document.getElementById(frameid).contentWindow.callBackSelectTp(indexid);
            }else {
                tipMsg('请选择模板！');
            }

        }
        top.closeDialog();


    }

    function back() {
        //window.history.go(-1);
        top.closeLayerDialog();
    }

    $(function () {

        initTree();
    });

    function initTree() {
        generateTreeMenu();
        $('#treeMenu').on('click', 'a', function () {
//		if($(this).parent().hasClass("has-list")){
//			return;
//		}
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
            for (var i in resultData) {
                resultData[i].title = resultData[i].categoryName;
                resultData[i].url = "#";
            }
            var rootNode = [{
                id: 'root',
                title: '模板分类',
                url: '#',
                open: true,
                children: resultData
            }];
            $('#treeMenu').tree({
                data: rootNode
            });
        });
    }

    function searchData() {
        var i = openProgressExt("加载中...");
        var $el = $("#treeMenu li a.active");
        var id = $el.parent().attr('data-id');
        var params = {};
        params.categoryId = id;
        myajax(ctx + '/platform/templetAllListByCateId', params, function (data) {
            closeProgressExt(i);
            appendHTML(data);
        });
    }

    function appendHTML(data) {
        var arr = [];
        $.each(data, function (i, o) {
            console.log(o);
            var templetName = o.templetName;
            var id = o.id;
            var indexid=o.indexid;
            arr.push('<div tplId="' + indexid + '" class="tpl-el btn-primary"><label>' + templetName + '</label><input type="radio"  name="tplName"/></div>');


        });
        $("#tplList").html(arr.join(''));

    }

    function getCheckedRadio(name){
        var radios = document.getElementsByName(name);
        var len = radios.length;
        for(var i = 0; i<len;i++){
            if(radios[i].checked){
                return radios[i].value;
            }
        }
        return null;
    }



</script>
</body>
</html>