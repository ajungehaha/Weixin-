package com.imooc.sell.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/15 17:41
 * @Version 1.0
 */
@Component//注解为配置类
public class WechatMPConfig {
    @Autowired
    WechatAccountConfig wechatAccountConfig;
    @Bean
    public WxMpService wxMpService()
    {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }
    @Bean
    public WxMpConfigStorage wxMpConfigStorage()
    {
        WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
        //在这里我们要设置appid 和 appsecret 需要在配置文件里面设置两个变量，这样全局都可以用
        //然后设置一个WechatAccountConfig类，来注入这两个参数，这样在使用的时候就可以直接调用这两个类
         wxMpConfigStorage.setAppId(wechatAccountConfig.getMpAppId());
         wxMpConfigStorage.setSecret(wechatAccountConfig.getMpAppSecret());
         return  wxMpConfigStorage;
    }
}
