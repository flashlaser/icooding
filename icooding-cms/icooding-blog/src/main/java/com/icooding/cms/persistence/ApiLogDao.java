package com.icooding.cms.persistence;

import java.util.List;

import com.icooding.cms.model.ApiLog;
import org.springframework.stereotype.Repository;


@Repository
public class ApiLogDao extends BaseDao<ApiLog>{

	public List<ApiLog> page(int curPage, int pageSize){
		String hql = "from ApiLog";
		return em.createQuery(hql, ApiLog.class).setFirstResult((curPage-1)*pageSize).setMaxResults(pageSize).getResultList();
	}
}
