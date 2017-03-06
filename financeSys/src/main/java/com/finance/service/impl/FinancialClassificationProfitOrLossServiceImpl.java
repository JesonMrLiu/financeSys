package com.finance.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.dao.FinancialClassificationProfitOrLossDao;
import com.finance.entity.FinancialClassification;
import com.finance.entity.FinancialClassificationProfitOrLoss;
import com.finance.service.FinancialClassificationProfitOrLossService;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Service
public class FinancialClassificationProfitOrLossServiceImpl implements FinancialClassificationProfitOrLossService {

	@Autowired
	private FinancialClassificationProfitOrLossDao dao;
	
	@Override
	public List<FinancialClassificationProfitOrLoss> getDatasInLastestOneMonth(Map<String, Object> param) {
		return dao.findDatasInLastestOneMonth(param);
	}

	@Override
	public void addMorePriftLoss(Map<String, Object> map) {
		dao.addMorePriftLoss(map);
	}

	@Override
	public List<FinancialClassificationProfitOrLoss> getProfitLoss(Map<String, Object> map, PageBounds pageBounds) {
		return dao.findProfitLoss(map, pageBounds);
	}

	@Override
	public void addOneProfitLoss(FinancialClassificationProfitOrLoss profit) {
		dao.addOneProfitLoss(profit);
	}

	@Override
	public void modifyProfitLoss(FinancialClassificationProfitOrLoss profit) {
		dao.udpateProfitLoss(profit);
	}

	@Override
	public FinancialClassificationProfitOrLoss getProfitLossById(
			FinancialClassificationProfitOrLoss profit) {
		return dao.findProfitLossById(profit);
	}

	@Override
	public void removeById(FinancialClassificationProfitOrLoss finance) {
		dao.deleteById(finance);
	}

	@Override
	public void logicRemoveById(FinancialClassificationProfitOrLoss profit) {
		dao.logicDeleteById(profit);
	}

	@Override
	public void logicRemoveProfitlossByCode(FinancialClassification finance) {
		dao.logicDeleteProfitlossByCode(finance);
	}

	@Override
	public void removeByCode(FinancialClassification finance) {
		dao.deleteByCode(finance);
	}

	@Override
	public FinancialClassificationProfitOrLoss getProfitLossByDay(Map<String, Object> param) {
		return dao.findProfitLossByDay(param);
	}

}
