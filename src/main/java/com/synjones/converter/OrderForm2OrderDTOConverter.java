package com.synjones.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.synjones.dataobject.OrderDetail;
import com.synjones.dto.OrderDTO;
import com.synjones.form.OrderForm;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/3 13:57
 */
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm){
        Gson gson = new Gson();
        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList = gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){}.getType());

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenId());
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }
}
