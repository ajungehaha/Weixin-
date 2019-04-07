package com.imooc.sell.controller;

import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dto.OrderMasterDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/16 18:11
 * @Version 1.0
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {
    @Autowired
    OrderService orderService;

    /**
     * 获取订单列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                             @RequestParam(value = "size",defaultValue = "10")Integer size,
                                Map<String,Object> map){
        PageRequest pageRequest = PageRequest.of(page-1, size);
        Page<OrderMasterDTO> orderMasterDTOPage = orderService.findList(pageRequest);
        map.put("orderMasterDTOPage", orderMasterDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("order/list", map);
    }

    /**
     * 查询订单详情
     * @param orderId
     * @param map
     * @return
     */
    @RequestMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId")String orderId,
                               Map<String,Object> map)
    {
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        try {
             orderMasterDTO = orderService.findOneByOrderId(orderId);
        }catch (Exception e)
        {
            log.error("【卖家端查询订单详情】message={}",e.getMessage());
            map.put("msg",e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("orderMasterDTO", orderMasterDTO);
        return new ModelAndView("order/detail", map);
    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId")String orderId,
                               Map<String,Object> map)
    {
        //orderservice 里面会抛出异常，我们要捕获异常  关于异常的处理的
        try {
            OrderMasterDTO orderMasterDTO = orderService.cancel(orderId);
        }catch (Exception e)
        {
            log.error("【卖家端取消订单】message={}",e.getMessage());
            map.put("msg",e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }


        //返回消息和url
        map.put("msg",ResultEnum.SUCCESS.getMsg());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success",map);

    }
    @RequestMapping("/finsh")
    public ModelAndView finsh(@RequestParam("orderId")String orderId,
                              Map<String,Object> map)
    {
        OrderMasterDTO orderMasterDTO = orderService.findOneByOrderId(orderId);
        try {
            orderService.finsh(orderMasterDTO);
        }catch (Exception e)
        {
            log.error("【卖家端完结订单】message={}",e.getMessage());
            map.put("msg",e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }


        //返回消息和url
        map.put("msg",ResultEnum.SUCCESS.getMsg());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }
}
