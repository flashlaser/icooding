package com.icooding.cms.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icooding.cms.model.ThirdPartyAccess;
import com.icooding.cms.persistence.ThirdPartyAccessDao;
import com.icooding.cms.service.ThirdPartyAccessService;

@Service
@Transactional
public class ThirdPartyAccessImpl implements ThirdPartyAccessService {

	@Autowired
	private ThirdPartyAccessDao thirdPartyAccessDao;
	
	public ThirdPartyAccess findByType(int type) {
		// TODO Auto-generated method stub
		return thirdPartyAccessDao.findByType(type);
	}

	public ThirdPartyAccess update(ThirdPartyAccess access) {
		// TODO Auto-generated method stub
		return thirdPartyAccessDao.update(access);
	}

}
