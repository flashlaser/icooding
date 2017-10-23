package com.icooding.cms.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icooding.cms.model.SystemMessage;
import com.icooding.cms.persistence.SystemMessageDao;
import com.icooding.cms.service.SystemMessageService;

@Service
@Transactional
public class SystemMessageServiceImpl implements SystemMessageService {

	@Autowired
	private SystemMessageDao systemMessageDao;
	
	@Override
	public Long getUnreadCount(int uid) {
		return systemMessageDao.getUnreadCount(uid);
	}

	@Override
	public void save(SystemMessage systemMessage) {
		systemMessageDao.save(systemMessage);
	}

}
