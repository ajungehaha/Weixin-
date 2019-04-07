package com.imooc.sell.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 获得一个随机的键值的工具
 * @Author: 阿俊哥
 * @Date: 2019/3/6 18:36
 * @Version 1.0
 */
public class KeyUtils {
//保证线程安全
    public static synchronized String getUniqueKey(){
        Random random = new Random();
        /*获取一个随机的六位数*/
        Integer number = random.nextInt(900000)+1000000;

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");//设置日期格式

        return df.format(new Date()) + String.valueOf(number);
    }
}
