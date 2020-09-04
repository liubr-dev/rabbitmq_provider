package com.liubr.rabbitmq.provider.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * <p>
 * TopicRabbitConfig
 * <p>
 *
 * @author liubr
 * @since 2020-05-15 09:11:34
 */
@Configuration
public class TopicRabbitConfig {

    /**
     * 绑定键
     */
    public static final String man = "topic.man";
    public static final String woman = "topic.woman";
    public static final String all = "topic.all";

    @Bean
    public Queue firstQueue() {
        return new Queue(TopicRabbitConfig.man);
    }

    @Bean
    public Queue secendQueue() {
        return new Queue(TopicRabbitConfig.woman);
    }

    @Bean
    public Queue thirdQueue() {
        return new Queue(TopicRabbitConfig.all);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    /**
     * 将firstQueue和topicExchange绑定,而且绑定的键值为topic.man
     * 这样只要是消息携带的路由键是topic.man,才会分发到该队列
     */
    @Bean
    Binding bindingExchangeMessage1() {
        return BindingBuilder.bind(firstQueue()).to(topicExchange()).with("topic.man");
    }

    @Bean
    Binding bindingExchangeMessage2() {
        return BindingBuilder.bind(secendQueue()).to(topicExchange()).with("topic.woman");
    }

    /**
     * 将secondQueue和topicExchange绑定,而且绑定的键值为用上通配路由键规则topic.#
     * 这样只要是消息携带的路由键是以topic.开头,都会分发到该队列
     */
    @Bean
    Binding bindingExchangeMessage3() {
        return BindingBuilder.bind(thirdQueue()).to(topicExchange()).with("topic.#");
    }

}
