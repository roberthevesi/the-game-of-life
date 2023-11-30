import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Thread consumerThread = new Thread(new EventConsumer());
        consumerThread.start();

        Environment environment = new Environment();
        environment.addFoodUnits(300);

        AsexualCell cellA1 = new AsexualCell(4, 3, false, true, 0, 0, environment);
        AsexualCell cellA2 = new AsexualCell(5, 6, false, true, 0, 0, environment);
        AsexualCell cellA3 = new AsexualCell(2, 5, false, true, 0, 0, environment);
        SexualCell cellS1 = new SexualCell(4, 3, false, true, 0, 0, environment, false);
        SexualCell cellS2 = new SexualCell(2, 4, false, true, 0, 0, environment, false);
        SexualCell cellS3 = new SexualCell(3, 4, false, true, 0, 0, environment, false);

        cellA1.start();
        cellA2.start();
        cellA3.start();
        cellS1.start();
        cellS2.start();
        cellS3.start();

        environment.addCell(cellA1);
        environment.addCell(cellA2);
        environment.addCell(cellA3);
        environment.addCell(cellS1);
        environment.addCell(cellS2);
        environment.addCell(cellS3);

        // Simulation loop
        for (int i = 0; i < 50; i++) {
            try {
                System.out.println("---------------------- Turn " + (i + 1) + " started. ----------------------");
                environment.simulateTurn();
                Thread.sleep(100); // Wait for a while before the next turn
                System.out.println("---------------------- Turn " + (i + 1) + " completed. ----------------------");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        environment.stopAllCells();
    }
}