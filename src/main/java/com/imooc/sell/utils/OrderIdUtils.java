package com.imooc.sell.utils;

import java.security.PrivateKey;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/6 14:59
 * @Version 1.0
 */
public class OrderIdUtils {

    /**
     * 一个threadlocal 对象 保证simpledateFromat的线程安全
     */
    private static ThreadLocal<SimpleDateFormat> format1 = new ThreadLocal();

    /**
     * 构造一个orderId
     * @param openId
     * @return
     */
    public static String getOrderId(String openId)
    {
        Date date = new Date();

        Format format = new SimpleDateFormat("yyyyMMddhhmmss");
        format1.set((SimpleDateFormat) format);
        String dateId = format1.get().format(date);
        return openId+dateId;
    }
}
