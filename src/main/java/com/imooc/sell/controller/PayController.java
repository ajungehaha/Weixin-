package com.imooc.sell.controller;

import com.imooc.sell.dto.OrderMasterDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.ResultException;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import com.sun.javafx.collections.MappingChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/11 19:55
 * @Version 1.0
 */
@Controller
@RequestMapping("/pay")
public class PayController {
    @Autowired
    OrderService orderService;
    @Autowired
    PayService payService;

    /**
     * 支付订单
     * @param orderId
     * @param returnUrl
     * @return
     */
    @RequestMapping("/create")
    public ModelAndView create(@RequestParam("orderId")String orderId,
                               @RequestParam("returnUrl")String returnUrl){
        //1.查询订单
        OrderMasterDTO orderMasterDTO = orderService.findOneByOrderId(orderId);
        if(orderMasterDTO == null)
        {

            throw new ResultException(ResultEnum.ORDER_NOT_EXIT);
        }
        //调用支付"
        PayResponse payResponse = payService.create(orderMasterDTO);
        Map<String,Object> map = new HashMap<>();
        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);
        return new ModelAndView("pay/create", map);

    }

    /**
     * 返回异步通知
     * @param notifyData
     * @return
     */
    @PostMapping("/notify")
    public ModelAndView notify(@RequestParam("notifyData")String notifyData)
    {
        PayResponse payResponse = payService.notify(notifyData);
        Map<String ,Object> map = new HashMap<>();
        map.put("payResponse", payResponse);
        //返回处理结果给微信，不然微信会一致访问你的notify方法
        return new ModelAndView("pay/success" , map);
    }
    //微信退款相比于微信支付会多一个证书，证书需要在支付的商户平台-->账户设置-->api安全里面有个证书下载
    //一共有四个证书，我们只需要第一个，将他放在项目路径下，必须是有可读的权限的
}
