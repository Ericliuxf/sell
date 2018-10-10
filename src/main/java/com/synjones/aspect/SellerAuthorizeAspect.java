package com.synjones.aspect;

import com.synjones.constant.CookieConstant;
import com.synjones.constant.RedisConstant;
import com.synjones.exception.SellerAuthorizeException;
import com.synjones.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/30 09:54
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

//    @Pointcut("execution(public * com.synjones.Controller.Seller*.*(..))&& " +
//            "!execution(public * com.synjones.Controller.SellerInfoController.*(..))")
//    public void verify(){}

//    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //判断cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie.getValue()==null){
            log.warn("【登陆验证】cookie中查不到token");
            throw new SellerAuthorizeException();
        }

        String tokenVal = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));

        if(StringUtils.isEmpty(tokenVal)){
            log.warn("【登陆验证】redis中查不到token");
            throw new SellerAuthorizeException();
        }
    }

}
