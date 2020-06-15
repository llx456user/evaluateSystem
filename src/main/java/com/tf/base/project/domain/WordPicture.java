package com.tf.base.project.domain;

public class WordPicture {

    private  String height ;//高度
    private  String weight;//宽度
    private  String base64 ;//base64

    public  WordPicture(String height,String weight,String base64){
        this.height=height;
        this.weight=weight;
        this.base64=base64;
    }
    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
}
