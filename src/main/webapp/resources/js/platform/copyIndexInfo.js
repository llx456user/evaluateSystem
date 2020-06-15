// 显示列表

$(document).ready(function () {
    //初始化model
    initModel();
    // setInterval(saveIndex,300000);
    // init();
});
var green = "#169675";//计算成功为绿色
var red = "#ac0717";//运行失败红色

var myDiagram;
// var blueShapeBody = "#B1EAD9";
var blueShapeBody = "#99ccff";
// connectPoint = "#3CA685";
var connectPoint = "#3C60A1";
var connectStuct = "#8a5aa1";
var intColor = "#676664";
var longColor = "#9b99fe";
var floatColor = "#0299c8";
var doubleColor = "#ffb440";
var structColor = "#1BB88F";
var stringColor = "#755d9f";
var lineOne = "#15B68D";
var polygoncolor = "#359F74";
var tableHeadercolor = "#FFFFFF";
var tableHeaderfontcolor = "#343b42";
var tableBodycolor = "#FFFFFF";
// checkboxSelectedColor ="#24BFAA";
var checkboxSelectedColor = "#3099f5";
var defaultColorFromExcanvas = "#D2EDE8";
var fontColor = "#343b42";//形状字体
var nodeDataArray = [];//节点信息数组
var linkDataArray = [];//连线信息数组
function initModel() {
    var indexid = $("#id").val();
    console.log(indexid);
    if (indexid != '') {
        getIndexInfo(indexid)
    } else {
        init();
    }
}

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
            }
            // ,
            // {
            //     contextMenu: partSetNameContextMenu     //箭头右键
            // }
        );
    }

    function makeIfPort(name, spot, output, input) {
        return $(go.Shape, "Circle",
            {
                fill: "transparent",
                stroke: null,  // this is changed to "white" in the showPorts function
                desiredSize: new go.Size(10, 10),
                alignment: spot, alignmentFocus: spot,  // align the port on the main Shape
                portId: name,  // declare this object to be a "port"
                fromSpot: spot, toSpot: spot,  // declare where links may connect at this port
                fromLinkable: output, toLinkable: input,  // declare whether the user may draw links to/from here
                text: name,
                cursor: "pointer"  // show a different cursor to indicate potential link point
            }
        );
    }


    function portInfo(d) {  // Tooltip info for a port data object
        if (!d.loc) {     //data and picture
            var unit = '';
            var mean = '';
            if (d.unit) {
                unit = d.unit;
            }
            if (d.parmeterUnitEx) {
                mean = d.parmeterUnitEx;
            }
            var str = '';
            if (d.modelPortType) {
                str = "类型：" + d.modelPortType + "， "
                    + "单位：" + unit + "， "
                    + "含义：" + mean + "\n";
            } else {
                str = "单位：" + unit + "， "
                    + "含义：" + mean + "\n";
            }
        }
        return str;
    }

    function modelinfo(d) {  // Tooltip info for a model data object
        var modelContent = '';
        if (d.modelContent) {
            modelContent = d.modelContent;
        }
        return "模型描述：" + modelContent;
    }

    /*****************************右键事件开始********************************/
    /*****************************图谱右键开始********************************/
    var partPictureContextMenu =         //图形右键
        $(go.Adornment, "Vertical",
            makeButton("设置标题",
                function (e, obj) {
                    var contextmenu = obj.part;
                    var part = contextmenu.adornedPart;
                    var key = part.data.key;
                    var pType = part.data.type;
                    if (pType == 'bar') {//柱状图
                        openSettingPictureTitle(OkSetTitle, key);
                    } else if (pType == 'pie') {//饼图
                        openSettingTableTitle(OkSetTitle, key)
                    } else if (pType == 'line' || pType == 'Hline' || pType == 'scatter') {//折线图//曲线图//散点图
                        openSettingPictureTitle(OkSetTitle, key);
                    }else if (pType == 'hscatter') {//三维图
                        openSettingPictureThreeTitle(OkSetThreeTitle, key);
                    }
                }),
            makeButton("增加",
                function (e, obj) {
                    var contextmenu = obj.part;
                    var part = contextmenu.adornedPart;
                    var colname = "";
                    var pType = part.data.type;
                    var color = connectPoint;
                    myDiagram.startTransaction("add picture");
                    if (pType == 'bar') {//柱状图
                        colname = "input-y" + (part.data.inServices.length - 2);
                        var port = {//-- 刷新问题
                            name: colname,
                            color: color,
                            isY: true
                        };
                        myDiagram.model.insertArrayItem(part.data.inServices, -1, port);
                    } else if (pType == 'pie') {//饼图
                        // openSettingTableTitle(OkSetTitle,key),饼图不进行添加
                    } else if (pType == 'line' || pType == 'Hline' || pType == 'scatter') {//折线图//曲线图//散点图
                        var colnum = parseInt((part.data.inServices.length /2)) ;
                        colname = "input-x" + colnum;
                        var port = {//-- 刷新问题
                            name: colname,
                            color: color,
                            isX: true
                        };
                        myDiagram.model.insertArrayItem(part.data.inServices, -1, port);
                        colname = "input-y" + colnum;
                        var port = {//-- 刷新问题
                            name: colname,
                            color: color,
                            isY: true
                        };
                        myDiagram.model.insertArrayItem(part.data.inServices, -1, port);
                    }else if(pType='hscatter'){//3D图形
                        var colnum = part.data.inServices.length / 3+ 1;
                        colname = "input-x" + colnum;
                        var port = {//-- 刷新问题
                            name: colname,
                            color: color,
                            isX: true
                        };
                        myDiagram.model.insertArrayItem(part.data.inServices, -1, port);
                        colname = "input-y" + colnum;
                        var port = {//-- 刷新问题
                            name: colname,
                            color: color,
                            isY: true
                        };
                        myDiagram.model.insertArrayItem(part.data.inServices, -1, port);

                        colname = "input-z" + colnum;
                        var port = {//-- 刷新问题
                            name: colname,
                            color: color
                        };
                        myDiagram.model.insertArrayItem(part.data.inServices, -1, port);
                    }
                    myDiagram.commitTransaction("add picture");
                }),
            makeButton("删除",
                function (e, obj) {
                    e.diagram.commandHandler.deleteSelection();
                },
                function (o) {
                    return o.diagram.commandHandler.canDeleteSelection();
                })
        );


    /************************************************************************/

    var partGridContextMenu =         //grid条件右键信息
        $(go.Adornment, "Vertical", makeButton("设置标题",
            function (e, obj) {
                var contextmenu = obj.part;
                var part = contextmenu.adornedPart;
                var key = part.data.key;
                openSettingTableTitle(OkSetTitle, key)
            }), makeButton("add col",
            function (e, obj) {
                var contextmenu = obj.part;
                var part = contextmenu.adornedPart;
                var colname = "列" + (part.data.inServices.length + 1);
                var model = myDiagram.model;
                var data = obj.data;
                myDiagram.startTransaction("add sport");
                var color = connectPoint;
                var port = {//-- 刷新问题
                    name: colname,
                    color: color
                };
                myDiagram.model.insertArrayItem(part.data.inServices, -1, port);
                myDiagram.commitTransaction("add sport");
            }),
            makeButton("Delete",
                function (e, obj) {
                    e.diagram.commandHandler.deleteSelection();
                },
                function (o) {
                    return o.diagram.commandHandler.canDeleteSelection();
                })
        );
