package com.imooc.sell.enums;

import lombok.Getter;
import org.apache.commons.lang3.ThreadUtils;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/4 21:32
 * @Version 1.0
 */
@Getter
public enum OrderStatusEnum implements CodeEnum {
        NEW(0,"新下单"),
        FINSH(1,"完结订单"),
        CANCEL(2,"订单已取消"),
    ;
    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    };




}
