package com.qgyshop.acition.admin;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.qgyshop.domain.Categorysecond;
import com.qgyshop.domain.User;
import com.qgyshop.service.AdminUserService;
import com.qgyshop.util.PageUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/3/15.
 * 普通用户的管理 需要带分页 但不需要修改 管理员应该没权限操纵用户数据
 */
@Controller
@Scope("prototype")
public class AdminUserAction extends ActionSupport implements ModelDriven<Categorysecond> {

    private Categorysecond model=new Categorysecond();

    @Override
    public Categorysecond getModel() {
        return model;
    }

    //传递当前页码
    private int page;

//
    @Resource
    private AdminUserService adminUserService;

    /**
     *  查询用户 需分类 可以查找 可筛选
     */
      public String findAll(){
          PageUtil<User> pageUtil=adminUserService.findAll(page);
//          将其返回至浏览器 用jstl应该 还是struts2呢?
          ActionContext.getContext().getValueStack().set("pageUtil",pageUtil);
          return "findAll";
      }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
