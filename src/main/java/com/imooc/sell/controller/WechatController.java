package com.imooc.sell.controller;

import com.google.common.base.Ascii;
import com.imooc.sell.config.ProjectUrlConfig;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.ResultException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxAccessToken;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/15 17:38
 * @Version 1.0
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpService wxOpenService;
    @Autowired
    ProjectUrlConfig projectUrlConfig;



    /**
     * 获取微信权限的开始方法
     * @param returnUrl
     * @return
     */

    @GetMapping("/authorize")
    public String  authorize(@RequestParam("returnUrl") String returnUrl)throws Exception
    {

        //1.配置
        //2.调用方法
        String url = projectUrlConfig.getWxMPServiceUrl()+ "/sell/wechat/userinfo";
        //返回之后从定向的url,调用方法
        String  redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE, URLEncoder.encode(returnUrl,"utf-8"));
        log.info("【微信网页授权】返回redirectUrl = {}",redirectUrl  );

        return "redirect:"+redirectUrl;

    }

    /**
     * 获取openid
     * @param code
     * @param returnUrl
     * @return
     */
    @GetMapping("/userinfo")
    public String  userInfo(@RequestParam("code") String code,
                         @RequestParam("state") String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken  = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】获取accessToken失败");
            throw new ResultException(ResultEnum.WXCHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("【微信网页授权】openid={}",openId);

         return "redirect:"+returnUrl+"?openid="+ openId;

    }

    /**
     * 微信网页扫码登录
     * @param returnUrl
     * @return
     */
    @RequestMapping("/qrauthorize")

    public String qrauthorize(@RequestParam("returnUrl") String returnUrl)throws Exception
    {
        //1.配置
        //2.调用方法
        String url = projectUrlConfig.getWxOpenServiceUrl()+"/sell/wechat/qruserinfo";
        //返回之后从定向的url,调用方法
        String  redirectUrl = wxOpenService.buildQrConnectUrl(url, WxConsts.QRCONNECT_SCOPE_SNSAPI_LOGIN, URLEncoder.encode(returnUrl,"utf-8"));
        log.info("【微信网页扫码登录】返回redirectUrl = {}",redirectUrl  );

        return "redirect:"+redirectUrl;
    }

    /**
     * 微信网页登录获取openid
     * @param code
     * @param returnUrl
     * @return
     */
    @RequestMapping("/qruserinfo")
    public String qruserinfo(@RequestParam("code") String code,
                             @RequestParam("state") String returnUrl)
    {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页扫码登录】获取accessToken失败");
            throw new ResultException(ResultEnum.WXCAHT_OPEN_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("【微信网页扫码登录】openid={}",openId);

        return "redirect:"+returnUrl+"?openid="+ openId;

    }
}
