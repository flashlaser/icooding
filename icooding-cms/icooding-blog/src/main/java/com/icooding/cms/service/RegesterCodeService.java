package com.icooding.cms.service;

import com.icooding.cms.dto.Page;
import com.icooding.cms.model.RegesterCode;
import com.icooding.cms.model.User;

import java.util.List;

/**
 * project_name icooding-cms
 * class RegesterCodeService
 * date  2017/11/24
 * author ibm
 * version 1.0
 */
public interface RegesterCodeService {

    public RegesterCode find(int id);

    public RegesterCode findByCode(String code);

    public List<RegesterCode> findAll();

    public void save(RegesterCode user);

    public RegesterCode update(RegesterCode user);

    public void delete(RegesterCode user);


    public Page list(int pageIndex,int pageSize);

    public void createRegesterCode();

}
