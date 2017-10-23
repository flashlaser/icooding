package com.icooding.cms.service;

import javax.servlet.http.HttpServletRequest;

import com.icooding.cms.model.User;
import com.icooding.cms.model.UserSession;

public interface UserSessionService {

	public void save(UserSession userSession);
	
	public UserSession update(UserSession userSession);
	
	public UserSession findByUserId(int uid);
	
	public UserSession find(String guid);
	
	public UserSession login(User user, HttpServletRequest request);
	
	public void logout(String guid);
	
	public UserSession loginByToken(int uid, String token);
	
	public UserSession findByToken(String token);
}
