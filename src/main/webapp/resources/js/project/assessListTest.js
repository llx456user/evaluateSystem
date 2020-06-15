// 显示列表

$(document).ready(function () {
    var timeInterval = setInterval(autoSaveDataBinding, 60000);
    var isGroupTemplate = document.getElementById("isGroupTemplate").value;
    if (isGroupTemplate == "true") {
        initialTreeSelect();
    }

});

function initialTreeSelect() {
    var projectId = document.getElementById("projectId").value;
    var param = {
        projectId: projectId
    };
    var cSelectData = [];
    var fSelectData = [];
    var f = openProgressExt("页面加载中...");
    myajax(ctx + '/project/getGroupTemplateInfo', param, function (data) {
        cSelectData = data.cParamList;
        fSelectData = data.fParamList;
        myajax(ctx + '/project/getProjectNodeInfo', param, function (data) {
            if (data.length >0){
                closeProgressExt(f)
                for (var i = 0; i < data.length; i++) {
                    console.log(data[i])
                    var indexName = data[i].indexName;
                    var indexId = data[i].indexId;
                    var nodeId = "";
                    var groupTemplateData = "";
                    console.log(indexName + "--" + indexId)

                    // 常量
                    for (var k = 0 ; k < data[i].cParamList.length; k++) {
                        console.log(indexName + "------" + ":常量")
                        nodeId = data[i].cParamList[0].nodeId;
                        groupTemplateData = data[i].cParamList[k].groupTemplateData;
                        var selectId = "projectParam" + "_" + indexId + "_" + nodeId;
                        //调用函数，传入要转换的list数组，和树中顶级元素的pid
                        var tree = listToTree(cSelectData, 0);
                        var elementsByName = document.getElementsByName(selectId);
                        if (elementsByName.length > 2){
                            var selectbox = elementsByName.item(getIndexNumber(k))
                            //调用函数，并将结构出入到下拉框容器
                            selectbox.innerHTML = "<option value=''>-请选择-</option>" + creatSelectTree(tree,groupTemplateData);
                        }
                        console.log(tree);
                    }
                    // 附件
                    for (var j = 0; j < data[i].fParamList.length; j++) {
                        nodeId = data[i].fParamList[j].nodeId;
                        groupTemplateData = data[i].fParamList[j].groupTemplateData;
                        var selectId = "projectParam" + "_" + indexId + "_" + nodeId;
                        //调用函数，传入要转换的list数组，和树中顶级元素的pid
                        var tree = listToTree(fSelectData, 0);
                        var elementsByName = document.getElementsByName(selectId);
                        if (elementsByName.length > 2){
                            var selectbox = elementsByName.item(getIndexNumber(data[i].cParamList.length + j))
                            //调用函数，并将结构出入到下拉框容器
                            selectbox.innerHTML = "<option value=''>-请选择-</option>" + creatSelectTree(tree,groupTemplateData);
                        }

                        console.log(tree);
                    }
                }
            }else {
                closeProgressExt(f)
            }

        });
    });

}
// 因为每隔三个就是一个下拉框
function getIndexNumber(val) {
    var tempNUmber = val>0?1 + val*3:1;
    return tempNUmber;
}
//list 转成树形json
function listToTree(list,pid) {
    var ret = [];//一个存放结果的临时数组
    for(var i in list) {
        if(list[i].pid == pid) {//如果当前项的父id等于要查找的父id，进行递归
            list[i].children = listToTree(list, list[i].id);
            ret.push(list[i]);//把当前项保存到临时数组中
        }
    }
    return ret;//递归结束后返回结果
}

var j="-";//前缀符号，用于显示父子关系，这里可以使用其它符号
function creatSelectTree(d,groupTemplateData){
    var option = "";
    for(var i=0;i<d.length;i++){
        if(d[i].children.length){//如果有子集
            var select = d[i].id == groupTemplateData?'selected':'';
            option+="<option value='"+d[i].id+"' "+select+" disabled>"+j+d[i].name+"</option>";
            j+="---";//前缀符号加一个符号
            option+=creatSelectTree(d[i].children,groupTemplateData);//递归调用子集
            j=j.slice(0,j.length-3);//每次递归结束返回上级时，前缀符号需要减一个符号
        }else{//没有子集直接显示
            var select = d[i].id == groupTemplateData?'selected':'';
            option+="<option value='"+d[i].id+"' "+select+">"+j+d[i].name+"</option>";
        }
    }
    return option;//返回最终html结果
}


function showInputOrUpload(pId) {
    console.log("这里代码执行了" + pId);
    var display = document.getElementById("inputDiv_" + pId).style.display;
    console.log(display);
    if (display == "none") {
        // $("[name=" + "projectParam_" + pId + "]").attr("disabled", true);
        $("#projectParam_" + pId).attr("disabled", true);
        document.getElementById("inputDiv_" + pId).style.display = "block";
    } else {
        // $("[name=" + "projectParam_" + pId + "]").attr("disabled", false);
        $("#projectParam_" + pId).attr("disabled", false);
        document.getElementById("inputDiv_" + pId).style.display = "none";
    }
}

