package com.dreamcatcher.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
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

        Message message = MessageBuilder.withBody("Hello world!".getBytes()).build();
        // 发送消息  绑定正常交换机和正常队列
        rabbitTemplate.convertAndSend("exchange.normal.a", "order", message);
        log.info("消息发送成功,发送时间:{}", new Date());
    }
}
