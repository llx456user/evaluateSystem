package com.tf.base.project.service;

import com.tf.base.common.utils.Dom4jUtil;
import com.tf.base.project.domain.DataBean;
import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.util.*;

/**
 * @Description 想定分析
 * @Author 圈哥
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/10/8
 */
public class AssumptionAnalysis {

    private final String constant = "1";
    private String mapXmlPash;
    private String xmlValuePath;
    private List<DataBean> dataBeans;
    private Map<String, String> typeMap;
    private Map<String, String> valueMap;

    public AssumptionAnalysis(String mapXmlPash, String xmlValuePath) {
        this.mapXmlPash = mapXmlPash;
        this.xmlValuePath = xmlValuePath;
        typeMap = new HashMap<>();
        valueMap = new HashMap<>();
        analysisMapXml();
        dataBeans = analysisEntity();
    }

    public static void main(String[] args) {
        String path1 = "E:\\映射关系.xml";
        String path2 = "E:\\想定格式.xml";
        AssumptionAnalysis assumptionAnalysis = new AssumptionAnalysis(path1, path2);
        List<DataBean> list = assumptionAnalysis.getDataBeans();
        for (DataBean bean : list) {
            System.out.println(bean.toString());
            System.out.println(assumptionAnalysis.getValue("经度",bean));
            System.out.println(assumptionAnalysis.getValue("纬度",bean));
            System.out.println(assumptionAnalysis.getValue("点位",bean));
        }
    }

    /**
     * 分析xml，获取实体列表
     *
     * @return
     * @throws DocumentException
     */
    private List<DataBean> analysisEntity() {
        Dom4jUtil dom4jUtil = null;
        try {
            dom4jUtil = new Dom4jUtil(this.xmlValuePath);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        List<DataBean> dataList = new ArrayList<>();
        //获取基本信息
        List<Element> list = dom4jUtil.getDocument().selectNodes("/Scenario/Entities/Force/Entity");
        //枚举所有的子节点
        for (Element e : list) {
            DataBean dataBean = new DataBean();
            // 数据名称,由name+code 组成
            String dataName = "";
            //解析属性
            for (Iterator i = e.attributeIterator(); i.hasNext(); ) {
                Attribute attribute = (Attribute) i.next();
                String name = attribute.getQName().getName();
                if ("Name".equals(name) || "Code".equals(name)) {
                    dataName += attribute.getText();
                }
            }
            dataBean.setDataName(dataName);
            //枚举所有子节点
            for (Iterator it = e.elementIterator(); it.hasNext(); ) {
                Element element = (Element) it.next();
                String eName = element.getQName().getName();
                String text = element.getText();
                //解析Deploy节点
                if ("Deploy".equals(eName)) {
                    //解析属性
                    for (Iterator i = element.attributeIterator(); i.hasNext(); ) {
                        Attribute attribute = (Attribute) i.next();
                        String name = attribute.getQName().getName();
                        if ("Longitude".equals(name)) {
                            dataBean.setLongitude(attribute.getText());
                        } else if ("Latitude".equals(name)) {
                            dataBean.setLatitude(attribute.getText());
                        }
                    }
                }
            }
            dataList.add(dataBean);
        }
        return dataList;
    }

    /**
     * 根据变量名称获取变量值
     *
     * @param key
     * @param dataBean
     * @return
     */
    public String getValue(String key, DataBean dataBean) {
        if (!typeMap.containsKey(key)) {
            return "";
        }
        //常量
        if (constant.equals(typeMap.get(key))) {
            return valueMap.get(key);
        }
        String value = "";
        if ("Latitude".equals(valueMap.get(key))) {
            value = dataBean.getLatitude();
        } else if ("Longitude".equals(valueMap.get(key))) {
            value = dataBean.getLongitude();
        }
        return value;

    }

    /**
     * 获取databean
     *
     * @return
     */
    public List<DataBean> getDataBeans() {
        return this.dataBeans;
    }

    private void analysisMapXml() {
        try {
            Dom4jUtil dom4jUtil = new Dom4jUtil(this.mapXmlPash);
            List<Element> list = dom4jUtil.getDocument().selectNodes("/mapping/map");
            //枚举所有的子节点
            for (Element e : list) {
                //解析属性
                String key = "";
                for (Iterator i = e.attributeIterator(); i.hasNext(); ) {
                    Attribute attribute = (Attribute) i.next();
                    String name = attribute.getQName().getName();
                    if ("key".equals(name)) {
                        key = attribute.getText();
                    } else if ("type".equals(name)) {
                        typeMap.put(key, attribute.getText());
                    } else if ("value".equals(name)) {
                        valueMap.put(key, attribute.getText());
                    }
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

}


