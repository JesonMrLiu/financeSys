package com.finance.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.finance.bean.ResultBean;
import com.finance.entity.FinancialClassification;
import com.finance.entity.FinancialClassificationProfitOrLoss;
import com.finance.entity.FinancialClassificationRecord;
import com.finance.factory.FinancialClassificationFactory;
import com.finance.service.FinancialClassificationProfitOrLossService;
import com.finance.service.FinancialClassificationService;
import com.finance.util.DateUtils;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Controller
@RequestMapping("financialController.do")
public class FinancialController extends BaseController {
	
	@Autowired
	private FinancialClassificationService service;
	@Autowired
	private FinancialClassificationProfitOrLossService ykService;
	
	@RequestMapping(params = "method=fenlei")
	public String fenlei(){
		init();
		try {
			//1、查询出已有分类列表，按添加的时间降序
			FinancialClassification finance = new FinancialClassification();
			finance.setUserId(getCurrentUser().getId());
			List<FinancialClassification> list = service.getAllFinances(finance);
			request.setAttribute("dataList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "finance/flShow";
	}
	
	@RequestMapping(params = "method=getChartDatas")
	@ResponseBody
	public String getChartDatas(HttpServletRequest request){
		JSONObject result = new JSONObject();
		
		try {
			long aDay = 24*3600*1000L;
			int showDay = 30;//显示多少天的chart图
			Date now = new Date(); 
			String endDateStr = request.getParameter("endDate");
			if(StringUtils.isNotEmpty(endDateStr)){
				DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
				now = fmt.parse(endDateStr);
//				now = new Date(now.getTime() + aDay);
			}
			//查询每个分类的每日盈亏记录，查询一个月时间的
			List<Integer> codes = FinancialClassificationFactory.getInstance().getCodes();
   			result.put("codes", codes.toArray());
			result.put("codeWithName", JSONArray.toJSON(FinancialClassificationFactory.getInstance().getCodeAndName()));
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("endDate", now);
			Date firstDay = new Date(now.getTime() - showDay * aDay);
			
			param.put("startDate", firstDay);//开始时间取一个月之前的
			//当前用户ID
			param.put("userId", getCurrentUser().getId());
			//x轴时间
			JSONArray xTime = new JSONArray();//x轴的时间数据
			for(int i = showDay; i >= 0; i--){
				if(codes.size() > 0){
					xTime.add(now.getTime() - (aDay*i));
				}
			}
			//添加时间轴时间
			result.put("xTime", xTime);
			
			for(int code : codes){
				param.put("code", code);
				List<FinancialClassificationProfitOrLoss> ykList = ykService.getDatasInLastestOneMonth(param);
				JSONArray yData = new JSONArray();//y轴的盈亏实际数据
				if(ykList == null || ykList.size() <= 0){
					for(int i = 0; i < showDay; i++){
						yData.add(0);
					}
				} else {
					FinancialClassificationProfitOrLoss first = ykList.get(0);//第一条记录
					FinancialClassificationProfitOrLoss last = ykList.get(ykList.size()-1);//取最后一条记录
					//计算第一天记录与当前时间往前他推一个月相差天数，少的天数补0
					long suffix = first.getProfitLossDate().getTime() - firstDay.getTime();//calendar.getTimeInMillis();
					//获得相隔天数
					long suffixDay = suffix / aDay;
					//差个天数，，补0
					for(int i = 0; i < ((int)suffixDay); i++){
						yData.add(0);
					}
					//将有数据的天数数据填入数组中
					Date preDate = null;
					for(FinancialClassificationProfitOrLoss pol : ykList){ 
						if(preDate != null){
							//判断上一个记录时间与当前记录时间比较
							Date current = pol.getProfitLossDate();//当前记录时间
							Date preNextDay = new Date(preDate.getTime() + aDay);//上一个记录时间的下一天
							//1）当前记录时间大于上一个记录时间的下一天时间，
							//2）并且两个记录的时间不是同一天
							boolean loop = true;
							while(loop){
								if((current.getTime() - preDate.getTime() > 0) 
										&& !DateUtils.compareIsSameDay(preNextDay, pol.getProfitLossDate())){
									yData.add(0);
								} else {
									loop = false;
								}
								preDate = preNextDay;
							}
							yData.add(pol.getProfitOrLossAmount());
						} else {
							yData.add(pol.getProfitOrLossAmount());
							preDate = pol.getProfitLossDate();
						}
					 }
					//在最后一条记录之后，没有的补零
					suffix = now.getTime() - last.getProfitLossDate().getTime();
					suffixDay = suffix / aDay;
					for(int i = 0; i < ((int)suffixDay); i++){
						yData.add(0);
					}
				}
				result.put("yData-"+code, yData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result.toJSONString();
	} 
	
	@RequestMapping(params = "method=getFenLeiById")
	@ResponseBody
	public String getFenLeiById(HttpServletRequest request){
		JSONObject result = new JSONObject();
		this.request = request;
		try {
			FinancialClassification finance = new FinancialClassification();
			finance.setId(intParam("id"));
			finance = service.getFinanceById(finance);
			
			result.put("suc", true);
			result.put("data", JSONObject.toJSON(finance));
		} catch (Exception e) {
			e.printStackTrace();
			result.put("suc", false);
			result.put("info", "抱歉，出现未知异常！");
		}
		return result.toJSONString();
	}
	
	@RequestMapping(params = "method=doFenLei")
	@ResponseBody
	public ResultBean doAddFenLei(HttpServletRequest request){
		try {
			this.request = request;
			int id = intParam("id");
			FinancialClassification finance = new FinancialClassification();
			finance.setCode(intParam("code"));
			finance.setName(param("name"));
			finance.setUserId(getCurrentUser().getId());
			finance.setFirstAmount(decimalParam("firstAmount"));
			//1、首先要验证理财分类代码是否已经添加过了
			FinancialClassification temp = service.getFinanceByCode(finance);
			if(temp != null && temp.getId() != id){
				String info = "抱歉，分类编号【编号："+finance.getCode()+"】的理财分类已经添加过，请确认重新添加！！";
				if(finance.getCode() < 0){
					info = "抱歉，分类编号【"+Math.abs(finance.getCode())+"】在已经存在，并在回收站中，如果需要新添加，请将回收站中理财分类清除！！";
				}
				res = new ResultBean(info, false, "");
				return res;
			}
			
			//2、如果没有添加过，则在进行相应操作
			if(id > 0){
				finance.setId(id);
				service.modifyFinance(finance);
			} else {
				finance.setCurrentTotalAmount(finance.getFirstAmount());
				service.saveFinance(finance);
			}
			
			res = new ResultBean("恭喜您，操作成功！！", true, "");
			FinancialClassificationFactory.getInstance().refresh();
		} catch (Exception e) {
			e.printStackTrace();
			res = new ResultBean("抱歉，出现异常！！", false, "");
		}
		return res;
	}
	
	@RequestMapping(params = "method=deleteFlById")
	@ResponseBody
	public String deleteFlById(String id){
		JSONObject result = new JSONObject();
		try {
			FinancialClassification finance = new FinancialClassification();
			finance.setId(Integer.valueOf(id));
			//获取理财分类记录
			finance = service.getFinanceById(finance);
			if(finance != null){
				//逻辑删除理财分类记录
				service.logicRemoveFinanceById(finance);
				
				//然后逻辑删除所有下属理财分类的体现、追投；盈亏记录
				//1、逻辑删除所有提现、追投的记录
				service.logicRemoveRecordByCode(finance);
				
				//2、逻辑删除所有盈亏记录
				ykService.logicRemoveProfitlossByCode(finance);
			}
			
			result.put("suc", true);
			FinancialClassificationFactory.getInstance().refresh();
		} catch (Exception e) {
			e.printStackTrace();
			result.put("suc", false);
			result.put("info", "抱歉，删除出现异常！");
		}
		return result.toJSONString();
	}
	
	@RequestMapping(params = "method=addFinanceRecord")
	@ResponseBody
	public ResultBean addFinanceRecord(HttpServletRequest request){
		try {
			this.request = request;
			FinancialClassificationRecord record = new FinancialClassificationRecord();
			record.setCode(intParam("code"));
			int type = intParam("type");
			BigDecimal amount = decimalParam("amount");
			BigDecimal realAmount = type == 2 ? BigDecimal.ZERO.subtract(amount) : amount; //1-追投；2-提现
			record.setAmount(realAmount);
			record.setInvertTime(new Date());
			record.setUserId(user.getId());
			//1、如果是提现操作，那么需要首先验证提现金额要小于等于当前的总金额
			if(type == 2){
				FinancialClassification finance = new FinancialClassification();
				finance.setCode(record.getCode());
				finance.setUserId(getCurrentUser().getId());
				finance = service.getFinanceByCode(finance);
				//1.1、判断是否有可否提现的账户
				if(finance == null){
					return new ResultBean("抱歉，没有可以提现的账户！！", false, "");
				}
				//1.2、比较当前总金额与提现金额
				if(finance.getCurrentTotalAmount().compareTo(amount) < 0){
					return new ResultBean("抱歉，当前总额【"+finance.getCurrentTotalAmount()+"】不足以提现【"+amount+"】。", false, "");
				}
			}
			//2、保存记录
			service.addFinanceRecord(record);
			
			//3、更新理财分类的相关金额
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("type", type);
			param.put("code", record.getCode());
			param.put("amount", realAmount);
			param.put("userId", getCurrentUser().getId());
			service.modifyFinanceAmount(param);

			res = new ResultBean("恭喜您，添加投入成功！！", true, "");
		} catch (Exception e) {
			e.printStackTrace();
			res = new ResultBean("抱歉，出现异常！！", false, "");
		}
		return res;
	}
	
	/**
	 * 盈亏统计
	 *
	 * @author Liugang
	 * @time 2017-2-23
	 * @return
	 */
	@RequestMapping(params = "method=profitLoss")
	public String profitLoss(){
		
		return "finance/profitLoss";
	}
	
	/**
	 * 盈亏统计表格显示
	 *
	 * @author Liugang
	 * @time 2017-2-27
	 * @return
	 */
	@RequestMapping(params = "method=profitLoss_tb")
	public String profitLoss_table(){
		try {
			init();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userId", getCurrentUser().getId());
			String _page_ = request.getParameter("page");
			if(StringUtils.isNotEmpty(_page_)){
				page = Integer.parseInt(_page_);
			}
			//理财分类
			String lcfl = param("code");
			if(StringUtils.isNotEmpty(lcfl)){
				param.put("code", lcfl);
			}
			Date start = dateParam("startdate");
			Date end = dateParam("enddate");
			if(start != null){
				param.put("startDate", start);
			}
			if(end != null){
				param.put("endDate", end);
			}
			List<FinancialClassificationProfitOrLoss> list = ykService.getProfitLoss(param, new PageBounds(page, size, Order.formString("profitLossDate.DESC")));
			request.setAttribute("dataList", list);
			initPage(list);
			//理财分类<code, name>
			request.setAttribute("lcfl", FinancialClassificationFactory.getInstance().getCodeAndName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "finance/profitLoss";
	}
	
	@RequestMapping(params = "method=doProfitLoss")
	@ResponseBody
	public String doProfitLoss(HttpServletRequest request){
		JSONObject result = new JSONObject();
		this.request = request;
		try {
			int id = intParam("id");
			int code = intParam("md-code");
			BigDecimal amount = decimalParam("amount");
			Date ysrq = dateParam("ysrq");
			FinancialClassificationProfitOrLoss profit = new FinancialClassificationProfitOrLoss();
			profit.setCode(code); 
			profit.setProfitOrLossAmount(amount);
			profit.setProfitLossDate(ysrq);
			profit.setUserId(getCurrentUser().getId());
			//根据盈亏时间，来查看是否已经添加过该天的盈亏记录
			Map<String, Object> p = new HashMap<String, Object>();
			p.put("day", DateUtils.dateToStr(ysrq));
			p.put("code", code);
			p.put("userId", profit.getUserId());
			FinancialClassificationProfitOrLoss _temp_ = ykService.getProfitLossByDay(p);
			if(_temp_ != null){
				if((id > 0 && _temp_.getId() != id) || id <= 0){
					result.put("suc", false);
					result.put("info", "抱歉，同一个理财分类，一天只能添加一次，盈亏时间【"+(DateUtils.dateToStr(ysrq, "yyyy-MM-dd HH:mm:ss"))+"】已经添加过！");
					return result.toJSONString();
				} 
			}
			if(id > 0){//修改
				profit.setId(id);
				ykService.modifyProfitLoss(profit);
			} else {//增加
				ykService.addOneProfitLoss(profit);
			}
			
			//操作成功后，修改理财分类中相应金额
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("type", 3);
			param.put("code", code);
			param.put("userId", getCurrentUser().getId());
			BigDecimal subAmount = amount.subtract(decimalParam("oldAmount"));
			param.put("amount", subAmount);
			service.modifyFinanceAmount(param);
			
			result.put("suc", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("suc", false);
			result.put("info", "抱歉，操作异常！！");
		}
		return result.toJSONString();
	}
	
	@RequestMapping(params = "method=getProfitLossById")
	@ResponseBody
	public String getProfitLossById(String id){
		JSONObject result = new JSONObject();
		try {
			FinancialClassificationProfitOrLoss profit = new FinancialClassificationProfitOrLoss();
			profit.setId(Integer.valueOf(id));
			profit = ykService.getProfitLossById(profit);
			result.put("suc", true);
			result.put("data", JSONObject.toJSON(profit));
		} catch (Exception e) {
			e.printStackTrace();
			result.put("suc", false);
			result.put("info", "抱歉，查询理财分类异常！");
		}
		
		return result.toJSONString();
	}
	
	/**
	 * 逻辑删除盈亏记录
	 *
	 * @author Liugang
	 * @time 2017-3-1
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "method=deleteById")
	@ResponseBody
	public String deleteById(String id){
		JSONObject result = new JSONObject();
		try {
			FinancialClassificationProfitOrLoss profit = new FinancialClassificationProfitOrLoss();
			profit.setId(Integer.valueOf(id));
			//根据ID获取盈亏记录实例
			profit = ykService.getProfitLossById(profit);
			if(profit != null){
				//逻辑删除盈亏记录
				ykService.logicRemoveById(profit);
				
				//删除成功之后，在更新理财分类中的相关金额
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("code", profit.getCode());
				param.put("userId", profit.getUserId());
				param.put("type", 3);
				param.put("amount", BigDecimal.ZERO.subtract(profit.getProfitOrLossAmount()));
				service.modifyFinanceAmount(param);
			}
			
			
			result.put("suc", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("suc", false);
			result.put("info", "抱歉，删除出现异常！！");
		}
		return result.toJSONString();
	}
	
	@RequestMapping(params = "method=withdrawRecord")
	public String withdrawRecord(){
		init();
		try {
			String _page_ = param("page");
			if(StringUtils.isNotEmpty(_page_)){
				page = Integer.valueOf(_page_);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userId", getCurrentUser().getId());
			//理财分类
			String lcfl = param("code");
			if(StringUtils.isNotEmpty(lcfl)){
				param.put("code", lcfl);
			}
			Date start = dateParam("startdate");
			Date end = dateParam("enddate");
			if(start != null){
				param.put("startDate", start);
			}
			if(end != null){
				param.put("endDate", end);
			}
			List<FinancialClassificationRecord> list = service.getRecords(param, new PageBounds(page, size, Order.formString("invertTime.DESC")));
			request.setAttribute("dataList", list);
			initPage(list);
			//理财分类<code, name>
			request.setAttribute("lcfl", FinancialClassificationFactory.getInstance().getCodeAndName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "finance/withdraws";
	}
	
	@RequestMapping(params = "method=getWithdrawInvestById")
	@ResponseBody
	public String getWithdrawInvestById(String id){
		JSONObject result = new JSONObject();
		try {
			FinancialClassificationRecord record = new FinancialClassificationRecord();
			record.setId(Integer.valueOf(id));
			record = service.getRecordById(record);
			
			result.put("suc", true);
			result.put("data", JSONObject.toJSON(record));
		} catch (Exception e) {
			e.printStackTrace();
			result.put("suc", false);
			result.put("info", "抱歉，获取提现投入记录出现异常！！");
		}
		return result.toJSONString();
	}
	
	@RequestMapping(params = "method=doWithdrawInvest")
	@ResponseBody
	public String doWithdrawInvest(HttpServletRequest request){
		JSONObject result = new JSONObject();
		this.request = request;
		try {
			FinancialClassificationRecord record = new FinancialClassificationRecord();
			int type = intParam("md-type");//提现或追投，1-追投；2-提现
			BigDecimal amount = decimalParam("amount");
			amount = type  == 1 ? amount : BigDecimal.ZERO.subtract(amount);
			record.setAmount(amount);
			record.setCode(intParam("code"));
			record.setInvertTime(dateParam("investTime"));
			record.setUserId(getCurrentUser().getId());
			//1、如果是提现操作，那么需要首先验证提现金额要小于等于当前的总金额
			if(type == 2){
				FinancialClassification finance = new FinancialClassification();
				finance.setCode(record.getCode());
				finance.setUserId(getCurrentUser().getId());
				finance = service.getFinanceByCode(finance);
				//1.1、判断是否有可否提现的账户
				if(finance == null){
					result.put("suc", false);
					result.put("info", "抱歉，没有可以提现的账户！！");
					return result.toJSONString();
				}
				//1.2、比较当前总金额与提现金额
				if(finance.getCurrentTotalAmount().compareTo(amount) < 0){
					result.put("suc", false);
					result.put("info", "抱歉，当前总额【"+finance.getCurrentTotalAmount()+"】不足以提现【"+amount+"】。");
					return result.toJSONString();
				}
			}
			
			int id = intParam("id");
			if(id > 0){
				record.setId(id);
				service.modifyFinanceRecordById(record);
			} else {
				service.addFinanceRecord(record);
			}
			
			//新增或修改成功之后，更改理财分类中相关金额
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("code", intParam("code"));
			param.put("type", type);//类别，提现或追投，1-追投；2-提现
			BigDecimal oldAmount = decimalParam("oldAmount");
			BigDecimal subAmount = amount.subtract(oldAmount);//新旧金额的差值
			param.put("amount", subAmount);
			param.put("userId", getCurrentUser().getId());
			service.modifyFinanceAmount(param);
			
			result.put("suc", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("suc", false);
			result.put("info", "抱歉，获取提现投入记录出现异常！！");
		}
		return result.toJSONString();
	}
	
	@RequestMapping(params = "method=deleteRecordById")
	@ResponseBody
	public String deleteRecordById(String id){
		JSONObject result = new JSONObject();
		try {
			FinancialClassificationRecord record = new FinancialClassificationRecord();
			record.setId(Integer.valueOf(id));
			//根据ID获取该记录实例
			record = service.getRecordById(record);
			if(record != null){
				//逻辑删除记录
				service.logicRemoveRecordById(record);
				
				//删除成功之后，在相应理财分类金额更新
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("code", record.getCode());
				param.put("userId", record.getUserId());
				param.put("amount", BigDecimal.ZERO.subtract(record.getAmount()));
				param.put("type", record.getAmount().compareTo(BigDecimal.ZERO) > 0 ? 1 : 2);
				service.modifyFinanceAmount(param);
			}
			
			result.put("suc", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("suc", false);
			result.put("info", "抱歉，删除出现异常！！");
		}
		return result.toJSONString();
	}
}
