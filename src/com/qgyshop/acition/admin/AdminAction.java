package com.qgyshop.acition.admin;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.qgyshop.domain.Adminuser;
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
public class AdminAction extends ActionSupport implements ModelDriven<Adminuser> {
//    接收参数
    Adminuser adminuser=new Adminuser();
    @Override
    public Adminuser getModel() {
        return adminuser;
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
    AdminService adminService;

//    获得session
    ActionContext actionContext= ActionContext.getContext();
    Map session=actionContext.getSession();



/**
 * 登录系统
 */
    public String login(){
//        数据判空的校验要放到拦截器中
        if (adminuser==null||adminuser.getUsername()==null||adminuser.getPassword()==null){
            return "login";
        }
        Adminuser adminuser1= adminService.login(adminuser);
        if (adminuser1!=null){
           session.put("admin_account",adminuser1);
           return SUCCESS;
        }else {
            msg="<script type='text/javascript'>alert('用户名或密码错误');</script>";
            return "index";
        }
    }

    /**
     * 注销
     * @return
     */
    public String logout(){
        if (session.get("admin_account")!=null){
            session.put("admin_account",null);
        }
        return "index";
    }
}
