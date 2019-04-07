package com.imooc.sell.serviceImpl;

import com.imooc.sell.dataobject.SellerInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/20 15:47
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerServiceImplTest {

    public static final String OPENID = "ABC";

    @Autowired
    SellerServiceImpl sellerService;

    @Test
    public void findSellerInfo() {
        SellerInfo result = sellerService.findSellerInfo(OPENID);
        Assert.assertNotNull(result);
    }
}