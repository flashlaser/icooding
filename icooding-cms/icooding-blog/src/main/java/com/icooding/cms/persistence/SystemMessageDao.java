package com.icooding.cms.persistence;

import org.springframework.stereotype.Repository;

import com.icooding.cms.model.SystemMessage;

@Repository
public class SystemMessageDao extends BaseDao<SystemMessage>{

	public Long getUnreadCount(int uid){
		String hql = "select count(*) from SystemMessage s where s.uid=:uid and isRead=false";
		return em.createQuery(hql, Long.class).setParameter("uid", uid).getSingleResult();
	}
}
