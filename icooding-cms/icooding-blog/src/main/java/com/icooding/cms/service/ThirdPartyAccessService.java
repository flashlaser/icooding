package com.icooding.cms.service;

import com.icooding.cms.model.ThirdPartyAccess;

public interface ThirdPartyAccessService {

	public ThirdPartyAccess findByType(int type);
	
	public ThirdPartyAccess update(ThirdPartyAccess access);
}
