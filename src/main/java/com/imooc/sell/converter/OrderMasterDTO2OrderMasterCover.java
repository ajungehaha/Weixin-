package com.imooc.sell.converter;

import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dto.OrderMasterDTO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**orderMaster->orderMasterDTO
 * @Author: 阿俊哥
 * @Date: 2019/3/7 17:10
 * @Version 1.0
 */
public class OrderMasterDTO2OrderMasterCover {

    public static OrderMasterDTO covert(OrderMaster orderMaster)
    {
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        BeanUtils.copyProperties(orderMaster, orderMasterDTO);
        return  orderMasterDTO;
    }

    public static List<OrderMasterDTO> covert(List<OrderMaster> orderMasterList)
    {
        return orderMasterList.stream().map(e ->covert(e)).collect(Collectors.toList());
    }
}
