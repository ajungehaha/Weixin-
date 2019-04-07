package com.imooc.sell.serviceImpl;

import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.enums.ProductStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.ResultException;
import com.imooc.sell.repository.ProductInfoRespository;
import com.imooc.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/2 14:05
 * @Version 1.0
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductInfoRespository respository;
    @Override
    public ProductInfo findById(String productId) {

        return respository.findById(productId).orElse(null);
    }

    @Override
    public List<ProductInfo> findByProductStatus(Integer productStatus) {
        return respository.findByProductStatus(productStatus);
    }

    @Override
    public ProductInfo saveProduct(ProductInfo productInfo) {
        return respository.save(productInfo);
    }

    @Override
    public List<ProductInfo> findByCategoryTypeIn(Integer categroyType) {
        return respository.findByCategoryTypeIn(categroyType);
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return respository.findAll(pageable);
    }

    @Override
    @Transactional
    public void increaseStock(List<OrderDetail> orderDetails) {

        for(OrderDetail orderDetail:orderDetails)
        {
            ProductInfo productInfo = respository.getOne(orderDetail.getProductId());
            if(productInfo==null)
            {
                throw  new ResultException(ResultEnum.PRODUCT_NOT_EXIT);
            }
            else{
                productInfo.setProductStock(productInfo.getProductStock() + orderDetail.getProductQuantity());
                respository.save(productInfo);
            }
        }


    }

    @Override
    @Transactional
    //扣库存可能会出现超卖现象
    public void decreaseStock(List<OrderDetail> orderDetails) {
        for(OrderDetail orderDetail:orderDetails)
        {
            ProductInfo productInfo = respository.getOne(orderDetail.getProductId());
            if(productInfo==null)
            {
                throw  new ResultException(ResultEnum.PRODUCT_NOT_EXIT);
            }
            Integer result = productInfo.getProductStock() - orderDetail.getProductQuantity();
            if(result<0)
            {
                throw new ResultException(ResultEnum.PRODUCT_NOT_ENOUGH);
            }
            else{
                productInfo.setProductStock(result);
                respository.save(productInfo);
            }
        }

    }

    @Override
    public ProductInfo offSale(String productId) {

        ProductInfo productInfo = respository.findById(productId).orElse(null);
        if(productInfo == null)
        {
            throw new ResultException(ResultEnum.PRODUCT_NOT_EXIT);
        }
        if(productInfo.getProductStatus() == ProductStatusEnum.DOWN.getCode())
        {
            throw new ResultException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return respository.save(productInfo);
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = respository.findById(productId).orElse(null);
        if(productInfo == null)
        {
            throw new ResultException(ResultEnum.PRODUCT_NOT_EXIT);
        }
        if(productInfo.getProductStatus() == ProductStatusEnum.UP.getCode())
        {
            throw new ResultException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return respository.save(productInfo);
    }


}
