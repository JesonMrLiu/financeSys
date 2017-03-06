package com.finance.controller;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.finance.bean.Pager;
import com.finance.bean.ResultBean;
import com.finance.util.PropertiesUtils;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

public class BaseController extends ParamController {

	protected ResultBean res = null;
	protected JSONObject result = null;
	protected Pager pager;
	protected int page = 1;// 当前页
	protected final int size = 11;// 每页显示的最大记录条数
	protected int sum = 1;

	protected Timestamp now() {
		Calendar cal = Calendar.getInstance();
		return new Timestamp(cal.getTimeInMillis());
	}

	protected void initPage(List<?> list) {
		int count = 0;
		PageList<?> pageList = (PageList<?>) list;
		if (pageList != null) {
			count = pageList.getPaginator().getTotalCount();
			this.sum = pageList.getPaginator().getTotalPages();
		}
		this.pager = new Pager(this.page, size, this.sum, count);
		this.request.setAttribute("pager", this.pager);
	}

	protected String getRoot() {
		String root = PropertiesUtils.getContextProperty("upload.file.path");
		return root;
	}

}
