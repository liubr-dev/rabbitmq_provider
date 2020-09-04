package com.liubr.rabbitmq.provider.controller;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * SendmessageController
 * <p>
 *
 * @author liubr
 * @since 2020-05-14 14:39:59
 */
@RestController
@AllArgsConstructor
@RequestMapping("/message")
public class SendmessageController {

    private final RabbitTemplate rabbitTemplate;

    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "Hello!DirectMessage!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
        return "OK";
    }

    @GetMapping("/sendTopicMessage1")
    public String sendTopicMessage1() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "Message: man";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map map = putMap(messageId, messageData, createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.man", map);
        return "OK";
    }

    @GetMapping("/sendTopicMessage2")
    public String sendTopicMessage2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "Message: woman";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map map = putMap(messageId, messageData, createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.woman", map);
        return "OK";
    }

    @GetMapping("/sendTopicMessage3")
    public String sendTopicMessage3() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "Message: ALL";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map map = putMap(messageId, messageData, createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.#", map);
        return "OK";
    }

    @GetMapping("/sendFanoutMessage")
    public String sendFanoutMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "Message: testFanoutMessage";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map map = putMap(messageId, messageData, createTime);
        rabbitTemplate.convertAndSend("fanoutExchange", null, map);
        return "OK";
    }


    /**
     * 消息推送到server，但是在server里找不到交换机
     */
    @GetMapping("/TestMessageAck")
    public String TestMessageAck() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: non-existent-exchange test message ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map map = putMap(messageId, messageData, createTime);
        rabbitTemplate.convertAndSend("non-existent-exchange", "TestDirectRouting", map);
        return "ok";
    }

    /**
     * 消息推送到server，找到交换机了，但是没找到队列
     */
    @GetMapping("/TestMessageAck2")
    public String TestMessageAck2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: lonelyDirectExchange test message ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map map = putMap(messageId, messageData, createTime);
        rabbitTemplate.convertAndSend("LonelyDirectExchange", "TestDirectRouting", map);
        return "ok";
    }


    private Map putMap(String messageId, String messageData, String createTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        return map;
    }

}
