package com.rabbitmq.sample.topics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.sample.utils.RabbitMQUtils;

import java.nio.charset.StandardCharsets;

/**
 * description:
 *
 * @author rock
 * time 2020/7/10 0010 13:11
 */
public class ReceiveLogsTopic {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = RabbitMQUtils.getConnectionFactory();

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String queueName = channel.queueDeclare().getQueue();

//        if (argv.length < 1) {
//            System.err.println("Usage: ReceiveLogsTopic [binding_key]...");
//            System.exit(1);
//        }
//
//        for (String bindingKey : argv) {
//            channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
//        }

//        // receive all the logs
//        String bindingKey = "#";
//        channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);

//        // receive all logs from the facility "kern"
//        String bindingKey = "kern.*";
//        channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);

//        // hear only about "critical" logs
//        String bindingKey = "*.critical";
//        channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);

        // multiple bindings
        String bindingKey1 = "kern.*";
        String bindingKey2 = "*.critical";
        channel.queueBind(queueName, EXCHANGE_NAME, bindingKey1);
        channel.queueBind(queueName, EXCHANGE_NAME, bindingKey2);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
