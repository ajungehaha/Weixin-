package com.imooc.sell.service;

import com.imooc.sell.dataobject.ProductCategory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/1 21:11
 * @Version 1.0
 */
@Service
public interface CategoryService {
    //卖家端需要的方法
    //查找一个类目
    ProductCategory findById(Integer categoryId);
    //查找所有的类目
    List<ProductCategory> findAllProductCategroys();
    //新增类目 更新类目
    ProductCategory saveProductCategory(ProductCategory productCategory);


    //买家端需要的方法
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryList);



}
