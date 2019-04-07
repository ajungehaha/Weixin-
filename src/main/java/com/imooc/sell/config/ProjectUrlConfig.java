package com.imooc.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/20 16:41
 * @Version 1.0
 */
@Data
@ConfigurationProperties(prefix = "project-url")
@Component
public class ProjectUrlConfig {

    private String WxMPServiceUrl;

    private String WxOpenServiceUrl;

    private String sell;
}
