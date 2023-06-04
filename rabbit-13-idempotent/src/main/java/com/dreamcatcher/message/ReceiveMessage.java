package com.dreamcatcher.message;

import com.dreamcatcher.vo.Orders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Component
@Slf4j
public class ReceiveMessage {

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @RabbitListener(queues = {"queue.idempotent"})
    public void receiveMsg(Message message, Channel channel) throws IOException {
        // 获取消息的唯一标识
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        //使用objectMapper把字节数组反序列化为对象
        Orders orders = objectMapper.readValue(message.getBody(), Orders.class);


        try {
            log.info("接收到消息为: {}",orders.toString() );
            //setIfAbsent方法返回true表示设置成功，返回false表示设置失败
            Boolean setResult = stringRedisTemplate.opsForValue().setIfAbsent("idempotent:" + orders.getOrderId(), orders.getOrderId());
            if (setResult) {
                //TODO 向数据库插入订单
                log.info("向数据库插入订单");
            }
            //int a = 1 / 0;
            // 手动确认消息
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error("消息处理失败，原因是: {}", e.getMessage());
            try {
                channel.basicNack(deliveryTag, false, true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }
}
