package com.sd.common.config;

import com.sd.common.constant.MqConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Package: com.sd.common.config.RabbitConfig
 * @Description: 
 * @author sudan
 * @date 2020/9/16 14:30
 */
 
@Configuration
public class RabbitConfig {

    @Bean
    public Queue bookQueue(){
        return new Queue(MqConstant.BAODAO_QUEUE);
    }

    @Bean
    public Exchange bookExchange(){
        return new TopicExchange(MqConstant.BOOK_EXCHANGE);
    }

    @Bean
    public Binding bookBinding(){
        return BindingBuilder.bind(bookQueue()).to(bookExchange()).with(MqConstant.BAODAO_ROUTING_KEY).noargs();
    }

}