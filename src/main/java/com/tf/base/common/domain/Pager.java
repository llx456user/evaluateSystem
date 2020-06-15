package com.tf.base.common.domain;

public class Pager {

	private int page;//当前页
	private int rows;//每页显示数量
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
}
