package com.qgyshop.acition.admin;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.qgyshop.domain.Category;
import com.qgyshop.service.CategoryService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/10.
 */
@Controller
@Scope("prototype")
public class AdminCategoryAction extends ActionSupport implements ModelDriven<Category> {
    Category category=new Category();

    @Override
    public Category getModel() {
        return category;
    }

    //    消息
    String msg;
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    //    服务
    @Resource
    CategoryService categoryService;

    //    获得session
    ActionContext actionContext= ActionContext.getContext();
    Map session=actionContext.getSession();
    Map request= (Map) actionContext.get("request");


/**
 * 查找所有一级分类
 */

public String findAll(){
    List<Category> categories =categoryService.findAll();
    session.put("categories",categories);
    ActionContext.getContext().getValueStack().set("categories", categories);
    return "findAll";
}

/**
 * 修改指定一级分类
 */
public String update(){
    Category category1=categoryService.getById(category.getCid());
//    设置参数
    category1.setCname(category.getCname());
//    提交修改
    categoryService.update(category1);

    return "toFindAll";
}


/**
 * 修改编辑的界面
 */
public String edit(){
    //如果没有传id则这是新增 否则为修改
    if (category.getCid()==null){
        return "addUI";
    }else {
        Category category1=categoryService.getById(category.getCid());
//       放入request中
//        el jstl 获取
//        request.put("categoryEdit",category1);
        ActionContext.getContext().getValueStack().set("categoryEdit", category1);
//        System.out.println("category1+++++++++++++++++++++++++++++" +category1.getCategorysecondsByCid().size());
//       可通过 s标签获取ModelDriven
        category=category1;
        return "editUI";
    }
}
    /**
 * 通过id删除指定一级分类
 */
public String delete(){
//    查询该分类
   Category category1= categoryService.getById(category.getCid());

//    验证
    if (category1==null){
        msg="<script type='text/javascript'>alert('删除失败 没有找到该一级分类');</script>";
        return "findAll";
    }

//    删除分类
    try {
        categoryService.delete(category1);
    } catch (Exception e) {
        msg="<script type='text/javascript'>alert('删除失败');</script>";

//        e.printStackTrace();
    }
    return "toFindAll";
}
/**
 * 添加一级分类
 */
    public String save(){
        categoryService.save(category);
        return "toFindAll";
    }
}
