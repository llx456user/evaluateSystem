package com.tf.base.jni.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.tf.base.jni.model.JNIParameter;
import com.tf.base.jni.model.ParameterType;
import com.tf.base.jni.test.Model.Book;
import com.tf.base.jni.test.Model.Box;
import com.tf.base.jni.test.Model.Pen;
import com.tf.base.jni.test.Model.Student;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import org.junit.Test;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HP on 2018/4/24.
 */
public class JsonParserTest {

    @Test
    public void productTest() {

        JNIParameter nameParameter = new JNIParameter("name", "String");
        JNIParameter ageParameter = new JNIParameter("age", "Integer");
        JNIParameter numberParameter = new JNIParameter("number", "Integer");
        JNIParameter sumParameter = new JNIParameter("sum", "Integer");
        JNIParameter widthParameter = new JNIParameter("width", "Integer", true);
        JNIParameter heightParameter = new JNIParameter("height", "Integer", true);
        JNIParameter areaParameter = new JNIParameter("area", "Integer", true);

        JNIParameter teachersParameter = new JNIParameter("teachers", "String", true);

        JNIParameter booksParameter = new JNIParameter("books", "Book", new JNIParameter[]{nameParameter}, true);

        JNIParameter pensParameter = new JNIParameter("pens", "Pen", new JNIParameter[]{nameParameter}, true);

        JNIParameter boxParameter = new JNIParameter("box", "Box", new JNIParameter[]{nameParameter, pensParameter});

        JNIParameter studentParameter = new JNIParameter("student", "Student", new JNIParameter[]{teachersParameter, nameParameter, booksParameter, boxParameter}, true);

        JNIParameter teacherParameter = new JNIParameter("teacher", "Teacher", new JNIParameter[]{studentParameter, nameParameter, booksParameter, boxParameter});

        JNIParameter inModel = new JNIParameter("in", "InParameter", new JNIParameter[]{teacherParameter,widthParameter, heightParameter});
        JNIParameter outModel = new JNIParameter("out", "OutParameter", new JNIParameter[]{areaParameter});
//        JNIParameter inModel =  new JNIParameter("in","integer",true);
//        JNIParameter outModel =  new JNIParameter("out","integer",true);

        List<JNIParameter> definitionList = Lists.asList(inModel, outModel, new JNIParameter[]{/*studentParameter,teacherParameter, booksParameter, pensParameter, boxParameter*/});

        String templatePath = "/mnt/document/workspace/evaluateSystem/evaluateSystem/src/test/resources/template/";
//        String templatePath = "E:/workspace/evaluateSystem/evaluateSystem/src/test/resources/template/";
        String dll_name = "project20180427142603";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("api_name", "customer_cpp_api");
        map.put("header", dll_name);
        map.put("inModel", inModel);
        map.put("outModel", outModel);
        map.put("list", definitionList);
        map.put("arrayFunction", true);
//        map.put("parent", new JNIParameter("source", ""));
//        String javaOutFile = "D:/workspace/work/server/eval/evaluateSystem/evaluateSystem/src/test/java/com/tf/base/jni/test/output/JsonParser.java";
//        makeFile(map, templatePath, "parse_java.ftl", javaOutFile);
        String cppWorkspacePath = "/mnt/document/workspace/output/";
//        String cppWorkspacePath = "E:/workspace-ms/parse/";

        makeFile(map, templatePath, "cpp_header.ftl", cppWorkspacePath + dll_name + ".h");
        makeFile(map, templatePath, "cpp_main.ftl", cppWorkspacePath + dll_name + ".cpp");
        makeFile(map, templatePath, "cpp_parse.ftl", cppWorkspacePath + "support.cpp");
//        makeFile(map, templatePath, "cpp_header.ftl", "E:/project20180427142603/TestDll/" + dll_name + ".h");
//        makeFile(map, templatePath, "cpp_test.ftl", "E:/project20180427142603/TestDll/TestDll.cpp");
    }

    public void makeFile(Object value, String templatePath, String templeName, String outFileName) {
        try {
            Configuration cfg = new Configuration();
            cfg.setTemplateLoader(new ClassTemplateLoader());
            cfg.setObjectWrapper(new DefaultObjectWrapper());
            cfg.setDefaultEncoding("UTF-8");
            cfg.setDirectoryForTemplateLoading(new File(templatePath));
            Writer out = new OutputStreamWriter(new FileOutputStream(outFileName), "UTF-8");
            Template temp = cfg.getTemplate(templeName);
            temp.setEncoding("UTF-8");
            temp.process(value, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
