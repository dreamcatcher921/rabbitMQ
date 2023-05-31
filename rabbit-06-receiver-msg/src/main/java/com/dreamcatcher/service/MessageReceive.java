package com.dreamcatcher.service;


import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
@Slf4j
public class MessageReceive {
    //Channel channel(信道) 用于手动确认消息
    @RabbitListener(queues = {"queue.normal.4"})
    public void receiveMsg(Message message, Channel channel) {
        //获取消息属性
        MessageProperties properties = message.getMessageProperties();

        //获取消息的唯一标识，类似身份证或学号
        long deliveryTag = properties.getDeliveryTag();//获取消息的tagId 用于手动确认消息 唯一标识（身份证号）

        try {
            byte[] body = message.getBody();
            String msg = new String(body);
            log.info("receive msg:{},接收时间为:{}", msg, new Date());
            //TODO 业务处理
            int i = 1 / 0;
            //手动确认消息 参数1：消息的tagId 参数2：是否批量确认
            channel.basicAck(deliveryTag, false);

        } catch (Exception e) {
            log.error("接收者出现问题:{}", e.getMessage());
            try {
                //拒绝消息 参数1：消息的tagId 参数2：是否批量拒绝 参数3：是否重回队列
                channel.basicNack(properties.getDeliveryTag(), false, false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }
}
