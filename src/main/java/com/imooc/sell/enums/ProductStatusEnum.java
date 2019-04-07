package com.imooc.sell.enums;

import lombok.Getter;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/2 15:24
 * @Version 1.0
 */

@Getter
/**
 * 创建一个枚举类型 辨识上架和下架的标签
 */
public enum ProductStatusEnum implements CodeEnum{
    UP(0,"在架"),DOWN(1,"下架");

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    };


}
