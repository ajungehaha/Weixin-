package com.imooc.sell.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.ProductStatusEnum;
import com.imooc.sell.utils.CodeUtils;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**商品
 * @Author: 阿俊哥
 * @Date: 2019/3/2 12:57
 * @Version 1.0
 */
@Entity
@Data
@DynamicUpdate
@Table(name = "product_info")
public class ProductInfo {
    @Id
    private String productId;

    /*商品名称*/
    private String productName;

    /*商品价格*/
    private BigDecimal productPrice;

    /*商品库存*/
    private Integer productStock;

    /*商品介绍*/
    private String productDescription;

    /*商品图片*/
    private String productIcon;

    /*商品状态 0 上架 1 下架*/
    private Integer productStatus=ProductStatusEnum.UP.getCode();

    private Date createTime;

    private Date updateTime;

    /*商品所在类目*/
    private Integer categoryType;

    public ProductStatusEnum getProductStatusCode()
    {
        return CodeUtils.getCode(productStatus, ProductStatusEnum.class);
    }


}
