$(document).ready(function () {


});

//TODO 保存
function saveProjectParamTemplate() {

    var arr = getFormJson("projectParam");
    console.log(JSON.stringify(arr));
    for (var i = 0; i < arr.length; i++) {
        if (arr[i].value == "") {
            tipMsg('请选择常量或附件!');
            return;
        }
    }
    var projectId = document.getElementById("projectId").value;
    var indexId = document.getElementById("indexId").value;
    var param = {
        params: JSON.stringify(arr),
        projectId: projectId,
        indexId:indexId,

    }
    param = JSON.stringify(param)
    console.log(param);

    var i = openProgressExt("保存中...");

    $.ajax({
        url: ctx + '/project/paramTemplateHandle',
        type:'POST',
        contentType : 'application/json',
        data:param,
        success: function (data) {
            closeProgressExt(i);
            if (data.status == 1) {
                tipMsg(data.msg);
                setTimeout(function () {
                    back();
                }, 1000);
            } else {
                tipMsg(data.msg);
            }
        }
    });

}

function getFormJson(inputName) {
    var arr = [];
    $("[name=" + inputName + "]").each(function () {
        console.log()
        var json = {
            id:$(this).attr("key"),
            value: $(this).val(),
            name: $(this).find("option:selected").text(),
        };
        arr.push(json);
    });

    return arr;
}

function back() {
    window.history.go(-1);
}
