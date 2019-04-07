package com.imooc.sell.serviceImpl;

import com.imooc.sell.config.WechatPayH5Config;
import com.imooc.sell.dto.OrderMasterDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.ResultException;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.PayService;
import com.imooc.sell.utils.JsonUtil;
import com.imooc.sell.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.sun.org.apache.xerces.internal.utils.XMLLimitAnalyzer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/11 20:00
 * @Version 1.0
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {

    public static final String ORDER_NAME="微信点餐订单";
    @Autowired
    private WechatPayH5Config wechatPayH5Config;
    @Autowired
    private BestPayServiceImpl bestPayService;
    @Autowired
    OrderService orderService;

    @Override
    public PayResponse create(OrderMasterDTO orderMasterDTO) {

        PayRequest payRequest = new PayRequest();

        payRequest.setOrderId(orderMasterDTO.getOrderId());
        payRequest.setOrderAmount(orderMasterDTO.getOrderAmount().doubleValue());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setOpenid(orderMasterDTO.getBuyOpenid());
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);


        log.info("【微信支付】 payrequest={}", payRequest);
        //发起支付
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】payresponse={}",payResponse);
        return payResponse;


    }

    @Override
    public PayResponse notify(String notifyData) {
        //1.验证签名
        //2.支付的状态
        //3.支付的金额 验证支付的金额
        //4.下单人和支付人是否一致

        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信异步通知】返回 notifyData={}",notifyData);
        //里面会有一个流水号
        OrderMasterDTO orderMasterDTO = orderService.findOneByOrderId(payResponse.getOrderId());
        //判断订单是否存在
        if(orderMasterDTO==null)
        {
            log.error("【微信异步通知订单不存在】orderId={}",payResponse.getOrderId());
            throw new ResultException(ResultEnum.ORDER_NOT_EXIT);
        }
        //判断系统金额和支付金额是否一直
        if(!MathUtil.equals(payResponse.getOrderAmount(),orderMasterDTO.getOrderAmount().doubleValue()))
        {
            log.error("【微信支付】支付金额和系统金额不一致orderId={}",payResponse.getOrderId());
            throw new ResultException(ResultEnum.WXCHAT_MONEY_NOT_SAME);
        }
        //2.修改订单的支付状态
        orderService.payid(orderMasterDTO);
        return payResponse;
    }

    @Override
    public void refund(OrderMasterDTO orderMasterDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderMasterDTO.getOrderId());
        refundRequest.setOrderAmount(orderMasterDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】 请求的参数", JsonUtil.toJson(refundRequest));
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】 返回的参数", JsonUtil.toJson(refundResponse));

    }
}
