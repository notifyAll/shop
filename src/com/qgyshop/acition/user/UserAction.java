package com.qgyshop.acition.user;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.qgyshop.domain.User;
import com.qgyshop.service.AdminUserService;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by vivid on 2017/3/18.
 */
@Controller
@Scope("prototype")
public class UserAction extends ActionSupport implements ModelDriven<User> {
    User model=new User();
    @Override
    public User getModel() {
        return model;
    }

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    //验证码
    private String checkcode;
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    //事物
    @Resource
    private AdminUserService adminUserService;

    /**
     * 这个是 action的全局的 校验方法 对这个action的请求方法都有效
     * this.addFieldError( 使用这个给异常并返回 input 视图 需要在struts的action中配置
     * 页面中也可以获取该校验错误信息 蛮好用的 就能同时传递多个这个信息
     * <span><c:fielderror name="username"></c:fielderror> </span>
     */
//    @Override
//    public void validate() {
////        code。。。
//        if (model.getName()==null||model.getName().trim().equals("")){
//            this.addFieldError("username","用户名不允许为空");
//        }
//    }

    /**
     * 这个针对局部方法的校验 validate +方法名（首字母调成大写）
     */
//    public void validateRegist() {
////        code。。。
//        if (model.getName()==null||model.getName().trim().equals("")){
//            this.addFieldError("username","用户名不允许为空");
//        }
//    }
    //登录
//    去登录界面
    public String loginPage(){
        return "loginPage";
    }

    /**
     *   登录方法
     */

    public String login(){
        User userAccount=adminUserService.findByAccount(model);
        if (userAccount==null){
            msg="<script type='text/javascript'>alert('用户名或密码错误');</script>";
            this.addActionError("用户名或密码错误");
            return "loginPage";
        }else {
            //判断用户是否激活
            if (userAccount.getState()==0){
                this.addActionError("该帐号未激活请去邮箱激活");
                return "loginPage"; //返回登录
            }
            //用户信息放入session中
            ServletActionContext.getRequest().getSession().setAttribute("userAccount",userAccount);
            return "toIndex"; //去主页
        }
    }
    //退出登录
    public String quit(){
        User user= (User) ServletActionContext.getRequest().getSession().getAttribute("userAccount");
        if (user!=null){
            ServletActionContext.getRequest().getSession().removeAttribute("userAccount");
        }
        msg="<script type='text/javascript'>alert('成功退出');</script>";
        return "quit";
    }
// 注册
   //前往注册界面
    public String registPage(){
        return "registPage";
    }


    /**
     * 注册用户
     * @return
     */
    public  String regist(){

//        先验证验证码是否正确
        //从session中获取验证码
        String checkcode1= (String) ServletActionContext.getRequest().getSession().getAttribute("checkcode");

        if (!checkcode.equalsIgnoreCase(checkcode1)){
            this.addActionError("验证码输入错误");
            return "checkcodeFail";
        }

        //存用户
        adminUserService.save(model);
        //提示成功
        this.addActionMessage("注册成功请去邮箱激活");
        return "registSuccess";
    }


//    验证用户名是否存在
    public String findByName() throws IOException {
        //获得response 响应对象
        HttpServletResponse response= ServletActionContext.getResponse();

        //重要  设置返回类型这里是html 和编码格式 重要否则乱码
        response.setContentType("text/html;charset=UTF-8");

        String name=model.getUsername();
//        System.out.println("ajax请求成功++++++++++++"+name);
        //判断传过来的用户名是否为空
        if (name==null||name.trim().equals("")){
            response.getWriter().print("<font color='red'>用户名不允许为空</font>");
            return NONE;
        }

        //判断用户名是否存在
       boolean username_isexist=adminUserService.usernameIsexist(name);
       if (username_isexist){
           response.getWriter().print("<font color='red'>用户名已经存在</font>");
       }else {
           response.getWriter().print("<font color='green'>用户名可以使用</font>");
       }
//     if (name!=null&&!name.trim().equals("")){
//         if (name.equals("admin")){
//             //这两种方法都没卵用 传不过去 s标签和el都不行
////             msg="用户名已经存在";
////             this.addActionMessage("用户已存请重新输入");
//             //可以通过响应返回html代码
//             response.getWriter().print("<font color='red'>用户名已经存在</font>");
//         }else {
//             response.getWriter().print("<font color='green'>用户名可以使用</font>");
//         }
//     }
     return NONE;
    }
    /**
     * 激活用户
     * @return
     */
    public String active(){
        String code=model.getCode();
        if (code==null||code.trim().equals("")){
            this.addActionMessage("激活失败激活码有误");
            return "activemsg";
        }

        //去数据库查有没有改激活码的用户
        User user= adminUserService.findByCode(code);

        if (user==null){
            this.addActionMessage("激活失败激活码有误");
        }else {
            //修改用户的状态 并的登录
            user.setState(1);
            user.setCode(null);
            adminUserService.update(user);

            //激活成功
            this.addActionMessage("激活成功请登录");
            return "loginPage"; //去登录
        }
        return "activemsg";
    }
}
