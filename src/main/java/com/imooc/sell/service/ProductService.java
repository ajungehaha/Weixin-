package com.imooc.sell.service;

import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.OrderDetailsDTO;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**商品service
 * @Author: 阿俊哥
 * @Date: 2019/3/2 13:18
 * @Version 1.0
 */
@Service
public interface ProductService {
    /*卖家使用的方法*/
    /*查询一个商品*/
    ProductInfo findById(String productId);

    /*查询所有的商品 根据状态*/
    List<ProductInfo> findByProductStatus(Integer productStatus);

    /*修改商品*/
    ProductInfo saveProduct(ProductInfo productInfo);

    /*根据类目查询商品*/
    List<ProductInfo> findByCategoryTypeIn(Integer categroyType);

    /*查询所得上商品 分页查询*/
    Page<ProductInfo> findAll(Pageable pageable);

    /*加库存*/
    void increaseStock(List<OrderDetail> orderDetails);
    /*减库存*/
    void decreaseStock(List<OrderDetail> orderDetails);
    /*下架*/
    ProductInfo offSale(String productId);
    /*上架*/
    ProductInfo onSale(String productId);



}
