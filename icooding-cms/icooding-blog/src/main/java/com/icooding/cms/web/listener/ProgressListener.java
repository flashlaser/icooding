package com.icooding.cms.web.listener;

public interface ProgressListener {

	void update(long pBytesRead, long pContentLength, int pItems);
	
	public void setIndex(int index);
}
