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

        String str = "Hello world!";
        Message message = MessageBuilder.withBody(str.getBytes()).build();
        rabbitTemplate.convertAndSend("exchange.normal.4", "order", message);

        log.info("消息发送成功,发送时间:{}", new Date());
    }
}
