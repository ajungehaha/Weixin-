package com.imooc.sell.serviceImpl;

import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dto.OrderDetailsDTO;
import com.imooc.sell.dto.OrderMasterDTO;
import com.imooc.sell.enums.OrderPayStatusEnum;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/7 15:07
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    public static final String BUYER_OPENID = "110110";

    public static final String ORDERID = "201903071615073331376183";

    @Autowired
    private OrderService orderService;

    @Test
    public void createOrder() {
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        orderMasterDTO.setBuyName("老高高高");
        orderMasterDTO.setBuyAdress("河北省蔚县");
        orderMasterDTO.setBuyPhone("111223333111");
        orderMasterDTO.setBuyOpenid(BUYER_OPENID);

        List<OrderDetail> orderDetailList = new ArrayList<>();

        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProductQuantity(3);
        orderDetail1.setProductId("031389898");
        OrderDetail orderDetai2 = new OrderDetail();
        orderDetai2.setProductQuantity(2);
        orderDetai2.setProductId("20202020");

        orderDetailList.add(orderDetail1);
        orderDetailList.add(orderDetai2);

        orderMasterDTO.setOrderDetailList(orderDetailList);

        OrderMasterDTO result = orderService.createOrder(orderMasterDTO);

        log.info("创建订单 result:{}",result);



    }

    @Test
    public void findOneByOrderId() {
        OrderMasterDTO orderMasterDTO = orderService.findOneByOrderId(ORDERID);
        log.info("查询单个订单 result：{}",orderMasterDTO);
        Assert.assertNotNull(orderMasterDTO);

    }

    @Test
    public void findList() {

        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<OrderMasterDTO> page = orderService.findList(BUYER_OPENID,pageRequest);
        Assert.assertNotEquals(0, page.getTotalElements());
    }

    @Test
    public void finsh() {
        OrderMasterDTO orderMasterDTO = orderService.findOneByOrderId(ORDERID);
        OrderMasterDTO result = orderService.finsh(orderMasterDTO);
        Assert.assertEquals(OrderStatusEnum.FINSH.getCode(), result.getOrderStatus());
    }

    @Test
    public void payid() {
        OrderMasterDTO orderMasterDTO = orderService.findOneByOrderId(ORDERID);
        OrderMasterDTO result = orderService.payid(orderMasterDTO);
        Assert.assertEquals(OrderPayStatusEnum.FINSHPAY.getCode(), result.getPayStatus());
    }

    @Test
    public void cancel() {
        OrderMasterDTO orderMasterDTO = orderService.findOneByOrderId(ORDERID);
        OrderMasterDTO result = orderService.cancel(ORDERID);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());


    }
    @Test
    public void list()
    {
        PageRequest pageRequest =PageRequest.of(0, 2);
        Page<OrderMasterDTO> page = orderService.findList(pageRequest);
//        Assert.assertNotEquals(0, page.getTotalElements());
        Assert.assertTrue("查询订单列表", page.getTotalElements()>0);
    }
}