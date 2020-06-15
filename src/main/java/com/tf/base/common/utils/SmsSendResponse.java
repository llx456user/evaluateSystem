package com.tf.base.common.utils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 短信发送完以后，短信商返回的结果。
 * @author chenfei
 *
 */
@XmlRootElement(name = "returnsms")
public class SmsSendResponse {

//	private String returnsms; // root标签,根不用加注解了。

	private String returnstatus;
	private String message;
	private String remainpoint;
	private String taskID;
	private String successCounts;

	public String getMessage() {
		return message;
	}
	
	@XmlElement(name = "remainpoint")
	public String getRemainpoint() {
		return remainpoint;
	}

	// @XmlElement(name = "returnsms")
//	public String getReturnsms() {
//		return returnsms;
//	}

	@XmlElement(name = "returnstatus")
	public String getReturnstatus() {
		return returnstatus;
	}

	@XmlElement(name = "successCounts")
	public String getSuccessCounts() {
		return successCounts;
	}

	@XmlElement(name = "taskID")
	public String getTaskID() {
		return taskID;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setRemainpoint(String remainpoint) {
		this.remainpoint = remainpoint;
	}

//	public void setReturnsms(String returnsms) {
//		this.returnsms = returnsms;
//	}

	public void setReturnstatus(String returnstatus) {
		this.returnstatus = returnstatus;
	}

	public void setSuccessCounts(String successCounts) {
		this.successCounts = successCounts;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}

}
