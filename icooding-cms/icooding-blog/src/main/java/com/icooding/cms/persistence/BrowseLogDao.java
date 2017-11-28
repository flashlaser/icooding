package com.icooding.cms.persistence;

import com.icooding.cms.model.BrowseLog;
import org.springframework.stereotype.Repository;

/**
 * project_name icooding-cms
 * class BrowseLogDao
 * date  2017/11/28
 * author ibm
 * version 1.0
 */
@Repository
public class BrowseLogDao extends BaseDao<BrowseLog>{


    public Long queryCountByDate(String format, String date){
        String hql = "select count(ip) from BrowseLog where DATE_FORMAT(createTime,'"+format+"')=:date  ";
        return em.createQuery(hql,Long.class).setParameter("date",date).getSingleResult();
    };
}
