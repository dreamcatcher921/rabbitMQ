package com.dreamcatcher.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class MessageService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Value("${my.exchangeName}")
    private String exchangeName;

    public void send() {
        MessageProperties messageProperties = new MessageProperties();

        Map<String, Object> headers = new HashMap<>();
        headers.put("type", "m");
        headers.put("status", 1);
        messageProperties.setHeaders(headers); // 设置消息头

        Message message= MessageBuilder.withBody("Hello world!".getBytes())
                .andProperties(messageProperties)
                .build();
        // 发送消息 头部交换机没有路由键
        rabbitTemplate.convertAndSend(exchangeName, "", message);
        log.info("消息发送成功");
    }
}
