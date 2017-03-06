package com.finance.service;

import java.util.List;
import java.util.Map;

import com.finance.entity.FinancialClassification;
import com.finance.entity.FinancialClassificationRecord;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface FinancialClassificationService {

	public void saveFinance(FinancialClassification finance);
	
	/**
	 * 更新理财分类记录
	 *
	 * @author Liugang
	 * @time 2017-3-1
	 * @param finance
	 */
	public void modifyFinance(FinancialClassification finance);
	
	/**
	 * 删除理财分类记录
	 *
	 * @author Liugang
	 * @time 2017-3-1
	 * @param finance
	 */
	public void removeFinanceById(FinancialClassification finance);
	
	/**
	 * 查询出所有的理财分类记录
	 *
	 * @author Liugang
	 * @time 2017-2-20
	 * @param finance
	 * @return
	 */
	public List<FinancialClassification> getAllFinances(FinancialClassification finance);
	
	/**
	 * 获取逻辑删除的理财分类记录
	 *
	 * @author Liugang
	 * @time 2017-3-1
	 * @param param
	 * @return
	 */
	public List<FinancialClassification> getHasDeleteFinances(Map<String, Object> param, PageBounds pageBounds);
	
	/**
	 * 根据分类编号获取记录
	 *
	 * @author Liugang
	 * @time 2017-2-22
	 * @param finance
	 * @return
	 */
	public FinancialClassification getFinanceByCode(FinancialClassification finance);
	
	/**
	 * 增加理财分类投入资金记录
	 *
	 * @author Liugang
	 * @time 2017-2-23
	 * @param record
	 */
	public void addFinanceRecord(FinancialClassificationRecord record);
	
	/**
	 * 新增投入资金时，更新理财分类的当前总资金
	 *
	 * @author Liugang
	 * @time 2017-2-23
	 * @param finance
	 */
	public void currentAmountAddNewInvert(FinancialClassification finance);
	
	/**
	 * 查询理财分类记录数据
	 *
	 * @author Liugang
	 * @time 2017-2-27
	 * @param param
	 * @return
	 */
	public List<FinancialClassificationRecord> getRecords(Map<String, Object> param, PageBounds pageBounds);
	
	/**
	 * 删除理财分类提现或入账记录
	 *
	 * @author Liugang
	 * @time 2017-2-27
	 * @param record
	 */
	public void removeRecordById(FinancialClassificationRecord record);
	
	/**
	 * 根据Code删除
	 *
	 * @author Liugang
	 * @time 2017-3-2
	 * @param finance
	 */
	public void removeRecordByCode(FinancialClassification finance);
	
	/**
	 * 根据ID获取理财分类提现追加投入记录数据
	 *
	 * @author Liugang
	 * @time 2017-2-28
	 * @param record
	 * @return
	 */
	public FinancialClassificationRecord getRecordById(FinancialClassificationRecord record);
	
	/**
	 * 更新理财分类提现与追投记录
	 *
	 * @author Liugang
	 * @time 2017-2-28
	 * @param record
	 */
	public void modifyFinanceRecordById(FinancialClassificationRecord record);
	
	/**
	 * 理财分类更改相关资金
	 * <p>1、追投（amount、oldAmount为正数）
			<br>1）新增：当前总金额+amount, 
			<br>2）修改：当前总金额+amount-oldAmount,
	 * <p>2、提现（amount、oldAmount为负数）
			<br>1）新增：当前总金额+amount, 本月提现+amount, 提现总额+amount
			<br>2）修改：当前总金额+amount-oldAmount, 本月提现+amount-oldAmount, 提现总额+amount-oldAmount
	 * <p>3、盈利(amount、oldAmount为正数)
			<br>1）新增：当前总金额+amount，昨日盈亏+amount，盈亏总计+amount
			<br>2）修改：当前总金额+amount-oldAmount，昨日盈亏+amount-oldAmount, 盈亏总计+amount-oldAmount
	 * <p>4、亏损（amount、oldAmount为负数）
			<br>1）新增：当前总金额+amount，昨日盈亏+amount，盈亏总计+amount
			<br>2）修改：当前总金额+amount-oldAmount，昨日盈亏+amount-oldAmount, 盈亏总计+amount-oldAmount
	 *
	 * @author Liugang
	 * @time 2017-2-28
	 * @param param
	 */
	public void modifyFinanceAmount(Map<String, Object> param);
	
	/**
	 * 根据ID获取理财分类
	 *
	 * @author Liugang
	 * @time 2017-2-28
	 * @param finance
	 * @return
	 */
	public FinancialClassification getFinanceById(FinancialClassification finance);

	/**
	 * 逻辑删除理财分类提现或入账记录
	 *
	 * @author Liugang
	 * @time 2017-3-1
	 * @param record
	 */
	public void logicRemoveRecordById(FinancialClassificationRecord record);
	
	/**
	 * 逻辑删除理财分类项
	 *
	 * @author Liugang
	 * @time 2017-3-1
	 * @param finance
	 */
	public void logicRemoveFinanceById(FinancialClassification finance);
	
	/**
	 * 逻辑删除理财分类编号为#{code}的提现、追投记录
	 *
	 * @author Liugang
	 * @time 2017-3-1
	 * @param finance
	 */
	public void logicRemoveRecordByCode(FinancialClassification finance);
	
}
