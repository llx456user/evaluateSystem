package com.tf.base.common.interceptor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.tf.base.common.domain.SqlExceptionLog;
import com.tf.base.common.persistence.SqlExceptionLogMapper;
import com.tf.base.common.utils.SpringContextHolder;

/**
 * Mybatis拦截器. 拦截SQL执行时的异常，写入DB.
 * 
 */
@Intercepts({
		@Signature(type = Executor.class, method = "update", args = {
				MappedStatement.class, Object.class }),
		@Signature(type = Executor.class, method = "query", args = {
				MappedStatement.class, Object.class, RowBounds.class,
				ResultHandler.class }) })
public class SqlExceptionInterceptor implements Interceptor {

	/**
	 * 线程池.
	 */
	private static ExecutorService executor = Executors.newFixedThreadPool(5);

	/**
	 * 拦截方法.
	 */
	public Object intercept(Invocation invocation) throws Throwable {

		try {
			return invocation.proceed();
		} catch (Exception e) {

			try {
				// 取得各种值
				MappedStatement statement = (MappedStatement) invocation
						.getArgs()[0];
				Object parameter = invocation.getArgs()[1];
				BoundSql boundSql = statement.getBoundSql(parameter);

				// 防止死循环
				if (statement.getId().toUpperCase()
						.contains("sqlExceptionLogMapper".toUpperCase())) {
					throw e;
				}

				// 日志对象
				final SqlExceptionLog record = new SqlExceptionLog();
				record.setSystemName("refundsystem"); // 系统名称
				record.setSqlId(statement.getId()); // sqlId
				record.setSqlParameter(parameter.toString()); // SQL参数
				record.setSqlSource(boundSql.getSql()); // SQL语句
				record.setSqlType(statement.getSqlCommandType().toString()); // SQL类型
				record.setExceptionMessage(e.getCause().toString()); // 异常简要信息
				record.setExceptionStack(getExceptionStackTrace(e)); // 堆栈信息
				record.setCreateTime(new Date()); // 创建时间

				// 放入线程池
				/*executor.execute(new Runnable() {
					public void run() {

						// 外面类里直接注入貌似有问题，故这么搞下。
						SqlExceptionLogMapper mapper = SpringContextHolder
								.getBean("sqlExceptionLogMapper");
						mapper.insertSelective(record);
					}
				});*/
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			// 抛出异常
			throw e;
		}
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	/**
	 * 配置文件读取属性.
	 */
	public void setProperties(Properties properties) {
	}

	/**
	 * 获取异常的堆栈信息.
	 * 
	 * @param e
	 * @return
	 */
	private String getExceptionStackTrace(Exception e) {
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		e.printStackTrace(new java.io.PrintWriter(buf, true));
		String expMessage = buf.toString();
		try {
			buf.close();
		} catch (IOException ex) {
		}
		return expMessage;
	}
}