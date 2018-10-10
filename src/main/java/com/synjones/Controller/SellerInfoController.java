package com.synjones.Controller;

import com.synjones.constant.CookieConstant;
import com.synjones.constant.RedisConstant;
import com.synjones.exception.SellException;
import com.synjones.service.SellerInfoService;
import com.synjones.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/27 11:05
 */
@Controller
@RequestMapping("/seller")
public class SellerInfoController {

    @Autowired
    private SellerInfoService sellerInfoService;

    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping("/login")
    private ModelAndView login(@RequestParam("openid")String openid,
                               Map<String,Object> map,
                               HttpServletResponse response){

        //1.查询买家信息
        try{
            sellerInfoService.findByOpenid(openid);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("/common/error",map);
        }

        //2.保存redis
        String token = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,RedisConstant.Expire,TimeUnit.SECONDS);

        //3.设置cookie
        CookieUtil.set(response,CookieConstant.TOKEN,token,CookieConstant.EXPIRE);

        return new ModelAndView("/seller/order/list");
    }

    @GetMapping("/loginOut")
    public ModelAndView loginOut(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Map<String,Object> map){
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);

        //清除redis
        redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));

        //清除cookie
        CookieUtil.set(response,CookieConstant.TOKEN,null,0);

        return  new ModelAndView("/seller/order/list");
    }

}
