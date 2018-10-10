package com.synjones.Controller;

import com.synjones.config.ProjectUrlConfig;
import com.synjones.enums.ResultEnum;
import com.synjones.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/4 17:14
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WeChatController{

    @Autowired
    private WxMpService wxMpService ;

    @Autowired
    private WxMpService wxOpenService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/authorize")
    public String auth(@RequestParam("returnUrl")String returnUrl){
        //1.配置  WxMpServiceConfig
        //2.调用
        String url = projectUrlConfig.getWechatMpAuthorize()+"/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE, URLEncoder.encode(returnUrl));
        log.info("【微信网页授权】获取code，result={}",redirectUrl);
        return "redirect:"+redirectUrl;
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code")String code,@RequestParam("state")String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e){
            log.error("【微信网页授权】{}",e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("回调地址：{}","redirect:"+returnUrl+"?openid="+openId);
        return "redirect:"+ returnUrl +"?openid="+openId;
    }

    /**
     * 扫码登陆
     */
    @GetMapping("qrAuthorize")
    private String qrAuthorize(@RequestParam("returnUrl")String returnUrl){

        String url = projectUrlConfig.getWechatOpenAuthorize()+"/sell/wechat/qrUserInfo";
        String redirecUrl = wxOpenService.buildQrConnectUrl(url, WxConsts.OAUTH2_SCOPE_BASE, URLEncoder.encode(returnUrl));

        return "redirect:"+redirecUrl;
    }

    @GetMapping("qrUserInfo")
    private String qrUserInfo(@RequestParam("code")String code,@RequestParam("state")String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();

        try{
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        }catch (WxErrorException e){
            log.info("【微信扫码登陆】error={}",e.getError().getErrorMsg());
            throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();

        return "redirect:"+returnUrl+"?openid="+openId;
    }

}
