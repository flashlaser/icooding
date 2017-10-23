package com.icooding.cms.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icooding.cms.model.Param;
import com.icooding.cms.persistence.ParamDao;
import com.icooding.cms.service.ParamService;

@Service
@Transactional
public class ParamServiceImpl implements ParamService {

	@Autowired
	private ParamDao paramDao;
	
	@Override
	public void save(Param param) {
		paramDao.save(param);
	}

	@Override
	public Param update(Param param) {
		return paramDao.update(param);
	}

	@Override
	public Param findByKey(String key) {
		return paramDao.findByKey(key);
	}

}
