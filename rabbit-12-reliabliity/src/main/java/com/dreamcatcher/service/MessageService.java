package com.dreamcatcher.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
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

    /**
     * 构造方法执行完之后自动执行
     */
    @PostConstruct
    public void init() {
        // 设置消息确认回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                log.error("消息没有到达交换机，原因是: {}", cause);
                //TODO 重发消息或者记录错误日志

            }
        });
        // 设置消息返回回调
        rabbitTemplate.setReturnsCallback((returnedMessage) -> {
            log.error("消息没有到达队列，交换机返回的原因是: {}", returnedMessage.getReplyText());
            //TODO 重发消息或者记录错误日志
        });
    }


    public void send() {
        MessageProperties messageProperties = new MessageProperties();
        // 设置单条消息持久化
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);

        Message message = MessageBuilder.withBody("Hello world!".getBytes()).andProperties(messageProperties).build();
        // 发送消息 头部交换机没有路由键
        rabbitTemplate.convertAndSend(exchangeName, "info", message);
        log.info("消息发送成功");
    }
}
