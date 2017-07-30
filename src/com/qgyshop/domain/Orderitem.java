package com.qgyshop.domain;

import javax.persistence.*;

/**
 * Created by vivid on 2017/3/12.
 */
@Entity
public class Orderitem {
    private Integer itemid;
    private Integer count;
    private Double subtotal;
    private Product productByPid;
    private Orders ordersByOid;

    @Id
    @Column(name = "itemid")
    public Integer getItemid() {
        return itemid;
    }

    public void setItemid(Integer itemid) {
        this.itemid = itemid;
    }

    @Basic
    @Column(name = "count")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Basic
    @Column(name = "subtotal")
    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Orderitem orderitem = (Orderitem) o;

        if (itemid != null ? !itemid.equals(orderitem.itemid) : orderitem.itemid != null) return false;
        if (count != null ? !count.equals(orderitem.count) : orderitem.count != null) return false;
        if (subtotal != null ? !subtotal.equals(orderitem.subtotal) : orderitem.subtotal != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = itemid != null ? itemid.hashCode() : 0;
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (subtotal != null ? subtotal.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "pid", referencedColumnName = "pid")
    public Product getProductByPid() {
        return productByPid;
    }

    public void setProductByPid(Product productByPid) {
        this.productByPid = productByPid;
    }

    @ManyToOne
    @JoinColumn(name = "oid", referencedColumnName = "oid")
    public Orders getOrdersByOid() {
        return ordersByOid;
    }

    public void setOrdersByOid(Orders ordersByOid) {
        this.ordersByOid = ordersByOid;
    }
}
