package com.finance.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bwdz.fpt.common.cache.JRedisClient;
import com.finance.entity.FinancialClassification;
import com.finance.entity.User;
import com.finance.service.FinancialClassificationService;
import com.finance.util.AppContextUtils;
import com.finance.util.KeysUtil;

public class FinancialClassificationFactory {

	private static FinancialClassificationFactory INSTANCE;
	
	private FinancialClassificationService service = (FinancialClassificationService) AppContextUtils.getBeanById("financialClassificationServiceImpl");//(FinancialClassificationService) context.getBean("financialClassificationServiceImpl");
	
	private JRedisClient client = (JRedisClient) AppContextUtils.getBeanById("redisClient");
	
	private FinancialClassificationFactory(){}
	
	public static FinancialClassificationFactory getInstance(){
		if(INSTANCE == null){
			synchronized (FinancialClassificationFactory.class) {
				if(INSTANCE == null){
					INSTANCE = new FinancialClassificationFactory();
				}
			}
		}
		
		return INSTANCE;
	}

	public void refresh(){
		try {
			FinancialClassification finance = new FinancialClassification();
			User user = (User) client.getCachObject(KeysUtil.LOGIN_USER_CACHE_KEY);
			finance.setUserId(user.getId());
			List<FinancialClassification> list = service.getAllFinances(finance);
			if(list != null && list.size() > 0){
				client.addCacheObject(KeysUtil.FINANCIALCLASSIFICATION_KEY, list, 7*24*60*60);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<FinancialClassification> getDatas(){
		List<FinancialClassification> list = null;
		try {
			list = (List<FinancialClassification>) client.getCachObject(KeysUtil.FINANCIALCLASSIFICATION_KEY);
			if(list == null || list.size() <= 0){
				refresh();
				list = (List<FinancialClassification>) client.getCachObject(KeysUtil.FINANCIALCLASSIFICATION_KEY);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	public List<Integer> getCodes(){
		List<Integer> result = new ArrayList<Integer>();
		
		List<FinancialClassification> list = getDatas();
		if(list != null){
			for(FinancialClassification fc : list){
				result.add(fc.getCode());
			}
		}
		
		return result;
	} 
	
	/**
	 * 获取不包含被逻辑删除了的
	 *
	 * @author Liugang
	 * @time 2017-3-2
	 * @return
	 */
	public Map<Integer, String> getCodeAndName(){
		Map<Integer, String> result = new HashMap<Integer, String>();
		List<FinancialClassification> list = getDatas();
		if(list != null){
			for(FinancialClassification fc : list){
				if(fc.getIsDeleted() <= 0){
					result.put(fc.getCode(), fc.getName());
				}
			}
		}
		return result;
	}
	
	/**
	 * 获取全部的编号与名称
	 *
	 * @author Liugang
	 * @time 2017-3-2
	 * @return
	 */
	public Map<Integer, String> getAllCodeAndName(){
		Map<Integer, String> result = new HashMap<Integer, String>();
		List<FinancialClassification> list = getDatas();
		if(list != null){
			for(FinancialClassification fc : list){
				result.put(fc.getCode(), fc.getName());
			}
		}
		return result;
	}
}
