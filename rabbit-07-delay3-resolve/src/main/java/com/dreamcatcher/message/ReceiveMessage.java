package com.dreamcatcher.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class ReceiveMessage {
    /**
     * 延迟队列一定要接受死信队列的消息
     * @param message
     */
    @RabbitListener(queues = {"queue.delay.dlx.3"})
    public void receiveMsg(Message message){
        String body = new String(message.getBody());
        log.info("接受的消息为:{},接受时间为：{}",body,new Date());
    }

}
