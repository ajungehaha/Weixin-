package com.imooc.sell.controller;

import com.imooc.sell.converter.ProductForm2ProductInfo;
import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.form.ProductForm;
import com.imooc.sell.service.CategoryService;
import com.imooc.sell.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/17 18:19
 * @Version 1.0
 */
@Controller
@Slf4j
@RequestMapping("/seller/product")
public class SellerProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;


    /**
     * 获取商品列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                             @RequestParam(value = "size",defaultValue = "10")Integer size,
                             Map<String,Object> map)
    {
        PageRequest pageRequest =PageRequest.of(page-1, size);
        Page<ProductInfo> productInfoPage = productService.findAll(pageRequest);
        map.put("productInfoPage",productInfoPage );
        map.put("currentPage", page);
        map.put("size", size);

        return new ModelAndView("product/list", map);
    }

    /**
     * 上架商品
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("/onSale")
    public ModelAndView onSale(@RequestParam("productId")String productId,
                               Map<String,Object> map)
    {
        try {
            productService.onSale(productId);
        }
        catch (Exception e)
        {
            log.error("【卖家端上架商品】message={}",e.getMessage());
            map.put("msg",e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }

        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success");
    }

    /**
     * 下架商品
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("/offSale")
    public ModelAndView offSale(@RequestParam("productId")String productId,
                               Map<String,Object> map)
    {
        try {
            productService.offSale(productId);
        }
        catch (Exception e)
        {
            log.error("【卖家端下架商品】message={}",e.getMessage());
            map.put("msg",e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success");
    }

    /**
     * 获取商品的详情 修改 如果有值，就查询，没有就是空
     * @param productId
     * @param map
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId",defaultValue = "")String productId,
                              Map<String ,Object> map)
    {
        //判断productId是否为空
        if(!StringUtils.isEmpty(productId)){
            ProductInfo productInfo = productService.findById(productId);
            map.put("productInfo", productInfo);
        }
        //查询类目
        List<ProductCategory> productCategorieList = categoryService.findAllProductCategroys();
        map.put("productCategorieList", productCategorieList);
        return new ModelAndView("product/index", map);
    }

    /**
     * 保存和修改商品
     * @param productForm
     * @param bindingResult
     * @param map
     * @return
     */
    @RequestMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult,
                             Map<String,Object> map)
    {
        //判断有没有出错
        if(bindingResult.hasErrors())
        {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }
        //对于修改，我们要先查询基本的值，再进行更新
        //捕获一下异常
        try{
            ProductInfo productInfo = new ProductInfo();
            if(StringUtils.isEmpty(productForm.getProductId()))
            {
                productInfo = ProductForm2ProductInfo.convert(productForm,productInfo);
            }
            else{
                productInfo = productService.findById(productForm.getProductId());
                productInfo = ProductForm2ProductInfo.convert(productForm,productInfo);
            }

            productService.saveProduct(productInfo);
        }catch (Exception e)
        {
            map.put("msg", ResultEnum.ERROR.getMsg());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }


        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }


}
