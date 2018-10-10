package com.synjones.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/30 09:18
 */
public class CookieUtil {

    public static void set(HttpServletResponse response,
                           String name,
                           String value,
                           Integer maxAge){

        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static Map<String,Cookie> getCookieMap(HttpServletRequest request){
        Map<String ,Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(Cookie cookie: cookies){
                cookieMap.put(cookie.getName(),cookie);
            }
        }
        return cookieMap;
    }


    public static Cookie get(HttpServletRequest request,
                             String name){

        Map<String, Cookie> cookieMap = getCookieMap(request);

        if(cookieMap.containsKey(name)){
            return cookieMap.get(name);
        }
        return  null;
    }


}
