package com.synjones.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jms.DeliveryMode;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/8/28 16:25
 */
@RestController
@RequestMapping("/activemq")
public class QueueMQ {

    @Resource
    private JmsTemplate jmsTemplate;

    @RequestMapping("/queue")
    public void send(@RequestParam("text") String text){

        ActiveMQQueue destination = new ActiveMQQueue("test_queue");
        jmsTemplate.convertAndSend(destination,text);

    }
}
