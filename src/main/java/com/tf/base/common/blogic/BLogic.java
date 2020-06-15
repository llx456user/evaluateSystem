package com.tf.base.common.blogic;

/**
 * 业务逻辑接口
 *
 * @author liyq
 *
 * @param <P> 业务逻辑输入参数
 * @param <R> 业务逻辑执行结果
 */
public interface BLogic<P, R> {

    /**
     * 执行业务逻辑
     *
     * @param params 业务逻辑输入参数
     * @return 业务逻辑执行结果
     */
    R execute(P params);
}
