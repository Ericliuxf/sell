package com.synjones.service.impl;

import com.synjones.config.BestPayConfig;
import com.synjones.dto.OrderDTO;
import com.synjones.exception.SellException;
import com.synjones.service.PublishMessageService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/8/1 17:30
 */
@Service
public class PublishMessageServiceImpl implements PublishMessageService {

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private BestPayConfig bestPayConfig;

    /**
     * 微信模板消息推送
     * @param orderDTO
     */
    @Override
    public void orderStatus(OrderDTO orderDTO) {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId(bestPayConfig.getTemplateId().get("orderStatus"));
        wxMpTemplateMessage.setToUser(orderDTO.getBuyerOpenid());

        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("",""),
                new WxMpTemplateData("",""),
                new WxMpTemplateData("",""),
                new WxMpTemplateData("",""),
                new WxMpTemplateData("",""),
                new WxMpTemplateData("","")
        );

        wxMpTemplateMessage.setData(data);
        try{
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        }catch (WxErrorException e){
            throw new SellException(e.getError().getErrorCode(),e.getError().getErrorMsg());
        }
    }
}
