package com.finance.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bwdz.fpt.common.cache.JRedisClient;
import com.finance.entity.User;
import com.finance.util.AppContextUtils;
import com.finance.util.KeysUtil;

public class LoginInterceptor implements HandlerInterceptor {
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		JRedisClient client = (JRedisClient) AppContextUtils.getBeanById("redisClient");
		//查询用户是否已经登录，如果没有登录，那么就跳转到登录页面
		User user = (User) client.getCachObject(KeysUtil.LOGIN_USER_CACHE_KEY);
		//查看session中是否有用户登陆信息
		User temp = (User) arg0.getSession().getAttribute(KeysUtil.LOGIN_USER_CACHE_KEY);
		//如果session中有用户登录信息，那么表名该用户正处于登录中，而redis中没有用户登录信息的话，那么就将用户登录信息保存到Redis中
		if(temp != null && user == null){
			client.addCacheObject(KeysUtil.LOGIN_USER_CACHE_KEY, temp, 24*3600);
		}
		//获取请求的URI
		String uri = arg0.getRequestURI();
		//如果是正在登录，则直接跳过
		if(uri.contains("loginController.do")){
			//如果用户已经登录了，那么不跳转到登录页
			if(user != null){
				arg1.sendRedirect("homeController.do?method=home");
				return false;
			}
			
			//验证session是否有用户登录信息
			user = (User) arg0.getSession().getAttribute(KeysUtil.LOGIN_USER_CACHE_KEY);
			if(user != null){
				arg1.sendRedirect("homeController.do?method=home");
				return false;
			}
			return true;
		}
		if(user == null){
			//检查session里面是否有用户信息
			user = (User) arg0.getSession().getAttribute(KeysUtil.LOGIN_USER_CACHE_KEY);
			if(user == null){
				arg1.sendRedirect("loginController.do?method=login");
				return false;
			}
		}
		
		return true;
	}

}
