package com.dreamcatcher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Application  implements ApplicationRunner {
    public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
    }

    /**
     * 实现ApplicationRunner接口，重写run方法
     * 系统启动任务
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("系统一启动就会执行这个方法");
    }
}
