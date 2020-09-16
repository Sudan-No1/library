package com.sd.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Package: com.sd.common.config.RabbitConfig
 * @Description: 
 * @author sudan
 * @date 2020/9/11 17:22
 */
 
@Slf4j
@Configuration
public class RabbitConnectConfig {

    @Bean
    public ConnectionFactory connectionFactory(@Value("${spring.rabbitmq.host}")String host,
                                               @Value("${spring.rabbitmq.port}")Integer port,
                                               @Value("${spring.rabbitmq.username}")String username,
                                               @Value("${spring.rabbitmq.password}")String password,
                                               @Value("${spring.rabbitmq.virtual-host}")String virtualHost){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost(host);
        cachingConnectionFactory.setPort(port);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setVirtualHost(virtualHost);
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

}