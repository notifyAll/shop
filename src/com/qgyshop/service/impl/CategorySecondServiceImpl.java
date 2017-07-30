package com.qgyshop.service.impl;

import com.qgyshop.dao.HiberneteDao;
import com.qgyshop.domain.Category;
import com.qgyshop.domain.Categorysecond;

import com.qgyshop.service.CategorySecondService;
import com.qgyshop.service.CategoryService;
import com.qgyshop.util.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * Created by vivid on 2017/3/16.
 */
@Service
@Transactional
public class CategorySecondServiceImpl implements CategorySecondService {
    private PageUtil<Categorysecond> pageUtil;
    @Resource
    HiberneteDao<Categorysecond> hiberneteDao;

    @Resource
    private CategoryService categoryService;
    @Override
    public PageUtil<Categorysecond> findAll(int page) {
        if (pageUtil==null){
            int size=hiberneteDao.getCount("select count(csid) from Categorysecond",null);
            pageUtil=new PageUtil<>(size,10);
        }
        pageUtil.setPage(page);
        System.out.println(page+" ----------------------");
        pageUtil.setPageDate(hiberneteDao.findByPage("from Categorysecond",null,pageUtil.getStartIndex(),pageUtil.getPageSize()));
        return pageUtil;
    }

    /**
     * 查询全部
     * @return
     */
    @Override
    public Collection<Categorysecond> findAll() {
        Collection<Categorysecond> csList=hiberneteDao.findByPage("from Categorysecond",null,0,-1);

        return csList;
    }

    //查找该二级分类
    @Override
    public Categorysecond findById(Integer csid) {

        return hiberneteDao.findById(Categorysecond.class,csid);
    }

    //修改 不改变记录数
    @Override
    public void update(Categorysecond oldCS,Categorysecond newCS) {
          //比较二级分类名是否一样 如果没变则不修改
        if (!newCS.getCsname().equals(oldCS.getCsname())&&newCS.getCsname()!=null&&!newCS.getCsname().trim().equals("")){
            oldCS.setCsname(newCS.getCsname());
        }
        //比较其一级分类是否改变 如果没变则不修改
        if (newCS.getCategoryByCid().getCid()!=oldCS.getCategoryByCid().getCid()){
            //查取新的一级分类的信息
            Category category=categoryService.getById(newCS.getCategoryByCid().getCid());
            oldCS.setCategoryByCid(category);
        }

        hiberneteDao.update(oldCS);
    }

    /**
     * 保存 由于增加了一条记录 需要改变 pageUtil的size 佛则可能出现加了数据却没有显示
     * @param model
     */
    @Override
    public void save(Categorysecond model) {
        //加一
        pageUtil.setSize(pageUtil.getSize()+1);
        hiberneteDao.save(model);
    }
//删除
    @Override
    public void delete(Categorysecond c) {
        //查找有无引用该二级分类的商品
        //code..
        pageUtil.setSize(pageUtil.getSize()-1);
        hiberneteDao.delete(c);
    }
}
