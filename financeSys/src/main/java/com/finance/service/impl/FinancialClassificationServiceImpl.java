package com.finance.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.dao.FinancialClassificationDao;
import com.finance.entity.FinancialClassification;
import com.finance.entity.FinancialClassificationRecord;
import com.finance.service.FinancialClassificationService;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Service
public class FinancialClassificationServiceImpl implements FinancialClassificationService {

	@Autowired
	private FinancialClassificationDao dao;
	
	@Override
	public void saveFinance(FinancialClassification finance) {
		dao.addFinance(finance);
	}

	@Override
	public List<FinancialClassification> getAllFinances(FinancialClassification finance) {
		return dao.findAllFinances(finance);
	}

	@Override
	public FinancialClassification getFinanceByCode(
			FinancialClassification finance) {
		return dao.findFinanceByCode(finance);
	}

	@Override
	public void addFinanceRecord(FinancialClassificationRecord record) {
		dao.addFinanceRecord(record);
	}

	@Override
	public void currentAmountAddNewInvert(FinancialClassification finance) {
		dao.currentAmountAddNewInvert(finance);
	}

	@Override
	public List<FinancialClassificationRecord> getRecords(Map<String, Object> param, PageBounds pageBounds) {
		return dao.findRecords(param, pageBounds);
	}

	@Override
	public void removeRecordById(FinancialClassificationRecord record) {
		dao.deleteRecordById(record);
	}

	@Override
	public FinancialClassificationRecord getRecordById(FinancialClassificationRecord record) {
		return dao.getRecordById(record);
	}

	@Override
	public void modifyFinanceRecordById(FinancialClassificationRecord record) {
		dao.updateFinanceRecordById(record);
	}

	@Override
	public void modifyFinanceAmount(Map<String, Object> param) {
		dao.updateFinanceAmount(param);
	}

	@Override
	public FinancialClassification getFinanceById(FinancialClassification finance) {
		return dao.findFinanceById(finance);
	}

	@Override
	public void modifyFinance(FinancialClassification finance) {
		dao.updateFinance(finance);
	}

	@Override
	public void removeFinanceById(FinancialClassification finance) {
		dao.deleteFinanceById(finance);
	}

	@Override
	public void logicRemoveRecordById(FinancialClassificationRecord record) {
		dao.logicDeleteRecordById(record);
	}

	@Override
	public void logicRemoveFinanceById(FinancialClassification finance) {
		dao.logicDeleteFinanceById(finance);
	}

	@Override
	public List<FinancialClassification> getHasDeleteFinances(Map<String, Object> param, PageBounds pageBounds) {
		return dao.findHasDeleteFinances(param, pageBounds);
	}

	@Override
	public void logicRemoveRecordByCode(FinancialClassification finance) {
		dao.logicDeleteRecordByCode(finance);
	}

	@Override
	public void removeRecordByCode(FinancialClassification finance) {
		dao.deleteRecordByCode(finance);
	}
	
	
}
