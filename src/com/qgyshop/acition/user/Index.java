package com.qgyshop.acition.user;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.qgyshop.domain.Category;
import com.qgyshop.domain.Categorysecond;
import com.qgyshop.domain.Product;
import com.qgyshop.service.CategorySecondService;
import com.qgyshop.service.CategoryService;
import com.qgyshop.service.ProductService;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by vivid on 2017/3/12.
 */
@Controller
public class Index extends ActionSupport {
    //一级分类事物
    @Resource
    private CategoryService categoryService;
    //二级分类事物
    @Resource
    private CategorySecondService categorySecondService;
    //商品事物
    @Resource
    private ProductService productService;

    @Override
    public String execute() throws Exception {


//        1查询一级分类
          List<Category> cList= categoryService.findAll();
          //放入session中
          ActionContext.getContext().getSession().put("cList",cList);
////        2查询二级分类
//          List<Categorysecond> categorysecondList= (List<Categorysecond>) categorySecondService.findAll();
//        3获取热门商品
         List<Product> hList=productService.findHot();
         //存到栈中
        ActionContext.getContext().getValueStack().set("hList",hList);
//         4获取最新商品
         List<Product> nList=productService.findNew();
        ActionContext.getContext().getValueStack().set("nList",nList);

        String path= ServletActionContext.getServletContext().getRealPath("/products/2");
        String path1= ServletActionContext.getServletContext().getRealPath("/");
        System.out.println(path);
        System.out.println(path1);
        return SUCCESS;
    }
}
