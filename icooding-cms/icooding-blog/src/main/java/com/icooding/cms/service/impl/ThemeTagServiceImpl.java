package com.icooding.cms.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icooding.cms.model.ThemeTag;
import com.icooding.cms.persistence.ThemeTagDao;
import com.icooding.cms.service.ThemeTagService;

@Service
@Transactional
public class ThemeTagServiceImpl implements ThemeTagService {

	@Autowired
	private ThemeTagDao themeTagDao;
	
	public ThemeTag findByNameAndUser(String tagName, int uid) {
		return themeTagDao.findByNameAndUser(tagName, uid);
	}

	public List<ThemeTag> pageByDateAndUser(int curPage, int pageSize, int uid) {
		return themeTagDao.pageByDateAndUser(curPage, pageSize, uid);
	}

	public void save(ThemeTag themeTag) {
		themeTagDao.save(themeTag);
	}
	
	public boolean exits(String tagName, int uid){
		return themeTagDao.findByNameAndUser(tagName, uid)==null?false:true;
	}

	public ThemeTag update(ThemeTag themeTag){
		return themeTagDao.update(themeTag);
	}

	@Override
	public List<ThemeTag> random(int pageSize) {
		List<ThemeTag> random = themeTagDao.random(pageSize);
		return random;
	}
}
