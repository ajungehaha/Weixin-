package com.imooc.sell.serviceImpl;

import com.imooc.sell.dto.OrderMasterDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.ResultException;
import com.imooc.sell.service.BuyerService;
import com.imooc.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/10 14:29
 * @Version 1.0
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;


    @Override
    public OrderMasterDTO findOrderOne(String orderid, String openid) {

       OrderMasterDTO orderMasterDTO =  orderService.findOneByOrderId(orderid);
       if(!orderMasterDTO.getBuyOpenid().trim().equalsIgnoreCase(openid.trim()))
       {
           log.error("【查询订单】 订单所属不一致 orderid = {} openid = {}",orderid,openid);
          throw  new ResultException(ResultEnum.ORDER_OPENID_ERROR);
       }


       return  orderMasterDTO;
    }

    @Override
    public OrderMasterDTO cancelOrder(String orderid, String openid) {

        OrderMasterDTO orderMasterDTO = orderService.cancel(orderid);
        if(!orderMasterDTO.getBuyOpenid().trim().equalsIgnoreCase(openid.trim()))
        {
            log.error("【查询订单】 订单所属不一致 orderid = {} openid = {}",orderid,openid);
            throw  new ResultException(ResultEnum.ORDER_OPENID_ERROR);
        }
        return orderMasterDTO;
    }
}
