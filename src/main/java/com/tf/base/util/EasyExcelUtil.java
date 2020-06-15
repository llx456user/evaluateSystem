//package com.tf.base.util;
//
//import com.alibaba.excel.EasyExcel;
//import com.alibaba.excel.context.AnalysisContext;
//import com.alibaba.excel.event.AnalysisEventListener;
//
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Description
// * @Author 圈哥
// * @Version V1.0.0
// * @Since 1.0
// * @Date 2020/3/5
// */
//public class EasyExcelUtil {
//
//    private Map<String,List<String>> results = new LinkedHashMap<String,List<String>>();
//    private List<String> names = new ArrayList<String>();
//    public EasyExcelUtil(String filePath){
//        final boolean[] isFinish = {false};
//         EasyExcel.read(filePath,null, new AnalysisEventListener<LinkedHashMap<String,String>>(){
//            @Override
//            public void invoke(LinkedHashMap<String,String> data, AnalysisContext context) {
//                Object[] values = data.values().toArray();
//                for(int i=0;i<values.length;i++){
//                    results.get(names.get(i)).add(String.valueOf(values[i]));
//                }
//            }
//
//            @Override
//            public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
//                for(String head:headMap.values()){
//                    List<String> objs = new ArrayList<String>();
//                    results.put(head,objs);
//                    names.add(head);
//                }
//            }
//
//            @Override
//            public void doAfterAllAnalysed(AnalysisContext context) {
//                isFinish[0] = true ;
//            }
//        }).sheet().doRead();
//
//        while (!isFinish[0]){
////            System.out.println("未结束");
//        }
//
//    }
//   public  Map<String, List<String>> getColumnsList(){
//        return results;
//    }
//
//}
