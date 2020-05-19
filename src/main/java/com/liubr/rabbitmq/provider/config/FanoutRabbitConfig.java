package com.liubr.rabbitmq.provider.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * <p>
 * FanoutRabbitConfig
 * <p>
 *
 * @author liubr
 * @since 2020-05-15 10:44:37
 */
@Configuration
public class FanoutRabbitConfig {
    /**
     * 创建三个队列 ：fanout.A   fanout.B  fanout.C
     * 将三个队列都绑定在交换机 fanoutExchange 上
     * 因为是扇型交换机, 路由键无需配置,配置也不起作用
     */

    @Bean
    public Queue queueA() {
        return new Queue("fanout.A");
    }

    @Bean
    public Queue queueB() {
        return new Queue("fanout.B");
    }

    @Bean
    public Queue queueC() {
        return new Queue("fanout.C");
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    public Binding bindingA() {
        return BindingBuilder.bind(queueA()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingB() {
        return BindingBuilder.bind(queueB()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingC() {
        return BindingBuilder.bind(queueC()).to(fanoutExchange());
    }


}
