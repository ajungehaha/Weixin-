package com.imooc.sell.controller;

import com.imooc.sell.config.ProjectUrlConfig;
import com.imooc.sell.constant.CookieContant;
import com.imooc.sell.constant.RedisContant;
import com.imooc.sell.dataobject.SellerInfo;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.ResultException;
import com.imooc.sell.service.SellerService;
import com.imooc.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/20 19:43
 * @Version 1.0
 */
@Controller
@RequestMapping("/seller")
@Slf4j
public class SellerLoginController {

    @Autowired
    private SellerService sellerService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @RequestMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String,Object> map)
    {
        //根据openid  查询是否存在
        SellerInfo sellerInfo = sellerService.findSellerInfo(openid);
        if(sellerInfo == null)
        {
           log.error("【微信扫码登录失败】用户未找到 ");
           throw new ResultException(ResultEnum.LOGIN_ERROR);

        }
        //找到后就保存到redis
        //生成token
        String token = UUID.randomUUID().toString();
        //设置超时时间
        Integer expire = RedisContant.EXPIRE;

        redisTemplate.opsForValue().set(String.format(RedisContant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);

        //保存到cookies
        CookieUtil.set(response, CookieContant.TOKEN, token, expire);


        return new ModelAndView("redirect:"+projectUrlConfig.getSell()+"/sell/seller/order/list");

    }
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String ,Object> map)
    {
        //1.从cookie 里面查询 如果有
        Cookie cookie = CookieUtil.get(request,  CookieContant.TOKEN);
        if(cookie!=null)
        {
            //2.删除cookie数据
            CookieUtil.set(response, CookieContant.TOKEN, null, 0);
            //3.删除redis的数据
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisContant.TOKEN_PREFIX, cookie.getValue()));

        }

        map.put("msg", ResultEnum.LOGOUT_SUCCESS);
        map.put("url", projectUrlConfig.getSell()+"/sell/seller/order/list");
        return new ModelAndView("common/success", map);


    }
}
