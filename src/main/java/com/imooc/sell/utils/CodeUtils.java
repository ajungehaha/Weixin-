package com.imooc.sell.utils;

import com.imooc.sell.enums.CodeEnum;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/16 20:58
 * @Version 1.0
 */
public class CodeUtils {
    public static <T extends CodeEnum> T getCode(Integer code, Class<T> enumClass)
    {
        for(T each :enumClass.getEnumConstants())
        {
            if(each.getCode().equals(code))
            {
                return each;
            }
        }
        return  null;
    }
}
