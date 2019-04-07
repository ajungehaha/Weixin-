package com.imooc.sell.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 阿俊哥
 * @Date: 2019/3/20 20:13
 * @Version 1.0
 */
public class CookieUtil {
    /**
     * 设置
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void set(HttpServletResponse response,
                           String name,
                           String value,
                           int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 返回cookie
     * @param request
     * @param name
     * @return
     */
    public static Cookie get(HttpServletRequest request,

                           String name)
    {
        Map<String,Cookie> cookieMap = cookies2map(request);
        if(cookieMap!=null)
        {
            if(cookieMap.containsKey(name))
            {
                return cookieMap.get(name);
            }

        }
       return null;
    }

    /**
     * 将cookie 转换为 map
     * @param request
     * @return
     */
    private static Map<String,Cookie> cookies2map(HttpServletRequest request)
    {

        Map<String,Cookie> map  = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies!=null)
        {
            for(Cookie cookie:cookies)
            {
                map.put(cookie.getName(), cookie);
            }
        }
        return map;
    }
}
