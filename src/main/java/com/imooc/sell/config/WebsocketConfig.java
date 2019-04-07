package com.imooc.sell.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/22 14:06
 * @Version 1.0
 */
//建立服务器连接，必须配置这样的一个类
@Component
public class WebsocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter()
    {
        return new ServerEndpointExporter();
    }
}
