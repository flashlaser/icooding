package com.icooding.cms.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icooding.cms.model.Media;
import com.icooding.cms.persistence.MediaDao;
import com.icooding.cms.service.MediaService;

@Service
@Transactional
public class MediaServiceImpl implements MediaService {

	@Autowired
	private MediaDao mediaDao;
	
	@Override
	public Media find(int id) {
		return mediaDao.find(id);
	}

	@Override
	public void save(Media media) {
		mediaDao.save(media);
	}

	@Override
	public Media update(Media media) {
		return mediaDao.update(media);
	}

	@Override
	public void delete(Media media) {
		mediaDao.delete(media);
	}

	@Override
	public Media findByUrl(String url) {
		return mediaDao.findByUrl(url);
	}

}
