package com.imooc.sell.utils;

/**比较两个double是否相等
 * @Author: 阿俊哥
 * @Date: 2019/3/16 14:11
 * @Version 1.0
 */
public class MathUtil {

    public static boolean equals(double s1,double s2)
    {
        if(Math.abs(s1-s2)<0.01)
        {
            return true;
        }
        return false;
    }
}
