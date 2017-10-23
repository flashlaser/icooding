package com.icooding.cms.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.icooding.cms.model.ThirdPartyAccess;

@Repository
public class ThirdPartyAccessDao extends BaseDao<ThirdPartyAccess>{

	public ThirdPartyAccess findByType(int type){
		String hql = "from ThirdPartyAccess t where t.type=:type";
		List<ThirdPartyAccess> list = em.createQuery(hql, ThirdPartyAccess.class).setParameter("type", type).getResultList();
		if(list.size()==0)
			return null;
		else
			return list.get(0);
	}
}
