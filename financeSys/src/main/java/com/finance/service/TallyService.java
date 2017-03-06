package com.finance.service;

import java.util.List;
import java.util.Map;

import com.finance.entity.Tally;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface TallyService {

	/**
	 * 查询账目信息
	 *
	 * @author Liugang
	 * @time 2017-3-3
	 * @param param
	 * @return
	 */
	public List<Tally> getTallys(Map<String, Object> param, PageBounds pageBounds);
	
	/**
	 * 添加一条账单记录
	 *
	 * @author Liugang
	 * @time 2017-3-3
	 * @param tally
	 */
	public void addOneTally(Tally tally);
	
	/**
	 * 添加多条账单
	 *
	 * @author Liugang
	 * @time 2017-3-3
	 * @param datas
	 */
	public void addMoreTallys(Map<String, List<Tally>> datas);
	
	/**
	 * 逻辑删除
	 *
	 * @author Liugang
	 * @time 2017-3-3
	 * @param tally
	 */
	public void logicDeleteById(Tally tally);
	
	/**
	 * 物理删除，真实删除
	 *
	 * @author Liugang
	 * @time 2017-3-3
	 * @param tally
	 */
	public void deleteById(Tally tally);
	
	/**
	 * 更新账单记录
	 *
	 * @author Liugang
	 * @time 2017-3-3
	 * @param tally
	 */
	public void updateTallyById(Tally tally);
	
	/**
	 * 根据ID获取账单记录
	 *
	 * @author Liugang
	 * @time 2017-3-6
	 * @param tally
	 * @return
	 */
	public Tally getTallyById(Tally tally);
	
}
