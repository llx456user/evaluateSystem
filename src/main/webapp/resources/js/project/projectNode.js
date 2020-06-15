// 显示列表

//$(document).ready(function() {
//	init();
//});

// define color
var green = "#008000";//计算成功为绿色
var red = "#FF0000";//运行失败红色
var blue="#0000FF" ;//配置了属性，可以运行，则为蓝色
var gray="#808080";//默认为灰色
// var rootkey=0;
var nodeDataArray = [];//节点信息数组
function init(initNodeArray) {
    if (window.goSamples) goSamples();  // init for these samples -- you don't need to call this
    var $ = go.GraphObject.make;  // for conciseness in defining templates

    myDiagram = $(go.Diagram, "myDiagramDiv",   // create a Diagram for the DIV HTML element
        {
            initialContentAlignment: go.Spot.Center,  // position the graph in the middle of the diagram
            "ModelChanged": function (e) {
                if (e.isTransactionFinished)
                    showModel();
            },
            layout: $(go.TreeLayout, { nodeSpacing: 5, layerSpacing: 30 }),
            allowDrop: true,  // must be true to accept drops from the Palette
            "LinkDrawn": showLinkLabel,  // this DiagramEvent listener is defined below
            "LinkRelinked": showLinkLabel,
            "animationManager.duration": 600, // slightly longer than default (600ms) animation
            "undoManager.isEnabled": true   // enable undo & redo
        });
    /****************************节点右键事件*************************************/
// To simplify this code we define a function for creating a context menu button:
    function makeButton(text, action, visiblePredicate) {
        return $("ContextMenuButton",
            $(go.TextBlock, text),
            {click: action},
            // don't bother with binding GraphObject.visible if there's no predicate
            visiblePredicate ? new go.Binding("visible", "", function (o, e) {
                return o.diagram ? visiblePredicate(o, e) : false;
            }).ofObject() : {});
    }

    var nodeContextMenu = $(go.Adornment, "Vertical",
        makeButton("指标设置",
            function (e, obj) {
                var contextmenu = obj.part;
                var part = contextmenu.adornedPart;
                settingNodeinfo(part.data);
            }),
        makeButton("子指标",
            function (e, obj) {
                var contextmenu = obj.part;
                var part = contextmenu.adornedPart;
                settingChildIndexinfo(part.data);
            }),
        makeButton("ahp设置",
            function (e, obj) {
                var contextmenu = obj.part;
                var part = contextmenu.adornedPart;
                calculationAhp(part.data);
            }),
        makeButton("权重设置",
        		function (e, obj) {
        	            var contextmenu = obj.part;
                        var part = contextmenu.adornedPart;
                        weightSet(part.data);
            }),
        makeButton("添加",
            function (e, obj) {
                var contextmenu = obj.part;
                var part = contextmenu.adornedPart;
                addIndexNode(part.data);
            }),
        makeButton("删除",
            function (e, obj) {
                var contextmenu = obj.part;
                var part = contextmenu.adornedPart;
                deleteIndexNode(part.data);
            }),
        makeButton("修改名称",
            function (e, obj) {
                var contextmenu = obj.part;
                var part = contextmenu.adornedPart;
                updateName(part.data);
            })
    );


    // Define a simple node template consisting of text followed by an expand/collapse button
    myDiagram.nodeTemplate =
        $(go.Node, "Horizontal",
            { selectionChanged: nodeSelectionChanged },  // this event handler is defined below
            $(go.Panel, "Auto",
                $(go.Shape, { fill: green, stroke: null ,width: 120, height: 35},new go.Binding("fill", "color")),
                $(go.TextBlock,
                    { font: "bold 13px Helvetica, bold Arial, sans-serif",
                        stroke: "white", margin: 3 },
                    new go.Binding("text", "name"))
            ),
            {contextMenu: nodeContextMenu},
            $("TreeExpanderButton")
        );

    // Define a trivial link template with no arrowhead.
    myDiagram.linkTemplate =
        $(go.Link,
            { selectable: false },
            $(go.Shape));  // the link shape

    // create the model for the DOM tree
    myDiagram.model =
        $(go.TreeModel, {
            isReadOnly: true,  // don't allow the user to delete or copy nodes
            // build up the tree in an Array of node data
            nodeDataArray: initNodeArray //getNodeDataArray()
        });

// Make link labels visible if coming out of a "conditional" node.
    // This listener is called by the "LinkDrawn" and "LinkRelinked" DiagramEvents.
    function showLinkLabel(e) {
        var label = e.subject.findObject("LABEL");
        if (label !== null) label.visible = (e.subject.fromNode.data.figure === "Diamond");
    }
}

function showModel() {
    var modelJson = myDiagram.model.toJson();
    // alert(modelJson);
}

