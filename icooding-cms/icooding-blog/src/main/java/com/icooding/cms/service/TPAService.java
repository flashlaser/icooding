package com.icooding.cms.service;

import java.util.List;

import com.icooding.cms.model.ThirdPartyAccount;

public interface TPAService {

	public ThirdPartyAccount find(String guid);
	
	public void save(ThirdPartyAccount account);
	
	public ThirdPartyAccount update(ThirdPartyAccount account);
	
	/**
	 * 获取用户下所有的第三方帐号绑定
	 * @param uid
	 * @return
	 */
	public List<ThirdPartyAccount> findByUid(int uid);
	
	public ThirdPartyAccount findByUidAndType(int uid, int type);
	
	public ThirdPartyAccount findByOpenId(String openId);
}
