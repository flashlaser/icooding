package com.icooding.cms.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.icooding.cms.dto.GlobalSetting;
import com.icooding.cms.model.RegesterCode;
import com.icooding.cms.persistence.RegesterCodeDao;
import com.icooding.cms.utils.EncryptUtil;
import com.icooding.cms.utils.ImageUtils;
import com.icooding.cms.utils.QiniuCloudUtils;
import com.icooding.cms.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icooding.cms.model.User;
import com.icooding.cms.persistence.UserDao;
import com.icooding.cms.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Autowired
	RegesterCodeDao regesterCodeDao;

	public User find(int uid) {
		return userDao.find(uid);
	}

	public void save(User user) {
		userDao.save(user);
	}

	public User update(User user) {
		return userDao.update(user);
	}

	public void delete(User user) {
		userDao.delete(user);
	}
	
	public User login(String loginName,String pwd){
		return userDao.login(loginName, pwd);
	}

	public boolean checkLoginName(String loginName){
		return userDao.checkLoginName(loginName);
	}

	public boolean checkNickName(String nickName) {
		return userDao.checkNickName(nickName);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public long count() {
		return userDao.count();
	}

	@Override
	public List<User> page(int curPage, int pageSize) {
		return userDao.page(curPage, pageSize);
	}

	@Override
	public User register(String nickName, String password, String username, int type, String registerCode) {
		User user = new User();
		user.setNickName(nickName);
		user.setDomain(TokenUtil.getRandomString(10,0));
		Date date = Calendar.getInstance().getTime();
		user.setPassword(EncryptUtil.pwd(date, password));
		if(type==1){
			user.setEmail(username);
		}
		else{
			user.setMobile(username);
		}

		String path = GlobalSetting.getInstance().getTemp_dir()+user.getNickName().hashCode()+".png";
		try {
			user.setActivateDate(date);
			userDao.save(user);
			ImageUtils.createHeadImage(500,500,nickName.substring(0,1),path);
			String url = QiniuCloudUtils.updateFile(path,user.getUid());
			user.setHeadIconSmall(url+"-50x50");
			user.setHeadIconMid(url+"-80x80");
			user.setHeadIconBig(url+"-150x150");
			user.setHeadIconHD(url+"-500x500");
			user.setHeadIconLocal(url);
			user.setHeadIconUsed(0);
			userDao.update(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		RegesterCode byCode = regesterCodeDao.findByCode(registerCode);
		byCode.setUserId(user.getUid());
		byCode.setUsername(user.getNickName());
		byCode.setUpdateTime(new Date());
		regesterCodeDao.update(byCode);
		return user;
	}
}
