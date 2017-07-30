package com.qgyshop.dao.impl;


import com.qgyshop.dao.CategoryDao;
import com.qgyshop.domain.Category;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/3/10.
 */
@Repository
public class CategoryDaoImpl implements CategoryDao {
    @Resource
    SessionFactory sessionFactory;

    /**
     * 查询所有数据
     * @return
     */
    @Override
    public List<Category> findAll() {
        Session session= sessionFactory.openSession();
        String HQL="from Category";
        Query query=session.createQuery(HQL);
        List<Category> categories = query.list();
        return categories;
    }

    /**
     *查询指定id的一级分类
     * @param cid
     * @return
     */
    @Override
    public Category getById(Integer cid) {
        Session session=sessionFactory.openSession();
        Category category= (Category) session.get(Category.class,cid);
        session.close();
        return category;
    }

    /**
     * 删除一级分类
     * @param category
     */
    @Override
    public void delete(Category category) {
        Session session=sessionFactory.openSession();
        session.delete(category);
        session.close();
    }

    /**
     *保存数据
     */
    @Override
    public void save(Category category) {
        Session session=sessionFactory.openSession();
        session.save(category);
        session.close();
    }
}
