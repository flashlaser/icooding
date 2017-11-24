package com.icooding.cms.service;

import java.util.List;

import com.icooding.cms.model.User;

public interface UserService {

	public User find(int uid);
	
	public List<User> findAll();
	
	public void save(User user);
	
	public User update(User user);
	
	public void delete(User user);
	
	public User login(String loginName, String pwd);
	
	/**
	 * 检查用户名是否存在
	 * @param loginName
	 * @return 返回true则存在，返回false则不存在
	 */
	public boolean checkLoginName(String loginName);
	
	public boolean checkNickName(String nickName);
	
	/**
	 * 统计注册会员数
	 * @return
	 */
	public long count();
	
	public List<User> page(int curPage, int pageSize);

	public User register(String nickName, String password, String username, int type, String registerCode);
}
