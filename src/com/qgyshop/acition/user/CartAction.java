package com.qgyshop.acition.user;



import com.opensymphony.xwork2.ActionSupport;
import com.qgyshop.service.ProductService;
import com.qgyshop.util.cart.Cart;
import com.qgyshop.util.cart.CartItem;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;


/**
 * Created by vivid on 2017/3/21.
 */
@Controller
@Scope("prototype")
public class CartAction extends ActionSupport{
    //这里就只接受了 商品id 和数量
    private int pid;
    private int count;

    private String msg;

    public int getPid() {
        return pid;
    }
    public void setPid(int pid) {
        this.pid = pid;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Resource
    ProductService productService; //商品事物
//添加到购物车 这次这里简略了 就放到session中 并没有放到后台数据库中
    public String addCart(){
        //购物车这块 我需要自己封装数据
        //购物车对象 中应该有许多的 购物车模块 购物车模块中 包含着商品信息 商品数量等

        //1先封装一个cartitem对象
        CartItem cartItem=new CartItem();
        cartItem.setCount(count);
        cartItem.setProduct(productService.findById(pid));

        //添加到cart对象中 cart从session中获取
        Cart cart=getCart();
        //由于已经在session中 这个只是对象的引用所以无需再次保存到session中
        cart.addCart(cartItem);

        return "addCart";
    }

    /**
     * 清空购物车
     */
    public String clearCart(){
        //页面的条件是长度为0 不知道这个全删的话对象都没了 应该会出现空指针异常 。。完成后测试一下
//        ServletActionContext.getRequest().getSession().removeAttribute("cart");
        Cart cart=getCart();
        cart.clearCart();

        return "clearCart";
    }
    /**
     * 从session中获取 购物车对象
     * @return
     */
    private Cart getCart() {
        Cart cart= (Cart) ServletActionContext.getRequest().getSession().getAttribute("cart");
//        两种方式都行 ActionContext是ServletActionContext的爹
//                                 ActionContext.getContext().getSession().get("cart");

        //如果是空则创建一个对象 并且放到session中
        if (cart==null){
            cart=new Cart();
            ServletActionContext.getRequest().getSession().setAttribute("cart",cart);
        }

        return cart;
    }

//去购物车页面 由于数据都在session中。。


    //前往购物车界面
    public String myCart(){
        return "myCart";
    }
}
