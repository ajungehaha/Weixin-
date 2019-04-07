package com.imooc.sell.serviceImpl;

import com.imooc.sell.config.WechatAccountConfig;
import com.imooc.sell.dto.OrderMasterDTO;
import com.imooc.sell.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/21 19:58
 * @Version 1.0
 */
@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {


    @Autowired
    WxMpService wxMpService;
    @Autowired
    WechatAccountConfig wechatAccountConfig;
    @Override
    public void templateOrderStatus(OrderMasterDTO orderMasterDTO) {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId(wechatAccountConfig.getTemplateId().get("OrderStatus"));
        wxMpTemplateMessage.setToUser(orderMasterDTO.getBuyOpenid());

        List<WxMpTemplateData> wxMpTemplateData = Arrays.asList(
                new WxMpTemplateData("first","订单完结"),
                new WxMpTemplateData("keyword1","微信点餐快餐店"),
                new WxMpTemplateData("keyword2","11111111"),
                new WxMpTemplateData("keyword3",orderMasterDTO.getOrderId()),
                new WxMpTemplateData("keyword4",orderMasterDTO.getOrderStatusCode().getMessage()),
                new WxMpTemplateData("keyword5",orderMasterDTO.getOrderAmount().toString()),
                new WxMpTemplateData("remark","谢谢惠顾")
        );
        wxMpTemplateMessage.setData(wxMpTemplateData);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        }catch (WxErrorException errorException){
            log.error("【微信模板消息推送】出现错误！" );
        }


    }
}
