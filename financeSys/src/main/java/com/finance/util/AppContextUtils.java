package com.finance.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class AppContextUtils {
	private static final String SPRING_FILE_PATH = "classpath:config/spring/springmvc-servlet.xml";
	private static ApplicationContext context = new FileSystemXmlApplicationContext(SPRING_FILE_PATH);

	public static Object getBeanById(String id)	{
		return context.getBean(id);
	}

	public static void main(String[] args) {
		
	}
}
