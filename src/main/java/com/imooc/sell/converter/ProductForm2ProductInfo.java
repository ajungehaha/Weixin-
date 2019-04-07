package com.imooc.sell.converter;

import com.google.gson.Gson;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.form.ProductForm;
import com.imooc.sell.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/19 20:11
 * @Version 1.0
 */
@Slf4j
public class ProductForm2ProductInfo {
    /**
     * 进行productForm --> productInfo
     * @param productForm
     * @return
     */
    public static ProductInfo convert(ProductForm productForm,ProductInfo productInfo)
    {

        //进行转换,先判断productId
        if(StringUtils.isEmpty(productForm.getProductId()))
        {
            productInfo.setProductId(KeyUtils.getUniqueKey());
        }


        productInfo.setProductName(productForm.getProductName());
        productInfo.setProductPrice(productForm.getProductPrice());
        productInfo.setProductIcon(productForm.getProductIcon());
        productInfo.setProductDescription(productForm.getProductDescription());
        productInfo.setCategoryType(productForm.getCategoryType());
        productInfo.setProductStock(productForm.getProductStock());


        return productInfo;


    }
}
