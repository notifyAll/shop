package com.qgyshop.acition.user;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.qgyshop.domain.Orders;
import com.qgyshop.domain.User;
import com.qgyshop.error.CartNotNull;
import com.qgyshop.error.UserNotNull;
import com.qgyshop.service.OrdersService;
import com.qgyshop.util.PageUtil;
import com.qgyshop.util.cart.Cart;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;


/**
 * Created by Administrator on 2017/3/22.
 */
public class OrderAction extends ActionSupport implements ModelDriven<Orders> {
    Orders model=new Orders();
    @Override
    public Orders getModel() {
        return model;
    }

    private String msg;
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    private int page; //分页
    public void setPage(int page) {
        this.page = page;
    }
    public int getPage() {
        return page;
    }

    @Resource
    OrdersService ordersService;
    /**
     * 保存订单
     * @return
     */
    public String saveOrder(){
        //用户 和购物车对象
        Cart cart= (Cart) ServletActionContext.getRequest().getSession().getAttribute("cart");
        User user= (User) ServletActionContext.getRequest().getSession().getAttribute("userAccount");

        try {
            model= ordersService.save(user,cart);
        } catch (UserNotNull userNotNull) {
            this.addActionError(userNotNull.getMessage());
            msg=userNotNull.getMessage();
//            msg="<script type='text/javascript'>alert('"+msg+"');</script>";
            return INPUT;
        } catch (CartNotNull cartNotNull) {
            this.addActionError(cartNotNull.getMessage());
            msg=cartNotNull.getMessage();
//            msg="<script type='text/javascript'>alert('"+msg+"');</script>";
            return INPUT;
        }

//        清空购物车
        cart.clearCart();
        return "saveOrder";
    }

    /**
     * 查找当前用户的所有订单 时间最新的在上面
     * @return
     */
    public String findByUid(){
        User userAccount= (User) ServletActionContext.getRequest().getSession().getAttribute("userAccount");
        //用户未登录去登录界面 当然可以放到拦截器里
        if (userAccount==null ){
            msg="<script type='text/javascript'>alert('请登录');</script>";
            return "toLogin";
        }

        //查询订单
        PageUtil<Orders> pageUtil=ordersService.findByUid(page,userAccount.getUid());

        //放入栈中
        ActionContext.getContext().getValueStack().set("pageUtil",pageUtil);

        return "findByUid";
    }

    //查找指定的订单
    public String findByOid(){
        //按id查订单
        Orders orders=ordersService.findById(model.getOid());
        model=orders;
//        ActionContext.getContext().getValueStack().push(orders);
// 返回支付页面
        return "payOrder";
    }

//    支付
    public String payOrder(){
        //考虑用户是否修改了地址之类的信息
        Orders orders=ordersService.findById(model.getOid());
        orders.setAddr(model.getAddr());
        orders.setName(model.getName());
        orders.setPhone(model.getPhone());

        orders.setState(2); //这里暂时直接设置为支付成功
        ordersService.update(orders);
        //支付模块 进行支付业务


        //
//        跳转
        this.addActionMessage("支付成功");
        return "msg";
    }

    public String callBack(){

        return NONE;
    }
}
