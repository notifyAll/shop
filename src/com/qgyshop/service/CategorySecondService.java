package com.qgyshop.service;

import com.qgyshop.domain.Categorysecond;
import com.qgyshop.util.PageUtil;

import java.util.Collection;

/**
 * Created by vivid on 2017/3/16.
 */
public interface CategorySecondService {
    PageUtil<Categorysecond> findAll(int page);
  //查询全部 不带分页
    Collection<Categorysecond> findAll();

    Categorysecond findById(Integer csid);

    void update(Categorysecond oldCS,Categorysecond newCS);

    void save(Categorysecond model);

    void delete(Categorysecond c);
}
