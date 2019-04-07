package com.imooc.sell.controller;


import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.form.CategoryForm;
import com.imooc.sell.service.CategoryService;
import freemarker.template.utility.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
 * @Date: 2019/3/19 21:57
 * @Version 1.0
 */
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    CategoryService categoryService;

    /**
     * 获取类目列表
     * @param map
     * @return
     */
    @RequestMapping("/list")

    public ModelAndView list(Map<String,Object> map)
    {

        List<ProductCategory> categoryList = categoryService.findAllProductCategroys();
        map.put("categoryList",categoryList);
        return new ModelAndView("category/list", map);

    }

    /**
     * 修改类目
     * @param categoryId
     * @param map
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId",defaultValue = "")Integer categoryId,
                              Map<String,Object> map)
    {
        if(!StringUtils.isEmpty(categoryId))
        {
            ProductCategory productCategory = categoryService.findById(categoryId);
            map.put("category",productCategory);
        }
        return new ModelAndView("category/index", map);
    }

    /**
     * 保存类目
     * @param categoryForm
     * @param bindingResult
     * @param map
     * @return
     */
    @RequestMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm,
                             BindingResult bindingResult,
                             Map<String,Object> map)
    {
        if(bindingResult.hasErrors())
        {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error", map);
        }
        ProductCategory productCategory = new ProductCategory();

        try {
            if(categoryForm.getCategoryId()!=null)
            {
                productCategory = categoryService.findById(categoryForm.getCategoryId());
            }
            BeanUtils.copyProperties(categoryForm, productCategory);
            categoryService.saveProductCategory(productCategory);
        }catch (Exception e)
        {
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error", map);
        }

        map.put("url","/sell/seller/category/list");
        return new ModelAndView("common/success", map);
    }
}
