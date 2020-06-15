package com.tf.base.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tf.base.common.domain.DatasourceDb;
import com.tf.base.common.domain.DatasourceSql;
import com.tf.base.common.utils.JSONUtil;
import com.tf.base.common.utils.JdbcUtils;



/**
 * 根据数据库类型获取
 */
public class ParamSqlImpl implements ParamProcess {

	// private String tableName;
	private Connection conn;
	// private ResultSet tableRet;
	// private DatabaseMetaData m_DBMetaData;
	private DatasourceSql sqlBean = null;
	private PreparedStatement pst = null;
	private ResultSet ret = null;

	/**
	 * 
	 * @param datasourceDb
	 *            {"server":"com.mysql.jdbc.Driver","url":"jdbc:mysql://47.104.1.212:3306/evaluation_integration","username":"evl_inte","password":"evl_inte0321"}
	 * @param sqlBean
	 *
	 */
	public ParamSqlImpl(DatasourceDb datasourceDb, DatasourceSql sqlBean) {
		this.sqlBean = sqlBean;

		String config = datasourceDb.getConfig();
		JSONObject jsonConfig = JSONObject.parseObject(config);
		String server = jsonConfig.getString("server");
		String url = jsonConfig.getString("url");
		String username = jsonConfig.getString("username");
		String password = jsonConfig.getString("password");

		// this.conn=JdbcUtils.getConn("com.mysql.jdbc.Driver",
		// "jdbc:mysql://47.104.1.212:3306/evaluation_integration",
		// "evl_inte",
		// "evl_inte0321");

		this.conn = JdbcUtils.getConn(server, url, username, password);

		try {
			pst = conn.prepareStatement(sqlBean.getSqlStr());
			// 获取数据库列
			ret = pst.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// finally{
		// try {
		// conn.close();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// }

		// try {
		// this.m_DBMetaData = conn.getMetaData();
		// /**
		// * /*其中"%"就是表示*的意思，也就是任意所有的意思。
		// * 其中tableName就是要获取的数据表的名字，如果想获取所有的表的名字，就可以使用"%"来作为参数了。
		// */
		// this.tableRet = m_DBMetaData.getTables(null, "%", tableName, new
		// String[] { "TABLE" });
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

	}

	@Override
	public String[] getColumnsName() {
		List<String> list = new ArrayList<>();
		try {
			// 数据库列名
			ResultSetMetaData data = ret.getMetaData();
			// getColumnCount 获取表列个数
			for (int i = 1; i <= data.getColumnCount(); i++) {
				// 获取列表 index 从1开始、列名、列类型、列的数据长度
				System.out.println(
						data.getColumnName(i) + "\t" + data.getColumnTypeName(i) + "\t" + data.getColumnDisplaySize(i));
				list.add(data.getColumnName(i));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// try {
		// /**
		// * JDBC里面通过getColumns的接口，实现对字段的查询。跟getTables一样，"%"表示所有任意的（字段），
		// * 而tableName就是数据表的名字。
		// **/
		// ResultSet colRet = m_DBMetaData.getColumns(null, "%", tableName,
		// "%");
		// while (colRet.next()) {
		// String columnName = colRet.getString("COLUMN_NAME");
		// list.add(columnName);
		// }
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		String[] ret = list.toArray(new String[list.size()]);
		return ret;
	}

	@Override
	public int getColumnsCount() {
		return this.getColumnsName().length;
	}

	@Override
	public int[] getColumnsIntValue(String columnName) {

		List<Integer> list = new ArrayList<>();
		try {
			while (ret.next()) {
				Integer r = ret.getInt(columnName);
				if (r != null) {
					list.add(r);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int[] ret = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ret[i] = list.get(i);
		}

		return ret;

	}

	@Override
	public long[] getColumnsLongValues(String columnName) {
		List<Long> list = new ArrayList<>();
		try {
			while (ret.next()) {
				Long r = ret.getLong(columnName);
				if (r != null) {
					list.add(r);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		long[] ret = new long[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ret[i] = list.get(i);
		}

		return ret;
	}

	@Override
	public double[] getColumnsDoubleValues(String columnName) {
		List<Double> list = new ArrayList<>();
		try {
			while (ret.next()) {
				Double r = ret.getDouble(columnName);
				if (r != null) {
					list.add(r);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		double[] ret = new double[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ret[i] = list.get(i);
		}

		return ret;

	}

	@Override
	public float[] getColumnsFloatValues(String columnName) {

		List<Float> list = new ArrayList<>();
		try {
			while (ret.next()) {
				Float r = ret.getFloat(columnName);
				if (r != null) {
					list.add(r);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		float[] ret = new float[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ret[i] = list.get(i);
		}

		return ret;

	}

	@Override
	public JSONObject getDataJSONObject() {
		JSONObject jsonObject = new JSONObject();
		String[] cNames = getColumnsName();;
		for(String cName:cNames){
			jsonObject.put(cName,new JSONArray());
		}
		try {
			while (ret.next()) {
				for(String cName:cNames){
					jsonObject.getJSONArray(cName).add(ret.getString(cName));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
 * 使用完该类后务必调用关闭连接方法
 */
	public void closeConnection() {

		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if (pst != null)
			try {
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if (ret != null)
			try {
				ret.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
