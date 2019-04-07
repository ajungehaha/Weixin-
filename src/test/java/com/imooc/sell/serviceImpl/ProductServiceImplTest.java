package com.imooc.sell.serviceImpl;

import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.validator.PublicClassValidator;
import org.mockito.internal.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/2 14:12
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {


    public static final String PRODUCTID="031389898";
    @Autowired
    ProductServiceImpl productService;
    @Test
    public void findById() {

        ProductInfo  productInfo = productService.findById("123456");
        Assert.assertNotNull(productInfo);
    }


    @Test
    public void findByProductStatus() {

        List<ProductInfo> page = productService.findByProductStatus(ProductStatusEnum.UP.getCode());
        Assert.assertNotEquals(0, page.size());

    }

    @Test
    public void saveProduct() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("031389898");
        productInfo.setProductName("牛栏山白酒");
        productInfo.setProductPrice(new BigDecimal(15));
        productInfo.setProductDescription("很好喝的白酒");
        productInfo.setProductStock(10);
        productInfo.setProductIcon("http://xxxxxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(2);
        ProductInfo result = productService.saveProduct(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByCategoryTypeIn() {
        Integer category = 2;
        List<ProductInfo> list = productService.findByCategoryTypeIn(category);
        Assert.assertNotEquals(0, list.size());
    }

    @Test
    public void findAll()
    {
        PageRequest pageRequest =PageRequest.of(0, 2);
       Page<ProductInfo> productInfoPage =  productService.findAll(pageRequest);
        Assert.assertNotEquals(0, productInfoPage.getSize());
    }
    @Test
    public void onsale()
    {
        ProductInfo productInfo = productService.onSale(PRODUCTID);
        Assert.assertNotEquals(productInfo.getProductStatus(), ProductStatusEnum.DOWN.getCode() );

    }
    @Test
    public void offsale()
    {
        ProductInfo productInfo = productService.offSale(PRODUCTID);
        Assert.assertNotEquals(productInfo.getProductStatus(), ProductStatusEnum.UP.getCode() );

    }
    @Test
    public void testString()
    {
        System.out.println(StringUtils.isEmpty(""));
    }
}