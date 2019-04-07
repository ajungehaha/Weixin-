package com.imooc.sell.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/19 20:06
 * @Version 1.0
 */
@Data
public class ProductForm {

    private String productId;

    @NotEmpty(message = "名称不能为空")
    /*商品名称*/
    private String productName;
    @NotEmpty(message ="价格不能为空" )
    /*商品价格*/
    private BigDecimal productPrice;
    @Min(value = 1,message = "库存数不能小于1")
    /*商品库存*/
    private Integer productStock;

    /*商品介绍*/
    private String productDescription;

    /*商品图片*/
    private String productIcon;
    /*商品所在类目*/
    private Integer categoryType;
}
