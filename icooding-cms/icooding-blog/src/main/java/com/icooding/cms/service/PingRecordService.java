package com.icooding.cms.service;

import java.util.List;

import com.icooding.cms.model.PingRecord;

public interface PingRecordService {

	public List<PingRecord> findAll(int curPage, int pageSize);
	
	public void save(PingRecord pingRecord);
	
	public PingRecord find(int id);
	
	public PingRecord update(PingRecord pingRecord);
	
	public PingRecord findByThemeGuid(String guid);
	
	public long count();
}
