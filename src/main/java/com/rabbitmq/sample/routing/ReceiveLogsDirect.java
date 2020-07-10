package com.rabbitmq.sample.routing;

import com.rabbitmq.client.*;
import com.rabbitmq.sample.utils.RabbitMQUtils;

import java.nio.charset.StandardCharsets;

/**
 * description:
 *
 * @author rock
 * time 2020/7/10 0010 11:33
 */
public class ReceiveLogsDirect {
    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = RabbitMQUtils.getConnectionFactory();

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();

//        if (argv.length < 1) {
//            System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]");
//            System.exit(1);
//        }
//
//        for (String severity : argv) {
//            channel.queueBind(queueName, EXCHANGE_NAME, severity);
//        }

        // see all the log messages
        String severity1 = "info";
        String severity2 = "warning";
        String severity3 = "error";
        channel.queueBind(queueName, EXCHANGE_NAME, severity1);
        channel.queueBind(queueName, EXCHANGE_NAME, severity2);
        channel.queueBind(queueName, EXCHANGE_NAME, severity3);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}
