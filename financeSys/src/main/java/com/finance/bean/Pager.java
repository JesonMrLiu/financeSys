package com.finance.bean;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class Pager {
	private int page = 1;
	private int size = 10;
	private int sum = 1;
	private int total = 0;

	public Pager() {
	}

	public Pager(int page, int size, int sum, int total) {
		this.page = page;
		this.size = size;
		this.sum = sum;
		this.total = total;
	}

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSum() {
		return this.sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public int getTotal() {
		return this.total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getHtml() {
		String params = initParams();
		StringBuffer buffer = new StringBuffer();
		buffer.append("<div class='dataTables_paginate paging_bootstrap pagination'>").append("<ul>")
				.append("<li class='disabled'><a>共").append(this.total).append("条")
				.append(" / ").append("共").append(this.sum)
				.append("页</a></li>").append("<li><a href='?")
				.append(params).append("page=1'>首页</a></li>")
				.append("<li><a href='?").append(params).append("page=")
				.append(this.page > 1 ? this.page - 1 : 1)
				.append("' aria-label='previous'>上一页</a></li>");
		if(sum <= 10){
			for(int index = 1; index <= sum; index++){
				buffer.append("<li class='").append(page == index ? "active" : "").append("'><a href='?")
					.append(params).append("page=").append(index).append("'>").append(index).append("</a></li>");
			}
		} else if(page <= 5) {
			for(int index = 1; index <= 10; index++){
				buffer.append("<li class='").append(page == index ? "active" : "").append("'><a href='?")
					.append(params).append("page=").append(index).append("'>").append(index).append("</a></li>");
			}
			if(sum > 10){
				buffer.append("<li><a href='javascript:;'>...</a></li>");
			}
		} else if ((page > 5) && (page < sum - 4)) {
			if(page - 4 > 1){
				buffer.append("<li><a href='javascript:;'>...</a></li>");
			}
		    for (int index = page - 4; index <= page + 4; index++){
		    	buffer.append("<li class='").append(page == index ? "active" : "").append("'><a href='?")
		    		.append(params).append("page=").append(index).append("'>").append(index).append("</a></li>");
		    }
		    if(page + 4 < sum){
		    	buffer.append("<li><a href='javascript:;'>...</a></li>");
		    }
	    } else {
	    	if(sum - 6 > 1){
	    		buffer.append("<li><a href='javascript:;'>...</a></li>");
	    	}
			for (int index = sum - 6; index <= sum; index++) {
				buffer.append("<li class='").append(page == index ? "active" : "").append("'><a href='?").append(params)
					.append("page=").append(index).append("'>").append(index).append("</a></li>");
			}
	    }
		buffer.append("<li><a href='?").append(params).append("page=")
				.append(this.page < this.sum ? this.page + 1 : this.sum)
				.append("' aria-label='next'>下一页</a></li>")
				.append("<li><a href='?").append(params).append("page=")
				.append(this.sum).append("'>末页</a></li>").append("</ul>")
				.append("</div>");
		return buffer.toString();
	}

	@SuppressWarnings("unchecked")
	public String initParams() {
		StringBuffer result = new StringBuffer();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		Map<String, Object> map = request.getParameterMap();
		for (String key : map.keySet()) {
			if (!"page".equals(key)) {
				String[] ps = (String[]) map.get(key);
				result.append(key).append("=").append(ps[0]).append("&");
			}
		}
		return result.toString();
	}
}
