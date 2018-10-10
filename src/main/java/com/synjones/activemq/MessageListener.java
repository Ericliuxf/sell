package com.synjones.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/8/29 11:24
 */
@RestController
public class MessageListener {

    @JmsListener(destination = "test_queue")
    public void getMessage(String text){
        System.out.println("接收到的消息："+text);
    }

}