function zuiupload(pId) {
    filePath_pId = pId;
    var options = {
        // 初始化选项
        chunk_size: 0,
        autoUpload: true,            // 当选择文件后立即自动进行上传操作
        url: ctx + '/assess/upload',  // 文件上传提交地址
        browse_button: '#uploaderBtn_' + pId,
        drop_element: 'self',
        multi_selection: false,
        deleteActionOnDone: function (file, doRemoveFile) {
            return true;
        },
        limitFilesCount: false,
        filters: {
            prevent_duplicates: false
        },
        responseHandler: function (responseObject, file) {
            if (responseObject.response != '') {
                $("#filepreview_" + pId).val(file.name);
                $("#filePath_" + pId).val(file.name + "_" + responseObject.response);
            }
        }
    };
    $('#inputDiv_' + pId).uploader(options);
}

function selectFiles(pId) {
    var fid = self.frameElement.getAttribute('id');
    var url = ctx + '/datasource/fileList2?frameid=' + fid + "&pId=" + pId;
    top.openDialog('选择文件', '1000px', '600px', url, function (data) {
        //TODO
        console.log(data);
    });
}

function callBackSelectFiles(pId, filePath, fileName) {
    $("#filePath_" + pId).val(fileName + "_" + filePath);
    $("#filepreview_" + pId).val(fileName);
}

function back() {
    window.history.go(-1);
}

// 提交所有的参数模板
function saveAllProjectParamTemplate() {
    var projectId = document.getElementById("projectId").value;
    var isGroupTemplate = document.getElementById("isGroupTemplate").value;
    var param = {
        projectId: projectId
    };
    var j = openProgressExt("保存中...");
    myajax(ctx + '/project/getProjectNodeInfo', param, function (data) {
        // closeProgressExt(j)
        var checkResult = true;
        if (data.length > 0) {
            for (var i = 0; i < data.length; i++) {
                console.log(data[i])
                var indexName = data[i].indexName;
                var indexId = data[i].indexId;
                var nodeId = "";
                console.log(indexName + "--" + indexId)
                if (data[i].fParamList.length > 0) {
                    console.log(indexName + "------" + ":附件")
                    nodeId = data[i].fParamList[0].nodeId;
                }
                if (data[i].cParamList.length > 0) {
                    console.log(indexName + "------" + ":常量")
                    nodeId = data[i].cParamList[0].nodeId;
                }
                var arr = getFormJson("projectParam" + "_" + indexId + "_" + nodeId);
                console.log(JSON.stringify(arr))
                for (var k = 0; k < arr.length; k++) {
                    if (arr[k].value == "") {
                        tipMsg(indexName + '请设置参数!');
                        document.getElementById(nodeId).classList.add("open");
                        $("#" + projectId + "_" + nodeId).css("color", "red");
                        closeProgressExt(j);
                        return;
                    }
                }
            }
        }
        // 检验无节点未设置参数时 进行保存
        // save(data);
        // 获取参数
        if (data.length > 0) {
            var checkResult = true;
            var resultArr = [];
            var nodeArr = [];
            for (var i = 0; i < data.length; i++) {
                checkResult = true;
                var nodeCount = 0;
                console.log(data[i])
                var indexName = data[i].indexName;
                var indexId = data[i].indexId;
                var nodeId = "";
                console.log(indexName + "--" + indexId)
                if (data[i].fParamList.length > 0) {
                    nodeId = data[i].fParamList[0].nodeId;
                }
                if (data[i].cParamList.length > 0) {
                    nodeId = data[i].cParamList[0].nodeId;
                }
                nodeCount = data[i].fParamList.length + data[i].cParamList.length;
                // 获取节点数据选择信息呢
                var arr = getFormJsonAuto("projectParam" + "_" + indexId + "_" + nodeId);
                // 判断是否有没选择的模板
                if (nodeCount > arr.length){
                    checkResult = false;
                }
                // 设置节点颜色
                if (checkResult == true) {
                    $("#" + projectId + "_" + nodeId).css("color", "green");
                }else {
                    $("#" + projectId + "_" + nodeId).css("color", "red");
                    document.getElementById(nodeId).classList.add("open")
                }
                // 增加nodeid 拼接节点信息
                if (arr.length>0) {
                    resultArr = resultArr.concat(arr);
                    var json = {
                        nodeId: nodeId
                    };
                    nodeArr.push(json);
                }
            }
            console.log(JSON.stringify(resultArr))
            console.log(JSON.stringify(nodeArr))
            // clickSave(resultArr,nodeArr);
            // 保存
            var param = {
                params: JSON.stringify(resultArr),
                projectId: projectId,
                isGroupTemplate:isGroupTemplate,
                nodeId: JSON.stringify(nodeArr)
            }
            param = JSON.stringify(param)
            console.log(param);
            // var j = openProgressExt("保存中...");
            $.ajax({
                url: ctx + '/project/paramTemplateHandle',
                type: 'POST',
                contentType: 'application/json',
                data: param,
                success: function (data) {
                    if (data.status == 1) {
                        closeProgressExt(j)
                        tipMsg(data.msg);
                        setTimeout(function(){
                            back();
                        },1000);
                    } else {
                        tipMsg(data.msg);
                    }
                }
            });
        }

    });

}

