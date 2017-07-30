package com.qgyshop.acition.admin;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.qgyshop.domain.Category;
import com.qgyshop.domain.Categorysecond;
import com.qgyshop.service.CategorySecondService;
import com.qgyshop.service.CategoryService;
import com.qgyshop.util.PageUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by vivid on 2017/3/16.
 */
@Controller
@Scope("prototype")
public class AdminCategorySecondAction extends ActionSupport implements ModelDriven<Categorysecond>{

    Categorysecond model=new Categorysecond();
    @Override
    public Categorysecond getModel() {
        return model;
    }

    //分页页码
    private int page;

    //service
    @Resource
    private CategorySecondService adminCategorySecondService;
    @Resource
    private CategoryService categoryService;
    public String findAll(){
       PageUtil<Categorysecond> pageUtil= adminCategorySecondService.findAll(page);
       //放到栈里
        ActionContext.getContext().getValueStack().set("pageUtil",pageUtil);
       return "findAll";
    }

    /**
     * 前往添加页面
     * @return
     */
     public String addPage(){
         //查出所有一级分类的信息用于二级分类
         List<Category> cList=categoryService.findAll();
         ActionContext.getContext().getValueStack().set("cList",cList);
        return "toAdd";
    }

    /**
     * 前往修改页面
     * @return
     */
    public String edit(){
        Categorysecond c=adminCategorySecondService.findById(model.getCsid());
        //值给model 由model送往页面
        model=c;

        //查出所有一级分类的信息用于二级分类
        List<Category> cList=categoryService.findAll();
        ActionContext.getContext().getValueStack().set("cList",cList);
        return "toEdit";
    }

    /**
     * 修改
     * @return
     */
    public String update(){
        Categorysecond oldCS=adminCategorySecondService.findById(model.getCsid());
//        if (!model.getCsname().equals(c.getCsname())&&model.getCsname()!=null&&!model.getCsname().trim().equals("")){
//            c.setCsname(model.getCsname());
//        }
//        if (model.getCategoryByCid().getCid()!=c.getCategoryByCid().getCid()){
//            Category category=categoryService.getById(model.getCategoryByCid().getCid());
//            c.setCategoryByCid(category);
//        }
        System.out.println("model.getCategoryByCid().getCid()-----------"+model.getCategoryByCid().getCid());
        adminCategorySecondService.update(oldCS,model);
        return "toFindAll";
    }

    /**
     * 保存
     * @return
     */
    public String save(){
        Category c=categoryService.getById(model.getCategoryByCid().getCid());
        model.setCategoryByCid(c);
        adminCategorySecondService.save(model);
        return "toFindAll";
    }

    /**
     * 删除 如果该分类底下有商品 则不允许删除 （静静的模式）
     *
     * 不过我认为 实际应该  提供一个完整删除的功能（要删就全删）
     * 当让这并不是推荐功能 删除应该让修改来代替 加个状态代码0 1 用于表示客户端可见不可见
     * 想拿出来的时候就可见 实在没用就删 结果没用的还不能删 客户端还看得见。。。（下次再写的时候使用该策略）
     * @return
     */
    public String delete(){
        Categorysecond c=adminCategorySecondService.findById(model.getCsid());
        adminCategorySecondService.delete(c);
        return "toFindAll";
    }
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}

