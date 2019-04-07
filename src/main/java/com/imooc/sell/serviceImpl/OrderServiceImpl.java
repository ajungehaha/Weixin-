package com.imooc.sell.serviceImpl;

import com.imooc.sell.converter.OrderMasterDTO2OrderMasterCover;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.OrderDetailsDTO;
import com.imooc.sell.dto.OrderMasterDTO;
import com.imooc.sell.enums.OrderPayStatusEnum;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.ResultException;
import com.imooc.sell.repository.OrderDetailRepository;
import com.imooc.sell.repository.OrderMasterRepository;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.PayService;
import com.imooc.sell.service.ProductService;
import com.imooc.sell.utils.KeyUtils;
import com.sun.xml.internal.bind.v2.TODO;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/6 17:13
 * @Version 1.0
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMasterRepository masterRepository;
    @Autowired
    OrderDetailRepository detailRepository;
    @Autowired
    ProductService productService;
    @Autowired
    PayService payService;

    @Override
    @Transactional
    public OrderMasterDTO createOrder(OrderMasterDTO orderMasterDTO) {
        //创建订单
        //1.根据controller 的传输的数据，我们要先查询商品
        List<OrderDetail> orderDetailList = orderMasterDTO.getOrderDetailList();

        BigDecimal orderAmount = new BigDecimal(BigDecimal.ROUND_UP);

       // List<OrderDetail> orderDetailList = new ArrayList<>();

        String orderId = KeyUtils.getUniqueKey();

        //循环遍历查找
        for(OrderDetail orderDetail: orderDetailList)
        {
            String detailId = KeyUtils.getUniqueKey();

            ProductInfo productInfo = new ProductInfo();
            //查找到一个商品

            productInfo = productService.findById(orderDetail.getProductId());
            if(productInfo==null)
            {
                throw new ResultException(ResultEnum.PRODUCT_NOT_EXIT);

            }

            //为orderdetail 设置属性值
//            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(detailId);
//            orderDetail.setProductQuantity(orderDetailsDTO.getProductQuantity());
            BeanUtils.copyProperties(productInfo, orderDetail);
            //2. 根据商品计算总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            //3.保存订单商品详情
            detailRepository.save(orderDetail);

        }
        //4.扣库存
        productService.decreaseStock(orderDetailList);

        //5.保存ordermaster
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderMasterDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        //默认新订单
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        //默认未支付
        orderMaster.setPayStatus(OrderPayStatusEnum.WAITPAY.getCode());
        masterRepository.save(orderMaster);

        //6.设置订单返回对象
        orderMasterDTO.setOrderId(orderId);


        return orderMasterDTO;
    }

    @Override
    public OrderMasterDTO findOneByOrderId(String orderId) {
        //1.查询ordermaster
        OrderMaster orderMaster = masterRepository.findById(orderId).orElse(null);
        if(orderMaster == null)
        {
            log.error("【查询订单】单个订单不存在 orderid={}",orderId);
            return  null;
        }
//        if(!orderMaster.getBuyOpenid().equals(openId))
//        {
//            log.error("【查询订单】 订单所属不一致 orderid = {} openid = {}",orderId,openId);
//            throw  new ResultException(ResultEnum.ORDER_OPENID_ERROR);
//        }

        //2.查询订单详情 根据订单的orderid
        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList = detailRepository.findByOrderId(orderMaster.getOrderId());
        if(orderDetailList.isEmpty())
        {
            throw new ResultException(ResultEnum.ORDER_DETAILS_NOT_EXIT);
        }
        //3.保存到ordermasterDto
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        BeanUtils.copyProperties(orderMaster, orderMasterDTO);
        orderMasterDTO.setOrderDetailList(orderDetailList);
        return orderMasterDTO;
    }


    @Override
    public Page<OrderMasterDTO> findList(String openId,Pageable pageable) {

        //查询所有的订单
        Page<OrderMaster> orderMasterPage = masterRepository.findByBuyOpenid(openId,pageable);
        //进行对象的转换

        List<OrderMasterDTO> orderMasterDTOList = OrderMasterDTO2OrderMasterCover.covert(orderMasterPage.getContent());

        Page<OrderMasterDTO> orderMasterDTOPage = new PageImpl<>(orderMasterDTOList, pageable, orderMasterPage.getTotalElements());

        return orderMasterDTOPage;
    }

    @Override
    @Transactional
    public OrderMasterDTO finsh(OrderMasterDTO orderMasterDTO) {
        //1.查询订单状态
       if(!orderMasterDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()))
       {
           log.error("【完结订单】订单状态错误 orderId = {} orderStatus = {}",orderMasterDTO.getOrderId(),orderMasterDTO.getOrderStatus());
           throw new ResultException(ResultEnum.ORDER_STATUS_ERROR);
       }
       //2.查看支付状态
        if(orderMasterDTO.getPayStatus().equals(OrderPayStatusEnum.WAITPAY.getCode()))
        {
            log.error("【完结订单】 订单未支付，不允许完结 orderId = {}",orderMasterDTO.getOrderId());
            throw new ResultException(ResultEnum.ORDER_NOT_PAY);
        }
        //3.修改订单状态
        orderMasterDTO.setOrderStatus(OrderStatusEnum.FINSH.getCode());
       OrderMaster orderMaster = new OrderMaster();
       BeanUtils.copyProperties(orderMasterDTO, orderMaster);
        OrderMaster updateResult = masterRepository.save(orderMaster);
        if(updateResult == null)
        {
            log.error("【完结订单】订单未找到 orderid = {} orderMasterDTO = {}",orderMasterDTO.getOrderId(),orderMasterDTO);
            throw new ResultException(ResultEnum.ORDER_UPDATE_FAIL);
        }


        return orderMasterDTO;
    }

    @Override
    @Transactional
    public OrderMasterDTO payid(OrderMasterDTO orderMasterDTO) {
        //1.查看订单状态，是不是新下单，只有新下单才可以支付
        if(!orderMasterDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()))
        {
            log.error("【支付订单】订单状态错误 orderId = {} orderStatus = {}",orderMasterDTO.getOrderId(),orderMasterDTO.getOrderStatus());
            throw new ResultException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2.判断支付状态
        if(!orderMasterDTO.getPayStatus().equals(OrderPayStatusEnum.WAITPAY.getCode()))
        {
            log.error("【支付订单】订单支付状态错误 orderId = {} orderStatus = {}",orderMasterDTO.getOrderId(),orderMasterDTO.getOrderStatus());
            throw new ResultException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //3.修改支付状态
        orderMasterDTO.setPayStatus(OrderPayStatusEnum.FINSHPAY.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderMasterDTO, orderMaster);
        OrderMaster updateResult = masterRepository.save(orderMaster);
        if(updateResult == null)
        {
            log.error("【支付订单】订单支付未完成 orderid = {} orderMasterDTO = {}",orderMasterDTO.getOrderId(),orderMasterDTO);
            throw new ResultException(ResultEnum.ORDER_UPDATE_FAIL);
        }


        return orderMasterDTO;
    }

    @Override
    @Transactional
    public OrderMasterDTO cancel(String orderId) {
        //注意前端返回的是openid 和 orderid

        //1.取消订单 判断订单状态
        OrderMaster orderMaster = masterRepository.findById(orderId).orElse(null);
        if(orderMaster == null)
        {
            log.error("【查询订单】 订单不存在 orderId = {}",orderId);
            throw new ResultException(ResultEnum.ORDER_NOT_EXIT);
        }
        if(!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()))
        {    //如果错处我们应该先将订单的状态和orderid 记录一下 利用日志
            log.error("【取消订单】订单取消失败 orderId: {} orderStatus: {}",orderMaster.getOrderId(),orderMaster.getOrderStatus());
            throw  new ResultException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2.修改订单状态，将订单状态转换为取消
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
//在这里不进行验证确实是不安全，但是我们还有卖家端，我们的卖家端的openid不一样怎么办，所以需要单独的建立方法来写
//        //为了保证人的一致性
//        if(!orderMaster.getBuyOpenid().equals(openId))
//        {
//            log.error("【取消订单】订单所属人不一致 openid={}",openId);
//            throw  new ResultException(ResultEnum.ORDER_OPENID_ERROR);
//        }
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        BeanUtils.copyProperties(orderMaster, orderMasterDTO);
        OrderMaster updateResult = masterRepository.save(orderMaster);
        if(updateResult == null)
        {
            log.error("【取消订单】订单更新失败 orderId:{} orderStatus:{}",orderMaster.getOrderId(),orderMaster.getOrderStatus());

            throw new ResultException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //3.返还库存
        //查询订单详情的所有订单列表

        List<OrderDetail> orderDetailList = detailRepository.findByOrderId(orderMasterDTO.getOrderId());
        productService.increaseStock(orderDetailList);

        //4.如果已支付，就要退款
        if(orderMaster.getPayStatus().equals(OrderPayStatusEnum.FINSHPAY.getCode()))
        {
            //退款
            payService.refund(orderMasterDTO);

        }

        return orderMasterDTO;
    }

    @Override
    public Page<OrderMasterDTO> findList(Pageable pageable) {
        //查询所有的订单
        Page<OrderMaster> orderMasterPage = masterRepository.findAll(pageable);
        //进行对象的转换

        List<OrderMasterDTO> orderMasterDTOList = OrderMasterDTO2OrderMasterCover.covert(orderMasterPage.getContent());

        Page<OrderMasterDTO> orderMasterDTOPage = new PageImpl<>(orderMasterDTOList, pageable, orderMasterPage.getTotalElements());

        return orderMasterDTOPage;
    }
}
