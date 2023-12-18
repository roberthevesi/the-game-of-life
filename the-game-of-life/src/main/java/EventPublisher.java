import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class EventPublisher {
    private final static String QUEUE_NAME = "cell_events";
    private final static String HOST = "localhost";
    private final static int PORT = 5672;

    public static void publish(String message) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST); // Change if RabbitMQ is on a different host
        factory.setPort(PORT);
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
