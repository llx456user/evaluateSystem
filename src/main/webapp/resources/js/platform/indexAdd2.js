// 显示列表

$(document).ready(function () {
    init();
});

var myDiagram;
var blueShapeBody = "#B1EAD9";
// connectPoint = "#3CA685";
var connectPoint = "#3C60A1";
var lineOne = "#15B68D";
var polygoncolor = "#359F74";
var tableHeadercolor = "#FFFFFF";
var tableHeaderfontcolor = "#343b42";
var tableBodycolor = "#FFFFFF";
// checkboxSelectedColor ="#24BFAA";
var checkboxSelectedColor = "#3099f5";
var defaultColorFromExcanvas = "#D2EDE8";
var fontColor = "#343b42";
var nodeDataArray = [];//节点信息数组
var linkDataArray = [];//连线信息数组
function init() {
    if (window.goSamples) goSamples();  // init for these samples -- you don't need to call this
    var $ = go.GraphObject.make;    // for conciseness in defining templates
    myDiagram =
        $(go.Diagram, "show",   // create a Diagram for the DIV HTML element
            {
                initialContentAlignment: go.Spot.Center,  // position the graph in the middle of the diagram
                "ModelChanged": function (e) {
                    if (e.isTransactionFinished)
                        showModel();
                },
                allowDrop: true,  // must be true to accept drops from the Palette
                "LinkDrawn": showLinkLabel,  // this DiagramEvent listener is defined below
                "LinkRelinked": showLinkLabel,
                "animationManager.duration": 600, // slightly longer than default (600ms) animation
                "undoManager.isEnabled": true   // enable undo & redo
            });


    //the left part
    function nodeStyle() {
        return [
            new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
            {
                locationSpot: go.Spot.Center,
                mouseEnter: function (e, obj) {
                    showPorts(obj.part, true);
                },
                mouseLeave: function (e, obj) {
                    showPorts(obj.part, false);
                }
            }
        ];
    }


    function showPorts(node, show) {
        var diagram = node.diagram;
        if (!diagram || diagram.isReadOnly || !diagram.allowLink) return;
        node.ports.each(function (port) {
            port.stroke = (show ? fontColor : null);
        });
    }

    function makePort(name, spot, output, input) {
        return $(go.Shape, "Circle",
            {
                fill: "transparent",
                stroke: null,  // this is changed to "white" in the showPorts function
                desiredSize: new go.Size(8, 8),
                alignment: spot, alignmentFocus: spot,  // align the port on the main Shape
                portId: name,  // declare this object to be a "port"
                fromSpot: spot, toSpot: spot,  // declare where links may connect at this port
                fromLinkable: output, toLinkable: input,  // declare whether the user may draw links to/from here
                cursor: "pointer"  // show a different cursor to indicate potential link point
            });
    }

    function portInfo(d) {  // Tooltip info for a port data object
        if (!d.loc) {     //data and picture
            if (d.unit) {     //model
                var str = "参数名称：" + d.name + "， "
                    + "参数单位：" + d.unit + "， "
                    + "参数类型：" + d.modelPortType + "\n";
            } else {
                var str = "参数变量名:" + d.name + "\n";
            }
        }
        return str;
    }


    /*****************************右键事件开始********************************/
// To simplify this code we define a function for creating a context menu button:
//右键按钮事件
// To simplify this code we define a function for creating a context menu button:
    function makeButton(text, action, visiblePredicate) {
        return $("ContextMenuButton",
            $(go.TextBlock, text),
            { click: action },
            // don't bother with binding GraphObject.visible if there's no predicate
            visiblePredicate ? new go.Binding("visible", "", function(o, e) { return o.diagram ? visiblePredicate(o, e) : false; }).ofObject() : {});
    }
    var partIfContextMenu  =  $(go.Adornment, "Vertical",
        makeButton("删除",
            function(e, obj) { e.diagram.commandHandler.deleteSelection(); },
            function(o) { return o.diagram.commandHandler.canDeleteSelection(); }),
        makeButton("设置条件",
            function(e, obj) { var contextmenu = obj.part;
                var part = contextmenu.adornedPart;
                alert(part.data)})
                    //     $scope.settingIf(part.data); })
    );

    function settingIf(data) {
        var key = data.key;
        // var condition = data
        // var


    }

    /****************************右键事件结束**********************************/

    //----------------------------------模型模板-------------------------------------//

    myDiagram.nodeTemplateMap.add("0",   //Data描画数据模型
        $(go.Node, "Spot", /*clickModel(),*/
            {
                selectionAdorned: false
                /*selectionChanged: nodeSelectionChanged*/
            },
            {
                locationSpot: go.Spot.Center,
                locationObjectName: "BODY"
            },
            new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
            $(go.Panel, "Auto",
                {name: "BODY"},
                $(go.Shape, "Database",  //model图形形状
                    {stroke: "gray", strokeWidth: 2, fill: blueShapeBody},
                    new go.Binding("stroke", "isSelected", function (b) {
                        return b ? SelectedBrush : UnselectedBrush;
                    }).ofObject()),
                $(go.Panel, "Vertical",
                    {width: 130, height: 90},  //model主体方框
                    $(go.TextBlock, new go.Binding("text", "name"),
                        {alignment: go.Spot.TopCenter, font: "14px Times New Roman", stroke: fontColor}
                    )
                )
            ),
            $(go.Panel, "Vertical",
                {name: "RightPorts", alignment: new go.Spot(1, 0.63, -33, 7)},
                new go.Binding("itemArray", "outServices"),
                {itemTemplate: makeItemTemplate(false)}
            ),
            {
                //    contextMenu: partDataContextMenu
            }
        ));

    myDiagram.nodeTemplateMap.add("1",      //Model描画模型
        $(go.Node, "Spot", /*clickModel(),*/
            {
                selectionAdorned: false
                /*selectionChanged: nodeSelectionChanged*/
            },
            {locationSpot: go.Spot.Center, locationObjectName: "BODY"},
            new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
            $(go.Panel, "Auto", {name: "BODY"},
                $(go.Shape, "Cube1",  //model图形形状
                    {stroke: "gray", strokeWidth: 2, fill: blueShapeBody},
                    new go.Binding("stroke", "isSelected", function (b) {
                        return b ? SelectedBrush : UnselectedBrush;
                    }).ofObject()),
                $(go.Panel, "Vertical",
                    {margin: 5, width: 65, height: 90},  //model主体方框
                    $(go.TextBlock, new go.Binding("text", "name"),
                        {alignment: go.Spot.Center, font: "14px Times New Roman", stroke: fontColor}
                    )
                )
            ),
            $(go.Panel, "Vertical",
                {name: "LeftPorts", alignment: new go.Spot(0, 0.6, 30, 7)},
                new go.Binding("itemArray", "inServices"),
                {itemTemplate: makeItemTemplate(true)}
            ),
            $(go.Panel, "Vertical",
                {name: "RightPorts", alignment: new go.Spot(1, 0.6, -30, 7)},
                new go.Binding("itemArray", "outServices"),
                {itemTemplate: makeItemTemplate(false)}
            ),
            {
                //   contextMenu: partContextMenu
            }
        ));

    myDiagram.nodeTemplateMap.add("2",        //Picture描画图表模型
        $(go.Node, "Spot", /*clickModel(),*/
            {
                selectionAdorned: false
                /*selectionChanged: nodeSelectionChanged*/
            },
            {locationSpot: go.Spot.Center, locationObjectName: "BODY"},
            new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
            $(go.Panel, "Auto", {name: "BODY"},
                $(go.Shape, "RoundedRectangle",  //model图形形状
                    {stroke: "gray", strokeWidth: 2, fill: blueShapeBody},
                    new go.Binding("stroke", "isSelected", function (b) {
                        return b ? SelectedBrush : UnselectedBrush;
                    }).ofObject()),
                $(go.Panel, "Vertical",
                    {margin: 5, width: 90, height: 100},  //model主体方框
                    $(go.TextBlock, new go.Binding("text", "text").makeTwoWay(),
                        {
                            alignment: go.Spot.Center, font: "14px Times New Roman",
                            stroke: fontColor, editable: true, isMultiline: false
                        }
                    )
                )
            ),
            $(go.Panel, "Vertical",
                {name: "LeftPorts", alignment: new go.Spot(0, 0.53, 35, 7)},
                new go.Binding("itemArray", "inServices"),
                {itemTemplate: makeItemTemplate(true)}
            ),
            {
                // contextMenu: partPictureContextMenu
            }
        ));
    var lightText = 'whitesmoke';
    myDiagram.nodeTemplateMap.add("3",                  //常数模型
        $(go.Node, "Spot", nodeStyle(),
            $(go.Panel, "Auto",
                $(go.Shape, "hexagon",
                    {
                        fill: polygoncolor, stroke: null,
                        minSize: new go.Size(80, 80)
                    },
                    new go.Binding("figure", "category")),
                $(go.TextBlock,
                    {
                        font: "12px Times New Roman",
                        stroke: lightText, margin: 2,
                        maxSize: new go.Size(100, 100),
                        wrap: go.TextBlock.WrapFit,
                        editable: true
                    },
                    new go.Binding("text").makeTwoWay())
            ),
            makePort("T", go.Spot.Top, false, true),
            makePort("L", go.Spot.Left, true, true),
            makePort("R", go.Spot.Right, true, true),
            makePort("B", go.Spot.Bottom, true, false)
        ));

    myDiagram.nodeTemplateMap.add("4",                 //if判断模型
        $(go.Node, "Spot", nodeStyle(),
            $(go.Panel, "Auto",
                $(go.Shape, "Diamond",
                    {
                        fill: polygoncolor, stroke: null,
                        minSize: new go.Size(80, 80)
                    },
                    new go.Binding("figure", "category")),
                $(go.TextBlock,
                    {
                        font: "12px Times New Roman",
                        stroke: lightText, margin: 2,
                        maxSize: new go.Size(100, 100),
                        wrap: go.TextBlock.WrapFit
                        // editable: true
                    },
                    new go.Binding("text").makeTwoWay())
            ),
            // { // this tooltip Adornment is shared by all groups
            //     toolTip:
            //         $(go.Adornment, "Auto",
            //             $(go.Shape, { fill: "#FFFFCC" }),
            //             $(go.TextBlock, { margin: 4 },
            //                 // bind to tooltip, not to Group.data, to allow access to Group properties
            //                 new go.Binding("text", "", groupInfo).ofObject())
            //         ),
            //     // the same context menu Adornment is shared by all groups
            //     contextMenu: partIfContextMenu
            // },
            {contextMenu: partIfContextMenu},       //if右键
            makePort("T", go.Spot.Top, false, true),
            makePort("L", go.Spot.Left, true, true),
            makePort("R", go.Spot.Right, true, true),
            makePort("B", go.Spot.Bottom, true, false)
        ));

    myDiagram.nodeTemplateMap.add("5",        //表格
        $(go.Node, "Spot", /*clickModel(),*/
            { selectionAdorned: false
                /*selectionChanged: nodeSelectionChanged*/ },
            { locationSpot: go.Spot.Center, locationObjectName: "BODY" },
            new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
            $(go.Panel, "Auto", { name: "BODY" },
                $(go.Shape, "RoundedRectangle",  //model图形形状
                    { stroke: "gray", strokeWidth: 2, fill: blueShapeBody },
                    new go.Binding("stroke", "isSelected", function(b) { return b ? SelectedBrush : UnselectedBrush; }).ofObject()),
                $(go.Panel, "Vertical",
                    { margin: 5 ,width: 90, height: 100},  //model主体方框
                    $(go.TextBlock, new go.Binding("text", "text").makeTwoWay(),
                        { alignment: go.Spot.Center, font: "14px Times New Roman",
                            stroke:fontColor,editable: true,isMultiline: false}
                    )
                )
            ),
            $(go.Panel, "Vertical",
                { name: "LeftPorts", alignment: new go.Spot(0, 0.53, 35, 7) },
                new go.Binding("itemArray", "inServices"),
                { itemTemplate: makeItemTemplateGrid(true) }
            ),
            {
                //contextMenu: partGridContextMenu
            }
        ));


    //表格port
    function makeItemTemplateGrid(leftside) {       //port描画端口
        return $(go.Panel, "Auto",
            { margin: new go.Margin(2, 0) },  // some space between ports
            $(go.Shape,
                { name: "SHAPE", //fill: UnselectedBrush,
                    fill: connectPoint, stroke: "gary", strokeWidth: 0,
                    width: 85, height: 14,    //port大小
                    geometryString: "F1 m 0,0 l 13,0 1,4 -1,4 -13,0 1,-4 -1,-4 z",
                    spot1: new go.Spot(0, 0, 5, 1),  // keep the text inside the shape
                    spot2: new go.Spot(1, 1, -5, 0),
                    toSpot: go.Spot.Left,
                    toLinkable: leftside,
                    fromSpot: go.Spot.Right,
                    fromLinkable: !leftside,
                    cursor: "pointer"
                },

                new go.Binding("portId", "name"),
                new go.Binding("fill", "color")
            ),
            $(go.TextBlock,
                new go.Binding("text", "name").makeTwoWay(),
                {
                    // isActionable: true,
                    // textAlign: "center",
                    // stroke:"black",
                    // font: "12px Times New Roman",
                    // editable: true
                    font: "12px Times New Roman",
                    stroke: lightText, margin: 2,
                    maxSize: new go.Size(100, 100),
                    wrap: go.TextBlock.WrapFit,
                    editable: true
                }
            ),
            {toolTip:                   //端口信息tooltip
                $(go.Adornment, "Auto",
                    $(go.Shape, { fill: "#FFFFCC", height: 25}),
                    $(go.TextBlock, { margin: 5 ,textAlign: "center",
                            alignment: go.Spot.Top,
                            font: "12px Times New Roman"},  // the tooltip shows the result of calling nodeInfo(data)
                        new go.Binding("text", "", portInfo)))
            }
        );
    }

    function makeItemTemplate(leftside) {       //port描画端口
        return $(go.Panel, "Auto",
            {margin: new go.Margin(2, 0)},  // some space between ports
            $(go.Shape,
                {
                    name: "SHAPE", //fill: UnselectedBrush,
                    fill: connectPoint, stroke: "gary", strokeWidth: 0,
                    width: 85, height: 14,    //port大小
                    geometryString: "F1 m 0,0 l 13,0 1,4 -1,4 -13,0 1,-4 -1,-4 z",
                    spot1: new go.Spot(0, 0, 5, 1),  // keep the text inside the shape
                    spot2: new go.Spot(1, 1, -5, 0),
                    toSpot: go.Spot.Left,
                    toLinkable: leftside,
                    fromSpot: go.Spot.Right,
                    fromLinkable: !leftside,
                    cursor: "pointer"
                },
                { // allow the user to select items -- the background color indicates whether "selected"
                    //?? maybe this should be more sophisticated than simple toggling of selection
                    click: function (e, item) {
                        if (!leftside)//点击又端口时
                        {
                            myDiagram.clearHighlighteds();
                            var links = item.part.findLinksTo(item);
                            var linkarray = links.tc.n;
                            for (var i = 0; i < linkarray.length; i++) {
                                if (linkarray[i].fromPortId == item.portId) {
                                    linkarray[i].isHighlighted = true;
                                }
                            }

                        }
                    }
                },
                new go.Binding("portId", "name"),
                new go.Binding("fill", "color")
            ),
            $(go.TextBlock,
                new go.Binding("text", "name"),
                {
                    isActionable: true,
                    textAlign: "center",
                    stroke: "black",
                    font: "12px Times New Roman",
                    editable: true

                }
            ),
            {
                toolTip:                   //端口信息tooltip
                    $(go.Adornment, "Auto",
                        $(go.Shape, {fill: "#FFFFCC", height: 25}),
                        $(go.TextBlock, {
                                margin: 5, textAlign: "center",
                                alignment: go.Spot.Top,
                                font: "12px Times New Roman"
                            },  // the tooltip shows the result of calling nodeInfo(data)
                            new go.Binding("text", "", portInfo)))
            }
        );
    }

    myPalette = $(go.Palette, "myPaletteDiv",  // must name or refer to the DIV HTML element
        {
            "animationManager.duration": 800, // slightly longer than default (600ms) animation
            nodeTemplateMap: myDiagram.nodeTemplateMap,  // share the templates used by myDiagram
            model: new go.GraphLinksModel([  // specify the contents of the Palette
                {text: "常数", category: "3"},
                {text: "if...", category: "4", sign: "", settingNum: ""}
            ])
        });


    //the linkLine
    myDiagram.linkTemplate = $(
        go.Link,
        {
            routing: go.Link.AvoidsNodes,
            curve: go.Link.JumpOver,
            corner: 5,
            relinkableFrom: true,
            relinkableTo: true,
            reshapable: true,
            resegmentable: true,
            toShortLength: -1,
            selectionChanged: linkSelectionChanged
        },
        {
            relinkableFrom: true,
            relinkableTo: true,
            toShortLength: 2
        },
        $(go.Shape, { isPanelMain: true,strokeWidth: 1.5, stroke: lineOne },
            new go.Binding("stroke", "isHighlighted", function(h) { return h ? "DarkTurquoise" :lineOne; }).ofObject()),

        $(go.Shape, { toArrow: "Standard", stroke: lineOne },
            new go.Binding("stroke", "isHighlighted", function(h) { return h ? "DarkTurquoise" : lineOne; }).ofObject())
        //$(
        //    go.Shape,{ isPanelMain: true,strokeWidth: 1.5, stroke: lineOne },
        //    new go.Binding("stroke", "isHighlighted", function(h) { return h ? "DarkTurquoise" : lineOne; }).ofObject()),
        //    $(
        //        go.Shape,
        //        {
        //            toArrow: 'Standard',
        //            stroke: lineOne
        //        }
        //    )
        //)
    );

    ////the linkLine
    //myDiagram.linkTemplate =            //line连接线设置
    //    $(go.Link,
    //        {
    //            routing: go.Link.AvoidsNodes,
    //            curve: go.Link.JumpOver,
    //            corner: 5,
    //            relinkableFrom: true,
    //            relinkableTo: true,
    //            reshapable: true,
    //            resegmentable: true,
    //            toShortLength: -1,
    //            selectionChanged: linkSelectionChanged
    //        },
    //        {relinkableFrom: true, relinkableTo: true, toShortLength: 2},
    //        // $(go.Shape,  // this shape only shows when it isHighlighted
    //        //     { isPanelMain: true, stroke: null,  strokeWidth: 5},
    //        //     new go.Binding("stroke", "isHighlighted", function(h) { return h ? "DarkTurquoise" : null; }).ofObject()),
    //        $(go.Shape, {isPanelMain: true, strokeWidth: 1.5, stroke: lineOne},
    //            new go.Binding("stroke", "isHighlighted", function (h) {
    //                return h ? "DarkTurquoise" : lineOne;
    //            }).ofObject()),
    //
    //        $(go.Shape, {toArrow: "Standard", stroke: lineOne},
    //            new go.Binding("stroke", "isHighlighted", function (h) {
    //                return h ? "DarkTurquoise" : lineOne;
    //            }).ofObject()),
    //
    //        $(go.Panel, "Auto",
    //            {visible: false, name: "LABEL", segmentIndex: 2, segmentFraction: 0.5},
    //            new go.Binding("visible", "visible").makeTwoWay(),
    //            $(go.Shape, "RoundedRectangle", {fill: "#F8F8F8", stroke: null}),
    //            $(go.TextBlock, "Yes",  // the label
    //                {
    //                    textAlign: "center",
    //                    font: "10pt helvetica, arial, sans-serif",
    //                    stroke: "#333333", editable: true
    //                },
    //                new go.Binding("text").makeTwoWay()
    //            )
    //        )
    //    );

    //check line两端数据类型是否匹配 OK/错误提示（删除line）
    function linkSelectionChanged(obj) {
        alert('连接库');
        //var fromPort = obj.part.data.fromPort;
        //var toPort = obj.part.data.toPort;
        var toNum = 0;
        var fromArray = [];
        var toArray = [];
        var fromPortType = "";
        var fromPortUnit = "";
        var toPortType = "";
        var toPortUnit = "";
        var indexToArray = -1;

        if (obj.part.data != null) {
            for (var i = 0; i < nodeDataArray.length; i++) {
                if (obj.part.data.from == nodeDataArray[i].key) {
                    fromArray = nodeDataArray[i];
                } else if (obj.part.data.to == nodeDataArray[i].key) {
                    toArray = nodeDataArray[i];
                    //   if(toArray.category="5"){
                    //       indexToArray=i;
                    //   }
                }
            }
            if (fromArray.category == "0") {
                fromPortType = "float";         //数据源端口全部为float类型（只有输出端口）
                //fromPortUnit
            } else if (fromArray.category == "1") {
                for (var j = 0; j < fromArray.outServices.length; j++) {
                    if (obj.part.data.fromPort == fromArray.outServices[j].name) {
                        fromPortType = fromArray.outServices[j].modelPortType;
                        fromPortUnit = fromArray.outServices[j].unit;
                    }
                }
            } else if (fromArray.category == "3") {            //常数
                //fromArray.text;  5
                //fromPortUnit
                //fromPortType
            } else if (fromArray.category == "4") {            //if判断
                //fromArray.text;  1233.2312
                //fromPortUnit
                //fromPortType
            }
            if (toArray.category == "1") {
                for (var k = 0; k < toArray.inServices.length; k++) {
                    if (obj.part.data.toPort == toArray.inServices[k].name) {
                        toPortType = toArray.inServices[k].modelPortType;
                        toPortUnit = toArray.inServices[k].unit;
                    }
                }
            } else if (toArray.category == "3") {          //常数
                //toArray.text
                //fromPortUnit
                //fromPortType
            } else if (toArray.category == "4") {          //if判断
                //toArray.text
                //fromPortUnit
                //fromPortType
            }

            for (var x = 0; x < linkDataArray.length; x++) {    //同一个输入只能有一个
                if ((linkDataArray[x].to == obj.part.data.to) && (linkDataArray[x].toPort == obj.part.data.toPort)) {
                    toNum++;
                }
            }
        }

        if (toNum > 1) {
            ModalUtils.alert("此端口已有输入", "modal-warning", "sm");
            //删除line
            // linkDataArray.splice(linkDataArray.length-1,1);
            drawDiagramModel();
        }
    }

//the linkLine end

    //建立初始化画板
    myDiagram.model =
        $(go.GraphLinksModel,
            {
                copiesArrays: true,
                copiesArrayObjects: true,
                linkFromPortIdProperty: "fromPort",
                linkToPortIdProperty: "toPort",
                nodeDataArray: nodeDataArray,
                linkDataArray: linkDataArray
            });

    //当有模型从列表中拖拽到画板，添加新描画模型，并重新描画
    var drawDiagramModel = function () {
        myDiagram.model =
            $(go.GraphLinksModel,
                {
                    copiesArrays: true,
                    copiesArrayObjects: true,
                    linkFromPortIdProperty: "fromPort",
                    linkToPortIdProperty: "toPort",
                    nodeDataArray: nodeDataArray,
                    linkDataArray: linkDataArray
                });
    };
//    var myModel = $(go.Model);
////  in the model data, each node is represented by a JavaScript object:
//    myModel.nodeDataArray = [
//        {key: "Alpha"},
//        {key: "Beta"},
//        {key: "Gamma"}
//    ];
//    myDiagram.model = myModel;

    // Make link labels visible if coming out of a "conditional" node.
    // This listener is called by the "LinkDrawn" and "LinkRelinked" DiagramEvents.
    function showLinkLabel(e) {
        var label = e.subject.findObject("LABEL");
        if (label !== null) label.visible = (e.subject.fromNode.data.figure === "Diamond");
    }





    //*************************************************init方法结束*************************************************************/
}

