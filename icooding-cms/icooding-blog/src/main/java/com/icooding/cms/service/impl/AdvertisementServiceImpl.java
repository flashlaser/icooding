package com.icooding.cms.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icooding.cms.model.Advertisement;
import com.icooding.cms.persistence.AdvertisementDao;
import com.icooding.cms.service.AdvertisementService;

@Service
@Transactional
public class AdvertisementServiceImpl implements AdvertisementService {

	@Autowired
	private AdvertisementDao advertisementDao;
	
	@Override
	public Advertisement find(int id) {
		return advertisementDao.find(id);
	}

	@Override
	public List<Advertisement> page(int curPage, int pageSize) {
		return advertisementDao.page(curPage, pageSize);
	}

	@Override
	public long count() {
		return advertisementDao.count();
	}

	@Override
	public void save(Advertisement advertisement) {
		advertisementDao.save(advertisement);
	}

	@Override
	public Advertisement update(Advertisement advertisement) {
		return advertisementDao.update(advertisement);
	}

	@Override
	public void delete(Advertisement advertisement) {
		advertisementDao.delete(advertisement);
	}

	@Override
	public Advertisement findLastByType(int type) {
		return advertisementDao.findLastByType(type);
	}

}
