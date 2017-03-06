package com.finance.service;

import java.util.List;
import java.util.Map;

import com.finance.entity.FinancialClassification;
import com.finance.entity.FinancialClassificationProfitOrLoss;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface FinancialClassificationProfitOrLossService {

	/**
	 * 查询某个理财分类的一个月的盈亏数据
	 *
	 * @author Liugang
	 * @time 2017-2-20
	 * @param param
	 * @return
	 */
	public List<FinancialClassificationProfitOrLoss> getDatasInLastestOneMonth(Map<String, Object> param);
	
	/**
	 * 添加多条盈亏记录
	 *
	 * @author Liugang
	 * @time 2017-2-23
	 * @param map
	 */
	public void addMorePriftLoss(Map<String, Object> map);
	
	/**
	 * 查询理财分类盈亏记录数据
	 *
	 * @author Liugang
	 * @time 2017-2-27
	 * @param map
	 * @return
	 */
	public List<FinancialClassificationProfitOrLoss> getProfitLoss(Map<String, Object> map, PageBounds pageBounds);
	
	/**
	 * 添加盈亏记录
	 *
	 * @author Liugang
	 * @time 2017-2-27
	 * @param profit
	 */
	public void addOneProfitLoss(FinancialClassificationProfitOrLoss profit);
	
	/**
	 * 更新盈亏记录
	 *
	 * @author Liugang
	 * @time 2017-2-27
	 * @param profit
	 */
	public void modifyProfitLoss(FinancialClassificationProfitOrLoss profit);
	
	/**
	 * 根据ID获取盈亏记录
	 *
	 * @author Liugang
	 * @time 2017-2-27
	 * @param profit
	 * @return
	 */
	public FinancialClassificationProfitOrLoss getProfitLossById(FinancialClassificationProfitOrLoss profit);
	
	/**
	 * 删除盈亏记录
	 *
	 * @author Liugang
	 * @time 2017-2-27
	 * @param profit
	 */
	public void removeById(FinancialClassificationProfitOrLoss profit);
	
	/**
	 * 根据Code删除所有相关的记录
	 *
	 * @author Liugang
	 * @time 2017-3-2
	 * @param finance
	 */
	public void removeByCode(FinancialClassification finance);
	
	/**
	 * 逻辑删除盈亏记录
	 *
	 * @author Liugang
	 * @time 2017-3-1
	 * @param profit
	 */
	public void logicRemoveById(FinancialClassificationProfitOrLoss profit);
	
	/**
	 * 逻辑删除盈亏记录，根据code和userId
	 *
	 * @author Liugang
	 * @time 2017-3-1
	 * @param finance
	 */
	public void logicRemoveProfitlossByCode(FinancialClassification finance);
	
	/**
	 * 查询某分类编码下某一天的记录
	 *
	 * @author Liugang
	 * @time 2017-3-2
	 * @param param
	 * @return
	 */
	public FinancialClassificationProfitOrLoss getProfitLossByDay(Map<String, Object> param);
}
