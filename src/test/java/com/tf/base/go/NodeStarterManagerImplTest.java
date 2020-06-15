package com.tf.base.go;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tf.base.go.model.IndexDefinition;
import com.tf.base.go.model.IndexNodeDefinition;
import com.tf.base.go.process.IndexManager;
import com.tf.base.go.process.NodeStarter;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2018/4/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "src/main/webapp")
@ContextConfiguration({"file:D:/evaluateSystem/evaluateSystem/src/main/webapp/WEB-INF/spring/applicationContext.xml"})
public class NodeStarterManagerImplTest {

    private static final Charset charset = StandardCharsets.UTF_8;

    public static String readFile(String fileName) throws IOException {
        String classpath = System.class.getResource("/").getPath();
        File file = new File(classpath, fileName);
        System.out.println("read file " + file.getAbsolutePath() +" , file exists "+file.exists());
        List<String> list = Files.readAllLines(file.toPath(), charset);
        return String.join("", list);
    }

    private static String jsonString;

    public static JSONObject readJsonObject(String fileName) throws IOException {
        String jsonString = readFile(fileName);
        return JSONObject.parseObject(jsonString);
    }

    @BeforeClass
    public static void setUp() throws IOException {
        jsonString = readFile("/template/index_sql.json");
    }

    @Test
    public void buildIndexTest() throws IOException {
//        indexModelManager = new IndexModelManagerImpl();
        IndexDefinition index = IndexDefinition.build(JSON.parseObject(jsonString));
//        System.out.println(indexModelManager);
        List<IndexNodeDefinition> list = index.getNodes();
        for(IndexNodeDefinition node : list){
            System.out.println(String.format("{category:%s,key:%d,deep:%d}",node.getNodeCategory(),node.getKey(),node.getDeep()));
        }
//        IndexManager manager = new IndexManager();
//       new IndexManager().run(index.getNodes());
    }

    @Autowired
    IndexManager indexManager;

    @Test
    public void buildIndexManagerTest(){
        IndexDefinition index = IndexDefinition.build(JSON.parseObject(jsonString));
        indexManager.build(index);
        indexManager.allowStart();
        indexManager.start(null);
    }

}
