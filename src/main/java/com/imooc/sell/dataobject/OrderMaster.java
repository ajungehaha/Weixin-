package com.imooc.sell.dataobject;

import com.imooc.sell.enums.OrderPayStatusEnum;
import com.imooc.sell.enums.OrderStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/4 21:24
 * @Version 1.0
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    @Id
    /*订单id*/
    private String orderId;

    /*买家名称*/
    private String buyName;

    /*买家电话*/
    private String buyPhone;

    /*买家地址*/
    private String buyAdress;

    /*买家的微信openid*/
    private String buyOpenid;

    /*订单总金额*/
    private BigDecimal orderAmount;

    /*订单状态 默认0 新下单 */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /*支付状态 默认0 未支付*/
    private Integer payStatus = OrderPayStatusEnum.WAITPAY.getCode();

    /*创建时间*/
    private Date createTime;

    /*修改时间*/
    private Date updateTime;

}
