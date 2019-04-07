package com.imooc.sell.config;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**微信网页端扫码登录配置service
 * @Author: 阿俊哥
 * @Date: 2019/3/20 16:20
 * @Version 1.0
 */
@Component
public class WxchatOpenConfig {

    @Autowired
    WechatAccountConfig wechatAccountConfig;


    @Bean
    public WxMpService wxOpenService()
    {
        WxMpService wxOpenService = new WxMpServiceImpl();
        wxOpenService.setWxMpConfigStorage(wxMpInMemoryConfigStorage());
        return  wxOpenService;
    }

    @Bean
    public WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage()
    {
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpInMemoryConfigStorage.setAppId(wechatAccountConfig.getOpenAppId());
        wxMpInMemoryConfigStorage.setSecret(wechatAccountConfig.getOpenAppSecret());
        return  wxMpInMemoryConfigStorage;
    }

}
