package com.qgyshop.service;

import com.qgyshop.domain.Product;
import com.qgyshop.util.PageUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/3/17.
 */
public interface ProductService {
    void save(Product model);

    PageUtil<Product> findAll(int page);

    Product findById(Integer pid);

    void delete(Product product);

    List<Product> findHot();

    List<Product> findNew();

    void update(Product newproduct);


    //通过cid 一级分类找商品
    PageUtil<Product> findByCid(int cid, int page);

    PageUtil<Product> findByCsid(int cid, int page);
}
