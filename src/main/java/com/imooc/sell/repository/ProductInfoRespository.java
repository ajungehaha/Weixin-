package com.imooc.sell.repository;

import com.imooc.sell.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;

/**商品
 * @Author: 阿俊哥
 * @Date: 2019/3/2 13:12
 * @Version 1.0
 */

@Repository
public interface ProductInfoRespository extends JpaRepository<ProductInfo,String> {
    /*根据一个类目的类目 查询部分商品*/
    List<ProductInfo> findByCategoryTypeIn(Integer categroyType);

    /*查询上架的商品*/

    List<ProductInfo> findByProductStatus(Integer productStatus);


}
