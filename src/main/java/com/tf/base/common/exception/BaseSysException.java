package com.tf.base.common.exception;

import java.util.ArrayList;
import java.util.List;

public class BaseSysException extends RuntimeException {

	/**
	 *  serialVersionUID
	 */
	private static final long serialVersionUID = -4756258986474599889L;

	/**
     * 异常对象
     */
    private Object[] errStackArray;

    /**
     * 异常码
     */
    private String errCode = null;

    /**
     * 构造方法
     */
    public BaseSysException(){
        super();
    }
    
	public BaseSysException(String errCode, Object... errObjects) {
		
		super(errCode);
		
		// 异常信息的参数
        List<Object> errStack = new ArrayList<Object>();

        // 拼接异常信息的参数
        for (Object object : errObjects) {
            errStack.add(object);
        }

        this.errStackArray = errStack.toArray();

        this.errCode = errCode;
	}
	
	 /**
     * 抛出异常。
     *
     * @param errMsg 异常信息
     * @param e 异常
     */
    public BaseSysException(String errMsg, Exception e) {
        super(errMsg, e.getCause());
    }

    /**
     * 抛出异常。
     *
     * @param e 异常
     */
    public BaseSysException(Exception e) {
        super(e.getMessage(), e.getCause());
    }

    /**
     * 取得异常对象
     *
     * @return 异常对象
     */
    public Object[] getErrStackArray() {
        return new Object[]{errStackArray};
    }

    /**
     * 取得异常码
     *
     * @return 异常码
     */
    public String getErrCode() {
        return errCode;
    }
}
