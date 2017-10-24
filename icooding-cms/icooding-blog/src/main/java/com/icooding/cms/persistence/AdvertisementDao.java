package com.icooding.cms.persistence;

import java.util.List;

import com.icooding.cms.model.Advertisement;
import org.springframework.stereotype.Repository;


@Repository
public class AdvertisementDao extends BaseDao<Advertisement>{

	public List<Advertisement> page(int curPage, int pageSize){
		String hql = "from t_advertisement a order by id desc";
		return em.createQuery(hql, Advertisement.class).setFirstResult((curPage-1)*pageSize).setMaxResults(pageSize).getResultList();
	}
	
	public long count(){
		String hql = "select count(*) from t_advertisement";
		return em.createQuery(hql, Long.class).getSingleResult();
	}
	
	public Advertisement findLastByType(int type){
		String hql = "from t_advertisement where type=:type and active=true order by id desc";
		List<Advertisement> list = em.createQuery(hql, Advertisement.class).setParameter("type", type).setFirstResult(0).setMaxResults(1).getResultList();
		if(list.size()==0)
			return null;
		else
			return list.get(0);
	}
}
