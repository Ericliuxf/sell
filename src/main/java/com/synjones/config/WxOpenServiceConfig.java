package com.synjones.config;

import lombok.Data;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/16 14:26
 */
@Component
public class WxOpenServiceConfig {

    @Autowired
    private BestPayConfig bestPayConfig;

    @Bean
    private WxMpServiceImpl wxOpenService(){
        WxMpServiceImpl wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

    private WxMpConfigStorage wxMpConfigStorage(){
        WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpConfigStorage.setAppId(bestPayConfig.getOpenAppid());
        wxMpConfigStorage.setSecret(bestPayConfig.getOpenSecret());
        return wxMpConfigStorage;
    }
}


