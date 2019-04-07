package com.imooc.sell.serviceImpl;

import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.repository.ProductCategoryRepository;
import com.imooc.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/1 21:30
 * @Version 1.0
 */
@Service
public class CategroyServiceImpl implements CategoryService {
    @Autowired
    ProductCategoryRepository repository;

    @Override
//    @Cacheable(cacheNames = "Category",key = "1234")
    public ProductCategory findById(Integer categoryId) {
        return repository.findById(categoryId).orElse(null);
    }

    @Override
//    @Cacheable(cacheNames = "Category",key = "123")
    public List<ProductCategory> findAllProductCategroys() {
        return repository.findAll();
    }

    @Override
//    @CachePut(cacheNames = "Category",key = "1234")
//    @CacheEvict(cacheNames = "Category",key = "1234")
    public ProductCategory saveProductCategory(ProductCategory productCategory) {
        return repository.save(productCategory);
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryList) {
        return repository.findByCategoryTypeIn(categoryList);
    }
}
