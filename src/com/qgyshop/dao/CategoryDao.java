package com.qgyshop.dao;

import com.qgyshop.domain.Category;

import java.util.List;

/**
 * Created by Administrator on 2017/3/10.
 */
public interface CategoryDao {

    List<Category> findAll();

    Category getById(Integer cid);

    void delete(Category category1);

    void save(Category category);
}
