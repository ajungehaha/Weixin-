package com.imooc.sell.controller;

import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.enums.ProductStatusEnum;
import com.imooc.sell.service.CategoryService;
import com.imooc.sell.service.ProductService;
import com.imooc.sell.utils.ResultVOUtils;
import com.imooc.sell.vo.ProductInfoVO;
import com.imooc.sell.vo.ProductVO;
import com.imooc.sell.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/4 19:21
 * @Version 1.0
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

//重点 不要把查询数据库放到for循环里面，要一次性查询

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @GetMapping("/list")
    @Cacheable(cacheNames = "product",key = "productList")
    public ResultVO list()
    {
        //1.查询所有的上架商品
        //2.查询类目（一次查询）
        //3.数据的拼装

        //查询数据

        //查询所有的类目，先查询所有的类目的list
        List<ProductInfo> productInfos = productService.findByProductStatus(ProductStatusEnum.UP.getCode());

//        List<Integer> categorylist = new ArrayList<>();
//        for (ProductInfo productInfo: productInfos
//             ) {
//            categorylist.add(productInfo.getCategoryType());
//
//        }
        //lambda表达式
        List<Integer> categorylist  = productInfos.stream().map(e->e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> categories = categoryService.findByCategoryTypeIn(categorylist);

        List<ProductVO> productVOList = new ArrayList<>();
        for(ProductCategory productCategory: categories)
        {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());
            List<ProductInfoVO> productInfoVOS = new ArrayList<>();
            for (ProductInfo productInfo:productInfos)
            {

                //在这里我们要先判断是否属于这个类目，如果属于就添加到这个类目中
                if(productCategory.getCategoryType().equals(productInfo.getCategoryType()))
                {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    //这里我们要拷贝属性  有个beanutils 可以帮助我们
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    //加入到productInfoVos 中
                    productInfoVOS.add(productInfoVO);
                }

            }
            productVO.setProductInfoVOS(productInfoVOS);
            productVOList.add(productVO);
        }


        return ResultVOUtils.success(productVOList);
    }

}
