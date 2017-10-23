package com.icooding.cms.service;

import com.icooding.cms.model.VoteRecord;

public interface VoteRecordService {

	public boolean exists(String sessionId, String guid, int type);
	
	public void save(VoteRecord commentVoteRecord);
}