// function getNodeDataArray(){
// 	var nodeArray = [{ key: "1" ,name:"根节点"}, { key: "2", parent: "1",name:"一级节点1" ,color:green}, { key: "2", parent: "1" ,name:"一级节点2",color:red}];
// 	return nodeArray;
// }


/**
 * 指标选择
 * @param data
 */
function settingNodeinfo(data) {
    var key = data.key;
    var fid= self.frameElement.getAttribute('id');
    var url = ctx + '/project/setProjectIndex?frameid='+fid+'&nodeId='+key;
	top.openDialog('选择指标','1000px','650px',url,function(data){
		//TODO
		console.log(data);
	});
}

/**
 * 子指标选择
 * @param data
 */
function settingChildIndexinfo(data) {
    var key = data.key;
    var fid= self.frameElement.getAttribute('id');
    var url = ctx + '/project/setProjectChildIndex?frameid='+fid+'&nodeId='+key;
    top.openDialog('选择子指标','1000px','650px',url,function(data){
        //TODO
        console.log(data);
    });
}


//等待 后台存储指标信息 indexId为选择的子节点Id
function callBackSetChildIndex(indexId,nodeId){
    var json = {id: nodeId,indexId:indexId};
    myajax(ctx+"/project/setIndexChildId",json,
        function(data){});
}

//等待 后台存储指标信息 indexId为选择的指标Id
function callBackSetIndex(indexId,nodeId){
    var json = {id: nodeId,indexId:indexId};
    myajax(ctx+"/project/setIndexId",json,
        function(data){});
}

/**
 * 计算ahp
 * @param data
 */
function calculationAhp(data) {
    var key = data.key;
    var isNode = true ;
    var nodedata = myDiagram.model.nodeDataArray;
    for (var i = 0; i < nodedata.length; i++) {
        if (key == nodedata[i].parent) {
            isNode=false;
        }
    }
    if(isNode){
        tipMsg('叶子节点不能设置ahp');
        return ;
    }
    var url = ctx + '/project/editProjectIndex?id='+key;
	//window.location.href = url;
	top.openLayerDialog('ahp','80%','80%',url,function(){
		//TODO
	});
}

/**
 * 权重设置
 * @param data
 */
function weightSet(data) {
    var key = data.key;
    var isNode = true ;
    var nodedata = myDiagram.model.nodeDataArray;
    for (var i = 0; i < nodedata.length; i++) {
        if (key == nodedata[i].parent) {
            isNode=false;
        }
    }
    if(isNode){
        tipMsg('叶子节点不能设置权重');
        return ;
    }
    var url = ctx + '/project/weightSet?id='+key;
	//window.location.href = url;
	top.openLayerDialog('修改指标','80%','80%',url,function(){
		//TODO
	});
}

/**
 * 添加节点
 * @param data
 */
function addIndexNode(data){
    var key = data.key;
    var node =addIndex(key);
//    myDiagram.startTransaction("add Tree Node");
//    var node = {
//        key: nodeDataArray.length+"",
//        name: "测试添加",
//        parent:key
//    }
//    myDiagram.model.addNodeData(node);
//    myDiagram.commitTransaction("add Tree Node");
//    myDiagram.rebuildParts;
}

/**
 * 删除节点
 * @param data
 */
function deleteIndexNode(data){
    var key = data.key;

    var isNode = true ;

    var nodedata = myDiagram.model.nodeDataArray;
    for (var i = 0; i < nodedata.length; i++) {
        if (key == nodedata[i].parent) {
            isNode=false;
        }
    }
    // if(nodedata.length=0){
    //     isNode=false;
    // }
    if(!isNode){
        tipMsg('根节点无法删除');
        return ;
    }
    if (nodedata.length==1){
        tipMsg('根节点无法删除');
        return ;
    }

    deleteNode(key);
//    myDiagram.startTransaction("delete Tree Node");
//    myDiagram.model.removeNodeData(node);
//    myDiagram.commitTransaction("delete Tree Node");
//    myDiagram.rebuildParts;
}


/**
 * 修改节点名称
 * @param data
 */
function updateName(data){
    var key = data.key;
    var name = data.name;


    updateNode(key,name,data);






}


//设置权重
//function weightCurrentSet(data){
//	var key = data.key;
//	var el = $("#setWeightCurrentPage");
//	el.modal({'show': true, 'backdrop': 'static', 'position': '50'});
//	el.find(".OK").off('click').on('click', function () {
//
//         var weightCurrent = el.find("input[name=weightCurrent]").val();
//        if (weightCurrent == '') {
//            tipMsg('请输入权重值');
//            return;
//        }
//
//        var json = {id: key,weightCurrent:weightCurrent};
//        myajax(ctx+"/project/setWeightCurrent",json,
//            function(data){
//                if(data.status==1){
//                    el.modal('hide');//确认关闭
//                    tipMsg(data.msg);
//                }else{
//                    tipMsg(data.msg);
//                }
//
//            });
//    });
//
//}

