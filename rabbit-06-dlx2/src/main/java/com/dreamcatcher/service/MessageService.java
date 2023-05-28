package com.dreamcatcher.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Slf4j
public class MessageService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMsg() {
        // 设置消息属性
        MessageProperties messageProperties = new MessageProperties();
        //设置单条消息过期时间 数据类型是字符串
        messageProperties.setExpiration("15000");  // 设置消息过期时间 15s
        Message message = MessageBuilder.withBody("Hello world!".getBytes()).build();
        // 发送消息  绑定正常交换机和正常队列
        rabbitTemplate.convertAndSend("exchange.normal.2", "order", message);
        log.info("消息发送成功,发送时间:{}", new Date());
    }
}
