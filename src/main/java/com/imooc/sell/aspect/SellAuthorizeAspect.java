package com.imooc.sell.aspect;

import com.imooc.sell.constant.CookieContant;
import com.imooc.sell.constant.RedisContant;
import com.imooc.sell.exception.SellAuthorizeException;
import com.imooc.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.rmi.runtime.Log;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**切面进行登录验证
 * @Author: 阿俊哥
 * @Date: 2019/3/21 18:17
 * @Version 1.0
 */
//@Component
//@Aspect
//@Slf4j
//public class SellAuthorizeAspect {
//
//    @Autowired
//    RedisTemplate redisTemplate;
//
//    //进行验证的切面
//    @Pointcut("execution(public * com.imooc.sell.controller.Seller*.*(..))"+
//    "&& !execution(public * com.imooc.sell.controller.SellerLoginController.*(..))")
//    public void verify(){}
//
//
//    @Before("verify()")
//    public void doVerify()  {
//        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//
//        HttpServletRequest request = servletRequestAttributes.getRequest();
//
//        //查询cookie
//        Cookie cookie = CookieUtil.get(request, CookieContant.TOKEN);
//        if(cookie == null)
//        {
//            log.error("【登录验证】错误，没有登录");
//            throw new SellAuthorizeException();
//        }
//        //查询radis
//        if (!redisTemplate.opsForValue().getOperations().hasKey(String.format(RedisContant.TOKEN_PREFIX, cookie))) {
//            log.error("【登录验证】错误，没有登录");
//            throw new SellAuthorizeException();
//
//        }
//
//    }
//}
