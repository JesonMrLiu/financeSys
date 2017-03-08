package com.finance.controller;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.bwdz.fpt.common.cache.JRedisClient;
import com.finance.entity.User;
import com.finance.util.AppContextUtils;
import com.finance.util.DateUtils;
import com.finance.util.KeysUtil;
import com.finance.util.PropertiesUtils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ParamController {

	protected HttpServletRequest request = null;
	protected HttpServletResponse response = null;
	protected String main_path;
	protected String super_path;
	protected String static_path;
	protected User user;
//	protected JRedisClient client;

	protected void init() {
		this.request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//		this.client = (JRedisClient) AppContextUtils.getBeanById("redisClient");

		this.main_path = PropertiesUtils.getContextProperty("main");
		this.super_path = PropertiesUtils.getContextProperty("super.main");
		this.static_path = PropertiesUtils.getContextProperty("static");
		
		this.user = ((User) this.request.getSession().getAttribute(KeysUtil.LOGIN_USER_CACHE_KEY));
		String title = PropertiesUtils.getContextProperty("title");

		String serverName = this.request.getServerName();
		/*if (this.super_path.contains(serverName))
			this.request.setAttribute("MAIN_PATH", this.super_path);
		else {
			this.request.setAttribute("MAIN_PATH", this.main_path);
		}*/
		this.request.setAttribute("STATIC_PATH", this.static_path);
		this.request.setAttribute("TITLE", title);
		this.request.setAttribute("CURRENT_USER", this.user);

		String uri = this.request.getRequestURI();
		this.request.setAttribute("URI", uri);

//		this.menus = AuthorityFactory.getInstance().getAuthMenusByAdmin(
//				this.current_admin);
//		this.request.setAttribute("menus", this.menus);
	}

//	public void initMenus(Admin admin) {
//	}

	protected User getCurrentUser() throws Exception{
		if(user == null){
			init();
		}
		//首先从redis中查找
		user = (User) request.getSession().getAttribute(KeysUtil.LOGIN_USER_CACHE_KEY);
		return user;
	}
	
	public int pageParam(String param) {
		int page = intParam(param);
		return page <= 1 ? 1 : page;
	}

	public int intParam(String param) {
		String str = param(param);
		if ((StringUtils.isNotEmpty(str)) && (str.matches("\\d+"))) {
			return Integer.parseInt(str);
		}
		return 0;
	}

	public Date dateParam(String param) {
		Date date = null;
		String str = param(param);
		String regex = "(\\d{4})([\\-\\/年\\.]){1}([01]\\d){1}([\\-\\/年\\.]){1}([0-3]\\d){1}((\\s[0-2]\\d){1}:([0-6]\\d){1}(:([0-6]\\d){1})?)?";
		if ((StringUtils.isNotEmpty(str)) && (str.matches(regex))) {
			date = DateUtils.strToDate(str);
		}
		return date;
	}

	public BigDecimal decimalParam(String param) {
		String str = param(param);
		if ((StringUtils.isNotEmpty(str)) && (str.matches("(\\-?)\\d+(\\.\\d)?"))) {
			Double d = Double.valueOf(str);
			return BigDecimal.valueOf(d.doubleValue());
		}
		return BigDecimal.ZERO;
	}

	public String param(String param) {
		return this.request.getParameter(param);
	}
}
