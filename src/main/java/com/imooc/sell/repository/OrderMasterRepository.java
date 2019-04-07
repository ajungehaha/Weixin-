package com.imooc.sell.repository;

import com.imooc.sell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/4 21:46
 * @Version 1.0
 */
@Repository
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {
    //查询当日的且完结的订单 未完成的订单 新订单
    Page<OrderMaster> findByCreateTimeAndOrderStatus(Date date,Integer orderStaus,Pageable pageable);

    //查询某个人所有的订单 分页来查
    Page<OrderMaster> findByBuyOpenid(String buyOpenid, Pageable pageable);

    //分页查询所有的订单

    Page<OrderMaster> findAll(Pageable pageable);

}
