package com.finance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.finance.bean.ResultBean;
import com.finance.entity.User;
import com.finance.service.UserService;
import com.finance.util.MD5Utils;

@Controller
@RequestMapping("registerController.do")
public class RegisterController {

	@Autowired
	private UserService service;
	
	
	/**
	 * 注册
	 * @return
	 */
	@RequestMapping(params = "method=register")
	public String register(){
		return "common/register";
	}
	
	@RequestMapping(params = "method=doRegister")
	@ResponseBody
	public ResultBean doRegister(@ModelAttribute User user){
		ResultBean res = null;
		try {
			User temp = service.getUserByUsername(user);
			if(temp != null){
				res = new ResultBean("抱歉，用户名【"+user.getUsername()+"】已经注册！", false, "");
				return res;
			}
			user.setPassword(MD5Utils.generate(user.getPassword()));
			service.addUser(user);
			
			res = new ResultBean("恭喜您，用户注册成功！", true, "");
		} catch (Exception e) {
			e.printStackTrace();
			res = new ResultBean("抱歉，用户注册出现位置错误！", false, "");
		}
		return res;
	}
}