function save(data) {
    var projectId = document.getElementById("projectId").value;
    if (data.length > 0) {
        var checkResult = true;
        var resultArr = [];
        var nodeArr = [];
        for (var i = 0; i < data.length; i++) {
            checkResult = true;
            var nodeCount = 0;
            console.log(data[i])
            var indexName = data[i].indexName;
            var indexId = data[i].indexId;
            var nodeId = "";
            console.log(indexName + "--" + indexId)
            if (data[i].fParamList.length > 0) {
                nodeId = data[i].fParamList[0].nodeId;
            }
            if (data[i].cParamList.length > 0) {
                nodeId = data[i].cParamList[0].nodeId;
            }
            nodeCount = data[i].fParamList.length + data[i].cParamList.length;
            // 获取节点数据选择信息呢
            var arr = getFormJsonAuto("projectParam" + "_" + indexId + "_" + nodeId);
            // 判断是否有没选择的模板
            if (nodeCount > arr.length){
                checkResult = false;
            }
            // 设置节点颜色
            if (checkResult == true) {
                $("#" + projectId + "_" + nodeId).css("color", "green");
            }else {
                $("#" + projectId + "_" + nodeId).css("color", "red");
                document.getElementById(nodeId).classList.add("open")
            }
            // 增加nodeid 拼接节点信息
            if (arr.length>0) {
                resultArr = resultArr.concat(arr);
                var json = {
                    nodeId: nodeId
                };
                nodeArr.push(json);
            }
        }
        console.log(JSON.stringify(resultArr))
        console.log(JSON.stringify(nodeArr))
        clickSave(resultArr,nodeArr);
    }
}
function autoSaveDataBinding() {
    console.log("定时器执行开始")
    var projectId = document.getElementById("projectId").value;
    var param = {
        projectId: projectId
    };
    myajax(ctx + '/project/getProjectNodeInfo', param, function (data) {
        if (data.length > 0) {
            var checkResult = true;
            var resultArr = [];
            var nodeArr = [];
            for (var i = 0; i < data.length; i++) {
                checkResult = true;
                var nodeCount = 0;
                console.log(data[i])
                var indexName = data[i].indexName;
                var indexId = data[i].indexId;
                var nodeId = "";
                console.log(indexName + "--" + indexId)
                if (data[i].fParamList.length > 0) {
                    nodeId = data[i].fParamList[0].nodeId;
                }
                if (data[i].cParamList.length > 0) {
                    nodeId = data[i].cParamList[0].nodeId;
                }
                nodeCount = data[i].fParamList.length + data[i].cParamList.length;
                // 获取节点数据选择信息呢
                var arr = getFormJsonAuto("projectParam" + "_" + indexId + "_" + nodeId);
                // 判断是否有没选择的模板
                if (nodeCount > arr.length){
                    checkResult = false;
                }
                // 设置节点颜色
                if (checkResult == true) {
                    $("#" + projectId + "_" + nodeId).css("color", "green");
                }else {
                    $("#" + projectId + "_" + nodeId).css("color", "red");
                    document.getElementById(nodeId).classList.add("open")
                }
                // 增加nodeid 拼接节点信息
                if (arr.length>0) {
                    resultArr = resultArr.concat(arr);
                    var json = {
                        nodeId: nodeId
                    };
                    nodeArr.push(json);
                }
            }
        }
        console.log(JSON.stringify(resultArr))
        console.log(JSON.stringify(nodeArr))
        autoSave(resultArr,nodeArr);
    });
    console.log("定时器执行结束")
}

