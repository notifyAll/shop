package com.qgyshop.service.impl;

import com.qgyshop.dao.HiberneteDao;
import com.qgyshop.domain.Product;
import com.qgyshop.service.ProductService;
import com.qgyshop.util.PageUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/3/17.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService{
    @Resource
    private HiberneteDao<Product> hiberneteDao;

    //分页
    private PageUtil<Product> pageUtil;
 //日志
    Log log= LogFactory.getLog(this.getClass());
    @Override
    public void save(Product model) {
        pageUtil.setSize(pageUtil.getSize()+1);
        hiberneteDao.save(model);
    }
 //查询所有商品带分页
    @Override
    public PageUtil<Product> findAll(int page) {
        if (pageUtil==null){
            int size=hiberneteDao.getCount("select count(pid) from Product",null);
            pageUtil=new PageUtil<>(size,15);
        }
        pageUtil.setPage(page);
        //查找
        pageUtil.setPageDate(hiberneteDao.findByPage("from Product",null,pageUtil.getStartIndex(),pageUtil.getPageSize()));

        return pageUtil;
    }
    /**
     * 查找指定id的数据
     */
    @Override
    public Product findById(Integer pid) {
        Product product=hiberneteDao.findById(Product.class,pid);
        return product;
    }

    /**
     * 删除的功能
     * @param product
     */
    @Override
    public void delete(Product product) {
        pageUtil.setSize(pageUtil.getSize()-1);
        hiberneteDao.delete(product);
    }

    //查找热门商品
    @Override
    public List<Product> findHot() {
        Object[] objects=new Object[1];
        objects[0]=1;
        List<Product> hList=  hiberneteDao.findByPage("from Product where isHot= ? ", objects,0,-1);
//        log.info("hList---------------"+hList.size());
        return hList;
    }
// 查找最新商品
    @Override
    public List<Product> findNew() {
       List<Product> nList= hiberneteDao.findByPage("from Product order by pdate desc",null,0,10);
//       log.info("nList------------"+nList.size());
       return nList;
    }
    @Override
    public void update(Product newproduct) {

        hiberneteDao.update(newproduct);
    }


    ////通过cid 一级分类id找商品
    @Override
    public PageUtil<Product> findByCid(int cid, int page) {
        Object[] objects=new Object[]{cid};
        //获取总记录数
        int count=hiberneteDao.getCount("select count(*) from Product where categorysecondByCsid.categoryByCid.cid = ? ",objects);
        //创建对象
        PageUtil<Product> pu=new PageUtil<Product>(count,16); //一页16个
        pu.setPage(page);
        //查询
        pu.setPageDate(hiberneteDao.findByPage("from Product where categorysecondByCsid.categoryByCid.cid = ?",objects,pu.getStartIndex(),pu.getPageSize()));

        return pu;
    }

    //通过二级id查商品
    @Override
    public PageUtil<Product> findByCsid(int cid, int page) {
        Object[] objects=new Object[]{cid};
        //获取总记录数
        int count=hiberneteDao.getCount("select count(*) from Product where categorysecondByCsid.csid = ? ",objects);
        //创建对象
        PageUtil<Product> pu=new PageUtil<Product>(count,16);
        pu.setPage(page);
        //查询
        pu.setPageDate(hiberneteDao.findByPage("from Product where categorysecondByCsid.csid = ?",objects,pu.getStartIndex(),pu.getPageSize()));

        return pu;
    }
}
