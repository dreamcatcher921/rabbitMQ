package com.dreamcatcher.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Slf4j
public class MessageService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Value("${my.exchangeName}")
    private String exchangeName;

    public void send() {
        {
            MessageProperties messageProperties = new MessageProperties();
            //延迟交换机 延迟消息要在消息头中加入 x-delay
            messageProperties.setHeader("x-delay", 25000);    // 设置消息的过期时间 25s 毫秒数
            Message message = MessageBuilder.withBody("Hello world 1!".getBytes())
                    .andProperties(messageProperties)
                    .build();
            // 发送消息 头部交换机没有路由键
            rabbitTemplate.convertAndSend(exchangeName, "plugins", message);
            log.info("消息发送成功,发送时间:{}", new Date());
        }
        {
            MessageProperties messageProperties = new MessageProperties();
            //messageProperties.setExpiration("15000");    // 设置消息的过期时间 15s 毫秒数
            messageProperties.setHeader("x-delay", 15000);
            Message message = MessageBuilder.withBody("Hello world 2!".getBytes())
                    .andProperties(messageProperties)
                    .build();
            // 发送消息 头部交换机没有路由键
            rabbitTemplate.convertAndSend(exchangeName, "plugins", message);
            log.info("消息发送成功,发送时间:{}", new Date());
        }
    }
}
