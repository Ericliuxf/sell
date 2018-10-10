package com.synjones.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/11 10:15
 */
@Data
@ConfigurationProperties(prefix = "projectUrl")
@Component
public class ProjectUrlConfig {
    /** 微信公众平台授权url */
    private String wechatMpAuthorize;

    /** 微信开放平台授权url */
    private String wechatOpenAuthorize;

    /** 点餐系统 */
    private String sell;

}


