package com.qgyshop.acition.admin;

import com.opensymphony.xwork2.ActionSupport;
import org.springframework.stereotype.Controller;

/**
 * Created by vivid on 2017/3/12.
 * home.jsp获取各组成部分
 */
@Controller
public class AdminGetUiAction extends ActionSupport {
//  后台主页。。。界面
    public String getTop(){
        return "top";
    }
    public String getLeft(){
        return "left";
    }
    public String getWelcome(){
        return "welcome";
    }
    public String getBottom(){
        return "bottom";
    }
    public String getHome(){return "home";}
}
