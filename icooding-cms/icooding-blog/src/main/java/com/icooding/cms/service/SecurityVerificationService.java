package com.icooding.cms.service;

import java.util.List;

import com.icooding.cms.model.SecurityVerification;

public interface SecurityVerificationService {
	
	public List<SecurityVerification> findAll();

	public SecurityVerification find(String guid);

	public void save(SecurityVerification securityVerification);

	public SecurityVerification update(SecurityVerification securityVerification);

	public void delete(SecurityVerification securityVerification);

	public SecurityVerification findBySecurityVerificationAndType(int uid, int type);
	
	public SecurityVerification findByLoginNameAndType(String loginName, int type);
	
	public SecurityVerification findByGuidAndType(String guid, int type);
}
