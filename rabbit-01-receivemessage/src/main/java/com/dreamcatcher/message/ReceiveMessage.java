package com.dreamcatcher.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReceiveMessage {

    @RabbitListener(queues = {"queue.fanout.a","queue.fanout.b"}) //监听队列
    public void receiveMessage(Message message) {
        byte[] body = message.getBody();
        //将字节数组转换为字符串
        String msg = new String(body);
        log.info("接收到消息：{}",msg);
    }
}
