package com.sd.common.listener;

import com.rabbitmq.client.Channel;
import com.sd.common.constant.MqConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

/**
 * @Package: com.sd.common.listener.TeacherListener
 * @Description: 
 * @author sudan
 * @date 2020/9/14 11:13
 */
 
@Configuration
@Slf4j
public class TeacherListener {

    @RabbitListener(queues = MqConstant.BAODAO_QUEUE)
    public void baodao(Message message, Channel channel) throws Exception{
        String msgJson = new String(message.getBody(),"utf-8");
        log.info("老师接收到报告，学号：{}",msgJson);
    }

}