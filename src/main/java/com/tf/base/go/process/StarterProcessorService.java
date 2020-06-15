package com.tf.base.go.process;

import com.alibaba.fastjson.JSONObject;
import com.tf.base.common.domain.DatasourceDb;
import com.tf.base.common.domain.DatasourceFile;
import com.tf.base.common.domain.DatasourceSql;
import com.tf.base.common.persistence.DatasourceDbMapper;
import com.tf.base.common.persistence.DatasourceFileMapper;
import com.tf.base.common.persistence.DatasourceSqlMapper;
import com.tf.base.go.model.IndexNodeDefinition;
import com.tf.base.go.type.Category;
import com.tf.base.go.type.NodeType;
import com.tf.base.util.ParamFileImpl;
import com.tf.base.util.ParamProcess;
import com.tf.base.util.ParamSqlImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * Created by HP on 2018/4/18.
 */
@Service
public class StarterProcessorService {

    private Logger logger = Logger.getLogger(StarterProcessorService.class.getName());

    @Autowired
    private DatasourceDbMapper datasourceDbMapper;
    @Autowired
    private DatasourceSqlMapper datasourceSqlMapper;
    @Autowired
    private DatasourceFileMapper datasourceFileMapper;

    private ParamProcess buildFileProcessor(IndexNodeDefinition node, JSONObject inputJson) {
        if (null != node) {
            DatasourceFile datasourceFile = datasourceFileMapper.selectByPrimaryKey(node.getId());
            String filePath = datasourceFile.getFilePath();
            if (null != inputJson && inputJson.containsKey("filePath")) {
                filePath = inputJson.getString("filePath");
            }
            return new ParamFileImpl(filePath);
        }
        return null;
    }

    private ParamProcess buildSqlProcessor(IndexNodeDefinition node, JSONObject inputJson) {
        if (null != node) {
            DatasourceSql datasourceSql = datasourceSqlMapper.selectByPrimaryKey(node.getId());
            if (null != datasourceSql) {
                DatasourceDb datasourceDb = datasourceDbMapper.selectByPrimaryKey(datasourceSql.getDatasourceId());
                if (null != datasourceDb) {
                    return new ParamSqlImpl(datasourceDb, datasourceSql);
                }
            }
        }
        return null;
    }

    public StarterProcess buildNodeProcessor(IndexNodeDefinition node, JSONObject inputJson) {
        if (null != node) {
            if (Category.Datasource.equals(node.getNodeCategory())) {
                if (NodeType.Sql.equals(node.getNodeType())) {
                    return new SimpleStarterProcessor(buildSqlProcessor(node, inputJson));
                } else if (NodeType.File.equals(node.getNodeType())) {
                    return new SimpleStarterProcessor(buildFileProcessor(node, inputJson));
                }
            } else if (Category.Constant.equals(node.getNodeCategory())) {

            } else if (Category.If.equals(node.getNodeCategory())) {

            } else if (Category.Grid.equals(node.getNodeCategory())) {

            } else if (Category.Model.equals(node.getNodeCategory())) {

            } else if (Category.Picture.equals(node.getNodeCategory())) {

            } else if (Category.Error.equals(node.getNodeCategory())) {
                //not todo
            }
        }
        return new SimpleStarterProcessor();
    }

}
