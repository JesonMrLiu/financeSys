package com.finance.bean;

import java.io.Serializable;

public class ResultBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String info;
	private boolean isSuc;
	private String datas;

	public ResultBean() {
	}

	public ResultBean(String info, boolean isSuc, String datas) {
		this.info = info;
		this.isSuc = isSuc;
		this.datas = datas;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean isSuc() {
		return this.isSuc;
	}

	public void setSuc(boolean isSuc) {
		this.isSuc = isSuc;
	}

	public String getDatas() {
		return this.datas;
	}

	public void setDatas(String datas) {
		this.datas = datas;
	}
}