package com.imooc.sell.repository;

import com.imooc.sell.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/2 13:33
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRespositoryTest {
    @Autowired
    ProductInfoRespository respository;

    @Test
    public void saveProduct()
    {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductName("牛奶");
        productInfo.setProductPrice(new BigDecimal(5.5));
        productInfo.setProductDescription("很好喝的牛奶");
        productInfo.setProductStock(10);
        productInfo.setProductIcon("http://xxxxxx.jpg");
        productInfo.setProductStatus(1);
        ProductInfo result = respository.save(productInfo);
        Assert.assertNotNull(result);

    }
}