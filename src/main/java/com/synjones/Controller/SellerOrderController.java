package com.synjones.Controller;

import com.synjones.dto.OrderDTO;
import com.synjones.exception.SellException;
import com.synjones.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/11 14:37
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 查询订单列表
     */
    @GetMapping("/list")
    public ModelAndView findOrderList(@RequestParam(value = "page",defaultValue = "1")Integer page,
                                @RequestParam(value = "size",defaultValue = "10")Integer size,
                                Map<String,Object> map){
        PageRequest pageRequest = new PageRequest(page-1,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);
        map.put("orderDTOPage",orderDTOPage);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("/seller/list",map);
    }

    /**
     * 订单详情
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId")String orderId,
                               Map<String,Object> map){
        OrderDTO orderDTO = orderService.findOne(orderId);
        map.put("orderDTO",orderDTO);
        return  new ModelAndView("/seller/detail",map);
    }

    /**
     * 取消订单
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId")String orderId,
                               Map<String,Object> map){

        try{
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        }catch(SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
        }

        map.put("msg","【卖家端取消订单】取消订单成功");
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("/common/success",map);
    }

    /**
     * 完结订单
     */
    @GetMapping("finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId,
                               Map<String,Object> map){
        OrderDTO orderDTO = orderService.findOne(orderId);
        orderService.finished(orderDTO);
        map.put("msg","【卖家完结订单】完结订单成功");
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("/common/success",map);
    }



}
