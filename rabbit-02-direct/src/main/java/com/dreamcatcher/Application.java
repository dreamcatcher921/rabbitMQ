package com.dreamcatcher;

import com.dreamcatcher.config.RabbitConfig;
import com.dreamcatcher.service.MessageService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.Resource;

@SpringBootApplication
@EnableConfigurationProperties({RabbitConfig.class})
public class Application implements ApplicationRunner {
    @Resource
    private MessageService messageService;
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        //发送消息
        messageService.sendMsg();
    }
}