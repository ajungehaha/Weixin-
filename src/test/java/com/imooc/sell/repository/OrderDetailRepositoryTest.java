package com.imooc.sell.repository;

import com.imooc.sell.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/6 15:42
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    OrderDetailRepository repository;

    public static final String orderid = "111112019030603";

    @Test
    public void saveProductDetils()
    {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("1233456");
        orderDetail.setOrderId(orderid);
        orderDetail.setProductIcon("http://XXXXXX.jpg");
        orderDetail.setProductId("031389898");
        orderDetail.setProductName("牛栏山白酒");
        orderDetail.setProductPrice(new BigDecimal(15));
        orderDetail.setProductQuantity(2);

        OrderDetail result  = repository.save(orderDetail);

        Assert.assertNotNull(result);
    }
    @Test
    public void findByOrderId()
    {
        List<OrderDetail> page = repository.findByOrderId(orderid);
        Assert.assertNotEquals(0,page.size());
    }

}