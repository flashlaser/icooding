package com.icooding.cms.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icooding.cms.model.ExceptionLog;
import com.icooding.cms.persistence.ExceptionLogDao;
import com.icooding.cms.service.ExceptionLogService;

@Service
@Transactional
public class ExceptionLogServiceImpl implements ExceptionLogService {

	@Autowired
	private ExceptionLogDao exceptionLogDao;
	
	@Override
	public List<ExceptionLog> page(int status, int curPage, int pageSize) {
		return exceptionLogDao.page(status, curPage, pageSize);
	}
	
	@Override
	public long count(int status){
		return exceptionLogDao.count(status);
	}

	@Override
	public void save(int status, String url, String source, String agent, String remark) {
		ExceptionLog exceptionLog = new ExceptionLog();
		exceptionLog.setStatus(status);
		exceptionLog.setUrl(url);
		exceptionLog.setSource(source);
		exceptionLog.setAgent(agent);
		exceptionLog.setRemark(remark);
		exceptionLogDao.save(exceptionLog);
	}

}
