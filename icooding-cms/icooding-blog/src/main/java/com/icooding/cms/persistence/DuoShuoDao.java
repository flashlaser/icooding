package com.icooding.cms.persistence;

import java.util.List;

import com.icooding.cms.model.DuoShuo;
import org.springframework.stereotype.Repository;


@Repository
public class DuoShuoDao extends BaseDao<DuoShuo>{

	public long count(){
		String hql = "select count(*) from DuoShuo d where status!='delete'";
		return em.createQuery(hql, Long.class).getSingleResult();
	}
	
	public void update(String ids, String action, long logId){
		if(action.equals("approve"))
			action += "d";
		String sql = "update t_duoshuo set status=:action,log_id=:logId where post_id in ("+ids+")";
		em.createNativeQuery(sql).setParameter("action", action).setParameter("logId", logId).executeUpdate();
	}
	
	public void delete(String ids){
		String sql = "delete from t_duoshuo where post_id in ("+ids+")";
		em.createNativeQuery(sql).executeUpdate();
	}
	
	public long findLastLogId(){
		String hql = "select distinct(d.logId) from DuoShuo d order by d.logId desc";
		List<Long> list = em.createQuery(hql, Long.class).setFirstResult(0).setMaxResults(1).getResultList();
		if(list.size()==0)
			return 0;
		else
			return list.get(0);
	}
}
