package com.imooc.sell.enums;

import lombok.Getter;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/4 21:35
 * @Version 1.0
 */
@Getter
public enum OrderPayStatusEnum implements CodeEnum {
    WAITPAY(0,"未支付"),
    FINSHPAY(1,"支付完成"),
    ;
    private Integer code;

    private String message;

    OrderPayStatusEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    };
}
