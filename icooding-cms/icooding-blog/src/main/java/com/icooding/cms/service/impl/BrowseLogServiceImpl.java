package com.icooding.cms.service.impl;

import com.icooding.cms.model.BrowseLog;
import com.icooding.cms.persistence.BrowseLogDao;
import com.icooding.cms.persistence.CommentsDao;
import com.icooding.cms.service.BrowseLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * project_name icooding-cms
 * class BrowseLogServiceImpl
 * date  2017/11/28
 * author ibm
 * version 1.0
 */
@Service
@Transactional
public class BrowseLogServiceImpl implements BrowseLogService {

    @Autowired
    private BrowseLogDao browseLogDao;


    @Override
    public void save(BrowseLog browseLog) {
        browseLogDao.save(browseLog);
    }

    @Override
    public Long queryCountByDate(String format, String date) {
        return browseLogDao.queryCountByDate(format,date);
    }
}
