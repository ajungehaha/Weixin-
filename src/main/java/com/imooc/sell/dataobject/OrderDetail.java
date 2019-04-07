package com.imooc.sell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**订单详情
 * @Author: 阿俊哥
 * @Date: 2019/3/4 21:39
 * @Version 1.0
 */
@Entity
@Data
public class OrderDetail {

    @Id
    /*订单详情id*/
    private String detailId;

    /*订单id*/
    private String orderId;

    /*商品id*/
    private String productId;

    /*商品名称*/
    private String productName;

    /*商品单价*/
    private BigDecimal productPrice;

    /*商品数量*/
    private Integer productQuantity;

    /*商品图片*/
    private String productIcon;


}
