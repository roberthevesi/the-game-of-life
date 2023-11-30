import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class EventConsumer implements Runnable {
    private final static String QUEUE_NAME = "cell_events";
    private final static String HOST = "localhost";
    private final static int PORT = 5672;

    @Override
    public void run() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST); // Change if RabbitMQ is on a different host
        factory.setPort(PORT);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + message + "'");
                System.out.println("HELLO @@@@@@@@@@@@@@@@@@@@@@");
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
