package com.qgyshop.error;

/**
 * Created by Administrator on 2017/3/22.
 * 用户不允许为空的异常
 */
public class UserNotNull extends Exception{
    public UserNotNull() {
        super();
    }
    public UserNotNull(String message) {
        super(message);
    }
}
