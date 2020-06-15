package com.tf.base.go.type;

/**
 * 只有Category类型为Datasource和Picture的节点才有类型
 */
public enum NodeType {
    Error("error"),//错误类型
    Sql("sql"),        //数据源-sql类型
    File("file"),       //数据源-文件
    Pie("pie"),     //图-pie 饼图
    Bar("bar"),    //图-bar 柱状图
    Line("line"),          //图-line 折线图
    Hline("Hline"),        //平滑折现图
    Scatter("scatter"),          //图-scatter 散点图
    Map("map"),          //图-map 地图
    Hscatter("hscatter");        //图-hscatter 三维图
    private NodeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    /**
     * 获取分类
     * @param value
     * @return
     */
    public static NodeType getType(String value){

        if(null == value){
            return  Error;
        }else if(value.equals(Sql.getValue())){
            return   Sql ;
        }else if(value.equals(File.getValue())){
            return  File ;
        }else if(value.equals(Pie.getValue())){
            return  Pie ;
        }else if(value.equals(Bar.getValue())){
            return  Bar ;
        }else if(value.equals(Line.getValue())){
            return  Line ;
        }else if(value.equals(Scatter.getValue())){
            return  Scatter ;
        }else if(value.equals(Map.getValue())){
            return  Map ;
        }else if(value.equals(Hscatter.getValue())){
            return  Hscatter ;
        }else if(value.equals(Hline.getValue())){
            return  Hline ;
        }
        return  Error;
    }

    private String value;
}