function showModel() {
    var modelJson = myDiagram.model.toJson();
    alert(modelJson);
}






function save() {
    var exampleInputAccount1= document.getElementById("exampleInputAccount1").value;
    var exampleInputPassword1=document.getElementById("exampleInputPassword1").value;

    var param={
        exampleInputAccount1:exampleInputAccount1,
        exampleInputPassword1:exampleInputPassword1
    }
    myajax(ctx+"/platform/indexSave",param,
    function(data){
        tipMsg(data.msg);

    },function(data){
            tipMsg(data.msg);

    });




}

//模型选择二级菜单
function slemodel(id) {


    var param = {
        modelId: id
    };
    myajax(ctx + '/platform/selectModel', param, function (data) {
        var result = eval(data);
        for (var i = 0; i < result.length; i++) {
            var html = "";
            html = "<li><a >" + data[i].modelName+ "</a></li>";
            $("#model_test1").append(html);//给li追加 a标签
        }
    });


}

function back() {

    window.history.go(-1);
}


//-----------------------------------------------------事件开始------------------------------------------------/
/**
 * 允许拖拽
 * @param ev
 */
function allowDrop(ev) {
    ev.preventDefault();
}

/**
 * 拖拽
 * @param ev
 */
function drag(ev) {
    ev.dataTransfer.setData("type", ev.target.type);
    ev.dataTransfer.setData("id", ev.target.id);
}

