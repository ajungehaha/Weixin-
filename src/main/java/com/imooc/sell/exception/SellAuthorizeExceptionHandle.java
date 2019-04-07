package com.imooc.sell.exception;

import com.imooc.sell.config.ProjectUrlConfig;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/21 18:37
 * @Version 1.0
 */
//@ControllerAdvice
public class SellAuthorizeExceptionHandle {
    @Autowired
    ProjectUrlConfig projectUrlConfig;

    @ExceptionHandler(value = SellAuthorizeException.class)
    public ModelAndView authorizeExceptionHandle()
    {

        return new ModelAndView("redirect:"+projectUrlConfig.getWxOpenServiceUrl()+"/sell/seller/qrauthorize" +
                "?returnUrl="+projectUrlConfig.getSell()+
                "/sell/seller/login");
    }

}
