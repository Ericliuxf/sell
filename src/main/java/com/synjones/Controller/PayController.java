package com.synjones.Controller;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.synjones.dto.OrderDTO;
import com.synjones.enums.ResultEnum;
import com.synjones.exception.SellException;
import com.synjones.service.OrderService;
import com.synjones.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/5 13:51
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @Autowired
    private BestPayServiceImpl bestPayService;
    /**
     *  支付订单
     */
    @GetMapping("/create")
    public ModelAndView pay(@RequestParam("orderId")String orderId,
                            @RequestParam("returnUrl")String returnUrl,
                            Map<String,Object> map){
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(null==orderDTO){
            throw new SellException(ResultEnum.ORDER_NOT_EXIT);
        }

        PayResponse payResponse = payService.create(orderDTO);
        map.put("payResponse",payResponse);
        map.put("returnUrl",returnUrl);

        return new ModelAndView("pay/create",map);
    }

    /**
     * 异步通知
     */
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData){

        payService.notify(notifyData);

        // 返回微信处理结果，微信不在发异步通知。
        return new ModelAndView("pay/success");
    }

}
