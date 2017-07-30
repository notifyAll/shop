package com.qgyshop.acition.user;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.qgyshop.domain.Product;
import com.qgyshop.service.ProductService;
import com.qgyshop.util.PageUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created by vivid on 2017/3/21.
 */
@Controller
@Scope("prototype")
public class ProductAction extends ActionSupport implements ModelDriven<Product> {
    Product model=new Product();
    @Override
    public Product getModel() {
        return model;
    }
    //一级id
    private int cid;
    private int csid;//二级
    //分页
    private int page;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getCsid() {
        return csid;
    }

    public void setCsid(int csid) {
        this.csid = csid;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    //事物
    @Resource
    ProductService productService;


    //通过商品id查找指定id
    public String findByPid(){

        Product product=productService.findById(model.getPid());
        //通过model 返回页面
        model=product;

        return "findByPid";
    }

//    通过指定一级分类查找商品 集合 带分页。。
    public String findByCid(){
       PageUtil<Product> pageUtil= productService.findByCid(cid,page);
//       放入栈中
        ActionContext.getContext().getValueStack().set("pageUtil",pageUtil);
        return "findByCid";
    }

    public String findByCsid(){
        PageUtil<Product> pageUtil= productService.findByCsid(csid,page);
//       放入栈中
        ActionContext.getContext().getValueStack().set("pageUtil",pageUtil);
        return "findByCsid";
    }
}

