package com.icooding.cms.persistence;

import com.icooding.cms.model.RegesterCode;
import com.icooding.cms.model.SecurityVerification;
import com.icooding.cms.utils.Base64;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * project_name icooding-cms
 * class RegesterCodeDao
 * date  2017/11/24
 * author ibm
 * version 1.0
 */
@Repository
public class RegesterCodeDao extends BaseDao<RegesterCode>{


    public RegesterCode findByCode(String code) {
        try{
            return em.createQuery("FROM RegesterCode where code=:code",RegesterCode.class).setParameter("code",code).getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    public List<RegesterCode> list(int pageIndex, int pageSize) {
        String hql = "from "+entityClass.getSimpleName() +" order by createTime desc";
        return em.createQuery(hql, entityClass).setMaxResults(pageSize).setFirstResult((pageIndex-1)*pageSize).getResultList();
    }

}
