package com.imooc.sell.serviceImpl;

import com.imooc.sell.dataobject.SellerInfo;
import com.imooc.sell.repository.SellerInfoRepository;
import com.imooc.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/20 15:46
 * @Version 1.0
 */
@Service
public class SellerServiceImpl implements SellerService {


    @Autowired
    SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findSellerInfo(String oprnid) {
        return sellerInfoRepository.findByOpenid(oprnid) ;
    }
}
