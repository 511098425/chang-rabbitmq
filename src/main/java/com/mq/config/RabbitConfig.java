package com.mq.config;

import com.mq.constants.RabbitMqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * rabbit配置
 *
 * @author Mr.Chang
 * @create 2018-04-09 15:22
 **/
@Slf4j
@Configuration
public class RabbitConfig {


    /**
     *
     *
     * 以下三个参数可以定义在yml配置文件，也可以在下面绑定的时候写死，配置文件更灵活
     * 可以根据自己命名喜好，在yml里面定义
     *
     *
     */
    @Value("${rabbit.queue.name}")
    String messageQueue;

    @Value("${rabbit.topic.exchange}")
    String topicExchange;

    @Value("${rabbit.topic.routeKey}")
    String routeKey;

    @Bean
    public Queue getMessageQueue(){
        return new Queue(messageQueue);
    }

     @Bean
    TopicExchange exchangeFanout(){
        return new TopicExchange("amq-fanout");
    }

   @Bean
    public Queue getMessageQueue1(){
        return new Queue("chang-queue-1");
    }

    @Bean
    public Queue getMessageQueue2(){
        return new Queue("chang-queue-2");
    }

    @Bean
    TopicExchange exchange(){
        return new TopicExchange(topicExchange);
    }

    @Bean
    Binding bindingExchange(){
        return BindingBuilder.bind(getMessageQueue()).to(exchange()).with(routeKey);
  }

    @Bean
    Binding bindingFanout1Exchange(){
        return BindingBuilder.bind(getMessageQueue1()).to(exchangeFanout()).with("amq.fanout.message");
    }
    @Bean
    Binding bindingFanout2Exchange(){
        return BindingBuilder.bind(getMessageQueue2()).to(exchangeFanout()).with("amq.fanout.message");
    }

    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(RabbitMqConstants.DEFAULT_EXCHANGE, true, false);
    }

    @Bean
    public Queue repeatTradeQueue() {
        Queue queue = new Queue(RabbitMqConstants.DEFAULT_REPEAT_TRADE_QUEUE_NAME,true,false,false);
        return queue;
    }

    @Bean
    public Binding repeatTradeBinding() {
        return BindingBuilder.bind(repeatTradeQueue()).to(defaultExchange()).with(RabbitMqConstants.DEFAULT_REPEAT_TRADE_QUEUE_NAME);
    }

    @Bean
    public Queue deadLetterQueue() {
        Map<String, Object> arguments = new HashMap<>(2);
        arguments.put("x-dead-letter-exchange", RabbitMqConstants.DEFAULT_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", RabbitMqConstants.DEFAULT_REPEAT_TRADE_QUEUE_NAME);
        Queue queue = new Queue(RabbitMqConstants.DEFAULT_DEAD_LETTER_QUEUE_NAME,true,false,false,arguments);
        log.info("参数：{}",queue.getArguments());
        return queue;
    }

    @Bean
    public Binding  deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(defaultExchange()).with(RabbitMqConstants.DEFAULT_DEAD_LETTER_QUEUE_NAME);
    }

    @Bean
    public Queue queue() {
        Queue queue = new Queue(RabbitMqConstants.NORMAL_QUEUE,true);
        return queue;
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(defaultExchange()).with(RabbitMqConstants.NORMAL_QUEUE);
    }

}
