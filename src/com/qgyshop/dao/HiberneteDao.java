package com.qgyshop.dao;

import java.util.List;

/**
 * Created by vivid on 2017/3/12.
 */
public interface HiberneteDao<T> {
    //查询所有的对象 有分页
    List<T> findByPage(String HQL, Object[] objects, int startIndex, int pageSize);

    //查询该表的记总录数 要有条件 因为有筛选查询的功能 此值主要用于分页操作
    int getCount(String HQL, Object[] objects);

// 查询拥有指定id的对象
    T findById(Class<T> t, int id);
//查询指定一条记录 通过条件
    T findByHql(String HQL, Object[] objects);
//    修改
    void update(T t);

//    删除
    void delete(T t);

//    添加
    void save(T t);


}
