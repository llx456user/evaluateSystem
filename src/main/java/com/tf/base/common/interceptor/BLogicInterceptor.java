package com.tf.base.common.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * BLogic拦截器
 *
 * @author liyq
 */
@Component
@Aspect
public class BLogicInterceptor {

	private static Logger log = LoggerFactory.getLogger(BLogicInterceptor.class);
	
    /**
     * 以所有blogic包下面的类的execute方法为切面
     */
    @Pointcut("execution(public * com.shijie99.*.*.blogic.*.execute(..))")
    public void blogicExecutePointcut() {
    }

    /**
     * 对blogicExecutePointcut切面的执行前后输出日志
     *
     * @param pjp ProceedingJoinPoint
     * @return result
     * @throws Throwable
     */
    @Around("blogicExecutePointcut()")
    public Object aroundBLogicExecute(ProceedingJoinPoint pjp) throws Throwable {

        if (log.isInfoEnabled()) {

            // 业务逻辑执行前日志
            log.info("*** Starting BLogic [" + pjp.getTarget().getClass().getName() + "] ***");

            Object input = pjp.getArgs()[0];

            if (input == null) {
                log.info("*** BLogicParams:null ***");
            } else {
                log.info("*** BLogicParams:" + input.toString() + " ***");
            }

            // 执行业务逻辑
            Object output = pjp.proceed();

            // 业务逻辑执行后日志
            if (output == null) {
                log.info("*** BLogicResult:null ***");
            } else {
                log.info("*** BLogicResult:" + output.toString() + " ***");
            }

            log.info("*** Finished BLogic [" + pjp.getTarget().getClass().getName() + "] ***");

            return output;

        } else {
            return pjp.proceed();
        }
    }
}
