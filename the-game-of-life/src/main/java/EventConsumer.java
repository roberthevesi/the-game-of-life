import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class EventConsumer implements Runnable {
    private final static String QUEUE_NAME = "cell_events";
    private final static String HOST = "localhost";
    private final static int PORT = 5672;

    @Override
    public void run() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST); // Change if RabbitMQ is on a different host
        factory.setPort(PORT);
        try{
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + message + "'");
                handleMessage(message);
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleMessage(String message) {
        // Example message format: "CellType:Action"
        String[] parts = message.split(":");
        if (parts.length < 3) return; // Basic validation

        String cellType = parts[0];
        String action = parts[1];
        String cellId = parts[2];

        if(action.equals("die")){
            handleDie(cellId);
            return;
        }

        switch (cellType) {
            case "SexualCell" -> handleSexualCellAction(action, cellId);
            case "AsexualCell" -> handleAsexualCellAction(action, cellId);
            default -> System.out.println("Unknown cell type: " + cellType);
        }
    }

    private void handleDie(String cellId){
        Environment environment = Environment.getInstance();
        Cell cell = environment.getCellById(cellId);
        if(cell == null)
            return;

        System.out.println("Cell with id=" + cellId + " has died!");
        cell.setAlive(false);

        Random random = new Random();
        int randomFoodUnits = random.nextInt(5) + 1;

        environment.addFoodUnits(randomFoodUnits);
    }

    private void handleSexualCellAction(String action, String cellId) {
        Environment environment = Environment.getInstance();
        SexualCell cell = (SexualCell) environment.getCellById(cellId);
        if(cell == null)
            return;


        // Implement logic specific to AsexualCell
        System.out.println("Handling SexualCell action: " + action);
        if(action.equals("multiply")) {
            synchronized (environment) {
                if (!cell.getIsAlive() || !cell.isReadyToMultiply()) return;

                // Find a partner cell that is ready to multiply
                environment.getLock().lock();
                SexualCell partner = environment.findReadySexualCell(cell);

                if (partner != null) {
                    System.out.println("Two sexual cells have multiplied into a third hungry cell!");
                    SexualCell offspring = new SexualCell(cell.getT_starve(), cell.getT_full(), true, true, cell.getHealth(), cell.getTimes_eaten(), environment, false);
                    offspring.start(); // start the new thread

                    environment.addCell(offspring);

                    cell.setReadyToMultiply(false);
                    partner.setReadyToMultiply(false);
                }

                environment.getLock().unlock();
            }
        }

    }

    private void handleAsexualCellAction(String action, String cellId) {
        Environment environment = Environment.getInstance();
        AsexualCell cell = (AsexualCell) environment.getCellById(cellId);
        if(cell == null)
            return;

        if(!cell.getIsAlive())
            return;

        // Implement logic specific to AsexualCell
        System.out.println("Handling AsexualCell action: " + action);
        if(action.equals("multiply")) {

            System.out.println("Asexual cell has multiplied into two hungry cells!");
            // multiply through division: delete current cell, create two new hungry cells
            AsexualCell c1 = new AsexualCell(3, 6, true, true, 0, 0, environment);
            AsexualCell c2 = new AsexualCell(4, 3, true, true, 0, 0, environment);

            c1.start(); // start the new threads
            c2.start();

            environment.addCell(c1);
            environment.addCell(c2);
        }
    }
}
