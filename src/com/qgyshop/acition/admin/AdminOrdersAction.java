package com.qgyshop.acition.admin;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.qgyshop.domain.Orderitem;
import com.qgyshop.domain.Orders;
import com.qgyshop.service.OrdersService;
import com.qgyshop.util.PageUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * Created by vivid on 2017/3/17.
 */
@Controller
@Scope("prototype")
public class AdminOrdersAction extends ActionSupport implements ModelDriven<Orders>{

    private Orders model=new Orders();
    @Override
    public Orders getModel() {
        return model;
    }
    //分页
    private int page;
    public void setPage(int page) {
        this.page = page;
    }

    //事物 //订单
    @Resource
    private OrdersService ordersService;

    /**
     * 查询所有订单 按逆序查询
     * @return
     */
    public String findAll(){
        //分页查询
        PageUtil<Orders> pageUtil=ordersService.findAll(page);
        ActionContext.getContext().getValueStack().set("pageUtil",pageUtil);
        return "findAll";
    }

    public String delete(){
        Orders orders=ordersService.findById(model.getOid());
        ordersService.delete(orders);
        return "toFindAll";
    }

    /**
     * 修改订单状态
     * @return
     */
    public String updateState(){
        Orders orders=ordersService.findById(model.getOid());
        orders.setState(3);
        ordersService.update(orders);
        return "toFindAll";
    }

//     根据订单的id查询订单项:
    public String findOrderItem(){
        // 根据订单id查询订单项:
        Collection<Orderitem> list = ordersService.findOrderItem(model.getOid());
        // 显示到页面:
        ActionContext.getContext().getValueStack().set("list", list);
        // 页面跳转
        return "findOrderItem";
    }



}
