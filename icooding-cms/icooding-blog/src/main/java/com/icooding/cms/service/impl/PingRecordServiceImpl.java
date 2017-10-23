package com.icooding.cms.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icooding.cms.model.PingRecord;
import com.icooding.cms.persistence.PingRecordDao;
import com.icooding.cms.service.PingRecordService;

@Service
@Transactional
public class PingRecordServiceImpl implements PingRecordService {

	@Autowired
	private PingRecordDao pingRecordDao;
	
	@Override
	public List<PingRecord> findAll(int curPage, int pageSize) {
		return pingRecordDao.findAll(curPage, pageSize);
	}

	@Override
	public void save(PingRecord pingRecord) {
		pingRecordDao.save(pingRecord);
	}

	@Override
	public PingRecord update(PingRecord pingRecord) {
		return pingRecordDao.update(pingRecord);
	}

	@Override
	public PingRecord findByThemeGuid(String guid) {
		return pingRecordDao.findByThemeGuid(guid);
	}

	@Override
	public long count() {
		return pingRecordDao.count();
	}

	@Override
	public PingRecord find(int id) {
		return pingRecordDao.find(id);
	}

}
