package com.finance.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.finance.entity.FinancialClassification;
import com.finance.entity.FinancialClassificationProfitOrLoss;
import com.finance.entity.FinancialClassificationRecord;
import com.finance.factory.FinancialClassificationFactory;
import com.finance.service.FinancialClassificationProfitOrLossService;
import com.finance.service.FinancialClassificationService;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Controller
@RequestMapping("dataController.do")
public class DataController extends BaseController {
	
	@Autowired
	private FinancialClassificationProfitOrLossService ykService;
	@Autowired
	private FinancialClassificationService service;
	
	@RequestMapping(params = "method=delFenlei")
	public String delFenlei(){
		init();
		try {
			String _page_ = param("page");
			if(StringUtils.isNotEmpty(_page_)){
				page = Integer.valueOf(_page_);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userId", getCurrentUser().getId());
			List<FinancialClassification> list = service.getHasDeleteFinances(param, new PageBounds(page, size, Order.formString("deleteTime.DESC")));
			request.setAttribute("dataList", list);
			initPage(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "dataSys/delFenlei";
	}
	
	@RequestMapping(params = "method=deleteFenleiById")
	@ResponseBody
	public String deleteFenleiById(String id){
		JSONObject result = new JSONObject();
		try {
			FinancialClassification finance = new FinancialClassification();
			finance.setId(Integer.valueOf(id));
			finance = service.getFinanceById(finance);
			if(null != finance){
				//彻底删除
				service.removeFinanceById(finance);
				//删除提现追投记录
				service.removeRecordByCode(finance);
				//删除盈亏记录
				ykService.removeByCode(finance);
			}
			
			result.put("suc", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("suc", false);
			result.put("info", "抱歉，删除出现异常！");
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
			service.removeRecordById(record);
			
			result.put("suc", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("suc", false);
			result.put("info", "抱歉，删除出现异常！");
		}
		return result.toJSONString();
	}
	
	@RequestMapping(params = "method=deleteProfitLossById")
	@ResponseBody
	public String deleteProfitLossById(String id){
		JSONObject result = new JSONObject();
		try {
			FinancialClassificationProfitOrLoss profit = new FinancialClassificationProfitOrLoss();
			profit.setId(Integer.valueOf(id));
			ykService.removeById(profit);

			result.put("suc", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("suc", false);
			result.put("info", "抱歉，删除出现异常！");
		}
		return result.toJSONString();
	}
	
	@RequestMapping(params = "method=delWithdrawInvest")
	public String delWithdrawInvest(){
		init();
		try {
			String _page_ = param("page");
			if(StringUtils.isNotEmpty(_page_)){
				page = Integer.valueOf(_page_);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userId", getCurrentUser().getId());
			param.put("isDeleted", 1);
			//理财分类
			String lcfl = param("code");
			if(StringUtils.isNotEmpty(lcfl)){
				param.put("code", Integer.valueOf(lcfl));
			}
			Date start = dateParam("startdate");
			Date end = dateParam("enddate");
			if(start != null){
				param.put("startDate", start);
			}
			if(end != null){
				param.put("endDate", end);
			}
			List<FinancialClassificationRecord> list = service.getRecords(param, new PageBounds(page, size, Order.formString("deleteTime.DESC")));
			request.setAttribute("dataList", list);
			initPage(list);
			//理财分类<code, name>
			request.setAttribute("lcfl", FinancialClassificationFactory.getInstance().getAllCodeAndName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "dataSys/delWithdrawInvest";
	}
	
	@RequestMapping(params = "method=delProfitLoss")
	public String delProfitLoss(){
		init();
		try {
			String _page_ = param("page");
			if(StringUtils.isNotEmpty(_page_)){
				page = Integer.valueOf(_page_);
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userId", getCurrentUser().getId());
			param.put("isDeleted", 1);
			//理财分类
			String lcfl = param("code");
			if(StringUtils.isNotEmpty(lcfl)){
				param.put("code", Integer.valueOf(lcfl));
			}
			Date start = dateParam("startdate");
			Date end = dateParam("enddate");
			if(start != null){
				param.put("startDate", start);
			}
			if(end != null){
				param.put("endDate", end);
			}
			List<FinancialClassificationProfitOrLoss> list = ykService.getProfitLoss(param, new PageBounds(page, size, Order.formString("DeleteTime.DESC")));
			request.setAttribute("dataList", list);
			initPage(list);
			//理财分类<code, name>
			request.setAttribute("lcfl", FinancialClassificationFactory.getInstance().getAllCodeAndName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "dataSys/delProfitLoss";
	}
}
