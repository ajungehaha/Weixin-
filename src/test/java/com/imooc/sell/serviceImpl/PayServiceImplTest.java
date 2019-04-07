package com.imooc.sell.serviceImpl;

import com.imooc.sell.dto.OrderMasterDTO;
import com.imooc.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/15 21:20
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {
    @Autowired
    OrderService orderService;

    @Autowired
    PayServiceImpl payService;

    public static final String ORDERID="201903082154157621277882";
    @Test
    public void create()
    {

        OrderMasterDTO orderMasterDTO = orderService.findOneByOrderId(ORDERID);
        payService.create(orderMasterDTO);
    }
}