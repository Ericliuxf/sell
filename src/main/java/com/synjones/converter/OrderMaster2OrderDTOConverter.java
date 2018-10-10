package com.synjones.converter;

import com.synjones.dataobject.OrderMaster;
import com.synjones.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/3 09:25
 */
public class OrderMaster2OrderDTOConverter {
    public static OrderDTO converter(OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }


    public static List<OrderDTO> converter(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(e -> OrderMaster2OrderDTOConverter.converter(e)).collect(Collectors.toList());

    }

}
