package com.imooc.sell.controller;

import com.imooc.sell.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sun.rmi.runtime.Log;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/15 16:38
 * @Version 1.0
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {
    @GetMapping("/auth")
    public  void auth(@RequestParam("code") String code)
    {
        log.info("进入auth方法");
        log.info("code = {}",code);
        //获取code之后，访问另一个地址

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx1c05fb88f5293d60&secret=4410a54df4b6a3c5b53ea6f30bf7d881&code="+code+"&grant_type=authorization_code";
        //拿到accesstoken 就能拿到openid
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        log.info("授权的信息 response = {}",response);
        JsonUtil.toJson(response);
    }
}
