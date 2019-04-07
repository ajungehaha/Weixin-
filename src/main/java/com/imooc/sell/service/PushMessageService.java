package com.imooc.sell.service;

import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dto.OrderMasterDTO;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/21 19:56
 * @Version 1.0
 */
public interface PushMessageService {

    void templateOrderStatus(OrderMasterDTO orderMasterDTO);
}
