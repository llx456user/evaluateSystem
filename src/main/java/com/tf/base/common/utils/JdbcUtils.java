package com.tf.base.common.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtils {

	public static Connection getConn(String jdbcName,String url,String username,String password){
		 
		  try {
               Class.forName(jdbcName);
               System.out.println("加载驱动成功！");
           } catch (ClassNotFoundException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
               System.out.println("加载驱动失败！");
               return null;
           }
           
		   Connection con = null;
           try {
               //获取数据库连接
               con = DriverManager.getConnection(url, username, password);
               System.out.println("获取数据库连接成功！");
               System.out.println("进行数据库操作！");
           } catch (SQLException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
               System.out.println("获取数据库连接失败！");
           }finally{
//               try {
//                   con.close();
//               } catch (SQLException e) {
//                   // TODO Auto-generated catch block
//                   e.printStackTrace();
//               }
           }
         return con;
			           
	}
}
