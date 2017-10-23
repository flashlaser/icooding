package com.icooding.cms.service;

import java.util.List;

import com.icooding.cms.model.ApiLog;

public interface ApiLogService {
	public List<ApiLog> page(int curPage, int pageSize);

	public void save(ApiLog apiLog);

}
