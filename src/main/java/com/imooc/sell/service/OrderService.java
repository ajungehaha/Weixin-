package com.imooc.sell.service;

import com.imooc.sell.dto.OrderMasterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**订单service
 * @Author: 阿俊哥
 * @Date: 2019/3/6 17:00
 * @Version 1.0
 */

public interface OrderService {

    /*创建一个新的订单*/
    OrderMasterDTO createOrder(OrderMasterDTO orderMasterDTO);

    /*查询一个订单*/
    OrderMasterDTO findOneByOrderId(String orderId);
    /*查询所有的订单 传输openid*/
    Page<OrderMasterDTO> findList(String openid,Pageable pageable);
    /*完结订单*/
    OrderMasterDTO finsh(OrderMasterDTO orderMasterDTO);

    /*已支付订单*/
    OrderMasterDTO payid(OrderMasterDTO orderMasterDTO);
    /*取消订单*/
    OrderMasterDTO cancel(String orderId);
    /*查询所有的订单列表*/
    Page<OrderMasterDTO> findList(Pageable pageable);

}
