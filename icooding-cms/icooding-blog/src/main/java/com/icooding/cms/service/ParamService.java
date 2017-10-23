package com.icooding.cms.service;

import com.icooding.cms.model.Param;

public interface ParamService {

	public void save(Param param);
	
	public Param update(Param param);
	
	public Param findByKey(String key);
}
