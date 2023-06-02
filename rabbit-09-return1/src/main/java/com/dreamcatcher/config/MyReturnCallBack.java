package com.dreamcatcher.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyReturnCallBack implements RabbitTemplate.ReturnsCallback {
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        //实现这个接口，如果这个接口的方法被回调了，说明消息没有被正确的路由到队列中（到达交换机就是没有到达队列）
        //如果它不回调，说明消息被正确的路由到队列中了
        //confirm不管到没到交换机（成没成功）他都会回调

        //也是三部曲第一个开启return模式 第二个实现这个接口  第三个在rabbitTemplate设置这个接口的实现类
        log.error("消息从交换机没有正确的路由（投递）队列，原因是：{}",returnedMessage.getReplyText());
    }
}
