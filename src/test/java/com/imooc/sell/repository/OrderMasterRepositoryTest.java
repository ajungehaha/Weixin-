package com.imooc.sell.repository;

import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.utils.OrderIdUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.Pageable;
import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/6 14:54
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    OrderMasterRepository repository;

    public static final String openID = "11111";
    @Test
    public void saveOrderMaster()
    {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setBuyAdress("河北省张家口市蔚县");
        orderMaster.setBuyName("小高");
        orderMaster.setBuyOpenid(openID);
        orderMaster.setBuyPhone("181818181818");
        orderMaster.setOrderAmount(new BigDecimal(3));
        orderMaster.setOrderId(OrderIdUtils.getOrderId(openID));

        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyOpenid()
    {
        PageRequest pageRequest =PageRequest.of(0, 2);
        Page<OrderMaster> page =  repository.findByBuyOpenid(openID, pageRequest);
        Assert.assertNotEquals(0, page.getTotalElements());

    }
}