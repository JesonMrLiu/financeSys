package com.finance.bean.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 月份
 *
 * @author Liugang
 * @time 2017-3-3
 */
public enum MonthEnum {

	JANUARY(1, "一月"),
	FEBRUARY(2, "二月"),
	MARCH(3, "三月"),
	APRIL(4, "四月"),
	MAY(5, "五月"),
	JUNE(6, "六月"),
	JULY(7, "七月"),
	AUGUST(8, "八月"),
	SEPTEMBER(9, "九月"),
	OCTOBER(10, "十月"),
	NOVEMBER(11, "十一月"),
	DECEMBER(12, "十二月")
	;
	private MonthEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}
	private int code;//月份数字表示
	private String name;//月份中文名称
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取月份Map集合
	 *
	 * @author Liugang
	 * @time 2017-3-3
	 * @return
	 */
	public static Map<Integer, String> getMonths(){
		Map<Integer, String> result = new LinkedHashMap<Integer, String>();
		MonthEnum[] months = MonthEnum.values();
		for(MonthEnum m : months){
			result.put(m.getCode(), m.getName());
		}
		return result;
	}
	
	public static void main(String[] args) {
		MonthEnum[] months = MonthEnum.values();
		for(MonthEnum m : months){
			System.out.println("code:"+m.getCode()+"\tname:"+m.getName());
		}
	}
}
