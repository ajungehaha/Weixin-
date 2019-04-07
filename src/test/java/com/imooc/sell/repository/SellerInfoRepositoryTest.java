package com.imooc.sell.repository;

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
 * @Date: 2019/3/20 15:30
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {

    public static final String OPENID = "ABC";

    @Autowired
    SellerInfoRepository sellerInfoRepository;

    @Test
    public void save()
    {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setOpenid(OPENID);
        sellerInfo.setUserName("aaa");
        sellerInfo.setPassword("aaa");
        SellerInfo result = sellerInfoRepository.save(sellerInfo);
        Assert.assertNotNull(result);
    }


    @Test
    public void findbyopenid()
    {
        SellerInfo result  = sellerInfoRepository.findByOpenid(OPENID);
        Assert.assertNotNull(result);
    }
}