// To simplify this code we define a function for creating a context menu button:
//右键按钮事件
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

    /****************************设置名字和默认值右键菜单*************************************/
        //设置名字
    var partSetNameContextMenu = $(go.Adornment, "Vertical",
        makeButton("设置名字",
            function (e, obj) {
                var contextmenu = obj.part;
                var part = contextmenu.adornedPart;
                var key = part.data.key;
                openSettingName(OkSetName, key);
            })
        );

    //设置名字和默认值
    var partSetNameAndDefaultContextMenu = $(go.Adornment, "Vertical",
        makeButton("设置名字",
            function (e, obj) {
                var contextmenu = obj.part;
                var part = contextmenu.adornedPart;
                var key = part.data.key;
                openSettingNameAndDefault(OkSetName, key);
            })
    );

    /**
     * 设置名字默认值回调,
     * @param json
     * @param key
     * @constructor
     */
    function OkSetName(json, key, hasDefault) {
        var nodedata = myDiagram.model.nodeDataArray;

        for (var i = 0; i < nodedata.length; i++) {
            if (key == nodedata[i].key) {
                nodedata[i].paramName = json.paramName;
                if (hasDefault) {
                    nodedata[i].defaultValue = json.defaultValue;
                    nodedata[i].text = json.paramName + ":" + json.defaultValue;
                }
            }
        }
        myDiagram.rebuildParts();
    }

    /**
     * 设置标题回调,
     * @param json
     * @param key
     * @constructor
     */
    function OkSetTitle(json, key, isTable) {
        var nodedata = myDiagram.model.nodeDataArray;
        for (var i = 0; i < nodedata.length; i++) {
            if (key == nodedata[i].key) {
                nodedata[i].pictureTitle = json.pictureTitle;
                if (isTable) {
                    nodedata[i].xTitle = json.xTitle;
                    nodedata[i].yTitle = json.yTitle;
                }
            }
        }
        myDiagram.rebuildParts();
    }

    /**
     * 设置标题回调,
     * @param json
     * @param key
     * @constructor
     */
    function OkSetThreeTitle(json, key) {
        var nodedata = myDiagram.model.nodeDataArray;
        for (var i = 0; i < nodedata.length; i++) {
            if (key == nodedata[i].key) {
                nodedata[i].pictureTitle = json.pictureTitle;
                nodedata[i].xTitle = json.xTitle;
                nodedata[i].yTitle = json.yTitle;
                nodedata[i].zTitle = json.zTitle;
            }
        }
        myDiagram.rebuildParts();
    }

    /****************************IF条件右键菜单开始*************************************/
    var partIfContextMenu = $(go.Adornment, "Vertical",
        makeButton("删除",
            function (e, obj) {
                e.diagram.commandHandler.deleteSelection();
            },
            function (o) {
                return o.diagram.commandHandler.canDeleteSelection();
            }),
        makeButton("设置条件",
            function (e, obj) {
                var contextmenu = obj.part;
                var part = contextmenu.adornedPart;
                settingIf(part.data);
            })
    );

    function settingIf(data) {
        var key = data.key;
        openSetting(OkIf, key);
    }

    /**
     * 条件回调函数
     * @param json
     * @param key
     * @constructor
     */
    function OkIf(json, key) {
        var nodedata = myDiagram.model.nodeDataArray;
        for (var i = 0; i < nodedata.length; i++) {
            if (key == nodedata[i].key) {
                nodedata[i].settingNum = json.iptParam;
                nodedata[i].sign = json.symbol;
                nodedata[i].text = json.symbol + json.iptParam;
            }
        }
        myDiagram.rebuildParts();
    }

    /****************************IF条件右键菜单结束*************************************/

    /****************************模型右键菜单开始*************************************/
    var partModelContextMenu = $(go.Adornment, "Vertical",
        makeButton("调试",
            function (e, obj) {
                var contextmenu = obj.part;
                var part = contextmenu.adornedPart;
                // alert(part.data.key);
                debugIndexNode(part.data.key);
            }),
        makeButton("查看调试结果",
            function (e, obj) {
                var contextmenu = obj.part;
                var part = contextmenu.adornedPart;
                debugResult(part.data.key);
            }),
        makeButton("删除",
            function (e, obj) {
                e.diagram.commandHandler.deleteSelection();
            },
            function (o) {
                return o.diagram.commandHandler.canDeleteSelection();
            })
    );


    /****************************右键事件结束**********************************/
        //----------------------------------模型模板-------------------------------------//
    var lightText = 'whitesmoke';
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
                    $(go.TextBlock, new go.Binding("text", "text"),
                        {alignment: go.Spot.TopCenter, font: "14px Times New Roman", stroke: fontColor}
                    )
                )
            ), {
                contextMenu: partSetNameContextMenu     //数据源右键
            },
            $(go.Panel, "Vertical",
                {name: "RightPorts", alignment: new go.Spot(1, 0.63, -33, 7)},
                new go.Binding("itemArray", "outServices"),
                {itemTemplate: makeItemTemplate(false)}
            )
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
                    new go.Binding("fill", "color"),//设置填充色
                    new go.Binding("stroke", "isSelected", function (b) {
                        return b ? SelectedBrush : UnselectedBrush;
                    }).ofObject()),
                $(go.Panel, "Vertical",
                    {margin: 5, width: 65, height: 90},  //model主体方框
                    $(go.TextBlock, new go.Binding("text", "text"),
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
                contextMenu: partModelContextMenu
            },
            {
                toolTip:                   //模型上的浮动信息
                    $(go.Adornment, "Auto",
                        $(go.Shape, {fill: "#FFFFCC", height: 45}),
                        $(go.TextBlock, {
                                margin: 5, textAlign: "center",
                                alignment: go.Spot.Top,
                                font: "12px Times New Roman"
                            },  // the tooltip shows the result of calling modelinfo(data)
                            new go.Binding("text", "", modelinfo)))
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
                {itemTemplate: makeItemTemplatePicture(true)}
            ),
            {
                contextMenu: partPictureContextMenu
            }
        ));
    // myDiagram.nodeTemplateMap.add("2",        //Picture描画图表模型
    //     $(go.Node, "Spot", /*clickModel(),*/
    //         {
    //             selectionAdorned: false
    //             /*selectionChanged: nodeSelectionChanged*/
    //         },
    //         {locationSpot: go.Spot.Center, locationObjectName: "BODY"},
    //         new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
    //         $(go.Panel, "Auto", {name: "BODY"},
    //             $(go.Shape, "RoundedRectangle",  //model图形形状
    //                 {stroke: "gray", strokeWidth: 2, fill: blueShapeBody},
    //                 new go.Binding("stroke", "isSelected", function (b) {
    //                     return b ? SelectedBrush : UnselectedBrush;
    //                 }).ofObject()),
    //             $(go.Panel, "Vertical",
    //                 {margin: 5, width: 90, height: 100},  //model主体方框
    //                 $(go.TextBlock, new go.Binding("text", "text").makeTwoWay(),
    //                     {
    //                         alignment: go.Spot.Center, font: "14px Times New Roman",
    //                         stroke: fontColor, editable: true, isMultiline: false
    //                     }
    //                 )
    //             )
    //         ),
    //         $(go.Panel, "Vertical",
    //             {name: "LeftPorts", alignment: new go.Spot(0, 0.53, 35, 7)},
    //             new go.Binding("itemArray", "inServices"),
    //             {itemTemplate: makeItemTemplatePicture(true)}
    //         ),
    //         {
    //             contextMenu: partPictureContextMenu
    //         }
    //     ));

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
                        wrap: go.TextBlock.WrapFit
                        //editable: true
                    },
                    new go.Binding("text").makeTwoWay())
            ),
            {contextMenu: partSetNameAndDefaultContextMenu},       //默认值右键
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
            {contextMenu: partIfContextMenu},       //if右键
            makePort("T", go.Spot.Top, false, true),
            makePort("L", go.Spot.Left, true, true),
            makePort("R", go.Spot.Right, true, true),
            makePort("B", go.Spot.Bottom, true, false)
        ));

    myDiagram.nodeTemplateMap.add("5",        //表格
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
                {itemTemplate: makeItemTemplateGrid(true)}
            ),
            {
                contextMenu: partGridContextMenu
            }
        ));


    myDiagram.nodeTemplateMap.add("6",                 //结束节点
        $(go.Node, "Spot", nodeStyle(),
            $(go.Panel, "Auto",
                $(go.Shape, "Circle",
                    {
                        fill: "#CE0620", stroke: null,
                        minSize: new go.Size(60, 60)
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
            makePort("T", go.Spot.Top, false, true),
            makePort("L", go.Spot.Left, true, true),
            makePort("R", go.Spot.Right, true, true),
            makePort("B", go.Spot.Bottom, true, false)
        ));
    myDiagram.nodeTemplateMap.add("7",                 //输出节点
        $(go.Node, "Spot", nodeStyle(),
            $(go.Panel, "Auto",
                $(go.Shape, "Circle",
                    {
                        fill: "#25ce18", stroke: null,
                        minSize: new go.Size(60, 60)
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

    myDiagram.nodeTemplateMap.add("8",                 //输出节点
        $(go.Node, "Spot", nodeStyle(),
            $(go.Panel, "Auto",
                $(go.Shape, "Circle",
                    {
                        fill: "#1f7bce", stroke: null,
                        minSize: new go.Size(60, 60)
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
    //表格port
    function makeItemTemplateGrid(leftside) {       //port描画端口
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

                new go.Binding("portId", "name"),
                new go.Binding("fill", "color")
            ),
            $(go.TextBlock,
                new go.Binding("text", "name").makeTwoWay(),
                {
                    font: "12px Times New Roman",
                    stroke: lightText, margin: 2,
                    maxSize: new go.Size(100, 100),
                    wrap: go.TextBlock.WrapFit,
                    editable: true
                }
            )
        );
    }

    function makeItemTemplatePicture(leftside) {
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
                new go.Binding("portId", "name"),
                new go.Binding("fill", "color")
            ),
            $(go.TextBlock,
                new go.Binding("text", "name").makeTwoWay(),
                {
                    font: "12px Times New Roman",
                    stroke: lightText, margin: 2,
                    maxSize: new go.Size(100, 100),
                    wrap: go.TextBlock.WrapFit,
                    editable: true
                }
            )
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

                // {
                //     contextMenu: partSetNameContextMenu     //数据源右键
                // },
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
                    stroke: "white",
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
                {text: "子节点", category: "8"},
                {text: "输出", category: "7"},
                {text: "常数", category: "3"},
                {text: "if...", category: "4", sign: "", settingNum: ""},
                {text: "指标值", category: "6"}
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
        $(go.Shape, {isPanelMain: true, strokeWidth: 1.5, stroke: lineOne},
            new go.Binding("stroke", "isHighlighted", function (h) {
                return h ? "DarkTurquoise" : lineOne;
            }).ofObject()),

        $(go.Shape, {toArrow: "Standard", stroke: lineOne},
            new go.Binding("stroke", "isHighlighted", function (h) {
                return h ? "DarkTurquoise" : lineOne;
            }).ofObject()),
        $(go.Panel, "Auto",
            {visible: false, name: "LABEL", segmentIndex: 2, segmentFraction: 0.5},
            new go.Binding("visible", "visible").makeTwoWay(),
            $(go.Shape, "RoundedRectangle", {fill: "#F8F8F8", stroke: null}),
            $(go.TextBlock, "input,out,yes,no",  // the label
                {
                    textAlign: "center",
                    font: "10pt helvetica, arial, sans-serif",
                    stroke: "#333333",
                    editable: true
                },
                new go.Binding("text").makeTwoWay()
            )
        )


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
            tipMsg("此端口已有输入");
            // ModalUtils.alert("此端口已有输入", "modal-warning", "sm");
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
        if (e.subject.fromNode.data.category === "4") {//如果到达节点
            if (label !== null) {
                label.visible = true;
            }
        } else if (e.subject.toNode.data.category === "4") {
            if (label !== null) {
                label.text = "Input";
                label.visible = true;

            }
        }
        // if (label !== null) label.visible = (e.subject.fromNode.data.category === "4");
    }

}

function showModel() {
    var modelJson = myDiagram.model.toJson();
    // alert(modelJson);
}

function save() {
    var id = $("#id").val();
    var indexName = $("#indexName").val();
    var indexContent = $("#indexContent").val();
    var indexCategoryid = $("#indexCategoryid").val();
    var indexData = myDiagram.model.toJson();
    if (indexName == "") {
        tipMsg("指标名称不能为空");
        return;
    }
    if (indexData == "") {
        tipMsg("图谱内容不能为空");
        return;
    }

    var param = {
        id: id,
        indexCategoryid: indexCategoryid,
        indexContent: indexContent,
        indexName: indexName,
        indexData: indexData
    }

    var i = openProgressExt("保存中...");
    //return;
    $.ajax({
        url: ctx + '/platform/copyIndexSave',
        data: param,
        success: function (data) {
            closeProgressExt(i);
            //console.log(data);
            if (data.status == 1) {
                tipMsg("保存成功");
                $("#id").val(data.msg);
            } else {
                tipMsg(data.msg);
            }
        }
    });
}
function saveIndex() {
    var id = $("#id").val();
    var indexName = $("#indexName").val();
    var indexContent = $("#indexContent").val();
    var indexCategoryid = $("#indexCategoryid").val();
    var indexData = myDiagram.model.toJson();
    if (indexName == "") {
        tipMsg("自动保存失败，指标名称不能为空");
        return;
    }
    if (indexData == "") {
        tipMsg("自动保存失败，图谱内容不能为空");
        return;
    }
    var param = {
        id: id,
        indexCategoryid: indexCategoryid,
        indexContent: indexContent,
        indexName: indexName,
        indexData: indexData
    }

    $.ajax({
        url: ctx + '/platform/autoindexSave',
        data: param,
        success: function (data) {

            var id=$("#id").val();
            if (data.status == 1) {
                if (id=="") {
                    if (data.status == 1) {
                        var id = $("#id").val(data.msg);
                    }
                }
            }else {
                tipMsg(data.msg);
            }
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
    if (ev.target.type == 'file' || ev.target.type == 'sql') {
        $("#" + ev.target.id).attr("filename")
        ev.dataTransfer.setData("filename", $("#" + ev.target.id).attr("filename"));
        ev.dataTransfer.setData("fileid", $("#" + ev.target.id).attr("fileid"));
    } else if (ev.target.type == 'selectModel') {
        ev.dataTransfer.setData("modelid", $("#" + ev.target.id).attr("modelid"));
    }

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
    var id = ev.dataTransfer.getData("id");
    if (id == 'gridBtn') {//图形结束，表格
        myDiagram.startTransaction("add grid");// 刷新问题
        var gridModel = {
            key: nodeDataArray.length,
            category: "5",      //表格
            text: "表格",
            inServices: [{name: "列1", color: connectPoint}]
        }
        myDiagram.model.addNodeData(gridModel);
        myDiagram.commitTransaction("add grid");
    } else if (type == 'file') {
        selectFile(ev.dataTransfer.getData("fileid"), type, ev.dataTransfer.getData("filename"), nodeDataArray);
    } else if (type == "sql") {
        selectFile(ev.dataTransfer.getData("fileid"), type, ev.dataTransfer.getData("filename"), nodeDataArray);
    } else if (type == 'bar') {//图形
        myDiagram.startTransaction("add Bar"); // 刷新问题
        var barModel = {
            key: nodeDataArray.length,
            category: "2",      //Picture
            type: type,         //图表类型
            text: "柱状图",
            inServices: [
                {name: "struct", color: connectStuct, isS: true},
                {name: "label", color: connectPoint, isL: true},
                {name: "input-x", color: connectPoint, isX: true},
                {name: "input-y", color: connectPoint, isY: true}
            ]
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
            inServices: [{name: "label", color: connectPoint, isX: true},
                {name: "value", color: connectPoint, isX: false},
                {name: "struct", color: connectStuct, isS: true}]
        }
        myDiagram.model.addNodeData(pieModel);
        myDiagram.commitTransaction("add pie");
    } else if (type == 'line') {
        myDiagram.startTransaction("add line");//
        var lineModel = {
            key: nodeDataArray.length,
            category: "2",      //Picture
            type: type,         //图表类型
            text: "折线图",
            inServices: [
                {name: "struct", color: connectStuct, isS: true},
                {name: "label", color: connectPoint, isL: true},
                {name: "input-x", color: connectPoint, isX: true},
                {name: "input-y", color: connectPoint, isY: true}]
        }
        myDiagram.model.addNodeData(lineModel);
        myDiagram.commitTransaction("add line");
    } else if (type == 'Hline') {
        myDiagram.startTransaction("add Hline");//
        var lineModel = {
            key: nodeDataArray.length,
            category: "2",      //Picture
            type: type,         //图表类型
            text: "曲线图",
            inServices: [
                {name: "struct", color: connectStuct, isS: true},
                {name: "label", color: connectPoint, isL: true},
                {name: "input-x", color: connectPoint, isX: true},
                {name: "input-y", color: connectPoint, isY: true}]
        }
        myDiagram.model.addNodeData(lineModel);
        myDiagram.commitTransaction("add Hline");
    } else if (type == 'scatter') {
        myDiagram.startTransaction("add scatter");//
        var scatterModel = {
            key: nodeDataArray.length,
            category: "2",      //Picture
            type: type,         //图表类型
            text: "散点图",
            inServices: [
                {name: "struct", color: connectStuct, isS: true},
                {name: "input-x", color: connectPoint,isX: true},
                {name: "input-y", color: connectPoint,isY: true}]
        };
        myDiagram.model.addNodeData(scatterModel);
        myDiagram.commitTransaction("add scatter");
    }
    // else if (type == 'map') {
    //
    //     myDiagram.startTransaction("add map");// 刷新问题
    //     var mapModel = {
    //         key: nodeDataArray.length,
    //         category: "2",      //Picture
    //         type: type,         //图表类型
    //         text: "地图",
    //         inServices: [{name: "input-x", color: connectPoint},
    //             {name: "input-y", color: connectPoint}]
    //     }
    //     myDiagram.model.addNodeData(mapModel);
    //     myDiagram.commitTransaction("add map");
    // }
    else if (type == 'hscatter') {
        myDiagram.startTransaction("add hscatter");//
        var hscatterModel = {
            key: nodeDataArray.length,
            category: "2",      //Picture
            type: type,         //图表类型
            text: "三维图",
            inServices: [
                {name: "struct", color: connectStuct, isS: true},
                {name: "input-x", color: connectPoint,isX: true},
                {name: "input-y", color: connectPoint,isY: true},
                {name: "input-z", color: connectPoint}]
        }
        myDiagram.model.addNodeData(hscatterModel);
        myDiagram.commitTransaction("add hscatter");
    }
    else if (type == 'selectModel') {
        getModelParams(ev.dataTransfer.getData("modelid"));
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

    if (id == 'indexBtn') {


        var fid= self.frameElement.getAttribute('id');
        var url = ctx + '/platform/tplPage?frameid='+fid;
        //window.location.href = url;
        top.openDialog('选择模板', '80%', '80%', url, function () {
            //TODO
        });
    }
}

function debugResult(key) {
    var id = $("#id").val();
    if (id == '') {
        tipMsg("指标没有保存，不能查看调试结果");
        return;
    }
    // alert(id);
    testResult(key, id);
}


//数据筛选设定 弹框 回调时获取录入数据
// 样例如下：
//openSetting(function(data){
//console.log(data);
//});
function openSetting(OKcallback, key) {
    var el = $("#settingPage");
    el.modal({'show': true, 'backdrop': 'static', 'position': '50'});
    el.find(".OK").off('click').on('click', function () {
        var symbol = el.find("select[name=symbol]").val();
        var iptParam = el.find("input[name=iptParam]").val();
        if (symbol == '') {
            tipMsg('请选择判断符号');
            return;
        }
        if (iptParam == '') {
            tipMsg('请输入参数');
            return;
        }
        var json = {symbol: symbol, iptParam: iptParam};
        OKcallback(json, key);
        el.modal('hide');//确认关闭
    });
}

function openSettingName(callback, key) {
    var el = $("#settingName");
    el.modal({'show': true, 'backdrop': 'static', 'position': '50'});
    var nodedata = myDiagram.model.nodeDataArray;
    for (var i = 0; i < nodedata.length; i++) {
        if (key == nodedata[i].key) {
            //如果包含默认值
            if (nodedata[i].paramName != undefined && nodedata[i].paramName != null && nodedata[i].paramName != '') {
                el.find("input[name=paramName]").val(nodedata[i].paramName);
            }
        }
    }
    el.find(".OK").off('click').on('click', function () {
        var paramName = el.find("input[name=paramName]").val();
        if (paramName == '') {
            tipMsg('请输入名称');
            return;
        }
        var json = {paramName: paramName};
        callback(json, key, false);
        el.modal('hide');//确认关闭
    });
}


//设置饼图和table标题
function openSettingTableTitle(callback, key) {
    var el = $("#settingTableTitle");
    el.modal({'show': true, 'backdrop': 'static', 'position': '50'});
    var nodedata = myDiagram.model.nodeDataArray;
    el.find("input[name=pictureTitle]").val('');
    for (var i = 0; i < nodedata.length; i++) {
        if (key == nodedata[i].key) {
            //如果包含标题
            if (nodedata[i].pictureTitle != undefined && nodedata[i].pictureTitle != null && nodedata[i].pictureTitle != '') {
                el.find("input[name=pictureTitle]").val(nodedata[i].pictureTitle);
            }
        }
    }
    el.find(".OK").off('click').on('click', function () {
        var pictureTitle = el.find("input[name=pictureTitle]").val();
        if (pictureTitle == '') {
            tipMsg('请输入标题');
            return;
        }
        var json = {pictureTitle: pictureTitle};
        callback(json, key, false);
        el.modal('hide');//确认关闭
    });
}

//设置图形标题
function openSettingPictureTitle(callback, key) {
    var el = $("#settingPictureTitle");
    el.modal({'show': true, 'backdrop': 'static', 'position': '50'});
    var nodedata = myDiagram.model.nodeDataArray;
    el.find("input[name=pictureTitle]").val('');
    el.find("input[name=xTitle]").val('');
    el.find("input[name=yTitle]").val('');
    for (var i = 0; i < nodedata.length; i++) {
        if (key == nodedata[i].key) {
            //如果包含标题
            if (nodedata[i].pictureTitle != undefined && nodedata[i].pictureTitle != null && nodedata[i].pictureTitle != '') {
                el.find("input[name=pictureTitle]").val(nodedata[i].pictureTitle);
            }
            if (nodedata[i].xTitle != undefined && nodedata[i].xTitle != null && nodedata[i].xTitle != '') {
                el.find("input[name=xTitle]").val(nodedata[i].xTitle);
            }
            if (nodedata[i].yTitle != undefined && nodedata[i].yTitle != null && nodedata[i].yTitle != '') {
                el.find("input[name=yTitle]").val(nodedata[i].yTitle);
            }
        }
    }
    el.find(".OK").off('click').on('click', function () {
        var pictureTitle = el.find("input[name=pictureTitle]").val();
        if (pictureTitle == '') {
            tipMsg('请输入标题');
            return;
        }
        var xTitle = el.find("input[name=xTitle]").val();
        if (xTitle == '') {
            tipMsg('请输入x标题');
            return;
        }
        var yTitle = el.find("input[name=yTitle]").val();
        if (yTitle == '') {
            tipMsg('请输入y标题');
            return;
        }
        var json = {pictureTitle: pictureTitle, xTitle: xTitle, yTitle: yTitle};
        callback(json, key, true);
        el.modal('hide');//确认关闭
    });
}


//设置三维图形标题
function openSettingPictureThreeTitle(callback, key) {
    var el = $("#settingPictureThreeTitle");
    el.modal({'show': true, 'backdrop': 'static', 'position': '50'});
    var nodedata = myDiagram.model.nodeDataArray;
    el.find("input[name=pictureTitle]").val('');
    el.find("input[name=xTitle]").val('');
    el.find("input[name=yTitle]").val('');
    el.find("input[name=zTitle]").val('');
    for (var i = 0; i < nodedata.length; i++) {
        if (key == nodedata[i].key) {
            //如果包含标题
            if (nodedata[i].pictureTitle != undefined && nodedata[i].pictureTitle != null && nodedata[i].pictureTitle != '') {
                el.find("input[name=pictureTitle]").val(nodedata[i].pictureTitle);
            }
            if (nodedata[i].xTitle != undefined && nodedata[i].xTitle != null && nodedata[i].xTitle != '') {
                el.find("input[name=xTitle]").val(nodedata[i].xTitle);
            }
            if (nodedata[i].yTitle != undefined && nodedata[i].yTitle != null && nodedata[i].yTitle != '') {
                el.find("input[name=yTitle]").val(nodedata[i].yTitle);
            }
            if (nodedata[i].zTitle != undefined && nodedata[i].zTitle != null && nodedata[i].zTitle != '') {
                el.find("input[name=zTitle]").val(nodedata[i].zTitle);
            }
        }
    }
    el.find(".OK").off('click').on('click', function () {
        var pictureTitle = el.find("input[name=pictureTitle]").val();
        if (pictureTitle == '') {
            tipMsg('请输入标题');
            return;
        }
        var xTitle = el.find("input[name=xTitle]").val();
        if (xTitle == '') {
            tipMsg('请输入x标题');
            return;
        }
        var yTitle = el.find("input[name=yTitle]").val();
        if (yTitle == '') {
            tipMsg('请输入y标题');
            return;
        }
        var zTitle = el.find("input[name=zTitle]").val();
        if (zTitle == '') {
            tipMsg('请输入z标题');
            return;
        }
        var json = {pictureTitle: pictureTitle, xTitle: xTitle, yTitle: yTitle, zTitle: zTitle};
        callback(json, key, true);
        el.modal('hide');//确认关闭
    });
}


function openSettingNameAndDefault(callback, key) {
    var el = $("#settingNameAndDault");
    el.modal({'show': true, 'backdrop': 'static', 'position': '50'});
    el.find("input[name=paramName]").val('');
    el.find("input[name=defaultValue]").val('');
    var nodedata = myDiagram.model.nodeDataArray;
    for (var i = 0; i < nodedata.length; i++) {
        if (key == nodedata[i].key) {
            //如果包含默认值
            if (nodedata[i].paramName != undefined && nodedata[i].paramName != null && nodedata[i].paramName != '') {
                el.find("input[name=paramName]").val(nodedata[i].paramName);
            }
            if (nodedata[i].defaultValue != undefined && nodedata[i].defaultValue != null && nodedata[i].defaultValue != '') {
                el.find("input[name=defaultValue]").val(nodedata[i].defaultValue);
            }
        }
    }
    el.find(".OK").off('click').on('click', function () {
        var paramName = el.find("input[name=paramName]").val();
        var defaultValue = el.find("input[name=defaultValue]").val();
        if (paramName == '') {
            tipMsg('请输入名称');
            return;
        }
        if (defaultValue == '') {
            tipMsg('请输入默认值');
            return;
        }
        var json = {paramName: paramName, defaultValue: defaultValue};
        callback(json, key, true);
        el.modal('hide');//确认关闭
    });
}

/****************************获取数据接口**********************************/

//获取
function getIndexInfo(id) {
    var param = {
        id: id
    };
    myajax(ctx + '/platform/getIndexInfo', param, function (data) {
        var result = eval(data);
        console.log(result);
        var initModelJson = jQuery.parseJSON(result.indexData);
        console.log(initModelJson);
        nodeDataArray = initModelJson.nodeDataArray;//节点信息数组
        linkDataArray = initModelJson.linkDataArray;//连线信息数组
        init();
    });
}

//文件接口
function selectFile(fileId, fileType, fileName, nodeDataArray) {
    var param = {
        fileId: fileId,
        fileType: fileType
    };

    myajax(ctx + '/platform/selectFileColumns', param, function (data) {
        var result = eval(data);
        var outServicesArray = [];
        for (var i = 0; i < result.length; i++) {
            var color = connectPoint;
            if (fileType == 'file' && i == 0) {
                color = lineOne;
            }

            outServicesArray.push(
                {name: data[i].columnName, color: color}
            );
        }
        myDiagram.startTransaction("add fileModel");
        var fileModelTemp = {
            key: nodeDataArray.length,
            category: "0",      //Data
            type: fileType,  //文件类型
            id: fileId,
            text: fileName,
            outServices: outServicesArray
        }
        myDiagram.model.addNodeData(fileModelTemp);
        myDiagram.commitTransaction("add fileModel");
    });
}

/**************************************获取入参和出参列表***************************************/

function getModelParams(modelId) {
    var param = {
        modelId: modelId
    };

    myajax(ctx + '/platform/getModelParams', param, function (data) {
        var result = eval(data);
        var formulaList = result.formulaList;
        var item = result.modelinfo;
        var inServicesArray = [];
        var outServicesArray = [];
        for (var i = 0; i < formulaList.length; i++) {
            var color = connectPoint;
            var type = "port";
            if (formulaList[i].parmeterType == "int" || formulaList[i].parmeterType == "int*") {            //颜色标定数据类型
                color = intColor;
            } else if (formulaList[i].parmeterType == "double" || formulaList[i].parmeterType == "double*") {
                color = doubleColor;
            } else if (formulaList[i].parmeterType == "float" || formulaList[i].parmeterType == "float*") {
                color = floatColor;
            } else if (formulaList[i].parmeterType == "long" || formulaList[i].parmeterType == "long*") {
                color = longColor;
            }else if(formulaList[i].parmeterType == "string" || formulaList[i].parmeterType == "string*"){
                color = stringColor;
            } else {
                // type = "struct";
                color = structColor
            }
            if(formulaList[i].inoutType == 1){
                outServicesArray.push(
                    {
                        name: formulaList[i].parmeterName,
                        color: color,
                        unit: formulaList[i].parmeterUnit,
                        modelPortType: formulaList[i].parmeterType,
                        parmeterUnitEx: formulaList[i].parmeterUnitEx,
                        isArray: formulaList[i].isArray
                    }
                );
            }else if(formulaList[i].inoutType == 0){
                inServicesArray.push(
                    {
                        name: formulaList[i].parmeterName, color: color, unit: formulaList[i].parmeterUnit,
                        modelPortType: formulaList[i].parmeterType, parmeterUnitEx: formulaList[i].parmeterUnitEx,
                        isArray: formulaList[i].isArray
                    }
                );
            }


            // var structId = 0;
            // if (formulaList[i].inoutType == 1) {              //输出
            //     // if (type == "struct") {
            //     //     for (var j = 0; j < formulaList.length; j++) {
            //     //         if (formulaList[i].parmeterType == formulaList[j].parmeterName) {
            //     //             structId = formulaList[j].id;
            //     //             break;
            //     //         }
            //     //     }
            //     //     for (var k = 0; k < formulaList.length; k++) {
            //     //         if (structId == formulaList[k].parentId) {            //查找list所有parentId
            //     //             var nameTemp = "";
            //     //             nameTemp = formulaList[i].parmeterName + "." + formulaList[k].parmeterName;
            //     //             if (formulaList[i].parmeterType == "int") {            //颜色标定数据类型
            //     //                 color = connectPoint;
            //     //             } else if (formulaList[i].parmeterType == "double") {
            //     //                 color = "blue";
            //     //             } else if (formulaList[i].parmeterType == "float") {
            //     //                 color = "white";
            //     //             } else if (formulaList[i].parmeterType == "long") {
            //     //                 color = "red";
            //     //             }
            //     //             outServicesArray.push(
            //     //                 {
            //     //                     name: nameTemp,
            //     //                     color: color,
            //     //                     unit: formulaList[i].parmeterUnit,
            //     //                     modelPortType: formulaList[i].parmeterType,
            //     //                     parmeterUnitEx: formulaList[i].parmeterUnitEx,
            //     //                     isArray: formulaList[i].isArray
            //     //                 }
            //     //             );
            //     //         }
            //     //     }
            //     // } else {
            //     //     outServicesArray.push(
            //     //         {
            //     //             name: formulaList[i].parmeterName, color: color,
            //     //             unit: formulaList[i].parmeterUnit,
            //     //             modelPortType: formulaList[i].parmeterType, parmeterUnitEx: formulaList[i].parmeterUnitEx,
            //     //             isArray: formulaList[i].isArray
            //     //         }
            //     //     );
            //     // }
            // } else if (formulaList[i].inoutType == 0) {        //输入
            //     // if (type == "struct") {
            //     //     for (var x = 0; x < formulaList.length; x++) {
            //     //         if (formulaList[i].parmeterType == formulaList[x].parmeterName) {
            //     //             structId = formulaList[x].id;
            //     //             break;
            //     //         }
            //     //     }
            //     //     for (var y = 0; y < formulaList.length; y++) {
            //     //         if (structId == formulaList[y].parentId) {            //查找list所有parentId
            //     //             var nameTemp1 = "";
            //     //             nameTemp1 = formulaList[i].parmeterName + "." + formulaList[y].parmeterName;
            //     //             if (formulaList[i].parmeterType == "int") {            //颜色标定数据类型
            //     //                 color = connectPoint;
            //     //             } else if (formulaList[i].parmeterType == "double") {
            //     //                 color = "blue";
            //     //             } else if (formulaList[i].parmeterType == "float") {
            //     //                 color = "white";
            //     //             } else if (formulaList[i].parmeterType == "long") {
            //     //                 color = "red";
            //     //             }
            //     //             inServicesArray.push(
            //     //                 {
            //     //                     name: nameTemp1,
            //     //                     color: color,
            //     //                     unit: formulaList[i].parmeterUnit,
            //     //                     modelPortType: formulaList[i].parmeterType,
            //     //                     parmeterUnitEx: formulaList[i].parmeterUnitEx,
            //     //                     isArray: formulaList[i].isArray
            //     //                 }
            //     //             );
            //     //         }
            //     //     }
            //     // } else {
            //     //     inServicesArray.push(
            //     //         {
            //     //             name: formulaList[i].parmeterName, color: color, unit: formulaList[i].parmeterUnit,
            //     //             modelPortType: formulaList[i].parmeterType, parmeterUnitEx: formulaList[i].parmeterUnitEx,
            //     //             isArray: formulaList[i].isArray
            //     //         }
            //     //     );
            //     // }
            // }
        }
        myDiagram.startTransaction("add addModel");//刷新问题
        var addModel = {
            key: nodeDataArray.length,
            category: "1",          //模型
            modelVersion: item.modelVersion,
            modelContent: item.modelContent,
            modelCategory: item.modelCategoryid,
            id: item.id,
            text: item.modelName,
            inServices: inServicesArray,
            outServices: outServicesArray
        };
        myDiagram.model.addNodeData(addModel);
        myDiagram.commitTransaction("add addModel");
    });

}

function showTplPage() {

    var fid= self.frameElement.getAttribute('id');
    var url = ctx + '/platform/tplPage?frameid='+fid;
    // var url = ctx + '/platform/tplPage';
    //window.location.href = url;
    top.openDialog('选择模板', '80%', '80%', url, function () {
        //TODO
    });
}

function otherSave() {
    var id = $("#id").val();
    var indexName = $("#indexName").val();
    var indexData = myDiagram.model.toJson();

    if (indexName == "") {
        tipMsg('指标名称为空');
        return;
    }
    if (indexData == "") {
        tipMsg("图谱内容不能为空");
        return;
    }

    var el = $("#otherSavePage");
    el.modal({'show': true, 'backdrop': 'static', 'position': '50'});
    el.find(".OK").off('click').on('click', function () {
        var tplName = el.find("input[name=tplName]").val();
        if (tplName == '') {
            tipMsg('请输入模板名称');
            return;
        }
        var json = {
            tplName: tplName,
            id: id,
            indexName: indexName,
            indexData: indexData

        };
        //TODO
        myajax(ctx + "/platform/otherSaveTpl", json,
            function (data) {
                if (data.status == 1) {
                    el.modal('hide');//确认关闭
                    tipMsg(data.msg);
                } else {
                    tipMsg(data.msg);
                }

            });

    });
}


function testResult(key, indexId) {
    var url = ctx + '/platform/indexTest?nodeKey=' + key + '&indexId=' + indexId;
    top.openDialog('调试结果', '1000px', '600px', url, function () {
        //TODO
    });
}

function debugIndexNode(nodekey) {

    var id = $("#id").val();
    var indexName = $("#indexName").val();
    var indexContent = $("#indexContent").val();
    var indexCategoryid = $("#indexCategoryid").val();
    var indexData = myDiagram.model.toJson();
    if (indexName == "") {
        tipMsg("请先保存");
        return;
    }
    if (indexData == "") {
        tipMsg("图谱内容不能为空");
        return;
    }

    var param = {
        id: id,
        indexCategoryid: indexCategoryid,
        indexContent: indexContent,
        indexName: indexName,
        indexData: indexData,
        nodekey: nodekey
    }

    var i = openProgressExt("调试中...");
    $.ajax({
        url: ctx + '/platform/indexNodeDebug',
        data: param,
        success: function (data) {
            closeProgressExt(i);
            //console.log(data);
            if (data.status == 1) {
                //调试成功
                tipMsg(data.msg);
                testCallback(1, nodekey);
            } else {
                tipMsg(data.msg);
                testCallback(0, nodekey);
            }
        }
    });
}

/**
 * 调试回调
 * @param issuccess
 * @constructor
 */
function testCallback(issuccess, key) {
    // myDiagram.startTransaction("add test");
    var nodedata = myDiagram.model.nodeDataArray;
    for (var i = 0; i < nodedata.length; i++) {
        if (key == nodedata[i].key) {
            console.log(nodedata[i].toString());
            if (issuccess == 1) {//调试成功
                nodedata[i].color = green;
            } else {
                nodedata[i].color = red;
            }
        }
    }
    // myDiagram.model.nodeDataArray=nodedata;
    // myDiagram.commitTransaction("add test");
    myDiagram.rebuildParts();
}
