package com.dreamcatcher.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class MessageService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Value("${my.exchangeName}")
    private String exchangeName;

    public void send() {
        Message message = MessageBuilder.withBody("Hello world!".getBytes()).build();
        // 发送消息 头部交换机没有路由键
        rabbitTemplate.convertAndSend(exchangeName, "info", message);
        log.info("消息发送成功");
    }
}
