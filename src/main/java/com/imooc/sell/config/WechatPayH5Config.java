package com.imooc.sell.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/15 20:46
 * @Version 1.0
 */
@Component

public class WechatPayH5Config {
    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Bean
    public BestPayServiceImpl bestPayService()
    {
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config());
        return  bestPayService;
    }

    //第三方的sdk要先写入一个h5的配置文件，配置必要的参数
    @Bean
    public WxPayH5Config wxPayH5Config()
    {
        WxPayH5Config wxPayH5Config  = new WxPayH5Config();
        wxPayH5Config.setAppId(wechatAccountConfig.getMpAppId());
        wxPayH5Config.setAppSecret(wechatAccountConfig.getMpAppSecret());
        wxPayH5Config.setKeyPath(wechatAccountConfig.getKeyPath());
        wxPayH5Config.setMchId(wechatAccountConfig.getMpAppId());
        wxPayH5Config.setMchKey(wechatAccountConfig.getMchKey());
        wxPayH5Config.setNotifyUrl(wechatAccountConfig.getNotifyUrl());
        return  wxPayH5Config;
    }

}
