package com.tf.base.common.utils;

import javax.xml.bind.annotation.XmlElement;

 
public class Statusbox {
//	private String statusbox;
	private String mobile;
	private String taskid;
	private String status;
	private String receivetime;
	private String errorcode;
	private String extno;

//	@XmlElement(name="statusbox")
//	public String getStatusbox() {
//		return statusbox;
//	}
//
//	public void setStatusbox(String statusbox) {
//		this.statusbox = statusbox;
//	}

	@XmlElement(name="mobile")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@XmlElement(name="taskid")
	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	@XmlElement(name="status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@XmlElement
	public String getReceivetime() {
		return receivetime;
	}

	public void setReceivetime(String receivetime) {
		this.receivetime = receivetime;
	}

	@XmlElement
	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	@XmlElement
	public String getExtno() {
		return extno;
	}

	public void setExtno(String extno) {
		this.extno = extno;
	}
}
