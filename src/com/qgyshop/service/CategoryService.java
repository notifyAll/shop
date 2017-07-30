package com.qgyshop.service;


import com.qgyshop.domain.Category;

import java.util.List;

/**
 * Created by Administrator on 2017/3/10.
 */
public interface CategoryService {
    List<Category> findAll();

    Category getById(Integer cid);

    void delete(Category category1) throws Exception;

    void save(Category category);

    void update(Category category);
}
