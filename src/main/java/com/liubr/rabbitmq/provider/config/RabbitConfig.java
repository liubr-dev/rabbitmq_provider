package com.liubr.rabbitmq.provider.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * RabbitConfig
 * <p>
 *
 * @author liubr
 * @since 2020-05-15 11:29:14
 */
@Configuration
public class RabbitConfig {

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, b, s) -> {
            System.out.println("ConfirmCallback:     " + "相关数据：" + correlationData);
            System.out.println("ConfirmCallback:     " + "确认情况：" + b);
            System.out.println("ConfirmCallback:     " + "原因：" + s);
        });
        rabbitTemplate.setReturnCallback((message, i, s, s1, s2) -> {
            System.out.println("ReturnCallback:     " + "消息：" + message);
            System.out.println("ReturnCallback:     " + "回应码：" + i);
            System.out.println("ReturnCallback:     " + "回应信息：" + s);
            System.out.println("ReturnCallback:     " + "交换机：" + s1);
            System.out.println("ReturnCallback:     " + "路由键：" + s2);
        });
        return rabbitTemplate;
    }
}
