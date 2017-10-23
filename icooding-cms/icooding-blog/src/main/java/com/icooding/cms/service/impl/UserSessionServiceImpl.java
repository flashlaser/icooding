package com.icooding.cms.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import com.icooding.weibo.Account;
import com.icooding.weibo.model.WeiboException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icooding.cms.model.User;
import com.icooding.cms.model.UserSession;
import com.icooding.cms.persistence.ThirdPartyAccountDao;
import com.icooding.cms.persistence.UserSessionDao;
import com.icooding.cms.service.UserSessionService;
import com.icooding.cms.utils.ClientInfo;
import com.icooding.cms.utils.TokenUtil;

@Service
@Transactional
public class UserSessionServiceImpl implements UserSessionService {

	@Autowired
	private UserSessionDao userSessionDao;
	
	@Autowired
	private ThirdPartyAccountDao thirdPartyAccountDao;
	
	public void save(UserSession userSession) {
		userSessionDao.save(userSession);
	}

	public UserSession update(UserSession userSession) {
		return userSessionDao.update(userSession);
	}

	public UserSession findByUserId(int uid) {
		return userSessionDao.findByUserId(uid);
	}

	public UserSession find(String guid){
		return userSessionDao.find(guid);
	}
	
	public UserSession login(User user, HttpServletRequest request){
		HttpSession session = request.getSession();
		UserSession userSession = findByUserId(user.getUid());
		if(userSession==null){
			userSession = new UserSession();
		}
		else{
			userSession.setLastLoginDate(userSession.getLoginDate());
			userSession.setLastLoginIp(userSession.getLoginIp());
		}
		userSession.setLoginDate(new Date());
		userSession.setLoginIp(ClientInfo.getIp(request));
		String sessionId = TokenUtil.getRandomString(32, 2);
		userSession.setSessionId(sessionId);
		userSession.setUser(user);
		userSession.setType(0);
		userSession = update(userSession);
		session.setAttribute("userSession", userSession);
		session.setAttribute("bwSessionId", userSession.getSessionId());
		return userSession;
	}
	
	public void logout(String guid){
		UserSession session = find(guid);
		session.setSessionId(null);
		if(session.getType()==UserSession.TYPE_XINLANG){
			Account am = new Account(thirdPartyAccountDao.findByUidAndType(session.getUser().getUid(),UserSession.TYPE_XINLANG).getAccessToken());
			try {
				am.endSession();
			} catch (WeiboException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		update(session);
	}
	
	public UserSession loginByToken(int uid,String token){
		return userSessionDao.loginByToken(uid, token);
	}
	
	public UserSession findByToken(String token){
		return userSessionDao.findByToken(token);
	}
}
