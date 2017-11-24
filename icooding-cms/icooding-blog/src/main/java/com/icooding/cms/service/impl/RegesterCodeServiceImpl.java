package com.icooding.cms.service.impl;

import com.icooding.cms.dto.Page;
import com.icooding.cms.model.RegesterCode;
import com.icooding.cms.persistence.RegesterCodeDao;
import com.icooding.cms.service.RegesterCodeService;
import com.icooding.cms.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * project_name icooding-cms
 * class RegesterCodeServiceImpl
 * date  2017/11/24
 * author ibm
 * version 1.0
 */
@Service
@Transactional
public class RegesterCodeServiceImpl implements RegesterCodeService {

    @Autowired
    private RegesterCodeDao regesterCodeDao;

    @Override
    public RegesterCode find(int id) {
        return regesterCodeDao.find(id);
    }

    @Override
    public RegesterCode findByCode(String code) {
        return regesterCodeDao.findByCode(code);
    }

    @Override
    public List<RegesterCode> findAll() {
        return regesterCodeDao.findAll();
    }

    @Override
    public void save(RegesterCode regesterCode) {
        regesterCodeDao.save(regesterCode);
    }

    @Override
    public RegesterCode update(RegesterCode regesterCode) {
        return regesterCodeDao.update(regesterCode);
    }


    @Override
    public void delete(RegesterCode regesterCode) {
        regesterCodeDao.delete(regesterCode);
    }

    @Override
    public Page list(int pageIndex, int pageSize) {
        Long count = regesterCodeDao.total();
        List<RegesterCode> list = regesterCodeDao.list(pageIndex, pageSize);
        Page page = new Page(pageIndex,pageSize,count.intValue());
        page.setRows(list);
        return page;
    }

    @Override
    public void createRegesterCode() {
        int index = 0;
        while (true) {
            if(index >= 10){
                break;
            }
            String randomString = TokenUtil.getRandomString(8, 2);
            if (regesterCodeDao.findByCode(randomString) == null) {
                RegesterCode regesterCode = new RegesterCode();
                regesterCode.setCreateTime(new Date());
                regesterCode.setUpdateTime(regesterCode.getCreateTime());
                regesterCode.setCode(randomString);
                regesterCode.setStatus(1);
                regesterCodeDao.save(regesterCode);
                index++;
            }
        }
    }
}
