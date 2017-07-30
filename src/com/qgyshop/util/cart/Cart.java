package com.qgyshop.util.cart;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by vivid on 2017/3/21.
 * 购物车对象
 */
public class Cart {
    //这里为什么用map 我猜应该是map 查询给力 此处的键其实是 商品的id 对于页面来说 pid 比序号什么更好用
    private Map<Integer,CartItem> map=new LinkedHashMap<Integer,CartItem>();

    //这方法用来获取该map所有值的列表 其实就是为了购物车页面遍历方便点 map遍历比较麻烦
    public Collection<CartItem> getCartItems(){
        return map.values();
    }


    //购物车总计
    private double total;

    public double getTotal() {
        return total;
    }

    //购物车的功能
    //1将购物项添加到购物车
    public void addCart(CartItem cartItem){
        //首先判断该购物项是否已经存在 有的话数量增加 然后计算总金额

//        获得商品id
        Integer pid=cartItem.getProduct().getPid();
        if (map.containsKey(pid)){
            //有
            CartItem cartItem1=map.get(pid);
            //设置数量
            cartItem1.setCount(cartItem.getCount());
        }else {
            //加入购物车
            map.put(pid,cartItem);
        }
        //计算总计 加上新加的商品数据
        total+=cartItem.getSubtotal();

    }

    //从购物车移除 通过id
    public  void removeCart(Integer pid){
        //删除map中的 该条记录
        CartItem cartItem=map.remove(pid);
        //总计就减去
        total-=cartItem.getSubtotal();
    }

    //清空购物车
    public void  clearCart(){
        map.clear(); //删除所有
        total=0; // 总计为zero
    }

}
