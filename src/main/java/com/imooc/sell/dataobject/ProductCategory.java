package com.imooc.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 类目
 * @Author: 阿俊哥
 * @Date: 2019/3/1 17:39
 * @Version 1.0
 */
@Entity
@Table(name = "product_category")
@DynamicUpdate//动态更新时间  动态更新的意思
@Data
public class ProductCategory implements Serializable {

    private static final long serialVersionUID = -552697962180954028L;
    /*
        id
         */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    /*
    名称
     */
    private String categoryName;
    /*
    类目编号
     */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
