package com.icooding.cms.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.icooding.cms.model.Navi;

@Repository
public class NaviDao extends BaseDao<Navi>{

	public List<Navi> searchRoot(){
		String hql = "from Navi n where n.parent is null order by n.order asc";
		return em.createQuery(hql, Navi.class).getResultList();
	}
}
