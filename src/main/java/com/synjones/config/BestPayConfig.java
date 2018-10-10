package com.synjones.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/5 12:25
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class BestPayConfig {

    /** 公众平台id */
    private String appid;

    /** 公众平台密钥 */
    private String secret;

    /** 微信开放平台 appid */
    private String openAppid;

    /** 微信开放平台 secret */
    private String openSecret;

    /** 商户号 */
    private String mchId;

    /** 商户密钥 */
    private String mchKey;

    /** 商户证书路径 */
    private String keyPath;

    /** 异步通知路径 */
    private String notifyUrl;

    private Map<String,String> templateId;

}
