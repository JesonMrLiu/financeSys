package com.finance.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bwdz.fpt.common.cache.JRedisClient;
import com.finance.bean.ResultBean;
import com.finance.entity.User;
import com.finance.service.UserService;
import com.finance.util.KeysUtil;
import com.finance.util.MD5Utils;

@Controller
@RequestMapping("loginController.do")
public class LoginController {

	@Autowired
	private UserService service;
	@Autowired
	private JRedisClient client;
	
	@RequestMapping(params = "method=login")
	public String login(){
		
		return "common/login";
	}
	
	@RequestMapping(params = "method=doLogin")
	@ResponseBody
	public ResultBean doLogin(@ModelAttribute User user, HttpServletRequest request){
		ResultBean res = null;
		try {
			String isRemember = request.getParameter("remember");
			User temp = service.getUserByUsername(user);
			if(temp == null){
				res = new ResultBean("抱歉，不存在【用户名："+user.getUsername()+"】的用户！！", false, "");
				return res;
			}
			
			//验证密码是否一致
			if(!temp.getPassword().equals(MD5Utils.generate(user.getPassword()))){
				res = new ResultBean("抱歉，用户【"+user.getUsername()+"】的的登录密码有误！！", false, "");
				return res;
			}
			client.addCacheObject(KeysUtil.LOGIN_USER_CACHE_KEY, temp, 24*3600);
			request.getSession().setAttribute(KeysUtil.LOGIN_USER_CACHE_KEY, temp);
			res = new ResultBean("恭喜您，登录成功！！", true, "");
		} catch (Exception e) {
			e.printStackTrace();
			res = new ResultBean("抱歉，登录出现异常！！", false, "");
		}
		return res;
	}
}
