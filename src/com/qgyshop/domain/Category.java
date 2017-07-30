package com.qgyshop.domain;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by vivid on 2017/3/12.
 */
@Entity
public class Category {
    private Integer cid;
    private String cname;
    private Collection<Categorysecond> categorysecondsByCid;

    @Id
    @Column(name = "cid")
    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    @Basic
    @Column(name = "cname")
    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (cid != null ? !cid.equals(category.cid) : category.cid != null) return false;
        if (cname != null ? !cname.equals(category.cname) : category.cname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cid != null ? cid.hashCode() : 0;
        result = 31 * result + (cname != null ? cname.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "categoryByCid")
    public Collection<Categorysecond> getCategorysecondsByCid() {
        return categorysecondsByCid;
    }

    public void setCategorysecondsByCid(Collection<Categorysecond> categorysecondsByCid) {
        this.categorysecondsByCid = categorysecondsByCid;
    }
}
