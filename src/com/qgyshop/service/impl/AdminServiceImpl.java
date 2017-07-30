package com.qgyshop.service.impl;


import com.qgyshop.dao.HiberneteDao;
import com.qgyshop.domain.Adminuser;
import com.qgyshop.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/3/10.
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {
//    @Resource
//    AdminuserDao adminuserDao;
    @Resource
    HiberneteDao<Adminuser> hiberneteDao;

    @Override
    public Adminuser login(Adminuser adminuser) {
        Object[] objects={adminuser.getUsername(),adminuser.getPassword()};
        Adminuser adminuser1= hiberneteDao.findByHql("from Adminuser where username=? and password= ?",objects);
        return adminuser1;
    }

}
