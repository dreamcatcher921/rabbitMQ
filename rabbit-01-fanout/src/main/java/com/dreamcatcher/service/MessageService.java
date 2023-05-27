package com.dreamcatcher.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
@Slf4j
public class MessageService {
    @Resource //@Resource 默认按照名称进行装配 @Autowired 默认按照类型进行装配
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(){
        //定义发送消息
        String msg = "hello world";
        log.info("发送消息：{}",msg);
        Message message = new Message(msg.getBytes());
        //发送消息  参数：交换机名称，路由键，消息  扇形交换机不需要路由键
        rabbitTemplate.convertAndSend("exchange.fanout","",message);
        log.info("发送消息成功,发送时间：{}",new Date());
    }


}
