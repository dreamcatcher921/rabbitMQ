package com.dreamcatcher.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
@Slf4j
public class MyConfirmCallBack implements RabbitTemplate.ConfirmCallback {

    /**
     * 交换机确认回调方法
     * @param correlationData 相关数据
     * @param ack 交换机是否收到了消息
     * @param cause 失败的原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (correlationData != null) {
            log.info("关联ID:{}",correlationData.getId());
        }

        if(ack){
            log.info("消息正确到达交换机");
            return;
        }
        log.error("消息没有正确到达交换机，原因是:{}",cause);

    }


}
