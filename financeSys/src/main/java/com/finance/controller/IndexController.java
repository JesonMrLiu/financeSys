package com.finance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("homeController.do")
public class IndexController {

	@RequestMapping(params = "method=home")
	public String home(){
		
		return "common/home";
	}
	
}
