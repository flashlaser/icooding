package com.icooding.cms.service;

import com.icooding.cms.model.BrowseLog; /**
 * project_name icooding-cms
 * class BrowseLogService
 * date  2017/11/28
 * author ibm
 * version 1.0
 */
public interface BrowseLogService {
    void save(BrowseLog browseLog);

    Long queryCountByDate(String format, String date);
}
