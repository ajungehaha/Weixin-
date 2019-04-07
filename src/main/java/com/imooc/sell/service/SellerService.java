package com.imooc.sell.service;

import com.imooc.sell.dataobject.SellerInfo;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/20 15:44
 * @Version 1.0
 */

public interface SellerService {

    SellerInfo findSellerInfo(String oprnid);

}
