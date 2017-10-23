package com.icooding.cms.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icooding.cms.model.ApiLog;
import com.icooding.cms.persistence.ApiLogDao;
import com.icooding.cms.service.ApiLogService;

@Service
@Transactional
public class ApiLogServiceImpl implements ApiLogService{

	@Autowired
	private ApiLogDao apiLogDao;
	
	@Override
	public List<ApiLog> page(int curPage, int pageSize) {
		// TODO Auto-generated method stub
		return apiLogDao.page(curPage, pageSize);
	}

	@Override
	public void save(ApiLog apiLog) {
		// TODO Auto-generated method stub
		apiLogDao.save(apiLog);
	}

}
