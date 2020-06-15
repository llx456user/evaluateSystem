package com.tf.base.common.utils;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "returnsms")
public class SmsQueryResponse {

	private List<Statusbox> statusbox = new ArrayList<Statusbox>();

	public List<Statusbox> getStatusbox() {
		return statusbox;
	}

	public void setStatusbox(List<Statusbox> statusbox) {
		this.statusbox = statusbox;
	}

}
