package com.tf.base.go.type;

/**
 * 节点分类
 */
public enum Category {

    Error(-1),//错误类型
    Datasource(0),//数据源
    Model(1),       //模型
    Picture(2),     //图
    Constant(3),    //常数
    If(4),          //IF判断
    Grid(5),       //表格
    End(6),        //结束节点
    Out(7),//输出
    Child(8); //子节点

    private Category(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    /**
     * 获取分类
     *
     * @param value
     * @return
     */
    public static Category getCategory(int value) {
        if (value == Datasource.getValue()) {
            return Datasource;
        } else if (value == Model.getValue()) {
            return Model;
        } else if (value == Picture.getValue()) {
            return Picture;
        } else if (value == If.getValue()) {
            return If;
        } else if (value == Constant.getValue()) {
            return Constant;
        } else if (value == Grid.getValue()) {
            return Grid;
        }else if (value == End.getValue()) {
            return End;
        }else if (value == Out.getValue()) {
            return Out;
        }else if (value == Child.getValue()) {
            return Child;
        }
        return Error;
    }

    private int value;
}

