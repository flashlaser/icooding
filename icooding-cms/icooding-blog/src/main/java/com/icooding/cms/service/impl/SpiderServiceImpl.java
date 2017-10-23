package com.icooding.cms.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icooding.cms.model.Spider;
import com.icooding.cms.persistence.SpiderDao;
import com.icooding.cms.service.SpiderService;

@Service
@Transactional
public class SpiderServiceImpl implements SpiderService {

	@Autowired
	private SpiderDao spiderDao;
	
	@Override
	public void save(Spider spider) {
		spiderDao.save(spider);
	}

	@Override
	public void delete(Spider spider) {
		spiderDao.delete(spider);
	}

	@Override
	public List<Spider> page(int curPage, int pageSize) {
		return spiderDao.page(curPage, pageSize);
	}

	@Override
	public long count() {
		return spiderDao.count();
	}

}
