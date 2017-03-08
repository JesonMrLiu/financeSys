package com.finance.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bwdz.fpt.common.cache.JRedisClient;
import com.finance.util.KeysUtil;

@Controller
@RequestMapping("homeController.do")
public class IndexController extends BaseController {

	@Autowired
	private JRedisClient client;
	
	@RequestMapping(params = "method=home")
	public String home(){
		init();
		return "common/home";
	}
	

	@RequestMapping(params = "method=logout")
	public String logout(HttpServletRequest request, HttpServletResponse response){
		try {
			client.delCache(KeysUtil.LOGIN_USER_CACHE_KEY);
			request.getSession().removeAttribute(KeysUtil.LOGIN_USER_CACHE_KEY);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:loginController.do?method=login";
	}
}
