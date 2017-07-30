package com.qgyshop.util.cart;

import com.qgyshop.domain.Product;

/**
 * Created by vivid on 2017/3/21.
 * 购物车中的购物项
 */
public class CartItem {

    private Product product; //商品信息
    private int count; //商品数量
    private double subtotal; //该商品的小计

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    //商品小计 获取时直接计算
    public double getSubtotal() {
        return count*product.getShopPrice();
    }

}
