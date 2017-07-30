package com.qgyshop.service.impl;


import com.qgyshop.dao.HiberneteDao;
import com.qgyshop.dao.impl.HibernateDaoImpl;
import com.qgyshop.domain.Category;
import com.qgyshop.domain.Categorysecond;
import com.qgyshop.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/3/10.
 * 一级分类事物
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Resource
    HiberneteDao<Category> hiberneteDao;
    //查询所有一级分类
    @Override
    public List<Category> findAll() {

        List<Category> categories =hiberneteDao.findByPage("from Category",null,0,-1);
        return categories;
    }

    @Override
    public Category getById(Integer cid) {
        Category category=hiberneteDao.findById(Category.class,cid);
        return category;
    }

    @Override
    public void delete(Category category1) throws Exception {
//        判断是不是空的
        if(category1==null){
            throw new Exception("被删除的数据不能为空");
        }
//        判断有没有对应的二级分类
        Categorysecond categorysecond=new HibernateDaoImpl<Categorysecond>().findByHql("from Categorysecond where categoryByCid.cid = ? ", new Integer[]{category1.getCid()});
       if(categorysecond!=null){
           throw new Exception("被删除的数据含有关联数据 不允许删除");
       }
       //最后才能删除
        hiberneteDao.delete(category1);
    }

    @Override
    public void save(Category category) {
        hiberneteDao.save(category);
    }

    @Override
    public void update(Category category) {
        hiberneteDao.update(category);
    }


}
