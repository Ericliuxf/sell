package com.synjones.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.rmi.runtime.Log;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/8/14 08:44
 */
@Component
@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     *  redis分布式锁:加锁
     * @param key 商品id
     * @param value 当前时间+过期时间
     * @return
     */
    public boolean lock(String key, String value){

        if(redisTemplate.opsForValue().setIfAbsent(key,value)){
            return true;
        }
        //避免死锁
        String currentValue = redisTemplate.opsForValue().get(key);

        //如果锁过期
        if(!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue)<System.currentTimeMillis()){

            // 获取上一次锁的value,新的线程锁赋值value
            String oldValue = redisTemplate.opsForValue().getAndSet(key,value);

            if(!StringUtils.isEmpty(oldValue) && currentValue.equals(oldValue)){
                return true;
            }

        }

        return false;
    }

    /**
     *  解锁
     * @param key
     * @param value
     */
    public void unlock(String key,String value){
        try{
            String currentValue = redisTemplate.opsForValue().get(key);

            if(StringUtils.isEmpty(key) && currentValue.equals(value)){
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e){
            log.error("【redis分布式锁】解锁异常");
        }



    }

}
