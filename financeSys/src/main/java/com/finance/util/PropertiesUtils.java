package com.finance.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertiesUtils extends PropertyPlaceholderConfigurer {
	
	private static Map<String, String> map;
	private static String projectRootPath = "";//暂时没有值
	public static String getProjectRootPath() {
		return projectRootPath;
	}

	public static void setProjectRootPath(String projectRootPath) {
		PropertiesUtils.projectRootPath = projectRootPath;
	}

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		map = new HashMap<String, String>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			map.put(keyStr, value);
		}
	}

	public static String getContextProperty(String name) {
		return map.get(name);
	}

}
