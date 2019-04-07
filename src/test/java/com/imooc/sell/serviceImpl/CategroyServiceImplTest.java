package com.imooc.sell.serviceImpl;

import com.imooc.sell.dataobject.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/1 21:35
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategroyServiceImplTest {

    @Autowired
    CategroyServiceImpl categroyService;

    @Test
    public void findById() {
        ProductCategory productCategory = categroyService.findById(1);
        System.out.println(productCategory.toString());
    }

    @Test
    public void findAllProductCategroys() {
        List<ProductCategory> list = categroyService.findAllProductCategroys();
    }

    @Test
    public void saveProductCategory() {
        ProductCategory productCategory = new ProductCategory("饮料", 4);
        categroyService.saveProductCategory(productCategory);
    }

    @Test
    public void findByCategoryTypeIn() {
        categroyService.findByCategoryTypeIn(Arrays.asList(1,2,3,4));
    }
}