package com.liubr.rabbitmq.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class RabbitmqProviderApplication {

    public static void main(String[] args) {
        log.info("MQ项目启动开始!!!!!!!!!!!!!!!!!!!!!!");
        SpringApplication.run(RabbitmqProviderApplication.class, args);
        log.info("MQ项目启动成功!!!!!!!!!!!!!!!!!!!!!!");
    }

}
