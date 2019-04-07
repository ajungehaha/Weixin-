package com.imooc.sell.controller;

import com.imooc.sell.converter.OrderForm2OrderMasterDTO;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dto.OrderMasterDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.ResultException;
import com.imooc.sell.form.OrderForm;
import com.imooc.sell.service.BuyerService;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.utils.ResultVOUtils;
import com.imooc.sell.vo.ResultVO;
import jdk.jfr.events.ThrowablesEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/8 17:18
 * @Version 1.0
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;
    @Autowired
    private WebScoket webScoket;
    /*创建订单*/
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderform, BindingResult bindingResult)
    {
        //判断表单校验之后有没有错误
        if(bindingResult.hasErrors())
        {
            log.error("【创建订单】 参数不正确");
            throw new ResultException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().toString());
        }
        OrderMasterDTO orderMasterDTO = OrderForm2OrderMasterDTO.convert(orderform);
        if(orderMasterDTO.getOrderDetailList().isEmpty())
        {
            log.error("【创建订单】购物车不能为空" );
            throw new ResultException(ResultEnum.BUYCAR_IS_NULL);
        }
        OrderMasterDTO orderMasterResult = orderService.createOrder(orderMasterDTO);

        Map<String,String> map = new HashMap<>();
        map.put("orderId", orderMasterResult.getOrderId());

        //websocket推送消息
        webScoket.sendMessage("您有新的订单   编号："+orderMasterResult.getOrderId());

        return ResultVOUtils.success(map);
    }
    /*查询订单列表*/
    @GetMapping("/list")
    public ResultVO<List<OrderMasterDTO>> list(@RequestParam("openid") String openid,
                                              @RequestParam(value = "page",defaultValue = "0")  Integer page,
                                              @RequestParam(value = "size",defaultValue = "10")Integer size)
    {
        //判断传输的数据
        if(StringUtils.isEmpty(openid))
        {
            log.error("【查询订单列表】openid 为空");
            throw new ResultException(ResultEnum.PARAM_ERROR);
        }
        //调用service 方法
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<OrderMasterDTO> masterDTOPage = orderService.findList(pageRequest);
        //将page对象转换为list
        List<OrderMasterDTO> orderMasterDTOList = masterDTOPage.getContent();
        return  ResultVOUtils.success(orderMasterDTOList);

    }
    /*订单详情*/
    @GetMapping("/detail")
    public ResultVO<OrderMasterDTO> detail(@RequestParam("openid")String openid,
                                                 @RequestParam("orderid")String orderid)
    {
        OrderMasterDTO orderMasterDTO = buyerService.findOrderOne(orderid, openid);
        return ResultVOUtils.success(orderMasterDTO);
    }
    /*取消订单*/
    @GetMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid")String openid,
                           @RequestParam("orderid")String orderid)
    {
        buyerService.cancelOrder(orderid, openid);
        return ResultVOUtils.success();
    }
}
