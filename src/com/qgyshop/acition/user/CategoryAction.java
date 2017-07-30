package com.qgyshop.acition.user;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.qgyshop.domain.Category;
import com.qgyshop.service.AdminService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/10.
 */
@Controller
@Scope("prototype")
public class CategoryAction extends ActionSupport implements ModelDriven<Category> {
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
    AdminService adminuserService;

    //    获得session
    ActionContext actionContext= ActionContext.getContext();
    Map session=actionContext.getSession();

}
