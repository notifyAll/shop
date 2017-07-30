package com.qgyshop.acition.admin;

import com.opensymphony.xwork2.ActionSupport;
import org.springframework.stereotype.Controller;

/**
 * Created by vivid on 2017/3/12.
 */
@Controller
public class AdminIndexAction extends ActionSupport {
    @Override
    public String execute() throws Exception {
        return "index";
    }
}
