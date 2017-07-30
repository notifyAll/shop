package com.qgyshop.myinterceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.qgyshop.domain.Adminuser;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/3/13.
 * 管理员是否登录的拦截器
 */
@Component
public class AdminInterceptor implements Interceptor {
    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
//先去session中找到管理员登录的信息
        Adminuser admin_account= (Adminuser) ActionContext.getContext().getSession().get("admin_account");

        if(admin_account==null){
//            去登录
            return "nologin";
        }else {
//放行
           return actionInvocation.invoke();
        }
    }
}
