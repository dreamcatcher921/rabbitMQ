package com.dreamcatcher.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class MessageReceive{
   @RabbitListener(queues = "{queue.normal.4}")
    public void receiveMsg(Message message){
       byte[] body = message.getBody();
       String msg = new String(body);

       log.info("receive msg:{},接收时间为:{}",msg,new Date());
   }
}
