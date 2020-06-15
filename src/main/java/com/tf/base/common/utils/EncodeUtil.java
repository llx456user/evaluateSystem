package com.tf.base.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class EncodeUtil {

    public static void main(String[] args) {
//        XmlAnalysis xmlAnalysis = XmlAnalysisImpl.getInstance(XmlAnalysisImpl.xmlParameter);
//        xmlAnalysis.build("E:\\项目管理\\二院项目\\项目三\\03开发\\模型描述文件模板20181212\\评估模板新\\装备模型描述文件模板新.xml");
//        Map map = xmlAnalysis.getMap();
//        System.out.printf("解析完成");
        try {
            String code = EncodeUtil.codeString("E:\\项目管理\\二院项目\\项目三\\03开发\\模型描述文件模板20181212\\作战模型描述文件模板.xml");
            System.out.println(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        ParameterXmlAnalysis parameterXmlAnalysis = new ParameterXmlAnalysis("E:\\项目管理\\二院项目\\项目三\\03开发\\参数描述文件\\参数描述文件模板.xml");
//        ParameterInfo inf = parameterXmlAnalysis.getParameterInfo();
//        List<JSONObject> vl = parameterXmlAnalysis.getParameterView();
//        ParameterXmlAnalysis.saveXml(inf,vl,"E:\\项目管理\\二院项目\\项目三\\03开发\\参数描述文件\\参数描述文件模板2.xml");
//        System.out.printf("解析完成");
    }
    /**
     * 判断文件的编码格式
     * @param filePath
     * @return 文件编码格式
     * @throws Exception
     */
    public static String codeString(String filePath) throws Exception{

        File file = new File(filePath);
        if(file==null || !file.exists()){
            System.out.println("文件不存在..."+file.getAbsolutePath());
            return null;
        }

        BufferedInputStream bin = new BufferedInputStream( new FileInputStream(file));
        int p = (bin.read() << 8) + bin.read();
        String code = null;
        //其中的 0xefbb、0xfffe、0xfeff、0x5c75这些都是这个文件的前面两个字节的16进制数
        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            case 0x5c75:
                code = "ANSI|ASCII" ;
                break ;
            default:
                code = "GBK";
        }

        return code;
    }

}