/****************************节点右键事件结束*************************************/


// When a Node is selected,
function nodeSelectionChanged(node) {
    if (node.isSelected) {

    } else {

    }
}

/****************************业务事件*************************************/
function viewIndex() {
    var id=$("#id").val();
    window.location.href = ctx + '/project/viewNodeTree?projectInfoId='+id ;
}

function addRootIndex(parentKey){
    var id=$("#id").val();
    var json = {projectId: id,parentId:parentKey};
    myajax(ctx+"/project/checkRoot",json,
        function(data) {
            if(data.status==0){
                tipMsg(data.msg);
                return;
            }else {
                addIndex(parentKey);
            }
        });
}
function addIndex(parentKey){
	var newKey;
	var indexName;
	 // var remarks;
    var el = $("#addIndexPage");

    el.modal({'show': true, 'backdrop': 'static', 'position': '50'});
    el.find("input[name=indexName]").val('');//初始化的时候设置为空
    // document.getElementsByName('indexName').focus();//获取焦点
    setTimeout("$('input[name=indexName]').focus()",500);
    el.find(".OK").off('click').on('click', function () {
        var id=$("#id").val();
        if(id == null || id =='' || id==undefined){
            tipMsg('请先保存项目后继续操作。');
            return;
        }
         indexName = el.find("input[name=indexName]").val();
         // remarks = el.find("textarea[name=remarks]").val();


        if (indexName == '') {
            tipMsg('请输入名称');
            return;
        }
        // if (remarks == '') {
        //     tipMsg('请输入备注信息');
        //     return;
        // }
        var i = openProgressExt("确认中...");
        var json = {projectId: id,indexName:indexName,parentId:parentKey};
        myajax(ctx+"/project/addProjectIndex",json,
            function(data){
                closeProgressExt(i);
                if(data.status==1){
                    el.modal('hide');//确认关闭
                    newKey=data.info;
//                    tipMsg(data.msg);
                    myDiagram.startTransaction("add Tree Node");
                    var node = {
                            key: newKey,
                            name: indexName,
                            parent:parentKey
                        };

                  myDiagram.model.addNodeData(node);
                  myDiagram.commitTransaction("add Tree Node");
                  myDiagram.rebuildParts;
                }else{
                    tipMsg(data.msg);
                }

            });
    });
}

//等待调用---指标编辑页面
function showEditIndex(id){
    var url = ctx + '/project/editProjectIndex?id=1';
    //window.location.href = url;
    top.openDialog('设置权重','80%','80%',url,function(){
        //TODO
    });
}
//等待调用---开始评估页面
function showEvaludate(id){
    var url = ctx + '/project/startEvaluate?indexId='+id+'&projectId='+$("#id").val();
    //window.location.href = url;
    top.openDialog('评估页面','80%','80%',url,function(){
        //TODO
    });
}

//等待调用---删除节点
function deleteNode(key){
    var nodeId = key;//当前节点本身的ID
//    var orderNum = 2;//当前节点所在位置、排序
    var url = ctx + '/project/deleteProjectIndex';
    var json = {id:nodeId};
    myajax(url,json,function(data){
            setTimeout(function() {
            	 var node=myDiagram.model.findNodeDataForKey(key) ;
            	    // alert(node);
            	   myDiagram.startTransaction("delete Tree Node");
            	    myDiagram.model.removeNodeData(node);
            	    myDiagram.commitTransaction("delete Tree Node");
            	    myDiagram.rebuildParts;
            }, 500);
    });

}

//节点名称修改
function updateNode(key,name,data){
    var nodeId = key;//当前节点本身的ID
    var el = $("#updateIndexName");

    el.find("input[name=indexName]").val(name);
    var id=$("#id").val();
    el.modal({'show': true, 'backdrop': 'static', 'position': '50'});
    // el.find("input[name=indexName]").val();//初始化的时候设置为空
    // document.getElementsByName('indexName').focus();//获取焦点
    setTimeout("$('input[name=indexName]').focus()",500);
    el.find(".OK").off('click').on('click', function () {
        var indexName = el.find("input[name=indexName]").val();
        // remarks = el.find("textarea[name=remarks]").val();
        if (indexName == '') {
            tipMsg('请输入名称');
            return;
        }
        var i = openProgressExt("确认中...");
        var json = {projectId: id,id:nodeId,indexName:indexName};
        myajax(ctx+"/project/updatename",json,
            function(data){
                closeProgressExt(i);
                if(data.status==1){
                    el.modal('hide');//确认关闭
//                    tipMsg(data.msg);
                    myDiagram.startTransaction("update Tree Node");
                    var node = {
                        name: indexName
                    };
                    var nodeData = myDiagram.model.findNodeDataForKey(key);
                    nodeData.name =indexName;
                    myDiagram.model.updateTargetBindings(nodeData);

                }else{
                    tipMsg(data.msg);
                }

            });
    });



}
