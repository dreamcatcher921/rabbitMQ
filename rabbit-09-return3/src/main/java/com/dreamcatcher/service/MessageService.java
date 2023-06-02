package com.dreamcatcher.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
@Slf4j
public class MessageService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Value("${my.exchangeName}")
    private String exchangeName;


    @PostConstruct
    public void init() {
        //使用匿名内部类的方式实现回调
     /*   rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returned) {
                log.error("消息从交换机没有正确的路由（投递）队列，原因是：{}",returned.getReplyText());
            }
        });*/
        //使用lambda表达式实现回调
        rabbitTemplate.setReturnsCallback(returned -> log.error("消息从交换机没有正确的路由（投递）队列，原因是：{}",returned.getReplyText()));    //设置回调
    }

    //发送消息到达交换机知不知道，要开启confirm模式 不开启confirm模式，消息到达交换机不知道（自己也就不知道成没成功）
    //发送消息到达队列知不知道，要开启return模式 不开启return模式，消息到达队列不知道（自己也就不知道成没成功）
    public void send() {
        Message message = MessageBuilder.withBody("Hello world!".getBytes()).build();
        // 发送消息 头部交换机没有路由键
        rabbitTemplate.convertAndSend(exchangeName, "info", message);
        log.info("消息发送成功");
    }


}
