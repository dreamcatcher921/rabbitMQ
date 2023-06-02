package com.dreamcatcher.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReceiveMessage {

    @RabbitListener(queues = {"queue.properties.1"})
    public void receiveMsg(Message  message) {
        log.info("接收到的消息为:{}", new String(message.getBody()));


    }
}
