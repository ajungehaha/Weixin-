package com.imooc.sell.form;

import lombok.Data;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/19 22:09
 * @Version 1.0
 */
@Data
public class CategoryForm {

    /*id*/
    private Integer categoryId;

    /*
   名称
    */
    private String categoryName;
    /*
    类目编号
     */
    private Integer categoryType;
}