/**
 * 拖拽松开
 * @param ev
 */
function drop(ev) {

    nodeDataArray = myDiagram.model.nodeDataArray;
    linkDataArray = myDiagram.model.linkDataArray;
    ev.preventDefault();
    var type = ev.dataTransfer.getData("type");
    var id=ev.dataTransfer.getData("id");
    alert(type);

  if(id=='gridBtn'){//图形结束，表格
        myDiagram.startTransaction("add grid");//by tengfei 刷新问题
        var gridModel={
            key: nodeDataArray.length,
            category: "5",      //表格
            text: "表格",
            inServices: [{ name: "列1", color:connectPoint}]
        }
        myDiagram.model.addNodeData(gridModel);
        myDiagram.commitTransaction("add grid");
    }else if (type == 'bar') {//图形
        myDiagram.startTransaction("add Bar"); // 刷新问题
        var barModel = {
            key: nodeDataArray.length,
            category: "2",      //Picture
            type: type,         //图表类型
            text: "柱状图",
            inServices: [{name: "input-x", color: connectPoint},
                {name: "input-y", color: connectPoint}]
        }
        myDiagram.model.addNodeData(barModel);
        myDiagram.commitTransaction("add Bar");
    } else if (type == 'pie') {
        myDiagram.startTransaction("add pie");// 刷新问题
        var pieModel = {
            key: nodeDataArray.length,
            category: "2",      //Picture
            type: type,         //图表类型
            text: "饼图",
            inServices: [{name: "input-x", color: connectPoint},
                {name: "input-y", color: connectPoint}]
        }
        myDiagram.model.addNodeData(pieModel);
        myDiagram.commitTransaction("add pie");
    } else if (type == 'line') {

        myDiagram.startTransaction("add line");// 刷新问题
        var lineModel = {
            key: nodeDataArray.length,
            category: "2",      //Picture
            type: type,         //图表类型
            text: "折线图",
            inServices: [{name: "input-x", color: connectPoint},
                {name: "input-y", color: connectPoint}]
        }
        myDiagram.model.addNodeData(lineModel);
        myDiagram.commitTransaction("add line");
    } else if (type == 'scatter') {
        myDiagram.startTransaction("add scatter");// 刷新问题
        var scatterModel = {
            key: nodeDataArray.length,
            category: "2",      //Picture
            type: type,         //图表类型
            text: "散点图",
            inServices: [{name: "input-x", color: connectPoint},
                {name: "input-y", color: connectPoint}]
        };
        myDiagram.model.addNodeData(scatterModel);
        myDiagram.commitTransaction("add scatter");
    } else if (type == 'map') {

        myDiagram.startTransaction("add map");// 刷新问题
        var mapModel = {
            key: nodeDataArray.length,
            category: "2",      //Picture
            type: type,         //图表类型
            text: "地图",
            inServices: [{name: "input-x", color: connectPoint},
                {name: "input-y", color: connectPoint}]
        }
        myDiagram.model.addNodeData(mapModel);
        myDiagram.commitTransaction("add map");
    } else if (type == 'hscatter') {

        myDiagram.startTransaction("add hscatter");// 刷新问题
        var hscatterModel = {
            key: nodeDataArray.length,
            category: "2",      //Picture
            type: type,         //图表类型
            text: "三维图",
            inServices: [{name: "input-x", color: connectPoint},
                {name: "input-y", color: connectPoint},
                {name: "input-z", color: connectPoint}]
        }
        myDiagram.model.addNodeData(hscatterModel);
        myDiagram.commitTransaction("add hscatter");
    }else if(type=='selectModel'){
        var formulaList = [{"formulaId":"1187","formulaType":"int","inoutType":"1","formulaName":"p1","formulaUnit":"not_array","formulaSource":"TXT"},
            {"formulaId":"1188","formulaType":"float","inoutType":"1","formulaName":"p2","formulaUnit":"not_array","formulaSource":"TXT"},
            {"formulaId":"1189","formulaType":"long","inoutType":"1","formulaName":"p3","formulaUnit":"not_array","formulaSource":"TXT"}];
        var inServicesArray = [];
        var outServicesArray = [];
        for(var i = 0; i < formulaList.length; i++){
            var color = connectPoint;
            var type = "port";
            if(formulaList[i].formulaType == "int"){            //颜色标定数据类型
                color = connectPoint;
            }else if(formulaList[i].formulaType == "double"){
                color = "blue";
            }else if(formulaList[i].formulaType == "float"){
                color = "white";
            }else if(formulaList[i].formulaType == "long"){
                color = "red";
            }else{
                type = "struct";
            }
            var structId = 0;
            if(formulaList[i].inoutType == 1){              //输出
                if(type == "struct"){
                    for(var j = 0; j < formulaList.length; j++){
                        if(formulaList[i].formulaType == formulaList[j].formulaName){
                            structId = formulaList[j].formulaId;
                            break;
                        }
                    }
                    for(var k = 0; k < formulaList.length; k++){
                        if(structId == formulaList[k].parentId){            //查找list所有parentId
                            var nameTemp = "";
                            nameTemp = formulaList[i].formulaName + "." + formulaList[k].formulaName;
                            if(formulaList[i].formulaType == "int"){            //颜色标定数据类型
                                color = connectPoint;
                            }else if(formulaList[i].formulaType == "double"){
                                color = "blue";
                            }else if(formulaList[i].formulaType == "float"){
                                color = "white";
                            }else if(formulaList[i].formulaType == "long"){
                                color = "red";
                            }
                            outServicesArray.push(
                                { name: nameTemp, color:color, unit:formulaList[i].formulaUnit,
                                    modelPortType:formulaList[i].formulaType,source:formulaList[i].formulaSource }
                            );
                        }
                    }
                }else{
                    outServicesArray.push(
                        { name: formulaList[i].formulaName, color:color,
                            unit:formulaList[i].formulaUnit,
                            modelPortType:formulaList[i].formulaType,source:formulaList[i].formulaSource}
                    );
                }
            }else if(formulaList[i].inoutType == 0){        //输入
                if(type == "struct"){
                    for(var x = 0; x < formulaList.length; x++){
                        if(formulaList[i].formulaType == formulaList[x].formulaName){
                            structId = formulaList[x].formulaId;
                            break;
                        }
                    }
                    for(var y = 0; y < formulaList.length; y++){
                        if(structId == formulaList[y].parentId){            //查找list所有parentId
                            var nameTemp1 = "";
                            nameTemp1 = formulaList[i].formulaName + "." + formulaList[y].formulaName;
                            if(formulaList[i].formulaType == "int"){            //颜色标定数据类型
                                color = connectPoint;
                            }else if(formulaList[i].formulaType == "double"){
                                color = "blue";
                            }else if(formulaList[i].formulaType == "float"){
                                color = "white";
                            }else if(formulaList[i].formulaType == "long"){
                                color = "red";
                            }
                            inServicesArray.push(
                                { name: nameTemp1, color:color, unit:formulaList[i].formulaUnit,
                                    modelPortType:formulaList[i].formulaType,source:formulaList[i].formulaSource }
                            );
                        }
                    }
                }else{
                    inServicesArray.push(
                        { name: formulaList[i].formulaName, color:color, unit:formulaList[i].formulaUnit,
                            modelPortType:formulaList[i].formulaType,source:formulaList[i].formulaSource }
                    );
                }
            }
        }

        var item={"modVersion":"1.0","modContext":"1","categoryId":"1","modId":"1","modName":"测试模型"}
        myDiagram.startTransaction("add addModel");//刷新问题
        var addModel={
            key: nodeDataArray.length,
            category:"1",          //模型
            modelVersion:item.modVersion,
            modelContext:item.modContext,
            modelCategory:item.categoryId,
            id:item.modId,
            name: item.modName,
            inServices: inServicesArray,
            outServices: outServicesArray
        };
        myDiagram.model.addNodeData(addModel);
        myDiagram.commitTransaction("add addModel");

    }
    //if('contants' == id || 'if' == id){
    //    var cNode = document.getElementById(id).cloneNode(true);
    //    ev.target.appendChild(cNode);
    //}else{
    //    var oDiv = document.createElement('div');
    //    oDiv.innerHTML= document.getElementById(id).innerHTML;
    //    oDiv.className = 'showGrid';
    //    ev.target.appendChild(oDiv);
    //}

  	if(id == 'indexBtn'){
        var fid= self.frameElement.getAttribute('id');
        var url = ctx + '/platform/tplPage?frameid='+fid;
  		// var url = ctx + '/platform/tplPage';
  		//window.location.href = url;
  		top.openDialog('选择模板','80%','80%',url,function(){
  			//TODO
  		});
  	}
}

