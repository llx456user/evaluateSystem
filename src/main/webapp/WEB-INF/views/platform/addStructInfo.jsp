<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/index/includeZui.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>评估系统-结构体添加</title>
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
    </style>
</head>
<body>
<div class="container">

    <!-- 新建文件 -->
    <div class="panel" style="margin-bottom: 5px;">
        <div class="panel-heading">
            <strong>结构体信息</strong>
        </div>
        <div class="panel-body">
            <form id="fileEditForm" method="post">
                <input type="hidden" name="id" id="id" value="${bean.id }"/>
                <input type="hidden" name=structCategoryid id="structCategoryid" value="${bean.categoryId }"/>
                <div class="form-group">
                    <label for="fileName">结构体名称</label>
                    <input type="text" class="form-control" id="structName" name="structName" placeholder="" value="${bean.structName }">
                </div>
                <div class="form-group">
                    <label for="fileContent">结构体描述</label>
                    <textarea class="form-control" id="structRemark" name="structRemark" placeholder="">${bean.structRemark }</textarea>
                </div>
                <!--  改造一下保存事件  添加以下内容 start -->
                <div class="form-group">
                    <table class="table table-borderless">
                        <thead>
                        <tr>
                            <th>参数变量名</th>

                            <th>参数类型</th>
                            <th>参数单位</th>
                            <th>参数含义</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody  id="stVariableTb">
                        <!--判断新建还是编辑-->
                        <c:choose>
                            <c:when test="${bean.id != null}">

                                <c:forEach items="${structList}" var="st" varStatus="stat">
                                    <tr>
                                        <td>
                                            <input autofocus="autofocus" type="text" class="form-control" name="parmeterName" value="${st.parmeterName }"  style=" text-indent: 4px; background: transparent;   ">
                                        </td>

                                        <td>
                                            <select name="parmeterType" class="form-control">
                                                <option value="">-请选择-</option>
                                                <option value="int" <c:if test="${st.parmeterType == 'int'}">selected</c:if>>int</option>
                                                <option value="int*" <c:if test="${st.parmeterType == 'int*'}">selected</c:if>>int*</option>
                                                <option value="long" <c:if test="${st.parmeterType == 'long'}">selected</c:if>>long</option>
                                                <option value="long*" <c:if test="${st.parmeterType == 'long*'}">selected</c:if>>long*</option>
                                                <option value="float" <c:if test="${st.parmeterType == 'float'}">selected</c:if>>float</option>
                                                <option value="float*" <c:if test="${st.parmeterType == 'float*'}">selected</c:if>>float*</option>
                                                <option value="double" <c:if test="${st.parmeterType == 'double'}">selected</c:if>>double</option>
                                                <option value="double*" <c:if test="${st.parmeterType == 'double*'}">selected</c:if>>double*</option>
                                                <option value="string" <c:if test="${st.parmeterType == 'string'}">selected</c:if>>string</option>
                                            </select>
                                        </td>
                                        <td>
                                            <input type="text" class="form-control" name="parmeterUnit" value="${st.parmeterUnit }" style=" text-indent: 4px; background: transparent;">
                                        </td>
                                        <td><input type="text" class="form-control" name="parmeterUnitEx" value="${st.parmeterUnitEx }" style=" text-indent: 4px; background: transparent;"></td>
                                        <td><i title="添加" onclick="addBtnSt(event)" class="icon icon-plus-sign icon-2x addBtn iptVar"></i><i title="删除" class="icon icon-trash icon-2x iptVar trash"></i></td>
                                    </tr>
                                </c:forEach>

                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td>
                                        <input type="text" class="form-control" name="parmeterName" value="Param1" style=" text-indent: 4px; background: transparent; ">
                                    </td>

                                    <td>
                                        <select name="parmeterType" class="form-control">
                                            <option value="">-请选择-</option>
                                            <option value="int">int</option>
                                            <option value="int*">int*</option>
                                            <option value="long">long</option>
                                            <option value="long*">long*</option>
                                            <option value="float">float</option>
                                            <option value="float*">float*</option>
                                            <option value="double">double</option>
                                            <option value="double*">double*</option>
                                            <option value="string">string</option>
                                        </select>
                                    </td>
                                    <td>
                                            <%--<select name="parmeterUnit" class="form-control" onchange="entryParmeterUnit(event)">--%>
                                            <%--<option value="">-请选择-</option>--%>
                                            <%--<option value="array">array</option>--%>
                                            <%--<option value="not_array">not array</option>--%>
                                            <%--</select>--%>
                                        <input type="text" class="form-control" name="parmeterUnit"  >
                                    </td>
                                    <td><input type="text" class="form-control" name="parmeterUnitEx" ></td>
                                    <%--<input type="text" class="form-control" name="parmeterName" value="oParam1" style=" text-indent: 4px; background: transparent;">--%>
                                    <%--<td><i class="icon icon-plus-sign icon-2x addBtn optVar"></i></td>--%>
                                    <td><i onclick="addBtnSt(event)" class="icon icon-plus-sign icon-2x addBtn iptVar"></i><i class="icon icon-trash icon-2x iptVar trash"></i></td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                        </tbody>
                    </table>
                </div>

            </form>

            <button class="btn btn-primary" type="button" onclick="save();">保存</button>
            <button class="btn btn-default" type="button" onclick="back();">关闭</button>
        </div>
    </div>

