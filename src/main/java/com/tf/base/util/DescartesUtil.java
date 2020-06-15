package com.tf.base.util;

import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author 圈哥
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/8/30
 */
public class DescartesUtil {

    public static void main(String[] args) {

        List<List<Integer>> result = DescartesUtil.getDescartesResult(new int[]{3, 2, 4});
        for (List<Integer> l : result) {
            System.out.println(l);
        }
    }


    /**
     * Discription: 笛卡尔乘积算法
     * 把一个List{[1,2],[A,B],[a,b]} 转化成
     * List{[1,A,a],[1,A,b],[1,B,a],[1,B,b],[2,A,a],[2,A,b],[2,B,a],[2,B,b]} 数组输出
     *
     * @param dimensionValue 原List
     * @param result         通过乘积转化后的数组
     * @param layer          中间参数
     * @param currentList    中间参数
     */
    private static void descartes(List<List<Integer>> dimensionValue, List<List<Integer>> result, int layer, List<Integer> currentList) {
        if (layer < dimensionValue.size() - 1) {
            if (dimensionValue.get(layer).size() == 0) {
                descartes(dimensionValue, result, layer + 1, currentList);
            } else {
                for (int i = 0; i < dimensionValue.get(layer).size(); i++) {
                    List<Integer> list = new ArrayList<Integer>(currentList);
                    list.add(dimensionValue.get(layer).get(i));
                    descartes(dimensionValue, result, layer + 1, list);
                }
            }
        } else if (layer == dimensionValue.size() - 1) {
            if (dimensionValue.get(layer).size() == 0) {
                result.add(currentList);
            } else {
                for (int i = 0; i < dimensionValue.get(layer).size(); i++) {
                    List<Integer> list = new ArrayList<Integer>(currentList);
                    list.add(dimensionValue.get(layer).get(i));
                    result.add(list);
                }
            }
        }
    }

    public static void getDescartesStringResult(List<List<String>> dimensionValue, List<List<String>> result, int layer, List<String> currentList) {
        if (layer < dimensionValue.size() - 1) {
            if (dimensionValue.get(layer).size() == 0) {
                getDescartesStringResult(dimensionValue, result, layer + 1, currentList);
            } else {
                for (int i = 0; i < dimensionValue.get(layer).size(); i++) {
                    List<String> list = new ArrayList<String>(currentList);
                    list.add(dimensionValue.get(layer).get(i));
                    getDescartesStringResult(dimensionValue, result, layer + 1, list);
                }
            }
        } else if (layer == dimensionValue.size() - 1) {
            if (dimensionValue.get(layer).size() == 0) {
                result.add(currentList);
            } else {
                for (int i = 0; i < dimensionValue.get(layer).size(); i++) {
                    List<String> list = new ArrayList<String>(currentList);
                    list.add(dimensionValue.get(layer).get(i));
                    result.add(list);
                }
            }
        }
    }


    public static List<List<Integer>> getDescartesResult(int[] groupCount) {
        // 返回集合
        List<List<Integer>> result = new ArrayList<>();
        List<List<Integer>> dimensionValue = new ArrayList<>();
        for (int group : groupCount) {
            dimensionValue.add(getGroupIndexArray(group));
        }
        descartes(dimensionValue, result, 0, new ArrayList<Integer>());
        return result;
    }

    /**
     * 获取下标数组
     *
     * @param count
     * @return
     */
    private static List<Integer> getGroupIndexArray(int count) {
        List<Integer> groupArray = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            groupArray.add(i);
        }
        return groupArray;
    }


}
