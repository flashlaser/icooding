package com.icooding.cms.persistence;

import com.icooding.cms.model.RegesterCode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class BaseDao<T> {

	@PersistenceContext(unitName = "fate-persistence")
    protected EntityManager em;

	protected Class<T> entityClass = null;

	public BaseDao() {
		Type t = getClass().getGenericSuperclass();
		if(t instanceof ParameterizedType){
			Type[] p = ((ParameterizedType)t).getActualTypeArguments();
			entityClass = (Class<T>)p[0];
		}
	}

	public T find(String guid){
		return em.find(entityClass, guid);
	}
	
	public T find(int id){
		return em.find(entityClass, id);
	}
	
	public void save(T t){
		em.persist(t);
	}
	
	public T update(T t){
		return em.merge(t);
	}
	
	public void delete(T t){
		em.remove(em.merge(t));
	}

	public Long total(){
		String hql = "select count(*) from "+entityClass.getSimpleName();
		return (Long)em.createQuery(hql, Long.class).getSingleResult();
	}
	public List<T> list(int pageIndex,int pageSize){
		String hql = "from "+entityClass.getSimpleName();
		return em.createQuery(hql, entityClass).setMaxResults(pageSize).setFirstResult((pageIndex-1)*pageSize).getResultList();
	}
	public List<T> findAll(){
        String hql = "from "+entityClass.getSimpleName();
		return em.createQuery(hql, entityClass).getResultList();
	}
}
