package com.synjones.Controller;

import com.synjones.converter.OrderForm2OrderDTOConverter;
import com.synjones.dto.OrderDTO;
import com.synjones.enums.ResultEnum;
import com.synjones.exception.SellException;
import com.synjones.form.OrderForm;
import com.synjones.service.BuyerOrderService;
import com.synjones.service.OrderService;
import com.synjones.util.ResultVoUtil;
import com.synjones.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.jms.QueueConnectionFactory;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/3 13:41
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private BuyerOrderService buyerOrderService;
    @Autowired
    private JmsTemplate jmsTemplate;

    //创建订单
    @RequestMapping("/create")
    public ResultVo create(@Valid OrderForm orderForm, BindingResult bindingResult){
        //1.表单校验
        if(bindingResult.hasErrors()){
            throw new SellException(ResultEnum.PARAM_NOT_EXIT.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        //2.将orderForm 转成 orderDTO，并验证商品数量不能为空
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.info("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_NOT_EMPTY);
        }
        //3.创建订单，返回orderId
        OrderDTO createResult = orderService.create(orderDTO);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",createResult.getOrderId());
        return ResultVoUtil.success(map);
    }

    //订单列表
    @GetMapping("/list")
    public ResultVo<List<OrderDTO>> findList(@RequestParam("openId") String openId,
                                   @RequestParam(value = "page" ,defaultValue = "0") Integer page,
                                   @RequestParam(value = "size",defaultValue = "10") Integer size){
        PageRequest pageRequest = new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage = orderService.findAll(openId, pageRequest);

        return ResultVoUtil.success(orderDTOPage.getContent());
    }

    //订单详情
    @PostMapping("/detail")
    public ResultVo findOrderOne(@RequestParam String openId, @RequestParam String orderId){
        OrderDTO orderDTO = buyerOrderService.findOrderOne(openId,orderId);

        return ResultVoUtil.success(orderDTO);
    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVo cancel(@RequestParam String openId, @RequestParam String orderId){
        buyerOrderService.cancel(openId,orderId);
        return  ResultVoUtil.success();
    }
}
