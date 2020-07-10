package com.rabbitmq.sample.utils;

import com.rabbitmq.client.ConnectionFactory;

/**
 * description:
 *
 * @author rock
 * time 2020/7/10 0010 11:08
 */
public class RabbitMQUtils {
    public static ConnectionFactory getConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.17.161");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");

        return factory;
    }
}
