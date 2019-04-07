package com.imooc.sell.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/6 18:35
 * @Version 1.0
 */
@Data
public class OrderDetailsDTO {

    private String productId;
    private Integer productQuantity;

    public OrderDetailsDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
