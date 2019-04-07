package com.imooc.sell.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.enums.OrderPayStatusEnum;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.utils.CodeUtils;
import lombok.Data;
import org.aspectj.weaver.patterns.PatternNode;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.text.Format;
import java.util.Date;
import java.util.List;

/**订单的在层次间传输的数据对象
 * @Author: 阿俊哥
 * @Date: 2019/3/6 17:03
 * @Version 1.0
 */
@Data

public class OrderMasterDTO {

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
    private Integer orderStatus ;

    /*支付状态 默认0 未支付*/
    private Integer payStatus;
    @JsonFormat(pattern = "yyMMddhhmm")
    /*创建时间*/
    private Date createTime;
    @JsonFormat(pattern = "yyMMddhhmm")
    /*修改时间*/
    private Date updateTime;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
    /*订单详情列表*/
    private List<OrderDetail> orderDetailList;

    //会返回json的
    @JsonIgnore
    public OrderPayStatusEnum getPayStatusCode()
    {
        return CodeUtils.getCode(payStatus, OrderPayStatusEnum.class);
    }
    @JsonIgnore
    public OrderStatusEnum getOrderStatusCode()
    {
        return CodeUtils.getCode(orderStatus, OrderStatusEnum.class);
    }


}