</div>
<script type="text/javascript">
    // $(document).ready(function() {
    //     var tr = document.getElementsByTagName('tbody')[0].getElementsByTagName('tr');
    //     if(tr.size()==1){
    //         $(tr).find(".trash").remove(".trash");
    //     }
    // })

    function back(){

        window.history.go(-1);
    }

    function getJson(el) {
        var json = [];
        $(el).find("tr").each(function () {
            var parmeterName = $(this).find("input[name=parmeterName]").val();
            var parmeterType = $(this).find("[name=parmeterType] option:selected").val();
            var parmeterUnit = $(this).find("[name=parmeterUnit]").val();
            var parmeterUnitEx = $(this).find("input[name=parmeterUnitEx]").val();
            var jsonObj = {
                parmeterName:parmeterName,
                parmeterType:parmeterType,
                parmeterUnit:parmeterUnit,
                parmeterUnitEx:parmeterUnitEx
            };
            json.push(jsonObj);
        });
        return json;
        
    }
    function save(){
        var stVariableTb = getJson("#stVariableTb");
        var id=$("#id").val();

        var structCategoryid=$("#structCategoryid").val();

        var structName=$("#structName").val();

        var structRemark=$("#structRemark").val();

        if(structName==""){
            tipMsg("结构体名称不能为空");
            return;
        }
        var param = {
            id:id,
            categoryId:structCategoryid,
            structName:structName,
            structRemark:structRemark
        };
        param.stVariableTb = stVariableTb;
        var i = openProgressExt("保存中...");
        $.ajax({
            url:ctx + '/platform/saveStructInfo',
            data:{reqJson:JSON.stringify(param)},
            dataType:'JSON',
            success:function (data) {
                closeProgressExt(i);
                if(data.status==1){
                    tipMsg(data.msg);
                    setTimeout(function () {
                        back();

                    },1000);
                }else {
                    tipMsg(data.msg);
                }

            }
        });

    }

    $("#stVariableTb").on('click','.trash',function(){

        var tr = document.getElementsByTagName('tbody')[0].getElementsByTagName('tr');
        if(tr.length==1){
            tipMsg('最后一行无法删除');
        }else {
            $(this).parent().parent().remove();
        }
    });



     //创建列点击事件
    // $("#stVariableTb").on('click','.addBtn',function(){
    //     var tr = document.getElementsByTagName('tbody')[0].getElementsByTagName('tr');
    //     $("#stVariableTb").append(createTrParam(this,"Param",tr.length+1));
    //
    // });


    //创建一列
    function createTrParam(el,paramNamePre,paramCount){
        var $tr = $(el).parent().parent();
        var $tmp = $tr.clone(true);
        $tmp.find("input[name=array]").prop("checked",false);
        $tmp.find("input").val("");
        $tmp.find("input[name=parmeterName]").val(paramNamePre+paramCount);

        if(!$tmp.find("i").hasClass("trash")){
            $tmp.find(".addBtn").after('<i class="icon icon-trash icon-2x iptVar trash"></i>');
        }

        return $tmp;
    }

    $("select[name=parmeterType]").mousedown(function(){//当按下鼠标按钮的时候
        this.sindex = this.selectedIndex;//把当前选中的值得索引赋给下拉选中的索引
        this.selectedIndex = -1;//把下拉选中的索引改变为-1,也就是没有!
    });

    $("select[name=parmeterType]").mouseout(function(){//当鼠标移开的时候
        var index = this.selectedIndex;//获取下拉选中的索引
        if(index == -1){//如果为-1,就是根本没有选
            this.selectedIndex = this.sindex;//就把下拉选中的索引改变成之前选中的值得索引,就默认选择的是之前选中的值
        }});

    $("select[name=parmeterType]").change(function(){
        var $self = $(this);
        if($self.val() == "struct"){
            selectStruct($self);
            $self.blur();//失去焦点
        }
    });


    function addBtnSt(event){
        var tr = document.getElementsByTagName('tbody')[0].getElementsByTagName('tr');
        var $tr = $(event.target).parent().parent();
        var $tmp = $tr.clone(true);
        $tmp.find("input[name=array]").prop("checked",false);
        $tmp.find("input").val("");
        $tmp.find("input[name=parmeterName]").val("oParam"+(tr.length+1));
        //$tmp.find("select").val("");
        $tmp.find("#asterisk").remove("#asterisk");
        if(!$tmp.find("i").hasClass("trash")){
            $tmp.find(".addBtn").after('<i class="icon icon-trash icon-2x iptVar trash"></i>');
        }
        $(event.target).closest("tr").after($tmp);
    }

 </script>

</body>

</html>
