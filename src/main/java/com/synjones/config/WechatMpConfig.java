package com.synjones.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/5 08:34
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatMpConfig {
    private String appid;
    private String secret;

}
