package com.tf.base.util;

import com.alibaba.fastjson.JSONObject;

public interface ParamProcess {

    /**
     * 获取列的名字
     * @return
     */
    String[] getColumnsName();
    /**
     * 获取文件列的数量
     * @return
     */
      int getColumnsCount();
    /**
     * 根据列名获取列数据
     * @param columnName
     * @return
     */
       int[] getColumnsIntValue(String columnName);

    /**
     * 根据列名获取列数据
     * @param columnName
     * @return
     */
      long[] getColumnsLongValues(String columnName);

    /**
     * 根据列名获取列数据
     * @param columnName
     * @return
     */
      double[] getColumnsDoubleValues(String columnName);


    /**
     * 根据列名获取列数据
     * @param columnName
     * @return
     */
      float[] getColumnsFloatValues(String columnName);

    /**
     * 获取所有的数据格式，按照json
     * @return
     */
    JSONObject getDataJSONObject();

}
