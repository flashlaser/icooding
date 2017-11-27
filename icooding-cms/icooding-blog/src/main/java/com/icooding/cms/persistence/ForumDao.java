package com.icooding.cms.persistence;

import java.util.List;

import com.icooding.cms.model.Forum;
import org.springframework.stereotype.Repository;


@Repository
public class ForumDao extends BaseDao<Forum>{

	public List<Forum> searchRoot(){
		String hql = "from Forum f where f.parentForum is null";
		return em.createQuery(hql, Forum.class).getResultList();
	}
	
	public boolean checkForumName(String forumName){
		String hql = "from Forum f where f.forumName=:forumName";
		List<Forum> forums = em.createQuery(hql, Forum.class).setParameter("forumName", forumName).getResultList();
		if(forums.size()==0)
			return false;
		else
			return true;
	}
	
	public List<Forum> searchChildPoint(){
		String hql = "from Forum f where f.parentForum is not null order by f.forumOrder asc";
		return em.createQuery(hql, Forum.class).getResultList();
	}
}
