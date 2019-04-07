package com.imooc.sell.repository;

import com.imooc.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/1 17:53
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;
    @Test
    public void test()
    {
        ProductCategory productCategory = repository.findById(1).orElse(null);
        System.out.println(productCategory.toString());
    }
    @Test

    public void saveTest()
    {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("凉菜");
        productCategory.setCategoryType(3);

        ProductCategory result = repository.save(productCategory);
        //设置断言用来测试一下是否为空
        Assert.assertNotNull(result);



    }
    @Test
    @Transactional
    public void updateTest()
    {
        ProductCategory productCategory = repository.findById(3).orElse(null);
        productCategory.setCategoryName("ssssssss");
        productCategory.setCategoryType(2);

        repository.save(productCategory);



    }
    @Test
    public void findProductCategory()
    {
        List<Integer> list = Arrays.asList(1,2,3);
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);

        Assert.assertNotEquals(0, result.size());
    }

}