/**
 * 
 */
package com.tf.base.common.exception;

/**
 * excel操作的异常
 * @author spring
 *
 */
public class ExcelOptExcepiton extends RuntimeException {

	private static final long serialVersionUID = 3183420419378782361L;

	public ExcelOptExcepiton() {
		super();
	}

	public ExcelOptExcepiton(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcelOptExcepiton(String message) {
		super(message);
	}

	public ExcelOptExcepiton(Throwable cause) {
		super(cause);
	}
	
	
}
