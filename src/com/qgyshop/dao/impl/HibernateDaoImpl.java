package com.qgyshop.dao.impl;

import com.qgyshop.dao.HiberneteDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by vivid on 2017/3/12.
 */

@Repository
public class HibernateDaoImpl<T> implements HiberneteDao<T>{
//    工厂连接对象
    @Resource
    private SessionFactory sessionFactory;
//  {
//       Configuration configuration=new Configuration().configure();
//       sessionFactory=configuration.buildSessionFactory();
//  }
    /**
     * hibernate 的分页功能 只要输入从第几条开始startIndex（起始为0）
     * startIndex如果超出数据库的总记录数 则返回null
     * pageSize 要查询的记录数 如果该值超出了 则有几条查几条 不会报错 放心用
     * @param HQL 查询的语句
     * @param objects 条件的参数列表
     * @param startIndex 查询的起始位置（0开始）
     * @param pageSize 查询的记录条数 如果pageSize为-1  startIndex为0 则查询所有的记录
     * @return
     */
    @Override
    public List<T> findByPage(String HQL, Object[] objects, int startIndex, int pageSize) {

        Session session=sessionFactory.getCurrentSession();
//        System.out.println("session"+session);
        Query query=session.createQuery(HQL);
//        设置参数列表
        if (objects!=null){
            for (int i = 0; i < objects.length; i++) {
                query.setParameter(i,objects[i]);
            }
        }

//        分页
        query.setFirstResult(startIndex);
        query.setMaxResults(pageSize);
        List<T> list=query.list();

        return list;
    }

    /**
     * 查询记录条数
     * @param HQL 查询的hql
     * @param objects 条件
     * @return
     */
    @Override
    public int getCount(String HQL, Object[] objects) {
//      HQL="select count(*) from User ";
        Session session =sessionFactory.getCurrentSession();
        Query query=session.createQuery(HQL);
        if (objects!=null){
            for (int i = 0; i < objects.length; i++) {
                query.setParameter(i,objects[i]);
            }
        }
//        获取记录总数 cunt默认返回long 所以要转换成int  intValue()可以将long 转换成int
        int size=((Long) query.uniqueResult()).intValue();
        return size;
    }

    /**
     * 获取对象通过id
     * @param id
     * @return
     */
    @Override
    public T findById(Class<T> t,int id) {
        Session session=sessionFactory.getCurrentSession();

//        session.get(t.getClass(),id);
        return (T) session.get(t,id);
    }

    /**
     * 查询指定对象
     * @param HQL
     * @param objects
     * @return
     */
    @Override
    public T findByHql(String HQL, Object[] objects) {
        Session session=sessionFactory.getCurrentSession();
        Query query=session.createQuery(HQL);
        if (objects!=null){
            for (int i = 0; i <objects.length ; i++) {
                query.setParameter(i,objects[i]);
            }
        }
        return (T) query.uniqueResult();
    }

    @Override
    public void update(T t) {
        Session session=sessionFactory.getCurrentSession();
        session.update(t);
    }

    @Override
    public void delete(T t) {
        Session session=sessionFactory.getCurrentSession();
        session.delete(t);
    }

    @Override
    public void save(T t) {
        Session session=sessionFactory.getCurrentSession();
        session.save(t);
    }
}
