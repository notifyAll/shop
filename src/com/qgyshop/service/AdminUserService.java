package com.qgyshop.service;

import com.qgyshop.domain.User;
import com.qgyshop.util.PageUtil;

/**
 * Created by vivid on 2017/3/15.
 */
public interface AdminUserService {
    //查询所有数据 带分业
    PageUtil<User> findAll(int page);

    User findByAccount(User model);

    void save(User model);

    boolean usernameIsexist(String name);

    User findByCode(String code);

    void update(User user);
}
