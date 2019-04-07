package com.imooc.sell.service;

import com.imooc.sell.dto.OrderMasterDTO;

import java.security.PrivateKey;

/**卖家service
 * @Author: 阿俊哥
 * @Date: 2019/3/10 14:24
 * @Version 1.0
 */

public interface BuyerService {
    //接口不允许有局部变量
    //买家查询订单详情
    OrderMasterDTO findOrderOne(String orderid,String openid);

    //买家取消订单
    OrderMasterDTO cancelOrder(String orderid,String openid);
}
