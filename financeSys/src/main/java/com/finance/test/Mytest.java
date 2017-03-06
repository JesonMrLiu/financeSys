package com.finance.test;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.finance.entity.FinancialClassificationProfitOrLoss;
import com.finance.service.FinancialClassificationProfitOrLossService;
import com.finance.util.AppContextUtils;

public class Mytest {

	public static void main(String[] args) {
		/*FinancialClassificationProfitOrLossService f = (FinancialClassificationProfitOrLossService) AppContextUtils.getBeanById("financialClassificationProfitOrLossServiceImpl");
		long aDay = 24 * 3600 * 1000;
		Date now = new Date();
		// 两个月前
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("start time -- "+ fmt.format(now));
		List<FinancialClassificationProfitOrLoss> list = new ArrayList<FinancialClassificationProfitOrLoss>();
		for (int k = 1; k < 4; k++) {
			for (int i = 0; i < 60; i++) {
				FinancialClassificationProfitOrLoss profit = new FinancialClassificationProfitOrLoss();
				Date t = new Date(now.getTime() - i * aDay);
				profit.setCreateDate(t);
				profit.setCode(k);
				profit.setUserId(1);
				profit.setProfitOrLossAmount(getAmount());
				list.add(profit);
			}
		}
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("datas", list);
		f.addMorePriftLoss(p);
		System.out.println("end time -- "+fmt.format(new Date()));*/
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("1", "one");
//		map.put("2", "two");
//		map.put("3", "three");
//		String result = JSONArray.toJSONString(map);
//		System.out.println("result-"+result);
		List<Integer> l = new ArrayList<Integer>();
		l.add(1);
		l.add(2);
		l.add(3);
		System.out.println();
	}

	
	public static BigDecimal getAmount() {
		double unit = Math.random() * 10;
		double amount = Math.random() * 2d * 10;
		DecimalFormat fmt = new DecimalFormat("#0.0");
		String result = "";
		if (unit < 3d) {
			result = "-" + fmt.format(amount);
		} else {
			result = fmt.format(amount);
		}
		return BigDecimal.valueOf(Double.parseDouble(result));
	}
}
