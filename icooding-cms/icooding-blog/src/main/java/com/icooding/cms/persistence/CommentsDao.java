package com.icooding.cms.persistence;

import com.icooding.cms.model.Comments;
import org.springframework.stereotype.Repository;


@Repository
public class CommentsDao extends BaseDao<Comments>{

	public long count(){
		String hql = "select count(*) from Comments c";
		return em.createQuery(hql, Long.class).getSingleResult();
	}
}
