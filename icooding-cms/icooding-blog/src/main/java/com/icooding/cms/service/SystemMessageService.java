package com.icooding.cms.service;

import com.icooding.cms.model.SystemMessage;

public interface SystemMessageService {

	public Long getUnreadCount(int uid);
	
	public void save(SystemMessage systemMessage);
}
