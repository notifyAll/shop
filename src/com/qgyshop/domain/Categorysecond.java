package com.qgyshop.domain;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by vivid on 2017/3/12.
 */
@Entity
public class Categorysecond {
    private Integer csid;
    private String csname;
    private Category categoryByCid;
    private Collection<Product> productsByCsid;

    @Id
    @Column(name = "csid")
    public Integer getCsid() {
        return csid;
    }

    public void setCsid(Integer csid) {
        this.csid = csid;
    }

    @Basic
    @Column(name = "csname")
    public String getCsname() {
        return csname;
    }

    public void setCsname(String csname) {
        this.csname = csname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Categorysecond that = (Categorysecond) o;

        if (csid != null ? !csid.equals(that.csid) : that.csid != null) return false;
        if (csname != null ? !csname.equals(that.csname) : that.csname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = csid != null ? csid.hashCode() : 0;
        result = 31 * result + (csname != null ? csname.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "cid", referencedColumnName = "cid")
    public Category getCategoryByCid() {
        return categoryByCid;
    }

    public void setCategoryByCid(Category categoryByCid) {
        this.categoryByCid = categoryByCid;
    }

    @OneToMany(mappedBy = "categorysecondByCsid")
    public Collection<Product> getProductsByCsid() {
        return productsByCsid;
    }

    public void setProductsByCsid(Collection<Product> productsByCsid) {
        this.productsByCsid = productsByCsid;
    }
}
