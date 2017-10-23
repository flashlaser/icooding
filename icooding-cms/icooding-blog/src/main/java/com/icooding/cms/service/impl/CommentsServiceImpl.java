package com.icooding.cms.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icooding.cms.model.Comments;
import com.icooding.cms.persistence.CommentsDao;
import com.icooding.cms.service.CommentsService;

@Service
@Transactional
public class CommentsServiceImpl implements CommentsService {

	@Autowired
	private CommentsDao commentsDao;
	
	public void save(Comments comments) {
		commentsDao.save(comments);
	}

	public Comments update(Comments comments) {
		return commentsDao.update(comments);
	}

	public void delete(Comments comments) {
		commentsDao.delete(comments);
	}
	
	public Comments find(String commemtsGuid){
		return commentsDao.find(commemtsGuid);
	}

	@Override
	public long count() {
		return commentsDao.count();
	}

}
