
// define color
var green = "#008000";//计算成功为绿色
var red = "#FF0000";//运行失败红色
var blue="#0000FF" ;//配置了属性，可以运行，则为蓝色
var gray="#808080";//默认为灰色

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

    //返回一个svg
   return  myDiagram.makeImageData({
        size: new go.Size(400,400)
    });
}

function showModel() {
    var modelJson = myDiagram.model.toJson();
}

// When a Node is selected,
function nodeSelectionChanged(node) {
    if (node.isSelected) {

    } else {

    }
}

