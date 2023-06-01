package com.dreamcatcher.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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

    public void send() {

        Message message = MessageBuilder.withBody("Hello world!".getBytes()).build();
        CorrelationData correlationData = new CorrelationData();  // 相关数据 关联数据
        correlationData.setId("order_123456");


        // 发送消息 头部交换机没有路由键
        rabbitTemplate.convertAndSend(exchangeName, "info", message,correlationData);
        log.info("消息发送成功");
    }

    @PostConstruct // 在构造方法执行完之后执行,相当于初始化方法
    public void init(){
        //使用lambda表达式
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (correlationData != null) {
                log.info("关联ID:{}",correlationData.getId()+" ");
            }
            if(ack){
                log.info("消息正确到达交换机");
                return;
            }
            log.error("消息没有正确到达交换机，原因是:{}",cause);
        });
    }


}
