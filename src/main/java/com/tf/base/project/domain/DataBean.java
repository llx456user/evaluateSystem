package com.tf.base.project.domain;

/**
 * @Description
 * @Author 圈哥
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/10/8
 */
public class DataBean {

    /**
     * 数据名称
     */
    private  String dataName ;
    //经度
    /**
     * 经度
     */
    private  String  longitude ;

    /**
     * 维度
     */
    private  String  latitude ;


    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return this.getDataName()+"_"+this.getLongitude()+"_"+this.getLatitude();
    }
}
