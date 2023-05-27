package com.dreamcatcher.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
@Slf4j
public class MessageService {
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(){
        //使用建造者模式创建消息
        Message message = MessageBuilder.withBody("hello world".getBytes()).build();
        //发送消息 参数：交换机名称，路由键，消息
        rabbitTemplate.convertAndSend("exchange.direct","error",message);
        log.info("发送消息成功,发送时间：{}",new Date());

    }
}
