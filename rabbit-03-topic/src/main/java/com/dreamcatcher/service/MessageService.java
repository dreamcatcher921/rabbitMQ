package com.dreamcatcher.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Slf4j
public class MessageService {

    @Resource  //AmqpTemplate是一个接口，它的实现类有RabbitTemplate
    private AmqpTemplate amqpTemplate;

    public void sendMsg(){
        Message message = MessageBuilder.withBody("hello rabbit".getBytes()).build();
        //发送消息 参数1：交换机名称  参数2：路由key  参数3：消息
        //amqpTemplate.convertAndSend("exchange.topic","hello.world",message); //hello.world匹配不到任何队列
        amqpTemplate.convertAndSend("exchange.topic","lazy.orange.rabbit",message);     //都有消息接收


        log.info("发送消息成功,发送时间:{}",new Date());


    }

}