function remove(array,index){
    if(index<=(array.length-1)){
        for(var i=index;i<array.length;i++){
            array[i]=array[i+1];
        }
    }else{
        throw new Error('Array index out of bounds!for function is:remove');
    }
    array.length=array.length-1;
    return array;
}
function clickSave(arr,nodeArr) {
    var projectId = document.getElementById("projectId").value;
    var param = {
        params: JSON.stringify(arr),
        projectId: projectId,
        nodeId: JSON.stringify(nodeArr)
    }
    param = JSON.stringify(param)
    console.log(param);
    var j = openProgressExt("保存中...");
    $.ajax({
        url: ctx + '/project/paramTemplateHandle',
        type: 'POST',
        contentType: 'application/json',
        data: param,
        success: function (data) {
            if (data.status == 1) {
                closeProgressExt(j)
                tipMsg(data.msg);
                setTimeout(function(){
                    back();
                },1000);
            } else {
                tipMsg(data.msg);
            }
        }
    });
}
function autoSave(arr,nodeArr) {
    var projectId = document.getElementById("projectId").value;
    var isGroupTemplate = document.getElementById("isGroupTemplate").value;
    var param = {
        params: JSON.stringify(arr),
        projectId: projectId,
        isGroupTemplate:isGroupTemplate,
        nodeId: JSON.stringify(nodeArr)
    }
    param = JSON.stringify(param)
    console.log(param);

    $.ajax({
        url: ctx + '/project/paramTemplateHandle',
        type: 'POST',
        contentType: 'application/json',
        data: param,
        success: function (data) {
            if (data.status == 1) {
                // tipMsg(data.msg);
                console.log(data.msg);
            } else {
                // tipMsg(data.msg);
                console.log(data.msg);
            }
        }
    });
}

    function submitProjectParamTemplate(pId) {
        console.log("nodeId" + pId)
        var indexId = document.getElementById("projectParamIndexId_" + pId).value;
        var arr = getFormJson("projectParam" + "_" + indexId + "_" + pId);
        var nodeArr = [];
        console.log(JSON.stringify(arr));
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].value == "") {
                tipMsg('请设置参数!');
                return;
            }
        }
        var projectId = document.getElementById("projectId").value;
        var json = {
            nodeId: pId
        };
        nodeArr.push(json);
        var param = {
            params: JSON.stringify(arr),
            projectId: projectId,
            nodeId: JSON.stringify(nodeArr)

        }
        param = JSON.stringify(param)
        console.log(param);

        var i = openProgressExt("保存中...");

        $.ajax({
            url: ctx + '/project/paramTemplateHandle',
            type: 'POST',
            contentType: 'application/json',
            data: param,
            success: function (data) {
                closeProgressExt(i);
                if (data.status == 1) {
                    tipMsg(data.msg);
                    // setTimeout(function () {
                    //     back();
                    // }, 1000);
                    $("#" + projectId + "_" + pId).css("color", "green");
                } else {
                    tipMsg(data.msg);
                }
            }
        });

    }
function getFormJsonAuto(inputName) {
    console.log(inputName)
    var arr = [];
    $("[name=" + inputName + "]").each(function () {
        // 选中后取取选择框的值
        if ($(this).attr("type") != "checkbox") {
            // console.log("这个选择框" + $(this).attr("type"));
            // console.log("这个选择框" + $(this).is(':checked'));
            console.log("标签类型" + $(this).prop("nodeName"));
            //
            if ($(this).prop("nodeName") == "SELECT" && !$(this).prop("disabled")) {
                console.log("下拉框" + $(this).prop("disabled"));
                if ($(this).val() != ""){
                    var json = {
                        key: $(this).attr("key"),
                        value: $(this).val(),
                        name: $(this).find("option:selected").text(),
                        isChooseSelf: 0
                    };
                    arr.push(json);
                }
            } else {
                if ($(this).parent('.uploader').css("display") == "block") {
                    console.log("是否隐藏" + $(this).parent('.uploader').css("display"))
                    if ($(this).val() != ""){
                        var json = {
                            key: $(this).attr("key"),
                            value: $(this).val(),
                            isChooseSelf: 1
                        };
                        arr.push(json);
                    }
                }

            }
        }
    });

    return arr;
}
    function getFormJson(inputName) {
        console.log(inputName)
        var arr = [];
        $("[name=" + inputName + "]").each(function () {
            // 选中后取取选择框的值
            if ($(this).attr("type") != "checkbox") {
                // console.log("这个选择框" + $(this).attr("type"));
                // console.log("这个选择框" + $(this).is(':checked'));
                console.log("标签类型" + $(this).prop("nodeName"));
                //
                if ($(this).prop("nodeName") == "SELECT" && !$(this).prop("disabled")) {
                    console.log("下拉框" + $(this).prop("disabled"));
                    var json = {
                        key: $(this).attr("key"),
                        value: $(this).val(),
                        name: $(this).find("option:selected").text(),
                        isChooseSelf: 0
                    };
                    arr.push(json);
                } else {
                    if ($(this).parent('.uploader').css("display") == "block") {
                        console.log("是否隐藏" + $(this).parent('.uploader').css("display"))
                        var json = {
                            key: $(this).attr("key"),
                            value: $(this).val(),
                            isChooseSelf: 1
                        };
                        arr.push(json);
                    }

                }
            }
        });

        return arr;
    }

