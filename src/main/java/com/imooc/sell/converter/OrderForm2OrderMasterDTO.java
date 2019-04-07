package com.imooc.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonToken;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dto.OrderMasterDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.ResultException;
import com.imooc.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/8 20:16
 * @Version 1.0
 */
@Slf4j
public class OrderForm2OrderMasterDTO {

    public static OrderMasterDTO convert(OrderForm orderForm)
    {
        Gson gson = new Gson();

        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        orderMasterDTO.setBuyName(orderForm.getName());
        orderMasterDTO.setBuyPhone(orderForm.getPhone());
        orderMasterDTO.setBuyAdress(orderForm.getAddress());
        orderMasterDTO.setBuyOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
           orderDetailList =  gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e)
        {
            log.error("【对象转换错误】错误 String = {}",orderForm.getItems());
            throw new ResultException(ResultEnum.PARAM_ERROR);
        }


        orderMasterDTO.setOrderDetailList(orderDetailList);
        return  orderMasterDTO;
    }
}
