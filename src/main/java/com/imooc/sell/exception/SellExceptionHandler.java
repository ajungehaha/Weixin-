package com.imooc.sell.exception;

import com.imooc.sell.config.ProjectUrlConfig;
import com.imooc.sell.utils.JSONResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/17 14:26
 * @Version 1.0
 */
@RestControllerAdvice//抛出异常的时候会被这个类所捕获
@Slf4j
public class SellExceptionHandler {

    //定义一个异常的错误页面
    public static String EXCEPTION_VIEW="common/GlobalError";


    @Autowired
    ProjectUrlConfig projectUrlConfig;

    @ExceptionHandler(value = SellAuthorizeException.class)
    public ModelAndView authorizeExceptionHandle()
    {

        return new ModelAndView("redirect:"+projectUrlConfig.getWxOpenServiceUrl()+"/sell/seller/qrauthorize" +
                "?returnUrl="+projectUrlConfig.getSell()+
                "/sell/seller/login");
    }

    /**
     *
     * 全局捕获异常的方法
     * @param e
     * @param request
     * @param response
     * @return
     */

    @ExceptionHandler(value = ResultException.class)
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public Object errorHandel(Exception e, HttpServletRequest request, HttpServletResponse response)
    {
        e.printStackTrace();
        if(isAjax(request))
        {
            return JSONResultUtils.errorException(e.getMessage());
        }
        else {

            Map<String ,Object> map = new HashMap<>();
            map.put("url",request.getRequestURL());
            map.put("msg", e.getMessage());

            log.info("【错误】message={}",e.getMessage());
            return new ModelAndView(EXCEPTION_VIEW, map);
        }
    }

    /**
     * 判断是否是ajax的异常
     * @param httpRequest
     * @return
     */
    public static boolean isAjax(HttpServletRequest httpRequest){
        return  (httpRequest.getHeader("X-Requested-With") != null
                && "XMLHttpRequest"
                .equals( httpRequest.getHeader("X-Requested-With").toString()) );
    }
}
