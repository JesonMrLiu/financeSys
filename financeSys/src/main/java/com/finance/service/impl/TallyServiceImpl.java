package com.finance.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.dao.TallyDao;
import com.finance.entity.Tally;
import com.finance.service.TallyService;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Service
public class TallyServiceImpl implements TallyService {
	
	@Autowired
	private TallyDao dao;
	
	@Override
	public List<Tally> getTallys(Map<String, Object> param, PageBounds pageBounds) {
		return dao.getTallys(param, pageBounds);
	}

	@Override
	public void addOneTally(Tally tally) {
		dao.addOneTally(tally);
	}

	@Override
	public void addMoreTallys(Map<String, List<Tally>> datas) {
		dao.addMoreTallys(datas);
	}

	@Override
	public void logicDeleteById(Tally tally) {
		dao.logicDeleteById(tally);
	}

	@Override
	public void deleteById(Tally tally) {
		dao.deleteById(tally);
	}

	@Override
	public void updateTallyById(Tally tally) {
		dao.updateTallyById(tally);
	}

	@Override
	public Tally getTallyById(Tally tally) {
		return dao.getTallyById(tally);
	}

}
