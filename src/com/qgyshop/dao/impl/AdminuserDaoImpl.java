package com.qgyshop.dao.impl;


import com.qgyshop.dao.AdminuserDao;
import com.qgyshop.domain.Adminuser;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/3/10.
 */
@Repository
public class AdminuserDaoImpl implements AdminuserDao {
    @Resource
    SessionFactory sessionFactory;
    @Override
    public Adminuser login(Adminuser adminuser) {
//        开启会话
        Session session=sessionFactory.openSession();
        String HQL="from Adminuser where username=? and password= ?";
        Query query=session.createQuery(HQL);
         query.setParameter(0,adminuser.getUsername());
         query.setParameter(1,adminuser.getPassword());
        Adminuser adminuser1= (Adminuser) query.uniqueResult();
        session.close();
        return adminuser1;
    }
}
