package com.qgyshop.util;

import java.util.UUID;

/**
 * Created by vivid on 2017/3/25.
 * 生成激活码
 */
public class UUIDUtils {

    /**
     * 生成随机的字符串
     * @return
     */
    public static String getUUID() {
//     UUID.randomUUID().toString()的输出格式为 84ed20d8-371e-4612-b0bb-343d70805b04
//    .replaceAll("-","") 这个可以替换掉它自带的 "-"
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
