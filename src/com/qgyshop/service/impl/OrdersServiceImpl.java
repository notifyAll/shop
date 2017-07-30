package com.qgyshop.service.impl;

import com.qgyshop.dao.HiberneteDao;
import com.qgyshop.domain.Orderitem;
import com.qgyshop.domain.Orders;
import com.qgyshop.domain.User;
import com.qgyshop.error.CartNotNull;
import com.qgyshop.error.UserNotNull;
import com.qgyshop.service.OrdersService;
import com.qgyshop.util.PageUtil;
import com.qgyshop.util.cart.Cart;
import com.qgyshop.util.cart.CartItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by vivid on 2017/3/17.
 */
@Service
@Transactional
public class OrdersServiceImpl implements OrdersService{
    @Resource
    private HiberneteDao<Orders> hiberneteDao;

    private PageUtil<Orders> pageUtil;

    @Override
    public PageUtil<Orders> findAll(int page) {
        if (pageUtil==null){
            int size=hiberneteDao.getCount("select count(*) from Orders",null);
            pageUtil=new PageUtil<>(size,20);
        }
        pageUtil.setPage(page);
        pageUtil.setPageDate(hiberneteDao.findByPage("from Orders  order by ordertime desc" ,null,pageUtil.getStartIndex(),pageUtil.getPageSize()));
        return pageUtil;
    }


    @Override
    public Orders findById(Integer oid) {
        Orders orders=hiberneteDao.findById(Orders.class,oid);
        return orders;
    }

    @Override
    public void delete(Orders orders) {

        pageUtil.setSize(pageUtil.getSize()-1);
        hiberneteDao.delete(orders);
    }
   //用在后台
    @Override
    public void save(Orders model) {
        hiberneteDao.save(model);
        pageUtil.setSize(pageUtil.getSize()+1);
    }

    @Override
    public void update(Orders orders) {
        hiberneteDao.update(orders);
    }

    @Override
    public Collection<Orderitem> findOrderItem(Integer oid) {
        Orders orders=hiberneteDao.findById(Orders.class,oid);

        //返回订单中对应的商品 由于偷懒了关了懒加载 所以。。。。。。。 实测得要是懒加载开着 即使是servlet也不能再去加载 提示session为空
        return orders.getOrderitemsByOid();
    }

    @Override
    public Orders save(User user, Cart cart) throws UserNotNull, CartNotNull {
        if (user==null){
            //用户不能为空
            throw new UserNotNull("请先登录");
        }
        if (cart.getCartItems().size()==0||cart==null){
            throw new CartNotNull("还没有购物 请先去购物");
        }

        Orders orders=new Orders();
        //设置用户信息
        orders.setName(user.getName());
        orders.setAddr(user.getAddr());
        orders.setPhone(user.getPhone());
        orders.setUserByUid(user);
        //总金额
        orders.setTotal(cart.getTotal());

        //设置商品信息
        Collection<Orderitem> orderitemsByOid=new HashSet<Orderitem>();
        for (CartItem cartItem:cart.getCartItems()) {
            Orderitem orderitem=new Orderitem();
//            CartItem cartItem=((ArrayList<CartItem>)cart.getCartItems()).get(i);
            orderitem.setCount(cartItem.getCount());
            orderitem.setSubtotal(cartItem.getSubtotal());
            orderitem.setProductByPid(cartItem.getProduct());
            orderitem.setOrdersByOid(orders);
            //加入集合中
            orderitemsByOid.add(orderitem);
        }
        orders.setOrderitemsByOid(orderitemsByOid);


        //时间
        orders.setOrdertime(new Date());
        //状态
        orders.setState(1);

//        然后保存
        hiberneteDao.save(orders);

        return orders;
    }

    //查询指定用户的购买记录 分页
    @Override
    public PageUtil<Orders> findByUid(int page, Integer uid) {
        Object[] objects=new Object[]{uid};
        int size=hiberneteDao.getCount("select count(*) from Orders where userByUid.uid= ? order by  ordertime desc",objects);
        PageUtil<Orders> pu=new PageUtil<>(size,6); //分页设置为6笔
        pu.setPage(page);
        pu.setPageDate(hiberneteDao.findByPage(" from Orders where userByUid.uid= ? order by  ordertime desc",objects,pu.getStartIndex(),pu.getPageSize()));
        return pu;
    }

}
