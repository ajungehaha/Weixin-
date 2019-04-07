package com.imooc.sell.service;

import com.imooc.sell.dto.OrderMasterDTO;
import com.lly835.bestpay.model.PayResponse;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/11 19:59
 * @Version 1.0
 */
public interface PayService {

     PayResponse create(OrderMasterDTO orderMasterDTO);

     PayResponse notify(String notifyData);

     void refund(OrderMasterDTO orderMasterDTO);
}
