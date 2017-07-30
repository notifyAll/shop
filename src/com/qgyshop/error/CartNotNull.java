package com.qgyshop.error;

/**
 * Created by Administrator on 2017/3/22.
 */
public class CartNotNull extends Exception {
    public CartNotNull() {
    }

    public CartNotNull(String message) {
        super(message);
    }
}
