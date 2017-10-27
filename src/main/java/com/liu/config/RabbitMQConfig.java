package com.liu.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 招股金服
 * CopyRight : www.zhgtrade.com
 * Author : liuyuanbo
 * Date： 2017/10/25
 */
@Configuration
public class RabbitMQConfig {
//    注入队列和交换机，通过bean注入，可以使用
//    这里是新建队列
    @Bean
    @Qualifier("hello")
    Queue hello(){
        return new Queue("hello");
    }

    @Bean
    Queue chat(){
        return new Queue("chat");
    }
//    新建交换机
//    第一种类型  top
    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange("topic");
    }
//群发交换机
    @Bean
    FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanout");
    }


//    交换机与队列绑定

    /**
     * 将队列topic.message与exchange绑定，binding_key为topic.message,就是完全匹配
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessage(Queue hello, TopicExchange exchange) {
        return BindingBuilder.bind(hello).to(exchange).with("topic.#");
    }

//    或者使用@Autowired @Qualifier配合使用，注明名称。@Qualifier也与@bean配合使用
//    @Bean
//    Binding bindingExchangeMessage(@Autowired @Qualifier("hello") Queue queue, TopicExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with("topic.message");
//    }



    /**
     * 将队列topic.messages与exchange绑定，binding_key为topic.#,模糊匹配
     * @param exchange
     * @return
     *
     */
    @Bean
//    注入queue和exchange的时候会分别根据不同情况注入，队列有两个 hello，和chat会根据名称注入
//    exchange也有两个，不同类型。会根据类型注入
    Binding bindingExchangeMessages(Queue chat, TopicExchange exchange) {
        return BindingBuilder.bind(chat).to(exchange).with("topic.#");
    }

    @Bean
    Binding bindingExchangeA(Queue chat,FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(chat).to(fanoutExchange);
    }

}
