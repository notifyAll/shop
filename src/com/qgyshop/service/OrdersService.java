package com.qgyshop.service;

import com.qgyshop.domain.Orderitem;
import com.qgyshop.domain.Orders;
import com.qgyshop.domain.User;
import com.qgyshop.error.CartNotNull;
import com.qgyshop.error.UserNotNull;
import com.qgyshop.util.PageUtil;
import com.qgyshop.util.cart.Cart;

import java.util.Collection;

/**
 * Created by vivid on 2017/3/17.
 */
public interface OrdersService {
    PageUtil<Orders> findAll(int page);

    Orders findById(Integer oid);

    void delete(Orders orders);

    void save(Orders model);

    void update(Orders orders);

    Collection<Orderitem> findOrderItem(Integer oid);

    Orders save(User user, Cart cart) throws UserNotNull, CartNotNull;

    PageUtil<Orders> findByUid(int page, Integer uid);
}

