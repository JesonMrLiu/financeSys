package com.finance.util;

import org.springframework.web.context.ContextLoader;

import com.bwdz.fpt.common.cache.JRedisClient;

public class RedisClientUtils {

	private static JRedisClient client;
	
	public static void addCacheObject(String key, Object obj, int seconds) throws Exception{
		client = (JRedisClient) ContextLoader.getCurrentWebApplicationContext().getBean("redisClient");
		client.addCacheObject(key, obj, seconds);
	}
	
	
	public static Object getCacheObject(String key) throws Exception{
		client = (JRedisClient) ContextLoader.getCurrentWebApplicationContext().getBean("redisClient");
		return client.getCachObject(key);
	}
}
