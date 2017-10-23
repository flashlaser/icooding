package com.icooding.cms.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icooding.cms.model.ThirdPartyAccount;
import com.icooding.cms.persistence.ThirdPartyAccountDao;
import com.icooding.cms.service.TPAService;

@Service
@Transactional
public class TPAServiceImpl implements TPAService {

	@Autowired
	private ThirdPartyAccountDao thirdPartyAccountDao;
	
	public ThirdPartyAccount find(String guid) {
		return thirdPartyAccountDao.find(guid);
	}

	public void save(ThirdPartyAccount account) {
        thirdPartyAccountDao.save(account);
	}

	public ThirdPartyAccount update(ThirdPartyAccount account) {
		return thirdPartyAccountDao.update(account);
	}

	public List<ThirdPartyAccount> findByUid(int uid){
		return thirdPartyAccountDao.findByUid(uid);
	}
	
	public ThirdPartyAccount findByUidAndType(int uid, int type){
		return thirdPartyAccountDao.findByUidAndType(uid, type);
	}
	
	public ThirdPartyAccount findByOpenId(String openId){
		return thirdPartyAccountDao.findByOpenId(openId);
	}
}
