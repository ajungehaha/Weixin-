package com.imooc.sell.serviceImpl;

import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dto.OrderMasterDTO;
import com.imooc.sell.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/21 20:23
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageServiceImplTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private PushMessageServiceImpl pushMessageService;

    @Test
    public void templateOrderStatus() {
        OrderMasterDTO orderMasterDTO = orderService.findOneByOrderId("201903082154157621277882");
        pushMessageService.templateOrderStatus(orderMasterDTO);
    }
}