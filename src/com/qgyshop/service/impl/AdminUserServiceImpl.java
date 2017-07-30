package com.qgyshop.service.impl;

import com.qgyshop.dao.HiberneteDao;
import com.qgyshop.domain.User;
import com.qgyshop.service.AdminUserService;
import com.qgyshop.util.MailUtils;
import com.qgyshop.util.PageUtil;
import com.qgyshop.util.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by vivid on 2017/3/15. 貌似该类为单列模式 所以有希望试试
 */
@Service
@Transactional
public class AdminUserServiceImpl implements AdminUserService{
    private PageUtil<User> pageUtil;

    @Resource
    private HiberneteDao<User> hiberneteDao;


    /**
     * 查询所有用户
     * @param page 要查的页码
     * @return
     */
    @Override
    public PageUtil<User> findAll(int page) {
//        如果是单例模式 那么pageutil 是一直在的 所以只有删除 添加操作 才会变更
        if (pageUtil==null){
            int size=hiberneteDao.getCount("select count(uid) from User",null);
            pageUtil=new PageUtil<>(size,3);
        }
//        设置页码
        pageUtil.setPage(page);
//查询
        pageUtil.setPageDate(hiberneteDao.findByPage("from User",null,pageUtil.getStartIndex(),pageUtil.getPageSize()));

        return pageUtil;
    }

    /**
     * 用户登录
     * @param model
     * @return
     */
    @Override
    public User findByAccount(User model) {
        String hql="from User where username=? and password= ? ";
        Object[] objects=new Object[]{model.getUsername(),model.getPassword()};
        User user=hiberneteDao.findByHql(hql,objects);
        return user;
    }

    /**
     * 注册
     */
    @Override
    public void save(User model) {

        model.setState(0); //未激活

        //生成激活码
        String code= UUIDUtils.getUUID();
        model.setCode(code);
        //存用户
        hiberneteDao.save(model);
        // 邮箱发送激活链接
        MailUtils.sendMail(model.getEmail(),code);

    }

    /**
     * 用户名是否存在
     * @param name 用户名
     * @return
     */
    @Override
    public boolean usernameIsexist(String name) {
//        System.out.println("name+++++" +name);
        User user=hiberneteDao.findByHql("from User where username= ? ",new Object[]{name});
//        System.out.println("User---  "+user);
        if (user!=null)return true; //存在
        return false; //不存在
    }

    @Override
    public User findByCode(String code) {
        User user=hiberneteDao.findByHql("from User where code=?",new Object[]{code});
        return user;
    }

    @Override
    public void update(User user) {
        hiberneteDao.update(user);
    }
}

