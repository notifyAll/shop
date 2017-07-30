package com.qgyshop.domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * Created by vivid on 2017/3/12.
 */
@Entity
public class Orders {
    private Integer oid;
    private Double total;
    private Date ordertime;
    private Integer state;
    private String name;
    private String phone;
    private String addr;
    private Collection<Orderitem> orderitemsByOid;
    private User userByUid;

    @Id
    @Column(name = "oid")
    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    @Basic
    @Column(name = "total")
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Basic
    @Column(name = "ordertime")
    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    @Basic
    @Column(name = "state")
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "addr")
    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Orders orders = (Orders) o;

        if (oid != null ? !oid.equals(orders.oid) : orders.oid != null) return false;
        if (total != null ? !total.equals(orders.total) : orders.total != null) return false;
        if (ordertime != null ? !ordertime.equals(orders.ordertime) : orders.ordertime != null) return false;
        if (state != null ? !state.equals(orders.state) : orders.state != null) return false;
        if (name != null ? !name.equals(orders.name) : orders.name != null) return false;
        if (phone != null ? !phone.equals(orders.phone) : orders.phone != null) return false;
        if (addr != null ? !addr.equals(orders.addr) : orders.addr != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = oid != null ? oid.hashCode() : 0;
        result = 31 * result + (total != null ? total.hashCode() : 0);
        result = 31 * result + (ordertime != null ? ordertime.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (addr != null ? addr.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "ordersByOid")
    public Collection<Orderitem> getOrderitemsByOid() {
        return orderitemsByOid;
    }

    public void setOrderitemsByOid(Collection<Orderitem> orderitemsByOid) {
        this.orderitemsByOid = orderitemsByOid;
    }

    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    public User getUserByUid() {
        return userByUid;
    }

    public void setUserByUid(User userByUid) {
        this.userByUid = userByUid;
    }
}
